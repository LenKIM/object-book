import java.time.LocalDateTime;

public class PeriodCondition implements DiscountCondition {
    private final LocalDateTime whenScreened;

    public PeriodCondition(LocalDateTime whenScreened) {
        this.whenScreened = whenScreened;
    }

    @Override
    public boolean isSatisfiedBy(Screening screening, int audienceCount) {
        return screening.whenScreened.equals(whenScreened);
    }
}