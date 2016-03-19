package com.cleareyes.twitterapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cleareyes.twitterapp.adapters.IssuesRecyclerAdapter;
import com.cleareyes.twitterapp.entities.Comment;
import com.cleareyes.twitterapp.entities.Issue;
import com.cleareyes.twitterapp.misc.Utilities;
import com.cleareyes.twitterapp.webservice.FetchIssuesService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView issuesRecyclerView;
    private IssuesRecyclerAdapter issuesRecyclerAdapter;

    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar;

    private ProgressDialog progressDialog;
    private CoordinatorLayout coordinatorLayout;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private LinearLayout drawerLinearLayout;
    private LinearLayout settingsLinearLayout;

    private LinearLayout emptyStateLinearLayout;
    private Button retryButton;
    private Snackbar snackbar = null;

    private ArrayList<Issue> issueArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
            emptyStateLinearLayout = (LinearLayout) findViewById(R.id.emptyStateLinearLayout);
            retryButton = (Button) findViewById(R.id.tryAgainButton);
            retryButton.setOnClickListener(this);

            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawerLinearLayout = (LinearLayout) findViewById(R.id.drawerLinearLayout);
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                    R.string.drawer_open, R.string.drawer_close) {

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    /*if (isLinkClicked) {
                        isLinkClicked = false;
                        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                    }*/
                }
            };
            actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
            drawerLayout.setDrawerListener(actionBarDrawerToggle);

            issuesRecyclerView = (RecyclerView) findViewById(R.id.issuesRecyclerView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            issuesRecyclerView.setLayoutManager(linearLayoutManager);
            issuesRecyclerAdapter = new IssuesRecyclerAdapter(this, issueArrayList, new IssuesRecyclerAdapter.IssuesClickListener() {
                @Override
                public void onClick(int position, Issue issue) {
                    fetchComment(issue);
                }
            });
            issuesRecyclerView.setAdapter(issuesRecyclerAdapter);

            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            fetchIssues();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    private void fetchComment(Issue issue) {
        if(issue.getComments() > 0) {
            progressDialog.setMessage("Please wait. Fetching all comments...");
            progressDialog.show();
            FetchIssuesService fetchIssuesService = new FetchIssuesService(MainActivity.this);
            fetchIssuesService.fetchCommentsForIssue(issue.getCommentsURL(), new FetchIssuesService.FetchCommentsListener() {
                @Override
                public void onSuccess(String response) {
                    ArrayList<Comment> comments = new Gson().fromJson(response, new TypeToken<ArrayList<Comment>>() {}.getType());
                    if(comments != null) {
                            dismissProgressDialog();
                            CommentsDialog commentsDialog = CommentsDialog.newInstance(comments);
                            commentsDialog.show(getFragmentManager(), "twitter");
                            return;
                    }
                    else {
                        dismissProgressDialog();
                        showErrorSnackBar();
                    }
                }

                @Override
                public void onError() {
                    dismissProgressDialog();
                    if(!Utilities.isNetworkAvailable(MainActivity.this)) {
                        showInternetErrorSnackBar();
                    }
                    else {
                        showErrorSnackBar();
                    }
                }
            });
        }
        else {
            Toast.makeText(MainActivity.this, "No comments available for this issue", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchIssues() {
        progressDialog.setMessage("Please wait. Fetching all issues...");
        progressDialog.show();
        FetchIssuesService fetchIssuesService = new FetchIssuesService(this);
        fetchIssuesService.fetchAllIssues(new FetchIssuesService.FetchIssuesListener() {
            @Override
            public void onSuccess(String response) {
                ArrayList<Issue> issues = new Gson().fromJson(response, new TypeToken<ArrayList<Issue>>() {}.getType());
                if(issues != null) {
                    emptyStateLinearLayout.setVisibility(View.GONE);
                    issuesRecyclerView.setVisibility(View.VISIBLE);
                    issueArrayList.addAll(Utilities.sortIssuesByLastUpdated(issues));
                    issuesRecyclerAdapter.notifyDataSetChanged();
                    dismissProgressDialog();
                }
                else {
                    dismissProgressDialog();
                    showErrorSnackBar();
                }
            }

            @Override
            public void onError() {
                dismissProgressDialog();
                if(!Utilities.isNetworkAvailable(MainActivity.this)) {
                    emptyStateLinearLayout.setVisibility(View.VISIBLE);
                    issuesRecyclerView.setVisibility(View.GONE);
                }
                else {
                    showErrorSnackBar();
                }
            }
        });
    }

    private void showErrorSnackBar() {
        snackbar = Snackbar.make(coordinatorLayout, "Looks like some error occurred. Please try again later...", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void showInternetErrorSnackBar() {
        snackbar = Snackbar.make(coordinatorLayout, "Please check your internet connection and try again.", Snackbar.LENGTH_INDEFINITE)
                .setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
        snackbar.show();
    }

    private void dismissProgressDialog() {
        try {
            if(progressDialog != null) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        }
        catch (Exception e) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tryAgainButton: {
                fetchIssues();
            }
        }
    }
}
