package th.ac.mju.itsci.reservevaccine_project;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Make_Appointment_Vaccine_page extends AppCompatActivity {
    //Initialize variable
    CustomCalendar customCalendar;
    String Username;
    String Appointment_Check;
    String check_vaccineNo;
    List<Appointment> List_Appointment;
    String Date_Appointment;
    String Date_Query;
    String appointment_id;
    String Time_Appointment;
    String amount ;
    String time;
    int check_amount;
    private List<String> Date_color = new ArrayList<>();
    List<String> Date_list = new ArrayList<>();
    List<String> Date_Key = new ArrayList<>();
    private Dialog dialog;
    private Button ShowDialog;
    int dialog_date = 0;
    RadioGroup radioGroup ;
    RadioButton radio_time_select;


    String key;

    TextView txttime1 ;
    TextView txttime2 ;
    TextView txttime3 ;
    TextView txttime4 ;
    TextView txttime5 ;


    TextView txtamount1 ;
    TextView txtamount2 ;
    TextView txtamount3 ;
    TextView txtamount4 ;
    TextView txtamount5 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make__appointment__vaccine_page);
        FirebaseApp.initializeApp(this);

        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");

        //Assign variable
        customCalendar = findViewById(R.id.custom_calendar);

        //Initialize description hash map
        HashMap<Object, Property> descHashMap = new HashMap<>();
        //Initialize default property
        Property defaultProperty = new Property();
        //Initialize default resource
        defaultProperty.layoutResource = R.layout.default_view;
        //Initialize and assing varriable
        defaultProperty.dateTextViewResource = R.id.text_view;
        //Put object and property
        descHashMap.put("default",defaultProperty);

        //For current date
        Property currentProperty = new Property();
        currentProperty.layoutResource = R.layout.current_view;
        currentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("current",currentProperty);

        //For present date
        Property presentProperty = new Property();
        presentProperty.layoutResource = R.layout.present_view;
        presentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("present",presentProperty);

        //For absent
        Property absentProperty = new Property();
        absentProperty.layoutResource = R.layout.absent_view;
        absentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("absent",absentProperty);

        //For Full
        Property FullProperty = new Property();
        FullProperty.layoutResource = R.layout.full_view;
        FullProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("full",FullProperty);




        //Set desc has map on custom calendar
        customCalendar.setMapDescToProp(descHashMap);

        //Initialize date hash map
        HashMap<Integer,Object> dateHashMap = new HashMap<>();
        //Initailze calendar
        Calendar calendar = Calendar.getInstance();
        //Put values
        //dateHashMap.put(calendar.get(Calendar.DAY_OF_MONTH),"current");
        //dateHashMap.put(22,"absent");

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_show_date = database.getReference("Login/admin1234/Schedule_time");
        myRef_show_date.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()){
                    String str_date_color = ds.getKey();
                    Date_color.add(str_date_color.substring(0,2));
                    Date_list.add(str_date_color);
                }

                Log.e("Date_color ", Date_color.toString());




                for(int i = 0 ; i < Date_color.size() ; i++ ){
                    if(Date_color.get(i).equals("15")){
                        dateHashMap.put(Integer.valueOf(Date_color.get(i)),"present");
                        Log.e("Text_Date ",String.valueOf(Date_color.get(i))+" present");
                        customCalendar.setDate(calendar,dateHashMap);
                    }else{
                        Log.e("Text_Date ",String.valueOf(Date_color.get(i))+" absent");
                        dateHashMap.put(Integer.valueOf(Date_color.get(i)),"absent");
                        customCalendar.setDate(calendar,dateHashMap);
                    }
                }

                // ยังมีปัญหาในการเรียกค่าออกมา
                HashMap<String, String> map = new HashMap<>();
                for(int i = 0 ; i < Date_list.size() ; i++){
                    DatabaseReference myRef_show_datelist = database.getReference("Login/admin1234/Schedule_time/"+Date_list.get(i));
                    Query query = myRef_show_datelist.orderByKey();

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot ds : snapshot.getChildren()){

                                for(int j = 0 ; j < Date_list.size() ; j++){
                                    Date_Key.add(ds.getKey());
                                    Log.e("Date_list ",Date_Key.toString());
                                    map.put(Date_list.get(j), Date_Key.get(j));


                                }
                                Log.e("Date_list.get(j) ", String.valueOf(map));



                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


            }





            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });











        //dateHashMap.put(5,"present");
