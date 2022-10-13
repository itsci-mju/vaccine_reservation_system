package th.ac.mju.itsci.reservevaccine_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class home_page extends AppCompatActivity {
    String Username;
    String Firstname;
    String Lastname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");
        //Toast.makeText(home_page.this,"สวัสดีคุณ "+Firstname+""+Lastname,Toast.LENGTH_LONG).show();


        TextView nameshow = findViewById(R.id.txt_nameshow);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_Member = database.getReference("Login/"+Username+"/Reserve");
        Query query1 = myRef_Member.orderByKey().equalTo("Member");
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){

                    String name =  ds.child("firstname").getValue().toString();
                    String lastname = ds.child("lastname").getValue().toString();

                    Log.e("1",name);
                    Log.e("2",lastname);
                    nameshow.setText("ยินดีต้อนรับ คุณ " + name + " " + lastname + " เข้าสู่ระบบ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }//onCreate


    public void Open_reserve_page(View view){
        Intent intent = new Intent(home_page.this, reserve_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);
    }

    public void Open_list_reserve(View view){
        Intent intent = new Intent(home_page.this, list_reserve_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);
    }

    public void Open_editprofile_page(View view){
        Intent intent = new Intent(home_page.this, editprofile_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);
    }

    public void Open_search_news_page(View view){
        Intent intent = new Intent(home_page.this, search_news_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);
    }

    public void Open_notify_make_appointment_page(View view){
        Intent intent = new Intent(home_page.this, notify_make_appointment_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);
    }

    public void Open_Login_page(View view){
        Intent intent = new Intent(home_page.this, Login_page.class);
        startActivity(intent);
    }

}//Class