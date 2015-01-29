<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.green.form.VideoForm" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.green.util.DiskUtil" %>
<%
    String realpath = request.getContextPath();
    List queryResult = null;    //VideoForm 列表
    if (request.getAttribute("list") != null) {
        queryResult = (List) request.getAttribute("list");
    }
    String count = "1";
    if (request.getAttribute("count") != null && !((String) request.getAttribute("count")).equals("")) {
        count = (String) request.getAttribute("count");
    }
    String currentPage = (String) request.getAttribute("currentPage");
    int icount = Integer.parseInt(count);   //总数
    int tPage = (icount - 1) / 10 + 1;       //总页数
    String flag = (String) request.getAttribute("flag");
    if (flag != null)
    {
        if (flag.indexOf("失败") != -1)
        {
            out.println("<script type='text/javascript'> ymPrompt.errorInfo({message:'" + flag + "',title:'操作结果'});</script>");
        }
        else
        {
            out.println("<script type='text/javascript'> ymPrompt.succeedInfo({message:'" + flag + "',title:'操作结果'});</script>");
        }
    }    
    StringBuffer uploadUrl = new StringBuffer("http://");
    uploadUrl.append(request.getHeader("Host"));
    uploadUrl.append(request.getContextPath());
    uploadUrl.append("/FileUploadServlet.htm");
%>
<html>
<link href="<%=realpath%>/js/ymPrompt/skin/qq/ymPrompt.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=realpath%>/js/ymPrompt/ymPrompt.js"></script>

<link href="<%=realpath%>/css/commonNew.css" rel="stylesheet" type="text/css">
<link href="<%=realpath%>/css/styleblue/css/mystyle.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=realpath %>/js/jquery.min.js"></script>

<link href="<%=realpath%>/video/css/default.css" rel="stylesheet" type="text/css" />
<link href="<%=realpath%>/video/css/button.css" rel="stylesheet"type="text/css" />

<link rel="stylesheet" type="text/css" href="<%=realpath%>/video/ext/resources/css/ext-all.css" />
<script type="text/javascript" src="<%=realpath%>/video/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=realpath%>/video/ext/ext-all.js"></script>
<script type="text/javascript" src="<%=realpath%>/video/ext/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=realpath%>/video/js/swfupload/swfupload.js"></script>
<script type="text/javascript" src="<%=realpath%>/video/js/swfupload/handlers.js"></script>
<script language="javascript">
</script>
<script src="<%=realpath%>/js/dialog/lhgcore.min.js" type="text/javascript"></script>
<script src="<%=realpath%>/js/dialog/lhgdialog.js" type="text/javascript"></script>
<head><title>视频列表</title></head>
<body>
<html:form method="post" action="/video.do?method=queryVideos">
    <html:hidden property="video_id"/>
    <input type="hidden" id="real_path" value="<%=realpath%>"/>
    
<table id="search" width="100%" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td align="center"><h2 class="myh2">视频列表</h2></td>
    </tr>
</table>


    <table id="search_table" width="100%" align="center" class="font12blue mytablegray  tdbgcolor" cellpadding="0" cellspacing="0">
        <tr class="font12blue tdbgcolor">
        	<td colspan="3">搜索提醒：支持模糊搜索，比如搜索“电视”时，可以查到包括“电视剧”的结果；搜索内容为空时，将得到所有结果。</td>
        </tr>
        <tr class="gridviewheadertall">
            <td width="20%"  align="center">搜索方式</td>
            <td width="60%"  align="center">关键字</td>
            <td width="20%"  align="center">搜索</td>
        </tr>
        <tr>
            <td  align="center">
                文件名
            </td>
            <td  align="center">
                <html:text property="video_name" style="width:100%; display:block;" />
            </td>
            <td  align="center">
                <input type="button" value="搜索" class="btn_2k3" onmouseout="this.className='btn_2k3'"
                       onmouseover="this.className='btn3_mouseover'" onclick='searchVideo()'>
            </td>
        </tr>
    </table>

