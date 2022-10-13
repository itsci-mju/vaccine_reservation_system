package th.ac.mju.itsci.reservevaccine_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Login_page extends AppCompatActivity {

    String Message = "กรุณากรอกรหัสใหม่";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        FirebaseApp.initializeApp(this);
    }


    public void ClickGOHome(View view) {
        EditText txt_user = findViewById(R.id.txt_user);
        String Username = txt_user.getText().toString();

        EditText txt_password = findViewById(R.id.txt_password);
        String Password = txt_password.getText().toString();

        if(!Username.equals("")&&!Password.equals("")){
            this.VerifyLogin(Username,Password);
        }
        else if(Username.equals("")){
            Toast.makeText(Login_page.this, "กรุณากรอกรหัสผู้ใช้", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(Login_page.this, "กรุณากรอกรหัสผ่าน", Toast.LENGTH_LONG).show();
        }
    }

    public void ClickGORegister(View view) {
        Intent intent = new Intent(Login_page.this, register_page.class);
        startActivity(intent);
    }

    private void VerifyLogin(String Username, String Password) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Login");

        String type_login = "admin";
        Query query1 = myRef.orderByKey().equalTo(Username);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){

                    Log.e("ค่าที่คาดหวัง",ds.getKey().trim());

                    if(ds.child("password").getValue().toString().equals(Password)){
                           Message = "login สําเร็จ";

                        if(Message.equals("login สําเร็จ")) {

                            if(ds.child("type_user").getValue().toString().equals(type_login)){
                                Intent intent = new Intent(Login_page.this, admin_manu_page.class);
                                intent.putExtra("Username", Username);
                                startActivity(intent);
                                String textalert = "ยินดีต้อนรับเข้าสู่ระบบ";
                                Toast.makeText(Login_page.this, textalert, Toast.LENGTH_LONG).show();

                            }else {
                                Intent intent = new Intent(Login_page.this, home_page.class);
                                intent.putExtra("Username", Username);
                                startActivity(intent);
                                String textalert = "ยินดีต้อนรับเข้าสู่ระบบ";
                                Toast.makeText(Login_page.this, textalert, Toast.LENGTH_LONG).show();
                            }
                        }//if
                    }else {
                        Message = "กรุณากรอกรหัสเข้าระบบใหม่";
                        Toast.makeText(Login_page.this, Message, Toast.LENGTH_LONG).show();
                    }//else
                } //for

                if(!Message.equals("login สําเร็จ")){
                    Message = "ไม่พบข้อมูลบัญชีนี้กรุณาลองใหม่อีกครั้ง";
                    Toast.makeText(Login_page.this, Message, Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Toast.makeText(login.this, "กรุณากรอกเบอร์โทรใหม่", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void ClickGOAdmin_Page(View view) {
        Intent intent = new Intent(Login_page.this, login_admin_page.class);
        startActivity(intent);
        finish();
    }



    }//public class





