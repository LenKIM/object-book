package UsedCreatePatternAndFactory;

/**
 * Factory
 */
public class AmountCalculatorFactory implements CalculatorFactory {

    private final Money money;
    private AmountCalculator cache;

    public AmountCalculatorFactory(Money money) {
        this.money = money;
    }

    @Override
    synchronized public Calculator getCalculator() {
        if (cache == null) cache = new AmountCalculator(money);
        return cache;
    }
}
