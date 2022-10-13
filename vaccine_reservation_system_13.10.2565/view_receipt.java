package th.ac.mju.itsci.reservevaccine_project;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
     String queue;
     BottomNavigationView bottomNavigationView;

     Button take;
     private static final int REQUEST_EXTERNAL_STORAGE = 1;
     private static String[] permissionstorage = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.view_receipt);

            Intent intent = getIntent();
            Username = intent.getStringExtra("Username");

            take = findViewById(R.id.btn_saverecipt);
             // check or ask for storage permission
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE )
                    != PackageManager.PERMISSION_GRANTED){
               requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
            }else{
                //do nothing at the moment
            }


            take.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //pass layout view to method to perform capture screenshot and save it.

                    AlertDialog.Builder builder = new AlertDialog.Builder(view_receipt.this);
                    builder.setTitle("คำความเตือน");
                    builder.setMessage("บันทึกรูปภาพสำเร็จแล้ว");
                    builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent intent = new Intent(view_receipt.this, list_reserve_page.class);
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

             FirebaseApp.initializeApp(this);
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
             queue = intent.getStringExtra("queue");
            TextView txt_queue = findViewById(R.id.txt_queue);
            TextView txt_Name = findViewById(R.id.txt_Name);
            TextView txt_vaccinename = findViewById(R.id.txt_vaccinename);
            TextView txt_VaccineNo = findViewById(R.id.txt_VaccineNo);
            TextView txt_totalPrice = findViewById(R.id.txt_reserveId);
            TextView txt_pricedate = findViewById(R.id.txt_pricedate);
            TextView recipt_status = findViewById(R.id.recipt_status);

            TextView showTitle = findViewById(R.id.show_receipt);
            showTitle.setText("ใบเสร็จรับเงิน");

            TextView txt_vaccine_reserve = findViewById(R.id.txt_vaccine_reserve);
            txt_vaccine_reserve.setText("วัคซีน "+vaccine_Name+" "+vaccine_No+" "+"เข็ม");
            txt_queue.setText(reserve_Id);
            txt_Name.setText(name_lastname);
            //txt_vaccinename.setText(vaccine_Name);
            txt_VaccineNo.setText("โอนเงินผ่านธนาคาร");
            txt_totalPrice.setText(totalprice+" บาท");
            txt_pricedate.setText(payment_date+" "+time);
            recipt_status.setText(queue);




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

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if(requestCode == 1000){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(view_receipt.this,"Permissions grant!",Toast.LENGTH_SHORT).show();
                    //do nothing at moment
                }else {
                    Toast.makeText(view_receipt.this,"Permissions not grant!",Toast.LENGTH_SHORT).show();
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
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyFiles";
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
        Intent intent = new Intent(view_receipt.this, home_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);
    }

}