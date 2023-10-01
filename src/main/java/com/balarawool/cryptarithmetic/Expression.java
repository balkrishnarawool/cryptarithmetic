package com.balarawool.cryptarithmetic;

import java.util.Map;

sealed interface Expression {

    record ConstExpr(int value) implements Expression { }
    record VarExpr(String name) implements Expression { }
    record AdditionExpr(Expression left, Expression right) implements Expression { }
    record MultiplicationExpr(Expression left, Expression right) implements Expression { }

    static int evaluate(Expression expr, Map<String, Integer> values) {
        return switch (expr) {
            case ConstExpr(var value) -> value;
            case VarExpr(var name) -> values.get(name);
            case AdditionExpr(var left, var right) -> evaluate(left, values) + evaluate(right, values);
            case MultiplicationExpr(var left, var right) -> evaluate(left, values) * evaluate(right, values);
        };
    }

    static Expression constant(int value) {
        return new ConstExpr(value);
    }
    static Expression varExpr(String name) {
        return new VarExpr(name);
    }
    static Expression add(Expression left, Expression right) {
        return new AdditionExpr(left, right);
    }
    static Expression multiply(Expression left, Expression right) {
        return new MultiplicationExpr(left, right);
    }
}
