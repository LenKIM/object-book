public class TickerSeller {

    private TicketOffice ticketOffice;

    public void setTicketOffice(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    public Ticket getTicket(Audience audience) {

        Ticket ticket = Ticket.EMTPY;
//        자기가 생각하는 한국말이 코드로 잘 적용되었는가가 어렵다.
        if (audience.getInvitation() != Invitation.EMPTY) {
            ticket = ticketOffice.getTicketWithNoFee();
            if (ticket != Ticket.EMTPY) audience.removeInvitation();

        } else if (audience.hasAmount(ticketOffice.getTicketPrice())) {
            ticket = ticketOffice.getTicketWithFee();
            if (ticket != Ticket.EMTPY) audience.minusAmount(ticketOffice.getTicketPrice());
        }
        return ticket;
    }
}
