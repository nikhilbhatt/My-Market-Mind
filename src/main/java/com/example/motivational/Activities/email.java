package com.example.motivational.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.motivational.R;

public class email extends AppCompatActivity {
    private EditText edittextsub,medittextmessage;
    private Button mbutton;
    private Toolbar email_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        email_toolbar=findViewById(R.id.email_toolbar);
        setSupportActionBar(email_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edittextsub=findViewById(R.id.edit_text_subject);
        medittextmessage=findViewById(R.id.edit_text_message);
        mbutton=findViewById(R.id.button_send);
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmail();
            }
        });
    }
    public void sendmail()
    {
        String recipient="nikhilbhatt931@gmail.com";
        String subject=edittextsub.getText().toString();
        String message=medittextmessage.getText().toString();
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,recipient);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,message);;
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Choose client"));
    }
}
