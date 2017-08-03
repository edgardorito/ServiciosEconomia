/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emprendedor;

import aspirante.datosAspirante;
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
@WebService(serviceName = "Emprendedor")
public class Emprendedor {




    /**
     * Web service operation
     */
    @WebMethod(operationName = "eliminarEmprendedor")
    public String eliminarEmprendedor(@WebParam(name = "nombreProyecto") String nombreProyecto) {
        String eliminar;
        if (nombreProyecto.isEmpty()) {
            eliminar = "Se necesita un nombre de proyecto";
            return eliminar;
        }
        else{
            Conexion con = new Conexion();
            String query = "DELETE FROM emprendedor WHERE nombreproy = " +nombreProyecto+";";
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
    @WebMethod(operationName = "crearReporte")
    public String crearReporte(@WebParam(name = "reporte") ReporteEmprendedor reporte) {
        String errorReporte;
        if (reporte.getNombreProyecto().isEmpty() ||
            reporte.getTipoProyecto().isEmpty() ||
            reporte.getCiudad().isEmpty() ||
            reporte.getDireccion().isEmpty()
            ) {
            errorReporte = "Falto llenar un dato";
            return errorReporte;
        }else if (reporte.getCantidadAsignada()<= 10000) {
            errorReporte = "La cantidad es menor de 10000";
            return errorReporte;
        }else if (reporte.getCantidadGastadaMes() <=0 ) {
            errorReporte = "Tiene que ser mayor que 0";
            return errorReporte;
        }else{
            Conexion con = new Conexion();
            String query = "INSERT INTO reporte VALUES ('"+reporte.getNombreProyecto()+"','" 
                    +reporte.getTipoProyecto()+"','"+reporte.getCiudad()+"','"
                    +reporte.getDireccion()+","+reporte.getCantidadAsignada()+","
                    +reporte.getCantidadGastadaMes()+");";
            Boolean creacion = con.insertar(query);
            if (creacion == true) {
                errorReporte = "Se creo el reporte";
                return errorReporte;
            }else{
                errorReporte = "No se pudo crear el reporte";
                return errorReporte;
            }
        }
        
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "crearEmprendedores")
    public List crearEmprendedores() {
        Conexion con = new Conexion();
        String query = "select * from aspirantes WHERE emprendedor = " + 1 + "; ";
        ResultSet rs = con.select(query);
        List emprendedores = new ArrayList<datosEmprendedor>();
        String lista;
        String eliminar = "DELETE FROM emprendedor;";
        con.eliminar(eliminar);
        try {
            while (rs.next()) {
                datosEmprendedor emprendedor = new datosEmprendedor();

                emprendedor.setNombre(rs.getString("nombre"));
                emprendedor.setApellidopaterno(rs.getString("apellidopaterno"));
                emprendedor.setApellidomaterno(rs.getString("apellidomaterno"));
                emprendedor.setCorreo(rs.getString("correo"));
                emprendedor.setNombreproy(rs.getString("nombreproy"));
                emprendedor.setDescripcionproy(rs.getString("descripcionproy"));
                emprendedor.setTelefono(rs.getInt("telefono"));
                emprendedor.setEdad(rs.getInt("edad"));

                String insertar = "INSERT INTO emprendedor VALUES ('" + emprendedor.getNombre() + "','"
                        + emprendedor.getApellidopaterno() + "','" + emprendedor.getApellidomaterno() + "','"
                        + emprendedor.getCorreo() + "','" + emprendedor.getNombre() + "','"
                        + emprendedor.getDescripcionproy() + "',"
                        + emprendedor.getTelefono() + "," + emprendedor.getEdad() + "," + 0 + ");";
                con.insertar(insertar);
            }
            
        } catch (SQLException e) {
            System.out.println("error: " + e);
        }
        
        String selectEmprendedor = "select * from emprendedor;";
        ResultSet rsEmpr = con.select(selectEmprendedor);

        try {
            while (rsEmpr.next()) {
                datosEmprendedor emprendedor = new datosEmprendedor();

                emprendedor.setNombre(rs.getString("nombre"));
                emprendedor.setApellidopaterno(rs.getString("apellidopaterno"));
                emprendedor.setApellidomaterno(rs.getString("apellidomaterno"));
                emprendedor.setCorreo(rs.getString("correo"));
                emprendedor.setNombreproy(rs.getString("nombreproy"));
                emprendedor.setDescripcionproy(rs.getString("descripcionproy"));
                emprendedor.setTelefono(rs.getInt("telefono"));
                emprendedor.setEdad(rs.getInt("edad"));
                emprendedor.setRecursodisponible(rs.getInt("recursodisponible"));

                emprendedores.add(emprendedor);
            }

        } catch (SQLException e) {
            System.out.println("error: " + e);
        }

        return emprendedores;
    }
}
