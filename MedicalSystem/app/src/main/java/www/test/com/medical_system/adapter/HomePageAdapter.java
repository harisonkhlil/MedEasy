package www.test.com.medical_system.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import www.test.com.medical_system.fragment.HomeFragment;
import www.test.com.medical_system.fragment.MineFragment;

public class HomePageAdapter extends FragmentPagerAdapter {
    private Context context;

    public HomePageAdapter(Context context, FragmentManager fm){
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new HomeFragment();
            case 1:
                return new MineFragment();
        }
        return new HomeFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
