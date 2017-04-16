package com.example.notepadby.myapplication;


import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_TYPE_NORMAL = 0;
    private static final int ITEM_TYPE_HEADER = 1;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem;
        LayoutInflater inflater;
        View view;
        switch (viewType) {
            case ITEM_TYPE_NORMAL:
                layoutIdForListItem = R.layout.recycler_item;
                inflater = LayoutInflater.from(context);

                view = inflater.inflate(layoutIdForListItem, parent, false);
                if (DataOwner.getColumns() == 5) {
                    view.setScaleX(4/5f);
                    view.setScaleY(4/5f);
                }

                return new AppViewHolder(view);
            case ITEM_TYPE_HEADER:
                layoutIdForListItem = R.layout.group_name_line;
                inflater = LayoutInflater.from(context);

                view = inflater.inflate(layoutIdForListItem, parent, false);
                return new GroupNameViewHolder(view);
            default:
                throw new IllegalArgumentException();
        }
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int itemType = getItemViewType(position);
        if (itemType == ITEM_TYPE_NORMAL) {
            ((AppViewHolder)holder).setContent(position);
        } else if (itemType == ITEM_TYPE_HEADER) {
            ((GroupNameViewHolder)holder).setHeaderText(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == DataOwner.getColumns() + 1 || position == (DataOwner.getColumns() + 1) * 2) {
            return ITEM_TYPE_HEADER;
        } else {
            return ITEM_TYPE_NORMAL;
        }
    }


    @Override
    public int getItemCount() {
        return AppManager.size();
    }

    
    class AppViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener,  View.OnCreateContextMenuListener,
            MenuItem.OnMenuItemClickListener {
        private TextView name;
        private ImageView image;

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            if (getAdapterPosition() > 2 * (DataOwner.getColumns() + 1)) {
                menu.add(R.string.delete).setOnMenuItemClickListener(this);
                menu.add(R.string.info).setOnMenuItemClickListener(this);
            }
        }

        void setContent(int pos) {
            int columns = DataOwner.getColumns();
            AppDetail app;
            if (pos <= columns) {
                app = AppManager.getPopularApp(pos - 1);
            } else if (pos < 2 * (columns + 1)) {
                app = AppManager.getNewApp(pos - columns - 2);
            } else {
                app = AppManager.getApp(pos - 2 * (columns + 1) - 1);
            }
            name.setText(app.getName());
            image.setImageDrawable(app.getIcon());
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(name.getContext(), name.getText(), Toast.LENGTH_SHORT).show();
        }

        AppViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
            name = (TextView) itemView.findViewById(R.id.recycler_view_text);
            image = (ImageView) itemView.findViewById(R.id.recycler_view_image);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getTitle().equals(DataOwner.getContext().getResources().getString(R.string.info))) {
                Toast.makeText(name.getContext(), name.getText(), Toast.LENGTH_SHORT).show();
            } else if (item.getTitle().equals(DataOwner.getContext().getResources().getString(R.string.delete))) {
                int pos = getAdapterPosition();
                AppManager.removeApp(pos - 2 * DataOwner.getColumns() - 3);
//                adapter.records.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, AppManager.size());
            }
            return true;
        }
    }

    private class GroupNameViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        GroupNameViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.group_name_line);
        }
        void setHeaderText(int listIndex) {
            if (listIndex == 0) {
                name.setText(R.string.popular_apps);
            } else if (listIndex == DataOwner.getColumns() + 1) {
                name.setText(R.string.new_apps);
            } else if (listIndex == (DataOwner.getColumns() + 1) * 2) {
                name.setText(R.string.all_apps);
            }
        }
    }
}