package com.example.demorecyclerview;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DataManager {
    private static final String REQUEST_URL = "https://raw.githubusercontent.com/tikivn/android-home-test/v2/keywords.json";

    private RequestKeyListener mRequestKeyListener;
    public interface RequestKeyListener {
        void onRequestSuccess();
        void onRequestFail();
    }

    public void setObserver(RequestKeyListener observer){
        mRequestKeyListener = observer;
    }
    public void removeObserver(){
        mRequestKeyListener = null;
    }

    public DataManager(){ mListKey = new ArrayList<>(); }

    // Using mListKey to cache data got from request api
    private ArrayList<String> mListKey;
    public ArrayList<String> getListKey(){ return mListKey; }

    // Parse Json from response
    private ArrayList<String> parseJson(String jsonString){
        if(TextUtils.isEmpty(jsonString)) return null;

        ArrayList<String> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for(int i = 0; i < jsonArray.length(); i++){
                String key = jsonArray.getString(i);
                list.add(key);
            }
        } catch (JSONException e) {
            Log.e("TAG", "parseJson(): Fail with Exception: " + e.toString());
            list = null;
        }

        return list;
    }

    /* --- RequestKeyTask to get data from url --- */
    private RequestKeyTask mRequestKeyTask = null;
    public void doGetRequest(){
        cancelPendingRequest();

        mRequestKeyTask = new RequestKeyTask();
        mRequestKeyTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, REQUEST_URL);
    }

    public void cancelPendingRequest(){
        if (mRequestKeyTask != null) {
            mRequestKeyTask.cancel(true);
        }
    }


    public class RequestKeyTask extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 5000;
        public static final int CONNECTION_TIMEOUT = 5000;

        @Override
        protected String doInBackground(String... params){

            String stringUrl = params[0];
            String result = null;
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);

                //Create a connection
                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();

                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();

                //Create a new InputStreamReader
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());

                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }

                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();

                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            }
            catch(Exception e){
                Log.e("TAG", "RequestKeyTask.onPostExecute(): Fail with exception = " + e.toString());
            }

            return result;
        }

        protected void onPostExecute(String result){
            super.onPostExecute(result);

            Log.d("TAG", "RequestKeyTask.doInBackground(): result = " + result);

            mListKey = parseJson(result);

            if(mListKey != null)
                mRequestKeyListener.onRequestSuccess();
            else
                mRequestKeyListener.onRequestFail();

        }
    }
    /* --- End of RequestKeyTask --- */

}
