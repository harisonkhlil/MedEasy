package www.test.com.medical_system.utils;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import www.test.com.medical_system.bean.CommonResult;
import www.test.com.medical_system.callback.Callback;

/**
 * 网络访问类
 */
public class NetUtils {
    /**
     * 访问后台
     * context 上下文，即环境
     * url 相对地址
     * baseUrl 根地址
     */
    public static void request(Context context, String url, Map<String, String> params, Callback callback) {
        BasePopupView loadingView = new XPopup.Builder(context).asLoading("网络请求中...").show();
        String token = SPUtils.getPrefString(context, Consts.TOKEN, "");
        //如果有token，那么把它放到参数里
        if (ValidateUtil.isStringValid(token)) {
            if (params == null) {
                params = new HashMap<>();
            }
            params.put(Consts.PTOKEN, token);
        }
        //使用OkHttp访问后台
        OkHttpUtils.post().url(Constants.BASE_URL + url).params(params).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                loadingView.dismiss();
                UiUtils.showError(context, "请求失败：" + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                loadingView.dismiss();
                CommonResult result = JSON.parseObject(response, CommonResult.class);
                if (result.getState().equals("ok")) {
                    callback.fun(result);
                } else {
                    UiUtils.showKnowDialog(context, "请求失败", result.getMsg());
                }
            }
        });

    }
}
