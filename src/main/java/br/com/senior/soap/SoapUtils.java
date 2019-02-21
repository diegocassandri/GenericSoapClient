package br.com.senior.soap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;


public class SoapUtils {

	/**
	 * Realiza o parse de um SoapObject para um JSONObject
	 *
	 * @param
	 * @return
	 */
	public static JSONObject convertSoapToJson(SoapObject soapObject){
        JSONObject json = new JSONObject();
        for (int i = 0; i < soapObject.getPropertyCount(); i++) {
            PropertyInfo propertyInfo = soapObject.getPropertyInfo(i);
            if(propertyInfo.getType().equals(SoapObject.class)){
                if(json.has(propertyInfo.getName())){
                    JSONArray arr = new JSONArray();
                    Class<?> clazz = json.get(propertyInfo.getName()).getClass();
                    if(clazz.isAssignableFrom(JSONObject.class)){
                        arr.put(json.get(propertyInfo.getName()));
                        json.put(propertyInfo.getName(), arr);
                    }else {
                        arr = json.getJSONArray(propertyInfo.getName());
                    }
                    arr.put(convertSoapToJson((SoapObject)propertyInfo.getValue()));
                }else{
                    json.put(propertyInfo.getName(), convertSoapToJson((SoapObject)propertyInfo.getValue()));
                }
            }else if(propertyInfo.getType().equals(SoapPrimitive.class)){
                json.put(propertyInfo.getName(), propertyInfo.getValue());
            }
        }
        return json;
    }
}