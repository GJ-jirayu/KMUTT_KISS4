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
<c:set var="ns"><portlet:namespace /></c:set>

<portlet:actionURL var="formAction">
  <portlet:param name="action" value="doSubmit"/>
</portlet:actionURL>
<head>
    <title></title>
  <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.2.min.js"/>"></script>
</head>
<body>
<form:form  id="filterForm" modelAttribute="filterForm" method="post"  name="filterForm" action="${formAction}" enctype="multipart/form-data">
<form:hidden path="instanceId"></form:hidden>
<b>Filters :</b><br/>
<table class="table-hover table-striped table-bordered table-condensed" border="1" style="font-size: 12px">
  <thead>
  <tr>
    <th width="2%"><div class="th_class"><input type="checkbox"  onchange="<portlet:namespace/>changeSelectAll(this)"/></div></th>
    <%--  <th width="7%"><div class="th_class">ปีงบประมาณ</div></th> --%>
    <th width="98%"><div class="th_class">Filter Name</div></th>
  </tr>
  </thead>
  <tbody>
  <c:if test="${not empty filterForm.filterList}">
    <c:forEach items="${filterForm.filterList}" var="filter" varStatus="loop">
      <tr style="cursor: pointer;">
        <td><form:checkbox path="filterGlobals" value="${filter.filterId}"/> </td>
        <td style="text-align: left">${filter.filterName}</td>
      </tr>

    </c:forEach>
  </c:if>
  </tbody>
</table>
<table width="100%">
<tr>
  <td align="center" >
    <button type="submit"
            class="btn btn-primary">Submit
    </button>
  </td>
</tr>
</table>
<script>
  function <portlet:namespace />changeSelectAll(obj){
    //alert(obj.checked)
    $( "input[name='ids']" ).each(function() {
      //$( this ).addClass( "foo" );
      if(!$( this ).prop( "disabled"))
        $( this ).prop( "checked", obj.checked );
    });

  }

</script>
</body>
  </form:form>
</html>
