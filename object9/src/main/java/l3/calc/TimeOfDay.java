package l3.calc;

import l3.Calc;
import l3.Call;
import l3.Money;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// New Code

public class TimeOfDay extends Calc {
    private final Money basePrice;
    private final Duration baseDuration;
    private final List<LocalTime> starts = new ArrayList<>();
    private final List<LocalTime> ends = new ArrayList<>();
    private final List<Duration> durations = new ArrayList<>();
    private final List<Money> prices = new ArrayList<>();

    public TimeOfDay(Money basePrice, Duration baseDuration) {
        this.basePrice = basePrice;
        this.baseDuration = baseDuration;
    }

    @Override
    protected Money calculate(Set<Call> calls, Money result) {
        Money sum = Money.ZERO;
        for(Call call: calls) {
            for (DateTimeInterval interval: call.splitByDay()) {
                //날짜별로 시간이 나오면 계산해서 구현.
            }
        }
        return result.plus(sum);
    }
}
