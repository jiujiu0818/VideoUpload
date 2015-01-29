<%@ page import="com.green.form.VideoForm" %>
<%@ page import="com.green.dao.VideoDao" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
  String id = request.getParameter("id");
    VideoForm form=new VideoDao().getVideoById(id);
  String filename = form.getVideo_name();
  String filepath =form.getVideo_url();
  if ("".equals(filepath) || filepath == null)
  {
    out.println("<script>alert('没有该文件！');window.close();</script>");
  }
  else
  {
    java.io.BufferedInputStream bis = null;
    java.io.BufferedOutputStream bos = null;
    try
    {

      response.setContentType("application/x-msdownload");
      response.setHeader("Content-disposition", "attachment; filename=" + new String(filename.getBytes("gbk"), "iso8859-1"));
      bis = new java.io.BufferedInputStream(new java.io.FileInputStream(filepath));
      bos = new java.io.BufferedOutputStream(response.getOutputStream());
      byte[] buff = new byte[2048];
      int bytesRead;
      while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
      {
        bos.write(buff, 0, bytesRead);
      }

    }
    catch (Exception e)
    {
      out.println("<script>alert('您下载的路径不对！');window.close();</script>");
    }
    finally
    {
      out.clear();
      out = pageContext.pushBody();
      if (bis != null) bis.close();
      if (bos != null) bos.close();
    }


  }
%>
