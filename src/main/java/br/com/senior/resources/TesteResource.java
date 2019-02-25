package br.com.senior.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.senior.domain.G5Auth;
import br.com.senior.service.G5AuthService;



@RestController
@RequestMapping("/v1")
public class TesteResource {
	
    @Autowired
    private G5AuthService g5AuthService;

	@RequestMapping(value = "/auth", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> List(@RequestBody G5Auth g5Auth) throws JSONException {
		
		var autentication = g5AuthService.authenticateG5(g5Auth);
		
		return new ResponseEntity<Object>(autentication.toString(), HttpStatus.OK);
			
	}
	
	
	
}
