package l3;

import java.util.Set;

public abstract class Calc {
    Money calc(Set<Call> calls, Money result) {
        return calculate(calls, result);
    }

    abstract protected Money calculate(Set<Call> calls, Money result);
}
