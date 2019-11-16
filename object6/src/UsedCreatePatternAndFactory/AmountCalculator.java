package UsedCreatePatternAndFactory;

public class AmountCalculator implements Calculator {

    private final Money amount;

    public AmountCalculator(Money amount) {
        this.amount = amount;
    }

    @Override
    public Money calculateFee(Money fee) {
        return fee.minus(amount);
    }
}
