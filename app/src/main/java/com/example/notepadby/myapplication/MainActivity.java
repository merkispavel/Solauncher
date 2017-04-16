package com.example.notepadby.myapplication;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;



public class MainActivity extends AppCompatActivity {
    private GridLayoutManager mLayoutManager;
    private int gridSizeInPortrait, getGridSizeInLandscape;


    private ViewPager mainPager;
    private PagerAdapter pagerAdapter;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            DataOwner.setColumns(gridSizeInPortrait);
        } else {
            DataOwner.setColumns(getGridSizeInLandscape);
        }
        mAdapter.notifyDataSetChanged();
        mLayoutManager.setSpanCount(DataOwner.getColumns());

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent.getStringExtra(WelcomePageActivity.COLOR_THEME_ARG) == null ||
                intent.getStringExtra(WelcomePageActivity.COLOR_THEME_ARG)
                .equals(WelcomePageActivity.LIGHT_THEME_ARG)) {
            setTheme(R.style.AppThemeLight);
        } else {
            setTheme(R.style.AppThemeDark);
        }

        setContentView(R.layout.activity_main);
        DataOwner.setContext(this);
        if (intent.getStringExtra(WelcomePageActivity.GRID_SIZE_ARG) == null ||
                intent.getStringExtra(WelcomePageActivity.GRID_SIZE_ARG)
                .equals(WelcomePageActivity.STANDARD_GRID_SIZE_ARG)) {
            gridSizeInPortrait = 4;
            getGridSizeInLandscape = 6;
        } else {
            gridSizeInPortrait = 5;
            getGridSizeInLandscape = 7;
        }


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            DataOwner.setColumns(gridSizeInPortrait);
        } else {
            DataOwner.setColumns(getGridSizeInLandscape);
        }
        mLayoutManager = new GridLayoutManager(this, DataOwner.getColumns());

        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int columns = DataOwner.getColumns();
                if (position == 0 || position == columns + 1 || position == 2 * (columns + 1)) {
                    return columns;
                }
                return 1;
            }
        });


    }


    void oncreate() {
        mainPager = (ViewPager) findViewById(R.id.main_pager);
        pagerAdapter = new MainPageAdapter(getSupportFragmentManager());
        mainPager.setAdapter(pagerAdapter);
    }
    private static class MainPageAdapter extends FragmentStatePagerAdapter {
        MainPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new AllAppsFragment();
                case 1:
                    return new FavoritesFragment();
                default:
                    throw new IllegalArgumentException("Asked fragment #" + position);
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
