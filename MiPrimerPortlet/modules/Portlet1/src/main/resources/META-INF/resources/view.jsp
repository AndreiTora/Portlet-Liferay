<%@ include file="/init.jsp" %>

<portlet:renderURL var='hola'>
	<portlet:param name='jspPage' value='/HolaMundo.jsp'/>
</portlet:renderURL>

<portlet:defineObjects />

<portlet:resourceURL var="exportCSV" />
     
<a href='${exportCSV}'>Exportar a CSV :D</a>



<p>
	<ul>
		<li><a href="${hola}">Hola Mundo</a></li>
	</ul>
</p>