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
        EditText edit_does = findViewById(R.id.edit_does);
        EditText manufacturing_company = findViewById(R.id.manufacturing_company);
        EditText imported_company = findViewById(R.id.imported_company);
        EditText edit_product_version = findViewById(R.id.edit_product_version);
        EditText edit_register_no = findViewById(R.id.edit_register_no);
        EditText edite_doesPrice = findViewById(R.id.edite_doesPrice);


        String id = "1";
        String vaccine_str = txt_vcName.getText().toString();
        String does_amout = edit_does.getText().toString();
        String manufacturing_company_str = manufacturing_company.getText().toString();
        String imported_company_str = imported_company.getText().toString();
        String product_version_str =  edit_product_version.getText().toString();
        String register_no_str  = edit_register_no.getText().toString();
        String doesPrice_str  = edite_doesPrice.getText().toString();


        this.IsaddVaccine(
                id,
                vaccine_str,
                date_result1,
                date_result2,
                date_result3,
                does_amout,
                manufacturing_company_str,
                imported_company_str,
                product_version_str,
                register_no_str,
                doesPrice_str
                );
    }

    private void IsaddVaccine(String id, String vaccine_str, String date_result1, String date_result2, String date_result3, String does_amout, String manufacturing_company_str, String imported_company_str, String product_version_str, String register_no_str, String doesPrice_str) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_vaccine = database.getReference("Login/admin1234/Vaccine/"+vaccine_str);

        Vaccine v = new Vaccine(
                id,
                vaccine_str,
                date_result1,
                date_result2,
                date_result3,
                does_amout,
                manufacturing_company_str,
                imported_company_str,
                product_version_str,
                register_no_str,
                doesPrice_str
        );


        myRef_vaccine.child(id).setValue(v);

        String Message = "บันทึกข้อมูลสำเร็จ";
        Toast.makeText(add_vaccine_page.this, Message, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(add_vaccine_page.this, admin_manu_page.class);
        intent.putExtra("Username", Username);
        intent.putExtra("VaccineName", vaccine_str);
        startActivity(intent);
        startActivity(intent);

    }






}