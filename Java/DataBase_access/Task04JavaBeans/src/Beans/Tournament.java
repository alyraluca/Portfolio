package Beans;
import java.beans.*;
import java.io.Serializable;

public class Tournament implements Serializable, PropertyChangeListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * ATTRIBUTES
	 */
	private PlayerBean objPlayerBean;
	private String stCode;
	private String stName;
	
	/*
	 * METHODS
	 */
	
	//Empty Constructor
	public Tournament() {		
	}
	
	//Constructor with all the fields
	public Tournament(PlayerBean objPlayerBean, String stCode, String stName) {
		this.objPlayerBean = objPlayerBean;
		this.stCode = stCode;
		this.stName = stName;
	}
	/*
	 * GETTERS & SETTERS
	 */

	public PlayerBean getObjPlayerBean() {
		return objPlayerBean;
	}
	

	public void setObjPlayerBean(PlayerBean objPlayerBean) {
		this.objPlayerBean = objPlayerBean;
	}

	public String getStCode() {
		return stCode;
	}

	public void setStCode(String stCode) {
		this.stCode = stCode;
	}

	public String getStName() {
		return stName;
	}

	public void setStName(String stName) {
		this.stName = stName;
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		
	}
	
	
}
