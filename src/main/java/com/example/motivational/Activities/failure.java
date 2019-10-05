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

public class failure extends AppCompatActivity {

    private RecyclerView failure_mrecycelrview;
    private RecyclerView.LayoutManager failure_mlayoutmanager;
    private RecyclerView.Adapter failure_madapter;
    private ArrayList<Examplecard> failure_list_item;
    private FirebaseFirestore failure_fb;
    private CollectionReference failure_collect;
    private SwipeRefreshLayout failure_swipe_refresh;
    private ProgressBar failure_progress;
    private Toolbar failure_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failure);

        failure_mrecycelrview=findViewById(R.id.failure_recyclerview);
        failure_toolbar=findViewById(R.id.failure_toolbar);
        setSupportActionBar(failure_toolbar);
        getSupportActionBar().setTitle("Failure");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        failure_swipe_refresh=findViewById(R.id.failure_swipe);
        failure_progress=findViewById(R.id.failure_progress_bar);

        failure_fb=FirebaseFirestore.getInstance();
        failure_collect=failure_fb.collection("failure");

        failure_list_item=new ArrayList<>();
        builrecycler();
        loaddata(0);

        failure_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loaddata(1);
                //employee_swipe_refresh.setRefreshing(false);
            }
        });
    }
    public void builrecycler()
    {
        failure_mrecycelrview.setHasFixedSize(true);
        failure_mlayoutmanager=new LinearLayoutManager(this);
        failure_madapter=new Adapterclass(failure_list_item,failure.this);
        failure_mrecycelrview.setLayoutManager(failure_mlayoutmanager);
        failure_mrecycelrview.setAdapter(failure_madapter);
        failure_madapter.notifyDataSetChanged();
    }
    public void loaddata(int temp)
    {
        if(temp==0)
            failure_progress.setVisibility(View.VISIBLE);
        clear();
        failure_collect.orderBy("order", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data;
                        failure_progress.setVisibility(View.GONE);
                        failure_swipe_refresh.setRefreshing(false);
                        for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                        {
                            Note note=documentSnapshot.toObject(Note.class);
                            data=note.getTittle();
                            failure_list_item.add(new Examplecard(data,R.drawable.share));
                            failure_madapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    public void clear()
    {
        failure_list_item.clear();
        failure_mrecycelrview.removeAllViewsInLayout();
        failure_madapter.notifyDataSetChanged();
    }
}
