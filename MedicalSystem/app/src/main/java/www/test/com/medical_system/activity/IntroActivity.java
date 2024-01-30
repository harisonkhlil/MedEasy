package www.test.com.medical_system.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import www.test.com.medical_system.R;
import www.test.com.medical_system.fragment.IntroFragment;

/**
 * 医院介绍
 */
public class IntroActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_intro);
        setTitle("医院介绍");

        Bundle bundle0 = new Bundle();
        bundle0.putInt("type", 0);
        Bundle bundle1 = new Bundle();
        bundle1.putInt("type", 1);
        Bundle bundle2 = new Bundle();
        bundle2.putInt("type", 2);
        Bundle bundle3 = new Bundle();
        bundle3.putInt("type", 3);
        Bundle bundle4 = new Bundle();
        bundle4.putInt("type", 4);
        Bundle bundle5 = new Bundle();
        bundle5.putInt("type", 5);

        FragmentPagerItems fragmentPagerItems = FragmentPagerItems.with(context)
                .add("校医院介绍", IntroFragment.class, bundle5)
                .add("就医指引", IntroFragment.class, bundle0)
                .add("急诊范围", IntroFragment.class, bundle1)
                .add("校外门诊报销流程", IntroFragment.class, bundle2)
                .add("校医院地址", IntroFragment.class, bundle3)
                .add("名医介绍", IntroFragment.class, bundle4)
                .create();
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), fragmentPagerItems);
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        SmartTabLayout viewpagertab = findViewById(R.id.viewpagertab);
        viewpagertab.setViewPager(viewPager);
    }
}






















