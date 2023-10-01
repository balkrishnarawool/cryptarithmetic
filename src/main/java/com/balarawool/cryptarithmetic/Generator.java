package com.balarawool.cryptarithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

record Generator(List<Variable> variables, boolean uniqueValues) {

    void forEachCombination(Consumer<Map<String, Integer>> consumer) {
        var values = new int[variables.size()];
        dfs(0, values, consumer);
    }

    private void dfs(int i, int[] values, Consumer<Map<String, Integer>> consumer) {
        if (i < values.length) { //non-leaf nodes
            var nextValues = getNextValuesFor(i, values);
            for (var newValues : nextValues) {
                if (!uniqueValues || allValuesUnique(newValues, i + 1)) {
                    dfs(i + 1, newValues, consumer);
                }
            }
        }
        if (i == values.length) { // leaf nodes
            if (!uniqueValues || allValuesUnique(values, i)) {
                consumer.accept(createVarValuesMap(values));
            }
        }
    }

    private Map<String, Integer> createVarValuesMap(int[] values) {
        return Arrays.stream(intArray(values.length))
                .collect(Collectors.<Integer, String, Integer>toMap(j -> variables.get(j).name(), j -> values[j]));
    }

    private Integer[] intArray(int length) {
        return IntStream.range(0, length)
                .boxed()
                .toArray(Integer[]::new);
    }

    private ArrayList<int[]> getNextValuesFor(int i, int[] values) {
        var result = new ArrayList<int[]>();
        var range = variables.get(i).range();
        for (int j = range.from(); j <= range.to(); j++) {
            var arr = values.clone();
            arr[i] = j;
            result.add(arr);
        }
        return result;
    }

    private boolean allValuesUnique(int[] values, int length) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < length; i++) {
            set.add(values[i]);
        }
        return set.size() == length;
    }

}
