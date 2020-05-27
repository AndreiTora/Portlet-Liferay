package com.andrea.formacion.portlet1.portlet;

import com.andrea.formacion.portlet1.constants.Portlet1PortletKeys;
import com.liferay.portal.kernel.io.OutputStreamWriter;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.osgi.service.component.annotations.Component;


/**
 * @author acanas
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=categoriaAndrea",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Portlet1",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + Portlet1PortletKeys.PORTLET1,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class Portlet1Portlet extends MVCPortlet {

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
		
		
			
		super.render(renderRequest, renderResponse);
	}
	
	 @Override
	 public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException, PortletException {
		
		 resourceResponse.setContentType("application/csv");
		 resourceResponse.addProperty( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listado.csv" );
		         
		    OutputStreamWriter osw = new OutputStreamWriter( resourceResponse.getPortletOutputStream() );
		    CSVPrinter printer = new CSVPrinter(
		        osw,
		        CSVFormat.DEFAULT.withHeader("Create Date", "URL", "Type")
		        );
		     
		    List<Layout> layouts;
			try {
				layouts = getLayouts(resourceRequest, resourceResponse); 
				
				for (Layout l : layouts) {
		        printer.printRecord(
		            l.getCreateDate().toString(),
		            l.getFriendlyURL(),
		            l.getType()
		            );
		        }
			} catch (Exception e) {

				e.printStackTrace();
			}

		    printer.flush();
		    printer.close();
			
	     }
	
	protected List<Layout> getLayouts(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = themeDisplay.getScopeGroupId();
		List<Layout> layouts =LayoutLocalServiceUtil.getLayouts(groupId, false);
		        
		return layouts;
		
	}

	
}