package th.ac.mju.itsci.reservevaccine_project;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class add_vaccine_page extends AppCompatActivity {
    String date_result1 = "";
    String date_result2 = "";
    String date_result3 = "";
    String Username;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_vaccine_page);
        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");


        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.bottom_navigator);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.Vaccine_list:
                        Intent intent = new Intent(add_vaccine_page.this, list_vaccine_page.class);
                        intent.putExtra("Username", Username);
                        startActivity(intent);
                        return true;

                    case R.id.admin_menu_page:
                        Intent intent1 = new Intent(add_vaccine_page.this, admin_manu_page.class);
                        intent1.putExtra("Username", Username);
                        startActivity(intent1);
                        return true;

                    case R.id.schedule_appointment:
                        Intent intent2 = new Intent(add_vaccine_page.this, schedule_appointment_page.class);
                        intent2.putExtra("Username", Username);
                        startActivity(intent2);
                        return true;
                }

                return false;
            }
        });

    }

    public void onclickDate1(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(add_vaccine_page.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {
                EditText txtdate = findViewById(R.id.date_in);
                String day =    checklength(String.valueOf(dayOfMount));
                String months = checklength(String.valueOf(month+1));
                txtdate.setText(day+"-"+months+"-"+year);
                date_result1 = txtdate.getText().toString();
            }
        },mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    public void onclickDate2(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(add_vaccine_page.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {
                EditText txtdate = findViewById(R.id.mgf_date);
                String day =    checklength(String.valueOf(dayOfMount));
                String months = checklength(String.valueOf(month+1));
                txtdate.setText(day+"-"+months+"-"+year);
                date_result2 = txtdate.getText().toString();
            }
        },mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    public void onclickDate3(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(add_vaccine_page.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {
                EditText txtdate = findViewById(R.id.exp_date);
                String day =    checklength(String.valueOf(dayOfMount));
                String months = checklength(String.valueOf(month+1));
                txtdate.setText(day+"-"+months+"-"+year);
                date_result3 = txtdate.getText().toString();
            }
        },mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }


    public  String checklength(String s){
        if (s.length()<2){
            s = "0"+s;
        }
        return s;
    }

    public void add_vaccine(View view){
        EditText txt_vcName = findViewById(R.id.txt_vcName);
        EditText company_edt = findViewById(R.id.company);
        String vcName = txt_vcName.getText().toString();
        String company = company_edt.getText().toString();
        String id = "1";
        this.IsaddVaccine(id,vcName,date_result1,date_result2,date_result3,company);
    }

    private void IsaddVaccine(String id, String txt_vcName, String date_result1, String date_result2, String date_result3, String company) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_vaccine = database.getReference("Login/admin1234/Vaccine/"+txt_vcName);
        Vaccine v = new Vaccine(id,txt_vcName,date_result1,date_result2,date_result3,company);
        myRef_vaccine.setValue(v);
        String Message = "บันทึกข้อมูลสำเร็จ";
        Toast.makeText(add_vaccine_page.this, Message, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(add_vaccine_page.this, admin_manu_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);
        startActivity(intent);
    }


}