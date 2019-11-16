package l3연결되는합성객체;

import java.time.Duration;
import java.util.Set;

public class PricePerTime implements Calculator {

    private final Calculator next;
    private final Money price;
    private final Duration second;

    public PricePerTime(Calculator next, Money price, Duration second) {
        this.next = next;
        this.price = price;
        this.second = second;
    }

    @Override
    public Money calcCallFee(Set<Call> calls, Money result) {
        for (Call call : calls) {
            result = result.plus(price.times((call.getDuration().getSeconds() / second.getSeconds())));
        }
        return next == null ? result : next.calcCallFee(calls, result);
    }
}
