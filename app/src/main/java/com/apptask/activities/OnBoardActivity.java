package com.apptask.activities;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;

import com.apptask.fragment.BoardFragment;
import com.apptask.R;
import com.badoualy.stepperindicator.StepperIndicator;

public class OnBoardActivity extends AppCompatActivity implements View.OnClickListener{

    SectionPagerAdapter adapter;

    private ViewPager mViewPager;
    private StepperIndicator mStepper;
    private TextView mNextBtn;
    private TextView mSkipBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);
        init();

    }

    private void init() {
        initViewPager();

        mNextBtn = findViewById(R.id.intro_next_btn);
        mNextBtn.setOnClickListener(this);
        mSkipBtn = findViewById(R.id.intro_skip_btn);
        mSkipBtn.setOnClickListener(this);

    }

    private void initViewPager() {
        adapter = new SectionPagerAdapter(getSupportFragmentManager());
        mStepper = findViewById(R.id.intro_stepper);
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                onPageChanged(i);
                onPageSkip(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        mStepper.setViewPager(mViewPager, adapter.getCount());
    }


    private void onPageChanged(int position) {
        String btnTextNext = "Next";
        if (position == 2) {
            btnTextNext = "Finish";
        }
        mNextBtn.setText(btnTextNext);
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("settings",MODE_PRIVATE);
               preferences.edit().putBoolean("isShown",true).apply();
                startActivity(new Intent(OnBoardActivity.this,MainActivity.class));
                finish();
            }
        });

    }

    private void onPageSkip(int position){
        String btnTextFinish = "Skip";
        if(position==2) {
            btnTextFinish = "";
        }
        mSkipBtn.setText(btnTextFinish);
    }

    private void onNextClick() {
        if (mViewPager.getCurrentItem() == adapter.getCount() - 1) {
            finish();
        } else {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.intro_next_btn:
                onNextClick();
                break;
            case R.id.intro_skip_btn:

                finish();
        }
    }

    public class SectionPagerAdapter extends FragmentPagerAdapter {
        private final int PAGES_COUNT = 3;

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Bundle bundle = new Bundle();
            bundle.putInt("pos", i);
            BoardFragment boardFragment = new BoardFragment();
            boardFragment.setArguments(bundle);
            return boardFragment;


        }

        @Override
        public int getCount() {
            return PAGES_COUNT;
        }
    }

    }
