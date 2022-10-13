package th.ac.mju.itsci.reservevaccine_project;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class list_Member_page extends AppCompatActivity {
    String Username;
    Spinner spinner_Vaccine;
    Spinner spinner_Lots;
    Spinner spinner_No;
    String company;
    String date_in;
    String vaccine_name;

    ArrayList<String> VaccineList = new ArrayList<>();
    ArrayList<String> LotsList = new ArrayList<>();
    String str_ck = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list__member_page);
        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");

        this.setspinnerVaccine_Name();


        Button btn_search = findViewById(R.id.button_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout show_member = findViewById(R.id.show_member);
                show_member.setVisibility(View.VISIBLE);
            }
        });


        Button btn_detail = findViewById(R.id. button_detail);
        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(list_Member_page.this, detail_member_page.class);
                Username = "1559900374952";
                intent.putExtra("Username", Username);
                startActivity(intent);
            }
        });

    }

    private void setspinnerVaccine_Name() {

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_list_vaccineName = database.getReference("Login/"+Username+"/"+"Vaccine/Moderna");

        Query query = myRef_list_vaccineName.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    String v_Name = ds.child("vaccineName\n").getValue().toString();

                    if(!str_ck.equals(v_Name)){
                        VaccineList.add(v_Name);
                        str_ck = v_Name;

                    }else{
                        v_Name = "";
                    }

                }
                Log.e("Value of Vaccine",VaccineList.toString());

                spinner_Vaccine = findViewById(R.id.spn_vaccineName);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(list_Member_page.this, android.R.layout.select_dialog_item, VaccineList);
                spinner_Vaccine.setAdapter(adapter);

                spinner_Vaccine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String vaccine_Name = adapterView.getSelectedItem().toString();
                        LotsList.clear();
                        Log.e("Value of Vaccine_Name",vaccine_Name);
                        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
                        DatabaseReference myRef_list_lots = database.getReference("Login/"+Username+"/Vaccine/"+vaccine_Name);

                        DatabaseReference myRef_vaccine_check = database.getReference("Login/"+Username+"/Vaccine");
                        Query query_ck = myRef_vaccine_check.orderByKey().equalTo(vaccine_Name);
                        query_ck.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    company = ds.child("company").getValue().toString();
                                    date_in = ds.child("date_in").getValue().toString();
                                    vaccine_name = ds.child("vaccineName").getValue().toString();
                                }
                                Log.e("company ",company);
                                Log.e("date_in ",date_in);
                                Log.e("vaccine_name ",vaccine_name);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                        Query query1 = myRef_list_lots.orderByKey();
                        query1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot ds : snapshot.getChildren()){
                                    if (!ds.getValue().toString().equals(company)) {
                                        if (!ds.getValue().toString().equals(date_in)) {
                                            if (!ds.getValue().toString().equals(vaccine_name)) {
                                                Log.e("Value of FOR THIS ",ds.getValue().toString());
                                                LotsList.add(ds.child("id").getValue().toString());
                                                Log.e("Value OF Lot_list  ",LotsList.toString());
                                            } else {
                                                Log.e("ค่าที่ไม่ต้องให้แสดงออกมา ",ds.getValue().toString());
                                            }
                                        }
                                    }

                                }//for

                                String num[] = {"เลือกจำนวน", "1", "2"};

                                spinner_Lots = findViewById(R.id.spn_vaccinelots);
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(list_Member_page.this, android.R.layout.select_dialog_item, LotsList);
                                spinner_Lots.setAdapter(adapter);


                                spinner_No = findViewById(R.id.spn_no_vaccine);
                                ArrayAdapter<String> adapter_no_vaccine = new ArrayAdapter<>(list_Member_page.this, android.R.layout.select_dialog_item, num);
                                spinner_No.setAdapter(adapter_no_vaccine);


                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }//OnItemselect

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }//void setspinnerVaccine_Name



    public void onclickDate_Start(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(list_Member_page.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {

                TextView TV_date = findViewById(R.id.tv_datestart);
                String day =    checklength(String.valueOf(dayOfMount));
                String months = checklength(String.valueOf(month+1));
                TV_date.setText(day + " - " + months + " - " + year);

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    public void onclickDate_End(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(list_Member_page.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {

                TextView TV_date = findViewById(R.id.tv_dateend);
                String day =    checklength(String.valueOf(dayOfMount));
                String months = checklength(String.valueOf(month+1));
                TV_date.setText(day + " - " + months + " - " + year);

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    public  String checklength(String s){
        if (s.length()<2){
            s = "0"+s;
        }
        return s;
    }

}