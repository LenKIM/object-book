package l3.calc;

import l3.Calc;
import l3.Call;
import l3.Money;

import java.time.Duration;
import java.util.Set;

public class PricePerTime extends Calc {
    private final Money price;
    private final Duration second;
    public PricePerTime(Money price, Duration second){
        if(price == null || price.isLessThanOrEqual(Money.ZERO)){
            throw new IllegalArgumentException("invalid price");
        }
        if(second == null || second.compareTo(Duration.ZERO) <= 0){
            throw new IllegalArgumentException("invalid second");
        }
        this.price = price;
        this.second = second;
    }
    @Override
    protected Money calculate(Set<Call> calls, Money result) {
        Money sum = Money.ZERO;
        for(Call call:calls){
            Money r = price.times((call.getDuration().getSeconds() / second.getSeconds()));
            if(r.isLessThanOrEqual(Money.ZERO)){
                throw new RuntimeException("calculate error");
            }
            sum = sum.plus(r);
        }
        return result.plus(sum);
    }
}
