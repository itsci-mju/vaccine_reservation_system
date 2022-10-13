package th.ac.mju.itsci.reservevaccine_project;

public class Vaccine {

    private String id;
    private String vaccineName ;
    private String date_in;
    private String mgf_date;
    private String exp_date;
    private String does_amount;
    private String manufacturing_company;
    private String imported_company;
    private String product_version;
    private String register_no;
    private String doesPrice;


    public Vaccine() {
        super();
    }

    public Vaccine(String id, String vaccineName, String date_in, String mgf_date, String exp_date, String does_amount, String manufacturing_company, String imported_company, String product_version, String register_no, String doesPrice) {
        this.id = id;
        this.vaccineName = vaccineName;
        this.date_in = date_in;
        this.mgf_date = mgf_date;
        this.exp_date = exp_date;
        this.does_amount = does_amount;
        this.manufacturing_company = manufacturing_company;
        this.imported_company = imported_company;
        this.product_version = product_version;
        this.register_no = register_no;
        this.doesPrice = doesPrice;
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

    public String getDoes_amount() {
        return does_amount;
    }

    public void setDoes_amount(String does_amount) {
        this.does_amount = does_amount;
    }

    public String getManufacturing_company() {
        return manufacturing_company;
    }

    public void setManufacturing_company(String manufacturing_company) {
        this.manufacturing_company = manufacturing_company;
    }

    public String getImported_company() {
        return imported_company;
    }

    public void setImported_company(String imported_company) {
        this.imported_company = imported_company;
    }

    public String getProduct_version() {
        return product_version;
    }

    public void setProduct_version(String product_version) {
        this.product_version = product_version;
    }

    public String getRegister_no() {
        return register_no;
    }

    public void setRegister_no(String register_no) {
        this.register_no = register_no;
    }

    public String getDoesPrice() {
        return doesPrice;
    }

    public void setDoesPrice(String doesPrice) {
        this.doesPrice = doesPrice;
    }
}
