<%@ include file="/init.jsp" %>

<portlet:renderURL var='hola'>
	<portlet:param name='jspPage' value='/HolaMundo.jsp'/>
</portlet:renderURL>

<portlet:renderURL var='lista'>
	<portlet:param name='jspPage' value='/listaClientes.jsp'/>
</portlet:renderURL>
<p>
	<ul>
		<li><a href="${hola}">Hola Mundo</a></li>
		<li><a href="${lista}">Lista Clientes</a></li>
	</ul>
</p>