package www.test.com.medical_system.bean;

/**
 * 某医生某个时间段的预约小计
 */
public class TimeListBean {
    /*存储到数据库中的该时间段的标示*/
    private Integer timeValue;
    /*显示给病人的该时间段*/
    private String timeText;
    /*该时间段可以预约的总病人数*/
    private Integer totalNum;
    /*该时间段已经预约的病人数*/
    private Integer reservedNum;
    /*该时间段还能预约的病人数*/
    private Integer remainingNum;

    public TimeListBean(Integer timeValue, String timeText, Integer totalNum, Integer reservedNum, Integer remainingNum) {
        this.timeValue = timeValue == null ? 0 : timeValue;
        this.timeText = timeText;
        this.totalNum = totalNum == null ? 0 : totalNum;
        this.reservedNum = reservedNum == null ? 0 : reservedNum;
        this.remainingNum = remainingNum == null ? 0 : remainingNum;
    }

    public TimeListBean(){

    }

    @Override
    public String toString() {
        return timeText+"（剩余"+remainingNum+"位）";
    }

    public Integer getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(Integer timeValue) {
        this.timeValue = timeValue;
    }

    public String getTimeText() {
        return timeText;
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getReservedNum() {
        return reservedNum;
    }

    public void setReservedNum(Integer reservedNum) {
        this.reservedNum = reservedNum;
    }

    public Integer getRemainingNum() {
        return remainingNum;
    }

    public void setRemainingNum(Integer remainingNum) {
        this.remainingNum = remainingNum;
    }
}