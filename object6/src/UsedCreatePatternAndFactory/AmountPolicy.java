package UsedCreatePatternAndFactory;

public class AmountPolicy extends DiscountPolicy {
    private final Money amount;

    public AmountPolicy(Money amount) {
        super(supplier);
        this.amount = amount;
    }

    @Override
    public Money calculateFee(Money fee) {
        return fee.minus(amount);
    }
}
