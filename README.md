# tuten_problema_2
Resolución de ejercicio numero 2

##Requisitos:
 * Java JDK >= 8
 * Gradle >= 4
 
##Tecnologías utilizadas
###Spring MVC 
   Con este framework se facilita mantener servicios web.  Inicialmente pensé en utilizar el API HttpServer, pero eso significaba horas
   de desarrollo adicionales.
   
##Instalación
  Descargar repositorio, luego en la raiz del proyecto, ejecutar en la consola de comandos : *gradle bootRun*

##Utilización
  Simplemente ejecutar en el navegador la url : *http://localhost/utc?hora=12:23:10&timezone=-4*
  Donde hora es la hora que se desea convertir y timezone el cambio que se requiere (acepta -12 hasta +12).
