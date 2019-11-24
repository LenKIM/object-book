import l3.Calculator;
import l3.Call;
import l3.Money;
import l3.Plan;
import l3.calc.AmountDiscount;
import l3.calc.PricePerTime;

import java.time.Duration;

public class main {

    public static void main(String[] args) {
        Plan plan = new Plan();
        plan.setCalculator(
                new Calculator()
                        .setNext(new PricePerTime(Money.of(18), Duration.ofSeconds(60)))
                        .setNext(new AmountDiscount(Money.of(10000)))
                        .setNext(new Tex(0.1))
        );
        plan.addCall(new Call());
    }
}
