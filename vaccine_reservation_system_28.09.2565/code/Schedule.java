package th.ac.mju.itsci.reservevaccine_project;

import java.io.Serializable;

public class Schedule implements Serializable {
    private String id;
    private String time;
    private String amount;
    private String status;
    private String queue_start;
    private String queue_end;

    public Schedule(String id, String time, String amount, String status, String queue_start, String queue_end) {
        this.id = id;
        this.time = time;
        this.amount = amount;
        this.status = status;
        this.queue_start = queue_start;
        this.queue_end = queue_end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQueue_start() {
        return queue_start;
    }

    public void setQueue_start(String queue_start) {
        this.queue_start = queue_start;
    }

    public String getQueue_end() {
        return queue_end;
    }

    public void setQueue_end(String queue_end) {
        this.queue_end = queue_end;
    }
}
