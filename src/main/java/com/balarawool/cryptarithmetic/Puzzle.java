package com.balarawool.cryptarithmetic;

import com.balarawool.cryptarithmetic.PuzzleConstraint.EqualityConstraint;
import com.balarawool.cryptarithmetic.PuzzleConstraint.UniqueValuesConstraint;

import java.util.List;
import java.util.Map;

record Puzzle(List<Variable> variables, List<PuzzleConstraint> constraints) {

    void solve() {
        var uniqueValuesNeeded = constraints.stream().anyMatch(constraint -> constraint instanceof UniqueValuesConstraint);
        var generator = new Generator(variables, uniqueValuesNeeded);
        generator.forEachCombination(values -> {
            if (isSolution(values)) {
                System.out.println(values);
            }
        });
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
