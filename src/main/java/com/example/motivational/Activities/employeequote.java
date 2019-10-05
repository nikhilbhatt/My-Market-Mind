package com.example.motivational.Activities;

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

public class employeequote extends AppCompatActivity {
    private RecyclerView mrecycelrview;
    private RecyclerView.LayoutManager mlayoutmanager;
    private RecyclerView.Adapter madapter;
    private ArrayList<Examplecard> list_item;
    private FirebaseFirestore fb;
    private CollectionReference collect;
    private SwipeRefreshLayout employee_swipe_refresh;
    private ProgressBar employee_progress;
    private Toolbar employee_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeequote);
        mrecycelrview=findViewById(R.id.employee_recyclerview);
        employee_toolbar=findViewById(R.id.employee_toolbar);
        setSupportActionBar(employee_toolbar);
        getSupportActionBar().setTitle("Employee");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        employee_swipe_refresh=findViewById(R.id.employee_swipe);
        employee_progress=findViewById(R.id.employee_progress_bar);

        fb=FirebaseFirestore.getInstance();
        collect=fb.collection("employee");

        list_item=new ArrayList<>();
        builrecycler();
        loaddata(0);

        employee_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loaddata(1);
                //employee_swipe_refresh.setRefreshing(false);
            }
        });

    }
    public void builrecycler()
    {
        mrecycelrview.setHasFixedSize(true);
        mlayoutmanager=new LinearLayoutManager(this);
        madapter=new Adapterclass(list_item,employeequote.this);
        mrecycelrview.setLayoutManager(mlayoutmanager);
        mrecycelrview.setAdapter(madapter);
        madapter.notifyDataSetChanged();
    }
    public void loaddata(int temp)
    {
        if(temp==0)
          employee_progress.setVisibility(View.VISIBLE);
        clear();
        collect.orderBy("order", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data;
                        employee_progress.setVisibility(View.GONE);
                        employee_swipe_refresh.setRefreshing(false);
                        for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                        {
                            Note note=documentSnapshot.toObject(Note.class);
                            data=note.getTittle();
                            list_item.add(new Examplecard(data,R.drawable.share));
                            madapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    public void clear()
    {
        list_item.clear();
        mrecycelrview.removeAllViewsInLayout();
        madapter.notifyDataSetChanged();
    }
}
