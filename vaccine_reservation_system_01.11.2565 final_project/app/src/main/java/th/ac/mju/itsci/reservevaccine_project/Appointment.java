package th.ac.mju.itsci.reservevaccine_project;

import java.io.Serializable;

public class Appointment implements Serializable {
    private String apm_id;
    private String apm_date;
    private String apm_status;
    private String apm_time;

    public Appointment(String apm_id, String apm_date, String apm_status, String apm_time) {
        this.apm_id = apm_id;
        this.apm_date = apm_date;
        this.apm_status = apm_status;
        this.apm_time = apm_time;
    }

    public Appointment() {
        super();
    }

    public String getApm_id() {
        return apm_id;
    }

    public void setApm_id(String apm_id) {
        this.apm_id = apm_id;
    }

    public String getApm_date() {
        return apm_date;
    }

    public void setApm_date(String apm_date) {
        this.apm_date = apm_date;
    }

    public String getApm_status() {
        return apm_status;
    }

    public void setApm_status(String apm_status) {
        this.apm_status = apm_status;
    }

    public String getApm_time() {
        return apm_time;
    }

    public void setApm_time(String apm_time) {
        this.apm_time = apm_time;
    }
}
