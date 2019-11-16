package l4다시합성으로;

import java.util.Set;

public interface Calc {
    Money calc(Set<Call> calls, Money result);
}