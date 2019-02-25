package br.com.senior.soap;


import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmlpull.v1.XmlPullParserException;

import br.com.senior.config.SoapConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Responsável por chamar o Client Default moldado conforme os serviços SOAP Senior.
 * @author Bryan.Leite
 *
 */
@Service
@Slf4j
public class SoapClientSeniorImpl extends SoapClient{

	private static final String SOAP_NAMESPACE = "http://services.senior.com.br";
	private static final String SOAP_PREFIX = "g5-senior-services";
	
	@Autowired
	SoapConfig soapConfig;
	
	
	@Override
	public JSONObject executeWSDL(String portProcess, String wsdlRoute, SoapObject soapObject) throws IOException, XmlPullParserException {
		Map<String, Object> newRequestProperties = new HashMap<>();
		
		log.info(soapConfig.toString());

		newRequestProperties.put("user", soapConfig.getMasterUsername());
		newRequestProperties.put("password", soapConfig.getMasterUsername());
		newRequestProperties.put("encryption", NumberUtils.INTEGER_ZERO);
		
		newRequestProperties.put("parameters", soapObject);
		
		return super.executeWSDL(portProcess, wsdlRoute, buildRequest(portProcess, newRequestProperties));
	}
	
	@Override
	protected String getSoapNamespace() {
		return SOAP_NAMESPACE;
	}

	@Override
	protected String getSoapPrefix() {
		return SOAP_PREFIX;
	}

}
