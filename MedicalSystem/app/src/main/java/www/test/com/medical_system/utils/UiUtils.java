package www.test.com.medical_system.utils;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.FragmentManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.contrarywind.interfaces.IPickerViewData;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.params.ButtonParams;

import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import www.test.com.medical_system.R;

public class UiUtils {
    public static void showText(Context context, String text) {
        new XPopup.Builder(context).asConfirm("提示",
                text,
                "", "知道了",
                null, null, true).show();
    }

    public static void setMargins(View view, int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(left, top, right, bottom);
        view.setLayoutParams(params);
    }


    public static void setWidthAndHeight(View view, int width, int height) {
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
        lp.width = width;
        lp.height = height;
        view.setLayoutParams(lp);
    }

    public static void showKnowDialog(Context context, String title, String content) {
        new XPopup.Builder(context).asConfirm(title, content,
                "", "知道了", null, null, true).show();
    }


    public static void showText(Context context, String title, String content) {
        new XPopup.Builder(context).asConfirm(title, content,
                "", "关闭", null, null, true).show();
    }


    public static void showEmpty(Context context, LinearLayout ll) {
        ll.removeAllViews();
        TextView tv = new TextView(context);
        tv.setText("查无数据");
        tv.setTextSize(16);
        tv.setTextColor(0xff333333);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tv.getLayoutParams();
        lp.setMargins(0, 50, 0, 0);
        tv.setLayoutParams(lp);
        ll.addView(tv);
    }

    public static void showConfirmDialog(Context context, FragmentManager fragmentManager, String title, String contentText, String positiveButtonText, final View.OnClickListener confirmListener) {
        new CircleDialog.Builder()
                .setTitle(title)
                .setTitleColor(0xff0F96DA)
                .setText(contentText + "\n")//换行是为了让text的上下距离相同
                .setTextColor(0xff000000)
                .setPositive(positiveButtonText, confirmListener)
                .configPositive((ButtonParams params) -> {
                    params.textColor = 0xff0F96DA;
                    params.textSize = 50;
                })
                .setNegative("取消", null)
                .configNegative(params -> params.textSize = 50)
                .show(fragmentManager);
    }

    public static void showError(Context context, String text) {
        Toasty.error(context, text, Toast.LENGTH_SHORT, true).show();
    }

    public static void showSuccess(Context context, String text) {
        Toasty.success(context, text, Toast.LENGTH_SHORT, true).show();
    }

    public static void showWarning(Context context, String text) {
        Toasty.warning(context, text, Toast.LENGTH_SHORT, true).show();
    }

    public static void showInfo(Context context, String text) {
        Toasty.info(context, text, Toast.LENGTH_SHORT, true).show();
    }

    public static void showInfoLong(Context context, String text) {
        Toasty.info(context, text, Toast.LENGTH_LONG, true).show();
    }

    public static void showNormal(Context context, String text) {
        Toasty.normal(context, text, Toast.LENGTH_SHORT).show();
    }

    public static View getEmptyView(Context context, String text) {
        View emptyView = ((Activity) context).getLayoutInflater().inflate(R.layout.view_empty, null);
        ((TextView) emptyView.findViewById(R.id.tv_content)).setText(text);
        return emptyView;
    }


    public static View getEmptyView(Context context, int iconResId, String text) {
        View emptyView = ((Activity) context).getLayoutInflater().inflate(R.layout.view_empty, null);
        ((ImageView) emptyView.findViewById(R.id.iv_icon)).setImageResource(iconResId);
        ((TextView) emptyView.findViewById(R.id.tv_content)).setText(text);
        return emptyView;
    }

    public static View getSmallEmptyView(Context context, String text) {
        View emptyView = ((Activity) context).getLayoutInflater().inflate(R.layout.view_empty_small, null);
        ((TextView) emptyView.findViewById(R.id.tv_content)).setText(text);
        return emptyView;
    }

    public static void rotateView(View viewTarget, float start, float end) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(viewTarget, "rotation", start, end);
        animator.setDuration(500);
        animator.start();
    }


    public static void scaleView(View viewTarget) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(viewTarget, "scaleY", 1f, 1.5f, 1f);
        // 动画作用的对象的属性是X轴缩放
        // 动画效果是:放大到3倍,再缩小到初始大小
        animator.setDuration(500);
        animator.start();
    }


    public static String generateColorString(String content) {
        SpannableString spannableString = new SpannableString(content);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.BLUE);
        spannableString.setSpan(foregroundColorSpan, 0, content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString.toString();
    }


    public static void showSingleOptionPicker(Context context, String title, int selectedIndex, final List<? extends IPickerViewData> listData, OnOptionsSelectListener onOptionsSelectListener) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, onOptionsSelectListener)
                .setTitleText(title)
                .setTitleSize(14)
                .setSubCalSize(14)
                .setContentTextSize(15)//设置滚轮文字大小
                .isDialog(true)
                .setLineSpacingMultiplier(2.5f)
                .setSelectOptions(selectedIndex)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleColor(Color.BLACK)
                .setTitleBgColor(Color.WHITE)
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setTitleColor(Color.parseColor("#333333"))
                .setSubmitColor(Color.parseColor("#0F96DA"))
                .setCancelColor(Color.parseColor("#666666"))
                .setTextColorCenter(Color.parseColor("#0F96DA"))
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setOutSideColor(0x99000000) //设置外部遮罩颜色
                .build();
        pvOptions.setPicker(listData);
        pvOptions.show();
    }


    public static void showTimePicker(Context context, String title, String format, final OnTimeSelectListener listener) {
        TimePickerBuilder builder = new TimePickerBuilder(context, listener);
        builder
                .setType(new boolean[]{true, true, true, format.contains("HH"), format.contains("mm"), format.contains("ss")})
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(15)//滚轮文字大小
                .setSubCalSize(15)
                .setTitleSize(15)//标题文字大小
                .setTitleText(title)//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTitleColor(0xFF333333)//标题文字颜色
                .setSubmitColor(0xFF0F96DA)//确定按钮文字颜色
                .setCancelColor(Color.GRAY)//取消按钮文字颜色
                .setTitleBgColor(0xffffffff)//标题背景颜色
                .setBgColor(0xffffffff)//滚轮背景颜色
                .setDate(Calendar.getInstance())// 如果不设置的话，默认是系统时间*/
//                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();

        TimePickerView pickerView = builder.build();
        pickerView.show();
    }

    public static void showConfirmDialog(Context context, String title, String content, OnConfirmListener onConfirmListener) {
        new XPopup.Builder(context).asConfirm(title, content, onConfirmListener)
                .show();
    }


    public static GradientDrawable getDrawable(int solidColor, int strokeColor, int strokeWidth, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        if (solidColor != 0) {
            drawable.setColor(solidColor);
        }
        if (strokeColor != 0) {
            drawable.setStroke(strokeWidth, strokeColor);
        }
        drawable.setCornerRadius(radius);
        return drawable;
    }
}