<table id="tblSort" width="100%"  class="font12blue sortable mytablegray " cellpadding="0" cellspacing="0" style="border-collapse:collapse">
    <tr id="tablehead">
        <td align="center" bgcolor="#cccccc">&nbsp;</td>
        <td align="center" bgcolor="#cccccc">视频名称</td>
        <td align="center" bgcolor="#cccccc">视频大小</td>
        <td align="center" bgcolor="#cccccc">下载查看</td>
        <td align="center" bgcolor="#cccccc">在线播放</td>
        <td align="center" bgcolor="#cccccc">删除</td>
    </tr>
    <%
        for (int i = 0; i < queryResult.size(); i++) {

            VideoForm tempForm = (VideoForm) queryResult.get(i);
    %>
    <tr>
        <td align="center" width="20px" ><html:checkbox property="selectedIds" value="<%=tempForm.getVideo_id()%>"></html:checkbox>
        </td>
        <td align="center"><%=tempForm.getVideo_name()%>
        </td>
        <td align="center"><%=DiskUtil.formatDouble((double) tempForm.getVideo_size() / 1024 / 1024)%>MB
        </td>
        <td align="center">
            <input type="button" value="下载" class="btn_2k3" onmouseout="this.className='btn_2k3'"
                   onmouseover="this.className='btn3_mouseover'" onclick='downloadFile("<%=tempForm.getVideo_id()%>")'>
            <%--<a href="javascript:downloadFile('<%=str2%>','<%=str1%>')">下载</a>--%>
        </td>
        <td align="center"><input type="button" class="btn_2k3" onmouseout="this.className='btn_2k3'"
                                  onmouseover="this.className='btn3_mouseover'"
                                  onclick="playVideo('<%=tempForm.getVideo_id()%>')" value="播放"
                >
        </td>
        <td align="center"><input type="button" class="btn_2k3" onmouseout="this.className='btn_2k3'"
                                  onmouseover="this.className='btn3_mouseover'"
                                  onclick="deleteVideo('<%=tempForm.getVideo_id()%>')" value="删除"
                />
        </td>
    </tr>
    <%
        }
    %>
    <tr>
        <td colspan="7" align="left" ><input id="cbAll" type="checkbox" onclick="cbAllClicked()">全选   |
            <a onclick="del_selected()" style="cursor:pointer;">删除选中项</a>
        </td>
    </tr>
    <tr>
        <td colspan="13"  align="center" class="">
            <div align="center">每页10条记录 | 共<%=count%>条 |
                <% if ("1".equals(currentPage)) {
                %>
                <a class="">首页 </a>
                <a class="">上页 </a>
                <%
                } else {
                %><a href="javascript:doQuery('1')" class="">首页</a>
                <a href="javascript:doQuery('<%=Integer.parseInt(currentPage)-1%>')" class="cpx12lan1">上页</a>
                <%
                    }
                %>
                <% if (String.valueOf(tPage).equals(currentPage)) {
                %>
                <a class="">下页 </a>
                <a class="">尾页 </a>
                <%
                } else {
                %><a href="javascript:doQuery('<%=Integer.parseInt(currentPage)+1%>')" class="cpx12lan1">下页</a>
                <a href="javascript:doQuery('<%=tPage%>')" class="">尾页</a>
                <%
                    }
                %>
            </div>
        </td>
    </tr>
    <tr>
        <td colspan="13" bgcolor="" align="center" class="">
            |第<%=Integer.parseInt(currentPage)%>页/共<%=tPage%>页| 转到第<input name="textfield" type="text"
                                                                          style="BACKGROUND-COLOR: white; BORDER-BOTTOM: #9C9A9C 1px solid; BORDER-LEFT: #9C9A9C 1px solid; BORDER-RIGHT: #9C9A9C 1px solid; BORDER-TOP: #9C9A9C 1px solid; font-family:Arial; width:  28px; font-size: 10px; height:18px"
                                                                          size="3">页
            <input type="button" value="提交" onclick="doQuery2()"  class="btn_2k3" onmouseout="this.className='btn_2k3'"
                   onmouseover="this.className='btn3_mouseover'">
        </td>
    </tr>

</table>
    <script type="text/javascript">
        var swfu;
        window.onload = function () {
            swfu = new SWFUpload({
                upload_url: "<%=uploadUrl.toString()%>",
                //post_params: {"name" : "huliang"},

                // File Upload Settings
                file_size_limit : "2048 MB",	// 1000MB
                file_types : "*.flv;*.f4v;*.mp4",
                //file_types : "*.*",
                //file_types : "*.jpg;*.png",
                file_types_description : "所有文件",
                file_upload_limit : "0",

                file_queue_error_handler : fileQueueError,//错误将显示在thum...下的infotable中，与上传进度div区分
                file_dialog_complete_handler : fileDialogComplete,//选择好文件后提交
                file_queued_handler : fileQueued,
                upload_progress_handler : uploadProgress,
                upload_error_handler : uploadError,
                upload_success_handler : uploadSuccess,
                upload_complete_handler : uploadComplete,

                // Button Settings
                button_image_url : "<%=realpath%>/video/images/SmallSpyGlassWithTransperancy_17x18.png",
                button_placeholder_id : "spanButtonPlaceholder",
                button_width: 180,
                button_height: 18,
                button_text : '<span class="button">选择视频 <span class="buttonSmall">(支持批量上传)</span></span>',
                button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
                button_text_top_padding: 0,
                button_text_left_padding: 18,
                button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
                button_cursor: SWFUpload.CURSOR.HAND,

                // Flash Settings
                flash_url : "<%=realpath%>/video/js/swfupload/swfupload.swf",

                custom_settings : {
                    upload_target : "divFileProgressContainer"
                },
                // Debug Settings
                debug: false//是否显示调试窗口
            });
        };
        function startUploadFile(){
            swfu.startUpload();
        }
        
    </script>
    <div id="content">
        <form>
            <div
                    style="display: inline; border: solid 1px #7FAAFF; background-color: rgb(131,192,197)/*#C5D9FF*/; padding: 2px;">
                <span id="spanButtonPlaceholder"></span>
                <!--<input id="btnCancel" type="button" value="取消所有上传"
                       onclick="cancelUpload();" disabled="disabled" class="btn3_mouseout" onMouseUp="this.className='btn3_mouseup'"
                       onmousedown="this.className='btn3_mousedown'"
                       onMouseOver="this.className='btn3_mouseover'"
                       onmouseout="this.className='btn3_mouseout'"/>-->
            </div>
        </form>
        <div id="divFileProgressContainer"></div>
        <div id="thumbnails">
            <table id="infoTable" border="0" width="530" style="display: inline; border: solid 1px #7FAAFF; background-color: #C5D9FF; padding: 2px;margin-top:8px;">
            </table>
        </div>
    </div>
