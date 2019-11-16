package l4다시합성으로;

import java.util.Set;

public class AmountDiscount implements Calc {
    private final Money amount;

    public AmountDiscount(Money amount) {
        this.amount = amount;
    }

    @Override
    public Money calc(Set<Call> calls, Money result) {
        return result.minus(amount);
    }
}
