package com.cleareyes.twitterapp.misc;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import com.cleareyes.twitterapp.entities.Issue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.TimeZone;

public class Utilities {

    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null){
            result += line;
        }

            /* Close Stream */
        if(null!=inputStream){
            inputStream.close();
        }
        return result;
    }

    public static String getDateString(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = null;
        try {
            value = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        dateFormatter.setTimeZone(TimeZone.getDefault());
        String dateTime = dateFormatter.format(value);
        return dateTime;
    }

    public static ArrayList<Issue> addDateToList(ArrayList<Issue> issues) {
        for (Issue issue :
                issues) {
            String dateString = issue.getUpdatedAtTimeStamp();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = null;
            try {
                value = simpleDateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            issue.setConvertedDate(value);
        }
        return issues;
    }

    public static ArrayList<Issue> sortIssuesByLastUpdated(ArrayList<Issue> issues) {
        issues = addDateToList(issues);
        Collections.sort(issues, new Comparator<Issue>() {
            @Override
            public int compare(Issue lhs, Issue rhs) {
                return lhs.getConvertedDate().compareTo(rhs.getConvertedDate());
            }
        });
        return issues;
    }

    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
                Network[]  networks = connectivity.getAllNetworks();
                NetworkInfo networkInfo;
                for (Network network : networks) {
                    networkInfo = connectivity.getNetworkInfo(network);
                    if(networkInfo.getState().equals(NetworkInfo.State.CONNECTED))
                        return true;
                }
            }
            else {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState().equals(NetworkInfo.State.CONNECTED))
                            return true;
            }

        }
        return false;
    }

}
