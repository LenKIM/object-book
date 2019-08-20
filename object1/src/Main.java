public class Main {

    public static void main(String[] args) {

        Theater theater = new Theater(100L);
        Audience audience1 = new Audience(0L);
        Audience audience2 = new Audience(50L);
        TicketOffice ticketOffice = new TicketOffice(0L);
        TickerSeller seller = new TickerSeller();

        theater.setTicketOffices(ticketOffice);
        theater.setTicket(ticketOffice, 10L);
        theater.setInvitation(audience1);

        seller.setTicketOffice(ticketOffice);

        audience1.buyTicket(seller);
        audience2.buyTicket(seller);

        boolean isOk = theater.enter(audience1);
        boolean isOk2= theater.enter(audience2);

        System.out.println(isOk);
        System.out.println(isOk2);
    }
}
