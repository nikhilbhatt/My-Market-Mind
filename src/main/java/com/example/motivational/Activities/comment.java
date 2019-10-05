package com.example.motivational.Activities;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.motivational.Adapters.Commentadapter;
import com.example.motivational.R;
import com.example.motivational.firebaseobject.Sendcomment;
import com.example.motivational.recyclerviewcards.Examplecomment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.google.android.gms.internal.zzbgp.NULL;

public class comment extends AppCompatActivity {


    private RecyclerView comment_mrecyclerview;
    private RecyclerView.LayoutManager comment_mlayoutmanager;
    private RecyclerView.Adapter comment_madapter;
    private ArrayList<Examplecomment> list_comment;
    private TextView  comment_name_text,comment_ques_text,ques_time;
    private FloatingActionButton comment_button;
    private CollectionReference collectionReference;
    private FirebaseFirestore firebaseFirestore;
    private String username,user_profile_image;
    private SwipeRefreshLayout comment_swipe_bottom;
    private ProgressBar comment_progress;
    private CircleImageView comment_quse_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        comment_swipe_bottom=findViewById(R.id.comment_swipe_refresh);
        comment_progress=findViewById(R.id.comment_progress_bar);

        comment_quse_image=findViewById(R.id.comment_ques_circle_image);

        SharedPreferences sharedPreferences=getSharedPreferences("Prefs",0);
        username=sharedPreferences.getString("username","");
        user_profile_image=sharedPreferences.getString("userprofileimage","");
        comment_name_text=findViewById(R.id.comment_name_text);
        comment_ques_text=findViewById(R.id.comment_ques_text);
        comment_button=findViewById(R.id.comment_float_button);
        ques_time=findViewById(R.id.comment_question_time);

        String data1=getIntent().getStringExtra("ques_username");
        String data2=getIntent().getStringExtra("ques_comment");
        String data3=getIntent().getStringExtra("ques_document_id");
        String data4=getIntent().getStringExtra("ques_time");
        String data5=getIntent().getStringExtra("ques_image_url");


        firebaseFirestore=FirebaseFirestore.getInstance();
        collectionReference=firebaseFirestore.collection("query").document(data3).collection("comment");


        ques_time.setText(data4);
        comment_name_text.setText(data1);
        comment_ques_text.setText(data2);
        if(!data5.equals("null")) {
            try {
                Glide.with(this).load(data5).into(comment_quse_image);
            }
            catch (NullPointerException e){
                comment_quse_image.setImageResource(R.drawable.person);
            }
        }
        else
            comment_quse_image.setImageResource(R.drawable.person);

        comment_mrecyclerview=findViewById(R.id.comementrecycelrview);

        list_comment=new ArrayList<>();
        buildrecyclerview();
        loaddata(0);

        comment_swipe_bottom.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loaddata(1);
                comment_swipe_bottom.setRefreshing(false);
            }
        });

        comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildalertdialog();
            }
        });
    }
    public void buildrecyclerview()
    {
       comment_mrecyclerview.setHasFixedSize(true);
       comment_mlayoutmanager=new LinearLayoutManager(this);
       comment_madapter=new Commentadapter(list_comment,comment.this);
       comment_mrecyclerview.setLayoutManager(comment_mlayoutmanager);
       comment_mrecyclerview.setAdapter(comment_madapter);
       comment_madapter.notifyDataSetChanged();

    }
    public void buildalertdialog()
    {
        LayoutInflater inflater=LayoutInflater.from(this);
        View v=inflater.inflate(R.layout.commentdialog,null);
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(this);
        final EditText alert_text=v.findViewById(R.id.commentdialog_edittext);
        alertdialog.setView(v);
        alertdialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //send data to firebase database of alerttext
                senddata(alert_text.getText().toString());
            }
        });
        AlertDialog a=alertdialog.create();
        a.show();
    }
    public void senddata(String comment)
    {
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String current_date=sdf1.format(new Date());
        Sendcomment sendcomment=new Sendcomment(username,comment,current_date,user_profile_image);
        collectionReference.add(sendcomment).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(comment.this,"Success",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void clear()
    {
        list_comment.clear();
        comment_mrecyclerview.removeAllViewsInLayout();
        comment_madapter.notifyDataSetChanged();
    }
    public void loaddata(int x)
    {
       clear();
       if(x==0)
           comment_progress.setVisibility(View.VISIBLE);

       collectionReference
               .orderBy("currentdate", Query.Direction.ASCENDING)
               .get()
               .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                   String commentdata,user_name,current_date,image_uri;
                   @Override
                   public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                       comment_progress.setVisibility(View.GONE);
                       for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                       {
                            Sendcomment sendcomment=documentSnapshot.toObject(Sendcomment.class);
                            commentdata=sendcomment.getComment();
                            user_name=sendcomment.getUser();
                            current_date=sendcomment.getCurrentdate();
                            image_uri=sendcomment.getImageuri();
                            list_comment.add(new Examplecomment(user_name,commentdata,current_date,image_uri));
                            comment_madapter.notifyDataSetChanged();
                       }
                   }
               });

    }
}
