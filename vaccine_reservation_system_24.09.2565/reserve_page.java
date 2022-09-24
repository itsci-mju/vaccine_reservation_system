package th.ac.mju.itsci.reservevaccine_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class reserve_page extends AppCompatActivity {
    String Username;
    Spinner spinner_Vaccine;
    Spinner spinner_VaccineNo;
    //ArrayList<String> List_vaccine = null;
    private static String[] VaccineList = {"Moderna","Pfizer","AstraZeneca","Sinovac","Sinopharm"};
    private static String[] Vaccine_no = {"วัคซีนจำนวน 1 เข็ม","วัคซีนจำนวน 2 เข็ม"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserve_page);


        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth=  c.get(Calendar.MONTH);
        int mDay= c.get(Calendar.DAY_OF_MONTH);
        String day =    checklength(String.valueOf(mDay));
        String months = checklength(String.valueOf(mMonth+1));

        TextView txtdate = findViewById(R.id.txt_reservedate);
        txtdate.setText(day+"-"+months+"-"+mYear);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_vaccine = database.getReference("Vaccine");

        Query query = myRef_vaccine.orderByValue();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Log.e("ค่าที่คาดหวัง", ds.getValue().toString());
                    Log.e("vaccine_name", ds.child("name").getValue().toString());
                    String vaccine = ds.child("name").getValue().toString();
                    //List_vaccine.add(vaccine);

                    //System.out.println(List_vaccine.toString());
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        spinner_Vaccine = findViewById(R.id.select_VaccineList_item);
        spinner_VaccineNo = findViewById(R.id.spn_no);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(reserve_page.this, android.R.layout.select_dialog_item, VaccineList);
        spinner_Vaccine.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(reserve_page.this, android.R.layout.select_dialog_item, Vaccine_no);
        spinner_VaccineNo.setAdapter(adapter2);

    }//onCreate




    public  String checklength(String s){
        if (s.length()<2){
            s = "0"+s;
        }
        return s;
    }


    public void ClickaddReserve(View view){
        TextView txt_reservedate = findViewById(R.id.txt_reservedate);
        String Reserve_date = txt_reservedate.getText().toString();
        Spinner spinner = (Spinner)findViewById(R.id.select_VaccineList_item);
        String vac_spin = spinner.getSelectedItem().toString();
        Spinner spinner2 = (Spinner)findViewById(R.id.spn_no);
        String spin_no = spinner2.getSelectedItem().toString();


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


        String queue = "Reserve_001";
        String status = "ได้สั่งจองวัคซีนแล้วรอชำระเงิน";
        String details = "ได้สั่งจองวัคซีน " + "1" + " เข็ม";

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");

        DatabaseReference myRef_reserve = database.getReference("Login/"+Username+"/"+"Reserve");
        Reserve rs = new Reserve(status,Reserve_date,vac_spin,queue);
        myRef_reserve.child("queue").setValue(rs.getQueue());
        myRef_reserve.child("status").setValue(rs.getStatus());
        myRef_reserve.child("reserve_date").setValue(rs.getReserve_date());
        myRef_reserve.child("details").setValue(rs.getDetails());

        DatabaseReference myRef_reserveDeatils = database.getReference("Login/"+Username+"/"+"Reserve"+"/Reserve_Deatails");
        Reserve_Deatails rd = new Reserve_Deatails("1",spin_no);
        myRef_reserveDeatils.setValue(rd);

        Intent intent = new Intent(reserve_page.this, home_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);

    }

    public  void  goHome(View view){
        Intent intent = new Intent(reserve_page.this, home_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);
    }
}