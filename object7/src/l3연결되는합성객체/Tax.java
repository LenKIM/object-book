package l3연결되는합성객체;

import java.util.Set;

public class Tax implements Calculator {
    private final Calculator next;
    private final Long ratio;

    public Tax(Calculator next, Long ratio) {
        this.next = next;
        this.ratio = ratio;
    }

    @Override
    public Money calcCallFee(Set<Call> calls, Money result) {
        result = result.plus(result.times(ratio));
        return next == null ? result : next.calcCallFee(calls, result);
    }
}
