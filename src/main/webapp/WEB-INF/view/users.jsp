<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link rel="shortcut icon" type="image/ico" href="<c:url value="/resources/images/favicon.ico" />" />
		
		<title>People List</title>
		<style type="text/css" title="currentStyle">
			@import "<c:url value="/resources/css/demo_page.css" />";
			@import "<c:url value="/resources/css/demo_table.css" />";
		</style>
		<script type="text/javascript" language="javascript" src="<c:url value="/resources/js/jquery.js" />"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/resources/js/jquery.jeditable.js" />"></script>
		<script type="text/javascript" language="javascript" src="<c:url value="/resources/js/jquery.dataTables.js" />"></script>
		<script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
                /* Init the table */
                var oTable = $('#example').dataTable( );

                /* Add a click handler to the rows - this could be used as a callback */
				$("#example tbody tr").click( function( e ) {
					if ( $(this).hasClass('row_selected') ) {
						$(this).removeClass('row_selected');
					}
					else {
						oTable.$('tr.row_selected').removeClass('row_selected');
						$(this).addClass('row_selected');
					}
				});
                /* Apply the jEditable handlers to the table */
                oTable.$('.name_editable').editable( '${pageContext.request.contextPath}/api/person/update', {
                    "event" : "dblclick",
                    "callback": function( sValue, y ) {
                        var aPos = oTable.fnGetPosition( this );
                        oTable.fnUpdate( sValue, aPos[0], aPos[1] );
                    },
                    "submitdata": function ( value, settings ) {
                        return {
                            "row_id": this.parentNode.getAttribute('id'),
                            "column": oTable.fnGetPosition( this )[2]
                        };
                    },
                    "height": "14px",
                    "width": "100%"
                });
                oTable.$('.age_editable').editable( '${pageContext.request.contextPath}/api/person/update', {
                    "event" : "dblclick",
                    type : "select",
                    data : "{'30':'30', '40':'40'}",
                    submit : "保存",
                    "callback": function( sValue, y ) {
                        var aPos = oTable.fnGetPosition( this );
                        oTable.fnUpdate( sValue, aPos[0], aPos[1] );
                    },
                    "submitdata": function ( value, settings ) {
                        return {
                            "row_id": this.parentNode.getAttribute('id'),
                            "column": oTable.fnGetPosition( this )[2]
                        };
                    },
                    "height": "14px",
                    "width": "100%"
                });


                /* Add a click handler for the delete row */
				$('#delete').click( function() {
					var anSelected = fnGetSelected( oTable );
					if ( anSelected.length !== 0 ) {
                        var rowIndex = oTable.fnGetPosition(anSelected[0]);
                        var aData = oTable.fnGetData(rowIndex);
                        console.info("Id: " + aData[0] + " Name: " + aData[1]);
                        $.post('${pageContext.request.contextPath}/api/person/remove/' + aData[0], function(response) {
                            $('#removeIdResponse').text(response);
                        });
						oTable.fnDeleteRow( anSelected[0] );
					}
				} );

			});
			
			
			/* Get the rows which are currently selected */
			function fnGetSelected( oTableLocal )
			{
				return oTableLocal.$('tr.row_selected');
			}
		</script>
	</head>
	<body id="dt_example">
		<div id="container">
			<div class="full_width big">
				People List
			</div>
			
			<h1>Selectable Row</h1>
			<p><a href="javascript:void(0)" id="delete">Delete selected row</a></p>
            <p><div id="removeIdResponse"> </div></p>
			<div id="demo">
<table cellpadding="0" cellspacing="0" border="0" class="display" id="example">
	<thead>
		<tr>
            <th>#</th>
			<th>Id</th>
			<th>Name</th>
			<th>Age</th>
		</tr>
	</thead>
	<tbody>
        <c:forEach items="${people}" var="person" varStatus="status">
		<tr class="gradeA" id="${person.id}">
            <td><c:out value="${status.index + 1}" /> </td>
			<td>${person.id}</td>
			<td class="name_editable">${person.name}</td>
			<td class="age_editable">${person.age}</td>
		</tr>
        </c:forEach>
	</tbody>
	<tfoot>
		<tr>
            <th>#</th>
			<th>Id</th>
			<th>Name</th>
			<th>Age</th>
		</tr>
	</tfoot>
</table>
            </div>
			<div class="spacer"></div>

		</div>
	</body>
</html>
