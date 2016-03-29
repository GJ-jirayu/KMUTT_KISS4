<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="javax.portlet.PortletURL"%>
<%--
  Created by IntelliJ IDEA.
  User: imake
  Date: 07/09/2015
  Time: 17:56
  To change this template use File | Settings | File Templates.
--%>
<html>

<portlet:resourceURL var="cascadeGlobalFilter" id="cascadeGlobalFilter"></portlet:resourceURL>  
<portlet:actionURL var="formAction">
    <portlet:param name="action" value="doSubmit"/>
</portlet:actionURL>
	<head>
    <title></title>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.2.min.js"/>"></script>
	<script>
		function regenerateItem(id,val,items){
			var g_fiter_prefix = "g_filter_";
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
		function cascadeGlobal(current){
			var filterId = current.id.replace("g_filter_","");
			var factor = [];
			var limitor = ":#:";
			var seperate = ":&:";
			$("#filterForm select.global_filter").each(function(){
				factor.push(this.id.replace("g_filter_","")+seperate+this.value);
			});
			$("#cascadeWaiting").show();
			$(".global_filter").prop("disabled",true);
			$.ajax({
	   	 		dataType: "json",
	   	 		url:"<%=cascadeGlobalFilter%>",
	   	 		data: { filterId : filterId , factor : factor.join(limitor)  },
	   	 		success:function(data){
	   	 			$("#cascadeWaiting").hide();
	   	 			$(".global_filter").prop("disabled",false);
	   	 			for(var i = 0 ; i<data["content"].length;i++){
	   	 				regenerateItem(data["content"][i]["id"],data["content"][i]["value"],data["content"][i]["item"]);
	   	 			}
	   	 		} //end success 
	   	 	}); // end ajax
		} //end function
	</script>
</head>
<body>
<form:form id="filterForm" modelAttribute="filterForm" method="post" name="filterForm"
           action="${formAction}" enctype="multipart/form-data" style="margin:0 0 0 0">
	<b>Filters :</b> <img id="cascadeWaiting"  src="<c:url value="/resources/images/rotate.gif"/>" style="cursor:pointer;width:22px;height: 22px;padding-left:5px;display:none;" />
	<br/> 
    <table class="" border="0" style="font-size: 14px;width:100%">
    <thead>
    </thead>
    <tbody>
    <c:if test="${not empty filterList}">
        <tr style="">
        <td style="text-align: left">
        <c:forEach items="${filterList}" var="filter" varStatus="loop">
           ${filter.filterName}  
                   &nbsp;&nbsp;
                <select id="g_filter_${filter.filterId}" name="g_filter_${filter.filterId}" class="global_filter" onchange="cascadeGlobal(this)">
                       	<c:forEach items="${filter.filterValues}" var="filterValue" varStatus="loop2">
                            <c:choose>
							    <c:when test="${filter.selectedValue.equals(filterValue.keyMapping)}">
							        <option value="${filterValue.keyMapping}" selected>${filterValue.valueMapping}</option>
							    </c:when>    
							    <c:otherwise>
							            <option value="${filterValue.keyMapping}">${filterValue.valueMapping}</option>
							    </c:otherwise>
							</c:choose>
                        </c:forEach>
                </select>
            &nbsp;&nbsp;
        </c:forEach>
        </td>
        </tr>
    </c:if>
    </tbody>
	</table>
    <table width="100%">
        <tr>
            <td align="center">
            <button type="submit"
            class="btn btn-primary">Submit
    </button>
            </td>
            </tr>
        </table>
</form:form>
</body>
</html>
