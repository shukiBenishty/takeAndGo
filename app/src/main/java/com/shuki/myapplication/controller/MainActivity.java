package com.shuki.myapplication.controller;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shuki.myapplication.R;
import com.shuki.myapplication.backend.BackendFactory;
import com.shuki.myapplication.backend.DataSource;
import com.shuki.myapplication.entities.Branch;
import com.shuki.myapplication.entities.Car;
import com.shuki.myapplication.entities.CarModel;
import com.shuki.myapplication.entities.Order;
import com.shuki.myapplication.fragments.BranchsFragment;
import com.shuki.myapplication.fragments.CarsFragment;
import com.shuki.myapplication.fragments.OrdersFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static DataSource dataSource =  BackendFactory.getDataSource();
    private static ArrayList<Order> orderList = new ArrayList<Order>();;
    private static ArrayList<Branch> branchList = dataSource.getBranchList();
    private static ArrayList<Car> carList = dataSource.getCarList();
    private static ArrayList<CarModel> carModelList = dataSource.getCarModelList();

    public static Adapter adapter;
    public static OrdersFragment ordersFragment;
    public static CarsFragment carsFragment;
    public static BranchsFragment brancesFragment;
    private DrawerLayout mDrawerLayout;
    public static TabLayout tabs;
    TabLayout.Tab tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



//        startService(new Intent(getBaseContext(), MyService.class)); com.example.shuki.takeandgo.MY_CUSTOM_INTENT
        setContentView(R.layout.activity_main);

        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        // Set Tabs inside Toolbar
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        // Create Navigation drawer and inlfate layout
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            VectorDrawableCompat indicator =
                    VectorDrawableCompat.create(getResources(), R.drawable.ic_menu, getTheme());
            indicator.setTint(ResourcesCompat.getColor(getResources(),R.color.white,getTheme()));
            supportActionBar.setHomeAsUpIndicator(indicator);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        // Set behavior of Navigation drawer
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Set item in checked state
//                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()) {
                            case R.id.nav_about:
                                menuItem.setChecked(true);
                                break;
                            case R.id.nav_available_car:
                                menuItem.setChecked(true);
                                tab = tabs.getTabAt(2);
                                tab.select();
                                break;
                            case R.id.nav_my_car:
                                menuItem.setChecked(true);
                                TabLayout.Tab tab = tabs.getTabAt(0);
                                tab.select();
                                break;
                            case R.id.nav_send:
                                sendEmail();
                                break;
                            case R.id.nav_phone:
                                phone();
                                break;
                            case R.id.nav_exit:
                                finish();
                                System.exit(0);
                                return true;
                        }

                        // Closing drawer on item click
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
//        // Adding Floating Action Button to bottom right of main view
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Hello Snackbar!",
                      Snackbar.LENGTH_LONG).show();
                        Notification.Builder nBuilder = new Notification.Builder(getBaseContext());
                        nBuilder.setSmallIcon(R.drawable.ic_location);
                        nBuilder.setContentTitle("Notification Title");
                        nBuilder.setContentText("Notification Content");
                        Object obj = getSystemService(Context.NOTIFICATION_SERVICE);
                        NotificationManager notificationManager = (NotificationManager)obj;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            notificationManager.notify(1234, nBuilder.build());
                        }
                        TabLayout.Tab tab = tabs.getTabAt(2);
                        tab.select();
            }
        });
    }
    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        adapter = new Adapter(getSupportFragmentManager());

        ordersFragment =  new OrdersFragment(orderList, branchList, carList,  carModelList);
        adapter.addFragment(ordersFragment, "My Order");

        brancesFragment = new BranchsFragment(branchList);
        adapter.addFragment(brancesFragment, "Branchs");

        carsFragment = new CarsFragment(carList,  carModelList);
        adapter.addFragment(carsFragment, "Cars");

        viewPager.setAdapter(adapter);

    }

    public static class Adapter extends FragmentPagerAdapter {
        public final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public final FragmentManager _manager;

        public Adapter(FragmentManager manager) {
            super(manager);
            _manager = manager;
        }

        @Override
        public Fragment getItem(int position)  {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }


    public void sendEmail() {


        String[] TO = {"shuki.benishty@gmail.com"};

        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.setType("application/octet-stream");
        try {

            startActivity(Intent.createChooser(emailIntent, "Connect us"));

        } catch (android.content.ActivityNotFoundException ex) {

        }
    }

    public void phone() {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "0546592374", null)));
    }

}

