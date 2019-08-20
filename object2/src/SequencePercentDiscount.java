public class SequencePercentDiscount extends PercentDiscount {
    private final int sequence;
    public SequencePercentDiscount(double percent, int sequence) {
        super(percent);
        this.sequence = sequence;
    }
    @Override
    public boolean isSatisfiedBy(Screening screening, int audienceCount) {
        return screening.sequence == sequence;
    }
}
