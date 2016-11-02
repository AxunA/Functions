package com.digital.functions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


public class BaseActivity extends AppCompatActivity {

    private final static String TAG="BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showLog(String log) {
        Log.d(TAG,log);
    }

    public void showLog(String tag,String log) {
        Log.d(tag,log);
    }

    public void showToast(String content) {
        Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
    }

    public void showToast(int res) {
        Toast.makeText(this,res,Toast.LENGTH_SHORT).show();
    }


}
