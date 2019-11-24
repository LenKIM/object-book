package l3.calc;

import l3.Calc;
import l3.Call;
import l3.Money;

import java.time.Duration;
import java.util.Set;

public class DurationPrice extends Calc {

    private DurationPriceRule rule = new DurationPriceRule(
            Money.ZERO,
            Duration.ZERO,
            null);

    public void addRule(Money price, Duration to) throws Exception {
        if (rule.getTo().compareTo(to) > 0) throw new Exception();
        if (price.isLessThan(Money.ZERO)) throw new Exception();
        rule = new DurationPriceRule(price, to, rule);
    }

    @Override
    protected Money calculate(Set<Call> calls, Money result) {
        Money sum = Money.ZERO;
        for (Call call : calls) {
            DurationPriceRule target = rule;
            do {
                sum = sum.plus(target.calculate(call.getDuration()));
                target = target.getPrev();
            } while (target != null);
        }
        return result.plus(sum);
    }
}