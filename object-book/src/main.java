public class main {
    private final static Ticket ticket = new Ticket(null);
    public static void main(String[] args) {

        Ticket ticket1 = null;
        System.out.println(ticket == null);
        System.out.println("--------------------");
        System.out.println(ticket1 == null);
    }
    static class Ticket {
        String a;
        public Ticket(String a) {
            this.a = a;
        }
    }
}
