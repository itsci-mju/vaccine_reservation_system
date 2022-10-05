package th.ac.mju.itsci.reservevaccine_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class list_vaccine_lots_page extends AppCompatActivity {
    String Username;
    String vaccine_name_Lot;
    String company;
    String date_ins;
    String vaccine_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_vaccine_lots_page);

        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");
        vaccine_name_Lot = intent.getStringExtra("VaccineName");
        Log.e("ส่งค่าวัคซีนที่ 1 ",vaccine_name_Lot);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_lot_vaccine = database.getReference("Login/"+Username+"/Vaccine/"+vaccine_name_Lot);
        DatabaseReference myRef_vaccine_check = database.getReference("Login/"+Username+"/Vaccine");

        Query query_ck = myRef_vaccine_check.orderByKey().equalTo(vaccine_name_Lot);
        query_ck.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                     company = ds.child("company").getValue().toString();
                     date_ins = ds.child("date_in").getValue().toString();
                     vaccine_name = ds.child("vaccineName").getValue().toString();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        LinearLayout list_Lot_vaccine = findViewById(R.id.list_Lot_Vaccine_show);
        list_Lot_vaccine.removeAllViews();

        Query query = myRef_lot_vaccine.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String id = null;
                String date_in = null;
                String amount = null;
                for (DataSnapshot ds : snapshot.getChildren()) {

                    if (!ds.getValue().toString().equals(company)) {
                        if (!ds.getValue().toString().equals(date_ins)) {
                            if (!ds.getValue().toString().equals(vaccine_name)) {
                                Log.e("ค่าหลัง IF", ds.getValue().toString());
                                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx     "+company);
                                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx     "+date_ins);
                                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx     "+vaccine_name);
                                View vaccine_lots = getLayoutInflater().inflate(R.layout.vaccine_lot_items, null);

                                TextView vaccine_lotId_view = vaccine_lots.findViewById(R.id.Lot_Id);
                                TextView vaccine_dateIn_view = vaccine_lots.findViewById(R.id.Lot_date_In);
                                TextView amount_view = vaccine_lots.findViewById(R.id.Lot_amount);

                                TextView vaccine_name_view = findViewById(R.id.txt_vaccine_name);

                                id = ds.child("id").getValue().toString();
                                date_in = ds.child("date_in").getValue().toString();
                                amount = ds.child("amount").getValue().toString();


                                vaccine_lotId_view.setText(id);
                                vaccine_dateIn_view.setText(date_in);
                                amount_view.setText(amount + " โดส");


                                list_Lot_vaccine.addView(vaccine_lots);
                                vaccine_name_view.setText("วันซีน " + vaccine_name_Lot);

                            } else {
                                Log.e("ค่าที่ไม่ต้องให้แสดงออกมา ",ds.getValue().toString());
                            }
/*                    String lot_id = ds.child("id").getValue().toString();
                    String amount = ds.child("amount").getValue().toString();
                    String date_in = ds.child("date_in").getValue().toString();
                    String date_exp = ds.child("exp_date").getValue().toString();*/

                        }
                    }

                    Button btn_scheule_lots = findViewById(R.id.btn_scheule_lots);
                    btn_scheule_lots.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(list_vaccine_lots_page.this, scheule_lots_page.class);
                            intent.putExtra("Username", Username);
                            startActivity(intent);
                        }
                    });


                }//for
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }






}