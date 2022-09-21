package th.ac.mju.itsci.reservevaccine_project;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import androidx.core.app.ActivityCompat;

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
import java.util.Date;


public class view_appointment_page extends AppCompatActivity {

    private static final String TAG = "";
    String Username;

    String date1;
    String time1;

    String date2;
    String time2;

    String vaccine_Name;
    String location = "โรงพยาบาลเชียงใหม่";
    BottomNavigationView bottomNavigationView;

    Button take;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] permissionstorage = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_appointment_page);

        take = findViewById(R.id.btn_savePic);
        checkpermissions(this); // check or ask for storage permission
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pass layout view to method to perform capture screenshot and save it.
                takeScreenshot(getWindow().getDecorView().getRootView());
            }
        });


        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.bottom_navigator);

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

                    case R.id.viewAppointment:
                        return true;
                }

                return false;
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Login/"+Username+"/Reserve/Reserve_Deatails/Appointment");

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

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DatabaseReference myRef_vaccine_name = database.getReference("Login/"+Username);
        Query query3 = myRef_vaccine_name.orderByKey().equalTo("Reserve");
        query3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    vaccine_Name = ds.child("details").getValue().toString();

                    Log.e("vaccine_Name",vaccine_Name);



                    TextView txt_vaccine_name1 = findViewById(R.id.txt_vaccine_name1);
                    txt_vaccine_name1.setText(vaccine_Name);

                    TextView txt_vaccine_name2 = findViewById(R.id.txt_vaccine_name2);
                    txt_vaccine_name2.setText(vaccine_Name);

                    TextView txt_location1 = findViewById(R.id.txt_location1);
                    txt_location1.setText(location);

                    TextView txt_location2 = findViewById(R.id.txt_location2);
                    txt_location2.setText(location);
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





    }//on create

    protected File takeScreenshot(View view) {
        Date date = new Date();
        try {
            String dirpath;
            // Initialising the directory of storage
            dirpath= view_appointment_page.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() ;
            File file = new File(dirpath);
            if (!file.exists()) {
                boolean mkdir = file.mkdir();
            }
            // File name : keeping file name unique using data time.
            String path = dirpath + "/"+ date.getTime() + ".jpeg";
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
            File imageurl = new File(path);
            FileOutputStream outputStream = new FileOutputStream(imageurl);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
            outputStream.flush();
            outputStream.close();
            Log.d(TAG, "takeScreenshot Path: "+imageurl);
            Toast.makeText(view_appointment_page.this,""+imageurl,Toast.LENGTH_LONG).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(view_appointment_page.this);
            builder.setTitle("คำความเตือน");
            builder.setMessage("บันทึกรูปภาพสำเร็จแล้ว");
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent intent = new Intent(view_appointment_page.this, view_appointment_page.class);
                    intent.putExtra("Username", Username);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

            return imageurl;
        } catch (FileNotFoundException io) {
            io.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    // check weather storage permission is given or not
    public static void checkpermissions(Activity activity) {
        int permissions = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // If storage permission is not given then request for External Storage Permission
        if (permissions != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, permissionstorage, REQUEST_EXTERNAL_STORAGE);
        }
    }
    


    public  void  goHome(View view){
        Intent intent = new Intent(view_appointment_page.this, home_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);
    }



}
