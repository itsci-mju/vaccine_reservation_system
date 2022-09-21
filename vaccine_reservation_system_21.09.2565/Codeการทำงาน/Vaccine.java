package th.ac.mju.itsci.reservevaccine_project;

public class Vaccine {
    private String id;
    private String vaccineName;
    private String date_in;
    private String mgf_date;
    private String exp_date;
    private String company;

    public Vaccine(String id, String vaccineName, String date_in, String mgf_date, String exp_date, String company) {
        this.id = id;
        this.vaccineName = vaccineName;
        this.date_in = date_in;
        this.mgf_date = mgf_date;
        this.exp_date = exp_date;
        this.company = company;

    }

    public Vaccine() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getDate_in() {
        return date_in;
    }

    public void setDate_in(String date_in) {
        this.date_in = date_in;
    }

    public String getMgf_date() {
        return mgf_date;
    }

    public void setMgf_date(String mgf_date) {
        this.mgf_date = mgf_date;
    }

    public String getExp_date() {
        return exp_date;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
