package com.example.notepadby.myapplication;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AllAppsFragment extends Fragment {
    private static GridLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;



    public AllAppsFragment() {
        super();
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.all_apps_recycler);
        mRecyclerView.setHasFixedSize(true);

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

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_apps_grid, container, false);
    }

    public static void setmLayoutManager(GridLayoutManager mLayoutManager) {
        AllAppsFragment.mLayoutManager = mLayoutManager;
    }

    private static GridLayoutManager
}
