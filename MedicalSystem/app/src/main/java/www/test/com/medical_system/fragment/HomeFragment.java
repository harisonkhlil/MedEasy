package www.test.com.medical_system.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import www.test.com.medical_system.R;
import www.test.com.medical_system.activity.AppointmentActivity;
import www.test.com.medical_system.activity.GuideActivity;
import www.test.com.medical_system.activity.IntroActivity;
import www.test.com.medical_system.activity.RecordListActivity;

/**
 * 首页Fragment
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout llYyjs;        //医院介绍
    private LinearLayout llZzdz;        //自助导诊
    private LinearLayout llYygh;        //预约挂号
    private LinearLayout llMzjl;        //门诊记录


    public HomeFragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        llYyjs = view.findViewById(R.id.ll_yyjs);
        llZzdz = view.findViewById(R.id.ll_zzdz);
        llYygh = view.findViewById(R.id.ll_yygh);
        llMzjl = view.findViewById(R.id.ll_mzjl);
        llYyjs.setOnClickListener(this);
        llZzdz.setOnClickListener(this);
        llYygh.setOnClickListener(this);
        llMzjl.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_yyjs:
                startActivity(new Intent(getActivity(), IntroActivity.class));
                break;
            case R.id.ll_zzdz:
                startActivity(new Intent(getActivity(), GuideActivity.class));
                break;
            case R.id.ll_yygh:
                startActivity(new Intent(getActivity(), AppointmentActivity.class));
                break;
            case R.id.ll_mzjl:
                startActivity(new Intent(getActivity(), RecordListActivity.class));
                break;
        }
    }

}
