public class TicketSeller {
    private TicketOffice ticketOffice;

    public void setTicketOffice(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    Reservation reserve(Customer customer, Theater theater, Movie movie, Screening screening, int count) {
        Reservation reservation = Reservation.NONE;
//        Money price = movie.calculateFee(screening, count);
        Money price = ticketOffice.calculateFee(movie, screening, count);
        if (customer.hasAmount(price)) {
            reservation = ticketOffice.reserve(theater, movie, screening, count);
            if (reservation != Reservation.NONE) customer.minusAmount(price);
        }
        return reservation;
    }
}