package com.example.notepadby.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;


public class WelcomePageActivity extends FragmentActivity {
    public static final String NUMBER_OF_APPS_ARG = "number of apps";
    public static final String GRID_SIZE_ARG = "grid size";
    public static final String COLOR_THEME_ARG = "color theme";
    public static final String STANDARD_GRID_SIZE_ARG = "standard_size";
    public static final String LARGE_GRID_SIZE_ARG = "large_size";
    public static final String LIGHT_THEME_ARG = "light_theme";
    public static final String DARK_THEME_ARG = "dark_theme";
    public static final String FIRST_START = "first start";

    private static int[] layoutId;
    private NoSwipeViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private Bundle arguments = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getBoolean(FIRST_START, false)) {
            Intent startMainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
//            startMainActivityIntent.putExtras(arguments);
            startActivity(startMainActivityIntent);
        }
        preferences.edit().putBoolean(FIRST_START, true).apply();

        layoutId = new int[]{R.layout.welcome1,
                            R.layout.welcome2,
                            R.layout.welcome3,
                            R.layout.welcome4,
                            R.layout.welcome5};

        mPager = (NoSwipeViewPager) findViewById(R.id.welcome_view_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);



        CirclePageIndicator circleIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        circleIndicator.setRadius(20);
        circleIndicator.setFillColor(getResources().getColor(R.color.colorGreen));
        circleIndicator.setViewPager(mPager);

        Button nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = mPager.getCurrentItem();
                if (currentItem == layoutId.length - 1) {
                    try {
                        EditText editText = (EditText) mPager.findViewById(R.id.number_edit_text);
                        int numberOfApps = Integer.parseInt(editText.getText().toString());
                        if (numberOfApps <= 0) {
                            throw new NumberFormatException();
                        }
                        arguments.putInt(NUMBER_OF_APPS_ARG, numberOfApps);
                        Intent startMainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startMainActivityIntent.putExtras(arguments);
                        startActivity(startMainActivityIntent);
                    } catch (NumberFormatException e) {
                        Toast.makeText(WelcomePageActivity.this, R.string.invalid_apps_number, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    switch (currentItem) {
                        case 2: //Theme
                            RadioGroup themeGroup =
                                    (RadioGroup) mPager.findViewById(R.id.choose_theme_group);
                            int checkedId = themeGroup.getCheckedRadioButtonId();
                            if (checkedId == -1) {
                                Toast.makeText(WelcomePageActivity.this, R.string.welcome3_text, Toast.LENGTH_SHORT).show();
                                return;
                            } else if (checkedId == R.id.light_theme_button) {
                                arguments.putString(COLOR_THEME_ARG, LIGHT_THEME_ARG);
                            } else {
                                arguments.putString(COLOR_THEME_ARG, DARK_THEME_ARG);
                            }
                            break;
                        case 3: //Grid Size
                            RadioGroup sizeGroup =
                                    (RadioGroup) mPager.findViewById(R.id.choose_size_group);
                            checkedId = sizeGroup.getCheckedRadioButtonId();
                            if (checkedId == -1) {
                                Toast.makeText(WelcomePageActivity.this, R.string.choose_grid_size, Toast.LENGTH_SHORT).show();
                                return;
                            } else if (checkedId == R.id.standard_size_button) {
                                arguments.putString(GRID_SIZE_ARG, STANDARD_GRID_SIZE_ARG);
                            } else {
                                arguments.putString(GRID_SIZE_ARG, LARGE_GRID_SIZE_ARG);
                            }
                            break;
                    }
                    mPager.setCurrentItem(currentItem + 1);
                }
            }
        });


    }
    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }


    public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public static final String PAGE_NUMBER_ARG = "page number argument";
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args = new Bundle();
            args.putInt(PAGE_NUMBER_ARG, position);
            ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return layoutId.length;
        }
    }

    public static class ScreenSlidePageFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            int pos = getArguments().getInt(ScreenSlidePagerAdapter.PAGE_NUMBER_ARG);
            ViewGroup rootView = (ViewGroup) inflater.inflate(layoutId[pos], container, false);
            return rootView;
        }
    }
}
