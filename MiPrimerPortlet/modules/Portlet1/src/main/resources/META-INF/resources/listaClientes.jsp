<%@ include file="/init.jsp" %>


<table>

	<tr>
		<th>DNI:</th>
		<th>Nombre:</th>
		<th>Importe:</th>
	</tr>
	
	<c:forEach var="cliente" items="${listaClientes}">
		<tr>
		<td>${cliente.dni}</td>
		<td>${cliente.nombre}</td>
		<td>${cliente.importe}</td>
	</tr>
	</c:forEach>

</table>