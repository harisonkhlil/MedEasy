package www.test.com.medical_system.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;


import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import www.test.com.medical_system.R;
import www.test.com.medical_system.utils.ActivityManager;

@SuppressLint("InflateParams")
public class BaseActivity extends AppCompatActivity implements BGASwipeBackHelper.Delegate, View.OnClickListener {

    public TextView tvTitle;
    public ImageView ivBack;
    public Context context;
    public TextView tvRight;
    public ImageView ivRight;
    public BGASwipeBackHelper mSwipeBackHelper;

    // 横竖屏切换不重新加载activity
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
    }


    public void initBaseViews() {
        tvTitle = findViewById(R.id.tv_title);
        tvRight = findViewById(R.id.tv_right);
        ivRight = findViewById(R.id.iv_right);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> finish());
        ivRight.setOnClickListener(this);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
        tvTitle.setOnClickListener(this);
    }

    public void setRightText(String text) {
        ivRight.setVisibility(View.GONE);
        tvRight.setVisibility(View.VISIBLE);
        ivRight.setVisibility(View.INVISIBLE);
        tvRight.setText(text);
        tvRight.setOnClickListener(this);
    }

    String getTitleText() {
        return tvTitle.getText().toString();
    }

    public void setRightIcon(int resId) {
        ivRight.setVisibility(View.VISIBLE);
        tvRight.setVisibility(View.INVISIBLE);
        ivRight.setImageResource(resId);
        ivRight.setOnClickListener(this);
        //右上角的图标是否被更替，默认false
        boolean isTopRightIconChange = true;
    }

    public void setView(int contentViewResId) {
        setContentView(contentViewResId);
        initBaseViews();
        ImmersionBar.with(this)
                .titleBar(R.id.layout_top_bar)
                .init();
    }

    // 在 super.onCreate(savedInstanceState) 之前调用该方法
    public void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    // 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {

    }

    @Override
    public void onSwipeBackLayoutCancel() {

    }

    // 滑动返回执行完毕，销毁当前 Activity
    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }

    @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        mSwipeBackHelper.backward();
    }


    // 点击键盘空白处自动收起键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {  //把操作放在用户点击的时候
            View v = getCurrentFocus();      //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (isShouldHideKeyboard(v, me)) { //判断用户点击的是否是输入框以外的区域
                hideKeyboard(v.getWindowToken());   //收起键盘
            }
        }
        return super.dispatchTouchEvent(me);
    }

    // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
    public boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if ((v instanceof EditText)) {  //判断得到的焦点控件是否包含EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],    //得到输入框在屏幕中上下左右的位置
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略
        return false;
    }

    // 隐藏软键盘
    public void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        ActivityManager.removeActivity(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
