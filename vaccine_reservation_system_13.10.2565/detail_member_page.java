package th.ac.mju.itsci.reservevaccine_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class detail_member_page extends AppCompatActivity {
    String Username;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String Gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_member_page);
        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");
        radioGroup = findViewById(R.id.radioSex);


        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_editprofile = database.getReference("Login/"+Username+"/"+"Reserve");

        Query query1 = myRef_editprofile.orderByKey().equalTo("Member");
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String firstname  = ds.child("firstname").getValue().toString();
                    String lastname = ds.child("lastname").getValue().toString();
                    String address = ds.child("address").getValue().toString();
                    String phone = ds.child("phone").getValue().toString();
                    String gender = ds.child("gender").getValue().toString();
                    String email = ds.child("email").getValue().toString();
                    String idcard = ds.child("idcard").getValue().toString();
                    String birthday = ds.child("birthday").getValue().toString();
                    String nationality = ds.child("nationality").getValue().toString();

                    EditText input_name = findViewById(R.id.input_name);
                    EditText input_lastname = findViewById(R.id.input_lastname);
                    EditText input_address = findViewById(R.id.input_address);
                    EditText input_phone = findViewById(R.id.input_phone);
                    //EditText input_gender = findViewById(R.id.input_gender);
                    EditText input_email = findViewById(R.id.input_email);
                    TextView input_idcard = findViewById(R.id.input_idcard);
                    EditText input_brithday = findViewById(R.id.input_brithday);
                    EditText input_nationality = findViewById(R.id.input_nationality);

                    input_name.setText(firstname);
                    input_lastname.setText(lastname);
                    input_address.setText(address);
                    input_phone.setText(phone);
                    //input_gender.setText(gender);
                    input_email.setText(email);
                    input_idcard.setText(idcard);
                    input_brithday.setText("05-10-1999");
                    input_nationality.setText(nationality);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}