//        dateHashMap.put(6,"present");
//        dateHashMap.put(7,"absent");
//        dateHashMap.put(8,"absent");
//        dateHashMap.put(9,"present");
//        dateHashMap.put(10,"present");

//        dateHashMap.put(12,"absent");
//        dateHashMap.put(13,"absent");
//        dateHashMap.put(14,"present");
//        dateHashMap.put(15,"present");
//        dateHashMap.put(16,"present");


        //dateHashMap.put(10,"full");

        //dateHashMap.put(24,"present");
//        dateHashMap.put(25,"present");
//        dateHashMap.put(26,"present");
//        dateHashMap.put(27,"present");
//        dateHashMap.put(28,"absent");



        //Set date
        customCalendar.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(View view, Calendar selectedDate, Object desc) {

                for(int i = 0 ; i < Date_color.size() ; i++ ) {
                    Log.e("selectedDate.get(Calendar.DAY_OF_MONTH) ",String.valueOf(selectedDate.get(Calendar.DAY_OF_MONTH)));
                    Log.e("Integer.parseInt(String.valueOf(Date_color.get(i) ",String.valueOf(Integer.parseInt(String.valueOf(Date_color.get(i)))));
                    if (selectedDate.get(Calendar.DAY_OF_MONTH) == Integer.parseInt(String.valueOf(Date_color.get(i)))) {
                        dialog_date = 20;
                        //Get string date
                        String sDate = checklength(String.valueOf(selectedDate.get(Calendar.DAY_OF_MONTH)))
                                + "/" + checklength(String.valueOf(selectedDate.get(Calendar.MONTH) + 1))
                                + "/" + selectedDate.get(Calendar.YEAR);

                        //Get string date query
                        String sDate_query = checklength(String.valueOf(selectedDate.get(Calendar.DAY_OF_MONTH)))
                                + "-" + checklength(String.valueOf(selectedDate.get(Calendar.MONTH) + 1))
                                + "-" + selectedDate.get(Calendar.YEAR);

                        //Display date toast
                        TextView show_date = findViewById(R.id.show_date_on_click);
                        show_date.setText("วันที่ : " + selectedDate.get(Calendar.DAY_OF_MONTH) + " ว่างให้ใช้บริการนัดหมาย");
                        Toast.makeText(getApplicationContext(), sDate, Toast.LENGTH_SHORT).show();
                        Date_Appointment = sDate;
                        Date_Query = sDate_query;
                        Log.e("Date_Appointment", Date_Appointment);
                        Log.e("Date_Query", Date_Query);
                        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
                        DatabaseReference myRef = database.getReference("Login/admin1234/Schedule_time/" + Date_Query);
                        //get time firebase
                        TableLayout Table_Time = findViewById(R.id.Main_Show_Time);
                        Table_Time.removeAllViews();

                        Query query1 = myRef.orderByValue();
                        query1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                View Vtime = getLayoutInflater().inflate(R.layout.appointment_time_layout, null);

                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    Table_Time.removeAllViews();
                                    Log.e("Date_Query For DAtaSnashot ", ds.getValue().toString());

                                    amount = ds.child("amount").getValue().toString();
                                    time = ds.child("time").getValue().toString();
                                    key = ds.getKey();
                                    Log.e("TIME ", time);
                                    Log.e("AMOUNT ", amount);
                                    Log.e("KEY ", key);

                                    txttime1 = Vtime.findViewById(R.id.txt_time1);
                                    txttime2 = Vtime.findViewById(R.id.txt_time2);
                                    txttime3 = Vtime.findViewById(R.id.txt_time3);
                                    txttime4 = Vtime.findViewById(R.id.txt_time4);
                                    txttime5 = Vtime.findViewById(R.id.txt_time5);

                                    txtamount1 = Vtime.findViewById(R.id.txt_amount1);
                                    txtamount2 = Vtime.findViewById(R.id.txt_amount2);
                                    txtamount3 = Vtime.findViewById(R.id.txt_amount3);
                                    txtamount4 = Vtime.findViewById(R.id.txt_amount4);
                                    txtamount5 = Vtime.findViewById(R.id.txt_amount5);


                                    Log.e("TIME IF 1 ", time);
                                    if (time.equals("08.00-09.00")) {

                                        if (txttime1.getText().toString() != null || !txttime1.getText().toString().equals("")) {
                                            Log.e("TIME CHECK 08.00-09.00 ", time);
                                            RadioButton r1 = findViewById(R.id.radioButton_time_1);
                                            txttime1.setText(time);
                                        }

                                        if (amount.equals("0")) {
                                            txtamount1.setText("เต็ม");
                                        } else {
                                            txtamount1.setText("เหลือ " + amount);
                                        }

                                    } else if (txttime1.getText().toString().equals("")) {
                                        txttime1.setText("08.00-09.00");
                                        txtamount1.setText("งดให้บริการ");
                                    }

                                    Log.e("TIME IF 2 ", time);
                                    if (time.equals("09.00-10.00")) {

                                        if (txttime2.getText().toString() != null || !txttime2.getText().toString().equals("")) {
                                            Log.e("TIME CHECK 09.00-10.00 ", time);
                                            RadioButton r1 = findViewById(R.id.radioButton_time_1);
                                            txttime2.setText(time);
                                        }

                                        if (amount.equals("0")) {
                                            txtamount2.setText("เต็ม");
                                        } else {
                                            txtamount2.setText("เหลือ " + amount);
                                        }

                                    } else if (txttime2.getText().toString().equals("")) {
                                        txttime2.setText("09.00-10.00");
                                        txtamount2.setText("งดให้บริการ");
                                    }

                                    Log.e("TIME IF 3 ", time);
                                    if (time.equals("10.00-11.00")) {

                                        if (txttime3.getText().toString() != null || !txttime3.getText().toString().equals("")) {
                                            Log.e("TIME CHECK 10.00-11.00 ", time);
                                            RadioButton r1 = findViewById(R.id.radioButton_time_1);
                                            txttime3.setText(time);
                                        }

                                        if (amount.equals("0")) {
                                            txtamount3.setText("เต็ม");
                                        } else {
                                            txtamount3.setText("เหลือ " + amount);
                                        }

                                    } else if (txttime3.getText().toString().equals("")) {
                                        txttime3.setText("10.00-11.00");
                                        txtamount3.setText("งดให้บริการ");
                                    }

                                    Log.e("TIME IF 4 ", time);
                                    if (time.equals("13.00-14.00")) {

                                        if (txttime4.getText().toString() != null || !txttime4.getText().toString().equals("")) {
                                            Log.e("TIME CHECK 13.00-14.00 ", time);
                                            RadioButton r1 = findViewById(R.id.radioButton_time_1);
                                            txttime4.setText(time);
                                        }

                                        if (amount.equals("0")) {
                                            txtamount4.setText("เต็ม");
                                        } else {
                                            txtamount4.setText("เหลือ " + amount);
                                        }

                                    } else if (txttime4.getText().toString().equals("")) {
                                        txttime4.setText("13.00-14.00");
                                        txtamount4.setText("งดให้บริการ");
                                    }

                                    Log.e("TIME IF 5 ", time);
                                    if (time.equals("14.00-15.00")) {

                                        if (txttime5.getText().toString() != null || !txttime5.getText().toString().equals("")) {
                                            Log.e("TIME CHECK 14.00-15.00 ", time);
                                            RadioButton r1 = findViewById(R.id.radioButton_time_1);
                                            txttime5.setText(time);
                                        }

                                        if (amount.equals("0")) {
                                            txtamount5.setText("เต็ม");
                                        } else {
                                            txtamount5.setText("เหลือ " + amount);
                                        }

                                    } else if (txttime5.getText().toString().equals("")) {
                                        txttime5.setText("14.00-15.00");
                                        txtamount5.setText("งดให้บริการ");
                                    }

//                            }else if(!"08.00-09.00".equals(time)){
//                                Log.e("TIME CHECK 08.00-09.00 ",time);
//                                RadioButton r1 = findViewById(R.id.radioButton_time_1);
//                                txttime1.setText("08.00-09.00 งดให้บริการ");
//                            }else if(!"09.00-10.00".equals(time)){
//                                Log.e("TIME CHECK 09.00-10.00 ",time);
//                                RadioButton r2 = findViewById(R.id.radioButton_time_2);
//                                txttime2.setText("09.00-10.00 งดให้บริการ");
//                            }else if(!"10.00-11.00".equals(time)){
//                                Log.e("TIME CHECK 10.00-11.00 ",time);
//                                RadioButton r3 = findViewById(R.id.radioButton_time_3);
//                                txttime3.setText("10.00-11.00 งดให้บริการ");
//                            }else if(!"13.00-14.00".equals(time)){
//                                Log.e("TIME CHECK 13.00-14.00 ",time);
//                                RadioButton r4 = findViewById(R.id.radioButton_time_4);
//                                txttime4.setText("13.00-14.00 งดให้บริการ");
//                            }else if(!"14.00-15.00".equals(time)){
//                                Log.e("TIME CHECK 14.00-15.00 ",time);
//                                RadioButton r5 = findViewById(R.id.radioButton_time_1);
//                                txttime5.setText("14.00-15.00 งดให้บริการ");

//                                if(time.equals("09.00-10.00")){
//                                    txttime2.setText(time);
//
//                                    if(amount.equals("เต็ม")){
//                                        txtamount2.setText("เต็ม");
//                                    }else {
//                                        txtamount2.setText("เหลือ "+ amount);
//                                    }
//                                }else {
//
//                                    RadioButton r2 = Vtime.findViewById(R.id.radioButton_time_2);
//                                    LinearLayout time_show2 = Vtime.findViewById(R.id.time_show2);
//                                    r2.setVisibility(View.GONE);
//                                    time_show2.setVisibility(view.GONE);
//                                }
//
//                                if(time.equals("10.00-11.00")){
//                                    txttime3.setText(time);
//
//                                    if(amount.equals("เต็ม")){
//                                        txtamount3.setText("เต็ม");
//                                    }else {
//                                        txtamount3.setText("เหลือ "+ amount);
//                                    }
//                                }else {
//                                    RadioButton r3 = Vtime.findViewById(R.id.radioButton_time_3);
//                                    LinearLayout time_show3 = Vtime.findViewById(R.id.time_show3);
//                                    r3.setVisibility(View.GONE);
//                                    time_show3.setVisibility(view.GONE);
//                                }
//
//                                if(time.equals("13.00-14.00")){
//                                    txttime4.setText(time);
//
//                                    if(amount.equals("เต็ม")){
//                                        txtamount4.setText("เต็ม");
//                                    }else {
//                                        txtamount4.setText("เหลือ "+ amount);
//                                    }
//                                }else {
//                                    RadioButton r4 = Vtime.findViewById(R.id.radioButton_time_4);
//                                    LinearLayout time_show4 = Vtime.findViewById(R.id.time_show4);
//                                    r4.setVisibility(View.GONE);
//                                    time_show4.setVisibility(view.GONE);
//                                }
//
//                                if(time.equals("14.00-15.00")){
//                                    txttime5.setText(time);
//
//                                    if(amount.equals("เต็ม")){
//                                        txtamount5.setText("เต็ม");
//                                    }else {
//                                        txtamount5.setText("เหลือ "+ amount);
//                                    }
//                                }else {
//                                    RadioButton r5 = Vtime.findViewById(R.id.radioButton_time_5);
//                                    LinearLayout time_show5 = Vtime.findViewById(R.id.time_show5);
//                                    r5.setVisibility(View.GONE);
//                                    time_show5.setVisibility(view.GONE);
//                                }


                                    Table_Time.addView(Vtime);

                                }//for  show datatime


                            }//OnDataChange End show time firebase

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });//End query time firebase


                        // Add Appointment to firebase
                        //RadioButton radio_time_select = findViewById(R.id.radioButton_time);
                        DatabaseReference myRef_check_vaccineNo = database.getReference("Login/" + Username + "/" + "Reserve");
                        Query query = myRef_check_vaccineNo.orderByKey().equalTo("Reserve_Deatails");
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot ds : snapshot.getChildren()) {

                                    Log.e("SHOW---------------------------------->", ds.getValue().toString());

                                    check_vaccineNo = ds.child("vaccine_no").getValue().toString();

                                    Log.e("check_vaccineNo ***************************************************** ", check_vaccineNo);

                                    if (check_vaccineNo.equals("วัคซีนจำนวน 1 เข็ม")) {
                                        Appointment_Check = "Appointment_1";
                                    } else {
                                        Appointment_Check = "Appointment_2";
                                    }
                                    Log.e("Appointment_Check ======================================================= ", Appointment_Check);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }

                        }); //end query vaccine_no


                        Button btn_ok = findViewById(R.id.btn_saveappointment);
                        btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog = new Dialog(Make_Appointment_Vaccine_page.this);
                                dialog.setContentView(R.layout.custom_dialog_layout);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
                                }
                                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                dialog.setCancelable(false); //Optional
                                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

                                Button Okay = dialog.findViewById(R.id.btn_okay);
                                Button Cancel = dialog.findViewById(R.id.btn_cancel);

                                Okay.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(Make_Appointment_Vaccine_page.this, "Okay", Toast.LENGTH_SHORT).show();

                                        radioGroup = findViewById(R.id.rg_time);

                                        int checkedId = radioGroup.getCheckedRadioButtonId();
                                        //radio_time_select = findViewById(selectId);

                                        if (checkedId == -1) {
                                            Toast.makeText(Make_Appointment_Vaccine_page.this, "Noting Selected", Toast.LENGTH_SHORT).show();
                                        } else {
                                            findRadioButton(checkedId);
                                        }

