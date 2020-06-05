<%@ include file="/init.jsp" %>

<portlet:renderURL var='import'>
	<portlet:param name='jspPage' value='/ImportForm.jsp'/>
</portlet:renderURL>

<portlet:defineObjects />

<portlet:resourceURL var="exportCSV" />
     

<p>
	<ul>
		<li><a href='${exportCSV}'>Exportar a CSV :D</a></li>
		<li><a href="${import}">Importación Archivo</a></li>
	</ul>
</p>