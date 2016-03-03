<%--
  Created by IntelliJ IDEA.
  User: imake
  Date: 13/09/2015
  Time: 21:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:set var="ns"><portlet:namespace/></c:set>
<portlet:actionURL var="formAction">
    <portlet:param name="action" value="doSubmit"/>
</portlet:actionURL>
<portlet:resourceURL var="loadChartProp" id="loadChartProp" ></portlet:resourceURL>
<portlet:resourceURL var="loadServiceFilter" id="loadServiceFilter" ></portlet:resourceURL>
<head>
    <title></title>
    <script src="<c:url value="/dwr/engine.js"/>"></script>
    <script src="<c:url value="/dwr/util.js"/>"></script>
    <script src="<c:url value="/dwr/interface/ChartAjax.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.2.min.js"/>"></script>
    <%-- --%>
    <script src="//cdn.ckeditor.com/4.5.4/basic/ckeditor.js"></script>
    <%--
   <script src="//cdn.ckeditor.com/4.5.4/standard/ckeditor.js"></script>

   <script src="//cdn.ckeditor.com/4.5.4/full/ckeditor.js"></script>
   --%>
    <style>
        .border_chart_setting{
            border-style: solid;border-width: 1px;border-color: #1993BF;padding: 10px;
        }
    </style>
