package l3연결되는합성객체;

public class Money {

    public static final Money ZERO = Money.of((double) 0);

    public static Money of(Double amount) {
        return new Money(amount);
    }

    private final Double amount;

    Money(Double amount) {
        this.amount = amount;
    }

    public Money minus(Money amount) {
        return new Money(this.amount > amount.amount ? this.amount - amount.amount : 0.0);
    }

    public Money multi(Double times) {
        return new Money(this.amount * times);
    }

    public Money plus(Money amount) {
        return new Money(this.amount + amount.amount);
    }

    public boolean greaterThen(Money amount) {
        return this.amount >= amount.amount;
    }

    public Money times(Long l) {
        return new Money(this.amount * l);
    }
}
