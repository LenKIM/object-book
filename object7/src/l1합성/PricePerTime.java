package l1합성;

import java.time.Duration;

public class PricePerTime implements Calculator {

    private final Money price;
    private final Duration second;

    public PricePerTime(Money price, Duration second) {
        this.price = price;
        this.second = second;
    }

    @Override
    public Money calcCallFee(Call call) {
        return price.times((call.getDuration().getSeconds() / second.getSeconds()));
    }
}
