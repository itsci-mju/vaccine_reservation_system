package th.ac.mju.itsci.reservevaccine_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

public class list_reserve_page extends AppCompatActivity {
    String Username;
    BottomNavigationView bottomNavigationView;
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

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_list_reserve = database.getReference("Login/"+Username);

        Query query1 = myRef_list_reserve.orderByKey().equalTo("Reserve");
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    Log.e("ค่าที่คาดหวัง",ds.getValue().toString());
                    String status = ds.child("status").getValue().toString();
                    String reserve_date = ds.child("reserve_date").getValue().toString();
                    String details = ds.child("details").getValue().toString();
                    String queue = ds.child("queue").getValue().toString();

                    TextView status_view = findViewById(R.id.txt_pricedate);
                    TextView reserve_date_view = findViewById(R.id.txt_Name);
                    TextView vaccine_name = findViewById(R.id.txt_phone);
                    TextView queue_view = findViewById(R.id.txt_queue);

                    status_view.setText(status);
                    reserve_date_view.setText(reserve_date);
                    vaccine_name.setText(details);
                    queue_view.setText(queue);

                }///for
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
                DatabaseReference myRef_reserve_deatails = database.getReference("Login/"+Username+"/"+"Reserve");
                Query query = myRef_reserve_deatails.orderByKey().equalTo("Reserve_Deatails");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()){
                            String vaccine_no = ds.child("vaccine_no").getValue().toString();
                            TextView details_vaccine_no = findViewById(R.id.txt_reserveId);
                            details_vaccine_no.setText(vaccine_no);

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });//datachange2

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });//datachange1

    }//oncreate


    public void ClickTOPayment(View view){
        Intent intent = new Intent(list_reserve_page.this, payment_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);

    }


}