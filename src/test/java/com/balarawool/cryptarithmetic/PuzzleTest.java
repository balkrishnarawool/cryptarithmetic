package com.balarawool.cryptarithmetic;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.balarawool.cryptarithmetic.Expression.add;
import static com.balarawool.cryptarithmetic.Expression.constant;
import static com.balarawool.cryptarithmetic.Expression.evaluate;
import static com.balarawool.cryptarithmetic.Expression.multiply;
import static com.balarawool.cryptarithmetic.Expression.varExpr;
import static org.junit.jupiter.api.Assertions.*;

class PuzzleTest {

    @Test
    public void testSolve() {
        var s = new Variable("S", new Variable.RangeConstraint(0, 9));
        var e = new Variable("E", new Variable.RangeConstraint(0, 9));
        var n = new Variable("N", new Variable.RangeConstraint(0, 9));
        var d = new Variable("D", new Variable.RangeConstraint(0, 9));
        var m = new Variable("M", new Variable.RangeConstraint(1, 9));
        var o = new Variable("O", new Variable.RangeConstraint(0, 9));
        var r = new Variable("R", new Variable.RangeConstraint(0, 9));
        var y = new Variable("Y", new Variable.RangeConstraint(0, 9));

        var solution = new Puzzle(List.of(s,e,n,d,m,o,r,y),
                List.of(
                        new PuzzleConstraint.UniqueValuesConstraint(),
                        new PuzzleConstraint.EqualityConstraint(add(createNumber(s,e,n,d), createNumber(m,o,r,e)), createNumber(m,o,n,e,y))
                ))
                .solve();
        assertEquals(evaluate(add(createNumber(s,e,n,d), createNumber(m,o,r,e)), solution), evaluate(createNumber(m,o,n,e,y), solution));
        prettyPrint(solution);
    }

    private Expression createNumber(Variable... vars) {
        var expr = constant(0);
        for (var variables: vars) {
            expr = add(multiply(expr, constant(10)), varExpr(variables.name()));
        }
        return expr;
    }

    private void prettyPrint(Map<String, Integer> values) {
        var equation = """
                  SEND
                + MORE
                ------
                 MONEY
                """;
        var solution = STR."""
                  \{values.get("S")}\{values.get("E")}\{values.get("N")}\{values.get("D")}
                + \{values.get("M")}\{values.get("O")}\{values.get("R")}\{values.get("E")}
                ------
                 \{values.get("M")}\{values.get("O")}\{values.get("N")}\{values.get("E")}\{values.get("Y")}
                """;
        System.out.println(equation);
        System.out.println(solution);
    }

}