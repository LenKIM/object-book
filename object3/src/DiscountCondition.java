interface DiscountCondition {
    boolean isSatisfiedBy(Screening screening, int audienceCount);
}
