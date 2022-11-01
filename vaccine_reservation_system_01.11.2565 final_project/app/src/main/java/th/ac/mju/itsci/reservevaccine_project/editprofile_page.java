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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.regex.Pattern;

public class editprofile_page extends AppCompatActivity {
    String Username;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String Gender;
    String birthday_result = "";

    public static final Pattern FirstnameANDLastname_REGEXP = Pattern.compile("[ก-์|A-Za-z]{2,45}$");
    public static final Pattern Phone_REGEXP = Pattern.compile("^[0]{1}[8|9|6]{1}[0-9]{8,}");
    public static final Pattern Email_REGEXP = Pattern.compile("^[_a-zA-Z0-9-]+(.[_a-zA-Z0-9-]+)@[a-zA-Z0-9-]+(.[a-zA-Z0-9-]+)(.([a-zA-Z]){2,4})$");
    public static final Pattern Idcard_REGEXP = Pattern.compile("^[0-9]{13}");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile_page);
        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");
        radioGroup = findViewById(R.id.radioSex);






        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_editprofile = database.getReference("Login/"+Username+"/"+"Reserve");

        Query query1 = myRef_editprofile.orderByKey().equalTo("Member");
       query1.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot ds : snapshot.getChildren()) {
                   String firstname  = ds.child("firstname").getValue().toString();
                   String lastname = ds.child("lastname").getValue().toString();
                   String address = ds.child("address").getValue().toString();
                   String phone = ds.child("phone").getValue().toString();
                   String gender = ds.child("gender").getValue().toString();
                   String email = ds.child("email").getValue().toString();
                   String idcard = ds.child("idcard").getValue().toString();
                   String birthday = ds.child("birthday").getValue().toString();
                   //String nationality = ds.child("nationality").getValue().toString();

                    //05101999
                   String d = birthday.substring(0,2);
                   String m = birthday.substring(2,4);
                   String y = birthday.substring(4);

                   EditText input_name = findViewById(R.id.input_name);
                   EditText input_lastname = findViewById(R.id.input_lastname);
                   EditText input_address = findViewById(R.id.input_address);
                   EditText input_phone = findViewById(R.id.input_phone);
                   //EditText input_gender = findViewById(R.id.input_gender);
                   EditText input_email = findViewById(R.id.input_email);
                   TextView input_idcard = findViewById(R.id.input_idcard);
                   TextView input_brithday = findViewById(R.id.input_brithday);
                   EditText input_nationality = findViewById(R.id.input_nationality);

                   input_name.setText(firstname);
                   input_lastname.setText(lastname);
                   input_address.setText(address);
                   input_phone.setText(phone);
                   //input_gender.setText(gender);
                   input_email.setText(email);
                   input_idcard.setText(idcard);
                   input_brithday.setText(d+"-"+m+"-"+y);
                   birthday_result = d+m+y;

                   //input_nationality.setText(nationality);


                   if(gender.equals("ชาย")){
                       RadioButton male = findViewById(R.id.radio_Male);
                       male.setChecked(true);
                       Gender = "ชาย";
                   }else {
                       RadioButton female = findViewById(R.id.radio_Female);
                       female.setChecked(true);
                       Gender = "หญิง";
                   }


               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
    }

    public void  ClickTOEdit(View view){
        EditText input_name = findViewById(R.id.input_name);
        EditText input_lastname = findViewById(R.id.input_lastname);
        EditText input_address = findViewById(R.id.input_address);
        EditText input_phone = findViewById(R.id.input_phone);
        //EditText input_gender = findViewById(R.id.input_gender);
        EditText input_email = findViewById(R.id.input_email);
        TextView input_idcard = findViewById(R.id.input_idcard);
        TextView input_brithday = findViewById(R.id.input_brithday);
        EditText input_nationality = findViewById(R.id.input_nationality);

        String firstname = input_name.getText().toString();
        String lastname = input_lastname.getText().toString();
        String address  = input_address.getText().toString();
        String phone = input_phone.getText().toString();
        //String gender = input_gender.getText().toString();
        String email = input_email.getText().toString();
        String idcard = input_idcard.getText().toString();
        String nationality = input_nationality.getText().toString();

        String birthday = input_brithday.getText().toString();
        //05-10-1999
        String d = birthday.substring(0,2);
        String m = birthday.substring(3,5);
        String y = birthday.substring(6);

        birthday_result = d+m+y;


        AlertDialog.Builder builder = new AlertDialog.Builder(editprofile_page.this);
        //---------------------------ตรวจสคริป firstname------------------------------------------------------------///
        if (firstname.isEmpty()) {
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("ชื่อของคุณห้ามเว้นช่องว่างไว้");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
//            Snackbar snackbar = Snackbar.make(view, "ชื่อของคุณห้ามเว้นช่องว่างไว้", Snackbar.LENGTH_SHORT);
//            View snackbarView = snackbar.getView();
//            snackbarView.setBackgroundColor(getResources().getColor(R.color.black));
//            snackbar.show();
            input_name.setError("ชื่อของคุณห้ามเว้นช่องว่างไว้");
            input_name.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();


        } else if (firstname.length() > 32 || firstname.length() < 2) {

            builder.setTitle("ข้อความเตือน");
            builder.setMessage("ชื่อมีความยาวตั้งแต่ 2 ตัวอักษรแต่ไม่เกิน 32 ตัวอักษร");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            input_name.setError("ชื่อมีความยาวตั้งแต่ 2 ตัวอักษรแต่ไม่เกิน 32 ตัวอักษร");
            AlertDialog alert = builder.create();
            alert.show();
            input_name.requestFocus();
            input_name.setText("");

        } else if (!FirstnameANDLastname_REGEXP.matcher(firstname).matches()) {
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("ชื่อต้องเป็นภาษาไทยและภาษาอังกฤษเท่านั้น");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            input_name.setError("ชื่อต้องเป็นภาษาไทยและภาษาอังกฤษเท่านั้น");
            // input_name.requestFocus();
            input_name.setText("");

            //---------------------------ตรวจสคริป lastname------------------------------------------------------------///
        } else if(lastname.isEmpty()){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("สกุลของคุณห้ามเว้นช่องว่างไว้");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            input_lastname.setError("สกุลของคุณห้ามเว้นช่องว่างไว้");
            input_lastname.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();


        }else if(lastname.length() > 32 || lastname.length() < 2) {

            builder.setTitle("ข้อความเตือน");
            builder.setMessage("นามสกุลมีความยาวตั้งแต่ 2 ตัวอักษรแต่ไม่เกิน 32 ตัวอักษร");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            input_lastname.setError("นามสกุลมีความยาวตั้งแต่ 2 ตัวอักษรแต่ไม่เกิน 32 ตัวอักษร");
            AlertDialog alert = builder.create();
            alert.show();
            input_lastname.requestFocus();
            input_lastname.setText("");
        }else if(!FirstnameANDLastname_REGEXP.matcher(lastname).matches()) {
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("นามสกุลต้องเป็นภาษาไทยและภาษาอังกฤษเท่านั้น");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            input_lastname.setError("นามสกุลต้องเป็นภาษาไทยและภาษาอังกฤษเท่านั้น");
            input_lastname.setText("");
            //---------------------------ตรวจสคริป address------------------------------------------------------------///
        }else if(address.isEmpty()){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("ที่อยู่ของคุณห้ามเว้นช่องว่างไว้");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            input_address.setError("ที่อยู่ของคุณห้ามเว้นช่องว่างไว้");
            input_address.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();
        }else if(address.length() > 200 || address.length() < 2){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("ที่อยู่มีความยาวตั้งแต่ 2 ตัวอักษรแต่ไม่เกิน 200 ตัวอักษร");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            input_address.setError("ที่อยู่มีความยาวตั้งแต่ 2 ตัวอักษรแต่ไม่เกิน 200 ตัวอักษร");
            AlertDialog alert = builder.create();
            alert.show();
            input_address.requestFocus();
            //---------------------------ตรวจสคริป phone------------------------------------------------------------///
        }else if(phone.isEmpty()){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("เบอร์โทรของคุณห้ามเว้นช่องว่างไว้");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            input_phone.setError("เบอร์โทรของคุณห้ามเว้นช่องว่างไว้");
            input_phone.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();
        }else if(phone.length() != 10){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("กรุณากรอกเบอร์โทรให้ครบ 10 หลัก");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            input_phone.setError("กรุณากรอกเบอร์โทรให้ครบ 10 หลัก");
            AlertDialog alert = builder.create();
            alert.show();
            input_phone.requestFocus();
        }else if(!Phone_REGEXP.matcher(phone).matches()){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("ต้องขึ้นต้นด้วยเลข 0 เท่านั้น และไม่มีช่องว่างหรือตัวอักษรใดๆ แทรกระหว่างตัวเลข");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            input_phone.setError("ต้องขึ้นต้นด้วยเลข 0 เท่านั้น และไม่มีช่องว่างหรือตัวอักษรใดๆ แทรกระหว่างตัวเลข");

            //---------------------------ตรวจสคริป Gender------------------------------------------------------------///
        }else if (Gender == ""){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("กรุณาเลือกเพศอย่างน้อย 1 เพศ");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

            //---------------------------ตรวจสคริป email------------------------------------------------------------///
        }else if(email.isEmpty()){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("อีเมล์ของคุณห้ามเว้นช่องว่างไว้");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            input_email.setError("อีเมล์ของคุณห้ามเว้นช่องว่างไว้");
            input_email.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();
        }else if(!Email_REGEXP.matcher(email).matches()){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("กรุณากรอกตามรูปแบบที่ถูกต้องของอีเมลเท่านั้น");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            input_email.setError("กรุณากรอกตามรูปแบบที่ถูกต้องของอีเมลเท่านั้น");
            input_email.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();
            //---------------------------ตรวจสคริป idcard------------------------------------------------------------///
        }else if(idcard.isEmpty()){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("หมายเลขบัตรประชาชนห้ามเป็นค่าว่าง");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            input_idcard.setError("หมายเลขบัตรประชาชนห้ามเป็นค่าว่าง");
            input_idcard.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();
            input_idcard.setText("");
        }else if(idcard.length() != 13){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("กรุณากรอกหมายเลขบัตรประชาชนให้ครบ 13 หลัก");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            input_idcard.setError("หมายเลขบัตรประชาชนห้ามเป็นค่าว่าง");
            input_idcard.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();
            input_idcard.setText("");
        }else if(!Idcard_REGEXP.matcher(idcard).matches()){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("กรุณากรอกหมายเลขบัตรประชาชนเฉพาะตัวเลข");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            input_idcard.setError("กรุณากรอกหมายเลขบัตรประชาชนเฉพาะตัวเลข");
            input_idcard.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();
            input_idcard.setText("");
        }

        else {

            Member memberEdit = new Member(firstname,lastname,address,phone,Gender,email,idcard,birthday_result);


            FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference myRef_memberEdit = database.getReference("Login/"+Username+"/Reserve"+"/"+"Member");
            Query query1 =  myRef_memberEdit.orderByValue();
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        myRef_memberEdit.child("firstname").setValue(memberEdit.getFirstname());
                        myRef_memberEdit.child("lastname").setValue(memberEdit.getLastname());
                        myRef_memberEdit.child("address").setValue(memberEdit.getAddress());
                        myRef_memberEdit.child("phone").setValue(memberEdit.getPhone());
                        myRef_memberEdit.child("gender").setValue(memberEdit.getGender());
                        myRef_memberEdit.child("email").setValue(memberEdit.getEmail());
                        myRef_memberEdit.child("idcard").setValue(memberEdit.getIdcard());
                        myRef_memberEdit.child("birthday").setValue(memberEdit.getBirthday());
                       // myRef_memberEdit.child("nationality").setValue(memberEdit.getNationality());
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            DatabaseReference myRef_member_user = database.getReference("Login/"+Username);
            myRef_member_user.child("password").setValue(memberEdit.getBirthday());

            builder.setTitle("ข้อความเตือน");
            builder.setMessage("คุณแก้ไขข้อมูลเรียบร้อยแล้ว");
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(editprofile_page.this, home_page.class);
                    intent.putExtra("Username", Username);
                    startActivity(intent);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }


    }


    public  String checklength(String s){
        if (s.length()<2){
            s = "0"+s;
        }
        return s;
    }


    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Gender = radioButton.getText().toString();
//        Toast.makeText(this, "Selected Radio Button: " + Gender,
//                Toast.LENGTH_SHORT).show();
    }

    public void goHome(View view){
        Intent intent = new Intent(editprofile_page.this, home_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);
    }

    public void onclickDate(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(editprofile_page.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {
                TextView txtdate = findViewById(R.id.input_brithday);
                String day =    checklength(String.valueOf(dayOfMount));
                String months = checklength(String.valueOf(month+1));
                txtdate.setText(day+"-"+months+"-"+year);
                birthday_result = day+months+year;
            }
        },mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

}