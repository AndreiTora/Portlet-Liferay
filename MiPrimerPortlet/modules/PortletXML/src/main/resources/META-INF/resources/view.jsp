<%@ include file="/init.jsp"%>

<p>
	<%@ include file="/init.jsp"%>
	<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
	<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
	<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
	<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
	<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
	<%@ taglib uri="http://liferay.com/tld/portlet"
		prefix="liferay-portlet"%>
	<liferay-theme:defineObjects />
	<portlet:defineObjects />
	
<portlet:renderURL var='newRuleForm'>
	<portlet:param name='jspPage' value='/newRuleForm.jsp'/>
</portlet:renderURL>     
<p>
	Lectura <b>Archivo XML</b> portlet.
</p>

<table>

<p><a href='${newRuleForm}'><aui:button value="Añadir Nueva Regla"/></a></p>
	<tr>
		<th>From</td>
		<th>To</td>
		<th>Type</td>
	</tr>

	<c:forEach var='r' items='${rules}'>
		<tr>
			<td>${r.from}</td>
			<td>${r.to}</td>
			<td>${r.type}</td>
		</tr>
	</c:forEach>
</table>



