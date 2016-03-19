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
<head>
    <title></title>
</head>
<body>
<portlet:actionURL var="formAction">
    <portlet:param name="action" value="doSubmit"/>
</portlet:actionURL>
<form:form id="filterForm" modelAttribute="filterForm" method="post" name="filterForm"
           action="${formAction}" enctype="multipart/form-data" style="margin:0 0 0 0">
<b>Filters :</b><br/>
<%--
${filterMap} , ${filterMap["1_1"]} , ${filterMap["1_x"]}
--%>
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
                <select id="g_filter_${filter.filterId}" name="g_filter_${filter.filterId}">
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
