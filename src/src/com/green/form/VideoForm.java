package com.green.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class VideoForm extends ActionForm {
    private String video_id;    //自增主键
    private String video_name;         //文件名，包含后缀名
    private String video_desc;
    private String video_url;           //物理路径
    private String addtime;              //创建时间，不用插入，default即为sysdate
    private String isdeleted;            // 1=删除
    private boolean isused;               //是否展示在首页     1=展示，整个表中，仅一个可以展示在首页
    private long video_size;
    private String[] selectedIds;
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        isused=false;
        super.reset(mapping, request);
    }


    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getVideo_desc() {
        return video_desc;
    }

    public void setVideo_desc(String video_desc) {
        this.video_desc = video_desc;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(String isdeleted) {
        this.isdeleted = isdeleted;
    }

    public boolean getIsused() {
        return isused;
    }

    public void setIsused(boolean isused) {
        this.isused = isused;
    }

    public long getVideo_size() {
        return video_size;
    }

    public void setVideo_size(long video_size) {
        this.video_size = video_size;
    }

    public String[] getSelectedIds() {
        return selectedIds;
    }

    public void setSelectedIds(String[] selectedIds) {
        this.selectedIds = selectedIds;
    }
}
