package problema2;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UTCController {

    @RequestMapping("/utc")
    public Map<String,Map<String,String>>   obtener_utc(
        @RequestParam(value="hora") String hora,
        @RequestParam(value="timezone") String timezone){

       Pattern patron = Pattern.compile("(\\d{1,2}):(\\d{1,2}):(\\d{1,2})");
       Matcher matcher = patron.matcher(hora);

       Map<String,Map<String,String>> retorno = new HashMap<String,Map<String,String>>();
       Map<String,String> resultado = new HashMap<String,String>();

       boolean valido = matcher.find() && timezone.matches("(\\-|\\+)?\\d{1,2}");

       if (valido){
         Integer pHora = Integer.parseInt(matcher.group(1));
         pHora = ((pHora + Integer.parseInt(timezone)) % 24);
         Integer pMinuto = Integer.parseInt(matcher.group(2));
         Integer pSegundo = Integer.parseInt(matcher.group(3));
         Integer pTimezone = Integer.parseInt(timezone);

         valido = pHora >= 0 && pHora <= 23 &&
                  pMinuto >= 0 && pMinuto <= 59 && 
                  pSegundo >= 0 && pSegundo <= 59 && 
                  pTimezone >= -12 && pTimezone <= 12;
         
         if (valido){
           resultado.put("hora",String.format("%02d",pHora) + ":" + 
                                String.format("%02d",pMinuto) + ":" + 
                                String.format("%02d",pSegundo));

           resultado.put("tipo","utc");
         }

       }

       if (!valido) resultado.put("error","hora o timezone no valido");

       retorno.put("response",resultado);

       return retorno;
    }
}

