package th.ac.mju.itsci.reservevaccine_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

public class view_receipt extends AppCompatActivity {

    String Username;
     String reserve_Id;
     String name_lastname;
     String vaccine_Name;
     String vaccine_No;
     String totalprice;
     String payment_id;
     String payment_date;
     String payment_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_receipt);


         FirebaseApp.initializeApp(this);
         Intent intent = getIntent();
         Username = intent.getStringExtra("Username");
         reserve_Id = intent.getStringExtra("reserve_Id");
         name_lastname = intent.getStringExtra("name_lastname");
         vaccine_Name = intent.getStringExtra("vaccine_name");
         vaccine_No = intent.getStringExtra("vaccine_no");
         totalprice = intent.getStringExtra("totalprice");
         payment_date = intent.getStringExtra("payment_date");
         payment_status = intent.getStringExtra("payment_status");
         payment_id = intent.getStringExtra("payment_Id");

        TextView txt_queue = findViewById(R.id.txt_queue);
        TextView txt_Name = findViewById(R.id.txt_Name);
        TextView txt_vaccinename = findViewById(R.id.txt_vaccinename);
        TextView txt_VaccineNo = findViewById(R.id.txt_VaccineNo);
        TextView txt_totalPrice = findViewById(R.id.txt_totalPrice);
        TextView txt_pricedate = findViewById(R.id.txt_pricedate);
        TextView recipt_status = findViewById(R.id.recipt_status);

        txt_queue.setText(payment_id);
        txt_Name.setText(name_lastname);
        txt_vaccinename.setText(vaccine_Name);
        txt_VaccineNo.setText(vaccine_No+" "+"เข็ม");
        txt_totalPrice.setText(totalprice);
        txt_pricedate.setText(payment_date);
        recipt_status.setText(payment_status);

    }//oncreate


    public  void  goHome(View view){
        Intent intent = new Intent(view_receipt.this, home_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);
    }

}