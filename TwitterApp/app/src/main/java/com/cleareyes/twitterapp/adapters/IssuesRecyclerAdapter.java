package com.cleareyes.twitterapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cleareyes.twitterapp.R;
import com.cleareyes.twitterapp.entities.Issue;
import com.cleareyes.twitterapp.misc.Utilities;

import java.util.ArrayList;

public class IssuesRecyclerAdapter extends RecyclerView.Adapter<IssuesRecyclerAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<Issue> issueArrayList = new ArrayList<>();
    private IssuesClickListener issuesClickListener;

    public interface IssuesClickListener {
        public void onClick(int position, Issue issue);
    }

    public IssuesRecyclerAdapter(Context context, ArrayList<Issue> issuesArrayList, IssuesClickListener issuesClickListener) {
        this.context = context;
        this.issueArrayList = issuesArrayList;
        this.issuesClickListener = issuesClickListener;
    }

    @Override
    public IssuesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_issue, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setTag(viewHolder);
        itemView.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IssuesRecyclerAdapter.ViewHolder holder, int position) {
        Issue issue = issueArrayList.get(position);
        if(issue != null) {
            holder.issueTitleTextView.setText("Issue: " + issue.getIssueTitle());
            holder.issueBodyTextView.setText(issue.getBody().replaceAll("\\\n", " "));
            holder.issueNumberTextView.setText(String.valueOf("Issue No: " + issue.getIssueNumber()));
            String date = Utilities.getDateString(issue.getUpdatedAtTimeStamp());
            holder.issueLastUpdatedTextView.setText(date);
        }
    }

    @Override
    public int getItemCount() {
        return issueArrayList.size();
    }

    @Override
    public void onClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag();
        int position = viewHolder.getAdapterPosition();
        issuesClickListener.onClick(position, issueArrayList.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView issueTitleTextView;
        private TextView issueBodyTextView;
        private TextView issueNumberTextView;
        private TextView issueLastUpdatedTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            issueTitleTextView = (TextView) itemView.findViewById(R.id.issueTitleTextView);
            issueBodyTextView = (TextView) itemView.findViewById(R.id.issueBodyTextView);
            issueNumberTextView = (TextView) itemView.findViewById(R.id.issueNumberTextView);
            issueLastUpdatedTextView = (TextView) itemView.findViewById(R.id.lastUpdatedTextView);
        }
    }

}
