package l3.calc;

import l3.Money;

import java.time.Duration;

public class DurationPriceRule {
    private final Money price;
    private final Duration to;
    private final DurationPriceRule prev;

    DurationPriceRule(Money price, Duration to, DurationPriceRule prev) {
        this.price = price;
        this.to = to;
        this.prev = prev;
    }

    Duration getTo() {
        return to;
    }

    DurationPriceRule getPrev() {
        return prev;
    }

    public Money calculate(Duration call) {
        if (prev == null || call.compareTo(prev.to) <= 0) return Money.ZERO;
        return price.times((call.compareTo(to) > 0 ? to : call).minus(prev.to).getSeconds());
    }
}