package th.ac.mju.itsci.reservevaccine_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class list_reserve_page extends AppCompatActivity {
    String Username;
    BottomNavigationView bottomNavigationView;
    List<String> Key_Reserve_Id = new ArrayList<>();
    List<String> Show_Status_wait = new ArrayList<>();
    List<String> Show_Status_completed = new ArrayList<>();

    String status ;
    String reserve_date;
    String details ;
    String id ;

    String Reserve_Id;
    Button btn_payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_reserve_page);
        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");




        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.ReserveCart);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.ReserveCart:
                        return true;

                    case R.id.Home:
                        Intent intent = new Intent(list_reserve_page.this, home_page.class);
                        intent.putExtra("Username", Username);
                        startActivity(intent);
                        return true;

                    case R.id.viewAppointment:
                        Intent intent1 = new Intent(list_reserve_page.this, view_appointment_page.class);
                        intent1.putExtra("Username", Username);
                        startActivity(intent1);
                        return true;
                }

                return false;
            }
        });



        TextView view_wait = findViewById(R.id.view_wait);
        TextView view_completed = findViewById(R.id.view_completed);

        view_wait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(list_reserve_page.this,"หน้า รอชำระเงิน",Toast.LENGTH_LONG).show();
            }
        });



        view_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(list_reserve_page.this,"หน้า ชำระเงินเสร็จแล้ว",Toast.LENGTH_LONG).show();
            }
        });


        LinearLayout list_reserve_LinearLayout = findViewById(R.id.list_reserve_show);
        list_reserve_LinearLayout.removeAllViews();


        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_list_reserve = database.getReference("Login/"+Username+"/Reserve/Reserve_List");
        Query query1 = myRef_list_reserve.orderByKey();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    View list_reserve = getLayoutInflater().inflate(R.layout.list_reserve_linearlayout, null);
                    Key_Reserve_Id.add(ds.getKey());
                    Log.e("Key_Reserve_Id ",Key_Reserve_Id.toString());

                    DatabaseReference myRef_Reserve_List = database.getReference("Login/" + Username + "/Reserve/Reserve_List");
                    Query query2 = myRef_Reserve_List.orderByKey().equalTo(ds.getKey());
                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                Log.e("Reserve_List ", ds.getValue().toString());
                                status = ds.child("status").getValue().toString();
                                reserve_date = ds.child("reserve_date").getValue().toString();
                                details = ds.child("details").getValue().toString();
                                id = ds.child("reserve_id").getValue().toString();

                                if(status.equals("รอยืนยันชำระเงิน")){
                                    Show_Status_wait.add(ds.getKey());
                                }else {
                                    Show_Status_completed.add(ds.getKey());
                                }


                                TextView reserve_Id_view = list_reserve.findViewById(R.id.id_view);
                                TextView txt_status_view = list_reserve.findViewById(R.id.status_view);


                                reserve_Id_view.setText(id);
                                if(status.equals("รอยืนยันชำระเงิน")){
                                    txt_status_view.setText(status);
                                    txt_status_view.setTextColor(Color.parseColor("#DC143C"));
                                }else {
                                    txt_status_view.setText(status);
                                    txt_status_view.setTextColor(Color.parseColor("#32CD32"));
                                }


                                TextView vaccine_name = list_reserve.findViewById(R.id.vaccine_view);
                                vaccine_name.setText(details);



                                DatabaseReference Reserve_Deatails = database.getReference("Login/" + Username + "/Reserve/Reserve_List/"+ds.getKey());
                                Query query3 = Reserve_Deatails.orderByKey().equalTo("Reserve_Deatails");
                                query3.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot ds : snapshot.getChildren()){
                                            Log.e("Reserve_Deatails ",ds.getValue().toString());
                                            String vaccine_no = ds.child("vaccine_no").getValue().toString();
                                            String Key_Reserve_Id = ds.child("reserve_deatails_id").getValue().toString();

                                            String text_vaccine_no;
                                            text_vaccine_no = vaccine_name.getText().toString();
                                            vaccine_name.setText(text_vaccine_no+" "+vaccine_no);

                                            btn_payment =  list_reserve.findViewById(R.id.btn_payment);
                                            btn_payment.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent intent = new Intent(list_reserve_page.this, payment_page.class);
                                                    intent.putExtra("Username", Username);
                                                    intent.putExtra("Key_Reserve_Id",Key_Reserve_Id);
                                                    startActivity(intent);
                                                }
                                            });

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });



                            }//for


                            list_reserve_LinearLayout.addView(list_reserve);


                        }//Data snapshot

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



    }//oncreate


    public void ClickTOPayment(View view){
        Intent intent = new Intent(list_reserve_page.this, payment_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);

    }


}//clsss
