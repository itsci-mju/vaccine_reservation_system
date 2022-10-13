package th.ac.mju.itsci.reservevaccine_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

public class search_news_page extends AppCompatActivity {
    String Username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_news_page);

//        Intent intent = getIntent();
//        Username = intent.getStringExtra("Username");
//        Log.e("UserReserve is ",Username);
        FirebaseApp.initializeApp(this);
    }


    public  void  goHome(View view){
        Intent intent = new Intent(search_news_page.this, Login_page.class);
        startActivity(intent);
    }
}