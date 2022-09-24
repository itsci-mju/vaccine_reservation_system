package th.ac.mju.itsci.reservevaccine_project;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.Calendar;
import java.util.HashMap;

public class scheule_lots_page extends AppCompatActivity {
     CustomCalendar customCalendar;
     String Username;
     String date_result1 = "";
     String date_result2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheule_lots_page);


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

        //Set desc has map on custom calendar
        customCalendar.setMapDescToProp(descHashMap);

        //Initialize date hash map
        HashMap<Integer,Object> dateHashMap = new HashMap<>();
        //Initailze calendar
        Calendar calendar = Calendar.getInstance();
        //Put values
        //dateHashMap.put(calendar.get(Calendar.DAY_OF_MONTH),"current");
        //dateHashMap.put(22,"absent");
        dateHashMap.put(5,"present");
        dateHashMap.put(6,"present");
        dateHashMap.put(7,"absent");
        dateHashMap.put(8,"present");
        dateHashMap.put(9,"present");
        dateHashMap.put(10,"present");



        //Set date
        customCalendar.setDate(calendar,dateHashMap);

        TableLayout Table_Time = findViewById(R.id.Main_layout_Lots);
        Table_Time.removeAllViews();

        View L  = getLayoutInflater().inflate(R.layout.lots_vaccine,null);
        Table_Time.addView(L);

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH)+1;
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        EditText d1 = findViewById(R.id.d1);
        EditText d2 = findViewById(R.id.d2);
        EditText text = findViewById(R.id.xx1);

        d1.setText(mDay+"-"+mMonth+"-"+mYear);
        d2.setText("10"+"-"+mMonth+"-"+mYear);
        text.setText("99");
    }



    public void onclickDate1(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(scheule_lots_page.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {
                EditText txtdate = findViewById(R.id.d1);
                String day =    checklength(String.valueOf(dayOfMount));
                String months = checklength(String.valueOf(month+1));
                txtdate.setText(day+"-"+months+"-"+year);
                date_result1 = txtdate.getText().toString();
            }
        },mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void onclickDate2(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(scheule_lots_page.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {
                EditText txtdate = findViewById(R.id.d2);
                String day =    checklength(String.valueOf(dayOfMount));
                String months = checklength(String.valueOf(month+1));
                txtdate.setText(day+"-"+months+"-"+year);
                date_result2 = txtdate.getText().toString();
            }

        },mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public  String checklength(String s){
        if (s.length()<2){
            s = "0"+s;
        }
        return s;
    }

}