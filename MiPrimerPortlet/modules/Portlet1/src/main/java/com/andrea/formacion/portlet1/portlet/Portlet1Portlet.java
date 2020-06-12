package com.andrea.formacion.portlet1.portlet;

import com.andrea.formacion.portlet1.constants.Portlet1PortletKeys;
import com.liferay.portal.kernel.io.OutputStreamWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.upload.UploadPortletRequest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
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
	
	private final String ENCODING = "ISO-8859-1";
	private final String FORMAT_DATE = "yyyy-MM-dd";
	private final String[] header = {"Id", "Name", "Create Date", "URL", "Type", "Parent Id"};

	SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATE);
	
	private static Log _log = LogFactoryUtil.getLog(Portlet1Portlet.class);
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
		super.render(renderRequest, renderResponse);
		
	}
	
	 @Override
	 public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException, PortletException {
		
		 resourceResponse.setContentType(ContentTypes.TEXT_CSV_UTF8);
		 resourceResponse.addProperty(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listado.csv");
		         
		    OutputStreamWriter osw = new OutputStreamWriter(resourceResponse.getPortletOutputStream(), ENCODING);
		    
		    CSVPrinter printer = new CSVPrinter(
		        osw,
		        CSVFormat.DEFAULT.withDelimiter(';').withHeader(header)
		        );
		     
		    List<Layout> layouts;
		    
			try {
				
				layouts = getLayouts(resourceRequest, resourceResponse); 

				for (Layout l : layouts) {
					printer.printRecord(
							l.getLayoutId(),
							l.getNameCurrentValue(),
							formatter.format(
									l.getCreateDate()
									),
							l.getFriendlyURL(),
							l.getType(),
							l.getParentLayoutId()
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
	
	/* IMPORTACIÓN */
	
	@ProcessAction(name = "uploadFile")
	public void uploadFileAction(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException {
		
		_log.info("Entrando en uploadFile 2");

		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		
		File file = uploadRequest.getFile("uploadedFile");
		
		BufferedReader reader = Files.newBufferedReader(file.toPath(),  Charset.forName(ENCODING));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';').withHeader(header));
        
        for (CSVRecord csvRecord : csvParser) {

            String id = csvRecord.get("Id");
            String name = csvRecord.get("Name");
            String url = csvRecord.get("URL");
            String type = csvRecord.get("Type");

            System.out.println("Id : " + id);
            System.out.println("Name : " + name);
            System.out.println("URL : " + url);
            System.out.println("Type : " + type);
        }
    }
	
}