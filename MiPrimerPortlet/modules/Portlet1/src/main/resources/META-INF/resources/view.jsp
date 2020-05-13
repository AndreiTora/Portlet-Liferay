<%@ include file="/init.jsp" %>

<portlet:renderURL var='hola'>
	<portlet:param name='hola' value='/HolaMundo.jsp'/>
</portlet:renderURL>

<portlet:renderURL var='lista'>
	<portlet:param name='jspLista' value='/listaClientes.jsp'/>
</portlet:renderURL>
<p>
	<ul>
		<li><a href="${hola}">Mi primer hola mundo :D</a></li>
		<li><a href="${lista}">Lista Clientes</a></li>
	</ul>
</p>