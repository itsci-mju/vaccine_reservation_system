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
import android.view.View;
import android.widget.Button;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class view_reserve_page extends AppCompatActivity {

     String Username;
     String reserve_id ;
     Button btn_saveReserve;
     Button btn_list_reserve;
    int day_payment ;
    int Month_payment ;
    int year_payment = 2565 ;
    String Date_reserve_subString;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] permissionstorage = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_reserve_page);
        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");
        reserve_id = intent.getStringExtra("reserve_id_intent");


        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        String day = checklength(String.valueOf(mDay));
        String months = checklength(String.valueOf(mMonth+1));


        int days = 31;
        if(months == "4" || months == "6" || months == "9" || months == "11"){
            days = 30 ;
        }

        day_payment = mDay+7;
        Month_payment = 0 ;
//        year_payment = mYear;

        if(day_payment > days){
            day_payment = day_payment - days;
            Month_payment = mMonth+2;
            if(Month_payment > 12){
                year_payment =  2566;
            }
        }else {
            Month_payment = mMonth+1;
        }


        btn_saveReserve = findViewById(R.id.btn_saveReserve);
        // check or ask for storage permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE )
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }else{
            //do nothing at the moment
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_list_reserve = database.getReference("Login/"+Username+"/Reserve/Reserve_List");
        Query query1 = myRef_list_reserve.orderByKey().equalTo(reserve_id);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    Log.e("ค่าที่คาดหวัง",ds.getValue().toString());
                    String status = ds.child("status").getValue().toString();
                    String reserve_date = ds.child("reserve_date").getValue().toString();
                    String details = ds.child("details").getValue().toString();
                    String id = ds.child("reserve_id").getValue().toString();



                    TextView reserve_Id_view = findViewById(R.id.txt_reserve_Id);
                    TextView reserve_date_view = findViewById(R.id.txt_date_reserve);
                    TextView vaccine_name = findViewById(R.id.txt_Vaccine_name);
                    TextView txt_status_view = findViewById(R.id.txt_status);


                    reserve_Id_view.setText(id);
                    reserve_date_view.setText(reserve_date);
                    vaccine_name.setText(details);
                    txt_status_view.setText(status);

                    Date_reserve_subString = reserve_date.substring(0,2);
                    Log.e("Date_reserve_subString ",Date_reserve_subString);


                    Log.e("day_payment ",String.valueOf(day_payment));
                    Log.e("Month_payment ",String.valueOf(Month_payment));
                    Log.e("year_payment ",String.valueOf(year_payment));



                    try {
                        Date d_reserve = new SimpleDateFormat("dd-MM-yyyy").parse(reserve_date);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM",new Locale("th", "TH"));
                        String fmt_reservedate = simpleDateFormat.format(d_reserve);

                        TextView txt_note = findViewById(R.id.txt_note);
                        txt_note.setText("ตั้งแต่ วันที่ "+Date_reserve_subString+" - "+day_payment+" "+fmt_reservedate+" "+"พ.ศ. "+year_payment);



                        System.out.println(fmt_reservedate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }

//                Format formatter;
//                formatter = new SimpleDateFormat("MMM", new Locale("th", "TH"));
//


                //วันจันทร์ ที่ 12 เดือน พฤศจิกายน พ.ศ. 2555
                // MM == พ.ย.


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference Reserve_Deatails = database.getReference("Login/" + Username + "/Reserve/Reserve_List/"+reserve_id);
        Query query2 = Reserve_Deatails.orderByKey().equalTo("Reserve_Deatails");
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    String vaccine_no = ds.child("vaccine_no").getValue().toString();
                    TextView number_vaccine_view = findViewById(R.id.txt_number_vaccine);
                    number_vaccine_view.setText(vaccine_no);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        btn_saveReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view_reserve_page.this);
                builder.setTitle("คำความเตือน");
                builder.setMessage("บันทึกรูปภาพสำเร็จแล้ว");
                builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(view_reserve_page.this, view_reserve_page.class);
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



        btn_list_reserve = findViewById(R.id.btn_list_reserve);
        btn_list_reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view_reserve_page.this, list_reserve_page.class);
                intent.putExtra("Username", Username);
                startActivity(intent);
            }
        });



    }//onCreate

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
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyReserve";
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1000){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(view_reserve_page.this,"Permissions grant!",Toast.LENGTH_SHORT).show();
                //do nothing at moment
            }else {
                Toast.makeText(view_reserve_page.this,"Permissions not grant!",Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }


    public  String checklength(String s){
        if (s.length()<2){
            s = "0"+s;
        }
        return s;
    }



}//public class view_reserve_page