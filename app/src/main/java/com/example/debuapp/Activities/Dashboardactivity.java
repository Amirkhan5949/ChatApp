package com.example.debuapp.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.debuapp.BuildConfig;
import com.example.debuapp.R;
import com.example.debuapp.UI.Fragment.AlluserFragment;
import com.example.debuapp.UI.Fragment.Chatfragment;
import com.example.debuapp.UI.Fragment.ProfileFragment;
import com.example.debuapp.utils.FirebaseConstants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class Dashboardactivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        drawerLayout=findViewById(R.id.drawerlayout);
        navigationView=findViewById(R.id.navigation);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        View hView =  navigationView.getHeaderView(0);
       final ImageView dp = (ImageView) hView.findViewById(R.id.dp);
       final TextView name=(TextView)hView.findViewById(R.id.name);
       final TextView number=(TextView)hView.findViewById(R.id.number);
        FirebaseDatabase.getInstance().getReference()
                .child("User")
                .child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String s=  dataSnapshot.child(FirebaseConstants.User.image).getValue().toString();
                        Picasso.get().load(s).into(dp);

                     String a= dataSnapshot.child(FirebaseConstants.User.user).getValue().toString();
                     name.setText(a);

                     String b=dataSnapshot.child(FirebaseConstants.User.number).getValue().toString();
                     number.setText(b);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        Log.i("sancbs", "onCreate: "+        drawerLayout);
        toggle=new ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        replace(new Chatfragment(),null,false);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()){

                    case R.id.chat:
                        replace(new Chatfragment(),"Chatfragment",true);
                        return true;

                    case R.id.user:
                        replace(new AlluserFragment(),"AlluserFragment",true);
                        return true;

                    case R.id.image:
                        replace(new ProfileFragment(FirebaseAuth.getInstance().getUid()),"ProfileFragment",true);
                        return true;


                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(Dashboardactivity.this, Loginactivity.class));
                        finish();
                        return true;

                    case R.id.share:
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,
                                "my first app man plase check it out: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                        return true;

                    case R.id.rate:Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse("market://details?id=" + getPackageName()));
                        startActivity(i);
                        return true;





                    default:
                        return false;


                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void replace(Fragment fragment,String tag,boolean b) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        if(b)
           fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f instanceof ProfileFragment) {
                    f.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

}
