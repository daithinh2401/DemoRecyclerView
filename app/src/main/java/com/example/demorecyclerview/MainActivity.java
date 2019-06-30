package com.example.demorecyclerview;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DataManager.RequestKeyListener {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManager;
    private DataManager mDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ArrayList<String> staticData = getStaticData();

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(this, staticData);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        mDataManager = new DataManager();

//        Just use for testing only
//        AppUtils.unitTest();

    }

    @Override
    protected void onStop() {
        super.onStop();

        cancelDialog();

        mDataManager.cancelPendingRequest();
        mDataManager.removeObserver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataManager.registerObserver(this);

        doGetRequest();
    }

    private void doGetRequest(){
        showDialog();

        mDataManager.doGetRequest();
    }

    private ProgressDialog mDialog;
    private void showDialog(){
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Get data, please wait...");
        mDialog.show();
    }

    private void cancelDialog(){
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    public void onRequestSuccess() {
        cancelDialog();

        ArrayList<String> listKey = mDataManager.getListKey();
        adapter.setData(listKey);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestFail() {
        cancelDialog();

        Toast.makeText(this, "Something wrong, please try again !", Toast.LENGTH_LONG).show();

        // Do nothing
    }

    private ArrayList<String> getStaticData(){
        ArrayList<String> listKey = new ArrayList<>();

        String[] staticData = StaticData.data;
        for(String sd: staticData){
            listKey.add(sd);
        }

        return listKey;
    }
}
