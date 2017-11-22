package com.bonnetrouge.quasar;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bonnetrouge.quasar.Adapters.ScreenSlidePagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 2;

    @BindView(R.id.view_pager) ViewPager viewPager;

    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        this.viewPager.setAdapter(this.pagerAdapter);
    }
}
