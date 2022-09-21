package th.ac.mju.itsci.reservevaccine_project;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class schedule_appointment_page extends AppCompatActivity {
    String Username;
    String date_schedule;
    int count_time_check;
    Button btn_add_schedule;
    List<String> add_schedule ;
    private static String[] time = {"08.00-09.00","09.00-10.00","10.00-11.00","11.00-12.00","13.00-14.00","14.00-15.00"};
    private List<String> List_time1 = new ArrayList<>();
    private List<String> List_time2 = new ArrayList<>();
    private List<String> List_time3 = new ArrayList<>();
    private List<String> List_time4 = new ArrayList<>();
    private List<String> List_time5 = new ArrayList<>();


    Long id_round ;



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


//        ImageView pic_add_time = findViewById(R.id.img_add_time);
//        pic_add_time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                count_time_check = count_time_check + 1;
//                LinearLayout time_show = findViewById(R.id.show_time);
//                time_show.removeAllViews();
//
//                ArrayList<String> list_amount = null;
//
//                for(int i =  0 ; i < count_time_check ; i++){
//
//
//                    View time_item_onclick = getLayoutInflater().inflate(R.layout.time_schedule_item,null);
//
//                    TextView number_count = time_item_onclick.findViewById(R.id.txt_number);
//                    EditText number_amount = time_item_onclick.findViewById(R.id.edit_number);
//                    Spinner spn_time = time_item_onclick.findViewById(R.id.spn_schedule);
//
//                    String text_num = String.valueOf(i+1);
//                    Log.e("เลขเอาไปเช็ค",text_num);
//                    number_count.setText(text_num);
//
//
//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(schedule_appointment_page.this, android.R.layout.select_dialog_item, time);
//                    spn_time.setAdapter(adapter);
//                    number_amount.setText("");
//
//                    time_show.addView(time_item_onclick);
//
//
//
//                    btn_add_schedule = findViewById(R.id.btn_add_schedule);
//                    btn_add_schedule.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
//                            DatabaseReference myRef_schedule = database.getReference("Login/admin1234/Schedule_time/"+date_schedule);
//
//
//
//                            EditText q_strat = findViewById(R.id.queue_start);
//                            EditText q_end  = findViewById(R.id.queue_end);
//
//                            String schedule_spin = spn_time.getSelectedItem().toString();
//                            String amount = number_amount.getText().toString();
//                            String queue_start = q_strat.getText().toString();
//                            String queue_end  = q_end.getText().toString();
//
//
////                            list_amount.add(amount);
////
////                            for(int i =  0 ; i <= count_time_check ; i++){
////                                String x = String.valueOf(i+1);
////                                String x1 = list_amount.toString();
////                                String x2 = schedule_spin;
////
////                                Log.e("x ",x);
////                                Log.e("x1 ",x1);
////                                Log.e("x2 ",x2);
////
////                            }
//
//                            Schedule S = new Schedule(schedule_spin,amount,"ว่าง",queue_start,queue_end);
//                            myRef_schedule.setValue(S);
//
//
//                           // DatabaseReference myRef_schedule_time = database.getReference("Login/admin1234/Schedule_time/"+date_schedule+"/"+xxx);
//
//
//                            String Message = "บันทึกข้อมูลสำเร็จ";
//                            Toast.makeText(schedule_appointment_page.this, Message, Toast.LENGTH_LONG).show();
//
//                            Intent intent = new Intent(schedule_appointment_page.this, admin_manu_page.class);
//                            intent.putExtra("Username", Username);
//                            startActivity(intent);
//                            startActivity(intent);
//
//
//                        }
//                    });
//                }
//
//            }
//        });

    }


    public void add_schedule_time(View view) {
        CheckBox c1 = findViewById(R.id.t1);
        CheckBox c2 = findViewById(R.id.t2);
        CheckBox c3 = findViewById(R.id.t3);
        CheckBox c4 = findViewById(R.id.t4);
        CheckBox c5 = findViewById(R.id.t5);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");

        EditText q_strat = findViewById(R.id.queue_start);
        EditText q_end  = findViewById(R.id.queue_end);

        String queue_start = q_strat.getText().toString();
        String queue_end  = q_end.getText().toString();

        if(c1.isChecked()){
            TextView View_time1 = findViewById(R.id.time1);
            EditText EditText_amount1 = findViewById(R.id.edit_no_time1);
            String check_id = "1";
            String Text_time1 = View_time1.getText().toString();
            String Text_amount1 = EditText_amount1.getText().toString();
            List_time1.add(check_id);
            List_time1.add(Text_time1);
            List_time1.add(Text_amount1);

            DatabaseReference myRef_schedule = database.getReference("Login/admin1234/Schedule_time/"+date_schedule+"/"+List_time1.get(0));
            Schedule S = new Schedule(List_time1.get(0),List_time1.get(1),List_time1.get(2),"ว่าง",queue_start,queue_end);
            myRef_schedule.setValue(S);
            List_time1.clear();
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

            DatabaseReference myRef_schedule = database.getReference("Login/admin1234/Schedule_time/"+date_schedule+"/"+List_time2.get(0));
            Schedule S = new Schedule(List_time2.get(0),List_time2.get(1),List_time2.get(2),"ว่าง",queue_start,queue_end);
            myRef_schedule.setValue(S);
            List_time2.clear();
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

            DatabaseReference myRef_schedule = database.getReference("Login/admin1234/Schedule_time/"+date_schedule+"/"+List_time3.get(0));
            Schedule S = new Schedule(List_time3.get(0),List_time3.get(1),List_time3.get(2),"ว่าง",queue_start,queue_end);
            myRef_schedule.setValue(S);
            List_time3.clear();
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

            DatabaseReference myRef_schedule = database.getReference("Login/admin1234/Schedule_time/"+date_schedule+"/"+List_time4.get(0));
            Schedule S = new Schedule(List_time4.get(0),List_time4.get(1),List_time4.get(2),"ว่าง",queue_start,queue_end);
            myRef_schedule.setValue(S);
            List_time4.clear();
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

            DatabaseReference myRef_schedule = database.getReference("Login/admin1234/Schedule_time/"+date_schedule+"/"+List_time5.get(0));
            Schedule S = new Schedule(List_time5.get(0),List_time5.get(1),List_time5.get(2),"ว่าง",queue_start,queue_end);
            myRef_schedule.setValue(S);
            List_time5.clear();
        }



        Log.e("ค่า LIST 1 ", List_time1.toString());
        Log.e("ค่า LIST 2 ", List_time2.toString());
        Log.e("ค่า LIST 3 ", List_time3.toString());
        Log.e("ค่า LIST 4 ", List_time4.toString());
        Log.e("ค่า LIST 5 ", List_time5.toString());








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
                date_schedule = txtdate.getText().toString();
            }
        },mYear, mMonth, mDay);
        //datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.show();


    }

    public  String checklength(String s){
        if (s.length()<2){
            s = "0"+s;
        }
        return s;
    }
}