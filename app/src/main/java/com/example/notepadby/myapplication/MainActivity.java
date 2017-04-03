package com.example.notepadby.myapplication;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;



public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private int gridSizeInPortrait, getGridSizeInLandscape;



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
        if (intent.getStringExtra(WelcomePageActivity.COLOR_THEME_ARG)
                .equals(WelcomePageActivity.LIGHT_THEME_ARG)) {
            setTheme(R.style.AppThemeLight);
        } else {
            setTheme(R.style.AppThemeDark);
        }

        setContentView(R.layout.activity_main);
        DataOwner.setContext(this);
        if (intent.getStringExtra(WelcomePageActivity.GRID_SIZE_ARG)
                .equals(WelcomePageActivity.STANDARD_GRID_SIZE_ARG)) {
            gridSizeInPortrait = 4;
            getGridSizeInLandscape = 6;
        } else {
            gridSizeInPortrait = 5;
            getGridSizeInLandscape = 7;
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            DataOwner.setColumns(gridSizeInPortrait);
        } else {
            DataOwner.setColumns(getGridSizeInLandscape);
        }
        mLayoutManager = new GridLayoutManager(this, DataOwner.getColumns());
        mRecyclerView.setLayoutManager(mLayoutManager);

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

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                int count = parent.getChildCount();
                for(int i = 0; i < count; i++) {
                    View child = parent.getChildAt(i);
                    int position = parent.getChildAdapterPosition(child);
                    if(position  == 2 * mLayoutManager.getSpanCount() || position == mLayoutManager.getSpanCount()) {
                        drawBackground(c, parent, i);
                    }
                }
            }

            private void drawBackground(Canvas c, RecyclerView parent, int index) {
                int l = parent.getLeft();
                int t = parent.getChildAt(index).getTop();
                int r = parent.getRight();
                int b = parent.getChildAt(index).getBottom();

                Paint mPaint = new Paint();
                mPaint.setARGB(100, 255, 255, 0);
                c.drawRect(l, t, r, b, mPaint);
            }
        });

        int numberOfApps = intent.getIntExtra(WelcomePageActivity.NUMBER_OF_APPS_ARG, 10);
        AppManager.setNumberOfApps(numberOfApps);

        mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }
}