//                                    for(int i = 0 ; i < radioGroup.getChildCount();i++){
//                                        RadioButton r = (RadioButton) radioGroup.getChildAt(i);
//                                        if(r.isChecked()){
//
//                                            Reserve_Deatails rd = new Reserve_Deatails();
//                                            DatabaseReference myRef_Query_Reserve = database.getReference("Login/Reserve");
//
//                                            TextView text_time_check = findViewById(R.id.txt_time);
//                                            //text_time_check.setText(time);
//                                            Time_Appointment = text_time_check.getText().toString().trim();
//
//                                        }else {
//                                            Toast.makeText(Make_Appointment_Vaccine_page.this, "เกิดข้อผิดพลาดในการเพิ่มข้อมูล", Toast.LENGTH_LONG).show();
//                                        }//End if add Appointment End if Radio check
//                                    }//End for

                                        if (Appointment_Check.equals("Appointment_1")) {
                                            System.out.println("Appointment_Check_For_Add_Appointment ======================================================= " + Appointment_Check);

                                            DatabaseReference myRef_Schedule_Appointments = database.getReference("Login/" + Username + "/Reserve/Reserve_Deatails/Appointment");
                                            myRef_Schedule_Appointments.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    appointment_id = String.valueOf(snapshot.getChildrenCount());
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }

                                            });
                                            String appointment_status = "ได้ทำการนัดหมายเข็มที่ 1 สำเร็จแล้ว";

                                            Appointment sa = new Appointment(appointment_id, Date_Appointment, appointment_status, Time_Appointment);
                                            //List_Appointment.add(sa);

                                            //List_Appointment.add(Integer.parseInt(appointment_id),appointment_status);
                                            myRef_Schedule_Appointments.child("Appointment_1").setValue(sa);
                                            Log.e("บันทึกเข็มที่ 1 ได้", appointment_status);
                                        } else {
                                            System.out.println("Appointment_Check_For_Add_Appointment ======================================================= " + Appointment_Check);
                                            DatabaseReference myRef_Schedule_Appointments = database.getReference("Login/" + Username + "/Reserve/Reserve_Deatails/Appointment");

                                            myRef_Schedule_Appointments.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    appointment_id = String.valueOf(snapshot.getChildrenCount());
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                            String appointment_status1 = "ได้ทำการนัดหมายเข็มที่ 1 สำเร็จแล้ว";
                                            String appointment_status2 = "ได้ทำการนัดหมายเข็มที่ 2 สำเร็จแล้ว";

                                            Appointment sa1 = new Appointment(appointment_id, Date_Appointment, appointment_status1, Time_Appointment);
                                            //List_Appointment.add(sa);

                                            //List_Appointment.add(Integer.parseInt(appointment_id),appointment_status);
                                            myRef_Schedule_Appointments.child("Appointment_1").setValue(sa1);


                                            String sDate = (selectedDate.get(Calendar.DAY_OF_MONTH))
                                                    + "/" + (selectedDate.get(Calendar.MONTH) + 2)
                                                    + "/" + selectedDate.get(Calendar.YEAR);


                                            Date_Appointment = sDate;
                                            Appointment sa2 = new Appointment(appointment_id, Date_Appointment, appointment_status2, Time_Appointment);
                                            myRef_Schedule_Appointments.child("Appointment_2").setValue(sa2);

                                            String message = "บันทึกการนัดหมายสำหรับ 2 เข็มสำเร็จ";
                                            Log.e("บันทึกเข็มที่ 2 ได้", message);

                                        }


                                        DatabaseReference myRef_del_amount = database.getReference("Login/" + "admin1234" + "/Schedule_time" + "/" + Date_Query);
                                        Query query_del = myRef_del_amount.orderByKey();
                                        query_del.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                for (DataSnapshot ds : snapshot.getChildren()) {
                                                    Log.e("query_del_value ", ds.getValue().toString());
                                                    Log.e("Time_Appointment ", Time_Appointment);
                                                    if (ds.child("time").getValue().toString().equals(Time_Appointment)) {
                                                        key = ds.getKey();
                                                        String num_time = ds.child("amount").getValue().toString();
                                                        check_amount = Integer.parseInt(num_time) - 1;

                                                        DatabaseReference myRef_edit_amount = database.getReference("Login/" + "admin1234" + "/Schedule_time" + "/" + Date_Query + "/" + key);
                                                        //Query query_edit = myRef_edit_amount.orderByKey();
                                                        myRef_edit_amount.child("amount").setValue(check_amount);
                                                        Log.e("หา time เจอ ", String.valueOf(check_amount));
                                                    } else {
                                                        Log.e("หา time ไม่เจอ ", String.valueOf(check_amount));
                                                        DatabaseReference myRef_edit_amount = database.getReference("Login/" + "admin1234" + "/Schedule_time" + "/" + Date_Query + "/" + key);
                                                        //myRef_edit_amount.child("amount").setValue(check_amount);
                                                    }

                                                }


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                        Log.e("cK_num_____", String.valueOf(check_amount));
                                        Intent intent = new Intent(Make_Appointment_Vaccine_page.this, view_appointment_page.class);
                                        intent.putExtra("Username", Username);
                                        startActivity(intent);
                                        dialog.dismiss();


                                    }


                                });

                                Cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Toast.makeText(Make_Appointment_Vaccine_page.this, "Cancel", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });

                                dialog.show(); // Showing the dialog here


                                // myRef_Schedule_Appointments.
                                // DatabaseReference myRef_Add_Appointments_1 = database.getReference("Login/"+Username+"/Reserve/Reserve_Deatails/Appointment");

                            }// End on click
                        }); //btn_ok.setOnClickListener
                    } else {
                        dialog_date = dialog_date-1 ;

                    }

                }//for check color

                if(dialog_date<=0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Make_Appointment_Vaccine_page.this);
                    builder.setTitle("คำความเตือน");
                    builder.setMessage("เลือกวันที่เฉพาะช่องสีเขียวเท่านั้น");
                    builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Make_Appointment_Vaccine_page.this, home_page.class);
                            intent.putExtra("Username", Username);
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    dialog_date = 0;
                }else {
                    dialog_date = 0;
                }
                Log.e("dialog_date", String.valueOf(dialog_date));
            }//public void onDateSelected
        }); //customCalendar.setOnDateSelectedListener



    }//onCreate




    public  String checklength(String s){
        if (s.length()<2){
            s = "0"+s;
        }
        return s;
    }

    private void findRadioButton(int checkedId) {
        switch (checkedId){
            case R.id.radioButton_time_1:
                Time_Appointment = txttime1.getText().toString();
                break;
            case R.id.radioButton_time_2:
                Time_Appointment = txttime2.getText().toString();
                break;
            case R.id.radioButton_time_3:
                Time_Appointment = txttime3.getText().toString();
                break;
            case R.id.radioButton_time_4:
                Time_Appointment = txttime4.getText().toString();
                break;
            case R.id.radioButton_time_5:
                Time_Appointment = txttime5.getText().toString();
                break;
        }

        Log.e("radioButton_time___________ ",Time_Appointment);
    }

}//class