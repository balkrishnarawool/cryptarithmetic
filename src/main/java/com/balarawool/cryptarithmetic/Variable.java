package com.balarawool.cryptarithmetic;

record Variable(String name, RangeConstraint range) {
    record RangeConstraint(int from, int to) { }
}
