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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class view_receipt extends AppCompatActivity {

     private static final String TAG = "";
     String Username;
     String reserve_Id;
     String name_lastname;
     String vaccine_Name;
     String vaccine_No;
     String totalprice;
     String payment_id;
     String payment_date;
     String payment_status;
     String time;
     BottomNavigationView bottomNavigationView;

     Button take;
     private static final int REQUEST_EXTERNAL_STORAGE = 1;
     private static String[] permissionstorage = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_receipt);

        take = findViewById(R.id.btn_saverecipt);
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
         reserve_Id = intent.getStringExtra("reserve_Id");
         name_lastname = intent.getStringExtra("name_lastname");
         vaccine_Name = intent.getStringExtra("vaccine_name");
         vaccine_No = intent.getStringExtra("vaccine_no");
         totalprice = intent.getStringExtra("totalprice");
         payment_date = intent.getStringExtra("payment_date");
         payment_status = intent.getStringExtra("payment_status");
         payment_id = intent.getStringExtra("payment_Id");
         time = intent.getStringExtra("time");

        TextView txt_queue = findViewById(R.id.txt_queue);
        TextView txt_Name = findViewById(R.id.txt_Name);
        TextView txt_vaccinename = findViewById(R.id.txt_vaccinename);
        TextView txt_VaccineNo = findViewById(R.id.txt_VaccineNo);
        TextView txt_totalPrice = findViewById(R.id.txt_reserveId);
        TextView txt_pricedate = findViewById(R.id.txt_pricedate);
        TextView recipt_status = findViewById(R.id.recipt_status);

        TextView showTitle = findViewById(R.id.show_receipt);
        showTitle.setText("ชำระค่าวัคซีน " + vaccine_Name + " สำเร็จแล้ว");

        TextView txt_vaccine_reserve = findViewById(R.id.txt_vaccine_reserve);
        txt_vaccine_reserve.setText("วัคซีน "+vaccine_Name+" "+vaccine_No+" "+"เข็ม");
        txt_queue.setText(payment_id);
        txt_Name.setText(name_lastname);
        //txt_vaccinename.setText(vaccine_Name);
        txt_VaccineNo.setText("Mobile Banking");
        txt_totalPrice.setText(totalprice+" บาท");
        txt_pricedate.setText(payment_date+" "+time);
        recipt_status.setText(reserve_Id);




//        bottomNavigationView = findViewById(R.id.bottom_navigator);
//        bottomNavigationView.setSelectedItemId(R.id.Receipt);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId())
//                {
//                    case R.id.Receipt:
//                        return true;
//
//                    case R.id.Home:
//                        Intent intent = new Intent(view_receipt.this, home_page.class);
//                        intent.putExtra("Username", Username);
//                        startActivity(intent);
//                        return true;
//
//                    case R.id.ReserveCart:
//                        Intent intent1 = new Intent(view_receipt.this, list_reserve_page.class);
//                        intent1.putExtra("Username", Username);
//                        startActivity(intent1);
//                        return true;
//                }
//
//                return false;
//            }
//        });



    }//oncreate

    protected File takeScreenshot(View view) {
        Date date = new Date();
        try {
            String dirpath;
            // Initialising the directory of storage
            dirpath= view_receipt.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() ;
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
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            Log.d(TAG, "takeScreenshot Path: "+imageurl);
            Toast.makeText(view_receipt.this,""+imageurl,Toast.LENGTH_LONG).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(view_receipt.this);
            builder.setTitle("คำความเตือน");
            builder.setMessage("บันทึกรูปภาพสำเร็จแล้ว");
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent intent = new Intent(view_receipt.this, view_appointment_page.class);
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
        Intent intent = new Intent(view_receipt.this, home_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);
    }

}