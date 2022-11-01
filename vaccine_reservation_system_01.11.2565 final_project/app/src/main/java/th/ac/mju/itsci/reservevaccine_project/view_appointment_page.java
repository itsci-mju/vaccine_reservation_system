package th.ac.mju.itsci.reservevaccine_project;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class view_appointment_page extends AppCompatActivity {

    private static final String TAG = "";
    String Username;

    String date1;
    String time1;

    String date2;
    String time2;

    String Reserve_id;
    String vaccine_Name;
    String Count_vaccine_reserve;
    String key_id_reserve;
    String queue_member;
    String location = "โรงพยาบาลเชียงใหม่";
    BottomNavigationView bottomNavigationView;

    Button take;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] permissionstorage = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_appointment_page);

        take = findViewById(R.id.btn_saveAppointment_pic);
        // check or ask for storage permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE )
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }else{
            //do nothing at the moment
        }


        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");
        key_id_reserve = intent.getStringExtra("key_id_reserve");
        queue_member = intent.getStringExtra("queue_member");

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.ReserveCart);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.ReserveCart:
                        Intent intent = new Intent(view_appointment_page.this,list_reserve_page.class);
                        intent.putExtra("Username", Username);
                        startActivity(intent);
                        return true;

                    case R.id.Home:
                        Intent intent1 = new Intent(view_appointment_page.this, home_page.class);
                        intent1.putExtra("Username", Username);
                        startActivity(intent1);
                        return true;

                    case R.id.ReserveVaccine:
                        Intent intent2 = new Intent(view_appointment_page.this, reserve_page.class);
                        startActivity(intent2);
                        return true;
                }

                return false;
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference My_Vaccine_no = database.getReference("Login/"+Username+"/Reserve/Reserve_List/"+key_id_reserve);
        Query query = My_Vaccine_no.orderByKey().equalTo("Reserve_Deatails");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Count_vaccine_reserve = ds.child("vaccine_no").getValue().toString();
                }

                Log.e("Count_vaccine_reserve ",Count_vaccine_reserve);
                if(Count_vaccine_reserve.equals("1 เข็ม")){
                    DatabaseReference myRef = database.getReference("Login/"+Username+"/Reserve/Reserve_List/"+key_id_reserve+"/Reserve_Deatails/Appointment");
                    Query query1 = myRef.orderByKey().equalTo("Appointment_1");
                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                date1 = ds.child("apm_date").getValue().toString();
                                time1 = ds.child("apm_time").getValue().toString();
                                Log.e("date1",date1);
                                Log.e("time1",time1);


                                TextView txt_date1 = findViewById(R.id.txt_date1);
                                TextView txt_time1 = findViewById(R.id.txt_time1);

                                txt_date1.setText(date1);
                                txt_time1.setText(time1);



                            }

                            DatabaseReference myRef_vaccine_name = database.getReference("Login/"+Username+"/Reserve/Reserve_List");
                            Query query3 = myRef_vaccine_name.orderByKey().equalTo(key_id_reserve);
                            query3.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot ds : snapshot.getChildren()){
                                        vaccine_Name = ds.child("details").getValue().toString();
                                        Reserve_id = ds.child("reserve_id").getValue().toString();

                                        Log.e("vaccine_Name",vaccine_Name);

                                        TextView show_resere = findViewById(R.id.show_resere);
                                        show_resere.setText("เลขที่ใบสั่งจองวัคซีน "+Reserve_id);

                                        TextView txt_vaccine_name1 = findViewById(R.id.txt_vaccine_name1);
                                        txt_vaccine_name1.setText(vaccine_Name);

                                        TextView txt_location1 = findViewById(R.id.txt_location1);
                                        txt_location1.setText(location);



                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else {
                    DatabaseReference myRef = database.getReference("Login/"+Username+"/Reserve/Reserve_List/"+key_id_reserve+"/Reserve_Deatails/Appointment");
                    Query query1 = myRef.orderByKey().equalTo("Appointment_1");
                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()) {
                                date1 = ds.child("apm_date").getValue().toString();
                                time1 = ds.child("apm_time").getValue().toString();
                                Log.e("date1", date1);
                                Log.e("time1", time1);


                                TextView txt_date1 = findViewById(R.id.txt_date1);
                                TextView txt_time1 = findViewById(R.id.txt_time1);

                                txt_date1.setText(date1);
                                txt_time1.setText(time1);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    Query query2 = myRef.orderByKey().equalTo("Appointment_2");
                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                date2 = ds.child("apm_date").getValue().toString();
                                time2 = ds.child("apm_time").getValue().toString();
                                Log.e("date2",date2);
                                Log.e("time2",time2);


                                TextView txt_date2 = findViewById(R.id.txt_date2);
                                TextView txt_time2 = findViewById(R.id.txt_time2);



                                txt_date2.setText(date2);
                                txt_time2.setText(time2);

                                TextView text_appointment2_show = findViewById(R.id.text_appointment2_show);
                                TextView txt_paymentid = findViewById(R.id.txt_paymentid);
                                //TextView txt_date2 = findViewById(R.id.txt_date2);
                                TextView textView16 = findViewById(R.id.textView16);
                                //TextView txt_time2 = findViewById(R.id.txt_time2);
                                TextView txt_vaccinename2 = findViewById(R.id.txt_vaccinename2);

                                TextView textView25 = findViewById(R.id.textView25);
                                TextView txt_location2 = findViewById(R.id.txt_location2);


                                text_appointment2_show.setText("ใบนัดหมายฉีดวัคซีน เข็มที่ 2");
                                txt_paymentid.setText("วันที่นัดหมาย :");
                                //txt_date2.setText("");
                                textView16.setText("เวลาที่นัด :");
                                //txt_time2.setText("");
                                txt_vaccinename2.setText("วัคซีน :");

                                textView25.setText("สถานที่ :");
                                txt_location2.setText(location);

                                TextView txt_vaccine_name2 = findViewById(R.id.txt_vaccine_name2);
                                txt_vaccine_name2.setText(vaccine_Name);

                            }

                            DatabaseReference myRef_vaccine_name = database.getReference("Login/"+Username+"/Reserve/Reserve_List");
                            Query query3 = myRef_vaccine_name.orderByKey().equalTo(key_id_reserve);
                            query3.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot ds : snapshot.getChildren()){
                                        vaccine_Name = ds.child("details").getValue().toString();
                                        Reserve_id = ds.child("reserve_id").getValue().toString();

                                        Log.e("vaccine_Name",vaccine_Name);

                                        TextView show_resere = findViewById(R.id.show_resere);
                                        show_resere.setText("หมายเลขคิวที่ "+queue_member);

                                        TextView txt_vaccine_name1 = findViewById(R.id.txt_vaccine_name1);
                                        txt_vaccine_name1.setText(vaccine_Name);

                                        TextView txt_location1 = findViewById(R.id.txt_location1);
                                        txt_location1.setText(location);


                                        TextView txt_vaccine_name2 = findViewById(R.id.txt_vaccine_name2);
                                        txt_vaccine_name2.setText(vaccine_Name);

                                        TextView txt_location2 = findViewById(R.id.txt_location2);
                                        txt_location2.setText(location);



                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

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

        DatabaseReference myRef_name_idcard = database.getReference("Login/"+Username+"/Reserve");
        Query query4 = myRef_name_idcard.orderByKey().equalTo("Member");
        query4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {

                    String firstname = ds.child("firstname").getValue().toString();
                    String lastname = ds.child("lastname").getValue().toString();
                    String idcard = ds.child("idcard").getValue().toString();

                    TextView Tv_name = findViewById(R.id.textView67);
                    Tv_name.setText("คุณ "+" "+firstname+" "+lastname);

                    TextView Tv_idcard = findViewById(R.id.textView72);
                    Tv_idcard.setText(idcard);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        Button button = findViewById(R.id.btn_savePic);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //takeScreenshot();
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(view_appointment_page.this);
//                builder.setTitle("คำความเตือน");
//                builder.setMessage("บันทึกรูปภาพสำเร็จแล้ว");
//                builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        Intent intent = new Intent(view_appointment_page.this, view_appointment_page.class);
//                        intent.putExtra("Username", Username);
//                    }
//                });
//                AlertDialog alert = builder.create();
//                alert.show();
//
//
//
//            }
//        });


        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pass layout view to method to perform capture screenshot and save it.

                AlertDialog.Builder builder = new AlertDialog.Builder(view_appointment_page.this);
                builder.setTitle("คำความเตือน");
                builder.setMessage("บันทึกรูปภาพสำเร็จแล้ว");
                builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(view_appointment_page.this, home_page.class);
                        intent.putExtra("Username", Username);
                        startActivity(intent);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


                View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
                Bitmap bitmap = getScreenShot(rootView);
                store(bitmap,"ScreenShot.png");
            }
        });






    }//on create

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1000){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(view_appointment_page.this,"Permissions grant!",Toast.LENGTH_SHORT).show();
                //do nothing at moment
            }else {
                Toast.makeText(view_appointment_page.this,"Permissions not grant!",Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }

    //get screen shot of the app
    public static Bitmap getScreenShot(View view){
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    // store the image on the device
    public void store(Bitmap bm,String filename){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyAppointment";
        File dir = new File(dirPath);
        if(!dir.exists()){
            dir.mkdirs();
        }

        File file = new File(dirPath,filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG,100,fos);
            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,"Error!",Toast.LENGTH_SHORT).show();
        }
    }
    


    public  void  goHome(View view){
        Intent intent = new Intent(view_appointment_page.this, home_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);
    }



}
