package www.test.com.medical_system.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.test.com.medical_system.R;
import www.test.com.medical_system.bean.Case;
import www.test.com.medical_system.utils.Constants;
import www.test.com.medical_system.utils.NetUtils;
import www.test.com.medical_system.utils.UiUtils;
import www.test.com.medical_system.utils.ValidateUtil;

/**
 * 门诊记录
 */
public class RecordListActivity extends BaseActivity implements View.OnClickListener {
    /*门诊记录列表*/
    private LinearLayout llContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_record_list);
        setTitle("门诊记录");
        llContent = findViewById(R.id.ll_content);
        getRecordList();
    }

    /**
     * 获取门诊记录列表并显示到页面
     */
    private void getRecordList(){
        Map<String,String> params = new HashMap<>();
        params.put("pageNumber","1");
        params.put("pageSize","100");
        NetUtils.request(context,Constants.CASE_LIST,params,result -> {
            llContent.removeAllViews();
            String rows = result.getRowsString();
            if(ValidateUtil.isStringValid(rows)){
                List<Case> list = JSON.parseArray(rows,Case.class);
                for(Case bean:list){
                    addRecord(bean);
                }
            }else{
                UiUtils.showKnowDialog(context,"提示","您暂无门诊记录");
            }
            Log.i("result",result.toString());
        });
    }

    /**
     * 每一个门诊记录的小块
     */
    private void addRecord(Case bean){
        View view = getLayoutInflater().inflate(R.layout.item_record,null);
        ((TextView)view.findViewById(R.id.tv_doctor)).setText(bean.getDoctor_name());
        ((TextView)view.findViewById(R.id.tv_department)).setText(bean.getDepartment_text());
        ((TextView)view.findViewById(R.id.tv_content)).setText(bean.getContent());
        ((TextView)view.findViewById(R.id.tv_date)).setText(bean.getDate());
        ImageView iv = view.findViewById(R.id.iv_icon);
        llContent.addView(view);
    }

}
