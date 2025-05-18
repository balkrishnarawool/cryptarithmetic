package com.balarawool.cryptarithmetic;

import com.balarawool.cryptarithmetic.PuzzleConstraint.EqualityConstraint;
import com.balarawool.cryptarithmetic.PuzzleConstraint.UniqueValuesConstraint;

import java.util.List;
import java.util.Map;

record Puzzle(List<Variable> variables, List<PuzzleConstraint> constraints) {

    public Map<String, Integer> solve() {
        var uniqueValues = constraints.stream().anyMatch(constraint -> constraint instanceof UniqueValuesConstraint);
        var generator = new Generator(variables, uniqueValues);
        for (var values: generator.combinations()) {
            if (isSolution(values, uniqueValues)) {
                return values;
            }
        }
        throw new IllegalStateException("No solution found!");
    }

    private boolean isSolution(Map<String, Integer> values, boolean uniqueValues) {
        return constraints.stream().allMatch(constraint -> isSatisfied(constraint, values, uniqueValues));
    }

    private boolean isSatisfied(PuzzleConstraint constraint, Map<String, Integer> values, boolean uniqueValues) {
        return switch (constraint) {
            case UniqueValuesConstraint _ -> uniqueValues;
            case EqualityConstraint equalityConstraint -> equalityConstraint.holdsTrueFor(values);
        };
    }
}
