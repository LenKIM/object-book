public class NosalePolicy extends DiscountPolicy {

    @Override
    public final Money calculateFee(Money fee) {
        return fee;
    }
}
