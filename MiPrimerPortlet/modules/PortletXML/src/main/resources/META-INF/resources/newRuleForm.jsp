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

	<portlet:actionURL name="newRule" var="newRule">
	</portlet:actionURL>
<p>
	Nueva <b>R U L E</b> form.
</p>


<aui:form name="fm" action="${newRule}" method="post"
	enctype="multipart/form-data">

	<aui:row>
		<aui:col width="50">
			<aui:input label="From" name="from" type="text" />
		</aui:col>
		<aui:col width="50">
			<aui:input label="To" name="to" type="text" />
		</aui:col>
		<aui:col width="50">
			<aui:select name="type">
				<aui:option value="forward">forward</aui:option>
				<aui:option value="passthrough">passthrough</aui:option>
				<aui:option value="redirect">redirect</aui:option>
				<aui:option value="permanent-redirect">permanent-redirect</aui:option>
				<aui:option value="temporary-redirect">temporary-redirect</aui:option>
				<aui:option value="pre-include">pre-include</aui:option>
				<aui:option value="post-include">post-include</aui:option>
				<aui:option value="proxy">proxy</aui:option>
			</aui:select>
		</aui:col>
	</aui:row>
	<aui:button-row>
		<aui:button name="newRule" type="submit" value="Submit" />
	</aui:button-row>
</aui:form>