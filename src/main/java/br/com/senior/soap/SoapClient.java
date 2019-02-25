package br.com.senior.soap;


import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.xmlpull.v1.XmlPullParserException;

import br.com.senior.config.SoapConfig;

import java.io.IOException;
import java.net.Proxy;
import java.util.Map;

/**
 * Client SOAP genérico
 * @author Bryan.Leite
 *
 */
@Slf4j
public abstract class SoapClient {
	
	private static final int CONN_TIMEOUT = 60000;
	
	@Autowired
	SoapConfig soapConfig;
	
	/**
	 * Executa um WSDL SOAP conforme parametrização e classe específica implementada
	 * @param portProcess
	 * @param wsdlRoute
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public JSONObject executeWSDL(String portProcess, String wsdlRoute, SoapObject request) throws IOException, XmlPullParserException {
		String wsdl = getSoapHost(wsdlRoute);

		log.info("wsdl: "+wsdl);

		SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(request);
		HttpTransportSE ht = getHttpTransportSE(wsdl, CONN_TIMEOUT);

		ht.call(portProcess, envelope);

		return SoapUtils.convertSoapToJson((SoapObject )envelope.getResponse());
	}
	
	protected SoapObject buildRequest(String portProcess, Map<String, Object> requestProperties) {
		SoapObject request = new SoapObject(getSoapNamespace(), portProcess);
		if(!CollectionUtils.isEmpty(requestProperties)) {
			for (Map.Entry<String, Object> property : requestProperties.entrySet()) {
				if(property.getValue() instanceof SoapObject) {
					request.addProperty(property.getKey(), property.getValue());
				}else {
					request.addProperty(property.getKey(), property.getValue());
				}
			}
		}
		return request;
	}
	
	private String getSoapHost(String wsdlRoute)  {
		/*SoapConfig conf = new SoapConfig();*/
		return String.format("%s%s/%s?wsdl", soapConfig.getHost(), getSoapPrefix(), wsdlRoute);
	}

	private HttpTransportSE getHttpTransportSE(String wsdl, int timeout) {
		HttpTransportSE ht = new HttpTransportSE(Proxy.NO_PROXY, wsdl, timeout);
		ht.debug = true;
		ht.setXmlVersionTag("<!--?xml version=\"1.0\" encoding= \"UTF-8\" ?-->");
		return ht;
	}

	private SoapSerializationEnvelope getSoapSerializationEnvelope(SoapObject request) {
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.implicitTypes = false;
		envelope.skipNullProperties = false;
		envelope.setOutputSoapObject(request);
		return envelope;
	}
	
	/**
	 * Responsável por retornar o namespace SOAP conforme implementação.
	 * @return
	 */
	protected abstract String getSoapNamespace();
	
	/**
	 * Responsável por retornar o prefixo presente antes do WSDL.
	 * Ex: No WSDL "g5-senior-services/rubi_Synccom_senior_g5_rh_fp_colaboradoresAdmitidos?wsdl", "g5-senior-services" é o prefixo
	 * 	   
	 * @return
	 */
	protected abstract String getSoapPrefix();
}
