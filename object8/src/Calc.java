import java.util.Set;

public interface Calc {
    Money calc(Set<Call> calls, Money result);
}