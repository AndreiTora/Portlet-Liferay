package PortletXML.portlet;

import PortletXML.constants.PortletXMLPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.xml.soap.Node;

import org.osgi.service.component.annotations.Component;
import org.xml.sax.InputSource;

/**
 * @author acanas
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=categoriaAndrea",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=PortletXML",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + PortletXMLPortletKeys.PORTLETXML,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class PortletXMLPortlet extends MVCPortlet {
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
		super.render(renderRequest, renderResponse);
		
	}
	
	@ProcessAction(name = "readFile")
	public void uploadFileAction(ActionRequest actionRequest, ActionResponse actionResponse) 
			throws IOException, PortletException {
		
		try {
			FileInputStream is = new FileInputStream("C:/Users/acanas/Documents/urlrewrite.xml");
			Document doc = SAXReaderUtil.read(is);	
			
			Element rootElement = doc.getRootElement();
			
			List<Element> preferences = rootElement.elements("rule");
			 
			 for(Element prefrenceElement  : preferences){                   
				 System.out.println("FROM:" + prefrenceElement.element("from").getStringValue());
				 System.out.println("TO:" + prefrenceElement.element("to").getStringValue()+"\n");
				 //System.out.println("TO:" + prefrenceElement.element("to").getStringValue());
			 }
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }	
	
}