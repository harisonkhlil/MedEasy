package www.test.com.medical_system.utils;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.mylhyl.circledialog.CircleDialog;

import java.util.List;

public class Utils {
    public static List JSONArray2List(JSONArray ja, Class clazz) {
        return JSONObject.parseArray(ja.toJSONString(), clazz);
    }

    public static String[] list2StringArray(List<?> list) {
        String[] arr = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i).toString();
        }
        return arr;
    }

    public static String getImageUrl(Context context, String path) {
        return SPUtils.getPrefString(context, "base_url", "") + "/upload/download/" + path;
    }


    public static void showConfirmDialog(Context context, FragmentManager fragmentManager, String title, String contentText, String positiveButtonText, final View.OnClickListener confirmListener) {
        new CircleDialog.Builder()
                .setTitle(title)
                .setTitleColor(0xff0F96DA)
                .setText(contentText + "\n")//换行是为了让text的上下距离相同
                .setTextColor(0xff000000)
                .setPositive(positiveButtonText, confirmListener)
                .configPositive(params -> {
                    params.textColor = 0xff0F96DA;
                    params.textSize = 50;
                })
                .setNegative("取消", null)
                .configNegative(params -> params.textSize = 50)
                .show(fragmentManager);
    }


    public static void showSingleSelector(Context context, String[] arrContent, int[] arrIcon, int checkedPosition, boolean asBottomList, OnSelectListener onSelectListener) {
        XPopup.Builder builder = new XPopup.Builder(context);
        if (asBottomList) {
            builder.maxHeight(DensityUtil.dip2px(context, 550)).asBottomList("请选择", arrContent, arrIcon, checkedPosition, onSelectListener).show();
        } else {
            builder.maxHeight(DensityUtil.dip2px(context, 550)).asCenterList("请选择", arrContent, arrIcon, checkedPosition, onSelectListener).show();
        }
    }

    public static void showSingleSelector(Context context, String title, String[] arrContent, int[] arrIcon, int checkedPosition, boolean asBottomList, OnSelectListener onSelectListener) {
        XPopup.Builder builder = new XPopup.Builder(context);
        if (asBottomList) {
            builder.maxHeight(DensityUtil.dip2px(context, 550)).asBottomList(title, arrContent, arrIcon, checkedPosition, onSelectListener).show();
        } else {
            builder.maxHeight(DensityUtil.dip2px(context, 550)).asCenterList(title, arrContent, arrIcon, checkedPosition, onSelectListener).show();
        }
    }


    public static int getCheckedPosition(String text, String[] arr) {
        if (!ValidateUtil.isStringValid(text)) {
            return -1;
        }
        if (arr.length == 0) {
            return -1;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(text)) {
                return i;
            }
        }
        return -1;
    }

}
