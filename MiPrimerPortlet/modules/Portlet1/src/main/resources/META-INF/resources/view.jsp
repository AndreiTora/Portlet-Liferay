<%@ include file="/init.jsp" %>

<portlet:renderURL var='import'>
	<portlet:param name='jspPage' value='/ImportForm.jsp'/>
</portlet:renderURL>

<portlet:defineObjects />

<portlet:resourceURL var="exportCSV" />
<portlet:renderURL var='importHijos'>
	<portlet:param name='jspPage' value='/ImportHijosForm.jsp'/>
</portlet:renderURL>     

<p>
	<ul>
		<li><a href='${exportCSV}'>Exportar a CSV :D</a></li>
		<li><a href="${import}">Importación Layouts sitio</a></li>
		<li><a href="${importHijos}">Importación Layouts Hijos</a></li>
	</ul>
</p>