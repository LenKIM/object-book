public class PercentPolicy extends DiscountPolicy {
    private final Double percent;

    public PercentPolicy(Double percent) {
        this.percent = percent;
    }

    @Override
    public final Money calculateFee(Money fee) {
        return fee.minus(fee.multi(percent));
    }
}
