package com.maven.common.util;

import com.jfinal.kit.Ret;

public class RetUtils {

    public static Ret operationOk() {
        return Ret.ok("msg", "操作成功");
    }

    public static Ret operationFail() {
        return Ret.fail("msg", "操作失败");
    }

    public static Ret operation(boolean success) {
        return success ? operationOk() : operationFail();
    }

    public static Ret saveOk() {
        return Ret.ok("msg", "保存成功");
    }

    public static Ret saveFail() {
        return Ret.fail("msg", "保存失败");
    }

    public static Ret save(boolean success) {
        return success ? saveOk() : saveFail();
    }

    public static Ret updateOK() {
        return Ret.ok("msg", "更新成功");
    }

    public static Ret updateFail() {
        return Ret.fail("msg", "更新失败");
    }

    public static Ret update(boolean success) {
        return success ? updateOK() : updateFail();
    }

    public static Ret deleteOk() {
        return Ret.ok("msg", "删除成功");
    }

    public static Ret deleteFail() {
        return Ret.fail("msg", "删除失败");
    }

    public static Ret delete(boolean success) {
        return success ? deleteOk() : deleteFail();
    }

    public static Ret parameterFail() {
        return Ret.fail("msg", "传入参数有误");
    }

    public static Ret ok(String msg) {
        return Ret.ok("msg", msg);
    }

    public static Ret fail(String msg) {
        return Ret.fail("msg", msg);
    }

    public static String getMsg(Ret ret) {
        return ret.getStr("msg");
    }
}
