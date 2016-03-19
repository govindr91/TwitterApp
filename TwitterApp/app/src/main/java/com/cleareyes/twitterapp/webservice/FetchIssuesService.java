package com.cleareyes.twitterapp.webservice;

import android.content.Context;
import android.os.AsyncTask;

import com.cleareyes.twitterapp.entities.AsyncCallType;
import com.cleareyes.twitterapp.entities.AsyncReturnType;
import com.cleareyes.twitterapp.misc.Enums;
import com.cleareyes.twitterapp.misc.Utilities;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchIssuesService {

    public interface FetchIssuesListener {
        public void onSuccess(String response);
        public void onError();
    }

    public interface FetchCommentsListener {
        public void onSuccess(String response);
        public void onError();
    }

    private FetchIssuesListener fetchIssuesListener;
    private FetchCommentsListener fetchCommentsListener;
    private Context context;

    public FetchIssuesService(Context context) {
        this.context = context;
    }

    public void fetchAllIssues(FetchIssuesListener fetchIssuesListener) {
        this.fetchIssuesListener = fetchIssuesListener;
        AsyncCallType asyncCallType = new AsyncCallType();
        asyncCallType.setCallType(Enums.CallType.getIssues);
        asyncCallType.setUrl("https://api.github.com/repos/rails/rails/issues");
        new AsyncHttpTask().execute(asyncCallType);
    }

    public void fetchCommentsForIssue(String url, FetchCommentsListener fetchCommentsListener) {
        this.fetchCommentsListener = fetchCommentsListener;
        AsyncCallType asyncCallType = new AsyncCallType();
        asyncCallType.setCallType(Enums.CallType.getComments);
        asyncCallType.setUrl(url);
        new AsyncHttpTask().execute(asyncCallType);
    }

    public class AsyncHttpTask extends AsyncTask<AsyncCallType, Void, AsyncReturnType> {

        @Override
        protected AsyncReturnType doInBackground(AsyncCallType... params) {
            InputStream inputStream = null;
            HttpURLConnection urlConnection = null;
            Integer result = 0;
            AsyncReturnType asyncReturnType = null;
            try {
                /* forming th java.net.URL object */
                AsyncCallType asyncCallType = params[0];
                URL url = new URL(asyncCallType.getUrl());
                urlConnection = (HttpURLConnection) url.openConnection();

                 /* optional request header */
                urlConnection.setRequestProperty("Content-Type", "application/json");

                /* optional request header */
                urlConnection.setRequestProperty("Accept", "application/json");

                /* for Get request */
                urlConnection.setRequestMethod("GET");
                int statusCode = urlConnection.getResponseCode();

                asyncReturnType = new AsyncReturnType();
                asyncReturnType.setCallType(asyncCallType.getCallType());
                /* 200 represents HTTP OK */
                if (statusCode == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    String response = Utilities.convertInputStreamToString(inputStream);
                    asyncReturnType.setResponse(response);
                    return asyncReturnType;
                } else {
                    return asyncReturnType;
                }
            } catch (Exception e) {
                return asyncReturnType;
            }
        }

        @Override
        protected void onPostExecute(AsyncReturnType asyncReturnType) {
            super.onPostExecute(asyncReturnType);
            if(asyncReturnType != null) {
                switch (asyncReturnType.getCallType()) {
                    case getComments: {
                        if (asyncReturnType.getResponse() != null)
                            fetchCommentsListener.onSuccess(asyncReturnType.getResponse());
                        else
                            fetchCommentsListener.onError();
                    }
                    break;

                    case getIssues: {
                        if (asyncReturnType.getResponse() != null)
                            fetchIssuesListener.onSuccess(asyncReturnType.getResponse());
                        else
                            fetchIssuesListener.onError();
                    }
                    break;
                }
            }
            else {
                if(fetchCommentsListener != null)
                    fetchCommentsListener.onError();
                else if(fetchIssuesListener != null)
                    fetchIssuesListener.onError();;
            }
        }

    }

}
