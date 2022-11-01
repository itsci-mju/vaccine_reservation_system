package th.ac.mju.itsci.reservevaccine_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class detail_member_page extends AppCompatActivity {
    String Username;
    String rid;
    String Name_lastname;
    String phone;
    String vaccine ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_member_page);
        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();

        Username = intent.getStringExtra("Username");
        rid = intent.getStringExtra("reserve_id");
        Name_lastname = intent.getStringExtra("Name_lastname");
        phone = intent.getStringExtra("phone");

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        Log.e("Username ",Username);
        Log.e("reserve_id ",rid);



        TextView txt_name_lastname  = findViewById(R.id.txt_name_lastname);
        TextView txt_phone = findViewById(R.id.txt_phone);
        TextView txt_idcard = findViewById(R.id.txt_idcard);
        TextView txt_rid = findViewById(R.id.txt_rid);
        TextView txt_vaccine_name = findViewById(R.id.txt_vaccine_name);
        TextView txt_status = findViewById(R.id.txt_status);
        TextView txt_date_reserve = findViewById(R.id.txt_date_reserve);

        //"รอยืนยันชำระเงิน","ชำระเงินเสร็จสิ้น","ยกเลิกการจอง","ทำการนัดหมายเรียบร้อยแล้ว","หมดเวลานัดการหมาย"
        DatabaseReference myRef_reserve = database.getReference("Login/"+Username+"/Reserve/Reserve_List");
        Query query = myRef_reserve.orderByKey().equalTo(rid);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    txt_name_lastname.setText(Name_lastname);
                    txt_phone.setText(phone);
                    txt_idcard.setText(Username);
                    txt_rid.setText(rid);
                    txt_vaccine_name.setText(ds.child("details").getValue().toString());
                    txt_status.setText(ds.child("status").getValue().toString());
                    txt_date_reserve.setText(ds.child("reserve_date").getValue().toString());

                    vaccine = txt_vaccine_name.getText().toString();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DatabaseReference Reserve_Deatails = database.getReference("Login/" + Username + "/Reserve/Reserve_List/"+rid);
        Query query1 = Reserve_Deatails.orderByKey().equalTo("Reserve_Deatails");
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    txt_vaccine_name.setText(vaccine+" จำนวน "+ds.child("vaccine_no").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}