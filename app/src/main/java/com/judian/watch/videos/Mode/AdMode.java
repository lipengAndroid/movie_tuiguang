package com.judian.watch.videos.Mode;

/**
 * Created by 李鹏 2017/12/21 0021.
 */

public class AdMode extends PublicMode {

    /**
     * id : 1
     * title : 测试广告
     * url : http://www.baidu.com
     * miao : 5
     */

    private int id;
    private String title;
    private String url;
    private int miao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMiao() {
        return miao;
    }

    public void setMiao(int miao) {
        this.miao = miao;
    }
}
