package UsedCreatePatternAndFactory;

interface DiscountCondition {
    boolean isSatisfiedBy(Screening screening, int audienceCount);

    Money calculateFee(Money fee);
}
