/*---------------------------------------------------------------------------*/
/* Program Assignment: Program 3 - PSP 0.1                                   */
/* Name: Rodriguez Quintero Luis Rodolfo                                     */
/* Date: 30/Nov/2025                                                         */
/* Description: Clase Input: módulo encargado de leer archivos de texto.     */ 
/*---------------------------------------------------------------------------*/

//Importesimport java.io.BufferedReader; // Importa BufferedReader para leer líneas de archivo
import java.io.FileReader; // Importa FileReader para abrir el archivo por ruta
import java.io.IOException; // Importa IOException para manejo de errores de E/S

public class Input { // Inicio de la clase Input
    private String data; // Campo para almacenar el contenido leído
    private BufferedReader br = null; // Campo para la referencia al lector (no requerido fuera)

    /** 
     * Lee todo el contenido del archivo especificado. 
     * @param inFile ruta del archivo de entrada
     * @return contenido completo del archivo como String
     * @throws IOException si ocurre un error de E/S
     */
    public String readData(String inFile) throws IOException { // Método público para leer archivo
        StringBuilder sb = new StringBuilder(); // Crea StringBuilder para acumular líneas
        try (BufferedReader r = new BufferedReader(new FileReader(inFile))) { // Abre archivo en try-with-resources
            this.br = r; // Asigna el BufferedReader al campo br
            String line; // Variable para cada línea leída
            while ((line = r.readLine()) != null) { // Lee línea por línea hasta EOF
                sb.append(line).append(System.lineSeparator()); // Añade la línea y salto de línea al acumulador
            } // fin del while de lectura
        } finally { // Bloque finally para limpieza de estado
            this.br = null; // Limpia la referencia br
        } // fin del try-finally
        this.data = sb.toString(); // Convierte StringBuilder a String y lo guarda en campo data
        return this.data; // Devuelve el contenido leído
    } // fin del método readData
} // fin de la clase Input
