package th.ac.mju.itsci.reservevaccine_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class edit_vaccine_page extends AppCompatActivity {

      String Username;

    private String id;
    private String vaccineName ;
    private String date_in;
    private String mgf_date;
    private String exp_date;
    private String does_amount;
    private String manufacturing_company;
    private String imported_company;
    private String product_version;
    private String register_no;
    private String doesPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_vaccine_page);
        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");
        vaccineName = intent.getStringExtra("VaccineName");

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_vaccine_by_id = database.getReference("Login/"+Username+"/Vaccine/"+vaccineName);
        Query query1 = myRef_vaccine_by_id.orderByKey().equalTo("1");

        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String vaccinename = ds.child("vaccineName").getValue().toString();
                    String date_in = ds.child("date_in").getValue().toString();
                    String exp_date = ds.child("exp_date").getValue().toString();
                    String mgf_date = ds.child("mgf_date").getValue().toString();
                    String company = ds.child("imported_company").getValue().toString();

                    EditText Vname = findViewById(R.id.txtedit_Vname);
                    TextView date_input = findViewById(R.id.txtedit_Vdate_Input);
                    TextView date_mgf = findViewById(R.id.txtedit_Vdate_mgf);
                    TextView date_exp = findViewById(R.id.txtedit_Vdate_exp);
                    EditText companyV = findViewById(R.id.txtedit_Vcompany);

                     Vname.setText(vaccinename);
                     date_input.setText(date_in);
                     date_mgf.setText(exp_date);
                     date_exp.setText(mgf_date);
                     companyV.setText(company);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }//Oncreate


    public void  Edit_Vaccine(View view){

//        EditText Vname = findViewById(R.id.txtedit_Vname);
//        TextView date_input = findViewById(R.id.txtedit_Vdate_Input);
//        TextView date_mgf = findViewById(R.id.txtedit_Vdate_mgf);
//        TextView date_exp = findViewById(R.id.txtedit_Vdate_exp);
//        EditText companyV = findViewById(R.id.txtedit_Vcompany);
//
//
//        vaccinename_edit = Vname.getText().toString();
//        date_in_edit = date_input.getText().toString();
//        mgf_date_edit = date_mgf.getText().toString();
//        exp_date_edit = date_exp.getText().toString();
//        company_edit = companyV.getText().toString();
//
//
//
//        String id = "1";
//        Vaccine v = new Vaccine(id,vaccine_str,date_result1,date_result2,date_result3,company_str, manufacturing_company_str,
//                does_amout,manufacturing_company_str,imported_company_str,product_version_str,register_no_str,doesPrice_str);
//        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
//        DatabaseReference myRef = database.getReference("Login/"+Username+"/Vaccine");
//        Log.e("VaccineName_DEL ",VaccineName);
//        DatabaseReference v_del = myRef.child(VaccineName);
//        v_del.removeValue();
//
//
//
//        DatabaseReference myRef_vaccineEdit_add = database.getReference("Login/admin1234/Vaccine/"+vaccinename_edit);
//        Log.e("VaccineName_EDIT ",vaccinename_edit);
//        myRef_vaccineEdit_add.setValue(v);
//
//
//
//
//        Intent intent = new Intent(edit_vaccine_page.this, list_vaccine_page.class);
//        intent.putExtra("Username", Username);
//        startActivity(intent);
    }

    public void  Cancal_Edit_Vaccine(View view){
        Intent intent = new Intent(edit_vaccine_page.this, list_vaccine_page.class);
        intent.putExtra("Username", Username);
        startActivity(intent);
    }
}