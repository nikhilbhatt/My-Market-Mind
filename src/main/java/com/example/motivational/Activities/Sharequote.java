package com.example.motivational.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.motivational.R;
import com.example.motivational.firebaseobject.Sendques;
import com.example.motivational.firebaseobject.Sendquote;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Sharequote extends AppCompatActivity {
    private EditText shareqoute_text;
    private Button sharequotebutton;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;
    private String data;
    private Toolbar sharequote_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharequote);

        sharequote_toolbar=findViewById(R.id.shareqoute_toolbar);
        setSupportActionBar(sharequote_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseFirestore=FirebaseFirestore.getInstance();
        collectionReference=firebaseFirestore.collection("userdata");
        shareqoute_text=findViewById(R.id.shareqoute_text);
        sharequotebutton=findViewById(R.id.shareqoute_button);
        sharequotebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   data=shareqoute_text.getText().toString();
                   senddata(data);
            }
        });
    }
    public void senddata(String data1)
    {
        Sendquote sendquote=new Sendquote(data1);
        collectionReference.add(sendquote).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Toast.makeText(Sharequote.this,"success",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
