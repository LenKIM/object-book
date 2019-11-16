package l3연결되는합성객체;

import java.util.Set;

public class AmountDiscount implements Calculator {

    private final Calculator next;
    private final Money amount;



    public AmountDiscount(Calculator next, Money amount) {
        this.next = next;
        this.amount = amount;
    }

    @Override
    public Money calcCallFee(Set<Call> calls, Money result) {
        result = result.minus(amount);
        return next == null ? result : next.calcCallFee(calls, result);
    }
}
