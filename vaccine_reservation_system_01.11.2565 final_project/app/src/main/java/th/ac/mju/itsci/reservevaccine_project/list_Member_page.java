package th.ac.mju.itsci.reservevaccine_project;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.Calendar;

public class list_Member_page extends AppCompatActivity {
    String Username;
    Spinner spinner_reserve_status;
    Spinner spinner_Lots;
    Spinner spinner_No;
    String company;
    String date_in;
    String vaccine_name;
    String Reserve_status_spn;
    String Name_lastname;
    String name_key;
    String id;
    String reserve_date;
    String details;
    String status;
    Button btn_detail;
    String phone;
    String rid;


    private String[] Reserve_Status = {"รอยืนยันชำระเงิน","ชำระเงินเสร็จสิ้น","ยกเลิกการจอง","ทำการนัดหมายเรียบร้อยแล้ว","หมดเวลานัดการหมาย"};
    ArrayList<String> List_Member = new ArrayList<>();
    ArrayList<String> LotsList = new ArrayList<>();
    String str_ck = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list__member_page);
        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        Username = intent.getStringExtra("Username");

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        this.setspinnerVaccine_Name();

        EditText TV_idcard = findViewById(R.id.tv_idcard);




        Button btn_search = findViewById(R.id.button_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    LinearLayout show_member = findViewById(R.id.show_member);
                    show_member.removeAllViews();

                    String idcard_username = TV_idcard.getText().toString();


                DatabaseReference myRef_listname = database.getReference("Login/"+idcard_username+"/"+"Reserve");
                Query query = myRef_listname.orderByKey().equalTo("Member");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Name_lastname = ds.child("firstname").getValue().toString() +" "+ds.child("lastname").getValue().toString();
                            phone = ds.child("phone").getValue().toString();

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                DatabaseReference myRef_list_reserve = database.getReference("Login/"+idcard_username+"/Reserve/Reserve_List");
                Query query1 = myRef_list_reserve.orderByKey();
                query1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            View Vreserve = getLayoutInflater().inflate(R.layout.list_view_all_member,null);
                            TextView txt_name_list = Vreserve.findViewById(R.id.txt_name_list);
                            TextView txt_vaccine_name_list = Vreserve.findViewById(R.id.txt_vaccine_name_list);
                            TextView txt_status_list = Vreserve.findViewById(R.id.txt_status_list);
                            TextView txt_date_list = Vreserve.findViewById(R.id.txt_date_list);

                            if(ds.child("status").getValue().toString().equals(Reserve_status_spn)){

                                status = ds.child("status").getValue().toString();
                                reserve_date = ds.child("reserve_date").getValue().toString();
                                details = ds.child("details").getValue().toString();
                                id = ds.child("reserve_id").getValue().toString();

                                txt_name_list.setText(Name_lastname);
                                txt_vaccine_name_list.setText(id);
                                txt_date_list.setText(reserve_date);
                                txt_status_list.setText(status);
                                rid = txt_vaccine_name_list.getText().toString();



                                DatabaseReference Reserve_Deatails = database.getReference("Login/" + idcard_username + "/Reserve/Reserve_List/"+ds.getKey());
                                Query query3 = Reserve_Deatails.orderByKey().equalTo("Reserve_Deatails");

                                query3.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot ds : snapshot.getChildren()){
                                            String Key_Reserve_Id = ds.child("reserve_deatails_id").getValue().toString();
                                            btn_detail = Vreserve.findViewById(R.id. button_detail);
                                            btn_detail.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent intent = new Intent(list_Member_page.this, detail_member_page.class);
                                                    Log.e("List Member rid ",id);
                                                    Log.e("List Member idcard_username ",idcard_username);

                                                    intent.putExtra("reserve_id",Key_Reserve_Id);
                                                    intent.putExtra("Username", idcard_username);
                                                    intent.putExtra("Name_lastname",Name_lastname);
                                                    intent.putExtra("phone",phone);
                                                    startActivity(intent);
                                                }
                                            });

                                        }

                                        show_member.addView(Vreserve);


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }






                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

            }//onClick
        });




    }//onCreate

    private void setspinnerVaccine_Name() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservevaccine-project-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef_list_vaccineName = database.getReference("Login");

        Query query = myRef_list_vaccineName.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    if(!ds.getKey().equals("admin1234")){
                        String Member_key = ds.getKey();
                        List_Member.add(Member_key);
                    }
                }

                Log.e("Value of List_Member",List_Member.toString());

                spinner_reserve_status = findViewById(R.id.spn_reserve_status);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(list_Member_page.this, android.R.layout.select_dialog_item, Reserve_Status);
                spinner_reserve_status.setAdapter(adapter);

                spinner_reserve_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Reserve_status_spn = adapterView.getSelectedItem().toString();
                        Log.e("Value of r_status",Reserve_status_spn);
                    }//OnItemselect

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }//void setspinnerVaccine_Name



    public void onclickDate_Start(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(list_Member_page.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {

                TextView TV_date = findViewById(R.id.tv_idcard);
                String day =    checklength(String.valueOf(dayOfMount));
                String months = checklength(String.valueOf(month+1));
                TV_date.setText(day + "-" + months + "-" + year);

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

//    public void onclickDate_End(View view) {
//        final Calendar c = Calendar.getInstance();
//        int mYear = c.get(Calendar.YEAR);
//        int mMonth = c.get(Calendar.MONTH);
//        int mDay = c.get(Calendar.DAY_OF_MONTH);
//        DatePickerDialog datePickerDialog = new DatePickerDialog(list_Member_page.this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMount) {
//
//                TextView TV_date = findViewById(R.id.tv_dateend);
//                String day =    checklength(String.valueOf(dayOfMount));
//                String months = checklength(String.valueOf(month+1));
//                TV_date.setText(day + " - " + months + " - " + year);
//
//            }
//        }, mYear, mMonth, mDay);
//        datePickerDialog.show();
//    }


    public  String checklength(String s){
        if (s.length()<2){
            s = "0"+s;
        }
        return s;
    }

}