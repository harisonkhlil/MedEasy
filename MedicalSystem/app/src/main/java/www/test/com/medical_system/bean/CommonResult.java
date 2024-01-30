package www.test.com.medical_system.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import www.test.com.medical_system.utils.ValidateUtil;

/**
 * 网络访问返回的对象
 */
public class CommonResult {
    private String state;   //状态
    private String msg;     //描述
    private String token;   //临时的标记，表明用户登录了
    private JSONObject data;//数据对象
    private JSONArray rows; //列表
    private JSONArray value;//列表
    private JSONArray values;//列表
    private int total;          //array的数量

    /*rows转换成字符串*/
    public String getRowsString(){
        return ValidateUtil.isJaValid(rows)? JSON.toJSONString(rows):"";
    }

    /*value转换成字符串*/
    public String getValueString(){
        return ValidateUtil.isJaValid(value)? JSON.toJSONString(value):"";
    }

    /*values转换成字符串*/
    public String getValuesString(){
        return ValidateUtil.isJaValid(values)? JSON.toJSONString(values):"";
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public JSONArray getRows() {
        return rows;
    }

    public void setRows(JSONArray rows) {
        this.rows = rows;
    }

    public JSONArray getValue() {
        return value;
    }

    public void setValue(JSONArray value) {
        this.value = value;
    }

    public JSONArray getValues() {
        return values;
    }

    public void setValues(JSONArray values) {
        this.values = values;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


    @Override
    public String toString() {
        return "CommonResult{" +
                "state='" + state + '\'' +
                ", msg='" + msg + '\'' +
                ", token='" + token + '\'' +
                ", data=" + data +
                ", rows=" + rows +
                ", value=" + value +
                ", values=" + values +
                ", total=" + total +
                '}';
    }
}
