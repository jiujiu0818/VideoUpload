package com.green.action;

import com.green.dao.VideoDao;
import com.green.form.VideoForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class VideoAction extends DispatchAction {
    public ActionForward addVideo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
        VideoForm dform=(VideoForm)form;
        String flag="";
        try{
            VideoDao dao=new VideoDao();
            flag=dao.addVideo(dform);
            request.setAttribute("flag",flag);
            return mapping.findForward("addVideo");
        }catch (Exception ex){
            ex.printStackTrace();
            return mapping.findForward("error");
        }
    }
    public ActionForward queryVideos(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
        VideoForm dform=(VideoForm)form;
        String sql_con = "";
        VideoDao dao=new VideoDao();
        String page = request.getParameter("currentPage");
        int ipage = 1;  //Ä¬ÈÏÒ³Êý
        try {
            if (page != null) {
                ipage = Integer.parseInt(page);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            List list = dao.queryVideos(dform,ipage, 10);
            int count = dao.getVideosCount();
            request.setAttribute("currentPage", String.valueOf(ipage));
            request.setAttribute("list", list);
            request.setAttribute("count", String.valueOf(count));
            return mapping.findForward("videoList");
        }
        catch (Exception ex) {
            return mapping.findForward("error");
        }
    }

    public ActionForward setUsed(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
        VideoForm dform=(VideoForm)form;
        String flag="";
        try{
            VideoDao dao=new VideoDao();
            flag=dao.setUsed(dform);
            request.setAttribute("flag",flag);
            return mapping.findForward("queryVideos");
        }catch (Exception ex){
            ex.printStackTrace();
            return mapping.findForward("error");
        }
    }

    public ActionForward doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
        VideoForm dform=(VideoForm)form;
        String flag="";
        try{
            VideoDao dao=new VideoDao();
            if(request.getParameter("selected")!=null&&request.getParameter("selected").equals("y"))
            {
                flag=dao.doMultiDelete(dform);
            }else {
                flag=dao.doDelete(dform);
            }
            request.setAttribute("flag",flag);
            return mapping.findForward("queryVideos");
        }catch (Exception ex){
            ex.printStackTrace();
            return mapping.findForward("error");
        }
    }
}
