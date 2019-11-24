package l3.calc;

import l3.Calc;
import l3.Call;
import l3.Money;

import java.util.HashSet;
import java.util.Set;
// 새로 만든 코드
public class DayOfWeek extends Calc {

    private final Set<DayPrice> prices = new HashSet<>();

    @Override
    protected Money calculate(Set<Call> calls, Money result) {
        Money sum = Money.ZERO;
        for (Call call : calls) {
            DateTimeInterval[] intervals = call.splitByDay();
            for (DayPrice price : prices) {
                sum = sum.plus(price.calculate(intervals));
            }
        }
        return result.plus(sum);
    }
}
