package th.ac.mju.itsci.reservevaccine_project;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Pattern;

public class add_vaccine_page extends AppCompatActivity {
    String date_result1 = "";
    String date_result2 = "";
    String date_result3 = "";
    String Username;
    BottomNavigationView bottomNavigationView;

    public static final Pattern nameVaccine_REGEXP = Pattern.compile("[0-9|A-Za-z]{2,20}$");
    public static final Pattern manufacturing_company_REGEXP = Pattern.compile("[\\s,|ก-์|A-Za-z]{2,100}$");
    public static final Pattern product_version_REGEXP = Pattern.compile("[/.-|A-Za-z]{2,10}$");


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
                TextView txtdate = findViewById(R.id.date_in);
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
                TextView txtdate = findViewById(R.id.mgf_date);
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
                TextView txtdate = findViewById(R.id.exp_date);
                String day =    checklength(String.valueOf(dayOfMount));
                String months = checklength(String.valueOf(month+1));
                txtdate.setText(day+"-"+months+"-"+year);
                date_result3 = txtdate.getText().toString();
            }
        },mYear, mMonth, mDay);
        //datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
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
        Log.e("manufacturing_company_str ", manufacturing_company_str);

        AlertDialog.Builder builder = new AlertDialog.Builder(add_vaccine_page.this);
        /*---------------------------ตรวจสคริป vaccine_str------------------------------------------------------------*/
        if(vaccine_str.isEmpty()){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("ชื่อวัคซีนห้ามเป็นค่าว่าง");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            txt_vcName.setError("ชื่อวัคซีนห้ามเป็นค่าว่าง");
            txt_vcName.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();

        }else if (vaccine_str.length() < 2 || vaccine_str.length() > 20){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("ชื่อวัคซีนมีความยาวตั้งแต่ 2 ตัวอักษรแต่ไม่เกิน 20 ตัวอักษร");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            txt_vcName.setError("ชื่อวัคซีนมีความยาวตั้งแต่ 2 ตัวอักษรแต่ไม่เกิน 20 ตัวอักษร");
            AlertDialog alert = builder.create();
            alert.show();
            txt_vcName.requestFocus();
            txt_vcName.setText("");

        } else if (!nameVaccine_REGEXP.matcher(vaccine_str).matches()){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("ชื่อวัคซีนต้องเป็นภาษาอังกฤษ หรือ ตัวเลขเท่านั้น");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            txt_vcName.setError("ชื่อวัคซีนต้องเป็นภาษาอังกฤษ หรือ ตัวเลขเท่านั้น");
            txt_vcName.requestFocus();
            txt_vcName.setText("");

            /*---------------------------ตรวจสคริป date_result1------------------------------------------------------------*/
        }else if (date_result1.equals("")){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("คุณยังไม่ได้เลือกวันที่นำเข้า");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

            /*---------------------------ตรวจสคริป date_result2------------------------------------------------------------*/
        }else if(date_result2.equals("")){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("คุณยังไม่ได้เลือกวันที่ผลิต");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

            /*---------------------------ตรวจสคริป date_result3------------------------------------------------------------*/
        }else if(date_result3.equals("")){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("คุณยังไม่ได้เลือกวันหมดอายุ");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            /*---------------------------ตรวจสคริป manufacturing_company_str------------------------------------------------------------*/

        }else if(manufacturing_company_str.isEmpty()){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("ชื่อบริษัทผู้ผลิตห้ามเป็นค่าว่าง");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            manufacturing_company.setError("ชื่อบริษัทผู้ผลิตห้ามเป็นค่าว่าง");
            manufacturing_company.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();

//        }else if(!manufacturing_company_REGEXP.matcher(manufacturing_company_str).matches()){
//            builder.setTitle("ข้อความเตือน");
//            builder.setMessage("ชื่อบริษัทผู้ผลิตต้องเป็นภาษาไทยและภาษาอังกฤษเท่านั้นและมีความยาวตั้งแต่ 2-100 ตัวอักษร");
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            manufacturing_company.setError("ชื่อบริษัทผู้ผลิตต้องเป็นภาษาไทยและภาษาอังกฤษเท่านั้นและมีความยาวตั้งแต่ 2-100 ตัวอักษร");
//            manufacturing_company.requestFocus();
//            AlertDialog alert = builder.create();
//            alert.show();

            /*---------------------------ตรวจสคริป imported_company_str------------------------------------------------------------*/
        }else if(imported_company_str.isEmpty()){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("ชื่อบริษัทผู้นำเข้าวัคซีนห้ามเป็นค่าว่าง");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            imported_company.setError("ชื่อบริษัทผู้นำเข้าวัคซีนห้ามเป็นค่าว่าง");
            imported_company.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();

            /*---------------------------ตรวจสคริป manufacturing_company_REGEXP------------------------------------------------------------*/
//        }else if(!manufacturing_company_REGEXP.matcher(imported_company_str).matches()){
//            builder.setTitle("ข้อความเตือน");
//            builder.setMessage("ชื่อบริษัทู้นำเข้าวัคซีนต้องเป็นภาษาไทยและภาษาอังกฤษเท่านั้นและมีความยาวตั้งแต่ 2-100 ตัวอักษร");
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            imported_company.setError("ชื่อบริษัทผู้นำเข้าวัคซีนต้องเป็นภาษาไทยและภาษาอังกฤษเท่านั้นและมีความยาวตั้งแต่ 2-100 ตัวอักษร");
//            imported_company.requestFocus();
//            AlertDialog alert = builder.create();
//            alert.show();

            /*---------------------------ตรวจสคริป product_version_str------------------------------------------------------------*/
        }else if(product_version_str.isEmpty()){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("คุณยังไม่ได้กรอกรุ่นการผลิต");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            edit_product_version.setError("คุณยังไม่ได้กรอกรุ่นการผลิต");
            edit_product_version.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();

//        }else if (!product_version_REGEXP.matcher(product_version_str).matches()){
//            builder.setTitle("ข้อความเตือน");
//            builder.setMessage("รุ่นการผลิตประกอบด้วยภาษาอังกฤษเท่านั้นและตัวเลขเท่านั้นและมีความยาวตั้งแต่ 2-5 ตัวอักษร");
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            edit_product_version.setError("มีความยาวตั้งแต่ 2-5 ตัวอักษร");
//            edit_product_version.requestFocus();
//            AlertDialog alert = builder.create();
//            alert.show();

        }else if(register_no_str.isEmpty()){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("เลขทะเบียนวัคซีนต้องไม่เป็นช่องว่าง");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            edit_register_no.setError("เลขทะเบียนวัคซีนต้องไม่เป็นช่องว่าง");
            edit_register_no.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();

        }else if(doesPrice_str.isEmpty()){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("ราคาโดสต้องไม่เป็นช่องว่าง");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            edite_doesPrice.setError("ราคาโดสต้องไม่เป็นช่องว่าง");
            edite_doesPrice.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();

        }else if (Integer.parseInt(doesPrice_str) <= 0){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("ราคาโดสต้องต้องเป็นตัวเลขตัวเลข 0-9 เท่านั้น และ มีความยาว 2-5 ตัวอักษร");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            edite_doesPrice.setError("ราคาโดสต้องต้องเป็นตัวเลขตัวเลข 0-9 เท่านั้น และ มีความยาว 2-5 ตัวอักษร");
            edite_doesPrice.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();
        }
        else {
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


        myRef_vaccine.setValue(v);
        AlertDialog.Builder builder = new AlertDialog.Builder(add_vaccine_page.this);
        builder.setTitle("ข้อความเตือน");
        builder.setMessage("ระบบได้เพิ่มข้อมูลวัคซีนใหม่สำเร็จแล้ว");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(add_vaccine_page.this, admin_manu_page.class);
                intent.putExtra("Username", Username);
                intent.putExtra("VaccineName", vaccine_str);
                startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

//        String Message = "บันทึกข้อมูลสำเร็จ";
//        Toast.makeText(add_vaccine_page.this, Message, Toast.LENGTH_LONG).show();

    }






}