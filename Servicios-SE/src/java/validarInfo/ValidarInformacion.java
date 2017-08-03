/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validarInfo;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author ERIDE21
 */
@WebService(serviceName = "ValidarInformacion")
public class ValidarInformacion {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "validarEdad")
    public Boolean validarEdad(@WebParam(name = "edad") int edad) {
        if (edad >= 18) {
            return true;
        }else{
            return false;
        }
        
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "validarResidencia")
    public Boolean validarResidencia(@WebParam(name = "residencia") String residencia) {

        if (residencia.equals("Chiapas") || residencia.equals("chiapas")) {
            return true;
        }else{
            return false;
        }
    }

}
