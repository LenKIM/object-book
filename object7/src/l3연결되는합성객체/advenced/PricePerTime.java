package l3연결되는합성객체.advenced;

import java.time.Duration;
import java.util.Set;

public class PricePerTime extends Calculator {
    private final Money price;
    private final Duration second;

    public PricePerTime(Money price, Duration second) {
        this.price = price;
        this.second = second;
    }

    @Override
    protected Money calc(Set<Call> calls, Money result) {
        for (Call call : calls) {
            result = result.plus(price.times((call.getDuration().getSeconds() / second.getSeconds())));
        }
        return result;
    }
}
