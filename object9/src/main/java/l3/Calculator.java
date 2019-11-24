package l3;

import java.util.HashSet;
import java.util.Set;

public class Calculator {
    private Set<Calc> calcs = new HashSet<>();

    public final Calculator setNext(Calc next) {
        if (next == null) throw new IllegalArgumentException("next is null");
        calcs.add(next);
        return this;
    }

    final Money calcCallFee(Set<Call> calls, Money result) {
        if (calcs.size() > 0) throw new IllegalArgumentException("calc is empty");
        for (Calc calc : calcs) result = calc.calc(calls, result);
        if (result.isLessThanOrEqual(Money.ZERO)) {
            throw new RuntimeException("calculate error");
        }
        return result;
    }
}
