<%--
  Created by IntelliJ IDEA. (imake)
  Date: 07/09/2015
  Time: 19:10
  To change this template use File | Settings | File Templates.
  REVESION BY GJ.PK.m  
  Date: 2016-03
--%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ page contentType="text/html; charset=utf-8" %>
<c:set var="ns"><portlet:namespace/></c:set>
<portlet:resourceURL var="cascadeInternalFilter" id="cascadeInternalFilter"></portlet:resourceURL>  
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <title></title>
    <style type="text/css">
	    .aui .table td{
	    	background-color:transparent;
	    }
    	.aui .table thead th {
	    	background-color:transparent;
	    }
	    .aui select{
	    	width:auto !important;
	    	max-width:98%;
	    }
	    .chartContainer .aui .table thead th {
	    	background-color:transparent;
	    }
	    .chartContainer .aui .table{
	    background-color:transparent;
	    }
	    .chartContainer .aui .table tbody{
	    background-color:transparent;
	    }
	     .chartContainer .aui .table tbody td {
	     	background-color:transparent;
	     }
	      .chartContainer .aui .table thead{
	      background-color:transparent;
	      }
	     .chartContainer .aui .table thead td {
	     	background-color:transparent;
	     }
    </style>
    <!-- Bootstrap core CSS -->
    <%--
    <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>" type="text/css"/>
--%>

</head>
<!-- class="icon-comment icon-white"  -->
<%--
<div><i id="${ns}myTooltip"  class="icon-comment icon-white"  title="${chartSettingForm.comment}"
        style="position:relative;top:0;left:97%;cursor: pointer;background-image:
                url('<c:url value="/resources/images/comment.jpg"/>') ">

</i> </div>
--%>
<div>
    <%--
    <button id="${ns}comment_bt" type="button" class="btn btn-lg btn-danger" data-toggle="popover" title="Comment"
            data-content="${chartSettingForm.comment}">Click to toggle popover</button>
--%>
        <a tabindex="0" id="${ns}comment_bt"
            data-toggle="popover" data-trigger="focus" title="Comment"
           data-content="${chartSettingForm.comment}">
            <img id="${ns}linktox" src="<c:url value="/resources/images/comment.jpg"/>" style="cursor:pointer;width:16px;height: 16px;padding-left:5px" />
        </a>
        <%--
        <img id="${ns}comment_bt"  data-toggle="popover" data-trigger="focus" title="Comment"
             data-content="${chartSettingForm.comment}" src="<c:url value="/resources/images/comment.jpg"/>"
        style="position:relative;top:0;left:0%;cursor:pointer;width:16px;height: 16px"  />
    <img id="${ns}myTooltip"   src="<c:url value="/resources/images/comment.jpg"/>"
          style="position:relative;top:0;left:0%;cursor:pointer;width:16px;height: 16px" title="${chartSettingForm.comment}" />
          --%>
    <c:if test="${not empty chartSettingForm.linkTo}">
        <%--  onclick="${ns}linkto('${chartSettingForm.linkTo}')"
        onclick="window.open('${chartSettingForm.linkTo}');" target="_blank"
        --%>
        <a  onclick='${ns}linkto("${chartSettingForm.linkTo}")'  >
        <img id="${ns}linktox" src="<c:url value="/resources/images/link-xxl.png"/>" style="cursor:pointer;width:16px;height: 16px;padding-left:5px" />
            </a>
    </c:if>
	<img id="${chartSettingForm.chartInstance}_cascadeWaiting"  src="<c:url value="/resources/images/rotate.gif"/>" style="cursor:pointer;width:22px;height: 22px;padding-left:5px;display:none;" />
</div>
<c:if test="${chartSettingForm.dataSourceType=='1' && chartSettingForm.showFilter=='1' }">
    <portlet:actionURL var="formAction">
        <portlet:param name="action" value="doSubmit"/>
    </portlet:actionURL>
    <form:form id="chartSettingForm" modelAttribute="chartSettingForm" method="post" name="chartSettingFormm"
               action="${formAction}" enctype="multipart/form-data">
        <form:hidden path="dataSourceType"/>
        <form:hidden path="chartInstance"/>
        <form:hidden path="globalFilterString"/>
	    <c:forEach items="${filters}" var="filter" varStatus="loop">
	    	<div style="display:inline-block">
	        &nbsp;&nbsp;${filter.filterName}:&nbsp;
	        <select id="filter_${chartSettingForm.chartInstance}_${filter.filterId}" name="filter_${chartSettingForm.chartInstance}_${filter.filterId}" class="filter_${chartSettingForm.chartInstance}" onchange="F${chartSettingForm.chartInstance}_cascade(this)" >
	        	<c:forEach items="${filter.filterValues}" var="item" varStatus="loop2">
		        	<c:choose>
					    <c:when test="${filter.selectedValue.equals(item.keyMapping)}">
					        <option value="${item.keyMapping}" selected>${item.valueMapping}</option>
					    </c:when>    
					    <c:otherwise>
					            <option value="${item.keyMapping}">${item.valueMapping}</option>
					    </c:otherwise>
					</c:choose>
	        	</c:forEach>
        	</select>
        	&nbsp;&nbsp;
        	</div>
    	</c:forEach>
    	<div style="display:inline-block">
	    	<button type="submit"  class="btn btn-primary">Submit</button>
	    </div>
    </form:form>
