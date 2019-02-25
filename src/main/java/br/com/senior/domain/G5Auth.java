package br.com.senior.domain;


import org.ksoap2.serialization.SoapObject;

import lombok.Data;

@Data
public class G5Auth {
	
	private String pmUserName;
	
	private String pmUserPassword;
	
	private Integer pmEncrypted;
	
	private Integer pmLoged;
	
	public SoapObject toSoapObject() {
		
		var soapObject = new SoapObject()
				.addProperty("pmUserName",pmUserName)
				.addProperty("pmUserPassword",pmUserPassword)
				.addProperty("pmEncrypted",pmUserPassword);
		
		return soapObject;
	}

}
