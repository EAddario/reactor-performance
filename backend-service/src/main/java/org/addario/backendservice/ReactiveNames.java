package org.addario.backendservice;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.core.scheduler.Schedulers;
import reactor.math.MathFlux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.IntStream;

public class ReactiveNames {
    private static final Random r = new Random();
    private static final List<String> namesList = Names.namesList;

    public static Mono<String> getName(int count) {
        var names = IntStream.range(0, count).mapToObj(__ -> namesList.get(r.nextInt(namesList.size()))).toList();

        var finalCounts = Flux.fromIterable(names)
                .buffer(Runtime.getRuntime().availableProcessors())
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .flatMap(ReactiveNames::processBatch)
                .sequential()
                .reduce(new HashMap<>(), ReactiveNames::mergeIntermediateCount)
                .flatMapIterable(HashMap::entrySet);

        return MathFlux.max(finalCounts, Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    public static Flux<String> getNamesList() {
        return Flux.fromIterable(namesList)
                //.delayElements(Duration.ofMillis(500L))
                .log("getNamesList", Level.INFO, SignalType.ON_NEXT);
    }

    private static HashMap<String, Long> mergeIntermediateCount(HashMap<String, Long> totalCount, Map<String, Long> intermediateResult) {
        intermediateResult.forEach((name, intermediateCount) -> totalCount.merge(name, intermediateCount, Long::sum));
        return totalCount;
    }

    private static Mono<Map<String, Long>> processBatch(List<String> batch) {
        return Flux.fromIterable(batch)
                .groupBy(Function.identity())
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(group -> group.count().map(count -> Tuples.of(group.key(), count)))
                .sequential()
                .collectMap(Tuple2::getT1, Tuple2::getT2);
    }
}