</c:if>
<table border="0" width="100%">
<div align="center" id="${ns}chart_table_caption"></div>
<div align="center" id="${ns}chart_table_subCaption"></div>
</table>
<div id="${ns}chartContainer" class="chartContainer"> Please Config Chart!</div>
<%--
<span id="${ns}chartContainer" style="line-height: 100%; display:
inline-block; zoom: 1; width: 99%; height: 34.65px; background-color: rgb(255, 255, 255);">
Please Config Chart!
</span>
--%>

<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.2.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/fusioncharts/js/fusioncharts.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/fusioncharts/js/themes/fusioncharts.theme.fint.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/wtpTable.js"/>"></script>


<script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script>
<%--
<script src="<c:url value='/resources/js/bootbox.min.js'/>" type="text/javascript"></script>
--%>
<script type="text/javascript">
    function ${ns}linkto(link_url){
        window.open(link_url,"_blank");
    }
    $(document).ready(function () {
        $("#${ns}comment_bt").popover({
            html:true
        });
        $("#${ns}comment_bt2").popover({
            html:true
        });
        var dataSource;
        var chartype="${chartSettingForm.chartType}";
        var chartHeight="300"; //default
        if(chartype=="gantt"){ chartHeight="600"; }
        else if(chartype=="hbullet"){ chartHeight="150"; }
        <c:if test="${not empty chartSettingForm.jsonStr && chartSettingForm.chartType!='table'}">
        var revenueChart = new FusionCharts({
            "type": "${chartSettingForm.chartType}",
            "renderAt": "${ns}chartContainer",
            "width": "100%", // 500
            "height": "${chartSettingForm.chartHeight}", // chartHeight,
            "dataFormat": "json",
            "dataSource":${chartSettingForm.jsonStr}

        });
        revenueChart.render();
        </c:if>        
        <c:if test="${chartSettingForm.chartType=='table' }">
			   	var jsonStrObj=${chartSettingForm.jsonStr};
				var table1 = new wtpTable("#${ns}chartContainer",jsonStrObj);
				table1.updatePath("/ChartPortlet");
				table1.render();
        </c:if>
    });
	function F${chartSettingForm.chartInstance}_regenerateItem(id,val,items){
		var g_fiter_prefix = "filter_${chartSettingForm.chartInstance}_";
		var cnt = $("#"+g_fiter_prefix+id);  
		cnt.empty();
		for(var i=0;i<items.length;i++){
			var opt = $("<option/>");
			opt.attr("value",items[i]["key"]);
			opt.html(items[i]["desc"]);
			cnt.append(opt);
		}
		//cnt.val(val); // *becareful, are u sure to open this?*
	}
	function F${chartSettingForm.chartInstance}_cascade(current){
		var filterId = current.id.replace("filter_${chartSettingForm.chartInstance}_","");
		var factor = [];
		var limitor = ":#:";
		var seperate = ":&:";
		$("select.filter_${chartSettingForm.chartInstance}").each(function(){
			factor.push(this.id.replace("filter_${chartSettingForm.chartInstance}_","")+seperate+this.value);
		});
		$("#${chartSettingForm.chartInstance}_cascadeWaiting").show();
		$(".filter_${chartSettingForm.chartInstance}").prop("disabled",true);
		$.ajax({
   	 		dataType: "json",
   	 		url:"<%=cascadeInternalFilter%>",
   	 		data: { filterId : filterId , factor : factor.join(limitor)  },
   	 		success:function(data){
   	 			$("#${chartSettingForm.chartInstance}_cascadeWaiting").hide();
   	 			$(".filter_${chartSettingForm.chartInstance}").prop("disabled",false);
   	 			for(var i = 0 ; i<data["content"].length;i++){
   	 				F${chartSettingForm.chartInstance}_regenerateItem(data["content"][i]["id"],data["content"][i]["value"],data["content"][i]["item"]);
   	 			}
   	 		} //end success 
   	 	}); // end ajax
	} //end function
</script>
        </body>
</html>
