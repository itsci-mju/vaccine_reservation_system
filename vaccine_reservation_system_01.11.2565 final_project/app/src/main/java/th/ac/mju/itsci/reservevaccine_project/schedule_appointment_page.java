package th.ac.mju.itsci.reservevaccine_project;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class schedule_appointment_page extends AppCompatActivity {
    String Username;
    String date_schedule1;
    String date_schedule2;
    int count_time_check;
    Button btn_add_schedule;
    List<String> add_schedule ;
    BottomNavigationView bottomNavigationView;

    private static String[] time = {"08.00-09.00","09.00-10.00","10.00-11.00","11.00-12.00","13.00-14.00","14.00-15.00"};
    private List<String> List_time1 = new ArrayList<>();
    private List<String> List_time2 = new ArrayList<>();
    private List<String> List_time3 = new ArrayList<>();
    private List<String> List_time4 = new ArrayList<>();
    private List<String> List_time5 = new ArrayList<>();

    int show_daialog = 5;
    int status_value_ch = 0;
    String month2;



    String M_now ;
    String M_next ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_appointment_page);

        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");


        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth=  c.get(Calendar.MONTH);
        int mDay= c.get(Calendar.DAY_OF_MONTH);
        String day =    checklength(String.valueOf(mDay));
        String months = checklength(String.valueOf(mMonth+1));

        TextView txtdate = findViewById(R.id.time_schedule);
        txtdate.setText(day+"-"+months+"-"+mYear);

         M_now =  checklength(String.valueOf(mMonth+1))+"-"+mYear;
         M_next = checklength(String.valueOf(mMonth+2))+"-"+mYear;

        month2 = day+"-"+checklength(String.valueOf(mMonth+2))+"-"+mYear;
        date_schedule1 = txtdate.getText().toString();
        date_schedule2 = month2;


        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.schedule_appointment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.Vaccine_list:
                        Intent intent = new Intent(schedule_appointment_page.this, list_vaccine_page.class);
                        intent.putExtra("Username", Username);
                        startActivity(intent);
                        return true;

                    case R.id.admin_menu_page:
                        Intent intent2 = new Intent(schedule_appointment_page.this, admin_manu_page.class);
                        intent2.putExtra("Username", Username);
                        startActivity(intent2);
                        return true;

                    case R.id.schedule_appointment:
                        return true;
                }

                return false;
            }
        });


    }

    public void add_schedule_time(View view) {
        CheckBox c1 = findViewById(R.id.t1);
        CheckBox c2 = findViewById(R.id.t2);
        CheckBox c3 = findViewById(R.id.t3);
        CheckBox c4 = findViewById(R.id.t4);
        CheckBox c5 = findViewById(R.id.t5);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");

//        EditText q_strat = findViewById(R.id.queue_start);
//        EditText q_end  = findViewById(R.id.queue_end);
//
//        String queue_start = q_strat.getText().toString();
//        String queue_end  = q_end.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(schedule_appointment_page.this);

        if(c1.isChecked()){

            TextView View_time1 = findViewById(R.id.time1);
            EditText EditText_amount1 = findViewById(R.id.edit_no_time1);
            String check_id = "1";
            String Text_time1 = View_time1.getText().toString();
            String Text_amount1 = EditText_amount1.getText().toString();
            List_time1.add(check_id);
            List_time1.add(Text_time1);
            List_time1.add(Text_amount1);

            if(Text_amount1.isEmpty()){
                List_time1.clear();
                EditText_amount1.setError("จำนวนคิวเวลา 08.00-09.00 ยังไม่ได้กรอก");
                EditText_amount1.requestFocus();
                status_value_ch = status_value_ch - 1 ;
            }else if(Integer.parseInt(Text_amount1) == 0){
                List_time1.clear();
                EditText_amount1.setError("จำนวนคิวเวลา 08.00-09.00 ห้ามเป็นค่าเป็น 0");
                EditText_amount1.requestFocus();
                status_value_ch = status_value_ch - 1;
//            }else if(queue_start.isEmpty()){
//                List_time1.clear();
//                q_strat.setError("คิวที่เริ่มห้ามเป็นค่าว่าง หรือ 0");
//                q_strat.requestFocus();
//                status_value_ch = status_value_ch - 1;
//            }else if(queue_end.isEmpty()){
//                List_time1.clear();
//                q_end.setError("คิวที่้สิ้นสุดห้ามเป็นค่าว่าง หรือ 0");
//                q_end.requestFocus();
//                status_value_ch = status_value_ch - 1 ;
            }
            else {
                DatabaseReference myRef_schedule = database.getReference("Login/admin1234/Schedule_time/"+M_now+"/"+date_schedule1+"/"+List_time1.get(0));
                Schedule S1 = new Schedule(List_time1.get(0),List_time1.get(1),List_time1.get(2),"ว่าง");
                myRef_schedule.setValue(S1);

                DatabaseReference myRef_schedule2 = database.getReference("Login/admin1234/Schedule_time/"+M_next+"/"+date_schedule2+"/"+List_time1.get(0));
                Schedule S2 = new Schedule(List_time1.get(0),List_time1.get(1),List_time1.get(2),"ว่าง");
                myRef_schedule2.setValue(S2);


                List_time1.clear();
                status_value_ch = status_value_ch + 1;
            }

        } else {
            show_daialog = show_daialog - 1;
        }


        if(c2.isChecked()){

            TextView View_time2 = findViewById(R.id.time2);
            EditText EditText_amount2 = findViewById(R.id.edit_no_time2);
            String check_id = "2";
            String Text_time2 = View_time2.getText().toString();
            String Text_amount2 = EditText_amount2.getText().toString();
            List_time2.add(check_id);
            List_time2.add(Text_time2);
            List_time2.add(Text_amount2);

        if(Text_amount2.isEmpty()){
            List_time2.clear();
            EditText_amount2.setError("จำนวนคิวเวลา 09.00-10.00 ยังไม่ได้กรอก หรือค่าเป็น 0");
            EditText_amount2.requestFocus();
            status_value_ch = status_value_ch - 2;
        }else if(Integer.parseInt(Text_amount2) == 0){
            List_time2.clear();
            EditText_amount2.setError("จำนวนคิวเวลา 09.00-10.00 ห้ามเป็นค่าเป็น 0");
            EditText_amount2.requestFocus();
            status_value_ch = status_value_ch - 2;
//        }else if(queue_start.isEmpty()){
//            List_time2.clear();
//            q_strat.setError("คิวที่เริ่มห้ามเป็นค่าว่าง หรือ 0");
//            q_strat.requestFocus();
//            status_value_ch = status_value_ch - 1;
//        }else if(queue_end.isEmpty()){
//            List_time2.clear();
//            q_end.setError("คิวที่้สิ้นสุดห้ามเป็นค่าว่าง หรือ 0");
//            q_end.requestFocus();
//            status_value_ch = status_value_ch - 1;
        }else {
                DatabaseReference myRef_schedule = database.getReference("Login/admin1234/Schedule_time/"+M_now+"/"+date_schedule1+"/"+List_time2.get(0));
                Schedule S = new Schedule(List_time2.get(0),List_time2.get(1),List_time2.get(2),"ว่าง");
                myRef_schedule.setValue(S);

                DatabaseReference myRef_schedule2 = database.getReference("Login/admin1234/Schedule_time/"+M_next+"/"+date_schedule2+"/"+List_time2.get(0));
                Schedule S2 = new Schedule(List_time2.get(0),List_time2.get(1),List_time2.get(2),"ว่าง");
                myRef_schedule2.setValue(S2);

                List_time2.clear();
                status_value_ch = status_value_ch + 1;

            }


        }else {
            show_daialog = show_daialog - 1;
        }

        if(c3.isChecked()){
            TextView View_time3 = findViewById(R.id.time3);
            EditText EditText_amount3 = findViewById(R.id.edit_no_time3);
            String check_id = "3";
            String Text_time3 = View_time3.getText().toString();
            String Text_amount3 = EditText_amount3.getText().toString();
            List_time3.add(check_id);
            List_time3.add(Text_time3);
            List_time3.add(Text_amount3);

            if(Text_amount3.isEmpty()){
                List_time3.clear();
                EditText_amount3.setError("จำนวนคิวเวลา 10.00-11.00 ยังไม่ได้กรอก หรือค่าเป็น 0");
                EditText_amount3.requestFocus();
                status_value_ch = status_value_ch - 3;
            }else if(Integer.parseInt(Text_amount3) == 0){
                List_time3.clear();
                EditText_amount3.setError("จำนวนคิวเวลา 10.00-11.00 ห้ามเป็นค่าเป็น 0");
                EditText_amount3.requestFocus();
                status_value_ch = status_value_ch - 3;
//            }else if(queue_start.isEmpty()){
//                List_time3.clear();
//                q_strat.setError("คิวที่เริ่มห้ามเป็นค่าว่าง หรือ 0");
//                q_strat.requestFocus();
//                status_value_ch = status_value_ch - 1;
//            }else if(queue_end.isEmpty()){
//                List_time3.clear();
//                q_end.setError("คิวที่้สิ้นสุดห้ามเป็นค่าว่าง หรือ 0");
//                q_end.requestFocus();
//                status_value_ch = status_value_ch - 1;
            }else {
                DatabaseReference myRef_schedule = database.getReference("Login/admin1234/Schedule_time/"+M_now+"/"+date_schedule1+"/"+List_time3.get(0));
                Schedule S = new Schedule(List_time3.get(0),List_time3.get(1),List_time3.get(2),"ว่าง");
                myRef_schedule.setValue(S);

                DatabaseReference myRef_schedule2 = database.getReference("Login/admin1234/Schedule_time/"+M_next+"/"+date_schedule2+"/"+List_time3.get(0));
                Schedule S2 = new Schedule(List_time3.get(0),List_time3.get(1),List_time3.get(2),"ว่าง");
                myRef_schedule2.setValue(S2);

                List_time3.clear();
                status_value_ch = status_value_ch + 1;
            }

        }else {
            show_daialog = show_daialog - 1;
        }

        if(c4.isChecked()){
            TextView View_time4 = findViewById(R.id.time4);
            EditText EditText_amount4 = findViewById(R.id.edit_no_time4);
            String check_id = "4";
            String Text_time4 = View_time4.getText().toString();
            String Text_amount4 = EditText_amount4.getText().toString();
            List_time4.add(check_id);
            List_time4.add(Text_time4);
            List_time4.add(Text_amount4);

            if(Text_amount4.isEmpty()){
                List_time3.clear();
                EditText_amount4.setError("จำนวนคิวเวลา 13.00-14.00 ยังไม่ได้กรอก หรือค่าเป็น 0");
                EditText_amount4.requestFocus();
                status_value_ch = status_value_ch - 4;
            }else if(Integer.parseInt(Text_amount4) == 0){
                List_time4.clear();
                EditText_amount4.setError("จำนวนคิวเวลา 13.00-14.00 ห้ามเป็นค่าเป็น 0");
                EditText_amount4.requestFocus();
                status_value_ch = status_value_ch - 4;
//            }else if(queue_start.isEmpty()){
//                List_time4.clear();
//                q_strat.setError("คิวที่เริ่มห้ามเป็นค่าว่าง หรือ 0");
//                q_strat.requestFocus();
//                status_value_ch = status_value_ch - 1;
//            }else if(queue_end.isEmpty()){
//                List_time4.clear();
//                q_end.setError("คิวที่้สิ้นสุดห้ามเป็นค่าว่าง หรือ 0");
//                q_end.requestFocus();
//                status_value_ch = status_value_ch - 1;
            }else {
                DatabaseReference myRef_schedule = database.getReference("Login/admin1234/Schedule_time/"+M_now+"/"+date_schedule1+"/"+List_time4.get(0));
                Schedule S = new Schedule(List_time4.get(0),List_time4.get(1),List_time4.get(2),"ว่าง");
                myRef_schedule.setValue(S);

                DatabaseReference myRef_schedule2 = database.getReference("Login/admin1234/Schedule_time/"+M_next+"/"+date_schedule2+"/"+List_time4.get(0));
                Schedule S2 = new Schedule(List_time4.get(0),List_time4.get(1),List_time4.get(2),"ว่าง");
                myRef_schedule2.setValue(S2);

                List_time4.clear();
                status_value_ch = status_value_ch + 1;

            }

        }else {
            show_daialog = show_daialog - 1;
        }

        if(c5.isChecked()){
            TextView View_time5 = findViewById(R.id.time5);
            EditText EditText_amount5 = findViewById(R.id.edit_no_time5);
            String check_id = "5";
            String Text_time5 = View_time5.getText().toString();
            String Text_amount5 = EditText_amount5.getText().toString();
            List_time5.add(check_id);
            List_time5.add(Text_time5);
            List_time5.add(Text_amount5);

            if(Text_amount5.isEmpty()){
                List_time5.clear();
                EditText_amount5.setError("จำนวนคิวเวลา 14.00-15.00 ยังไม่ได้กรอก หรือค่าเป็น 0");
                EditText_amount5.requestFocus();
                status_value_ch = status_value_ch - 5;
            }else if(Integer.parseInt(Text_amount5) == 0){
                List_time5.clear();
                EditText_amount5.setError("จำนวนคิวเวลา 10.00-11.00 ห้ามเป็นค่าเป็น 0");
                EditText_amount5.requestFocus();
                status_value_ch = status_value_ch - 5;
//            }else if(queue_start.isEmpty()){
//                List_time5.clear();
//                q_strat.setError("คิวที่เริ่มห้ามเป็นค่าว่าง หรือ 0");
//                q_strat.requestFocus();
//                status_value_ch = status_value_ch - 1;
//            }else if(queue_end.isEmpty()){
//                List_time5.clear();
//                q_end.setError("คิวที่้สิ้นสุดห้ามเป็นค่าว่าง หรือ 0");
//                q_end.requestFocus();
//                status_value_ch = status_value_ch - 1;
            }else {
                DatabaseReference myRef_schedule = database.getReference("Login/admin1234/Schedule_time/"+M_now+"/"+date_schedule1+"/"+List_time5.get(0));
                Schedule S = new Schedule(List_time5.get(0),List_time5.get(1),List_time5.get(2),"ว่าง");
                myRef_schedule.setValue(S);

                DatabaseReference myRef_schedule2 = database.getReference("Login/admin1234/Schedule_time/"+M_next+"/"+date_schedule2+"/"+List_time5.get(0));
                Schedule S2 = new Schedule(List_time5.get(0),List_time5.get(1),List_time5.get(2),"ว่าง");
                myRef_schedule2.setValue(S2);

                List_time5.clear();
                status_value_ch = status_value_ch + 1;

            }

        }else {
                show_daialog = show_daialog - 1;
        }

        Log.e("ค่า LIST 1 ", List_time1.toString());
        Log.e("ค่า LIST 2 ", List_time2.toString());
        Log.e("ค่า LIST 3 ", List_time3.toString());
        Log.e("ค่า LIST 4 ", List_time4.toString());
        Log.e("ค่า LIST 5 ", List_time5.toString());

        Log.e("show_daialog",String.valueOf(show_daialog));
        Log.e("status_value_ch",String.valueOf(status_value_ch));

        if(show_daialog >= 0 && status_value_ch <= 0 ){
            builder.setTitle("คำแจ้งเตือน");
            builder.setMessage("คุณต้องเลือกรอบการนัดหมายของวันที่  "+date_schedule1+" จำนวนคิวบางรอบยังไม่ได้กรอก");
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //DatabaseReference myRef_schedule = database.getReference("Login/admin1234/Schedule_time/"+date_schedule1);
                    DatabaseReference myRef_schedule1 = database.getReference("Login/admin1234/Schedule_time/"+M_now+"/"+date_schedule1);
                    DatabaseReference myRef_schedule2 = database.getReference("Login/admin1234/Schedule_time/"+M_next+"/"+date_schedule2);
                    myRef_schedule1.removeValue();
                    myRef_schedule2.removeValue();
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            show_daialog = 5;
            status_value_ch = 0;
        }else {
            builder.setTitle("คำแจ้งเตือน");
            builder.setMessage("ระบบเพิ่มวันแจ้งการนัดหมายวันที่ "+date_schedule1+" สำเร็จแล้ว");
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(schedule_appointment_page.this, schedule_appointment_page.class);
                    intent.putExtra("Username", Username);
                    startActivity(intent);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            show_daialog = 5;
            status_value_ch = 0;

        }
    }

    public void onclickDate_schedule(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(schedule_appointment_page.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {
                TextView txtdate = findViewById(R.id.time_schedule);
                String day =    checklength(String.valueOf(dayOfMount));
                String months = checklength(String.valueOf(month+1));
                txtdate.setText(day+"-"+months+"-"+year);



                M_now =  checklength(String.valueOf(mMonth+1))+"-"+mYear;
                M_next = checklength(String.valueOf(mMonth+2))+"-"+mYear;


                month2 = day+"-"+checklength(String.valueOf(mMonth+2))+"-"+year;
                date_schedule1 = txtdate.getText().toString();
                date_schedule2 = month2;
            }
        },mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.show();

    }

    public  String checklength(String s){
        if (s.length()<2){
            s = "0"+s;
        }
        return s;
    }
}