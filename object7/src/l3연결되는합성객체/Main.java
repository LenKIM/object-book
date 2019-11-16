package l3연결되는합성객체;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        Plan plan = new Plan();
        plan.setCalculator(
                new PricePerTime(
                        new AmountDiscount(
                                new Tax(null, (long) 0.1),
                                Money.of((double) 10000)),
                        Money.of((double) 18),
                        Duration.ofSeconds(60)
                )
        );
    }
}
