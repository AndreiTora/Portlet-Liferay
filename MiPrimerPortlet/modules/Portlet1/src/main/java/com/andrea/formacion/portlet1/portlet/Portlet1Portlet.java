package com.andrea.formacion.portlet1.portlet;

import com.andrea.formacion.portlet1.constants.Portlet1PortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;
import java.util.Hashtable;
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
			
		
		super.render(renderRequest, renderResponse);
	}
}