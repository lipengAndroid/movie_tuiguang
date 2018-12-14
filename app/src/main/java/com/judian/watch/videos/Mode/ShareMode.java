package com.judian.watch.videos.Mode;

/**
 * Created by 李鹏 2017/12/18 0018.
 */

public class ShareMode extends PublicMode {

    /**
     * title : 邀请好友免费看VIP视频
     * content : 邀请好友后可免费观看全网所有VIP视频
     * imageurl : http://judian.s1.natapp.cc/movie/public/problem/share/20171215/4dc572bc46d5dc0c076ff28cafb73dab.jpg
     * url : http://shangpin.com/index.php?s=index/share/share_h5
     */

    private String title;
    private String content;
    private String imageurl;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
