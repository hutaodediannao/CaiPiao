package com.app.caipiao.shishicai.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.app.caipiao.shishicai.R;
import com.app.caipiao.shishicai.activity.WebActivity;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserCenterFragment extends BaseFragment implements View.OnClickListener {

    private View viewItem0, viewItem1;
    private static final String aboutUrl = "http://bmob-cdn-16523.b0.upaiyun.com/2018/01/27/0a6e0d9e40aa7ccd80c83b4effd8a0ba.html";

    @Override
    int getlayout() {
        return R.layout.fragment_user_center;
    }

    @Override
    void hanldEvent() {

        initView();
        setListener();
    }

    private void initView() {
        viewItem0 = findView(R.id.item0);
        viewItem1 = findView(R.id.item1);
    }

    private void setListener() {
        viewItem0.setOnClickListener(this);
        viewItem1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item0:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        //申请WRITE_EXTERNAL_STORAGE权限
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
                    } else {
                        openCamera();
                    }
                } else {
                    openCamera();
                }
                break;
            case R.id.item1:
                openAboutActivity();
                break;
        }
    }

    //打开关于
    private void openAboutActivity() {
        WebActivity.startWebActivity(getActivity(), WebActivity.class, aboutUrl);
    }

    //二维码扫码
    private void openCamera() {
        Intent intent = new Intent(this.getActivity(), CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public static final int REQUEST_CODE = 200;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    WebActivity.startWebActivity(getActivity(), WebActivity.class, result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(this.getContext(), "不受支持的二维码类型", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                openCamera();
            } else {
                // Permission Denied
                //  displayFrameworkBugMessageAndExit();
                Toast.makeText(getActivity(), "请在应用管理中打开“相机”访问权限！", Toast.LENGTH_LONG).show();
            }
        }
    }
}
