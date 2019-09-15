import java.util.HashSet;
import java.util.Set;

abstract class DiscountPolicy {

    private Set<DiscountCondition> conditions = new HashSet<>();

    public void addCondition(DiscountCondition condition) {
        conditions.add(condition);
    }

    public void copyCondition(DiscountPolicy policy) {
        policy.conditions.addAll(conditions);
    }

    public Money calculateFee(Screening screening, int count, Money fee) {
        for (DiscountCondition condition : conditions) {
            if (condition.isSatisfiedBy(screening, count)) return calculateFee(fee);
        }
        return fee;
    }

    protected abstract Money calculateFee(Money fee);
}
