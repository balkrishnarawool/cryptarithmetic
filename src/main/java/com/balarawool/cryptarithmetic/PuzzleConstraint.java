package com.balarawool.cryptarithmetic;

import java.util.Map;

import static com.balarawool.cryptarithmetic.Expression.evaluate;

sealed interface PuzzleConstraint {
    record UniqueValuesConstraint() implements PuzzleConstraint { }
    record EqualityConstraint(Expression left, Expression right) implements PuzzleConstraint {
        boolean holdsTrueFor(Map<String, Integer> values) {
            return evaluate(left, values) == evaluate(right, values);
        }
    }
}
