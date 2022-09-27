package th.ac.mju.itsci.reservevaccine_project;

import java.io.Serializable;

public class Reserve implements Serializable {
    private String reserve_id;
    private String status;
    private String reserve_date;
    private String details;
    private String queue;


    public Reserve(String status, String reserve_date, String details, String queue) {
        this.status = status;
        this.reserve_date = reserve_date;
        this.details = details;
        this.queue = queue;
    }

    public Reserve() {
        super();
    }

    public String getReserve_id() {
        return reserve_id;
    }

    public void setReserve_id(String reserve_id) {
        this.reserve_id = reserve_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReserve_date() {
        return reserve_date;
    }

    public void setReserve_date(String reserve_date) {
        this.reserve_date = reserve_date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }
}
