<%@ include file="/init.jsp" %>

<portlet:renderURL var='alta'>
	<portlet:param name='jspPage' value='/altaClientes.jsp'/>
</portlet:renderURL>

<portlet:renderURL var='lista'>
	<portlet:param name='jspPage' value='/listaClientes.jsp'/>
</portlet:renderURL>
<p>
	<ul>
		<li><a href="${alta}">Alta Cliente</a></li>
		<li><a href="${lista}">Lista Clientes</a></li>
	</ul>
</p>