package th.ac.mju.itsci.reservevaccine_project;

import java.io.Serializable;

public class Schedule implements Serializable {
    private String id;
    private String time;
    private String amount;
    private String status;


    public Schedule(String id, String time, String amount, String status) {
        this.id = id;
        this.time = time;
        this.amount = amount;
        this.status = status;
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


}
