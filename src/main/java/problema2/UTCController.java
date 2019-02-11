package problema2;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UTCController {

    @RequestMapping("/utc")
    public Map<String,Map<String,String>>   obtener_utc(
        @RequestParam(value="hora") String hora,
        @RequestParam(value="timezone") String timezone,
        HttpServletResponse  response)){

       //expresión regular para validar hora (formato NN:NN:NN)
       Pattern patron = Pattern.compile("(\\d{1,2}):(\\d{1,2}):(\\d{1,2})");
       Matcher matcher = patron.matcher(hora);

       //Mapa para preparar el json de retorno
       Map<String,Map<String,String>> retorno = new HashMap<String,Map<String,String>>();
       Map<String,String> resultado = new HashMap<String,String>();

       //validador del campo UTC (-N|+N|N)
       boolean valido = matcher.find() && timezone.matches("(\\-|\\+)?\\d{1,2}");

       //comprueba que sean válidas las formas
       if (valido){
         //recoge los distintos grupos de la expresion regular para extraer
         //los componentes de la hora
         Integer pHora = Integer.parseInt(matcher.group(1));
         Integer pMinuto = Integer.parseInt(matcher.group(2));
         Integer pSegundo = Integer.parseInt(matcher.group(3));
         Integer pTimezone = Integer.parseInt(timezone);

         //válida los numeros en hora y timezone
         valido = pHora >= 0 && pHora <= 23 &&
                  pMinuto >= 0 && pMinuto <= 59 && 
                  pSegundo >= 0 && pSegundo <= 59 && 
                  pTimezone >= -12 && pTimezone <= 12;
         
         //si es valido prepara el calculo
         if (valido){
           //realiza el cálculo de cambio de utc
           pHora = ((pHora + Integer.parseInt(timezone)) % 24);
           //formatea el resultado
           resultado.put("hora",String.format("%02d",pHora) + ":" + 
                                String.format("%02d",pMinuto) + ":" + 
                                String.format("%02d",pSegundo));
             
           //formatea el formato del utc de respuesta
           String utc = pTimezone > 0 ? "+"+pTimezone.toString() : pTimezone.toString();
           resultado.put("tipo","utc" + utc );
         }

       }
       //imprime el error ya que la variable valido es falsa
       if (!valido) resultado.put("error","hora o timezone no valido");

       //permite CORS
       response.setHeader("Access-Control-Allow-Origin", "*");
       //prepara el objeto JSON de respuesta
       retorno.put("response",resultado);
        
       //retorno del webservice
       return retorno;
    }
}

