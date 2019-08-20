public class Customer {
    Reservation reservation = Reservation.NONE;
    private Money amount;

    public Customer(Money amount) {
        this.amount = amount;
    }

    public void reserve(TicketSeller seller, Theater theater, Movie movie, Screening screening, int count) {
        reservation = seller.reserve(this, theater, movie, screening, count);
    }

    boolean hasAmount(Money amount) {
        return this.amount.greaterThen(amount);
    }

    void minusAmount(Money amount) {
        this.amount = this.amount.minus(amount);
    }

}
