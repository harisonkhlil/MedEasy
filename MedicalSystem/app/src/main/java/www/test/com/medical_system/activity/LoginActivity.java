package www.test.com.medical_system.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

import www.test.com.medical_system.R;
import www.test.com.medical_system.bean.CommonResult;
import www.test.com.medical_system.utils.Constants;
import www.test.com.medical_system.utils.Consts;
import www.test.com.medical_system.utils.NetUtils;
import www.test.com.medical_system.utils.SPUtils;
import www.test.com.medical_system.utils.TimeUtil;
import www.test.com.medical_system.utils.UiUtils;
import www.test.com.medical_system.utils.ValidateUtil;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    /*用户名*/
    private MaterialEditText metUsername;
    /*密码*/
    private MaterialEditText metPwd;
    /*登录按钮*/
    private Button btnLogin;
    /*记住密码*/
    private Switch swRememberPwd;
    /*暂不登录*/
    private TextView tvYouke, face_login;
    /*注册新用户*/
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_login);
        ImmersionBar.with(this).init();
        initView();
    }

    /**
     * 初始化页面组件
     */
    private void initView() {
        metUsername = findViewById(R.id.met_username);
        metPwd = findViewById(R.id.met_pwd);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        swRememberPwd = findViewById(R.id.sw_remember_pwd);
        boolean rememberPwd = SPUtils.getPrefBoolean(context, Consts.REMEMBER_PWD, false);
        swRememberPwd.setChecked(rememberPwd);
        swRememberPwd.setOnCheckedChangeListener(((buttonView, isChecked) -> SPUtils.setPrefBoolean(context, Consts.REMEMBER_PWD, isChecked)));
        if (rememberPwd) {
            metUsername.setText(SPUtils.getPrefString(this, Consts.USERNAME, ""));
            metPwd.setText(SPUtils.getPrefString(this, Consts.PASSWORD, ""));
        }
        tvYouke = findViewById(R.id.tv_youke);
        tvYouke.setOnClickListener(this);
        tvRegister = findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(this);
        face_login = findViewById(R.id.face_login);
        face_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (ValidateUtil.isStringValid(Constants.BASE_URL)) {
                    if (!TextUtils.isEmpty(metUsername.getText()) && !TextUtils.isEmpty(metPwd.getText())) {
                        login();
                    } else {
                        UiUtils.showError(context, "请先填写用户名和密码");
                    }
                } else {
                    UiUtils.showKnowDialog(context, "提示", "无法连接到服务器");
                }
                break;
            case R.id.tv_youke:
                SPUtils.setPrefBoolean(context, Consts.IS_LOGIN, false);
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_register:
                new XPopup.Builder(context).asCustom(new RegisterPopup()).show();
                break;
            case R.id.face_login:
                startActivity(new Intent(context, FaceActivity.class));
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        String userName = metUsername.getText().toString();
        String password = metPwd.getText().toString();
        Map<String, String> params = new HashMap<>();
        params.put("userName", userName);
        params.put("password", password);
        NetUtils.request(context, Constants.LOGIN, params, (CommonResult result) -> {
            String token = result.getToken();
            SPUtils.setPrefString(context, Consts.TOKEN, token);
            SPUtils.setPrefString(context, Consts.USERNAME, userName);
            SPUtils.setPrefString(context, Consts.PASSWORD, password);
            SPUtils.setPrefBoolean(context, Consts.IS_LOGIN, true);
            Log.i("result",result.toString());

            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }


    public class RegisterPopup extends BottomPopupView {
        private RadioGroup rg;          //性别
        private EditText etName;        //姓名
        private EditText etIdNum;       //身份证
        private EditText etPhone;      //手机号
        private TextView tvBirthday;   //出生年月
        private EditText etUsername;    //用户名
        private EditText etPwd1;      //密码
        private EditText etPwd2;      //再次输入密码
        private Button btnSubmit;     //提交

        public RegisterPopup() {
            super(context);
        }

        @Override
        protected int getImplLayoutId() {
            return R.layout.popup_register;
        }

        @Override
        protected void onCreate() {
            super.onCreate();
            rg = findViewById(R.id.rg);
            etName = findViewById(R.id.et_name);
            etIdNum = findViewById(R.id.et_id_num);
            etPhone = findViewById(R.id.et_phone);
            tvBirthday = findViewById(R.id.tv_birthday);
            tvBirthday.setOnClickListener(v -> selectDate());
            etUsername = findViewById(R.id.et_username);
            etPwd1 = findViewById(R.id.et_pwd_1);
            etPwd2 = findViewById(R.id.et_pwd_2);
            btnSubmit = findViewById(R.id.btn_submit);
            btnSubmit.setOnClickListener(v -> {
                if (TextUtils.isEmpty(etName.getText())) {
                    UiUtils.showError(context, "请填写姓名");
                    return;
                }
                if (TextUtils.isEmpty(etIdNum.getText())) {
                    UiUtils.showError(context, "请填写身份证号");
                    return;
                }
                if (TextUtils.isEmpty(etPhone.getText())) {
                    UiUtils.showError(context, "请填写手机号码");
                    return;
                }
                if (TextUtils.isEmpty(tvBirthday.getText())) {
                    UiUtils.showError(context, "请选择出生年月");
                    return;
                }
                if (TextUtils.isEmpty(etUsername.getText())) {
                    UiUtils.showError(context, "请填写用户名");
                    return;
                }
                if (TextUtils.isEmpty(etPwd1.getText()) || TextUtils.isEmpty(etPwd2.getText())) {
                    UiUtils.showError(context, "请输入密码");
                    return;
                }
                if (!etPwd1.getText().toString().equals(etPwd2.getText().toString())) {
                    UiUtils.showError(context, "两次输入的密码不一致");
                    return;
                }
                checkAccount();

            });
        }

        /*选择出生年月*/
        private void selectDate() {
            TimeUtil.selectDateTime(LoginActivity.this, "请选择出生年月", false, true, false, (d, view) -> {
                tvBirthday.setText(TimeUtil.getDateTimeText(d, "yyyy-MM-dd"));
            });
        }

        /*后台检查账号是否合法*/
        private void checkAccount() {
            Map<String, String> params = new HashMap<>();
            params.put("account", etUsername.getText().toString());
            NetUtils.request(context, Constants.CHECK_ACCOUNT, params, result -> register());
        }

        /**
         * 注册
         */
        private void register() {
            Map<String, String> params = new HashMap<>();
            params.put("account", etUsername.getText().toString());
            params.put("password", etPwd1.getText().toString());
            params.put("name", etName.getText().toString());
            params.put("id_num", etIdNum.getText().toString());
            params.put("phone", etPhone.getText().toString());
            params.put("birthday", tvBirthday.getText().toString());
            params.put("sex", rg.getCheckedRadioButtonId() == R.id.rb_male ? "1" : "0");
            NetUtils.request(context, Constants.REGISTER, params, result -> {
                UiUtils.showSuccess(context, "注册成功");
                dismiss();
                Log.i("result",result.toString());
            });
        }
    }

}























