package com.beater.yala;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.beater.yala.fragments.AlbumDetailsFragment;
import com.beater.yala.fragments.CollectionFragment;
import com.beater.yala.fragments.EstadisticasFragment;
import com.beater.yala.fragments.ProfileFragment;
import com.beater.yala.fragments.SearchFragment;
import com.beater.yala.interfaces.IComunicaFragments;
import com.beater.yala.model.Album;

public class ContainerActivity extends AppCompatActivity implements IComunicaFragments{

    private BottomNavigationView bottomNavBar;
    private FrameLayout frameLayout;

    private ProfileFragment profileFragment;
    private CollectionFragment collectionFragment;
    private SearchFragment searchFragment;
    private EstadisticasFragment statsFragment;
    private AlbumDetailsFragment albumFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        profileFragment = new ProfileFragment();
        collectionFragment =  new CollectionFragment();
        searchFragment = new SearchFragment();
        statsFragment = new EstadisticasFragment();

        bottomNavBar = (BottomNavigationView) findViewById(R.id.bottomNavigationBar);
        frameLayout = (FrameLayout) findViewById(R.id.main_frame);

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

                    case R.id.search_tab:
                        setFragment(searchFragment);
                        return true;
                    case R.id.stats_tab:
                        setFragment(statsFragment);
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

    @Override
    public void enviarAlbum(Album album) {
        albumFragment = new AlbumDetailsFragment();
        Bundle datos = new Bundle();
        datos.putSerializable("album", album);
        albumFragment.setArguments(datos );

        //cargar el fragment en el activity

        getSupportFragmentManager().
                beginTransaction().replace(R.id.main_frame,albumFragment).addToBackStack(null).commit();
    }
}
