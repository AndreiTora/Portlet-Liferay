package com.andrea.formacion.portlet1.portlet;

import com.andrea.formacion.portlet1.constants.Portlet1PortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.OutputStreamWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
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
	private final String[] header = {"Id", "UuId", "Name", "Create Date", "URL", "Type", "Hidden", "Private", "Parent Id", "Title", "Description"};

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
							l.getUuid(),
							l.getNameCurrentValue(),
							formatter.format(
									l.getCreateDate()
									),
							l.getFriendlyURL(),
							l.getType(),
							l.getHidden(),
							l.getPrivateLayout(),
							l.getParentLayoutId(),
							l.getTitle(),
							l.getDescription()
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

		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = themeDisplay.getScopeGroupId();
		List<Layout> layouts =LayoutLocalServiceUtil.getLayouts(groupId, false);
		
		return layouts;
		
	}
	
	/* IMPORTACIÓN */
	
	@ProcessAction(name = "uploadFile")
	public void uploadFileAction(ActionRequest actionRequest, ActionResponse actionResponse) 
			throws IOException, PortletException {
		
		_log.info("Entrando en uploadFile");
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay =  (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long groupId = themeDisplay.getLayout().getGroupId();
		
		File file = uploadRequest.getFile("uploadedFile");
		
		BufferedReader reader = Files.newBufferedReader(file.toPath(),  Charset.forName(ENCODING));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';').withHeader(header));
        
        boolean primero = true;
        
        /* DELETE LAYOUTS */

        String deleteLayouts = ParamUtil.getString(actionRequest, "deleteLayouts");
        
        if(deleteLayouts.equals( "deleteLayouts")) {
        	_log.info("Borrar Layouts seleccionado \n");
        	
    		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(groupId, false);
        	
    		for (Layout layout : layouts) {
    			if (layout.getPlid() == themeDisplay.getLayout().getPlid()) {
    				_log.info("Layout actual \n");
    			} else {
    				try {
    					LayoutLocalServiceUtil.deleteLayout(layout.getPlid());
    				} catch (PortalException e) {
    					e.printStackTrace();
		
    				}
    			}
    		}
        	
        }
        
        /* IMPORT CSV*/
        
        for (CSVRecord csvRecord : csvParser) {
 	
        	if (primero) {
        		_log.info("Primera linea del CSV saltada");
        		primero = false;
        	} else {
        			try {
						if(LayoutLocalServiceUtil.hasLayout(csvRecord.get(header[1]), groupId, false)) {
							_log.info("Layout ya creado \n");
						} else {        	
							LayoutLocalServiceUtil.addLayout(
									themeDisplay.getUserId(), 
									groupId,
									Boolean.parseBoolean(
					        				csvRecord.get(header[7]
					        						)), 
									Long.parseLong(
					        				csvRecord.get(header[8]
					        						)), 
									csvRecord.get(header[2]), 
									csvRecord.get(header[9]), 
									csvRecord.get(header[10]), 
									csvRecord.get(header[5]), 
									Boolean.parseBoolean(
					        				csvRecord.get(header[6]
					        						)), 
									csvRecord.get(header[4]), 
									ServiceContextThreadLocal.getServiceContext());
						}
					} catch (PortalException e) {
						e.printStackTrace();
					}
        		}
        	}
    }	
	
	/* IMPORTACIÓN HIJOS */
	
	@ProcessAction(name = "uploadHijosFile")
	public void uploadHijosFileAction(ActionRequest actionRequest, ActionResponse actionResponse) 
			throws IOException, PortletException {
		
		_log.info("Entrando en upload hijos File");
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay =  (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long groupId = themeDisplay.getLayout().getGroupId();
		
		File file = uploadRequest.getFile("uploadedHijosFile");
		
		BufferedReader reader = Files.newBufferedReader(file.toPath(),  Charset.forName(ENCODING));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';').withHeader(header));
        
        boolean primero = true;
        
        String hijosLayout = ParamUtil.getString(actionRequest, "hijosLayout");
    	List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(groupId, false);
    	
		for (Layout layout : layouts) {
			System.out.println(layout.getFriendlyURL());
			
			if (layout.getFriendlyURL().equals(hijosLayout)) {
				_log.info("Misma URL");
				
				try {
					Layout layoutByUrl = LayoutLocalServiceUtil.getFriendlyURLLayout(groupId, false, layout.getFriendlyURL());
					Long id = layoutByUrl.getLayoutId();
					
					System.out.println(layoutByUrl + "" + id);
					
					for (CSVRecord csvRecord : csvParser) {
						
					        	if (primero) {
					        		_log.info("Primera linea del CSV saltada");
					        		primero = false;
					        	} else {
				        			try {
									if(LayoutLocalServiceUtil.hasLayout(csvRecord.get(header[1]), groupId, false)) {
										_log.info("Layout ya creado \n");
									} else {        	
										LayoutLocalServiceUtil.addLayout(
												themeDisplay.getUserId(), 
												groupId,
												Boolean.parseBoolean(
								        				csvRecord.get(header[7]
								        						)), 
								        		id, 
												csvRecord.get(header[2]), 
												csvRecord.get(header[9]), 
												csvRecord.get(header[10]), 
												csvRecord.get(header[5]), 
												Boolean.parseBoolean(
							        				csvRecord.get(header[6]
								        						)), 
												csvRecord.get(header[4]), 
												ServiceContextThreadLocal.getServiceContext());
									}
								} catch (PortalException e) {
									e.printStackTrace();
								}
					        }
					}
				} catch (PortalException e) {
					e.printStackTrace();
				}
			}
		}
    }	
}