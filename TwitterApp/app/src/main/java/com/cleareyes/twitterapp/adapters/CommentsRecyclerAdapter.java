package com.cleareyes.twitterapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cleareyes.twitterapp.R;
import com.cleareyes.twitterapp.entities.Comment;

import java.util.ArrayList;

public class CommentsRecyclerAdapter extends RecyclerView.Adapter<CommentsRecyclerAdapter.ViewHolder> {

    private ArrayList<Comment> commentArrayList = new ArrayList<>();

    public CommentsRecyclerAdapter(ArrayList<Comment> commentArrayList) {
        this.commentArrayList = commentArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comment, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setTag(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment = commentArrayList.get(position);
        holder.nameTextView.setText(comment.getUser().getUserLogin() + ":");
        holder.messageTextView.setText(comment.getBody());
    }

    @Override
    public int getItemCount() {
        return commentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView messageTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
        }
    }
}
