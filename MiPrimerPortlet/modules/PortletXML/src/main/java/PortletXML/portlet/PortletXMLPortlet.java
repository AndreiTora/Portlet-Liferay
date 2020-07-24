package PortletXML.portlet;

import PortletXML.constants.PortletXMLPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.xml.transform.TransformerConfigurationException;

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
	
		public void newRule(ActionRequest actionRequest, ActionResponse actionResponse)
				throws IOException, PortletException, TransformerConfigurationException {
			String from = ParamUtil.getString(actionRequest, "from");
			String to = ParamUtil.getString(actionRequest, "to");
			String type = ParamUtil.getString(actionRequest, "type");
			
			System.out.println("FROM: " + from + " TO: " + to);
			
			try {
				FileInputStream is = new FileInputStream("C:/Users/acanas/Documents/urlrewrite.xml");
				Document doc = SAXReaderUtil.read(is);	
				
				Element rootElement = doc.getRootElement();

			    Element ruleElement = SAXReaderUtil.createElement("rule");
			      
			    Element fromElement = ruleElement.addElement("from");
			    fromElement.addText(from);
			      
			    Element toElement = ruleElement.addElement("to");
			    toElement.addText(to);
			    toElement.addAttribute("type", type);

			    rootElement.add(ruleElement);
			     
			     try {  
			            Writer w = new FileWriter("C:/Users/acanas/Documents/urlrewrite.xml");  
			            String content = rootElement.asXML();  
			            w.write(content);  
			            w.close();  
			        } catch (IOException e) {  
			            e.printStackTrace();  
			        }  
			     
			} catch (DocumentException e) {
				e.printStackTrace();
			}

		}
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
		Vector<Hashtable<String, String>> rules = new Vector<Hashtable<String,String>>();
		
		try {
			FileInputStream is = new FileInputStream("C:/Users/acanas/Documents/urlrewrite.xml");
			Document doc = SAXReaderUtil.read(is);	
			
			Element rootElement = doc.getRootElement();
			
			List<Element> preferences = rootElement.elements("rule");
			 
			 for(Element prefrenceElement  : preferences){   
				 Hashtable<String, String> r1 = new Hashtable<String, String>();
				 
				 r1.put("from", prefrenceElement.element("from").getStringValue());
				 r1.put("to", prefrenceElement.element("to").getStringValue());
				 r1.put("type", prefrenceElement.element("to").attributeValue("type"));

				 rules.add(r1);
			 }
			 
			 renderRequest.setAttribute("rules", rules);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.render(renderRequest, renderResponse);
		
	}	
	
}