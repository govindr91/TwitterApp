package com.cleareyes.twitterapp;

import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cleareyes.twitterapp.adapters.CommentsRecyclerAdapter;
import com.cleareyes.twitterapp.entities.Comment;

import java.util.ArrayList;

public class CommentsDialog extends DialogFragment {

    private RecyclerView commentsRecyclerView;
    private ImageView closeImageView;
    private ArrayList<Comment> commentArrayList = new ArrayList<>();

    static CommentsDialog newInstance(ArrayList<Comment> commentArrayList) {
        CommentsDialog commentsDialog = new CommentsDialog();
        Bundle args = new Bundle();
        args.putSerializable("comments", commentArrayList);
        commentsDialog.setArguments(args);
        return commentsDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commentArrayList = (ArrayList<Comment>) getArguments().getSerializable("comments");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_comments, container, false);
        commentsRecyclerView = (RecyclerView) view.findViewById(R.id.commentsRecyclerView);
        closeImageView = (ImageView) view.findViewById(R.id.closeImageView);
        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentsDialog.this.dismiss();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        commentsRecyclerView.setLayoutManager(linearLayoutManager);
        CommentsRecyclerAdapter commentsRecyclerAdapter = new CommentsRecyclerAdapter(commentArrayList);
        commentsRecyclerView.setAdapter(commentsRecyclerAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        int width = (int)(getResources().getDisplayMetrics().widthPixels * 0.99);
        int height = (int)(getResources().getDisplayMetrics().heightPixels * 0.70);
        getDialog().getWindow().setLayout(width, height);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
    }
}
