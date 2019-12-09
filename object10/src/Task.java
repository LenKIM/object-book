import java.time.LocalDateTime;

public class Task {

    private String title;
    private LocalDateTime date;
    private Boolean isComplete = false;

    Task(String title, LocalDateTime date) {
        this.title = title;
        this.date = date;
    }

    public void toggle() {
        isComplete = !isComplete;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Boolean getComplete() {
        return isComplete;
    }
}
