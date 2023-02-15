package com.example.fetchjsonhomework;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ContactDetailActivity extends AppCompatActivity {

    private String personName, contactPhone, contactEmail;
    private TextView nameView, phoneView, emailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_detail);

        personName = getIntent().getStringExtra("name");
        contactPhone = getIntent().getStringExtra("phone");
        contactEmail = getIntent().getStringExtra("email");

        nameView = findViewById(R.id.nameTextView);
        nameView.setText(personName);
        phoneView = findViewById(R.id.phoneTextView);
        phoneView.setText(contactPhone);
        emailView = findViewById(R.id.emailTextView);
        emailView.setText(contactEmail);

    }

}
