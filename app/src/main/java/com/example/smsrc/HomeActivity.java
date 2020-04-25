package com.example.smsrc;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.example.smsrc.requester.Requester;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_command, R.id.nav_users, R.id.nav_pin)
                .setDrawerLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navController.navigate(R.id.action_homeRouterFragment_to_commandFragment);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if(!isTopLevel())
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_command:
                if(!navController.getCurrentDestination().getLabel().equals("Commands")) {
                    navigateTo(R.id.action_homeRouterFragment_to_commandFragment);
                }
                break;
            case R.id.nav_users:
                if(!navController.getCurrentDestination().getLabel().equals("Users")){
                    navigateTo(R.id.action_homeRouterFragment_to_usersListFragment);
                }
                break;
            case R.id.nav_pin:
                if(!navController.getCurrentDestination().getLabel().equals("Pin")) {
                    navigateTo(R.id.action_homeRouterFragment_to_pinFragment);
                }
                break;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Requester requester = new Requester(this);
        requester.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void closeDrawer(){
        drawer.closeDrawer(GravityCompat.START);
    }

    private void navigateTo(int id){
        navController.popBackStack();
        navController.navigate(id);
        closeDrawer();
    }

    private boolean isTopLevel(){
        return navController.getCurrentDestination().getLabel().equals("Pin") ||
                navController.getCurrentDestination().getLabel().equals("Users") ||
                navController.getCurrentDestination().getLabel().equals("Commands");
    }
}
