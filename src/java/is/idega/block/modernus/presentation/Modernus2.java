/*
 * $Id$
 *
 * Copyright (C) 2000-2003 Idega Software. All Rights Reserved.
 *
 * This software is the proprietary information of Idega Software.
 * Use is subject to license terms.
 */
package is.idega.block.modernus.presentation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.faces.context.FacesContext;

import com.idega.presentation.IWContext;
import com.idega.presentation.PresentationObjectTransitional;
import com.idega.presentation.text.Text;

/**
 * @author palli
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Modernus2 extends PresentationObjectTransitional {
	
	private String iPageName = null;
	private String iPortion = null;
	private String iServiceID = null;
	private String iServer = null;
	
	public String getServer() {
		return iServer;
	}
	
	public String getPortion() {
		return iPortion;
	}
	
	public String getPageName() {
		return iPageName;
	}
	
	public String getServiceID() {
		return iServiceID;
	}
	
	/* (non-Javadoc)
	 * @see com.idega.presentation.PresentationObjectTransitional#initializeComponent(javax.faces.context.FacesContext)
	 */
	protected void initializeComponent(FacesContext context) {
		IWContext iwc = IWContext.getIWContext(context);

		if (getPageName() == null) {
			setPageName(getParentPage().getLocalizedTitle(iwc));
		}

		if (iwc.isInEditMode()) {
			getChildren().add(new Text("Modernus"));
		}
		else if (getPortion() == null) {
			getChildren().add(new Text("Portion must be set"));
		}
		else if (getServiceID() == null) {
			getChildren().add(new Text("Service ID must be set"));
		}
		else if (getServer() == null) {
			getChildren().add(new Text("Server must be set"));
		}
		else {
			try {
				setPageName(URLEncoder.encode(getPageName(), "UTF-8"));
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			StringBuffer buffer = new StringBuffer();
			buffer.append("<!-- Virk vefmaeling byrjar -->").append("\n");
			buffer.append("<script language=\"javascript\" type=\"text/javascript\"><!--").append("\n");
			buffer.append("\t").append("var p_run_js=0;").append("\n");
			buffer.append("//--></script>\n");
			buffer.append("<script language=\"javascript1.1\" type=\"text/javascript\"><!--").append("\n");
			buffer.append("\t").append("var p_run_js=1;").append("\n");
			buffer.append("//--></script>\n");
			buffer.append("<script language=\"javascript\" type=\"text/javascript\"><!--").append("\n");
			buffer.append("//<![CDATA").append("\n");
			buffer.append("\t").append("var service_id = \"").append(getServiceID()).append("\";").append("\n");
			buffer.append("\t").append("var portion = \"").append(getPortion()).append("\";").append("\n");
			buffer.append("\t").append("var page = \"").append(getPageName()).append("\";").append("\n");
			buffer.append("\t").append("if(!p_run_js) {").append("\n");
			buffer.append("\t\t").append("document.write('<img width=\"1\" height=\"1\" src=\"http://");
			buffer.append(getServer()).append(".teljari.is/potency/potency.php?o='+service_id+';i='+portion+';p='+page+';j=1.0\" alt=\"\" border=\"0\" />');").append("\n");
			buffer.append("\t").append("}").append("\n");
			buffer.append("//]]>");
			buffer.append("//--></script>\n");
			buffer.append("<script language=\"javascript1.1\" type=\"text/javascript\" src=\"http://").append(getServer()).append(".teljari.is/potency/js/potency.js\">").append("\n");
			buffer.append("</script>").append("\n");
			buffer.append("<noscript>").append("\n");
			buffer.append("<img width=\"1\" height=\"1\" src=\"http://").append(getServer()).append(".teljari.is/potency/potency.php?o=");
			buffer.append(getServiceID()).append(";i=").append(getPortion()).append(";p=").append(getPageName()).append("\" alt=\"\" border=\"0\" />");
			buffer.append("</noscript>").append("\n");
			buffer.append("<!-- Virk vefmaeling endar  -->");
			
			getChildren().add(new Text(buffer.toString()));
		}
	}	
	
	/* (non-Javadoc)
	 * @see com.idega.presentation.PresentationObjectTransitional#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws IOException {
		super.encodeBegin(context);
	}

	/* (non-Javadoc)
	 * @see com.idega.presentation.PresentationObjectTransitional#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException {
		super.encodeChildren(context);
	}

	/* (non-Javadoc)
	 * @see com.idega.presentation.PresentationObjectTransitional#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext arg0) throws IOException {
		super.encodeEnd(arg0);
	}
	
	/**
	 * @see javax.faces.component.UIComponentBase#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[5];
		values[0] = super.saveState(ctx);
		values[1] = getPageName();
		values[2] = getServer();
		values[3] = getServiceID();
		values[4] = getPortion();
		return values;
	}

	/**
	 * @see javax.faces.component.UIComponentBase#restoreState(javax.faces.context.FacesContext,
	 *      java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(ctx, values[0]);
		setPageName((String) values[1]);
		setServer((String) values[2]);
		setServiceID((String) values[3]);
		setPortion((String) values[4]);
	}
	
	public void setServer(String server) {
		iServer = server;
	}
	
	public void setPageName(String name) {
		iPageName = name;	
	}
	
	public void setPortion(String portion) {
		iPortion = portion;
	}
	
	public void setServiceID(String serviceID) {
		iServiceID = serviceID;
	}
}