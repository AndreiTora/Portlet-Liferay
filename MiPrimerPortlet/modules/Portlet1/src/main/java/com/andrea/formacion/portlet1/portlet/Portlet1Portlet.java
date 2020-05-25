package com.andrea.formacion.portlet1.portlet;

import com.andrea.formacion.portlet1.constants.Portlet1PortletKeys;
import com.andrea.formacion.portlet1.utils.CSVUtils;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.OutputStreamWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ProgressTracker;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.CSVUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

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

			Layout layoutKey = (Layout) renderRequest.getAttribute(WebKeys.LAYOUT);
			
			
			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(layoutKey.getLayoutId());
				
				File file = new File("C:/Users/acanas/git/primerportletgit/MiPrimerPortlet/modules/Portlet1/src/main/resources/META-INF/resources/resultadoPrueba.csv");
				  
				//Create the file
				if (file.createNewFile()) {
				    System.out.println("File is created!");
				} else {
				    System.out.println("File already exists.");
				}
				
				String csvFile = "C:/Users/acanas/git/primerportletgit/MiPrimerPortlet/modules/Portlet1/src/main/resources/META-INF/resources/resultadoPrueba.csv";
				FileWriter writer = new FileWriter(csvFile);
				 
				 List<Layout> layouts = Arrays.asList(layout);

			        //for header
			        CSVUtils.writeLine(writer, Arrays.asList("Create Date", "URL", "Type"));

			        for (Layout d : layouts) {

			            List<String> list = new ArrayList<>();

			            list.add(d.getCreateDate().toString());
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
	
	 @Override
	 public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException, PortletException {
		
		 resourceResponse.setContentType("application/csv");
		 resourceResponse.addProperty( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listado.csv" );
		         
		    OutputStreamWriter osw=new OutputStreamWriter( resourceResponse.getPortletOutputStream() );
		    CSVPrinter printer= new CSVPrinter(
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
		
		Layout layoutKey = (Layout) themeDisplay.getLayout();
		Layout layout = LayoutLocalServiceUtil.getLayout(layoutKey.getLayoutId());
		List<Layout> layouts = Arrays.asList(layout);
		
		System.out.println(layouts);
		
		return layouts;
		
	}

	
}