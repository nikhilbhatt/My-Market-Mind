package com.example.motivational.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.motivational.Adapters.Questionadapter;
import com.example.motivational.R;
import com.example.motivational.firebaseobject.Sendques;
import com.example.motivational.recyclerviewcards.Examplequestion;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class discuss extends AppCompatActivity {

    private String username,imageuri;
    private FloatingActionButton discuss_edit_button;
    private RecyclerView discuss_mrecyclerview;
    private RecyclerView.Adapter discuss_madapter;
    private RecyclerView.LayoutManager discuss_mlayoutmanager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference quesreference;
    private ArrayList<Examplequestion> ques_list;
    private SwipeRefreshLayout discuss_swipe_bottom;
    private ProgressBar discuss_progress_bar;
    private Context context;
    private Toolbar discuss_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);

        discuss_toolbar=findViewById(R.id.discuss_toolbar);
        setSupportActionBar(discuss_toolbar);
        getSupportActionBar().setTitle("Discussion");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences=getSharedPreferences("Prefs",0);
        username=sharedPreferences.getString("username","");
        imageuri=sharedPreferences.getString("userprofileimage","");
        context=discuss.this;

        discuss_mrecyclerview=findViewById(R.id.discuss_recyclerview);
        discuss_swipe_bottom=findViewById(R.id.discuss_swipe_bottom);
        discuss_progress_bar=findViewById(R.id.discuss_progress_bar);



        firebaseFirestore=FirebaseFirestore.getInstance();
        quesreference=firebaseFirestore.collection("query");


        ques_list=new ArrayList<>();
        buildrecyclerview();
        loaddata(0);

        discuss_swipe_bottom.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loaddata(1);
                discuss_swipe_bottom.setRefreshing(false);
            }
        });


        discuss_edit_button=findViewById(R.id.discuss_edit_button);
        discuss_edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createalertdialog();
            }
        });

    }
    public void createalertdialog()
    {
        LayoutInflater inflater=LayoutInflater.from(discuss.this);
        View view=inflater.inflate(R.layout.askques,null);
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(discuss.this);
        final EditText askques_text=view.findViewById(R.id.askques_edittext);
        alertdialog.setView(view);
        alertdialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        })
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TO do send the data to firebase database with name of the user
                          sendata(askques_text.getText().toString());
                    }
                });
        AlertDialog A=alertdialog.create();
        A.show();
    }
    public void sendata(String question)
    {
         SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
         String currentdate=sdf.format(new Date());
         Sendques sendques=new Sendques(username,question,currentdate,imageuri);
         quesreference.add(sendques).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
             @Override
             public void onSuccess(DocumentReference documentReference) {
                 Toast.makeText(discuss.this,"success",Toast.LENGTH_SHORT).show();
             }
         });
    }
    public void buildrecyclerview()
    {
        discuss_mrecyclerview.setHasFixedSize(true);
        discuss_mlayoutmanager=new LinearLayoutManager(this);
        discuss_madapter=new Questionadapter(ques_list,context);
        discuss_mrecyclerview.setLayoutManager(discuss_mlayoutmanager);
        discuss_mrecyclerview.setAdapter(discuss_madapter);
        discuss_madapter.notifyDataSetChanged();
    }
    public void loaddata(int x)
    {
        clear();
        if(x==0)
        {
            discuss_progress_bar.setVisibility(View.VISIBLE);
        }

            quesreference
                    .orderBy("currentdate", Query.Direction.DESCENDING)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        String que,user_name,current_date,image_uri;
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            discuss_progress_bar.setVisibility(View.GONE);
                            for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                            {
                                Sendques sendques=documentSnapshot.toObject(Sendques.class);
                                que=sendques.getQues();
                                user_name=sendques.getUser();
                                sendques.setDocumentId(documentSnapshot.getId());
                                current_date=sendques.getCurrentdate();
                                image_uri=sendques.getImageuri();
                                ques_list.add(new Examplequestion(user_name,que,sendques.getDocumentId(),current_date,image_uri));
                                discuss_madapter.notifyDataSetChanged();
                            }
                        }
                    });

    }
    public void clear()
    {
        ques_list.clear();
        discuss_mrecyclerview.removeAllViewsInLayout();
        discuss_madapter.notifyDataSetChanged();
    }
}
