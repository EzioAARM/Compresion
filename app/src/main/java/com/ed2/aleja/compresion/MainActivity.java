package com.ed2.aleja.compresion;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar_navigation);
        toolbar.setTitle(R.string.my_comp);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.nav_open, R.string.nav_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new CompresosFragment()).commit();
            navigationView.setCheckedItem(R.id.compresiones);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.compresiones:
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new CompresosFragment()).commit();
                toolbar.setTitle(R.string.my_comp);
                break;
            case R.id.comprimir:
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new ComprimirFragment()).commit();
                toolbar.setTitle(R.string.comprimir);
                break;
            case R.id.descomprimir:
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new DescomprimirFragment()).commit();
                toolbar.setTitle(R.string.descomprimir);
                break;
        }
        navigationView.setCheckedItem(item.getItemId());
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            // Cierra el activity Drawer
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // Otra accion cuando se presione regresar
            super.onBackPressed();
        }
    }
}
