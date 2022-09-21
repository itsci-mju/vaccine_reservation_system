package th.ac.mju.itsci.reservevaccine_project;

import java.io.Serializable;

public class Reserve_Deatails implements Serializable {
    private String reserve_deatails_id;
    private String vaccine_no;

    public Reserve_Deatails() {
        super();
    }

    public Reserve_Deatails(String reserve_deatails_id, String vaccine_no) {
        this.reserve_deatails_id = reserve_deatails_id;
        this.vaccine_no = vaccine_no;
    }

    public String getReserve_deatails_id() {
        return reserve_deatails_id;
    }

    public void setReserve_deatails_id(String reserve_deatails_id) {
        this.reserve_deatails_id = reserve_deatails_id;
    }

    public String getVaccine_no() {
        return vaccine_no;
    }

    public void setVaccine_no(String vaccine_no) {
        this.vaccine_no = vaccine_no;
    }
}
