package com.example.motivational.Activities;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.example.motivational.Adapters.Adapterclass;
import com.example.motivational.R;
import com.example.motivational.firebaseobject.Note;
import com.example.motivational.recyclerviewcards.Examplecard;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class student extends AppCompatActivity {
    private RecyclerView mrecyclerview;
    private RecyclerView.LayoutManager mlayoutmanager;
    private RecyclerView.Adapter madapter;
    private ArrayList<Examplecard> list_items;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collect;
    private SwipeRefreshLayout swipebottom;
    private Context context;
    private ProgressBar student_progress;
    private Toolbar student_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        student_toolbar=findViewById(R.id.student_toolbar);
        setSupportActionBar(student_toolbar);
        getSupportActionBar().setTitle("Student");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context=student.this;
        mrecyclerview=findViewById(R.id.student_recycler_view);

        student_progress=findViewById(R.id.student_progress_bar);

        firebaseFirestore=FirebaseFirestore.getInstance();
        collect=firebaseFirestore.collection("student");


        list_items=new ArrayList<>();
        recycler();
        loaddata(0);

        swipebottom=findViewById(R.id.student_swipe);
        swipebottom.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loaddata(1);
                swipebottom.setRefreshing(false);
            }
        });
    }
    public void recycler()
    {
        mrecyclerview.setHasFixedSize(true);
        mlayoutmanager=new LinearLayoutManager(this);
        madapter=new Adapterclass(list_items,context);
        mrecyclerview.setLayoutManager(mlayoutmanager);
        mrecyclerview.setAdapter(madapter);
        madapter.notifyDataSetChanged();
    }
    public void loaddata(int test)
    {
        clear();
        if(test==0)
          student_progress.setVisibility(View.VISIBLE);

        collect.
                orderBy("order", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    String data1;
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        student_progress.setVisibility(View.GONE);
                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                        {
                            Note note=documentSnapshot.toObject(Note.class);
                            data1=note.getTittle();
                            list_items.add(new Examplecard(data1,R.drawable.share));
                            madapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    public void clear()
    {
        list_items.clear();
        mrecyclerview.removeAllViewsInLayout();
        madapter.notifyDataSetChanged();
    }
}
