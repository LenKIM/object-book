package Template_VS_Strategy;

public class AmountPolicyTemplate extends DiscountPolicyTemplate {
    private final Money amount;

    public AmountPolicyTemplate(Money amount) {
        this.amount = amount;
    }

    @Override
    public Money calculateFee(Money fee) {
        return fee.minus(amount);
    }
}
