package com.example.firebasetest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.firebasetest.fragment.ControlFragment;
import com.example.firebasetest.fragment.DisplayFragment;
import com.example.firebasetest.fragment.SettingFragment;
import com.example.firebasetest.fragment.chartt;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    SNavigationDrawer mSNavigationDrawer;
    Class fragmentClass;
    public static Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dashboard );

        mSNavigationDrawer = findViewById( R.id.nav );
        List<MenuItem> menuItems = new ArrayList<>();


        menuItems.add( new MenuItem( getString(R.string.display), R.drawable.img1 ) );
        menuItems.add( new MenuItem( getString(R.string.control), R.drawable.img3 ) );
        menuItems.add( new MenuItem( getString(R.string.chart), R.drawable.img3 ) );
        menuItems.add( new MenuItem( getString(R.string.logout2), R.drawable.img3 ) );


        mSNavigationDrawer.setMenuItemList( menuItems );
        fragmentClass = DisplayFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .setCustomAnimations( android.R.animator.fade_in, android.R.animator.fade_out )
                    .replace( R.id.frameLayout, fragment )
                    .commit();
        }

        mSNavigationDrawer.setOnMenuItemClickListener(
                new SNavigationDrawer.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClicked(int position) {
                        System.out.println( "Position " + position );

                        switch (position) {
                            case 0: {
                                fragmentClass = DisplayFragment.class;
                                break;
                            }

                            case 1: {
                                fragmentClass = ControlFragment.class;
                                break;
                            }
                            case 2: {
                                fragmentClass = chartt.class;
                                break;
                            }
                            case 3: {
                                fragmentClass = SettingFragment.class;

                            }

                        }

                        mSNavigationDrawer.setDrawerListener(
                                new SNavigationDrawer.DrawerListener() {

                                    @Override
                                    public void onDrawerOpened() {

                                    }

                                    @Override
                                    public void onDrawerOpening() {

                                    }

                                    @Override
                                    public void onDrawerClosing() {
                                        System.out.println( "Drawer closed" );

                                        try {
                                            fragment = (Fragment) fragmentClass.newInstance();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        if (fragment != null) {
                                            FragmentManager fragmentManager =
                                                    getSupportFragmentManager();
                                            fragmentManager.beginTransaction()
                                                    .setCustomAnimations(
                                                            android.R.animator.fade_in,
                                                            android.R.animator.fade_out )
                                                    .replace( R.id.frameLayout, fragment )
                                                    .commit();
                                        }
                                    }

                                    @Override
                                    public void onDrawerClosed() {

                                    }

                                    @Override
                                    public void onDrawerStateChanged(int newState) {
                                        System.out.println( "State " + newState );
                                    }
                                } );
                    }
                } );
    }
}
