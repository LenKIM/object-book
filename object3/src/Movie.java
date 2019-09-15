import java.time.Duration;

public class Movie {

    private final String title;
    private final Duration runningTime;
    private final Money fee;
    private final DiscountPolicy policy;

    public Movie(String title, Duration runningTime, Money fee, DiscountPolicy policy) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.policy = policy;
    }
    Money calculateFee(Screening screening, int count){
        return policy.calculateFee(screening, count, fee);
    }
}
