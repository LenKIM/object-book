package l3연결되는합성객체.advenced;

import java.util.Set;

public class AmountDiscount extends Calculator {
    private final Money amount;

    public AmountDiscount(Money amount) {
        this.amount = amount;
    }

    @Override
    public Money calc(Set<Call> calls, Money result) {
        return result.minus(amount);
    }
}
