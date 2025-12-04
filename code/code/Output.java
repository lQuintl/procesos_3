/*--------------------------------------------------------------------------------*/
/* Program Assignment: Program 3 - PSP 0.1                                        */
/* Name: Rodriguez Quintero Luis Rodolfo                                          */
/* Date: 30/Nov/2025                                                              */
/* Description: Clase Output: módulo encargado de escribir resultados en fichero. */
/*--------------------------------------------------------------------------------*/

//Importes
import java.io.BufferedWriter; // Importa BufferedWriter para buffer de escritura
import java.io.FileWriter; // Importa FileWriter para crear/abrir el archivo
import java.io.IOException; // Importa IOException para manejo de errores


public class Output { // Inicio de la clase Output

    /** 
     * Escribe texto en el archivo de salida.
     * @param outFile ruta del archivo de salida
     * @param outText contenido a escribir
     * @throws IOException si ocurre un error de E/S
     */
    public void writeData(String outFile, String outText) throws IOException { // Método para escribir archivo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFile))) { // Abre writer en try-with-resources
            bw.write(outText); // Escribe el texto completo en el archivo
            bw.flush(); // Fuerza el volcado del buffer al archivo
        } // cierre automático del BufferedWriter
    } // fin del método writeData
} // fin de la clase Output
