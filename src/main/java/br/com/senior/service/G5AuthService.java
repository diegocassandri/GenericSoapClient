package br.com.senior.service;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmlpull.v1.XmlPullParserException;

import br.com.senior.domain.G5Auth;
import br.com.senior.soap.SoapClientSeniorImpl;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class G5AuthService {
	
	@Autowired
	private SoapClientSeniorImpl soapClientSenior;
	
	private static final String AUTHENTICATE = "AuthenticateJAAS";

	
	public JSONObject authenticateG5(G5Auth g5Auth)  {

        try {
            return soapClientSenior.executeWSDL(AUTHENTICATE, "rubi_SyncMCWFUsers", g5Auth.toSoapObject());
        } catch (IOException |XmlPullParserException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Erro ao executar serviço G5.", e);
        }
    }
		

}
