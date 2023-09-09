package com.example.omra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

public class AppointmentActivity extends AppCompatActivity {
    TextView email, viewAppointments;
    FirebaseAuth auth;
    FirebaseUser user;
    EditText date, desc, time, contact, appPurpose;
    Spinner month;
    Button button, back, submit;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailView);
        user = auth.getCurrentUser();
        button = findViewById(R.id.logout);
        date = findViewById(R.id.dateText);
        month = findViewById(R.id.spinnermonths);
        back = findViewById(R.id.back);
        desc = findViewById(R.id.desc);
        time = findViewById(R.id.timeText);
        contact = findViewById(R.id.contactText);
        appPurpose = findViewById(R.id.appPurposeText);
        submit = findViewById(R.id.submit);



        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            email.setText(user.getEmail());
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Initialize Firebase Database reference
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        reference = db.getReference("bookAppointment");

        submit.setOnClickListener(view -> {
            String Desc = desc.getText().toString();
            String Date = date.getText().toString();
            String Month = month.getSelectedItem().toString();
            String Time = time.getText().toString();
            String Contact = contact.getText().toString();
            String AppPurpose = appPurpose.getText().toString();

            if (!Desc.isEmpty() && !Date.isEmpty() && !Month.isEmpty() && !Time.isEmpty() && !Contact.isEmpty() && !AppPurpose.isEmpty()) {
                // Generate a unique key for the appointment entry
                DatabaseReference newAppointmentRef = reference.push();
                String appointmentId = newAppointmentRef.getKey();

                // Create an AppointmentDetails object with the provided data
                AppointmentDetails appointmentDetails = new AppointmentDetails(Desc, Date, Month, Time, Contact, AppPurpose);

                // Push the appointment data to the generated key
                newAppointmentRef.setValue(appointmentDetails).addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        // Error occurred
                        Log.e("Database", "Error adding data: " + task.getException());
                        Toast.makeText(AppointmentActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                    } else {
                        // Data added successfully
                        Log.d("Database", "Data added successfully");
                        desc.setText("");
                        date.setText("");
                        time.setText("");
                        contact.setText("");
                        appPurpose.setText("");
                        Toast.makeText(AppointmentActivity.this, "You Have booked an appointment", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                // Handle empty fields
                Toast.makeText(AppointmentActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            }
        });

    }
}