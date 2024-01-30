package www.test.com.medical_system;

import android.app.Application;

import com.lxj.xpopup.XPopup;
import com.mylhyl.circledialog.res.values.CircleDimen;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.log.LoggerInterceptor;


import java.util.concurrent.TimeUnit;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import es.dmoral.toasty.Toasty;
import okhttp3.OkHttpClient;

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initXPopup();
        configToasty();
        configCircleDialog();
        initOkHttp();
        initSwipeBack();
    }

    private void initOkHttp() {
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG"))
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    private void initXPopup() {
        XPopup.setPrimaryColor(getResources().getColor(R.color.colorPrimary));
    }

    private void initSwipeBack() {
        //  第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
        BGASwipeBackHelper.init(this, null);
    }


    private void configCircleDialog() {
        // body文本内间距 [left, top, right, bottom]
        CircleDimen.TEXT_PADDING = new int[]{30, 30, 30, 30};
        CircleDimen.CONTENT_TEXT_SIZE = 45;
    }

    private void configToasty() {
        Toasty.Config.getInstance().setErrorColor(getResources().getColor(R.color.red)).
                setSuccessColor(getResources().getColor(R.color.green2))
                .setWarningColor(getResources().getColor(R.color.yellow2))
                .setInfoColor(getResources().getColor(R.color.blue_title_text))
                .apply();
    }
}
