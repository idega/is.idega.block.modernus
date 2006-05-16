package is.idega.block.modernus.svarbox.presentation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Iterator;
import javax.faces.context.FacesContext;

import com.idega.core.accesscontrol.business.NotLoggedOnException;
import com.idega.core.contact.data.Email;
import com.idega.presentation.IWContext;
import com.idega.presentation.PresentationObjectTransitional;
import com.idega.presentation.text.Text;
import com.idega.user.data.User;
import com.idega.util.IWTimestamp;


/**
 * <p>
 * TODO sigtryggur Describe Type ModernusAnswerBox
 * </p>
 *  Last modified: $Date: 2006/05/16 15:00:34 $ by $Author: laddi $
 * 
 * @author <a href="mailto:sigtryggur@idega.com">sigtryggur</a>
 * @version $Revision: 1.10 $
 */
public class ModernusSvarbox extends PresentationObjectTransitional {
	
	private String _serviceID = null;
	private String _name = null;
	private String _email = null;
	private String _timestamp = null;
	private String _password = null;
	private String _hash = null;
	private String _linkImageURL = null;
	private String _linkText = null;
	private boolean useHttps = false;
	private String _imageWidth = null;
	private String protocol = "http";

	public String getServiceID() {
		return this._serviceID;
	}

	public String getName() {
		return this._name;
	}

	public String getEmail() {
		return this._email;
	}

	public String getTimestamp() {
		return this._timestamp;
	}

	public String getPassword() {
		return this._password;
	}

	public String getHash() {
		return this._hash;
	}
	public String getLinkImageURL() {
		return this._linkImageURL;
	}

	public String getLinkText() {
		return this._linkText;
	}

	public boolean isUseHttps() {
		return this.useHttps;
	}
	
	public String getImageWidth() {
		return this._imageWidth;
	}

	public String getProtocol() {
		return this.protocol;
	}
	/* (non-Javadoc)
	 * @see com.idega.presentation.PresentationObjectTransitional#initializeComponent(javax.faces.context.FacesContext)
	 */
	protected void initializeComponent(FacesContext context) {
		IWContext iwc = IWContext.getIWContext(context);
		
		User user = null;
		try {
			user = iwc.getCurrentUser();
			setName(user.getName());

			Collection userEmails = user.getEmails();
			Iterator emailIt = userEmails.iterator();
			if (emailIt.hasNext()) {
				Email email = (Email)emailIt.next();
				setEmail(email.getEmailAddress());
			}
		}
		catch (NotLoggedOnException n) {
			setName("");
		}
			
		setTimestamp(String.valueOf(IWTimestamp.getTimestampRightNow().getTime()/1000));
		
		if (iwc.isInEditMode()) {
			getChildren().add(new Text("ModernusAnswerBox"));
		}
		else if (getServiceID() == null) {
			getChildren().add(new Text("Service ID must be set"));
		}
		else if (getPassword() == null) {
			getChildren().add(new Text("Password must be set"));
		}
		else {
			try {
				setName(URLEncoder.encode(getName(), "UTF-8"));
				if (getEmail() != null) {
					setEmail(URLEncoder.encode(getEmail(), "UTF-8"));
				}
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			try {
				setHash(md5(getName()+getEmail()+getTimestamp()+getPassword()));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			StringBuffer buffer = new StringBuffer();
			buffer.append("<a href=\"");
			if (user != null) {
				buffer.append(getProtocol());
				buffer.append("://svarbox.teljari.is/?c=");
				buffer.append(getServiceID());
				buffer.append("&n=");
				buffer.append(getName());
				buffer.append("&email=");
				buffer.append(getEmail());
				buffer.append("&_hdate=");
				buffer.append(getTimestamp());
				buffer.append("&_hash=");
				buffer.append(getHash());
				buffer.append("\" target=\"modernus_answerbox\">");
			}
			else {
				buffer.append("\">");
			}
			if (getLinkImageURL() != null && !getLinkImageURL().equals("")) {
				buffer.append("<img alt=\"Svarbox\"");
				if (getImageWidth() != null) {
					buffer.append(" width=").append(getImageWidth());
				}
				buffer.append(" src=\"");
				buffer.append(getLinkImageURL());
				buffer.append("\" >");
			}
			else if (getLinkText() != null && !getLinkText().equals("")) {
				buffer.append(getLinkText());
			}
			else {
				buffer.append("Svarbox");
			}
			buffer.append("</a>");
			
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
		Object values[] = new Object[6];
		values[0] = super.saveState(ctx);
		values[1] = getServiceID();
		values[2] = getLinkImageURL();
		values[3] = getLinkText();
		values[4] = getPassword();
		values[5] = getImageWidth();
		//values[5] = Boolean.valueOf((isUseHttps()));
		return values;
	}

	/**
	 * @see javax.faces.component.UIComponentBase#restoreState(javax.faces.context.FacesContext,
	 *      java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(ctx, values[0]);
		setServiceID((String) values[1]);
		setLinkImageURL((String) values[2]);
		setLinkText((String) values[3]);
		setPassword((String) values[4]);
		setImageWidth((String) values[5]);
		//setAsHttps(((Boolean) values[5]).booleanValue());
	}

	public void setServiceID(String serviceID) {
		this._serviceID = serviceID;
	}

	public void setName(String name) {
		this._name = name;
	}

	public void setEmail(String email) {
		this._email = email;
	}

	public void setPassword(String password) {
		this._password = password;
	}

	public void setTimestamp(String timestamp) {
		this._timestamp = timestamp;
	}

	public void setHash(String hash) {
		this._hash = hash;
	}

	public void setLinkImageURL(String linkImageURL) {
		this._linkImageURL = linkImageURL;
	}

	public void setLinkText(String linkText) {
		this._linkText = linkText;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public void setImageWidth(String imageWidth) {
		this._imageWidth = imageWidth;
	}

	public void setAsHttps(boolean asHttps) {
		this.useHttps = asHttps;
		if (asHttps) {
			this.protocol = "https";
		}
		else {
			this.protocol = "http";
		}
	}
	
	private String  md5(String original) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("md5");
        md.update(original.getBytes() );
        BigInteger hash = new BigInteger(1,md.digest());
        return hash.toString(16);
	}
}