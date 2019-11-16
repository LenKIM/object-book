package l2좋은합성;

import java.time.Duration;

public class PricePerTime extends Plan {

    private final Money price;
    private final Duration second;

    public PricePerTime(Money price, Duration second) {
        this.price = price;
        this.second = second;
    }

    @Override
    protected Money calcCallFee(Call call) {
        return price.times((call.getDuration().getSeconds() / second.getSeconds()));
    }
}
