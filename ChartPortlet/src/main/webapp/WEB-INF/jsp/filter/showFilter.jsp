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
           action="${formAction}" enctype="multipart/form-data">
<b>Filters :</b><br/>
<%--
${filterMap} , ${filterMap["1_1"]} , ${filterMap["1_x"]}
--%>
    <table class="table-hover table-striped table-bordered table-condensed" border="0" style="font-size: 12px;width:100%">
    <thead>
    </thead>
    <tbody>
    <c:if test="${not empty filterList}">
        <tr style="cursor: pointer;">
        <td style="text-align: left">
        <c:forEach items="${filterList}" var="filter" varStatus="loop">
           ${filter.filterM.filterName}
                   &nbsp;&nbsp;
                <select name="aoe_global">
                    <option value="-1">ทั้งหมด</option>
                    <c:if test="${not empty filter.filterM.filterValues}">
                        <c:forEach items="${filter.filterM.filterValues}" var="filterValue" varStatus="loop2">
                            <c:set var="filter_check">${filter.filterM.filterId}_${filterValue.keyMapping}_${filterValue.valueMapping}</c:set>
                            <c:if test="${ not empty filterMap[filter_check] }">
                                <option value="${filter.filterM.filterId}_${filterValue.keyMapping}_${filterValue.valueMapping}" selected>${filterValue.valueMapping}</option>
                            </c:if>
                            <c:if test="${  empty filterMap[filter_check] }">
                                <option value="${filter.filterM.filterId}_${filterValue.keyMapping}_${filterValue.valueMapping}" >${filterValue.valueMapping}</option>
                            </c:if>
                        </c:forEach>
                    </c:if>
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
