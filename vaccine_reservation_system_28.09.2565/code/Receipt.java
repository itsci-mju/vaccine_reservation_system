package th.ac.mju.itsci.reservevaccine_project;

import java.io.Serializable;

public class Receipt implements Serializable {
    private String receipt_id;
    private String receipt_date;
    private String receipt_status;
    private String receipt_img;
    private String total;

    public Receipt(String receipt_id, String receipt_date, String receipt_status, String receipt_img, String total) {
        this.receipt_id = receipt_id;
        this.receipt_date = receipt_date;
        this.receipt_status = receipt_status;
        this.receipt_img = receipt_img;
        this.total = total;
    }

    public Receipt() {
        super();
    }

    public String getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(String receipt_id) {
        this.receipt_id = receipt_id;
    }

    public String getReceipt_date() {
        return receipt_date;
    }

    public void setReceipt_date(String receipt_date) {
        this.receipt_date = receipt_date;
    }

    public String getReceipt_status() {
        return receipt_status;
    }

    public void setReceipt_status(String receipt_status) {
        this.receipt_status = receipt_status;
    }

    public String getReceipt_img() {
        return receipt_img;
    }

    public void setReceipt_img(String receipt_img) {
        this.receipt_img = receipt_img;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
