<%@ include file="/init.jsp" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<liferay-theme:defineObjects/>
<portlet:defineObjects/>
<p>Importación hijos del Layout mediante <b>Archivo CSV</b> portlet.</p>
 
<portlet:actionURL name="uploadHijosFileAction" var="uploadHijosFile">
</portlet:actionURL>
 
<form action="${uploadHijosFile}" method="post" enctype="multipart/form-data">
    <table>
            
            <tr>
                <td><input type="file" name="<portlet:namespace/>uploadedHijosFile" multiple="multiple"></td>
                <aui:input type="text" id="hijosLayout" name="hijosLayout"></aui:input>
            </tr>
        </table>
    
    
    <p><aui:button type="submit" value="upload" name="upload" cssClass="topSpace"/></p>
</form>

<p><b><a href='<portlet:renderURL/>'>Volver</a></b></p>
