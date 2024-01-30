package www.test.com.medical_system.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@SuppressLint("SimpleDateFormat")
public class TimeUtil {

    // 获取若干天前/后的日期字符串
    public static String getPreOrLaterDateString(int days) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + days);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dft.format(endDate);
    }

    // 比较两个日期字符串的大小（返回为true即第二个日期比较大）
    public static boolean isDate2Bigger(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1;
        Date dt2;
        try {
            dt1 = sdf.parse(date1);
            dt2 = sdf.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return false;
            } else if (dt1.getTime() < dt2.getTime()) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static String getCurrentTime(String format) {
        // "yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    public static int getCurrentDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH) + 1;// Java月份从0开始算
        // 周日是1，周一是2，周六是7
        return calendar.get(Calendar.DAY_OF_WEEK);
    }


    public static int getCurrentDayOfWeekNew() {
        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH) + 1;// Java月份从0开始算
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 1) {
            day = 6;
        } else {
            day = day - 2;
        }
        return day;
    }


    // 获取days天后的日期字符串
    public static String getDateStringLater(int days, String format) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH, days);
        return new SimpleDateFormat(format).format(now.getTime());
    }

    // 获取days天前的日期字符串
    public static String getDateStringBefore(int days, String format) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH, -days);
        return new SimpleDateFormat(format).format(now.getTime());
    }


    public static int getDayOfWeekByDate(String datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        Date date;
        try {
            date = sdf.parse(datetime);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return w;
    }

    public static String getDateTimeText(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static boolean checkTime(String time1, String time2) {
        boolean ret = true;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = sdf.parse(time1);
            Date date1 = sdf.parse(time2);
            if (date.getTime() > date1.getTime()) ret = false;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return ret;
    }


    public static boolean checkTime(String time1, String time2, String format) {
        boolean ret = true;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(time1);
            Date date1 = sdf.parse(time2);
            if (date.getTime() > date1.getTime()) ret = false;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return ret;
    }

    static double calcDays(String beginDate, String endDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");//输入日期的格式
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(beginDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date date2 = null;
        try {
            date2 = simpleDateFormat.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return (cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (1000 * 3600 * 24.0);
    }

    public static double string2Timestamp(String date, String format) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static Date[] getAllDaysOfWeek() {
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        Date[] dates = new Date[7];
        for (int i = 0; i < 7; i++) {
            dates[i] = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public static String getDateStringByWeek(int index) {
        Date[] arrDate = getAllDaysOfWeek();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(arrDate[index]);
    }


    // 选择日期-时间（可选选择模式：仅日期，仅时间，日期+时间）
    public static void selectDateTime(Activity activity, String title, boolean asDialog, boolean showDate, boolean showTime, OnTimeSelectListener onTimeSelectListener) {
        TimePickerBuilder builder = new TimePickerBuilder(activity, onTimeSelectListener);
        builder.setType(new boolean[]{showDate, showDate, showDate, showTime, showTime, false})
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(16)//滚轮文字大小
                .setTitleSize(14)//标题文字大小
                .setSubCalSize(14)//标题文字大小
                .setLineSpacingMultiplier(2.8f)
                .setTitleText(title)//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTitleColor(0xFF333333)//标题文字颜色
                .setSubmitColor(0xFF0F96DA)//确定按钮文字颜色
                .setCancelColor(Color.GRAY)//取消按钮文字颜色
                .setTitleBgColor(0xFFFFFFFF)//标题背景颜色
                .setBgColor(0xFFFFFFFF)//滚轮背景颜色
                .setDate(Calendar.getInstance())// 如果不设置的话，默认是系统时间*/
//                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(asDialog)//是否显示为对话框样式
                .build();
        TimePickerView pickerView = builder.build();
        pickerView.show();
    }

    public static void selectMonth(Activity activity, String title, boolean asDialog, OnTimeSelectListener onTimeSelectListener) {
        TimePickerBuilder builder = new TimePickerBuilder(activity, onTimeSelectListener);
        builder.setType(new boolean[]{true, true, false, false, false, false})
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(16)//滚轮文字大小
                .setTitleSize(14)//标题文字大小
                .setSubCalSize(14)//标题文字大小
                .setLineSpacingMultiplier(2.8f)
                .setTitleText(title)//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTitleColor(0xFF333333)//标题文字颜色
                .setSubmitColor(0xFF0F96DA)//确定按钮文字颜色
                .setCancelColor(Color.GRAY)//取消按钮文字颜色
                .setTitleBgColor(0xFFFFFFFF)//标题背景颜色
                .setBgColor(0xFFFFFFFF)//滚轮背景颜色
                .setDate(Calendar.getInstance())// 如果不设置的话，默认是系统时间*/
//                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(asDialog)//是否显示为对话框样式
                .build();
        TimePickerView pickerView = builder.build();
        pickerView.show();
    }


}


