abstract public class PercentDiscount implements DiscountPolicy.PERCENT, DiscountCondition {

    private final double percent;

    public PercentDiscount(double percent) {
        this.percent = percent;
    }

    @Override
    public Money calculateFee(Money fee) {
        return fee.minus(fee.multi(percent));
    }
}
