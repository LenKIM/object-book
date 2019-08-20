abstract public class SequenceDiscount implements DiscountCondition {

    private final int sequence;

    public SequenceDiscount(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean isSatisfiedBy(Screening screening, int audienceCount) {
        return screening.sequence == sequence;
    }
}
