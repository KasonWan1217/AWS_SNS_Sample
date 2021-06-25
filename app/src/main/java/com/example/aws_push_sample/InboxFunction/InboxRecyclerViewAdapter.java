package com.example.aws_push_sample.InboxFunction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aws_push_sample.Object.InboxService.InboxRecordResponse;
import com.example.aws_push_sample.R;

import java.util.List;

public class InboxRecyclerViewAdapter extends RecyclerView.Adapter<InboxRecyclerViewAdapter.ViewHolder> {

    private List<InboxRecordResponse> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    InboxRecyclerViewAdapter(Context context, List<InboxRecordResponse> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.content_table_inbox, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_datetime.setText(mData.get(position).getMsg_timestamp());
        holder.tv_title.setText(mData.get(position).getTitle());
        if (!mData.get(position).isStatus())
            holder.img_unread_marker.setVisibility(View.GONE);
//        String animal = mData.get(position);
//        holder.myTextView.setText(animal);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_datetime , tv_title;
        ImageView img_unread_marker;

        ViewHolder(View itemView) {
            super(itemView);
            tv_datetime = (TextView) itemView.findViewById(R.id.tv_datetime);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            img_unread_marker = (ImageView) itemView.findViewById(R.id.img_unread_marker);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    InboxRecordResponse getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }



    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
