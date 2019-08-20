import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Theater {
    final private List<TicketOffice> ticketOffices = new ArrayList<>();
    final private Long fee;

    public Theater(Long fee) {
        this.fee = fee;
    }

    public Long getFee() {
        return fee;
    }

    public void setTicketOffices(TicketOffice ... ticketOffices){
        this.ticketOffices.addAll(Arrays.asList(ticketOffices));
    }

    public void setTicket(TicketOffice ticketOffice, Long num){
        if(!ticketOffices.contains(ticketOffice)) return;
        while (num -- > 0){
            ticketOffice.addTicket(new Ticket(this));
        }
    }

    public void setInvitation(Audience audience){
        audience.setInvitation(new Invitation(this));
    }

    public boolean enter(Audience audience){
        Ticket ticket = audience.getTicket();
        return ticket.isValid(this);
    }

}
