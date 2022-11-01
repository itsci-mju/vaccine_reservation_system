package th.ac.mju.itsci.reservevaccine_project;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class payment_page extends AppCompatActivity {
    String Username;
    String Key_Reserve_Id;
    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private EditText mName, mDetail, mPrice;
    private ImageView mImageView;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;

    private String reserve_Id;
    private String name_lastname;
    private String vaccine_Name;
    private String vaccine_no;
    private String totalprice;
    private String payment_id;
    private String payment_date;
    private String payment_status;
    private String time;
    private int queue;
    String vaccine_unit;
    private String Date_to_day;
    private String Time_to_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_page);

        mButtonChooseImage = findViewById(R.id.btn_choosefile);
        mButtonUpload = findViewById(R.id.btn_saverecipt);
        mImageView = findViewById(R.id.image_view);
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");



        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");
        Key_Reserve_Id = intent.getStringExtra("Key_Reserve_Id");
        Log.e("Key_Reserve_Id _ PAYMENT ",Key_Reserve_Id);


        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        TextView txt_name_lastname  = findViewById(R.id.txt_name_lastname);
        TextView txt_address = findViewById(R.id.txt_Name);
        TextView txt_phone = findViewById(R.id.txt_phone);

        DatabaseReference myRef_Member = database.getReference("Login/"+Username+"/"+"Reserve");
        Query query1 = myRef_Member.orderByKey().equalTo("Member");
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    String name =  ds.child("firstname").getValue().toString();
                    String lastname = ds.child("lastname").getValue().toString();
                    String address = ds.child("address").getValue().toString();
                    String phone = ds.child("phone").getValue().toString();

                    txt_name_lastname.setText(name+" "+lastname);
                    txt_address.setText(address);
                    txt_phone.setText(phone);

                    name_lastname = name + " " + lastname;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        TextView txt_id = findViewById(R.id.txt_reserveId);
        TextView txt_vaccineno = findViewById(R.id.txt_vaccineno);
        TextView txt_vaccineName = findViewById(R.id.text_vaccine);
        TextView txt_price = findViewById(R.id.txt_price);
        TextView txt_total = findViewById(R.id.txt_total);
        TextView txt_totalprice = findViewById(R.id.txt_totalprice);

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth=  c.get(Calendar.MONTH);
        int mDay= c.get(Calendar.DAY_OF_MONTH);
        String day =    checklength(String.valueOf(mDay));
        String months = checklength(String.valueOf(mMonth+1));

        TextView tv_date = findViewById(R.id.edit_date);
        tv_date.setText(day+"-"+months+"-"+mYear);
        payment_date = tv_date.getText().toString();

        Date_to_day = tv_date.getText().toString();


        TextView Time = findViewById(R.id.edit_date2);
        int  mHour= c.get(Calendar.HOUR_OF_DAY);
        int mMinute= c.get(Calendar.MINUTE);

        String hour = checklength(String.valueOf(mHour));
        String m =  checklength(String.valueOf(mMinute));

        Time.setText(hour+" : "+m+" น.");
        time = Time.getText().toString();


        DatabaseReference myRef_Reserve_List = database.getReference("Login/" + Username + "/Reserve/Reserve_List");
        Query query2 = myRef_Reserve_List.orderByKey().equalTo(Key_Reserve_Id);

        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){

                    String reserve_id = ds.child("reserve_id").getValue().toString();
                    String vaccine_name = ds.child("details").getValue().toString();
                    Log.e("reserve_id ",reserve_id);
                    Log.e("vaccine_name ",vaccine_name);
                    //String vaccine_name = ds.child("deatails").getValue().toString();

                    txt_id.setText(reserve_id);
                    txt_vaccineName.setText("วัคซีน "+vaccine_name);

                    reserve_Id = reserve_id;
                    vaccine_Name = vaccine_name;
                }//for query2

                FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
                DatabaseReference Reserve_Deatails = database.getReference("Login/" + Username + "/Reserve/Reserve_List/"+Key_Reserve_Id);
                Query query3 = Reserve_Deatails.orderByKey().equalTo("Reserve_Deatails");
                query3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {

                            String ck_vaccine = ds.child("vaccine_no").getValue().toString();
                            Log.e("vaccine_no ",ck_vaccine);
                            if (ck_vaccine.equals("1 เข็ม")) {
                                ck_vaccine = "1";
                                vaccine_unit = ck_vaccine;
                            } else {
                                ck_vaccine = "2";
                                vaccine_unit = ck_vaccine;
                            }
                            String sum_vaccineNo = String.valueOf(Integer.parseInt(ck_vaccine) * 1650);
                            int sum_totalprice = 0;
                            sum_totalprice = Integer.parseInt(sum_totalprice + sum_vaccineNo);
                            String txt_sum_totalprice = String.valueOf(sum_totalprice);

                            txt_price.setText(sum_vaccineNo);
                            txt_vaccineno.setText(ck_vaccine);
                            txt_total.setText(sum_vaccineNo);
                            txt_totalprice.setText(txt_sum_totalprice);

                            vaccine_no = ck_vaccine;
                            totalprice = txt_sum_totalprice;
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });//query3
            }//onDataChange2

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });//query2


        DatabaseReference myRef_payment_auto_id = database.getReference("Login/admin1234/Queue");
        myRef_payment_auto_id.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
           if(snapshot.exists()){
            queue = (int)(snapshot.getChildrenCount());
           }
           }
           @Override
           public void onCancelled(@NonNull DatabaseError error) {
           }

          }
        );


    }//on create

    public void openFileChooser(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) { mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(mImageView);
        }
    }

    public void uploadFile(View view) {
        if (mUploadTask != null && mUploadTask.isInProgress()) {
            Toast.makeText(payment_page.this, "Upload in progress", Toast.LENGTH_SHORT).show();
        }

        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 10000);
                    String URL = fileReference.toString();
                    String url = URL.substring(48);
                    System.out.println(url);


                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");


                    //FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
                    DatabaseReference myRef_payment = database.getReference("Login/"+Username+"/Reserve/Receipt/"+Key_Reserve_Id);



                    Log.e("queue ",String.valueOf(queue));
                    Receipt rt = new Receipt(Key_Reserve_Id,payment_date,"ชำระเงินเรียบร้อยแล้ว","https://firebasestorage.googleapis.com/v0/b/reservevaccine-project.appspot.com/o/uploads%2F"+ url +"?alt=media&token=",totalprice,String.valueOf(queue+1));
                    myRef_payment.setValue(rt);

                    final Calendar c = Calendar.getInstance();
                    int  mHour= c.get(Calendar.HOUR_OF_DAY);
                    int mMinute= c.get(Calendar.MINUTE);

                    String hour = checklength(String.valueOf(mHour));
                    String m =  checklength(String.valueOf(mMinute));

                    time = hour+" : "+m+" น.";


                    myRef_payment.child("receipt_date").setValue(Date_to_day);
                    myRef_payment.child("vaccine_unit").setValue(vaccine_unit);
                    myRef_payment.child("time").setValue(time);

                    payment_status = "ชำระเงินเรียบร้อยแล้ว";

                    DatabaseReference myRef_payment_auto_id = database.getReference("Login/admin1234/Queue/"+String.valueOf(queue+1)+"/"+Username);
                    myRef_payment_auto_id.setValue(rt);

                    DatabaseReference myRef_reserve_by_Id = database.getReference("Login/" + Username + "/Reserve/Reserve_List/"+Key_Reserve_Id);
                    myRef_reserve_by_Id.child("status").setValue("ชำระเงินเสร็จสิ้น");

                    Intent intent = new Intent(payment_page.this, view_receipt.class);
                    intent.putExtra("username", Username);
                    intent.putExtra("name_lastname", name_lastname);
                    intent.putExtra("vaccine_name", vaccine_Name);
                    intent.putExtra("reserve_Id", reserve_Id);
                    intent.putExtra("vaccine_no", vaccine_no);
                    intent.putExtra("totalprice", totalprice);
                    intent.putExtra("payment_date", payment_date);
                    intent.putExtra("payment_status", payment_status);
                    intent.putExtra("payment_Id",payment_id);
                    intent.putExtra("time",time);
                    intent.putExtra("Username", Username);
                    intent.putExtra("queue", String.valueOf(queue+1));
                    intent.putExtra("Key_Reserve_Id",Key_Reserve_Id);
                    startActivity(intent);


                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(payment_page.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onPaused(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }


    public void onclickDate(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(payment_page.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {
                TextView tv_date = findViewById(R.id.edit_date);
                String day =    checklength(String.valueOf(dayOfMount));
                String months = checklength(String.valueOf(month+1));
                tv_date.setText(day+"-"+months+"-"+year);
                payment_date = tv_date.getText().toString();
            }
        },mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    public  String checklength(String s){
        if (s.length()<2){
            s = "0"+s;
        }
        return s;
    }


    public void onclicktime(View view){
        final Calendar c = Calendar.getInstance();
        int  mHour= c.get(Calendar.HOUR_OF_DAY);
        int mMinute= c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(payment_page.this, android.R.style.Theme_Holo_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                TextView Time = findViewById(R.id.edit_date2);

                String hour = checklength(String.valueOf(hourOfDay));
                String m =  checklength(String.valueOf(minute));

                Time.setText(hour+" : "+m+" น.");
                time = Time.getText().toString();

            }
        },mHour,mMinute,true);
        timePickerDialog.show();
    }



}//public class