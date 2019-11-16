package l4다시합성으로;

import java.time.Duration;

public class Main {

    public static void main(String[] args) {
        Plan plan = new Plan();
        plan.setCalculators(
                new PricePerTime(Money.of((double) 18), Duration.ofSeconds(60)), new AmountDiscount(Money.of((double) 10000)),
                new Tax(0.1)
        );
    }
}
