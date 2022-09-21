package th.ac.mju.itsci.reservevaccine_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

public class notify_make_appointment_page extends AppCompatActivity {
    String Username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_make_appointment_page);
        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");
    }


    public void Open_make_appointment_vaccine_page(View view){
        Intent intent = new Intent(notify_make_appointment_page.this, Make_Appointment_Vaccine_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);
    }

}//class