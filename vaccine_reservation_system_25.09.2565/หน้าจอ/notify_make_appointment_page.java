package th.ac.mju.itsci.reservevaccine_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.util.List;

public class notify_make_appointment_page extends AppCompatActivity {
    String Username;
    String queue_member;
    String schedule_queue_member;
    List<String> Date_Key = new ArrayList<>();
    String queue_start = null ;
    String queue_end = null ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_make_appointment_page);
        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_queue = database.getReference("Login/"+Username);
        Query query = myRef_queue.orderByKey().equalTo("Reserve");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
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


        //FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_schedule_queue = database.getReference("Login/admin1234/Schedule_time");
        Query query1 = myRef_schedule_queue.orderByKey();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()){
                     Date_Key.add(ds.getKey());
//                     queue_start = ds.child("queue_start").getValue().toString();
//                     queue_end = ds.child("queue_end").getValue().toString();
                }
                Log.e("Date_Key ",Date_Key.toString());

                for(int i = 0 ; i < Date_Key.size() ; i++){
                    Log.e("Date_Key In For ",Date_Key.get(i));
                    DatabaseReference myRef_check_queue = database.getReference("Login/admin1234/Schedule_time/"+Date_Key.get(i));
                    Query query2 = myRef_check_queue.orderByValue();
                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                queue_start = ds.child("queue_start").getValue().toString();
                                queue_end = ds.child("queue_end").getValue().toString();

                                int qs = Integer.parseInt(queue_start);
                                int qe = Integer.parseInt(queue_end);

                                if(Integer.parseInt(queue_member)>=qs&&Integer.parseInt(queue_member)<=qe){
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