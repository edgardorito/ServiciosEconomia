/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notificacion;

import java.util.Properties;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import static org.apache.coyote.http11.Constants.a;

/**
 *
 * @author ERIDE21
 */
@WebService(serviceName = "Notificaciones")
public class Notificaciones {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "enviarCorreo")
    public Boolean enviarCorreo(@WebParam(name = "correo") String correo, @WebParam(name = "mensaje") String mensaje) {
       String asunto = "Secretaria de Economia";
       String correoSecretaria = "secretariaeconomia.soa@gmail.com";
        if (correo.isEmpty() || mensaje.isEmpty()) {
            return false;
        }else{
            try {
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.user", correo);

                Session session = Session.getInstance(props, null);
                BodyPart texto = new MimeBodyPart();
                texto.setText(mensaje);
                MimeMultipart m = new MimeMultipart();
                m.addBodyPart(texto);
                MimeMessage mimensaje = new MimeMessage(session);
                mimensaje.setFrom(new InternetAddress(correoSecretaria));
                mimensaje.setText(mensaje);
                mimensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(correo));
                mimensaje.setSubject(asunto);

                Transport t = session.getTransport("smtp");
                t.connect(correoSecretaria, "rmdqhkvktcxiipwb");
                t.sendMessage(mimensaje, mimensaje.getAllRecipients());
                t.close();

                return true;
            } catch (Exception e) {
                System.out.println("Error " + e);
                return false;
            }
        }
 
       


        
    }
}
