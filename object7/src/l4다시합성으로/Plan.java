package l4다시합성으로;

import java.util.HashSet;
import java.util.Set;

public class Plan {
    private Calculator calc;
    private Set<Call> calls = new HashSet<>();

    public final void addCall(Call call) {
        calls.add(call);
    }

    public final void setCalculators(Calc... calcs) {
        boolean isFirst = true;
        for (Calc calc : calcs) {
            if (isFirst) {
                isFirst = false;
                this.calc = new Calculator(calcs[0]);
            } else {
                this.calc.setNext(calc);
            }
        }
    }

    public final Money calculateFee() {
        return calc.calcCallFee(calls, Money.ZERO);
    }
}