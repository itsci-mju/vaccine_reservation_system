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

import java.util.ArrayList;

public class summary_report_page extends AppCompatActivity {
    String Username;
    ArrayList<String> List_Member = new ArrayList<>();
    String status;
    String vaccine_no1;
    String vaccine_no2;
    TextView show_vaccineNo1;
    TextView show_vaccineNo2;
    TextView show_queue_appointment;
    String queue_appointment;

    TextView show_vaccineNo1_month;
    TextView show_vaccineNo2_month;
    TextView show_queue_appointment_month;

    String aq_now;
    String aq_next;

    TextView amount_queue_now;
    TextView amount_queue_next;

    int v1 = 0;
    int v2 = 0;
    int q = 0;
    int sumq = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_report_page);
        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");


        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");

        DatabaseReference myRef_list_vaccineName = database.getReference("Login");
        Query query = myRef_list_vaccineName.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count_user = 0;
                for(DataSnapshot ds : snapshot.getChildren()){
                    if(!ds.getKey().equals("admin1234")){
                        String Member_key = ds.getKey();
                        List_Member.add(Member_key);
                        count_user = count_user + 1;

                        DatabaseReference myRef_recipt = database.getReference("Login/"+Member_key+"/Reserve/Receipt");
                        Query query1 = myRef_recipt.orderByKey();
                        query1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()){
                                    String checl_unit  = ds.child("vaccine_unit").getValue().toString();
                                    Log.e("checl_unit",checl_unit);
                                    if(checl_unit.equals("1")){
                                        v1 = v1 + 1;
                                    }else {
                                        v2 = v2 + 1;
                                    }


                                    String count_queue = ds.child("receipt_status").getValue().toString();
                                    if (count_queue.equals("ทำการนัดหมายเรียบร้อยแล้ว")){
                                        q = q+1;
                                    }

                                    if (!ds.getKey().equals(null)){
                                        sumq = sumq + 1;
                                    }


                                }
                                vaccine_no1 = String.valueOf(v1);
                                vaccine_no2 = String.valueOf(v2);

                                show_vaccineNo1 = findViewById(R.id.show_vaccineNo1);
                                show_vaccineNo2 = findViewById(R.id.show_vaccineNo2);

                                show_vaccineNo1_month = findViewById(R.id.show_vaccineNo1_month);
                                show_vaccineNo2_month = findViewById(R.id.show_vaccineNo2_month);

                                show_vaccineNo1.setText("การสั่งจอง 1 เข็ม จำนวน " + vaccine_no1 +" รายการ");
                                show_vaccineNo2.setText("การสั่งจอง 2 เข็ม จำนวน " + vaccine_no2 +" รายการ");

                                show_vaccineNo1_month.setText("การสั่งจอง 1 เข็ม จำนวน " + vaccine_no2 +" รายการ");
                                show_vaccineNo2_month.setText("การสั่งจอง 2 เข็ม จำนวน " + 0 +" รายการ");

                                queue_appointment = String.valueOf(q);
                                show_queue_appointment = findViewById(R.id.show_queue_appointment);
                                show_queue_appointment.setText("จำนวนคิวที่ได้นัดหมายไปแล้ว จำนวน "+ queue_appointment + " คิว");

                                show_queue_appointment_month = findViewById(R.id.show_queue_appointment_month);
                                show_queue_appointment_month.setText("จำนวนคิวที่ได้นัดหมายไปแล้ง จำนวน "+ vaccine_no2 + " คิว");

                                aq_now = String.valueOf(sumq);
                                amount_queue_now = findViewById(R.id.amount_queue_now);
                                amount_queue_now.setText("จำนวนการจองคิวทั้งหมด "+aq_now+" คิว");

                                amount_queue_next = findViewById(R.id.amount_queue_next);
                                amount_queue_next.setText("จำนวนการจองคิวทั้งหมด "+vaccine_no2+" คิว");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }

                Log.e("ผู้ใช้งานระบบทั้งหมด ",String.valueOf(count_user));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }//Oncreate

}