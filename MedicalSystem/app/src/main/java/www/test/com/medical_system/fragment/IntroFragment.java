package www.test.com.medical_system.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import www.test.com.medical_system.R;

/**
 * 医院介绍Fragment
 */
public class IntroFragment extends Fragment implements View.OnClickListener {
    public Context context;

    public IntroFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro, container, false);
        ImageView iv = view.findViewById(R.id.iv);
        TextView tv = view.findViewById(R.id.tv);
        int type = getArguments().getInt("type");
        switch (type) {
            case 0:
                tv.setVisibility(View.VISIBLE);
                tv.setText("【普通门诊时间】\n" +
                        "·上午: 08:00--11:45|下午: 14:30--17:15\n" +
                        "\n"+
                        "【夜间门诊】\n"+
                        ".夜间门诊请走急诊通道进入\n"+
                        ".急诊科，位置：一楼急诊科，咨询电话：65188120\n"+
                        ".康复中心：位置：四楼康复中心门诊，咨询电话：65188015\n"+
                        ".针灸推拿理疗 位置：三楼中医特色疗法中心，咨询电话：65188006\n"+
                        "\n"+
                        "【药品邮寄服务】\n"+
                        ".门诊、住院自煎中药（不包含颗粒）全国免费邮寄\n"+
                        ".门诊、住院代煎中药（不包含颗粒）河南省内免费邮寄\n"+
                        ".咨询电话（药房）：0371-65188037\n"+
                        "\n"+
                        "一楼 | 急诊科、导诊台、收费室、药房\n" +
                        "\n" +
                        "二楼 | 内科、儿科、康复门诊、医技部门\n" +
                        "\n" +
                        "三楼 | 国际医疗部、中医特色疗法中心、外科门诊、国际过敏性疾病诊疗中心\n" +
                        "\n" +
                        "四楼 | 体检中心、整形美容科"+
                        "\n"+
                        "【就诊必备】\n" +
                        "·就诊卡/身份证/学生证/社保卡\n"



                );
                break;
            case 1:
                iv.setVisibility(View.VISIBLE);
                iv.setImageResource(R.mipmap.jizhen);
                break;
            case 2:
                iv.setVisibility(View.VISIBLE);
                iv.setImageResource(R.mipmap.baoxiao);
                break;
            case 3:
                iv.setVisibility(View.VISIBLE);
                iv.setImageResource(R.mipmap.img_parking);
                tv.setVisibility(View.VISIBLE);
                tv.setText("公交\n"+
                        "①乘160路、170路、278路、312路到龙子湖南路博学路站下车\n" +
                        "②乘S177到博学路龙子湖南路站下车\n" +
                        "③乘265路、S136路到博学路站下车\n" +"\n"+
                        "地铁\n"+
                        "①1号线龙子湖站C口出站转S136路到博学路站下车\n"+
                        "②1号线市体育中心站C口出站转160路到龙子湖南路博学路站下车，\n"+
                        "或B口出站到市体育中心公交站转170路到龙子湖南路博学路站下车\n"
                );
                break;
            case 4:
                tv.setVisibility(View.VISIBLE);
                tv.setText("扁鹊，其真实姓名是秦越人，又号卢医。据人考证，约生于周威烈王十九年（公元前四O七年），卒于赧王五年（公元前三一O年）。他为什么被称为“扁鹊”呢？这是他的绰号。绰号的由来可能与《禽经》中“灵鹊兆喜”的说法有关。因为医生治病救人，走到哪里，就为那里带去安康，如同翩翩飞翔的喜鹊，飞到哪里，就给那里带来喜讯。因此，古人习惯把那些医术高明的医生称为扁鹊。秦越人在长期医疗实践中，刻苦钻研，努力总结前人的经验，大胆创新，成为一个学识渊博，医术高明的医生。他走南闯北，真心实意地为人民解除疾病的痛苦，获得人民普遍的崇敬和欢迎。于是，人们也尊敬地把他称为扁鹊。\n" +
                        "\n" +
                        "扁鹊善于运用四诊，尤其是脉诊和望诊来诊断疾病。《史记·扁鹊仓公列传》中记述了与他有关的两个医案：一个是用脉诊的方法诊断赵子简的病，一个是用望诊的方法诊断齐桓侯的病。      \n"
                );
                break;
            case 5:
                tv.setVisibility(View.VISIBLE);
                tv.setText("河南中医药大学第一附属医院龙子湖院区针对高校师生及附近居民30余万人，将建成大专科、小综合的综合性中医院！\n"+
                        "\n"+
                        "院区总建筑面积约为32000平方米，院区目前开设了急诊、门诊（二楼中医内科门诊、儿科门诊、康复门诊）\n"+"\n"+"三楼国际医疗部、外科门诊、国际过敏性疾病诊疗中心、中医特色疗法中心\n"+"\n"+"四楼体检中心、整形美容科、医技、检验、药学以及财务等部门，院区门诊设置了内、外、妇、儿等各临床专业。");
                break;
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }
}
