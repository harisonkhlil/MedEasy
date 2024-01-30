function showTip(msg, id) {
    var $ele = (typeof(id) === 'object') ? id : $('#' + id);
    layer.tips(msg, $ele, {
        tips: [1, '#FF3030'],
        tipsMore: true,
        time: 5000
    });
}

function hasText(value) {
    var valueTrim = $.trim(value);
    return !(undefined === valueTrim || null === valueTrim || '' === valueTrim);
}

function showError(msg) {
    layer.msg(msg);
}

function showPostOk(msg, beforeFun, afteFunc) {
    beforeFun && beforeFun();//自动关闭后处理事件

    layer.msg(msg, {
        icon: 1,
        time: 3000 //3秒关闭（如果不配置，默认是3秒）
    }, function () {
        afteFunc && afteFunc();
    });
}

function showPostFail(msg, beforeFun, afteFunc) {
    beforeFun && beforeFun();//自动关闭后处理事件

    layer.msg(msg, {
            shift: 0
            , shade: 0
            , time: 2500
            , offset: "165px"
            , closeBtn: 1
            , shadeClose: true
        }, function () {
            afteFunc && afteFunc();
        }
    );
}

function doPostWithVali(url, data, successFun, failFun, valiFun) {
    $.ajax(url, {
        type: "POST",
        cache: false,
        dataType: "json",
        data: data,
        beforeSend: function () {
            this.layerIndex = layer.load(1, {shade: [0.1, '#fff']});
        },

        success: function (json) {
            if (hasText(json.state)) {
                if (json.state == "ok") {
                    if (typeof successFun == "function") successFun(json, this.layerIndex);
                } else {
                    check_login(json);
                    if (typeof failFun == "function") failFun(json, this.layerIndex);
                }
            } else {
                if (typeof valiFun == "function") valiFun(json, this.layerIndex);
            }
        },

        complete: function () {
            layer.close(this.layerIndex);
        },
        error: function (res) {
            showPostFail("对不起，访问出现" + res.status + "错误");
        }
    });
}

function doPost(url, data, successFun) {
    $.ajax(url, {
        type: "POST",
        cache: false,
        dataType: "json",
        data: data,
        beforeSend: function () {
            this.layerIndex = layer.load(1, {shade: [0.1, '#fff']});
        },

        success: function (json) {
            if (hasText(json.state)) {
                if (json.state == "ok") {
                    if (typeof successFun == "function") successFun(json, this.layerIndex);
                } else {
                    check_login(json);
                    showPostFail(json.msg);
                }
            }
        },

        complete: function () {
            layer.close(this.layerIndex);
        },
        error: function (res) {
            showPostFail("对不起，访问出现" + res.status + "错误");
        }
    });
}

function check_login(json) {
    if (json.code == -2) {
        showPostFail(json.msg, function () {

        }, function () {
            window.location.href = "/login";
        })
    }
}

function textOverflow(maxWidth) {
    return {
        css: {
            "max-width": ($.isNumeric(maxWidth) ? maxWidth : 150) + "px",
            "text-overflow": "ellipsis",
            "overflow": "hidden",
            "white-space": "nowrap"
        }
    };
}

function subDateStr(dateString) {
    if (hasText(dateString)) {
        return dateString.substring(0, 10);
    }
    return dateString;
}