package dbgsprw.springkafkapractice;

public class Task {
    public static final String REGISTERED = "REGISTERED";
    public static final String FAILURE = "FAILURE";
    public static final String PENDING = "PENDING";
    public static final String RUNNING = "RUNNING";
    public static final String SUCCESS = "SUCCESS";

    private final String id;
    private final String status;

    public Task(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
