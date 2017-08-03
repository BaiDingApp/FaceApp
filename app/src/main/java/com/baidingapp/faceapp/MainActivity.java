package com.baidingapp.faceapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_INDEX =
            "com.baidingapp.faceapp.page_index";

    private int mPageIndex = 0;


    // mIsSingle can be modified in both BasicInformationDefault and BasicInformationSingle Activities
    //     So define it as a static variable
    public static boolean mIsSingle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState != null) {
            mPageIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }


        setupBottomNavigation();
    }

    private void setupBottomNavigation() {

        BottomNavigationView mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        if (mBottomNavigationView !=null) {

            // Select first menu item by default and show Fragment accordingly
            Menu menu = mBottomNavigationView.getMenu();
            selectFragment(menu.getItem(mPageIndex));

            // Set action to perform when any menu-item is selected.
            mBottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            selectFragment(item);
                            return false;
                        }
                    });
        }
    }

    /**
     * Perform action when any item is selected.
     * @param item Item that is selected.
     */
    protected void selectFragment(MenuItem item) {

        // Make the corresponding Menu Item Highlighted
        item.setChecked(true);

        switch (item.getItemId()) {
            case R.id.action_face:
                mPageIndex = 0;
                // Action to perform when Face Menu item is selected
                pushFragment(new FacePageFragment());
                break;
            case R.id.action_resume:
                mPageIndex = 1;
                // Action to perform when Resume Menu item is selected
                pushFragment(new ResumePageFragment());
                break;
            case R.id.action_game:
                mPageIndex = 2;
                // Action to perform when Game Menu item is selected
                pushFragment(new GamePageFragment());
                break;
            case R.id.action_me:
                mPageIndex = 3;
                // Action to perform when Me Menu item is selected
                // Push different fragments for different Me Pages
                if (mIsSingle) {
                    pushFragment(new MeSinglePageFragment());
                } else {
                    pushFragment(new MePageFragment());
                }
                break;
        }
    }

    /**
     * Method to push any fragment into given id.
     * @param fragment An instance of Fragment to show into the given id.
     */
    protected void pushFragment(Fragment fragment) {
        if (fragment == null)
            return;

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.fragment_container, fragment);
                ft.commit();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mPageIndex);
    }

}

