</head>
<form:form id="chartSettingForm" modelAttribute="chartSettingForm" method="post" name="chartSettingFormm"
           action="${formAction}" enctype="multipart/form-data">
    <b>Chart Type :</b>
    <form:select path="chartType" id="${ns}chartType" onchange="${ns}loadChartProp('chartJson')" >
        <form:options items="${chartList}" itemValue="chartType" itemLabel="chartName"/>
    </form:select>
    <%--
    <form:select path="chartType" >
        <form:option value="column2d">Column 2D</form:option>
        <form:option value="pyramid">Pyramid Chart</form:option>
        <form:option value="mscolumn3dlinedy">Multi-series Column 3D + Line - Dual Y Axis</form:option>
        <form:option value="mscombidy2d">Multi-series 2D Dual Y Combination Chart (Column + Line + Area)</form:option>
        <form:option value="mscolumn2d">Multi-series Column 2D</form:option>
        <form:option value="mscombi2d">Multi-series 2D Single Y Combination Chart (Column + Line + Area)</form:option>
        <form:option value="mscolumn3d">Multi-series Column 3D</form:option>
        <form:option value="mscombi3d">Multi-series 3D Single Y Combination Chart (Column + Line + Area)</form:option>
        <form:option value="pie2d">Pie 2D</form:option>
        <form:option value="pie3d">Pie 3D</form:option>
        <form:option value="multilevelpie">Multi-level Pie Chart</form:option>
        <form:option value="angulargauge">Real-time Angular</form:option>
        <form:option value="msline">Multi-series Line 2D</form:option>
        <form:option value="MSArea">Multi-series Area 2D</form:option>
        <form:option value="hbullet">Horizontal bullet graph</form:option>
        <form:option value="bar2d">Bar 2D</form:option>
        <form:option value="stackedcolumn2d">Stacked Column 2D</form:option>
        <form:option value="heatmap">Heat Map Chart</form:option>
        <form:option value="gantt">Gantt Chart</form:option>
        <form:option value="msbar2d">Multi-series Bar 2D</form:option>
        <form:option value="stackedcolumn2d">Stacked Column 2D</form:option>
        <form:option value="msstackedcolumn2d">Multi-series Stacked Column 2D</form:option>
        <form:option value="stackedbar2d">Stacked Bar 2D</form:option>
        <form:option value="doughnut3d">Doughnut 3D</form:option>
        <form:option value="doughnut2d">Doughnut 2D</form:option>
        <form:option value="stackedcolumn3dlinedy">Stacked Column 3D + Line Dual Y Axis</form:option>
        <form:option value="radar">Radar Chart</form:option>
        <form:option value="hlineargauge">Real-time Horizontal Linear</form:option>
        <form:option value="hled">Real-time Horizontal LED</form:option>
        <form:option value="vled">Real-time Vertical LED</form:option>
        <form:option value="vbullet">Vertical bullet graph</form:option>
        <form:option value="msstackedcolumn2dlinedy">Multi-series Stacked Column 2D + Line Dual Y Axis</form:option>
        <form:option value="boxandwhisker2d">Box and Whisker Chart</form:option>
        <form:option value="scatter">Scatter Chart</form:option>

    </form:select>
    --%>
    <form:hidden path="chartInstance"></form:hidden>
    <br/>
    <b>Data Source</b><br/>
    <%--
    <input type="radio" name="service_select"/>
    --%>
    <div class="border_chart_setting">
    &nbsp;&nbsp;<form:radiobutton path="dataSourceType" value="1"/> <b>Services: </b>
    <form:select path="dataSource" id="${ns}dataSource" >
        <form:options items="${serviceList}" itemValue="serviceId" itemLabel="serviceName"/>
    </form:select>

    <span style="padding-left: 10px" ><button  class="btn btn-default" type="button" onclick="${ns}listServiceFilterMapping()">Load Filter</button></span><br/><br/>
    &nbsp;&nbsp;<form:radiobutton path="dataSourceType" value="2"/>
    <b>Ad hoc Data </b>&nbsp;&nbsp;<button class="btn btn-default" onclick="${ns}findChartById('1')" type="button">Load Default</button><br/>
    <form:textarea path="dataAdhoc" id="${ns}dataAdhoc" cssStyle="width: 451px; height: 91px;"></form:textarea>
    </div>
    <br/>
   <br/><br/>
    <b>Chart</b><br/>
    <div class="border_chart_setting">
    &nbsp;&nbsp; Title:<form:input path="chartTitle" />  &nbsp;&nbsp;<form:checkbox path="titleFromFilter"  value="1"/> &nbsp;Use From Filter<br/>
    &nbsp;&nbsp; Sub Title:<form:input path="chartSubTitle"/> &nbsp;&nbsp;<form:checkbox path="subFromFilter"  value="1"/> &nbsp;Use From Filter<br/>
    &nbsp;&nbsp; Height:<form:input path="chartHeight"  cssStyle="width:100px" />
    </div>
    <br/>
    <br/>
    <b>Override Property</b> 
		&nbsp;&nbsp;<button class="btn btn-default"  onclick="${ns}loadChartProp('chartJson')" type="button">Load Default</button>
	<br/>
	<div class="border_chart_setting">
	    <form:textarea path="chartJson" id="${ns}chartJson" cssStyle="height: 107px; width: 451px;"></form:textarea><br/>
	</div>
    <br/>
    &nbsp;&nbsp;<form:checkbox path="showFilter"  value="1"/>&nbsp;<b>Show Filter on Front Page</b><br/><br/>
    <b id="${ns}filter_element">Filters for this chart </b><br/>
    <c:if test="${not empty  serviceFilterMappingMList}">
    <div id="${ns}filterMapping_element" class="border_chart_setting">
    </c:if>
    <c:if test="${empty  serviceFilterMappingMList}">
        <div id="${ns}filterMapping_element"  >
    </c:if>
    <span id="${ns}filter_section">
    <c:if test="${not empty  serviceFilterMappingMList}">
        <c:forEach items="${serviceFilterMappingMList}" var="filter" varStatus="loop">
            &nbsp;&nbsp;${filter.filterM.filterName}:&nbsp;
        <select name="aoe_internal"  >
            <option value="-1_-1">ทั้งหมด</option>
            <c:if test="${not empty filter.filterM.filterValues}">
                <c:forEach items="${filter.filterM.filterValues}" var="filterValue" varStatus="loop2">
                    <c:set var="filter_check">${filter.filterM.filterId}_${filterValue.keyMapping}</c:set>
                    <c:if test="${ not empty filterMap[filter_check] }">
                        <option value="${filter.filterM.filterId}_${filterValue.keyMapping}" selected>${filterValue.valueMapping}</option>
                    </c:if>
                    <c:if test="${  empty filterMap[filter_check] }">
                        <option value="${filter.filterM.filterId}_${filterValue.keyMapping}" >${filterValue.valueMapping}</option>
                    </c:if>
                </c:forEach>
            </c:if>
        </select>&nbsp;&nbsp;
        </c:forEach>
    </c:if>
    </span>
  </div>

    <%--
    &nbsp;&nbsp; ประเภทผลงาน&nbsp;<select name="aoe_internal" style="width:100px" >
    <option >  </option>
    <option value="1">ผลงานวิจัย</option>
    </select>
     --%>
     <br/> <br/>
    &nbsp;&nbsp;<form:checkbox path="filterRole"  value="1"/> &nbsp;<b>Enable Role Filter</b><br/>
    <%--
    <form:input path="filterRole"  cssStyle="width: 451px;"/>

    <br/>
    --%>
    <br/>
    <b>Comment: </b><br/>
    <form:textarea path="comment" id="${ns}comment" cssStyle="width: 451px; height: 52px;"></form:textarea><br/>
    <script>
        // Replace the <textarea id="editor1"> with a CKEditor
        // instance, using default configuration.
        CKEDITOR.replace( '${ns}comment' );
    </script>
    <br/>
    <b>Link to: </b><br/>
    <form:input path="linkTo" cssStyle="width: 451px;"></form:input><br/>
    <button class="btn btn-primary" type="submit">Submit</button>
