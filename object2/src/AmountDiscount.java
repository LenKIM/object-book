abstract public class AmountDiscount implements DiscountPolicy.AMOUNT, DiscountCondition {

    private final Money amount;

    public AmountDiscount(Money amount) {
        this.amount = amount;
    }

    @Override
    public Money calculateFee(Money fee) {
        return fee.minus(amount);
    }
}
