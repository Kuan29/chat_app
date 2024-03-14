package com.example.lab2022_2sub;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private Task<AuthResult> task;
    private String personName ;
    private String perSonEmail;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ImageView profileimage;
    private TextView tv_name;
    private TextView tv_emal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        navigationView = (NavigationView)findViewById(R.id.navigationview);
        View header = navigationView.getHeaderView(0);
        navigationView.bringToFront();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
        tv_name = (TextView) header.findViewById(R.id.tv_name);
        tv_emal = (TextView) header.findViewById(R.id.tv_email);
        profileimage=(ImageView) header.findViewById(R.id.iv_image);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); // 왼쪽 상단 버튼 만들기
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_name); // 왼쪽 상당 버튼 아이콘 지정
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.closed);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("199799821375-gcek93hgtlr2mhcecejnf883kj1emmsb.apps.googleusercontent.com")
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            long nNom = System.currentTimeMillis();
            Date mReDate = new Date(nNom);
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            String chattime = format.format(mReDate);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            personName = acct.getDisplayName();
            perSonEmail = acct.getEmail();
            tv_name.setText(personName);
            tv_emal.setText(perSonEmail);
            Picasso.with(this).load(acct.getPhotoUrl()).into(profileimage);
            UserModel userModel = new UserModel();
            userModel.userName = personName;
            userModel.userEmail = perSonEmail;
            userModel.profileimg = acct.getPhotoUrl().toString();
            userModel.usersignuptime = chattime;
            String uid = user.getUid();
            userModel.uid = uid;

           FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel);


        }




        getFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new PeopleFragment2()).commit();


    }
}
