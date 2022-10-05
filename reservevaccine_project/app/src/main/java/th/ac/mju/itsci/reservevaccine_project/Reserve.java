package th.ac.mju.itsci.reservevaccine_project;

import java.io.Serializable;

public class Reserve implements Serializable {
    private String reserve_id;
    private String status;
    private String reserve_date;
    private String details;


    public Reserve(String reserve_id, String status, String reserve_date, String details) {
        this.reserve_id = reserve_id;
        this.status = status;
        this.reserve_date = reserve_date;
        this.details = details;
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
}
