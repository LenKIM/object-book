package l3연결되는합성객체;

import java.util.Set;

public interface Calculator {
    Money calcCallFee(Set<Call> calls, Money result);
}
