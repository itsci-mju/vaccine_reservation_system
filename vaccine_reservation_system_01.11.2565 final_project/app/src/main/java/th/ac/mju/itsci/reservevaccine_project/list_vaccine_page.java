package th.ac.mju.itsci.reservevaccine_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

public class list_vaccine_page extends AppCompatActivity {
    String Username;
    int check_delete = 0;
    List<String> VaccineList = new ArrayList<>();
    String vaccineName ;

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_vaccine_page);

        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");


        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.Vaccine_list);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.Vaccine_list:
                        return true;

                    case R.id.admin_menu_page:
                        Intent intent2 = new Intent(list_vaccine_page.this, admin_manu_page.class);
                        intent2.putExtra("Username", Username);
                        startActivity(intent2);
                        return true;

                    case R.id.schedule_appointment:
                        Intent intent = new Intent(list_vaccine_page.this, schedule_appointment_page.class);
                        intent.putExtra("Username", Username);
                        startActivity(intent);
                        return true;
                }

                return false;
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_list_vaccine = database.getReference("Login/"+Username+"/Vaccine");


        LinearLayout listvaccine = findViewById(R.id.listVaccine_show);
        listvaccine.removeAllViews();

        Log.e("ค้นหาข้อมูลยังไปไม่ถึง",Username);
        Query query = myRef_list_vaccine.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){

                    String str_key = ds.getKey();
                    VaccineList.add(str_key);
                    Log.e("ค่าที่วัคซีนที่ได้",ds.getValue().toString());

                    String vaccine_name = ds.child("vaccineName").getValue().toString();
                    String vaccine_dateIn = ds.child("date_in").getValue().toString();
                    String conpany = ds.child("imported_company").getValue().toString();

                    View vaccine_item = getLayoutInflater().inflate(R.layout.vacccine_item,null);
                    TextView vaccine_name_view = vaccine_item.findViewById(R.id.VaccineName);
                    TextView vaccine_dateIn_view = vaccine_item.findViewById(R.id.Vac_Date_Input);
                    TextView conpany_view = vaccine_item.findViewById(R.id.Company);


                    vaccine_name_view.setText(vaccine_name);
                    vaccine_dateIn_view.setText(vaccine_dateIn);
                    conpany_view.setText(conpany);

                    listvaccine.addView(vaccine_item);

                    ImageView btn_del = vaccine_item.findViewById(R.id.txtdel);

                    btn_del.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(list_vaccine_page.this);
                            builder.setTitle("คำแจ้งตือน");
                            builder.setMessage("คุณเเน่ใจหรอว่าต้องการลบ ?");
                            builder.setPositiveButton("ฉันเเน่ใจ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DatabaseReference myRef = database.getReference("Login/"+Username+"/Vaccine");
                                    Log.e("VNAME = ",vaccine_name);
                                    DatabaseReference v = myRef.child(vaccine_name);
                                    v.removeValue();

                                    Toast.makeText(list_vaccine_page.this,"ระบบได้ทำการลบข้อมูลสำเร็จเรียบร้อยแล้ว",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(list_vaccine_page.this, admin_manu_page.class);
                                    intent.putExtra("Username", Username);
                                    startActivity(intent);
                                }
                            });

                            builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });

                            AlertDialog alert = builder.create();
                            alert.show();
                        }


                    });//btn_del

                    ImageView btn_edit = vaccine_item.findViewById(R.id.txtedit);
                    btn_edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(list_vaccine_page.this, edit_vaccine_page.class);
                            vaccineName = vaccine_name;
                            intent.putExtra("VaccineName",vaccineName);
                            intent.putExtra("Username", Username);
                            startActivity(intent);
                        }
                    });

                    ImageView img_vaccineLots_page = vaccine_item.findViewById(R.id.vaccine_lots);
                    img_vaccineLots_page.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(list_vaccine_page.this, list_vaccine_lots_page.class);
                            vaccineName = vaccine_name;
                            intent.putExtra("VaccineName",vaccineName);
                            intent.putExtra("Username", Username);
                            startActivity(intent);
                        }
                    });


//                    LinearLayout layout = findViewById(R.id.layoutvaccine_name);
//                    layout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(list_vaccine_page.this, list_vaccine_lots_page.class);
//                            vaccineName = vaccine_name;
//                            intent.putExtra("VaccineName",vaccineName);
//                            intent.putExtra("Username", Username);
//                            startActivity(intent);
//                        }
//                    });


                } ///for

                    Log.e("VaccineList ",VaccineList.toString());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }//OnCreate




}//public