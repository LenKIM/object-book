package l4다시합성으로;

import java.util.Set;

public class Tax implements Calc {
    private final double ratio;

    public Tax(double ratio) {
        this.ratio = ratio;
    }

    @Override
    public Money calc(Set<Call> calls, Money result) {
        return result.plus(result.times((long) ratio));
    }
}
