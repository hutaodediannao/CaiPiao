package com.app.caipiao.shishicai.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.caipiao.shishicai.R;

public abstract class BaseFragment extends Fragment {

    private View mFragmentContentView;

    private static final String ARG_PARAM1 = "param1";
    public String mUrl;

    public BaseFragment() {}

    public static BaseFragment newInstance(String param1, Class fragmentCla) {
        BaseFragment fragment = null;
        try {
            fragment = (BaseFragment) fragmentCla.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentContentView = LayoutInflater.from(getContext()).inflate(getlayout(), null);
        hanldEvent();
        return mFragmentContentView;
    }

    abstract int getlayout();

    abstract void hanldEvent();

    public <T> T findView(int viewId) {
        View t = mFragmentContentView.findViewById(viewId);
        return (T) t;
    }

    public void showMesg(String msg) {
        Toast.makeText(getContext(), msg==null?"":msg, Toast.LENGTH_SHORT).show();
    }
}
