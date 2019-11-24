package l3.calc;

import l3.Calc;
import l3.Call;
import l3.Money;

import java.util.Set;

public class AmountDiscount extends Calc {
    private final Money amount;
    public AmountDiscount(Money amount){
        this.amount = amount;
    }
    @Override
    protected Money calculate(Set<Call> calls, Money result) {
        return result.minus(amount);
    }
}