</form:form>
<script>
	function ${ns}toggleOverrideChartJson(current){
		/*if(current.checked){ // not use anymore
			$("#${ns}chartJson").attr("disabled",false);
		}else{
			$("#${ns}chartJson").attr("disabled",true);
		}*/
	}
	function ${ns}loadChartProp(prop){ // create by GJ
		var chartType=$('#${ns}chartType').val();
		$.ajax({
   	 		dataType: "json",
   	 		url:"<%=loadChartProp%>",
   	 		data: { chartType : chartType , prop: prop },
   	 		success:function(data){
   	 			if(data["header"]["success"]>0){
   	 				if( prop =='chartJson'){
   	 				 $("#${ns}chartJson").val(data["content"]);
   	 				}
   	 			}
   	 		} 
   	 	});
		
	}
	function ${ns}loadServiceFilter(){
	    var serviceId=$('#${ns}dataSource').val();
	    $.ajax({
   	 		dataType: "json",
   	 		url:"<%=loadServiceFilter%>",
   	 		data: { serviceId : serviceId },
   	 		success:function(data){
   	 			if(data["header"]["success"]>0){
   	 				if( prop =='chartJson'){
   	 				 $("#${ns}chartJson").val(data["content"]);
   	 				}
   	 			}
   	 		} 
   	 	});
	}
    function ${ns}findChartById(type){
        var chartType=$('#${ns}chartType').val();
        //alert(chartType)
        var chartM={
            chartType:chartType
        }

         ChartAjax.listChart(chartM, {
            callback: function (data) {
            //  data = data.resultListObj;
                if (data != null && data.length>0) {
                    if(type=='1')
                        $("#${ns}dataAdhoc").val(data[0].dataJson);
                }
            }
         });

    }
    function ${ns}listServiceFilterMapping(){
        var serviceId=$('#${ns}dataSource').val();
        var serviceFilterMapping={
            serviceId:serviceId
        }
        $("#${ns}filterMapping_element").removeClass("border_chart_setting");
        ChartAjax.listServiceFilterMapping(serviceFilterMapping, {
            callback: function (data) {
                //  data = data.resultListObj;
                var str="";

                if (data != null && data.length > 0) {
                   for(var i=0;i<data.length;i++){
                       str=str+"&nbsp;&nbsp;"+data[i].filterM.filterName+":&nbsp;"+
                                "<select name=\"aoe_internal\"  >"+
                                " <option value=\"-1_-1\">ทั้งหมด</option>";
                                if(data[i].filterM.filterValues!=null && data[i].filterM.filterValues.length>0){
                                    // alert(data[i].filterM.filterValues.length)
                                    for(var j=0;j<data[i].filterM.filterValues.length;j++){
                                        //alert(data[i].filterM.filterValues[j].valueMapping)
                                        str=str+" <option value="+data[i].filterM.filterId+"_"+data[i].filterM.filterValues[j].keyMapping+">"+data[i].filterM.filterValues[j].valueMapping+"</option> ";
                                    }
                                }

                       str=str+" </select><br/>";

                   }
                    $("#${ns}filterMapping_element").addClass("border_chart_setting");
                }
                $("#${ns}filter_section").html(str);

            }
        });
       <%--  $("#${ns}filter_element").focus(); --%>
    }
    function ${ns}testCall() {
        var filterM={
            filterId:"1"
        }
        /*
        ChartAjax.testCall(filterM, {
            callback: function (data) {
              //  data = data.resultListObj;
                if (data != null && data.length > 0) {
                   alert(data.length)
                }
            }
        });
        */
    }
</script>
</body>
</html>
