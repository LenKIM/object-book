package l3.calc;

import l3.Call;
import l3.Money;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
// 새로 만든 코드
public class DayPrice {

    private final Money price;
    private final Duration duration;
    //==평일 요금 / 주말 요금==//
    private final Set<DayOfWeek> dayOfWeeks = new HashSet<>();

    public DayPrice(Money price, Duration duration) {
        this.price = price;
        this.duration = duration;
    }

    public Money calculate(DateTimeInterval[] intervals) {
        Money sum = Money.ZERO;
        for (DateTimeInterval interval: intervals){
//
        }
        return sum;
    }
}
