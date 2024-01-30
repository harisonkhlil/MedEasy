package www.test.com.medical_system.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.yinglan.alphatabs.AlphaTabsIndicator;

import www.test.com.medical_system.R;
import www.test.com.medical_system.adapter.HomePageAdapter;
import www.test.com.medical_system.utils.UiUtils;

/**
 * 主页面
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private AlphaTabsIndicator alphaTabsIndicator;
    /*是否退出*/
    private boolean is2CallBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_main);
        ivBack.setVisibility(View.GONE);
        setTitle("首页");
        viewPager = findViewById(R.id.viewPager);
        HomePageAdapter adapter = new HomePageAdapter(context,getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        alphaTabsIndicator = findViewById(R.id.alphaIndicator);
        alphaTabsIndicator.setViewPager(viewPager);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i){
            case 0:
                setTitle("首页");
                break;
            case 1:
                setTitle("个人中心");
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    /**
     * 两秒钟之内连续点击后退键退出app
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(is2CallBack){
                android.os.Process.killProcess(android.os.Process.myPid());
            }else{
                is2CallBack = true;
                UiUtils.showInfo(context,"再按一次返回退出app");
                new Handler().postDelayed(() -> is2CallBack = false ,2000);
            }
        }
        return true;
    }
}
