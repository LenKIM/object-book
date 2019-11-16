package l3연결되는합성객체.advenced;

import java.util.Set;

public class Tax extends Calculator {
    private final Long ratio;

    public Tax(Long ratio) {
        this.ratio = ratio;
    }

    @Override
    protected Money calc(Set<Call> calls, Money result) {
        return result.plus(result.times(ratio));
    }
}
