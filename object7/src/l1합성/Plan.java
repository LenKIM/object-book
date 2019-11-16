package l1합성;

import java.util.HashSet;
import java.util.Set;

public class Plan {

    private Calculator calc;
    private Set<Call> calls = new HashSet<>();

    public final void addCall(Call call) {
        calls.add(call);
    }

    public final Money calculateFee() {
        Money result = Money.ZERO;
        for (Call call : calls) result = result.plus(calc.calcCallFee(call));
        return result;
    }
}
