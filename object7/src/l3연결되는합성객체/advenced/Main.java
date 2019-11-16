package l3연결되는합성객체.advenced;

import java.time.Duration;

public class Main {

    public static void main(String[] args) {
        Plan plan = new Plan();
        plan.setCalculator(
                new PricePerTime(Money.of(18D), Duration.ofSeconds(60))
                        .setNext(new AmountDiscount(Money.of(10000D)))
                        .setNext(new Tax((long) 0.1))
        );
    }
}
