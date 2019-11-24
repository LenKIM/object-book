package l3;

import java.util.HashSet;
import java.util.Set;

public class Plan {

    private Calculator calc = new Calculator();
    private Set<Call> calls = new HashSet<>();

    public final void addCall(Call call) {
        if (call == null) throw new IllegalArgumentException("call is null");
        calls.add(call);
    }

    public final void setCalculator(Calculator calc) {
        if (calc == null) throw new IllegalArgumentException("calc is null");
        this.calc = calc;
    }

    public final Money calculateFee() {
        return calls.size() == 0 ? Money.ZERO : calc.calcCallFee(calls, Money.ZERO);
    }
}
