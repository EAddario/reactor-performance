package org.addario.blockingservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class Names {
    static Random r = new Random();
    static List<String> names = List.of("Ahmad", "Ankush", "Chiamaka", "Chris", "Daniel", "David", "Ed", "Faris",
            "Guilherme", "Ian", "Illia", "Jędrzej", "Jonathan", "Leo", "Mark", "Mic", "Nicola", "Pato", "Pedro", "Sudi");

    public static String getName(int count) {
        var namesList = IntStream.range(0, count).mapToObj(__ -> names.get(r.nextInt(names.size()))).toList();

        return findName(namesList);
    }

    public static List<String> getNamesList() {
        return names;
    }

    private static String findName(List<String> namesList) {
        Map<String, Long> counts = new HashMap<>();

        for (String name : namesList) {
                counts.compute(name, (__, c) -> c == null ? 1L : c + 1);
        }

        return counts.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }
}
