package th.ac.mju.itsci.reservevaccine_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class notify_make_appointment_page extends AppCompatActivity {
    String Username;
    String queue_member;
    String schedule_queue_member;
    List<String> Date_Key = new ArrayList<>();
    String queue_start = null ;
    String queue_end = null ;
    String status;
    int qs;
    int qe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_make_appointment_page);
        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_list_reserve = database.getReference("Login/"+Username+"/Reserve/Reserve_List");
        Query query = myRef_list_reserve.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    status = ds.child("status").getValue().toString();
                    Log.e("status ",status);

                    if(status.equals("ชำระเงินเสร็จสิ้น")){
                        DatabaseReference myRef_payment = database.getReference("Login/"+Username+"/Reserve/Receipt");
                        Query query_queue = myRef_payment.orderByKey().equalTo(ds.getKey());
                        query_queue.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot ds : snapshot.getChildren()){

                                    queue_member = ds.child("queue").getValue().toString();
                                    Log.e("queue_member ",queue_member);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }



            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        List<Integer> Sub_date_list = new ArrayList<>();
        //FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_schedule_queue = database.getReference("Login/admin1234/Schedule_time");
        Query query1 = myRef_schedule_queue.orderByKey();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()){
                     Date_Key.add(ds.getKey());
                     Sub_date_list.add(Integer.parseInt(ds.getKey().substring(0,2)));
//                     queue_start = ds.child("queue_start").getValue().toString();
//                     queue_end = ds.child("queue_end").getValue().toString();
                }

                Collections.sort(Sub_date_list);
                int Max = Sub_date_list.get(Sub_date_list.size() - 1);
                int Min = Sub_date_list.get(0);

                Log.e("Date_Key ",Date_Key.toString());
                Log.e("Sub_date_list ",Sub_date_list.toString());
                Log.e("Max Min  ", String.valueOf(Min));
                Log.e("Max Max  ", String.valueOf(Max));


                TextView txt_show_date  = findViewById(R.id.txt_show_date);
                txt_show_date.setText("กรุณาทำการนัดหมายภายในวันที่ ");
                TextView txt_date_apponitments  = findViewById(R.id.txt_date_apponitments);
                txt_date_apponitments.setText(Min + " - " + Max +" ก.ย. 2565 เวลา 23.59 น.");



                for(int i = 0 ; i < Date_Key.size() ; i++){
                    Log.e("Date_Key In For ",Date_Key.get(i));
                    DatabaseReference myRef_check_queue = database.getReference("Login/admin1234/Schedule_time/"+Date_Key.get(i));
                    Query query2 = myRef_check_queue.orderByKey();
                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                Log.e("เข้านะ ",Date_Key.get(0));
                                queue_start = ds.child("queue_start").getValue().toString();
                                queue_end = ds.child("queue_end").getValue().toString();


                                TextView show_queue_tietle = findViewById(R.id.show_queue_tietle);
                                show_queue_tietle.setText(" ลำดับที่ "+ queue_start + " ถึง " + queue_end );

                                 qs = Integer.parseInt(queue_start);
                                 qe = Integer.parseInt(queue_end);

                                if(Integer.parseInt(queue_member) >= qs && Integer.parseInt(queue_member) <= qe){
                                    Toast.makeText(notify_make_appointment_page.this, "คุณมีสิทธิ์เลือกวันนัดหมาย", Toast.LENGTH_SHORT).show();
                                    break;
                                }else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(notify_make_appointment_page.this);
                                    builder.setTitle("คำความเตือน");
                                    builder.setMessage("ท่านยังไม่มีสิทธิ์ในการนัดหมาย");
                                    builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(notify_make_appointment_page.this, home_page.class);
                                            intent.putExtra("Username", Username);
                                            startActivity(intent);
                                        }
                                    });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }

                            }
                            Log.e("queue_start ",queue_start);
                            Log.e("queue_end ",queue_end);


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

















    }//on crate


    public void Open_make_appointment_vaccine_page(View view){
        Intent intent = new Intent(notify_make_appointment_page.this, Make_Appointment_Vaccine_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);
    }

}//class