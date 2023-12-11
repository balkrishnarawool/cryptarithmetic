package com.balarawool.cryptarithmetic;

import com.balarawool.cryptarithmetic.PuzzleConstraint.EqualityConstraint;
import com.balarawool.cryptarithmetic.PuzzleConstraint.UniqueValuesConstraint;

import java.util.List;
import java.util.Map;

record Puzzle(List<Variable> variables, List<PuzzleConstraint> constraints) {

    public Map<String, Integer> solve() {
        var uniqueValuesNeeded = constraints.stream().anyMatch(constraint -> constraint instanceof UniqueValuesConstraint);
        var generator = new Generator(variables, uniqueValuesNeeded);
        for (var values: generator.combinations()) {
            if (isSolution(values)) {
                return values;
            }
        }
        throw new IllegalStateException("No solution found!");
    }

    private boolean isSolution(Map<String, Integer> values) {
        return constraints.stream().allMatch(constraint -> isSatisfied(constraint, values));
    }

    private boolean isSatisfied(PuzzleConstraint constraint, Map<String, Integer> values) {
        return switch (constraint) {
            case UniqueValuesConstraint _ -> true;
            case EqualityConstraint equalityConstraint -> equalityConstraint.holdsTrueFor(values);
        };
    }
}
