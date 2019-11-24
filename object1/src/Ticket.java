public class Ticket {
    final static public Ticket EMTPY = new Ticket(null);
    final private Theater theater; //불변으로 잡는다.
    private boolean isEntered = false;

    public Ticket(Theater theater) {
        this.theater = theater;
    }

    public boolean isValid(Theater theater) {
        if (isEntered || theater != this.theater || this == EMTPY) { //객체를 식별자로 판단하면서 발생하는 긍정적 신호
            return false;
        } else {
            isEntered = true;
            return true;
        }
    }

    public Long getFee() {
        return theater.getFee();
    }
}
