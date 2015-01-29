package com.green.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class VideoForm extends ActionForm {
    private String video_id;    //��������
    private String video_name;         //�ļ�����������׺��
    private String video_desc;
    private String video_url;           //����·��
    private String addtime;              //����ʱ�䣬���ò��룬default��Ϊsysdate
    private String isdeleted;            // 1=ɾ��
    private boolean isused;               //�Ƿ�չʾ����ҳ     1=չʾ���������У���һ������չʾ����ҳ
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
