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

public class success extends AppCompatActivity {
    private RecyclerView success_mrecycelrview;
    private RecyclerView.LayoutManager success_mlayoutmanager;
    private RecyclerView.Adapter success_madapter;
    private ArrayList<Examplecard> success_list_item;
    private FirebaseFirestore success_fb;
    private CollectionReference success_collect;
    private SwipeRefreshLayout success_swipe_refresh;
    private ProgressBar success_progress;
    private Toolbar success_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        success_mrecycelrview=findViewById(R.id.success_recyclerview);
        success_toolbar=findViewById(R.id.success_toolbar);
        setSupportActionBar(success_toolbar);
        getSupportActionBar().setTitle("Success");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        success_swipe_refresh=findViewById(R.id.success_swipe);
        success_progress=findViewById(R.id.success_progress_bar);

        success_fb=FirebaseFirestore.getInstance();
        success_collect=success_fb.collection("success");

        success_list_item=new ArrayList<>();
        builrecycler();
        loaddata(0);

        success_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loaddata(1);
                //employee_swipe_refresh.setRefreshing(false);
            }
        });
    }
    public void builrecycler()
    {
        success_mrecycelrview.setHasFixedSize(true);
        success_mlayoutmanager=new LinearLayoutManager(this);
        success_madapter=new Adapterclass(success_list_item,success.this);
        success_mrecycelrview.setLayoutManager(success_mlayoutmanager);
        success_mrecycelrview.setAdapter(success_madapter);
        success_madapter.notifyDataSetChanged();
    }
    public void loaddata(int temp)
    {
        if(temp==0)
            success_progress.setVisibility(View.VISIBLE);
        clear();
        success_collect.orderBy("order", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data;
                        success_progress.setVisibility(View.GONE);
                        success_swipe_refresh.setRefreshing(false);
                        for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                        {
                            Note note=documentSnapshot.toObject(Note.class);
                            data=note.getTittle();
                            success_list_item.add(new Examplecard(data,R.drawable.share));
                            success_madapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    public void clear()
    {
        success_list_item.clear();
        success_mrecycelrview.removeAllViewsInLayout();
        success_madapter.notifyDataSetChanged();
    }
}
