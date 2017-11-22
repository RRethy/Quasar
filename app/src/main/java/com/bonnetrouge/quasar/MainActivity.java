package com.bonnetrouge.quasar;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bonnetrouge.quasar.Adapters.PagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.view_pager) ViewPager viewPager;

    private android.support.v4.view.PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        this.viewPager.setAdapter(this.pagerAdapter);
    }
}
