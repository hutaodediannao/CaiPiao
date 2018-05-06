package com.app.caipiao.shishicai.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.app.caipiao.shishicai.R;
import com.app.caipiao.shishicai.fragment.BaseFragment;
import com.app.caipiao.shishicai.fragment.ChildFragment;
import com.app.caipiao.shishicai.fragment.UserCenterFragment;

public class MainActivity extends BaseActivity {

    private BaseFragment caiPiaoZouShiFragment, searchMoreFragment, userCenterFragment;
    private BottomNavigationView navigation;

    //    private String url1 = "https://m.cjcp.com.cn/";


    private String url0 = "https://m.cjcp.com.cn/zst/";
    private String url1 = "https://m.cjcp.com.cn/zhuanjia/";
    private String url2 = "https://kj.cjcp.com.cn/";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changeFrdagment(caiPiaoZouShiFragment);
                    return true;
                case R.id.navigation_dashboard:
                    changeFrdagment(searchMoreFragment);
                    return true;
                case R.id.navigation_notifications:
                    changeFrdagment(userCenterFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFragment();
        setListener();
    }

    private void setListener() {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void initView() {
        navigation = findViewById(R.id.navigation);
    }

    private void initFragment() {
        caiPiaoZouShiFragment = BaseFragment.newInstance(url0, ChildFragment.class);
        searchMoreFragment = BaseFragment.newInstance(url1, ChildFragment.class);
        userCenterFragment = BaseFragment.newInstance("", UserCenterFragment.class);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentGroup, caiPiaoZouShiFragment)
                .add(R.id.fragmentGroup, searchMoreFragment)
                .add(R.id.fragmentGroup, userCenterFragment)
                .hide(caiPiaoZouShiFragment)
                .hide(searchMoreFragment)
                .hide(userCenterFragment)
                .show(caiPiaoZouShiFragment)
                .commit();
    }

    private void changeFrdagment(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .hide(caiPiaoZouShiFragment)
                .hide(searchMoreFragment)
                .hide(userCenterFragment)
                .show(fragment)
                .commit();
    }

}
