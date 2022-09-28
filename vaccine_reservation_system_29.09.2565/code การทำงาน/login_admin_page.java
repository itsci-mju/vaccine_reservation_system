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


public class login_admin_page extends AppCompatActivity {

    String Message = "กรุณากรอกรหัสใหม่";
    EditText txt_user;
    EditText txt_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_admin_page);
        FirebaseApp.initializeApp(this);

    }

    public void ClickGOAdmin_manu_page(View view) {

        EditText txt_user = findViewById(R.id.Admin_User);
        String admin_id = txt_user.getText().toString();

        EditText txt_password = findViewById(R.id.Admin_Password);
        String admin_pass = txt_password.getText().toString();

        if(!admin_id.equals("")&&!admin_pass.equals("")){
            this.VerifyLogin(admin_id,admin_pass);
        }
        else if(admin_id.equals("")){
            Toast.makeText(login_admin_page.this, "กรุณากรอกรหัสผู้ใช้", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(login_admin_page.this, "กรุณากรอกรหัสผ่าน", Toast.LENGTH_LONG).show();
        }


    }


    private void VerifyLogin(String admin_id, String admin_pass) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("Login/admin1234");


        Query query1 = myRef.orderByKey().equalTo("password");
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){

                    Log.e("ค่าที่คาดหวัง",ds.getValue().toString());
                    Log.e("ค่าที่คาดหวัง2",ds.getKey().intern());

                    if(ds.getValue().toString().equals(admin_pass)){
                        Message = "login สําเร็จ";
                        if(Message.equals("login สําเร็จ")) {
                            Intent intent = new Intent(login_admin_page.this, admin_manu_page.class);
                            intent.putExtra("Username", admin_id);
                            startActivity(intent);
                            Message = "ยินดีต้อนรับเข้าสู่ระบบ";
                            Toast.makeText(login_admin_page.this, Message, Toast.LENGTH_LONG).show();
                        }//if
                    }else {
                        Message = "กรุณากรอกรหัสเข้าระบบใหม่";
                        Toast.makeText(login_admin_page.this, Message, Toast.LENGTH_LONG).show();
                    }//else
                } //for
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Toast.makeText(login.this, "กรุณากรอกเบอร์โทรใหม่", Toast.LENGTH_LONG).show();
            }
        });
    }



}