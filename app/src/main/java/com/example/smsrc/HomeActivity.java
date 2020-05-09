package com.example.smsrc;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.smsrc.cache.CacheManager;
import com.example.smsrc.permissions.interfaces.Handler;
import com.example.smsrc.permissions.model.DeviceAdminHandler;
import com.example.smsrc.permissions.model.NotificationManagerHandler;
import com.example.smsrc.permissions.model.PermissionChain;
import com.example.smsrc.permissions.model.CompatPermissionsHandler;
import com.example.smsrc.users.dals.UserRepository;
import com.example.smsrc.users.models.User;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private PermissionChain permissionChain;
    private User user;

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

        initListeners();

        // Getting permissions at runtime
        permissionChain = new PermissionChain();
        Handler notificationManagerHandler = new NotificationManagerHandler(permissionChain, this);
        Handler deviceAdminHandler = new DeviceAdminHandler(permissionChain, this);
        Handler readPhoneStateHandler = new CompatPermissionsHandler(permissionChain, this);

        permissionChain.setHandler(notificationManagerHandler);
        notificationManagerHandler.setNext(deviceAdminHandler);
        deviceAdminHandler.setNext(readPhoneStateHandler);

        permissionChain.start();

        // Getting current user
        getAndInflateUserInfo(navigationView);
    }

    /* -------------------------------   Drawer  ------------------------------------------*/

    private void initListeners() {
        Button logoutBtn = findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(e->{
            CacheManager cacheManager = new CacheManager(this);
            cacheManager.clearUser();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void closeDrawer(){
        drawer.closeDrawer(GravityCompat.START);
    }

    /* -------------------------------   Navigation  ------------------------------------------*/
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
            case R.id.nav_restore:
                if(!navController.getCurrentDestination().getLabel().equals("Restore")) {
                    navigateTo(R.id.action_homeRouterFragment_to_restoreStateFragment);
                }
        }
        return true;
    }

    private void navigateTo(int id){
        navController.popBackStack();
        navController.navigate(id);
        closeDrawer();
    }

    private boolean isTopLevel(){
        return navController.getCurrentDestination().getLabel().equals("Pin") ||
                navController.getCurrentDestination().getLabel().equals("Users") ||
                navController.getCurrentDestination().getLabel().equals("Commands") ||
                navController.getCurrentDestination().getLabel().equals("Restore");
    }

    /* -------------------------------   Permissions  ------------------------------------------*/

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(
                requestCode == ResultCodes.REQUEST_ACTIVITY_NOTIFICATION ||
                        requestCode == ResultCodes.REQUEST_CODE_DEVICE_ADMIN
        ){
            permissionChain.next();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(
                requestCode == ResultCodes.REQUEST_ACTIVITY_NOTIFICATION ||
                requestCode == ResultCodes.REQUEST_CODE_DEVICE_ADMIN
        ){
            permissionChain.next();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /* -----------------------------  User Management  -----------------------------------*/
    private void getAndInflateUserInfo(NavigationView view) {
        CacheManager cacheManager = new CacheManager(this.getApplicationContext());
        UserRepository repository = UserRepository.getUserRepository(this.getApplicationContext());
        this.user = repository.getUserByUsername(cacheManager.getCachedUser()).get(0);
        TextView usernamePlaceholder = view.getHeaderView(0).findViewById(R.id.username_placeholder);
        TextView authRolePlaceholder = view.getHeaderView(0).findViewById(R.id.role_placeholder);
        usernamePlaceholder.setText("Username: " + this.user.getUsername());
        authRolePlaceholder.setText("Role: " + this.user.getAuthLevel());
    }

    public User getCurrentUser(){ return this.user; }
}
