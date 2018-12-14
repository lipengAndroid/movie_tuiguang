package com.judian.watch.videos.Mode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李鹏 2017/12/7 0007.
 */

public class UriListMode implements Serializable {

    private List<ban> list = new ArrayList<>();

    public List<ban> getList() {
        return list;
    }

    public void setList(List<ban> list) {
        this.list = list;
    }

    public static class ban implements Serializable {
        private String vid;

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

    }
}
