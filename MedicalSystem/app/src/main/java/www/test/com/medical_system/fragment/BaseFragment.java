package www.test.com.medical_system.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.test.com.medical_system.R;

/**
 * 所有Fragment的基础类
 */
public class BaseFragment extends Fragment implements View.OnClickListener {
    public Context context;

    public BaseFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base,container,false);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }
}
