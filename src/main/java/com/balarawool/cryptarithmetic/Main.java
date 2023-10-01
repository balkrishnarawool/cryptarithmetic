package com.balarawool.cryptarithmetic;

import com.balarawool.cryptarithmetic.PuzzleConstraint.EqualityConstraint;
import com.balarawool.cryptarithmetic.PuzzleConstraint.UniqueValuesConstraint;
import com.balarawool.cryptarithmetic.Variable.RangeConstraint;

import java.util.List;

import static com.balarawool.cryptarithmetic.Expression.add;
import static com.balarawool.cryptarithmetic.Expression.constant;
import static com.balarawool.cryptarithmetic.Expression.multiply;
import static com.balarawool.cryptarithmetic.Expression.varExpr;

public class Main {
    //SEND + MORE = MONEY
    //9567 + 1085 = 10652
    public static void main(String[] args) {
        var s = new Variable("S", new RangeConstraint(0, 9));
        var e = new Variable("E", new RangeConstraint(0, 9));
        var n = new Variable("N", new RangeConstraint(0, 9));
        var d = new Variable("D", new RangeConstraint(0, 9));
        var m = new Variable("M", new RangeConstraint(1, 9));
        var o = new Variable("O", new RangeConstraint(0, 9));
        var r = new Variable("R", new RangeConstraint(0, 9));
        var y = new Variable("Y", new RangeConstraint(0, 9));

        new Puzzle(List.of(s,e,n,d,m,o,r,y),
                List.of(
                        new UniqueValuesConstraint(),
                        new EqualityConstraint(add(createNumber(s,e,n,d),
                                                   createNumber(m,o,r,e)),
                                                 createNumber(m,o,n,e,y))
                ))
                .solve();
    }

    private static Expression createNumber(Variable... vars) {
        var expr = constant(0);
        for (var variables: vars) {
            expr = add(multiply(expr, constant(10)), varExpr(variables.name()));
        }
        return expr;
    }
}
