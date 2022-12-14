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
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class notify_make_appointment_page extends AppCompatActivity {
    String Username;
    String queue_member = "0";
    String schedule_queue_member;
    List<String> Date_Key = new ArrayList<>();
    String queue_start = null ;
    String queue_end = null ;
    String status;
    int qs;
    int qe;
    int status_ck = 0;
    String key_id_reserve;
    String date_show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_make_appointment_page);
        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        String months = checklength(String.valueOf(mMonth+1));
        date_show = months+"-"+mYear;

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_payment = database.getReference("Login/"+Username+"/Reserve/Receipt");
        Query query_queue = myRef_payment.orderByKey();
        query_queue.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Log.e("receipt_status ",ds.child("receipt_status").getValue().toString());
                    if(!ds.child("receipt_status").getValue().toString().equals("???????????????????????????????????????????????????????????????????????????")){
                        key_id_reserve = ds.getKey();
                        queue_member = ds.child("queue").getValue().toString();
                        Log.e("queue_member ",queue_member);

                        TextView show_queue_able = findViewById(R.id.show_queue_able);
                        show_queue_able.setText("??????????????????????????????????????? "+queue_member+" ?????????????????????????????????????????????????????????");
                        Toast.makeText(notify_make_appointment_page.this, "??????????????????????????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();

                        status_ck=1;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        List<Integer> Sub_date_list = new ArrayList<>();
        //FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_schedule_queue = database.getReference("Login/admin1234/Schedule_time/"+date_show);
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

                if(Sub_date_list.size() < 0 ){
                    AlertDialog.Builder builder = new AlertDialog.Builder(notify_make_appointment_page.this);
                    builder.setTitle("?????????????????????????????????");
                    builder.setMessage("??????????????????????????????????????????????????????????????????????????????????????????");
                    builder.setPositiveButton("????????????", new DialogInterface.OnClickListener() {
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

                Collections.sort(Sub_date_list);
                int Max = Sub_date_list.get(Sub_date_list.size() - 1);
                int Min = Sub_date_list.get(0);

                Log.e("Date_Key ",Date_Key.toString());
                Log.e("Sub_date_list ",Sub_date_list.toString());
                Log.e("Max Min  ", String.valueOf(Min));
                Log.e("Max Max  ", String.valueOf(Max));


                TextView txt_show_date  = findViewById(R.id.txt_show_date);
                txt_show_date.setText("???????????????????????????????????????????????????????????????????????????????????? ");
                TextView txt_date_apponitments  = findViewById(R.id.txt_date_apponitments);
                txt_date_apponitments.setText(Min + " - " + Max +" ???.???. 2565 ???????????? 23.59 ???.");


                for(int i = 0 ; i < Date_Key.size() ; i++){
                    Log.e("Date_Key In For ",Date_Key.get(i));
                    DatabaseReference myRef_check_queue = database.getReference("Login/admin1234/Schedule_time/"+date_show+"/"+Date_Key.get(i));
                    Query query2 = myRef_check_queue.orderByKey();
                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                Log.e("?????????????????? ",Date_Key.get(0));
                                //queue_start = ds.child("queue_start").getValue().toString();
                                //queue_end = ds.child("queue_end").getValue().toString();


//                                TextView show_queue_tietle = findViewById(R.id.show_queue_tietle);
//                                show_queue_tietle.setText(" ???????????????????????? "+ queue_start + " ????????? " + queue_end );

                                 //qs = Integer.parseInt(queue_start);
                                 //qe = Integer.parseInt(queue_end);
                                //Integer.parseInt(queue_member) >= qs && Integer.parseInt(queue_member) <= qe
                                if(status_ck==1){
                                    TextView show_queue_able = findViewById(R.id.show_queue_able);
                                    show_queue_able.setText("??????????????????????????????????????? "+queue_member+" ?????????????????????????????????????????????????????????");
                                    Toast.makeText(notify_make_appointment_page.this, "??????????????????????????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                                    break;
                                }else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(notify_make_appointment_page.this);
                                    builder.setTitle("?????????????????????????????????");
                                    builder.setMessage("??????????????????????????????????????????????????????????????????????????????????????????");
                                    builder.setPositiveButton("????????????", new DialogInterface.OnClickListener() {
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

                            //Log.e("queue_start ",queue_start);
                            //Log.e("queue_end ",queue_end);

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


    public  String checklength(String s){
        if (s.length()<2){
            s = "0"+s;
        }
        return s;
    }


    public void Open_make_appointment_vaccine_page(View view){
        Intent intent = new Intent(notify_make_appointment_page.this, Make_Appointment_Vaccine_page.class);
        intent.putExtra("key_id_reserve",key_id_reserve);
        intent.putExtra("queue_member",queue_member);
        intent.putExtra("Username", Username);
        startActivity(intent);
    }

}//class