package th.ac.mju.itsci.reservevaccine_project;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class edit_vaccine_page extends AppCompatActivity {

      String Username;

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

    String date_result1 = "";
    String date_result2 = "";
    String date_result3 = "";

    public static final Pattern nameVaccine_REGEXP = Pattern.compile("[0-9|A-Za-z]{2,20}$");
    public static final Pattern manufacturing_company_REGEXP = Pattern.compile("[\\s,|ก-์|A-Za-z]{2,100}$");
    public static final Pattern product_version_REGEXP = Pattern.compile("[\\s/().-|A-Za-z]{2,10}$");


    private String vaccineName_to_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_vaccine_page);
        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");
        vaccineName = intent.getStringExtra("VaccineName");

        Log.e("vaccineName ",vaccineName);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_vaccine_by_id = database.getReference("Login/"+Username+"/Vaccine");
        Query query1 = myRef_vaccine_by_id.orderByKey().equalTo(vaccineName);

        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String vaccinename = ds.child("vaccineName").getValue().toString();
                    String date_in = ds.child("date_in").getValue().toString();
                    String exp_date = ds.child("exp_date").getValue().toString();
                    String mgf_date = ds.child("mgf_date").getValue().toString();
                    String manufacturing_company = ds.child("manufacturing_company").getValue().toString();
                    String does_amount = ds.child("does_amount").getValue().toString();
                    String imported_company = ds.child("imported_company").getValue().toString();
                    String product_version = ds.child("product_version").getValue().toString();
                    String register_no = ds.child("register_no").getValue().toString();
                    String doesPrice = ds.child("doesPrice").getValue().toString();

                    EditText Vname = findViewById(R.id.txtedit_Vname);
                    TextView date_input = findViewById(R.id.txtedit_Vdate_Input);
                    TextView date_mgf = findViewById(R.id.txtedit_Vdate_mgf);
                    TextView date_exp = findViewById(R.id.txtedit_Vdate_exp);
                    EditText edit_doesamount = findViewById(R.id.edit_doesamount);
                    EditText edit_manufacturing_company  = findViewById(R.id.edit_manufacturing_company);
                    EditText txtedit_Vcompany_input = findViewById(R.id.txtedit_Vcompany_input);
                    EditText txtedit_product_version = findViewById(R.id.txtedit_product_version);
                    EditText txtedit_register_no = findViewById(R.id.txtedit_register_no);
                    EditText txtedit_doesPrice = findViewById(R.id.txtedit_doesPrice);


                     Vname.setText(vaccinename);
                     date_input.setText(date_in);
                     date_mgf.setText(mgf_date);
                     date_exp.setText(exp_date);
                     edit_doesamount.setText(does_amount);
                     edit_manufacturing_company.setText(manufacturing_company);
                     txtedit_Vcompany_input.setText(imported_company);
                     txtedit_product_version.setText(product_version);
                     txtedit_register_no.setText(register_no);
                     txtedit_doesPrice.setText(doesPrice);

                    date_result1 = date_in;
                    date_result2 = exp_date;
                    date_result3 = mgf_date;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }//Oncreate


    public void  Edit_Vaccine(View view){

        EditText Vname = findViewById(R.id.txtedit_Vname);
        TextView date_input = findViewById(R.id.txtedit_Vdate_Input);
        TextView date_mgf = findViewById(R.id.txtedit_Vdate_mgf);
        TextView date_exp = findViewById(R.id.txtedit_Vdate_exp);
        EditText edit_doesamount = findViewById(R.id.edit_doesamount);
        EditText edit_manufacturing_company  = findViewById(R.id.edit_manufacturing_company);
        EditText txtedit_Vcompany_input = findViewById(R.id.txtedit_Vcompany_input);
        EditText txtedit_product_version = findViewById(R.id.txtedit_product_version);
        EditText txtedit_register_no = findViewById(R.id.txtedit_register_no);
        EditText txtedit_doesPrice = findViewById(R.id.txtedit_doesPrice);


        vaccineName_to_edit = Vname.getText().toString();
        date_in = date_input.getText().toString();
        mgf_date = date_mgf.getText().toString();
        exp_date = date_exp.getText().toString();
        does_amount = edit_doesamount.getText().toString();
        manufacturing_company = edit_manufacturing_company.getText().toString();
        imported_company = txtedit_Vcompany_input.getText().toString();
        product_version = txtedit_product_version.getText().toString();
        register_no = txtedit_register_no.getText().toString();
        doesPrice = txtedit_doesPrice.getText().toString();



        String id = "1";
//        Vaccine v = new Vaccine(id,vaccine_str,date_result1,date_result2,date_result3,company_str, manufacturing_company_str,
//                does_amout,manufacturing_company_str,imported_company_str,product_version_str,register_no_str,doesPrice_str);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Login/"+Username+"/Vaccine/"+vaccineName);

//        DatabaseReference v_del = myRef.child(vaccineName);
//        v_del.removeValue();
        AlertDialog.Builder builder = new AlertDialog.Builder(edit_vaccine_page.this);
        /*---------------------------ตรวจสคริป vaccine_str------------------------------------------------------------*/
        if(vaccineName_to_edit.isEmpty()){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("ชื่อวัคซีนห้ามเป็นค่าว่าง");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            Vname.setError("ชื่อวัคซีนห้ามเป็นค่าว่าง");
            Vname.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();

        }else if (vaccineName_to_edit.length() < 2 || vaccineName_to_edit.length() > 20){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("ชื่อวัคซีนมีความยาวตั้งแต่ 2 ตัวอักษรแต่ไม่เกิน 20 ตัวอักษร");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            Vname.setError("ชื่อวัคซีนมีความยาวตั้งแต่ 2 ตัวอักษรแต่ไม่เกิน 20 ตัวอักษร");
            AlertDialog alert = builder.create();
            alert.show();
            Vname.requestFocus();
            Vname.setText("");

        } else if (!nameVaccine_REGEXP.matcher(vaccineName_to_edit).matches()){
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
            Vname.setError("ชื่อวัคซีนต้องเป็นภาษาอังกฤษ หรือ ตัวเลขเท่านั้น");
            Vname.requestFocus();
            Vname.setText("");

            /*---------------------------ตรวจสคริป date_result1------------------------------------------------------------*/
        }else if (date_in.equals("")){
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
        }else if(mgf_date.equals("")){
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
        }else if(exp_date.equals("")){
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

        }else if(manufacturing_company.isEmpty()){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("ชื่อบริษัทผู้ผลิตห้ามเป็นค่าว่าง");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            edit_manufacturing_company.setError("ชื่อบริษัทผู้ผลิตห้ามเป็นค่าว่าง");
            edit_manufacturing_company.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();

//        }else if(!manufacturing_company_REGEXP.matcher(manufacturing_company).matches()){
//            builder.setTitle("ข้อความเตือน");
//            builder.setMessage("ชื่อบริษัทผู้ผลิตต้องเป็นภาษาไทยและภาษาอังกฤษเท่านั้นและมีความยาวตั้งแต่ 2-100 ตัวอักษร");
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            edit_manufacturing_company.setError("ชื่อบริษัทผู้ผลิตต้องเป็นภาษาไทยและภาษาอังกฤษเท่านั้นและมีความยาวตั้งแต่ 2-100 ตัวอักษร");
//            edit_manufacturing_company.requestFocus();
//            AlertDialog alert = builder.create();
//            alert.show();

            /*---------------------------ตรวจสคริป imported_company_str------------------------------------------------------------*/
        }else if(imported_company.isEmpty()){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("ชื่อบริษัทผู้นำเข้าวัคซีนห้ามเป็นค่าว่าง");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            txtedit_Vcompany_input.setError("ชื่อบริษัทผู้นำเข้าวัคซีนห้ามเป็นค่าว่าง");
            txtedit_Vcompany_input.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();

            /*---------------------------ตรวจสคริป manufacturing_company_REGEXP------------------------------------------------------------*/
//        }else if(!manufacturing_company_REGEXP.matcher(imported_company).matches()){
//            builder.setTitle("ข้อความเตือน");
//            builder.setMessage("ชื่อบริษัทู้นำเข้าวัคซีนต้องเป็นภาษาไทยและภาษาอังกฤษเท่านั้นและมีความยาวตั้งแต่ 2-100 ตัวอักษร");
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            txtedit_Vcompany_input.setError("ชื่อบริษัทผู้นำเข้าวัคซีนต้องเป็นภาษาไทยและภาษาอังกฤษเท่านั้นและมีความยาวตั้งแต่ 2-100 ตัวอักษร");
//            txtedit_Vcompany_input.requestFocus();
//            AlertDialog alert = builder.create();
//            alert.show();

            /*---------------------------ตรวจสคริป product_version_str------------------------------------------------------------*/
        }else if(product_version.isEmpty()){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("คุณยังไม่ได้กรอกรุ่นการผลิต");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            txtedit_product_version.setError("คุณยังไม่ได้กรอกรุ่นการผลิต");
            txtedit_product_version.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();

//        }else if (!product_version_REGEXP.matcher(product_version).matches()){
//            builder.setTitle("ข้อความเตือน");
//            builder.setMessage("รุ่นการผลิตประกอบด้วยภาษาอังกฤษเท่านั้นและตัวเลขเท่านั้นและมีความยาวตั้งแต่ 2-5 ตัวอักษร");
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            txtedit_product_version.setError("มีความยาวตั้งแต่ 2-5 ตัวอักษร");
//            txtedit_product_version.requestFocus();
//            AlertDialog alert = builder.create();
//            alert.show();

        }else if(register_no.isEmpty()){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("เลขทะเบียนวัคซีนต้องไม่เป็นช่องว่าง");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            txtedit_register_no.setError("เลขทะเบียนวัคซีนต้องไม่เป็นช่องว่าง");
            txtedit_register_no.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();

        }else if(doesPrice.isEmpty()){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("ราคาโดสต้องไม่เป็นช่องว่าง");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            txtedit_doesPrice.setError("ราคาโดสต้องไม่เป็นช่องว่าง");
            txtedit_doesPrice.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();

        }else if (Integer.parseInt(doesPrice) <= 0){
            builder.setTitle("ข้อความเตือน");
            builder.setMessage("ราคาโดสต้องต้องเป็นตัวเลขตัวเลข 0-9 เท่านั้น และ มีความยาว 2-5 ตัวอักษร");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            txtedit_doesPrice.setError("ราคาโดสต้องต้องเป็นตัวเลขตัวเลข 0-9 เท่านั้น และ มีความยาว 2-5 ตัวอักษร");
            txtedit_doesPrice.requestFocus();
            AlertDialog alert = builder.create();
            alert.show();
        }
        else {

            DatabaseReference myRef_vaccineEdit_add = database.getReference("Login/"+Username+"/Vaccine/"+vaccineName);
            myRef_vaccineEdit_add.removeValue();

            DatabaseReference myRef_vaccineEdit_add_real = database.getReference("Login/"+Username+"/Vaccine/"+vaccineName_to_edit);
            myRef_vaccineEdit_add_real.child("vaccineName").setValue(vaccineName_to_edit);
            myRef_vaccineEdit_add_real.child("date_in").setValue(date_result1);
            myRef_vaccineEdit_add_real.child("mgf_date").setValue(date_result2);
            myRef_vaccineEdit_add_real.child("exp_date").setValue(date_result3);
            myRef_vaccineEdit_add_real.child("does_amount").setValue(does_amount);
            myRef_vaccineEdit_add_real.child("manufacturing_company").setValue(manufacturing_company);
            myRef_vaccineEdit_add_real.child("imported_company").setValue(imported_company);
            myRef_vaccineEdit_add_real.child("product_version").setValue(product_version);
            myRef_vaccineEdit_add_real.child("register_no").setValue(register_no);
            myRef_vaccineEdit_add_real.child("doesPrice").setValue(doesPrice);



            builder.setTitle("ข้อความเตือน");
            builder.setMessage("ระบบได้แก้ไขข้อมูลวัคซีนเรียบร้อยแล้ว");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(edit_vaccine_page.this, admin_manu_page.class);
                    intent.putExtra("Username", Username);
                    startActivity(intent);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }



    }

    public void  Cancal_Edit_Vaccine(View view){
        Intent intent = new Intent(edit_vaccine_page.this, list_vaccine_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);
    }


    public void onclickDate1(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(edit_vaccine_page.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {
                TextView txtdate = findViewById(R.id.txtedit_Vdate_Input);
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(edit_vaccine_page.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {
                TextView txtdate = findViewById(R.id.txtedit_Vdate_mgf);
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(edit_vaccine_page.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {
                TextView txtdate = findViewById(R.id.txtedit_Vdate_exp);
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

}