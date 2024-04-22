package org.addario.backendservice;

import lombok.extern.java.Log;

import java.util.*;
import java.util.stream.IntStream;

@Log
public class BlockingNames {
    private static final Random r = new Random();
    private static final List<String> namesList = Names.namesList;

    public static String getName(int count) {
        var names = IntStream.range(0, count).mapToObj(__ -> namesList.get(r.nextInt(namesList.size()))).toList();

        return findName(names);
    }

    public static List<String> getNamesList() {
        var newNamesList = new ArrayList<String>();

        for (String name : namesList) {
            newNamesList.add(name);
            log.info(name);

            //try {
            //    Thread.sleep(500L);
            //} catch (InterruptedException e) {
            //    Thread.currentThread().interrupt();
            //}
        }

        return newNamesList;
    }

    private static String findName(List<String> names) {
        Map<String, Long> counts = new HashMap<>();

        for (String name : names) {
            counts.compute(name, (__, c) -> c == null ? 1L : c + 1L);
        }

        return counts.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }
}
