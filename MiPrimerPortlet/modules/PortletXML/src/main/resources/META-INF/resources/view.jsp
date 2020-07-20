<%@ include file="/init.jsp" %>

<p>
	<%@ include file="/init.jsp" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<liferay-theme:defineObjects/>
<portlet:defineObjects/>
<p>Lectura <b>Archivo XML</b> portlet.</p>
 
<portlet:actionURL name="readFile" var="readFile">
</portlet:actionURL>
 
<form action="${readFile}" method="post" enctype="multipart/form-data">
    
    <p><aui:button type="submit" value="upload" name="upload" cssClass="topSpace"/></p>
</form>

<p><b><a href='<portlet:renderURL/>'>Volver</a></b></p>

</p>