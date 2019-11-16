package l4다시합성으로;

import java.time.Duration;
import java.util.Set;

public class PricePerTime implements Calc {
    private final Money price;
    private final Duration second;

    public PricePerTime(Money price, Duration second) {
        this.price = price;
        this.second = second;
    }

    @Override
    public Money calc(Set<Call> calls, Money result) {
        for (Call call : calls) {
            result = result.plus(price.times((call.getDuration().getSeconds() / second.getSeconds())));
        }
        return result;
    }
}