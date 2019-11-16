package UsedCreatePatternAndFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Lazy Pull
 */
abstract class DiscountPolicy {

    private final Set<DiscountCondition> conditions = new HashSet<>();
    private final CalculatorFactory supplier;

    protected DiscountPolicy(CalculatorFactory supplier) {
        this.supplier = supplier;
    }

    public void addCondition(DiscountCondition condition) {
        conditions.add(condition);
    }

    public Money calculateFee(Screening screening, int count, Money fee) {
        for (DiscountCondition condition : conditions) {
            if (condition.isSatisfiedBy(screening, count))
                return supplier.getCalculator().calculateFee(fee);
            //디미터법칙 위반
            //1. factory와 calculator를 알게
            //2. factory 만 알게
            // 둘 중 하나를 선택 해 한다. 만약 2번을 선택시
        }
        return fee;
    }

    protected abstract Money calculateFee(Money fee);
}
