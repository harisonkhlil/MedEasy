package www.test.com.medical_system.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import www.test.com.medical_system.R;
import www.test.com.medical_system.fragment.IntroFragment;

/**
 * 自助导诊
 */
public class GuideActivity extends BaseActivity implements View.OnClickListener {
    /*左侧布局*/
    private LinearLayout llLeft;
    /*右侧布局*/
    private LinearLayout llRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_guide);
        setTitle("自助导诊");
        initLeft();
        initRight();
    }

    /**
     * 增加所有的左侧症状
     */
    private void initLeft() {
        llLeft = findViewById(R.id.ll_left);
        addLeftItem("头疼");
        addLeftItem("眼睛疼");
        addLeftItem("牙疼");
        addLeftItem("胸疼");
        //addLeftItem("乳房疼");
        addLeftItem("腹疼");
        addLeftItem("腿疼");
    }

    /**
     * 增加一个左侧症状
     */
    private void addLeftItem(String text) {
        View view = getLayoutInflater().inflate(R.layout.item_left, null);
        TextView tvName = view.findViewById(R.id.tv_name);
        tvName.setText(text);
        if (text.equals("头疼")) {
            tvName.setTextColor(0xff4da8ff);
        }
        view.setOnClickListener(v -> {
            for (int i = 0; i < llLeft.getChildCount(); i++) {
                View child = llLeft.getChildAt(i);
                TextView tv = child.findViewById(R.id.tv_name);
                if (tv.getText().toString().equals(text)) {
                    tv.setTextColor(0xff4da8ff);
                    child.setBackgroundColor(0xfff8f8f8);
                } else {
                    tv.setTextColor(0xff666666);
                    child.setBackgroundColor(0xffffffff);
                }
            }
            llRight.removeAllViews();
            switch (text) {
                case "头疼":
                    addHeadache();
                    break;
                case "牙疼":
                    addTeethPain();
                    break;
                case "眼睛疼":
                    addEyePain();
                    break;
                case "胸疼":
                    addBreastPain();
                    break;
                case "腹疼":
                    addBellyPain();
                    break;
                case "腿疼":
                    addLegPain();
                    break;
            }
        });

        llLeft.addView(view);
    }

    /**
     * 增加所有的右侧建议
     */
    private void initRight() {
        llRight = findViewById(R.id.ll_right);
        addHeadache();
    }

    /*头疼*/
    private void addHeadache() {
        addRightItem("症状一", "看书、写字时，出现头痛并伴有眼眶疼，视觉模糊，眼睛流泪或眼挂眼科结膜充血，有可能是青光眼或眼内压增高等情况。可考虑挂眼科进一步查看。");
        addRightItem("症状二", "突然出现剧烈的头痛，且伴有恶心，呕吐，疼痛难忍，多考虑急性挂神经内科的蛛网膜下腔出血。可考虑挂神经内科进一步查看。");
        addRightItem("症状三", "一侧头痛，多为偏头痛或慢性、持续性头痛，与疲劳、紧张有关，如果伴随着失眠、记忆减退、情绪不稳定等情况。可考虑挂神经内科进一步查看。");
        addRightItem("症状四", "如果头痛时伴有眩晕、耳鸣，或者鼻塞、流脓鼻涕，或鼻涕带血，可考虑挂耳鼻喉科耳鼻喉科进一步查看。");
        addRightItem("症状五", "如果是外伤引发的剧烈头痛、呕吐，并出现神志不清的，建议挂神经外科。");
    }

    /*牙疼*/
    private void addTeethPain() {
        addRightItem("症状一", "如果是一侧面部或是上下颌，出现剧烈的放射性疼痛，考虑为三叉神经痛，一般需要挂神经内科或疼痛科。");
        addRightItem("症状二", "如果牙疼得连另一侧的嘴和脸都感到疼痛、鼻塞时，有可能是耳鼻部感染，可以挂耳鼻喉科。");
        addRightItem("症状三", "如果牙痛伴有牙齿发麻，颈部、下颌部发紧，需警惕是心脏疾病的信号，应挂心内科。");
        addRightItem("症状四", "如果是龋齿导致的牙疼，比如喝冷水时牙会受到刺激而疼痛，这时就需要去补牙，应挂牙体牙髓科或者口腔科。");
    }

    /*胸疼*/
    private void addBreastPain() {
        addRightItem("症状一", "如果经常在劳累、紧张时出现胸痛，每次疼痛大概几分钟到十几分钟，休息可以缓解，多是心脏缺血的表现，需要去心内科就诊。");
        addRightItem("症状二", "如果胸痛伴有咳嗽、深吸气或呼气时加重，多为胸膜、肺部疾病，如胸膜炎、肺炎等，建议去呼吸科或胸外科就诊。");
        addRightItem("症状三", "如果运动后突然出现剧烈胸痛，并伴有呼吸困难，考虑为气胸（高压性气胸）的情况，需要挂胸外科。");
        addRightItem("症状四", "如果胸部及前胸后背突然出现撕裂性疼痛，并伴有血压升高，这种情况较危险，需要及时挂血管外科。");
    }

    /*乳房疼*/
    private void addTitsPain() {
        addRightItem("症状一", "女性乳房疼痛最常见的原因是乳腺增生，应直接挂乳腺专科。");
        addRightItem("症状二", "如果乳腺及局部皮肤出现水疱，产生疼痛，考虑为带状疱疹，需要挂皮肤科。");
    }

    /*腹疼*/
    private void addBellyPain() {
        addRightItem("症状一", "如果出现一般的腹痛（痛感不剧烈），且按压时腹部疼痛能有所减轻，考虑为内科腹痛，可以看消化内科。");
        addRightItem("症状二", "如果腹部疼痛比较剧烈，且按压时痛感明显加重，一般考虑为外科腹痛，建议挂普通外科或者急诊腹痛。");
        addRightItem("症状三", "如果是下腹坠痛且伴有阴道出血，可能得挂妇科了。");
        addRightItem("症状四", "如果肚子痛的同时伴有尿频、尿痛、尿急等尿道症状，需要挂泌尿外科。");
    }

    /*腿疼*/
    private void addLegPain() {
        addRightItem("症状一", "如果出现大腿小腿外侧疼，痛感延伸到屁股，多是坐骨神经受到压迫或刺激，引起的疼痛。这种症状应首先考虑到骨科看看。");
        addRightItem("症状二", "如果是在运动后，腿部关节出现疼痛，一般要看骨科或骨关节科。");
        addRightItem("症状三", "如果是踝关节、足趾关节出现红肿疼痛，尤其是在吃海鲜、啤酒后发作，血检发现尿酸升高，可能是痛风的表现，需要看内分泌科（或风湿免疫科）。");
        addRightItem("症状四", "如果是膝关节、手关节等全身多处关节出现两侧对称性疼痛，早上起床时还很僵硬，可能是类风湿性关节炎的表现，多属于风湿免疫科。");
        addRightItem("症状五", "如果是上时间坐车，或是久坐后出现了小腿肚肿胀（尤其是老年人），手指按压会出现疼痛，建议可以到血管外科检查，看是否是深静脉血栓或动脉血栓导致。");
    }

    /**
     * 眼睛疼
     */
    private void addEyePain() {
        addRightItem("症状一", "干眼症。在日常生活中像长时间使用电脑，手机等电子产品，佩戴隐形眼镜等原因都会引起干眼症。从而出现眼睛干涩，疼痛，有异物感，害怕光线，流眼泪等症状，需要大家减少使用电子用品的时间，平时多眨眼，正确佩戴隐形眼镜，这样可以消除干眼症的症状。");
        addRightItem("症状二", "结膜炎，如果在眼睛疼痛的同时出现了畏光，流眼泪，视力下降，眼睛充血，眼睛水肿等症状就可能患了结膜炎，需要及时就医，查明原因，然后有针对性的进行治疗。");
        addRightItem("症状二", "眼睛刺痛 眼睛刺痛的原因很多，角膜疾病可以引起，正常的眼球表面覆盖着一层泪膜，它是维持眼表健康、舒适的基础。白领由于长时间盯着电脑，注意力高度集中，眼睑睁开得较大，泪液蒸发比平时增加，而同时眨眼次数大大减少，造成眼球表面泪液滋润不足，加上荧屏光线的辐射，因此容易出现眼睛干涩、刺痛。");

    }

    /**
     * 增加一个右侧建议
     */
    private void addRightItem(String title, String content) {
        View view = getLayoutInflater().inflate(R.layout.item_right, null);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        TextView tvContent = view.findViewById(R.id.tv_content);
        tvContent.setText(content);
        llRight.addView(view);
    }
}



























