package th.ac.mju.itsci.reservevaccine_project;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
    String queue_member;
    int check_amount;
    int check_date_dialog = 0;
    private List<String> Date_color = new ArrayList<>();
    private List<String> Date_list = new ArrayList<>();
    List<String> Date_list_For_Qurry_Time = new ArrayList<>();
    List<String> Count_status = new ArrayList<>();
    private Dialog dialog;
    private Button ShowDialog;
    int dialog_date = 0;
    RadioGroup radioGroup ;
    RadioButton radio_time_select;
    String full_amount;
    String Date_red_Color ;
    String key_id_reserve;

    String date_show1;
    String date_show2;
    String Month2;
    String key;

    String date_recipt ;
    String check_time1 = "";
    String check_time2 = "";
    String check_time3 = "";
    String check_time4 = "";
    String check_time5 = "";

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
        queue_member = intent.getStringExtra("queue_member");
        key_id_reserve = intent.getStringExtra("key_id_reserve");


        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        String months = checklength(String.valueOf(mMonth+1));
        String months2 = checklength(String.valueOf(mMonth+2));
        date_show1 = months+"-"+mYear;
        date_show2 = months2+"-"+mYear;

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
        DatabaseReference myRef_recipt = database.getReference("Login/"+Username+"/Reserve/Receipt");
        Query query_date_receipt = myRef_recipt.orderByKey().equalTo(key_id_reserve);
        query_date_receipt.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    date_recipt = ds.child("receipt_date").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //String sub_date_receipt = date_recipt.substring(0,2);


        //FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_show_date = database.getReference("Login/admin1234/Schedule_time/"+date_show1);
        myRef_show_date.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()){
                    if(!ds.getKey().equals(date_recipt)){
                        String str_date_color = ds.getKey();
                        Date_list.add(str_date_color);
                        Date_color.add(str_date_color.substring(0,2));
                    }
                }

                Log.e("Date_list ", Date_list.toString());
                Log.e("Date_color ", Date_color.toString());


            // แสดงสีวันนัดหมายบนปฏิทิน
                for(int i = 0 ; i < Date_list.size() ; i++ ){
                    String date_sub = Date_list.get(i);
                    DatabaseReference myRef_check_queue = database.getReference("Login/admin1234/Schedule_time/"+date_show1+"/"+Date_list.get(i));
                    Query query2 = myRef_check_queue.orderByKey();
                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int total_count = 0;
                            for(DataSnapshot ds : snapshot.getChildren()) {
                                String status = ds.child("status").getValue().toString();
                                Log.e("Status ",status);
                                if (ds.child("status").getValue().toString().equals("ว่าง")) {
                                    total_count = total_count+2;
                                    Log.e("Text_Date ", String.valueOf(date_sub.substring(0,2)) + " present");
                                } else {
                                    total_count = total_count - 1 ;
                                    Log.e("Text_Date ", String.valueOf(date_sub.substring(0,2)) + " absent");

                                }
                            }//for DataSnapshot

                            if(total_count<=0){
                                dateHashMap.put(Integer.valueOf(date_sub.substring(0,2)), "absent");
                                customCalendar.setDate(calendar, dateHashMap);
                                Date_red_Color = date_sub.substring(0,2);
                            }else {
                                dateHashMap.put(Integer.valueOf(date_sub.substring(0,2)), "present");
                                customCalendar.setDate(calendar, dateHashMap);
                                Date_list_For_Qurry_Time.add(date_sub.substring(0,2));
                                Date_red_Color = "0";
                            }
                                Log.e("Date_list_For_Qurry_Time ",Date_list_For_Qurry_Time.toString());

                        }//onDataChange
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                }//for datelist
            }//onDataChange
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        // Add Appointment to firebase
        //RadioButton radio_time_select = findViewById(R.id.radioButton_time);
        DatabaseReference myRef_check_vaccineNo = database.getReference("Login/" + Username + "/" + "Reserve/Reserve_List/"+key_id_reserve);
        Query query = myRef_check_vaccineNo.orderByKey().equalTo("Reserve_Deatails");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {

                    Log.e("SHOW---------------------------------->", ds.getValue().toString());

                    check_vaccineNo = ds.child("vaccine_no").getValue().toString();

                    Log.e("check_vaccineNo ***************************************************** ", check_vaccineNo);

                    if (check_vaccineNo.equals("1 เข็ม")) {
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









        customCalendar.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(View view, Calendar selectedDate, Object desc) {

                // เลือกวันที่แสดงเวลาออกมา
                Log.e("Date_red_Color ",Date_red_Color);
                for(int i = 0 ; i < Date_list.size() ; i++ ) {
                    check_date_dialog = check_date_dialog + 1 ;
                    String Date_Full = Date_list.get(i);
                    if(Integer.parseInt(Date_color.get(i)) == Integer.parseInt(String.valueOf(selectedDate.get(Calendar.DAY_OF_MONTH))) && Integer.parseInt(String.valueOf(selectedDate.get(Calendar.DAY_OF_MONTH))) != Integer.parseInt(Date_red_Color)){

                        Log.e("1 วันที่ในFirebase ", String.valueOf(Integer.parseInt(Date_Full.substring(0,2))));
                        Log.e("2 ที่เลือก  ", String.valueOf(selectedDate.get(Calendar.DAY_OF_MONTH)));
                        Log.e("3 วันในFor ",Date_list.get(i));


                        DatabaseReference myRef_show_time = database.getReference("Login/admin1234/Schedule_time/"+date_show1+"/"+Date_list.get(i));
                        Query query2 = myRef_show_time.orderByKey();
                        query2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int  Full_Date = 5;
                                for(DataSnapshot ds : snapshot.getChildren()) {
                                    String status = ds.child("status").getValue().toString();
                                    if(status.equals("เต็ม")){
                                        Full_Date = Full_Date - 1;
                                        Log.e("วันนี้เต็มแล้ว ", String.valueOf(Full_Date));
                                        Log.e("selectedDate.get(Calendar.DAY_OF_MONTH) ",String.valueOf(selectedDate.get(Calendar.DAY_OF_MONTH)));
//                                        Log.e("Integer.parseInt((Date_Full.substring(0,2))) ",String.valueOf(Integer.parseInt((Date_Full.substring(0,2)))));
                                    }  else {
                                        Log.e("วันนี้ยังไม่เต็ม ", String.valueOf(Full_Date));
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
                                    }
                                }//for


                                if (Full_Date > 0 ) {
                                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
                                    DatabaseReference myRef = database.getReference("Login/admin1234/Schedule_time/"+date_show1+"/"+ Date_Query);

                                    //get time firebase

                                    TableLayout Table_Time = findViewById(R.id.Main_Show_Time);
                                    Table_Time.removeAllViews();

                                    View Vtime = getLayoutInflater().inflate(R.layout.appointment_time_layout, null);

                                    Query query1 = myRef.orderByKey();
                                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot ds : snapshot.getChildren()) {

                                                amount = ds.child("amount").getValue().toString();
                                                time = ds.child("time").getValue().toString();
                                                key = ds.getKey();

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


//---------------------------------------------------------------------time 1---------------------------------------------------------------------------------

                                                if (time.equals("08.00-09.00")) {
                                                    Log.e("TIME IF 1 ", time);
                                                    if (txttime1.getText().toString() != null || !txttime1.getText().toString().equals("")) {
                                                        txttime1.setText(time);
                                                        RadioButton myRadioButton1 = Vtime.findViewById(R.id.radioButton_time_1);
                                                        myRadioButton1.setVisibility(View.VISIBLE);
                                                        check_time1 = "08.00-09.00";
                                                    }

                                                    if (txttime1.getText().toString().equals("08.00-09.00")) {
                                                        Log.e("txttime1 มีค่าแล้ว", txttime1.getText().toString());
                                                    } else {
                                                        txttime1.setText("08.00-09.00");
                                                        txtamount1.setText(" " + "งดให้บริการ");
                                                    }

                                                    if (amount.equals("0")) {
                                                        txtamount1.setText("เต็ม");
                                                        RadioButton myRadioButton1 = Vtime.findViewById(R.id.radioButton_time_1);
                                                        myRadioButton1.setVisibility(View.INVISIBLE);

                                                        txttime1.setTextColor(Color.parseColor("#DC143C"));
                                                        txtamount1.setTextColor(Color.parseColor("#DC143C"));

                                                    } else {
                                                        txtamount1.setText("เหลือ " + amount);
                                                    }

                                                }

//---------------------------------------------------------------------time 2---------------------------------------------------------------------------------
                                                 if (time.equals("09.00-10.00")) {
                                                     Log.e("TIME IF 2 ", time);
                                                     if (txttime2.getText().toString() != null || !txttime2.getText().toString().equals("")) {
                                                         Log.e("TIME CHECK 09.00-10.00 ", time);
                                                         txttime2.setText(time);
                                                         RadioButton myRadioButton2 = Vtime.findViewById(R.id.radioButton_time_2);
                                                         myRadioButton2.setVisibility(View.VISIBLE);
                                                         check_time2 = "09.00-10.00";
                                                     }

                                                     if (txttime2.getText().toString().equals("09.00-10.00")) {
                                                         Log.e("txttime2 มีค่าแล้ว", txttime2.getText().toString());
                                                     } else {
                                                         txttime2.setText("09.00-10.00");
                                                         txtamount2.setText(" " + "งดให้บริการ");


                                                     }

                                                     if (amount.equals("0")) {
                                                         txtamount2.setText("เต็ม");
                                                         RadioButton myRadioButton2 = Vtime.findViewById(R.id.radioButton_time_2);
                                                         myRadioButton2.setVisibility(View.INVISIBLE);

                                                         txttime2.setTextColor(Color.parseColor("#DC143C"));
                                                         txtamount2.setTextColor(Color.parseColor("#DC143C"));
                                                     } else {
                                                         txtamount2.setText("เหลือ " + amount);
                                                     }
                                                 }
//---------------------------------------------------------------------time 3---------------------------------------------------------------------------------
                                                 if (time.equals("10.00-11.00")) {
                                                     Log.e("TIME IF 3 ", time);
                                                     if (txttime3.getText().toString() != null || !txttime3.getText().toString().equals("")) {
                                                         Log.e("TIME CHECK 10.00-11.00 ", time);
                                                         txttime3.setText(time);
                                                         RadioButton myRadioButton3 = Vtime.findViewById(R.id.radioButton_time_3);
                                                         myRadioButton3.setVisibility(View.VISIBLE);
                                                         check_time3 = "10.00-11.00";
                                                     }

                                                     if (txttime3.getText().toString().equals("10.00-11.00")) {
                                                         Log.e("txttime3 มีค่าแล้ว", txttime3.getText().toString());
                                                     } else {
                                                         txttime3.setText("100.00-11.00");
                                                         txtamount3.setText(" " + "งดให้บริการ");


                                                     }

                                                     if (amount.equals("0")) {
                                                         txtamount3.setText("เต็ม");
                                                         RadioButton myRadioButton3 = Vtime.findViewById(R.id.radioButton_time_3);
                                                         myRadioButton3.setVisibility(View.INVISIBLE);


                                                         txttime3.setTextColor(Color.parseColor("#DC143C"));
                                                         txtamount3.setTextColor(Color.parseColor("#DC143C"));
                                                     } else {
                                                         txtamount3.setText("เหลือ " + amount);
                                                     }
                                                 }
//---------------------------------------------------------------------time 4---------------------------------------------------------------------------------
                                                 if (time.equals("13.00-14.00")) {
                                                     Log.e("TIME IF 4 ", time);
                                                     if (txttime4.getText().toString() != null || !txttime4.getText().toString().equals("")) {
                                                         Log.e("TIME CHECK 13.00-14.00 ", time);
                                                         txttime4.setText(time);
                                                         RadioButton myRadioButton4 = Vtime.findViewById(R.id.radioButton_time_4);
                                                         myRadioButton4.setVisibility(View.VISIBLE);
                                                         check_time4 = "13.00-14.00";
                                                     }

                                                     if (txttime4.getText().toString().equals("13.00-14.00")) {
                                                         Log.e("txttime4 มีค่าแล้ว", txttime4.getText().toString());
                                                     } else {
                                                         txttime4.setText("13.00-14.00");
                                                         txtamount4.setText(" " + "งดให้บริการ");


                                                     }

                                                     if (amount.equals("0")) {
                                                         txtamount4.setText("เต็ม");
                                                         RadioButton myRadioButton4 = Vtime.findViewById(R.id.radioButton_time_4);
                                                         myRadioButton4.setVisibility(View.INVISIBLE);

                                                         txttime4.setTextColor(Color.parseColor("#DC143C"));
                                                         txtamount4.setTextColor(Color.parseColor("#DC143C"));
                                                     } else {
                                                         txtamount4.setText("เหลือ " + amount);
                                                     }
                                                 }
//---------------------------------------------------------------------time 5---------------------------------------------------------------------------------
                                                if (time.equals("14.00-15.00")) {
                                                    Log.e("TIME IF 5 ", time);
                                                    if (txttime5.getText().toString() != null || !txttime5.getText().toString().equals("")) {
                                                        Log.e("TIME CHECK 14.00-15.00 ", time);
                                                        txttime5.setText(time);
                                                        RadioButton myRadioButton5 =  Vtime.findViewById(R.id.radioButton_time_5);
                                                        myRadioButton5.setVisibility(View.VISIBLE);
                                                        check_time5 = "14.00-15.00";
                                                    }

                                                    if (txttime5.getText().toString().equals("14.00-15.00")) {
                                                        Log.e("txttime5 มีค่าแล้ว",txttime5.getText().toString());
                                                    }else {
                                                        txttime5.setText("14.00-15.00");
                                                        txtamount5.setText(" "+"งดให้บริการ");

                                                    }

                                                    if (amount.equals("0")) {
                                                        txtamount5.setText("เต็ม");
                                                        RadioButton myRadioButton5 =  Vtime.findViewById(R.id.radioButton_time_5);
                                                        myRadioButton5.setVisibility(View.INVISIBLE);

                                                        txttime5.setTextColor(Color.parseColor("#DC143C"));
                                                        txtamount5.setTextColor(Color.parseColor("#DC143C"));

                                                    } else {
                                                        txtamount5.setText("เหลือ " + amount);
                                                    }
                                                }


                                                Log.e("check_time1",check_time1);
                                                Log.e("check_time2",check_time2);
                                                Log.e("check_time3",check_time3);
                                                Log.e("check_time4",check_time4);
                                                Log.e("check_time5",check_time5);




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
                                                                    Get_Out_my_page_now();
                                                                } else {
                                                                    findRadioButton(checkedId);

                                                                    if (Appointment_Check.equals("Appointment_1")) {
                                                                        System.out.println("Appointment_Check_For_Add_Appointment ======================================================= " + Appointment_Check);

                                                                        DatabaseReference myRef_Schedule_Appointments = database.getReference("Login/" + Username + "/" + "Reserve/Reserve_List/"+key_id_reserve+"/Reserve_Deatails/Appointment");
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
                                                                        DatabaseReference myRef_Schedule_Appointments = database.getReference("Login/" + Username + "/" + "Reserve/Reserve_List/"+key_id_reserve+"/Reserve_Deatails/Appointment");

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

                                                                    }// check do apoointment 1 or 2


                                                                    DatabaseReference myRef_del_amount = database.getReference("Login/" + "admin1234" + "/Schedule_time/" + date_show1 + "/" + Date_Query);
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

                                                                                    DatabaseReference myRef_edit_amount = database.getReference("Login/" + "admin1234" + "/Schedule_time/" + date_show1 + "/" + Date_Query + "/" + key);
                                                                                    //Query query_edit = myRef_edit_amount.orderByKey();

                                                                                    if(check_amount<=0){
                                                                                        full_amount = "เต็ม";
                                                                                    }else {
                                                                                        full_amount = "ว่าง";
                                                                                    }
                                                                                    myRef_edit_amount.child("amount").setValue(check_amount);
                                                                                    myRef_edit_amount.child("status").setValue(full_amount);

//                                                                                    String month2 ;
//                                                                                    String date = Date_Query.substring(0,2);
//                                                                                    String month = Date_Query.substring(3, 5);
//                                                                                    String  year =  Date_Query.substring(6);
//
//                                                                                    int monthInt = Integer.parseInt(month+1);

                                                                                   // month2 = date+"-"+checklength(String.valueOf(monthInt))+"-"+year;


                                                                                    if (Appointment_Check.equals("Appointment_2")) {
                                                                                        Month2 = (checklength(String.valueOf(selectedDate.get(Calendar.DAY_OF_MONTH))))
                                                                                                + "-" + (selectedDate.get(Calendar.MONTH) + 2)
                                                                                                + "-" + selectedDate.get(Calendar.YEAR);

                                                                                        DatabaseReference myRef_edit_amount_month2 = database.getReference("Login/" + "admin1234" + "/Schedule_time" + "/" +date_show2 + "/" + Month2 + "/" + key);
                                                                                        myRef_edit_amount_month2.child("amount").setValue(check_amount);
                                                                                        myRef_edit_amount_month2.child("status").setValue(full_amount);
                                                                                    }

                                                                                    Log.e("หา time เจอ ", String.valueOf(check_amount));
                                                                                } else {
                                                                                    Log.e("หา time ไม่เจอ ", String.valueOf(check_amount));
                                                                                    DatabaseReference myRef_edit_amount = database.getReference("Login/" + "admin1234" + "/Schedule_time" + "/" +date_show1+ "/" + Date_Query + "/" + key);
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
                                                                    intent.putExtra("queue_member",queue_member);
                                                                    intent.putExtra("key_id_reserve",key_id_reserve);
                                                                    intent.putExtra("Username", Username);
                                                                    startActivity(intent);
                                                                    dialog.dismiss();




                                                                }//else check radio_id

                                                              DatabaseReference myRef_payment_status = database.getReference("Login/"+Username+"/Reserve/Reserve_List/"+key_id_reserve);
                                                              myRef_payment_status.child("status").setValue("ทำการนัดหมายเรียบร้อยแล้ว");

                                                              DatabaseReference myRef_recipt = database.getReference("Login/"+Username+"/Reserve/Receipt/"+key_id_reserve);
                                                              myRef_recipt.child("receipt_status").setValue("ทำการนัดหมายเรียบร้อยแล้ว");


                                                            } //on click void
                                                        });  //okey.setonClick

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

                                            }//for

                                            Table_Time.addView(Vtime);

                                            if(check_time1 != "08.00-09.00"){
                                                txttime1.setText("08.00-09.00");
                                                txtamount1.setText(" "+"งดให้บริการ");
                                                System.out.println("--------------------11111111111111111111111111111111111111111111");
                                                txttime1.setTextColor(Color.parseColor("#adadad"));
                                                txtamount1.setTextColor(Color.parseColor("#adadad"));
                                            }

                                            if(check_time2 != "09.00-10.00"){
                                                txttime2.setText("09.00-10.00");
                                                txtamount2.setText(" "+"งดให้บริการ");
                                                System.out.println("--------------------22222222222222222222222222222222222222222222");
                                                txttime2.setTextColor(Color.parseColor("#adadad"));
                                                txtamount2.setTextColor(Color.parseColor("#adadad"));
                                            }

                                            if(check_time3 != "10.00-11.00"){
                                                txttime3.setText("10.00-11.00");
                                                txtamount3.setText(" "+"งดให้บริการ");
                                                System.out.println("--------------------33333333333333333333333333333333333333333333");
                                                txttime3.setTextColor(Color.parseColor("#adadad"));
                                                txtamount3.setTextColor(Color.parseColor("#adadad"));
                                            }

                                            if(check_time4 != "13.00-14.00"){
                                                txttime4.setText("13.00-14.00");
                                                txtamount4.setText(" "+"งดให้บริการ");
                                                System.out.println("--------------------44444444444444444444444444444444444444444444");
                                                txttime4.setTextColor(Color.parseColor("#adadad"));
                                                txtamount4.setTextColor(Color.parseColor("#adadad"));
                                            }

                                            if(check_time5 != "14.00-15.00"){
                                                txttime5.setText("14.00-15.00");
                                                txtamount5.setText(" "+"งดให้บริการ");
                                                System.out.println("--------------------55555555555555555555555555555555555555555555");
                                                txttime5.setTextColor(Color.parseColor("#adadad"));
                                                txtamount5.setTextColor(Color.parseColor("#adadad"));
                                            }


                                            check_time1 = "";
                                            check_time2 = "";
                                            check_time3 = "";
                                            check_time4 = "";
                                            check_time5 = "";
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });




                                }




                            }//onDateSelected

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }

                        });

                        check_date_dialog = check_date_dialog + 1;
                    }else {
                        check_date_dialog = check_date_dialog - 1;
                    }

                }//for check color

                System.out.println("XxXxX XxXxX "+check_date_dialog);

                if(check_date_dialog == 0 ){
                    Log.e("dialog_date ", String.valueOf(dialog_date));
                    AlertDialog.Builder builder = new AlertDialog.Builder(Make_Appointment_Vaccine_page.this);
                    builder.setTitle("คำความเตือน");
                    builder.setMessage("เลือกวันที่เฉพาะช่องสีเขียวเท่านั้น");
                    builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Make_Appointment_Vaccine_page.this, Make_Appointment_Vaccine_page.class);
                            intent.putExtra("Username", Username);
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                check_date_dialog = 0;

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


    private void Get_Out_my_page_now() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Make_Appointment_Vaccine_page.this);
        builder.setTitle("คำความเตือน");
        builder.setMessage("คุณยังไม่ได้เลือกวันที่นัดหมาย");
        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Make_Appointment_Vaccine_page.this, Make_Appointment_Vaccine_page.class);
                intent.putExtra("Username", Username);
                startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}//class