<script language="javascript">
    function doQuery(currentPage)
    {
        var form = document.forms[0];
        form.action = "<%=realpath%>/video.do?method=queryVideos&currentPage=" + currentPage;
        form.submit();
    }

    function doQuery2()
    {
        var page = document.all.textfield.value;
        if (page == "")
        {
            ymPrompt.errorInfo({message:'请输入要转向的页数！',title:'缺少条件'});
            return;
        }
        if (isNaN(page))
        {
            ymPrompt.errorInfo({message:'请输入正确数字！',title:'信息提示'});
            return;
        }

        document.forms[0].action = "<%=realpath%>/video.do?method=queryVideos&currentPage=" + page;
        document.forms[0].submit();
    }

    function setUsed(id)
    {
        ymPrompt.confirmInfo({message:'首页上将会显示此视频，确定?',handler:function(xx)
        {
            if (xx == "ok")
            {

                document.all.video_id.value = id;
                document.forms[0].action = "<%=realpath %>/video.do?method=setUsed";
                document.forms[0].submit();
            }
            else
            {
                return;
            }
        }});
    }

    function deleteVideo(id)
    {
        ymPrompt.confirmInfo({message:'是否删除?',handler:function(xx)
        {
            if (xx == "ok")
            {

                document.all.video_id.value = id;
                document.forms[0].action = "<%=realpath %>/video.do?method=doDelete";
                document.forms[0].submit();

            }
            else
            {
                return;
            }
        }});

    }
    function downloadFile(id)
    {
        window.open("<%=realpath %>/video/downloadfile.jsp?id=" + id );
    }

    function searchVideo()
    {
        var fileName = document.all.video_name.value;
//        if (fileName == "")
//        {
//            ymPrompt.errorInfo({message:'请输入搜索关键词！',title:'缺少条件'});
//            return;
//        }
        document.forms[0].action = "<%=realpath%>/video.do?method=queryVideos";
        document.forms[0].submit();
    }
    function cbAllClicked()
    {
        var checkBoxList = document.getElementsByName("selectedIds");
        if (document.getElementById("cbAll").checked == false) {
            for (var i = 0; i < checkBoxList.length; i++) {
                if (checkBoxList[i].disabled != true) {
                    checkBoxList[i].checked = false;
                }
            }
        }
        else {
            for (var i = 0; i < checkBoxList.length; i++) {
                if (checkBoxList[i].disabled != true) {
                    checkBoxList[i].checked = true;
                }
            }
        }
    }


    function del_selected()
    {
        var checkBoxList = document.getElementsByName("selectedIds");
        var flag=false;
        for (var i = 0; i < checkBoxList.length; i++) {
            if (checkBoxList[i].checked == true) {
                flag=true;
            }
        }
        if (flag==false) {
            ymPrompt.errorInfo({message:'请至少选择一个删除项', title:'缺少条件'});
            return;
        }
        ymPrompt.confirmInfo({message:'确认删除?', handler:function (xx) {
            if (xx == "ok") {
                document.forms[0].action = "<%=realpath%>/video.do?method=doDelete&selected=y";
                document.forms[0].submit();
            }
            else {
                return;
            }
        }});
    }
    
    
    function playVideo(id)
    {        
        var real_path = document.getElementById("real_path").value; 
        var win = new Ext.Window({
            title : '视频播放',
            closeAction : 'close',
            width : 680,
            height : 550,
            resizable : false,
            modal : true,
            html : '<iframe src="'+real_path+'/video/js/ckplayer/playVideo.jsp?id='+id+'" width="100%" height="100%"></iframe>'
        });              
        win.show();       
    }    
</script>
</html:form>
</body>
</html>