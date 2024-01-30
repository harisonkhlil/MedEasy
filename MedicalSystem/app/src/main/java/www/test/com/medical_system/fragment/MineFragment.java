package www.test.com.medical_system.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;

import java.util.HashMap;
import java.util.Map;

import www.test.com.medical_system.R;
import www.test.com.medical_system.activity.LoginActivity;
import www.test.com.medical_system.activity.OthersListActivity;
import www.test.com.medical_system.utils.ActivityManager;
import www.test.com.medical_system.utils.Constants;
import www.test.com.medical_system.utils.Consts;
import www.test.com.medical_system.utils.NetUtils;
import www.test.com.medical_system.utils.SPUtils;
import www.test.com.medical_system.utils.UiUtils;

/**
 * 个人中心Fragment
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    public MineFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        boolean isLogin = SPUtils.getPrefBoolean(context, Consts.IS_LOGIN, false);
        //已登录
        if (isLogin) {
            view.findViewById(R.id.btn_manage_others).setOnClickListener(this);
            view.findViewById(R.id.btn_update_pwd).setOnClickListener(this);
            view.findViewById(R.id.btn_logout).setOnClickListener(this);
//            view.findViewById(R.id.btn_login_face_picture).setOnClickListener(this);
        } else {     //未登录
            view.findViewById(R.id.btn_login).setVisibility(View.VISIBLE);
            view.findViewById(R.id.btn_login).setOnClickListener(this);
            view.findViewById(R.id.ll_button).setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:           //退出登录
                UiUtils.showConfirmDialog(context, getChildFragmentManager(), "提示", "确定退出登录？", "确定", v2 -> logout());
                break;
            case R.id.btn_login:            //去登录
                logout();
                break;
            case R.id.btn_manage_others:    //管理其他就诊人
                startActivity(new Intent(context, OthersListActivity.class));
                break;
            case R.id.btn_update_pwd:       //修改密码
                new XPopup.Builder(context).asCustom(new UpdatePwdPopup()).show();
                break;


        }
    }

    /*退出登录*/
    private void logout() {
        startActivity(new Intent(context, LoginActivity.class));
        getActivity().finish();
        ActivityManager.finishAll();
    }

    /**
     * 修改密码框
     */
    public class UpdatePwdPopup extends BottomPopupView {
        private EditText etOld;      //密码
        private EditText etPwd1;      //密码
        private EditText etPwd2;      //再次输入密码
        private Button btnSubmit;     //提交

        public UpdatePwdPopup() {
            super(context);
        }

        @Override
        protected int getImplLayoutId() {
            return R.layout.popup_update_pwd;
        }

        @Override
        protected void onCreate() {
            super.onCreate();
            etOld = findViewById(R.id.et_pwd_old);
            etPwd1 = findViewById(R.id.et_pwd_1);
            etPwd2 = findViewById(R.id.et_pwd_2);
            btnSubmit = findViewById(R.id.btn_submit);
            btnSubmit.setOnClickListener(v -> {
                if (TextUtils.isEmpty(etOld.getText())) {
                    UiUtils.showError(context, "请填写旧密码");
                    return;
                }
                if (TextUtils.isEmpty(etPwd1.getText()) || TextUtils.isEmpty(etPwd2.getText())) {
                    UiUtils.showError(context, "请输入新密码");
                    return;
                }
                if (!etPwd1.getText().toString().equals(etPwd2.getText().toString())) {
                    UiUtils.showError(context, "两次新密码输入的不一致");
                    return;
                }
                updatePwd();

            });
        }

        /*修改密码提交*/
        private void updatePwd() {
            Map<String, String> params = new HashMap<>();
            params.put("oldPassword", etOld.getText().toString());
            params.put("password", etPwd1.getText().toString());
            params.put("confirmPassword", etPwd2.getText().toString());
            NetUtils.request(context, Constants.UPDATE_PWD, params, result -> success());

        }

        /**
         * 修改成功
         */
        private void success() {
            UiUtils.showSuccess(context, "密码修改成功，请重新登录");
            dismiss();
            startActivity(new Intent(context, LoginActivity.class));
            getActivity().finish();
            ActivityManager.finishAll();
        }
    }


}























