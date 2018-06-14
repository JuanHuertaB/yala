package com.beater.yala;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.beater.yala.fragments.CollectionFragment;
import com.beater.yala.fragments.ProfileFragment;
import com.beater.yala.fragments.SearchFragment;
import com.beater.yala.model.Coleccionista;

public class ContainerActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavBar;
    private FrameLayout frameLayout;

    private FloatingActionButton fabAddAlbum;
    private ProfileFragment profileFragment;
    private CollectionFragment collectionFragment;
    private SearchFragment searchFragment;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        profileFragment = new ProfileFragment();
        collectionFragment =  new CollectionFragment();
        searchFragment = new SearchFragment();

        bottomNavBar = (BottomNavigationView) findViewById(R.id.bottomNavigationBar);
        frameLayout = (FrameLayout) findViewById(R.id.main_frame);
        fabAddAlbum = (FloatingActionButton) findViewById(R.id.fab_id);

        showToolbar(getResources().getString(R.string.toolbar_title_createAccount), true);

        Bundle objetoEnviado = getIntent().getExtras();
        Coleccionista coleccionista = null;



        //Primer fragmento en mostrar
        setFragment(collectionFragment);
        bottomNavBar.setSelectedItemId(R.id.collection_tab);

        bottomNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.profile_tab:
                        setFragment(profileFragment);
                        return true;

                    case R.id.collection_tab:
                        setFragment(collectionFragment);
                        return true;

                    case R.id.serch_tab:
                        setFragment(searchFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });

        }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }


}
