package Template_VS_Strategy;

interface DiscountCondition {
    boolean isSatisfiedBy(Screening screening, int audienceCount);

    Money calculateFee(Money fee);
}
