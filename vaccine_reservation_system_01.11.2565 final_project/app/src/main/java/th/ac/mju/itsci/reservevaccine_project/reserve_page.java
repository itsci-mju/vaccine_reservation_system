package th.ac.mju.itsci.reservevaccine_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class reserve_page extends AppCompatActivity {
    String Username;
    Spinner spinner_Vaccine;
    Spinner spinner_VaccineNo;
    private  ArrayList<String> List_vaccine = new ArrayList<>();
    private  String[] VaccineList = {"Moderna","Pfizer","AstraZeneca","Sinovac","Sinopharm"};
    private  String[] Vaccine_no = {"วัคซีนจำนวน 1 เข็ม","วัคซีนจำนวน 2 เข็ม"};
    String Select_Vaccine;
    String Reserve_To_Day;
    String spin_no ;
    int day_payment ;
    int Month_payment ;
    int year_payment ;


    private static  ArrayList<String> List_reserve_id= new ArrayList<>();
    long Max_reserve_id ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserve_page);


        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        String day = checklength(String.valueOf(mDay));
        String months = checklength(String.valueOf(mMonth+1));

        TextView txtdate = findViewById(R.id.txt_reservedate);
        Reserve_To_Day = day+"-"+months+"-"+mYear;
        txtdate.setText("วันที่ "+day+"-"+months+"-"+mYear);

        spinner_Vaccine = findViewById(R.id.select_VaccineList_item);
        spinner_VaccineNo = findViewById(R.id.spn_no);


        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_vaccine_Name = database.getReference("Login/admin1234/Vaccine");
        myRef_vaccine_Name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    List_vaccine.add(ds.getKey());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(reserve_page.this, android.R.layout.select_dialog_item, List_vaccine);
                spinner_Vaccine.setAdapter(adapter);

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(reserve_page.this, android.R.layout.select_dialog_item, Vaccine_no);
                spinner_VaccineNo.setAdapter(adapter2);

                // Log.e(" Vaccine_Name ", List_vaccine.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });



        //ShowDataSpinner();


        spinner_Vaccine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Select_Vaccine = spinner_Vaccine.getSelectedItem().toString();
                Log.e("Select_Vaccine ",Select_Vaccine);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_Reserve_List = database.getReference("Login/" + Username + "/Reserve/Reserve_List");
        myRef_Reserve_List.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Max_reserve_id =  (dataSnapshot.getChildrenCount());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }//onCreate

    private void ShowDataSpinner() {




    }


    public  String checklength(String s){
        if (s.length()<2){
            s = "0"+s;
        }
        return s;
    }


    public void ClickaddReserve(View view){
        TextView txt_reservedate = findViewById(R.id.txt_reservedate);
        String Reserve_date = txt_reservedate.getText().toString();
//        Spinner spinner = (Spinner)findViewById(R.id.select_VaccineList_item);
//        String vac_spin = spinner.getSelectedItem().toString();
        Spinner spinner2 = (Spinner)findViewById(R.id.spn_no);
                 spin_no = spinner2.getSelectedItem().toString();


        if("วัคซีนจำนวน 2 เข็ม".equals(spin_no)){
            spin_no = "2 เข็ม";
        }else {
            spin_no = "1 เข็ม";
        }
//        CheckBox ck_no1 = findViewById(R.id.ck_vaccineNo1);
//        CheckBox ck_no2 = findViewById(R.id.ck_vaccineNo2);
//        String VaccineNo1 ="";
//        String VaccineNo2 ="";
//        String count = "";
//
//        if(ck_no1.isChecked()){
//            VaccineNo1 = ck_no1.getText().toString();
//            count = "1";
//        }else {
//            VaccineNo2 = ck_no2.getText().toString();
//            count = "2";
//        }


        String queue = "300";
        String status = "รอยืนยันชำระเงิน";
        String details = "ได้สั่งจองวัคซีน " + "1" + " เข็ม";



        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_Reserve_List = database.getReference("Login/" + Username + "/Reserve/Reserve_List");
        Log.e("Max_reserve_id ", String.valueOf(Max_reserve_id));


        Max_reserve_id = Max_reserve_id + 1 ;
        Reserve rs = new Reserve(String.valueOf(Max_reserve_id),status,Reserve_To_Day,Select_Vaccine);
        myRef_Reserve_List.child(String.valueOf(Max_reserve_id)).setValue(rs);


        DatabaseReference myRef_reserveDeatils = database.getReference("Login/" + Username + "/Reserve/Reserve_List/"+Max_reserve_id+"/Reserve_Deatails");
        Reserve_Deatails rd = new Reserve_Deatails(String.valueOf(Max_reserve_id),spin_no);
        myRef_reserveDeatils.setValue(rd);


        Intent intent = new Intent(reserve_page.this, view_reserve_page.class);
        String reserve_id_intent = String.valueOf(Max_reserve_id);
        Log.e("reserve_id_intent ",reserve_id_intent);
        intent.putExtra("reserve_id_intent",reserve_id_intent);
        intent.putExtra("Username", Username);

        intent.putExtra("day_payment", day_payment);
        intent.putExtra("Month_payment", Month_payment);
        intent.putExtra("year_payment", year_payment);

        startActivity(intent);



//        myRef_reserve.child("id").setValue(reserve_id);
//        myRef_reserve.child("status").setValue(rs.getStatus());
//        myRef_reserve.child("reserve_date").setValue(rs.getReserve_date());
//        myRef_reserve.child("details").setValue(rs.getDetails());



    }

    public  void  goHome(View view){
        Intent intent = new Intent(reserve_page.this, home_page.class);
        intent.putExtra("Username", Username);
        List_vaccine.clear();
        startActivity(intent);
    }
}