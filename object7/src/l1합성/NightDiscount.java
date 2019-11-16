package l1합성;

import java.time.Duration;

public class NightDiscount implements Calculator {

    private final Money dayPrice;
    private final Money nightPrice;
    private final Duration second;

    public NightDiscount(Money dayPrice, Money nightPrice, Duration second) {
        this.dayPrice = dayPrice;
        this.nightPrice = nightPrice;
        this.second = second;
    }

    @Override
    public Money calcCallFee(Call call) {
        Money price = call.getFrom().getHour() >= 22 ? nightPrice : dayPrice;
        return price.times((call.getDuration().getSeconds() / second.getSeconds()));
    }
}
