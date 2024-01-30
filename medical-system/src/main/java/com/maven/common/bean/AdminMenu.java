package com.maven.common.bean;

/**
 * 后台菜单项详细内容
 */
public class AdminMenu {
    /*菜单名*/
    private String name;
    /*菜单url地址*/
    private String url;
    /*图片地址*/
    private String icon;

    public AdminMenu(String name, String url, String icon) {
        this.name = name;
        this.url = url;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
