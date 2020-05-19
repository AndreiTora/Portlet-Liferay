package com.andrea.formacion.portlet1.portlet;

import com.andrea.formacion.portlet1.constants.Portlet1PortletKeys;
import com.andrea.formacion.portlet1.utils.CSVUtils;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

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
	
	LayoutService layoutService;

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
			Vector<Hashtable<String, String>> lista = new Vector<Hashtable<String, String>>();
			
			Hashtable<String, String> cliente1 = new Hashtable<String, String>();
			cliente1.put("dni", "48113221G");
			cliente1.put("nombre", "Andrea");
			cliente1.put("importe", "50 €");
			
			lista.add(cliente1);
			
			Hashtable<String, String> cliente2 = new Hashtable<String, String>();
			cliente2.put("dni", "75986421S");
			cliente2.put("nombre", "Luis");
			cliente2.put("importe", "15 €");
			
			lista.add(cliente2);
			
			renderRequest.setAttribute("listaClientes", lista);

			
			Layout layoutKey = (Layout) renderRequest.getAttribute(WebKeys.LAYOUT);
			
			
			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(layoutKey.getLayoutId());
				
				
				System.out.println(layout.getChildren());
				
				System.out.println("  ---  ");
				
				System.out.println(layout.getAncestors());
				File file = new File("C:/Users/acanas/git/primerportletgit/MiPrimerPortlet/modules/Portlet1/src/main/resources/META-INF/resources/portlet.csv");
				  
				//Create the file
				if (file.createNewFile())
				{
				    System.out.println("File is created!");
				} else {
				    System.out.println("File already exists.");
				}
				
				String csvFile = "C:/Users/acanas/git/primerportletgit/MiPrimerPortlet/modules/Portlet1/src/main/resources/META-INF/resources/portlet.csv";
				FileWriter writer = new FileWriter(csvFile);
				 
				 List<Layout> layouts = Arrays.asList(layout);

			        //for header
			        CSVUtils.writeLine(writer, Arrays.asList("Title", "URL", "Type"));

			        for (Layout d : layouts) {

			            List<String> list = new ArrayList<>();
			            
			            list.add(d.getTitle());
			            list.add(d.getFriendlyURL());
			            list.add(d.getType());
			            
			            CSVUtils.writeLine(writer, list);

			        }

		        writer.flush();
		        writer.close();
			} catch (PortalException e) {
				e.printStackTrace();
			}
			
		super.render(renderRequest, renderResponse);
	}
}