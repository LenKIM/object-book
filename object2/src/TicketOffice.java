import java.util.HashMap;
import java.util.Map;

public class TicketOffice {
    private Money amount;
    private Map<Theater, Double> commissionRate = new HashMap<>();

    public TicketOffice(Money amount) {
        this.amount = amount;
    }

    boolean contract(Theater theater, Double rate) {
        if (commissionRate.containsKey(theater)) return false;
        commissionRate.put(theater, rate);
        return true;
    }

    boolean cancel(Theater theater) {
        if (!commissionRate.containsKey(theater)) return false;
        commissionRate.remove(theater);
        return true;
    }

    Reservation reserve(Theater theater, Movie movie, Screening screening, int count) {
        if (
                !commissionRate.containsKey(theater) ||
                        !theater.isValidScreening(movie, screening) ||
                        !screening.hasSeat(count)
        ) return Reservation.NONE;
        Reservation reservation = theater.reserve(movie, screening, count);
        if (reservation != Reservation.NONE) {
            Money sales = movie.calculateFee(screening, count);
            Money commission = sales.multi(commissionRate.get(theater));
            amount = amount.plus(commission);
            theater.plusAmount(sales.minus(commission));
        }
        return reservation;
    }
}