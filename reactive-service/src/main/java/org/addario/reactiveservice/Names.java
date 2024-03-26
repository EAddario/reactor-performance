package org.addario.reactiveservice;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.math.MathFlux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Names {
    static Random r = new Random();
    static List<String> names = List.of("Ahmad", "Ankush", "Chiamaka", "Chris", "Daniel", "David", "Ed", "Faris",
            "Guilherme", "Ian", "Illia", "Jędrzej", "Jonathan", "Leo", "Mark", "Mic", "Nicola", "Pato", "Pedro", "Sudi");

    public static Mono<String> getName(int count) {
        var namesList = IntStream.range(0, count).mapToObj(__ -> names.get(r.nextInt(names.size()))).toList();

        var finalCounts = Flux.fromIterable(namesList)
                .buffer(Runtime.getRuntime().availableProcessors())
                .parallel()
                .runOn(Schedulers.newParallel("Aggregator"))
                .sequential()
                .flatMap(Names::processBatch)
                .reduce(new HashMap<>(), Names::mergeIntermediateCount)
                .flatMapIterable(HashMap::entrySet);

        return MathFlux.max(finalCounts, Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    public static Flux<String> getNamesList() {
        return Flux.fromIterable(names);
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
