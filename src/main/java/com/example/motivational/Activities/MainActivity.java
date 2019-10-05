package com.example.motivational.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.motivational.Adapters.Viewpageradapter;
import com.example.motivational.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.MissingFormatArgumentException;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,GoogleApiClient.OnConnectionFailedListener{
    private ImageView main_student, main_employee,main_success,main_failure;
    private TextView main_thought,nav_profile_name,nav_profile_email;
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference documentReference;
    private ProgressBar main_thought_progress;
    private DrawerLayout main_draw;
    private Toolbar main_toolbar;
    private CircleImageView nav_profile_image;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private String[] Imageurl;
    private ViewPager viewpager;
    private Timer timer;
    final long DELAY_MS=500,PERIOD_MS=5000;
    public static String s1,s2,s3,s4,s5;
    private LinearLayout main_layout1,main_layout2,main_layout3;
//    private TabLayout.Tab main_tab;
    private TabLayout main_tab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_thought_progress = findViewById(R.id.main_thought_progress);

        firebaseFirestore=FirebaseFirestore.getInstance();
        documentReference=firebaseFirestore.collection("Thought").document("Name");
        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        s1=documentSnapshot.get("key1").toString();
                        s2=documentSnapshot.get("key2").toString();
                        s3=documentSnapshot.get("key3").toString();
                        s4=documentSnapshot.get("key4").toString();
                        s5=documentSnapshot.get("key5").toString();
                        Imageurl=new String[]{
                                s1,s2,s3,s4,s5
                        };
                        main_thought_progress.setVisibility(View.GONE);
                        viewpager=findViewById(R.id.main_view_pager);

                        main_tab= findViewById(R.id.main_tab_layout);
                        main_tab.setupWithViewPager(viewpager,true);

                        Viewpageradapter adapter=new Viewpageradapter(MainActivity.this,Imageurl);
                        viewpager.setAdapter(adapter);
                        final Handler handler=new Handler();
                        final Runnable update=new Runnable() {
                            @Override
                            public void run() {
                                int pos= viewpager.getCurrentItem()+1;
                                 if(pos>=5)
                                 {
                                     pos=0;
                                 }
                                 viewpager.setCurrentItem(pos,true);
                            }
                        };
                        timer=new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(update);
                            }
                        },DELAY_MS,PERIOD_MS);
                    }
                });


        main_toolbar=findViewById(R.id.main_toolbar);
        setSupportActionBar(main_toolbar);
        main_draw=findViewById(R.id.main_drawer);
        NavigationView navigationView=findViewById(R.id.main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,main_draw,main_toolbar, R.string.nav_draw_open,R.string.nav_draw_close );
        main_draw.addDrawerListener(toggle);
        toggle.syncState();
        View navheaderview=navigationView.getHeaderView(0);
        initnavheader(navheaderview);
//
//        main_thought = findViewById(R.id.main_Thought_of__the_day);
//
//        firebaseFirestore = FirebaseFirestore.getInstance();
//        documentReference = firebaseFirestore.collection("Thought").document("Name");
//        documentReference.get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    String value;
//
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        main_thought_progress.setVisibility(View.GONE);
//                        value = documentSnapshot.get("key").toString();
//                        main_thought.setText(value);
//                    }
//                });


        main_employee = findViewById(R.id.main_employee);
        main_student = findViewById(R.id.main_student);
        main_success=findViewById(R.id.main_success);
        main_failure=findViewById(R.id.main_failure);
        main_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, student.class);
                startActivity(intent);
            }
        });
        main_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newintent = new Intent(MainActivity.this, employeequote.class);
                startActivity(newintent);
            }
        });
        main_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent success_intent=new Intent(MainActivity.this,success.class);
                startActivity(success_intent);
            }
        });
        main_failure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent failure_intent=new Intent(MainActivity.this,failure.class);
                startActivity(failure_intent);
            }
        });
        main_layout1=findViewById(R.id.main_linear_share_quote);
        main_layout2=findViewById(R.id.main_linear_discuss);
        main_layout3=findViewById(R.id.main_linear_email_us);
        main_layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent miintent=new Intent(MainActivity.this,Sharequote.class);
                startActivity(miintent);
            }
        });
        main_layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Intent inntent=new Intent(MainActivity.this,discuss.class);
                startActivity(inntent);
            }
        });
        main_layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inter=new Intent(MainActivity.this,email.class);
                startActivity(inter);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId())
        {
            case R.id.drawer_discuss:
                Intent intent=new Intent(MainActivity.this,discuss.class);
                startActivity(intent);
                break;
            case R.id.drawer_send:
                Intent mintent=new Intent(MainActivity.this,Sharequote.class);
                startActivity(mintent);
                break;

            case R.id.drawer_logout:
                FirebaseAuth.getInstance().signOut();
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                if (status.isSuccess()){
                                    gotoMainActivity();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Session not close",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                 break;
            case R.id.drawer_share:
                Toast.makeText(this,"Implemented soon",Toast.LENGTH_SHORT).show();
                 break;

            case R.id.drawer_instagram:
                Intent intent1=new Intent(MainActivity.this,instagram.class);
                startActivity(intent1);
                break;

            case R.id.drawer_sendmail:
                Intent inte=new Intent(MainActivity.this,email.class);
                startActivity(inte);
                break;
        }
        main_draw.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr= Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result=opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (main_draw.isDrawerOpen(GravityCompat.START))
        {
                     main_draw.closeDrawer(GravityCompat.START);
        }
        else
        {
             super.onBackPressed();
        }

    }
    public void initnavheader(View view)
    {

        nav_profile_name=view.findViewById(R.id.profile_name);
        nav_profile_email=view.findViewById(R.id.profile_email);
        nav_profile_image=view.findViewById(R.id.profile_image);
        gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
            nav_profile_name.setText(account.getDisplayName());
            String username=account.getDisplayName();
            SharedPreferences sharedPreferences=getSharedPreferences("Prefs",0);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("username",username);
            editor.putString("userprofileimage", String.valueOf(account.getPhotoUrl()));
            editor.apply();
            nav_profile_email.setText(account.getEmail());
            try{
                if(account.getPhotoUrl()!=null)
                    Glide.with(this).load(account.getPhotoUrl()).into(nav_profile_image);
//                nav_profile_image.setImageResource(R.drawable.person);
            }catch (NullPointerException e){
                nav_profile_image.setImageResource(R.drawable.person);
            }
        }else{
            gotoMainActivity();
        }
    }
    private void gotoMainActivity(){
        Intent intent=new Intent(this,register.class);
        startActivity(intent);
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
