package com.judian.watch.videos.Mode;

/**
 * Created by Administrator on 2017/11/6 0006.
 */

public class UpFileMode {

    /**
     * rows : {"url":"http://39.108.215.103:8899/zsj-scp/upload/images/201711/3f726486-bafc-4e63-a672-c33a5845646d.png"}
     */

    private RowsBean rows;

    public RowsBean getRows() {
        return rows;
    }

    public void setRows(RowsBean rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * url : http://39.108.215.103:8899/zsj-scp/upload/images/201711/3f726486-bafc-4e63-a672-c33a5845646d.png
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
