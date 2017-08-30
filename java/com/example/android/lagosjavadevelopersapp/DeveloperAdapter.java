package com.example.android.lagosjavadevelopersapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by Admin on 22/08/2017.
 */


public class DeveloperAdapter extends RecyclerView.Adapter<DeveloperAdapter.DeveloperViewHolder>{

    List<Developer> mList;
    private ListItemClickListener mOnclickListener;


    public interface ListItemClickListener {
        void onListItemClick(Developer clickedItem);
    }

    public class DeveloperViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mUserName;
        private ImageView mUserImage;

        public DeveloperViewHolder(View v) {
            super(v);

            mUserImage = (ImageView) v.findViewById(R.id.image);
            mUserName = (TextView) v.findViewById(R.id.name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnclickListener.onListItemClick(mList.get(clickedPosition));
        }
    }

    public DeveloperAdapter( List<Developer>developer, ListItemClickListener itemClickListener) {
        mOnclickListener = itemClickListener;
        mList = developer;

    }

    @Override
    public DeveloperAdapter.DeveloperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.developer_list_item, parent, false);

            return new DeveloperViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DeveloperAdapter.DeveloperViewHolder holder, int position) {
        final Developer developer = mList.get(position);
        holder.mUserName.setText(developer.getmName());
        Picasso.with(holder.itemView.getContext()).load(developer.getmProfilePic()).into(holder.mUserImage);


    }

    @Override
    public int getItemCount() {

        return mList.size();



    }

}


