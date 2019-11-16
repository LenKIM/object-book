package Template_VS_Strategy;

import java.util.HashSet;
import java.util.Set;

/**
 * 전략 패턴 설명
 */
abstract class DiscountPolicyStrategy {
    private Set<DiscountCondition> conditions = new HashSet<>();
    private final Calculator calculator;

    public DiscountPolicyStrategy(Calculator calculator) {
        this.calculator = calculator;
    }

    public void addCondition(DiscountCondition condition) {
        conditions.add(condition);
    }

    public Money calculateFee(Screening screening, int count, Money fee) {
        for (DiscountCondition condition : conditions) {
            if (condition.isSatisfiedBy(screening, count)) return calculator.calculateFee(fee);
        }
        return fee;
    }
}
