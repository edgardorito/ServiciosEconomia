/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coordinacion;

import aspirante.datosAspirante;
import conexionBD.Conexion;
import emprendedor.Emprendedor;
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
@WebService(serviceName = "Coordinacion")
public class Coordinacion {


    /**
     * Web service operation
     */
    @WebMethod(operationName = "guardarEvento")
    public String guardarEvento(@WebParam(name = "evento") Evento evento) {
        String eventoError;
        if (evento.getNombreEvento().isEmpty() ||
            evento.getFecha().isEmpty() ||  
            evento.getUbicacion().isEmpty() ||
            evento.getDuracionDias() <=0 ||
            evento.getCosto() < 0 ){
            eventoError = "Falto llenar un campo";
            return eventoError;
        }else{
            Conexion con = new Conexion();
            String query = "INSERT INTO evento VALUES ('" + evento.getNombreEvento() + "','"
                    +  evento.getFecha() + "','" + evento.getUbicacion() + "',"
                    + evento.getDuracionDias() + "," + evento.getCosto() + ");";
            Boolean creacion = con.insertar(query);
            if (creacion == true) {
                eventoError = "Se creo el evento";
                return eventoError;
            } else {
                eventoError = "No se pudo crear el evento";
                return eventoError;
            }
        }
        
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "obtenerEventos")
    public List obtenerEventos() {
        Conexion con = new Conexion();
        String query = "select * from evento; ";
        ResultSet rs = con.select(query);
        List eventos = new ArrayList<Evento>();
        String lista;
        try {
            while(rs.next()){
                Evento evento = new Evento();
                evento.setNombreEvento(rs.getString("nombreevento"));
                evento.setFecha(rs.getString("fecha"));
                evento.setUbicacion(rs.getString("ubicacion"));
                evento.setDuracionDias(rs.getInt("duraciondias"));
                evento.setCosto(rs.getInt("costo"));
                eventos.add(evento);
            }
        } catch (SQLException e) {            
            System.out.println("error: "+e);
        }
        return eventos;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "obtenerResultados")
    public List obtenerResultados() {
        Conexion con = new Conexion();
        String query = "select * from emprendedor; ";
        ResultSet rs = con.select(query);
        List emprendedores = new ArrayList<datosAspirante>();
        String lista;
        try {
            while(rs.next()){
                datosAspirante emprendedor = new datosAspirante();
                emprendedor.setNombre(rs.getString("nombre"));
                emprendedor.setApellidoPaterno(rs.getString("apellidopaterno"));
                emprendedor.setApellidoMaterno(rs.getString("apellidomaterno"));
                emprendedor.setCorreo(rs.getString("correo"));
                emprendedor.setNombreProyecto(rs.getString("nombreproy"));
                emprendedor.setDescripcionProyecto(rs.getString("descripcionproy"));
                emprendedor.setTelefono(rs.getInt("telefono"));
                emprendedor.setEdad(rs.getInt("edad"));
                emprendedores.add(emprendedor);
            }
        } catch (SQLException e) {            
            System.out.println("error: "+e);
        }
        return emprendedores;
    }

}
