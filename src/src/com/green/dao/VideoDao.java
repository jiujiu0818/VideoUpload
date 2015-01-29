package com.green.dao;

import com.green.util.DBUtil;
import com.green.form.VideoForm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VideoDao {
    public String addVideo(VideoForm form)throws Exception{
        Connection conn=null;
        PreparedStatement ps=null;
        String sql="insert into i_video(video_id,video_name,video_desc,isused,video_url,video_size)values(SEQ_index_video.nextval,?,?,?,?,?)";
        try{
            conn= DBUtil.getCurrentConnection();
            if(form.getIsused()){
               Statement stmt=conn.createStatement();
               stmt.execute("update i_video set isused = '0' where isused='1'");
               conn.commit();
               DBUtil.close(stmt);
            }
            ps=conn.prepareStatement(sql);
            ps.setString(1,form.getVideo_name());
            ps.setString(2,form.getVideo_desc());
            ps.setBoolean(3, form.getIsused());
            ps.setString(4,form.getVideo_url());
            ps.setLong(5,form.getVideo_size());
            ps.execute();
            conn.commit();
            return "添加成功！";
        }catch (Exception ex){
            if(conn!=null){
                conn.rollback();
            }
            ex.printStackTrace();
            return "添加失败！";
        }finally {
            DBUtil.close(ps);
        }
    }

    //将某个视频显示在首页上
    public String setUsed(VideoForm form)throws Exception{
        Connection conn=null;
        Statement stmt =null;
        String sql="update i_video set isused = '0' where isused='1'";
        String sql2="update i_video set isused='1' where video_id ="+form.getVideo_id();
        try{
            conn= DBUtil.getCurrentConnection();
            stmt=conn.createStatement();
            stmt.addBatch(sql);
            stmt.addBatch(sql2);
            stmt.executeBatch();
            conn.commit();
            return "设置成功！";
        }catch (Exception ex){
            if(conn!=null){
                conn.rollback();
            }
            ex.printStackTrace();
            return "设置失败！";
        }finally {
            DBUtil.close(stmt);
        }
    }

    public String doDelete(VideoForm form)throws Exception{
        Connection conn=null;
        Statement stmt =null;
        String sql="update i_video set isdeleted='1' where video_id ="+form.getVideo_id();
        try{
            conn= DBUtil.getCurrentConnection();
            stmt=conn.createStatement();
            stmt.execute(sql);
            conn.commit();
            return "删除成功！";
        }catch (Exception ex){
            if(conn!=null){
                conn.rollback();
            }
            ex.printStackTrace();
            return "删除失败！";
        }finally {
            DBUtil.close(stmt);
        }

    }

    public String doMultiDelete(VideoForm form)throws Exception{
        Connection conn=null;
        Statement stmt =null;
        String ids="";
        for(int i=0;i<form.getSelectedIds().length;i++){
            if(i==0){
                ids+=form.getSelectedIds()[i];
            }else {
                ids+=","+form.getSelectedIds()[i];
            }
        }
        String sql="update i_video set isdeleted='1' where video_id in ("+ids+")";
        //System.out.println(sql);
        try{
            conn= DBUtil.getCurrentConnection();
            stmt=conn.createStatement();
            stmt.execute(sql);
            conn.commit();
            return "删除成功！";
        }catch (Exception ex){
            if(conn!=null){
                conn.rollback();
            }
            ex.printStackTrace();
            return "删除失败！";
        }finally {
            DBUtil.close(stmt);
        }

    }
    public List queryVideos(VideoForm form, int page, int pageSize) throws Exception {
        List list = new ArrayList();
        int num1 = pageSize * (page - 1) + 1;
        int num2 = num1 + (pageSize - 1);
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getCurrentConnection();
            //select video_id,video_name,video_desc,isused,video_url from(select i.*, rownum no from i_video i where i.isdeleted='0' order by i.video_id)where no between 8 and 9
            String sql = "select video_id,video_name,video_desc,isused,video_url,video_size " +
                    "from(select t.*,rownum no from(select * from i_video i where i.isdeleted='0'";
            if(form.getVideo_name()!=null&&!form.getVideo_name().equals("")){
                sql+="and i.video_name like '%"+form.getVideo_name()+"%'";
            }
            sql+=" order by i.isused desc, i.addtime desc) t )" +
                    "where no between "+num1+" and "+num2;
                // System.out.println(sql);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                VideoForm tempForm = new VideoForm();
                tempForm.setVideo_id(judgeNull(rs.getString(1)));
                tempForm.setVideo_name(judgeNull(rs.getString(2)));
                tempForm.setVideo_desc(judgeNull(rs.getString(3)));
                tempForm.setIsused(rs.getBoolean(4));
                tempForm.setVideo_url(judgeNull(rs.getString(5)));
                tempForm.setVideo_size(rs.getLong(6));
                list.add(tempForm);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DBUtil.close(rs);
            DBUtil.close(ps);
        }
        return list;
    }

    public int getVideosCount() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = null;
        int count = 0;
        try {
            conn = DBUtil.getCurrentConnection();
            sql = "select count(*) from(select t.video_id from i_video t where t.isdeleted='0' )";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("error at getPapersCount()");
        }
        finally {
            DBUtil.close(rs);
            DBUtil.close(ps);
        }
        return count;
    }
    //不完整，形如upload/project\2012\11\10\69916bb4-78b7-449a-bbdf-afbf0bb4eb1b.avi
    public String getUsedUrl() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = null;
        String url="";
        try {
            conn = DBUtil.getCurrentConnection();
            sql = "select t.video_url from i_video t where t.isused='1'and t.ISDELETED ='0'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                url = rs.getString(1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("error at getPapersCount()");
        }
        finally {
            DBUtil.close(rs);
            DBUtil.close(ps);
        }
        return url.trim();
    }
    public String getUsedId() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = null;
        String id="";
        try {
            conn = DBUtil.getCurrentConnection();
            sql = "select t.video_id from i_video t where t.isused='1' and t.ISDELETED ='0'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getString(1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("error at getPapersCount()");
        }
        finally {
            DBUtil.close(rs);
            DBUtil.close(ps);
        }
        return id;
    }

    public VideoForm getVideoById(String id) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = null;
        String url="";
        VideoForm form=new VideoForm();
        try {
            conn = DBUtil.getCurrentConnection();
            sql = "select t.video_name,t.video_url from i_video t where t.video_id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,id);
            rs = ps.executeQuery();
            if (rs.next()) {
               form.setVideo_name(rs.getString(1));
               form.setVideo_url(rs.getString(2));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("error at getPapersCount()");
        }
        finally {
            DBUtil.close(rs);
            DBUtil.close(ps);
        }
        return form;
    }
    /*
    * 将boolean转换成string
    * */
//    private  String  b2s(boolean b)
//    {
//        String s;
//        if(b){
//            s="1";
//        }else {
//            s="0";
//        }
//        return  s;
//    }
    public String judgeNull(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }


 }

