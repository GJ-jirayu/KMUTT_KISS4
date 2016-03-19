/**
 * Create By pongkorn
 */
function wtpTable(x,y){
	this.dir_root = window.location.href;
	this.jsonString = y;
	this.container = x;
	this.defaultFont = "MS Sans Serif";
	this.defaultFontSize = "14";
	this.json = JSON.parse(this.jsonString);   // JSON.stringify(json)
	this.contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}
wtpTable.prototype.updatePath = function(x){
	this.dir_root = x;
};
wtpTable.prototype.getRootPath = function(){
	return this.dir_root;
};
wtpTable.prototype.createTable = function(){
	 if( this.json!=null && this.container!=null ){
		 //define function help
		 var numberWithCommas = function(x) {
				return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		 }
		 var textToNumber = function(x) {
			 if( typeof x == "undefined" || x == "undefined"){
				 return 0;
			 }else{
				 return Number(x.toString().replace(",",""));
			 }
		 } 
		 var isDuplicate = function(ar,x){
			 // if dup return original position
			 var total = 0;
			 for(var i = 0 ; i<ar.length;i++){
				 if(ar[i]==x){  total++; }
			 }
			 var dup = total<=1? 0:1;
			 return dup
		 }
		 var uniqueArray = function(ar){
			 var uniqueNames = [];
				$.each(ar, function(i, el){
				    if($.inArray(el, uniqueNames) === -1) uniqueNames.push(el);
				});
			return uniqueNames;
		 }
		 var ascSort = function(selection,pos){
			 // 1 dimension
			 var pos = pos; // min 1 not zero
			 var items = [];
			 var newTbody = $("<tbody></tbody");
			 var tbody = $(selection).children("table").children("tbody");
			 var length = tbody.children("tr").length;
			 tbody.children("tr.dataset").children("td:nth-child("+pos+")").each(function(){
				items.push($(this).html()); 
			 });
			 //sort
			 var newItems = items.slice(0).sort();  // slice(0) mean not replace old items
			 // swap (generate newBody)
			 for(var i = 0 ; i<newItems.length;i++){
				 var idx = parseInt(items.indexOf(newItems[i]));
				 items[idx] = "";
				 var lineIdx = idx + 1;
				 newTbody.append(tbody.children("tr:nth-child("+lineIdx+")").clone());
				 
			 }
			 //renew
			 tbody.empty();
			 tbody.append(newTbody.html());
		 }
		 var swSort = function(x){
			 var pos = x;
			 
		 }
		 var wGroup = function(selection,cateIdx,headIdxAr,prop){
			 //grouping
			 // retrive Meta 
			 // may be
			 // do group
			 var tbody = $(selection).children("table").children("tbody");
			var pos = cateIdx;
			 var items = [];
			 tbody.children("tr.dataset").children("td:nth-child("+pos+")").each(function(){
					items.push($(this).html()); 
				 });
			 
			 var uniq = uniqueArray(items);
			 var totals = [];
			 $.each(uniq,function(i,cate){
				 //lookup in table sumary
				 totals = [];
				 var lastLineNumber = 0;
				 tbody.children("tr").each(function(i,el){
					 var row = $(el);
					 var txt = row.children("td:nth-child("+pos+")").html();
					 if(txt==cate){
						 lastLineNumber = i + 1;
						 $.each(headIdxAr,function(i,el){  
							 totals[el] = textToNumber(totals[el])+textToNumber(row.children("td:nth-child("+this+")").html());
						 });
					 }
				 });// end lookup row
				 // create group summary line
				 var lastLine =  tbody.children("tr:nth-child("+lastLineNumber+")");
				 var newTr = lastLine.clone();
				 newTr.removeClass().addClass("wtpGroup");
				 newTr.attr("style"," background-color :" +prop.bgColor);
				 newTr.children("td").empty();
				 newTr.children("td:nth-child("+pos+")").html(" ผลรวม "+cate);
				 $.each(totals,function(i,total){
					 if(total!=null){
						 newTr.children("td:nth-child("+i+")").html(numberWithCommas(total));
					 }
				 });
				 newTr.insertAfter(lastLine);;
				 
			 }); // end lookup uniq group text
		 }
		 // do begin
		 var jsonStrObj =   this.json;
		 var caption=jsonStrObj.table.caption;
	     var subCaption=jsonStrObj.table.subCaption;
	     var header =jsonStrObj.header;
	     var dataset= jsonStrObj.dataset;
	     //setting
	     var style = ""
	     if(jsonStrObj.table.font!=null){
	    	 style = style + "font-family: "+this.defaultFont+";";
	      	 style = style + "font-family: "+jsonStrObj.table.font+" "+this.defaultFont+";"; 
	     }else{
	    	 style = style + "font-family: "+this.defaultFont+";";
		 }
	     if(jsonStrObj.table.fontSize!=null){
	    	 style = style + "font-size:"+jsonStrObj.table.fontSize+"px;";
	     }else{
	    	 style = style + "font-size:"+this.defaultFontSize+"px;";
	     }
	     // title
	     $(this.container).append( $("<div align=\"center\"></div>").html("<b>"+caption+"</b><br/>") );
	     $(this.container).append(  $("<div align=\"center\"></div>").html(subCaption) );
	     var str="<table class=\"table table-bordered\">";
	    //header
	     if(header.length>0){
	         str=str+"<thead bgcolor=\""+jsonStrObj.table.headerBgColor+"\">"+"<tr>";
		         for(var i=0;i<header.length;i++){
		             str=str+""+
		                 "<th class=\"th_class\"  style=\"text-align:center;"+style+"\" >"+header[i].cell+"</th>";
		         }
		         //check extra
		         if(jsonStrObj.extra!=null){
		        	 for(var i=0;i<jsonStrObj.extra.length;i++){
		        		 str=str+"<th></th>";
		        	 }
		         }//end check extra
		         str=str+"</tr></thead>";
	     }
	     //data
	     str=str+"<tbody bgcolor=\""+jsonStrObj.table.bodyBgColor+"\">";
	     for(var i=0;i<dataset.length;i++){
	         var datarow=dataset[i].data;
	         str=str+""+ " <tr class=\"dataset\"  style=\"cursor: pointer;\" \">";
	         for(var j=0;j<datarow.length;j++){
	             str=str+""+"<td class=\"wtpColumn\"  style=\"text-align:left;"+style+"\">";
	             if(jsonStrObj.header[j].type=="number"){
	            	 str=str+""+numberWithCommas(datarow[j].value);
	             }else{
	            	 str=str+""+datarow[j].value;
	             }
	             str=str+""+"</td>";
	         }//end td data
	         //compare
	         if(jsonStrObj.extra!=null){
	        	 for(var j=0;j<jsonStrObj.extra.length;j++){
	            	 str=str+"<td width=\"50\" style=\"text-align:center;"+style+"\">";
	        		 if(jsonStrObj.extra[j].type=='growth'){
	        			var diff = datarow[textToNumber(jsonStrObj.extra[j].cellB)-1].value-datarow[textToNumber(jsonStrObj.extra[j].cellA)-1].value;
	        			var growth = (diff/datarow[textToNumber(jsonStrObj.extra[j].cellA)-1].value)*100;
	        				if(growth>0){
			            	      str=str+"<img src=\""+this.contextPath+"/resources/js/sort_up_green.png\" style=\"width:20px;height: 20px;vertical-align:bottom\" />";
			            	 }else if(growth<0){
			            		   str=str+"<img src=\""+this.contextPath+"/resources/js/sort_down_red.png\" style=\"width:20px;height: 20px;\" />";
			            	 }
	        			str = str + " "+growth+" %";
	        		 }//end growth
	            	 str=str+"</td>";
	        	 }//end extra list
	         }//end extra
	         str=str+"</tr>";
	     }
	     str=str+"</tbody>";
	     str=str+"</table>";
	     $(this.container).html(str);
	     if(jsonStrObj.group!=null && jsonStrObj.group.length>0 ){
	    	 for(var ig = 0 ; ig<jsonStrObj.group.length ; ig++){
		    	 var posAr = jsonStrObj.group[ig].cell;
		    	 for(var igx = 0 ; igx < posAr.length;igx++){
		    		 ascSort(this.container,posAr[igx]);
		    	 	var prop = JSON.parse("{ \"bgColor\":\""+jsonStrObj.table.groupBgColor+"\"}");
			     	//wGroup(this.container,posAr[igx],[2,3],prop);
		    	 }
	    	 }
	     }
	     //footer
		 var tfooter =	$(this.container+">table>tbody");
	     if(jsonStrObj.footer.length>0){
	    	 var footer = jsonStrObj.footer;
	    	 var trNew = tfooter.children("tr:last-child").clone();
	    	 trNew.removeClass(); trNew.addClass("footer"); trNew.attr("style","");
	    	 trNew.attr("bgcolor",jsonStrObj.table.footerBgColor);
	    	 trNew.children("td").empty();//clear content
	    	for(var i = 0 ;i<jsonStrObj.footer.length;i++){
	    		if(footer[i].type == "sum"){
	    			trNew.children("td:nth-child(1)").html("รวม");
	    			var total = 0;
	    			tfooter.children("tr.dataset").children("td:nth-child("+footer[i].cell+")").each(function(){
	    				total=textToNumber(total)+textToNumber($(this).html());
	    			})
	    			trNew.children("td:nth-child("+footer[i].cell+")").html(numberWithCommas(total));
	    		}//if sum
	    	}//end loop
	    	tfooter.append(trNew);
	     }
	     //setting
	     var table = $(this.container).children("table");
	 }//check null
}
wtpTable.prototype.render = function(){
	this.createTable();
};
wtpTable.prototype.renderAfter = function(){
	// v1 ,it error don't use this//
	var newDiv = $("<div/>").attr("id","newDiv");
	newDiv.insertAfter($(this.container));
	this.container = "#newDiv";
	this.createTable();
};