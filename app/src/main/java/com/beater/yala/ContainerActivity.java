package com.beater.yala;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class ContainerActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavBar;
    private FrameLayout frameLayout;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        bottomNavBar = (BottomNavigationView) findViewById(R.id.bottomNavigationBar);
        frameLayout = (FrameLayout) findViewById(R.id.main_frame);

        bottomNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.profile_tab:
                        bottomNavBar.setItemBackgroundResource(R.color.colorPrimary);
                        return true;

                    case R.id.collection_tab:
                        bottomNavBar.setItemBackgroundResource(R.color.colorAccent);
                        return true;

                    case R.id.serch_tab:
                        bottomNavBar.setItemBackgroundResource(R.color.colorPrimaryDark);
                        return true;

                    default:
                        return false;
                }
            }
        });

        }

}
