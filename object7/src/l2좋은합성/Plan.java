package l2좋은합성;

import java.util.HashSet;
import java.util.Set;

abstract class Plan {
    private Set<Call> calls = new HashSet<>();

    public final void addCall(Call call) {
        calls.add(call);
    }

    public final Money calculateFee() {
        Money result = Money.ZERO;
        for (Call call : calls) result = result.plus(calcCallFee(call));
        return result;
    }

    abstract protected Money calcCallFee(Call call);
}
