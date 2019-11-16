package Template_VS_Strategy;

import java.util.HashSet;
import java.util.Set;

/**
 * 템플릿 메서드 패턴 설명
 */
abstract class DiscountPolicyTemplate {

    private Set<DiscountCondition> conditions = new HashSet<>();


    public void addCondition(DiscountCondition condition) {
        conditions.add(condition);
    }

    public Money calculateFee(Screening screening, int count, Money fee) {
        for (DiscountCondition condition : conditions) {
            if (condition.isSatisfiedBy(screening, count)) return calculateFee(fee);
        }
        return fee;
    }

    protected abstract Money calculateFee(Money fee);
}
