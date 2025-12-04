/*---------------------------------------------------------------------------*/
/* Program Assignment: Program 3 - PSP 0.1                                   */
/* Name: Rodriguez Quintero Luis Rodolfo                                     */
/* Date: 30/Nov/2025                                                         */
/* Description: Clase Data: prepara y normaliza líneas de entrada según UML. */
/*---------------------------------------------------------------------------*/

//Importes
import java.util.ArrayList; // Importa ArrayList para colecciones dinámicas
import java.util.List; // Importa List para tipo de retorno

public class Data { // Inicio de la clase Data

    /** 
     * Normaliza contenido crudo y devuelve líneas útiles.
     * @param data contenido crudo
     * @return arreglo de líneas útiles
     */
    public String[] saveData(String data) { // Método que normaliza las líneas
        if (data == null || data.isEmpty()) { // Si el contenido es nulo o vacío
            return new String[0]; // Devuelve arreglo vacío
        } // fin del if
        String[] rawLines = data.split("\\r?\\n"); // Separa por saltos de línea
        List<String> clean = new ArrayList<>(); // Lista para líneas procesadas
        for (String line : rawLines) { // Itera sobre cada línea cruda
            if (line == null) continue; // Si la línea es nula, la ignora
            String t = line.trim(); // Quita espacios iniciales y finales
            if (t.isEmpty()) continue; // Ignora líneas vacías
            if (t.startsWith("#")) continue; // Ignora líneas que son comentarios con #
            clean.add(t); // Añade la línea útil a la lista limpia
        } // fin del for
        return clean.toArray(new String[0]); // Devuelve arreglo de líneas útiles
    } // fin del método saveData
} // fin de la clase Data
