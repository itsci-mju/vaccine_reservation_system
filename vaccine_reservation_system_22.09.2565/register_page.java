package th.ac.mju.itsci.reservevaccine_project;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class register_page extends AppCompatActivity {
    String birthday_result = "";
    RadioGroup radioGroup;
    RadioButton radioButton;
    String Gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        FirebaseApp.initializeApp(this);

        radioGroup = findViewById(R.id.radioSex);


        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth=  c.get(Calendar.MONTH);
        int mDay= c.get(Calendar.DAY_OF_MONTH);
        String day =    checklength(String.valueOf(mDay));
        String months = checklength(String.valueOf(mMonth+1));

        EditText txtdate = findViewById(R.id.input_brithday);
        txtdate.setText(mYear+"-"+months+"-"+day);

    }

    public  String checklength(String s){
        if (s.length()<2){
            s = "0"+s;
        }
        return s;
    }

    public void onclickDate(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(register_page.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {
                EditText txtdate = findViewById(R.id.input_brithday);
                String day =    checklength(String.valueOf(dayOfMount));
                String months = checklength(String.valueOf(month+1));
                txtdate.setText(day+"-"+months+"-"+year);
                birthday_result = day+months+year;
            }
        },mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }
    public void ClickTORegister(View view){
        EditText input_name = findViewById(R.id.input_name);
        EditText input_lastname = findViewById(R.id.input_lastname);
        EditText input_address = findViewById(R.id.input_address);
        EditText input_phone = findViewById(R.id.input_phone);
       // EditText input_gender = findViewById(R.id.input_gender);
        EditText input_email = findViewById(R.id.input_email);
        EditText input_idcard = findViewById(R.id.input_idcard);
        EditText input_brithday = findViewById(R.id.input_brithday);
        EditText input_nationality = findViewById(R.id.input_nationality);


        String firstname = input_name.getText().toString();
        String lastname = input_lastname.getText().toString();
        String address = input_address.getText().toString();
        String phone = input_phone.getText().toString();
        //String gender = input_gender.getText().toString();
        String email = input_email.getText().toString();
        String idcard = input_idcard.getText().toString();
        String birthday = birthday_result;
        String nationality = input_nationality.getText().toString();


        AlertDialog.Builder builder = new AlertDialog.Builder(register_page.this);
        if(firstname.equals("")){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("กรุณากรอกชื่อ");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        if(lastname.equals("")){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("กรุณากรอกนามสกุล");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }



        this.IsaddRegister(firstname,lastname,address,phone,Gender,email,idcard,birthday,nationality);
    }

    private void IsaddRegister(String firstname, String lastname, String address, String phone, String gender, String email, String idcard, String birthday, String nationality) {
        String TypeUser = "member";
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_login = database.getReference("Login/"+idcard);
        myRef_login.child("username").setValue(idcard);
        myRef_login.child("password").setValue(birthday);
        myRef_login.child("type_user").setValue(TypeUser);

        DatabaseReference myRef_member = database.getReference("Login/"+idcard+"/"+"Reserve"+"/"+"Member");
        Member mb = new Member(firstname,lastname,address,phone,gender,email,idcard,birthday,nationality);
        myRef_member.setValue(mb);
    }



    public  void  goHome(View view){
        Intent intent = new Intent(register_page.this, Login_page.class);
        startActivity(intent);
    }


    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Gender = radioButton.getText().toString();
        Toast.makeText(this, "Selected Radio Button: " + Gender,
                Toast.LENGTH_SHORT).show();
    }

}// public class

