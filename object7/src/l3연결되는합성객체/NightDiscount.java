package l3연결되는합성객체;

import java.time.Duration;
import java.util.Set;

public class NightDiscount implements Calculator {

    private final Calculator next;
    private final Money dayPrice;
    private final Money nightPrice;
    private final Duration second;

    public NightDiscount(Calculator next, Money dayPrice, Money nightPrice, Duration second) {
        this.next = next;
        this.dayPrice = dayPrice;
        this.nightPrice = nightPrice;
        this.second = second;
    }

    @Override
    public Money calcCallFee(Set<Call> calls, Money result) {
        for (Call call : calls) {
            Money price = call.getFrom().getHour() >= 22 ? nightPrice : dayPrice;
            result = result.plus(price.times((call.getDuration().getSeconds() / second.getSeconds())));
        }
        return next == null ? result : next.calcCallFee(calls, result);
    }
}
