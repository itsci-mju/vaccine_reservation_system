package th.ac.mju.itsci.reservevaccine_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

public class admin_manu_page extends AppCompatActivity {
    String Username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manu_page);
        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");
    }



    public void Open_addVaccine_page(View view){
        Intent intent = new Intent(admin_manu_page.this, add_vaccine_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);
    }

    public void Open_ListVaccine_page(View view){
        Intent intent = new Intent(admin_manu_page.this, list_vaccine_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);
    }

    public void Open_ListMember_page(View view){
        Intent intent = new Intent(admin_manu_page.this, list_Member_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);
    }

    public void Open_Login_page(View view){
        Intent intent = new Intent(admin_manu_page.this, Login_page.class);
        startActivity(intent);
    }

    public void Open_Schedule_appointment_page(View view){
        Intent intent = new Intent(admin_manu_page.this, schedule_appointment_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);
    }

}