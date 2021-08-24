package com.text.detection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.text.detection.adapters.ListAdapter;
import com.text.detection.databinding.ActivityListBinding;

public class ListActivity extends AppCompatActivity {
ActivityListBinding b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b= DataBindingUtil.setContentView(this,R.layout.activity_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listTexts();
    }

    private void listTexts() {
        b.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        b.recyclerview.setAdapter(new ListAdapter(ListActivity.this));
    }

    @Override
    public boolean onNavigateUp() {
        super.onBackPressed();
        return super.onNavigateUp();
    }
}