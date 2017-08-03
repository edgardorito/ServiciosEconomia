/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aspirante;

import conexionBD.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author ERIDE21
 */
@WebService(serviceName = "Aspirante")
public class Aspirante {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "aprobacionEmprendedor")
    public String aprobacionEmprendedor(@WebParam(name = "nombreProyecto") String nombreProyecto) {
        String regreso;
        if (nombreProyecto.isEmpty()) {
            regreso = "La entrada esta vacia";
            return regreso;
        }else{
            Conexion con = new Conexion();
            String query = "UPDATE aspirantes SET emprendedor = 1 WHERE nombreproy='" +nombreProyecto+ "';";
            con.actualizar(query);
            regreso = "Se ha aprobado el proyecto";
            return regreso;
        }
        
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "crearAspirante")
    public String crearAspirante(@WebParam(name = "datosAspirante") datosAspirante datosAspirante) {
        String errorAspirante;
        if (datosAspirante.getNombre().isEmpty() ||
            datosAspirante.getApellidoPaterno().isEmpty() ||
            datosAspirante.getApellidoMaterno().isEmpty() ||
            datosAspirante.getCorreo().isEmpty() ||
            datosAspirante.getNombreProyecto().isEmpty() ||
            datosAspirante.getDescripcionProyecto().isEmpty() ||
            datosAspirante.getTelefono() <= 0 ||
            datosAspirante.getEdad() <= 0    
            ) {
            errorAspirante = "Falto llenar un dato";
            return errorAspirante;
        }else{
            Conexion con = new Conexion();
            String query = "INSERT INTO aspirantes VALUES ('" + datosAspirante.getNombre() + "','"
                    + datosAspirante.getApellidoPaterno() + "','" + datosAspirante.getApellidoMaterno() + "','"
                    + datosAspirante.getCorreo() + "','" + datosAspirante.getNombre() + "','"
                    + datosAspirante.getDescripcionProyecto() + "',"
                    + datosAspirante.getTelefono() + "," + datosAspirante.getEdad() + "," + 0 + ");";
            Boolean creacion = con.insertar(query);
            if (creacion == true) {
                errorAspirante = "Se creo el aspirante";
                return errorAspirante;
            } else {
                errorAspirante = "No se pudo crear el aspirante";
                return errorAspirante;
            }
        }

        
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "eliminarAspirante")
    public String eliminarAspirante(@WebParam(name = "nombreProyecto") String nombreProyecto) {
        String eliminar;
        if (nombreProyecto.isEmpty()) {
            eliminar = "Se necesita un nombre de proyecto";
            return eliminar;
        }
        else{
            Conexion con = new Conexion();
            String query = "DELETE FROM aspirantes WHERE nombreproy = " +nombreProyecto+";";
            Boolean eliminado = con.eliminar(query);
            if (eliminado == true) {
                eliminar = "Se ha eliminado correctamente el aspirante";
                return eliminar;
            }else{
                eliminar = "No se ha podido eliminar el aspirante";
                return eliminar;
            }
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "listarAspirantes")
    public List listarAspirantes() {
        Conexion con = new Conexion();
        String query = "select * from aspirantes; ";
        ResultSet rs = con.select(query);
        List listaAspirantes = new ArrayList<datosAspirante>();
        String lista;
        try {
            while(rs.next()){
                datosAspirante aspirante = new datosAspirante();
                aspirante.setNombre(rs.getString("nombre"));
                aspirante.setApellidoPaterno(rs.getString("apellidopaterno"));
                aspirante.setApellidoMaterno(rs.getString("apellidomaterno"));
                aspirante.setCorreo(rs.getString("correo"));
                aspirante.setNombreProyecto(rs.getString("nombreproy"));
                aspirante.setDescripcionProyecto(rs.getString("descripcionproy"));
                aspirante.setTelefono(rs.getInt("telefono"));
                aspirante.setEdad(rs.getInt("edad"));
                aspirante.setEmprendedor(rs.getInt("emprendedor"));
                listaAspirantes.add(aspirante);
            }
        } catch (SQLException e) {            
            System.out.println("error: "+e);
        }
        return listaAspirantes;
    }
}
