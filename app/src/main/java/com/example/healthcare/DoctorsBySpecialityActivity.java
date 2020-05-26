package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.models.Doctor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DoctorsBySpecialityActivity extends AppCompatActivity {
    ListView myDoctorsBySpecialityListView;
    List<Doctor>  myDoctors;
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_by_speciality);
        Intent intent = getIntent();
        final String speciality = intent.getStringExtra("speciality");
        myDoctorsBySpecialityListView = findViewById(R.id.myDoctorsBySpeciality);
        myDoctors = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Doctors");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myDoctors.clear();
                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    Doctor doctor = data.getValue(Doctor.class);
                    if(doctor.getSpeciality().equals(speciality))
                    {
                        myDoctors.add(doctor);
                        Collections.sort(myDoctors);
                        adapter = new ListViewAdapter(getApplicationContext(), myDoctors);
                        myDoctorsBySpecialityListView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
