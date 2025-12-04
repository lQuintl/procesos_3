/*---------------------------------------------------------------------------*/
/* Program Assignment: Program 3 - PSP 0.1                                   */
/* Name: Rodriguez Quintero Luis Rodolfo                                     */
/* Date: 30/Nov/2025                                                         */
/* Description: Clase Logic: integra módulos conforme al diagrama UML.       */
/*---------------------------------------------------------------------------*/

//Importes
import java.io.IOException; // Importa IOException para manejo de E/S
import java.util.ArrayList; // Importa ArrayList para listas dinámicas
import java.util.List; // Importa List para tipos de colección
import java.util.Locale; // Importa Locale para formateo de strings
import java.util.Optional; // Importa Optional para argumento xk opcional

public class Logic { // Inicio de la clase Logic

    private String dataX; // Campo auxiliar para datos X en texto (opcional)
    private String dataY; // Campo auxiliar para datos Y en texto (opcional)
    private String dataXk; // Campo auxiliar para xk en texto (opcional)
    private String[] arrDataX; // Campo para almacenar X como Strings
    private String[] arrDataY; // Campo para almacenar Y como Strings
    private double thisXk; // Campo para almacenar xk como double si se provee

    /** Método principal que ejecuta el flujo completo. */
    public void logic1a(String inFile, String outFile, Optional<Double> maybeXk) { // Inicio de logic1a
        Input in = new Input(); // Crea instancia de Input
        Output out = new Output(); // Crea instancia de Output
        Data dataModule = new Data(); // Crea instancia de Data
        // No se crea EstimacionCorLineal aún porque necesita los arrays parseados

        String raw; // Variable para almacenar contenido crudo
        try { // Intenta leer el archivo de entrada
            raw = in.readData(inFile); // Lee todo el archivo como String
        } catch (IOException e) { // Si ocurre error de lectura
            String err = "ERROR: No se pudo leer el archivo de entrada: " + e.getMessage(); // Mensaje de error
            try { out.writeData(outFile, err); } catch (IOException ex) { /* Ignora si también falla escritura */ } // Intenta escribir error
            return; // Finaliza ejecución
        } // fin del try-catch

        String[] lines = dataModule.saveData(raw); // Normaliza y obtiene líneas útiles
        List<String> xListStr = new ArrayList<>(); // Lista para valores X como String
        List<String> yListStr = new ArrayList<>(); // Lista para valores Y como String

        for (String line : lines) { // Itera sobre cada línea normalizada
            String cleaned = line.trim(); // Limpia espacios externos
            if (cleaned.isEmpty()) continue; // Ignora líneas vacías por seguridad
            cleaned = cleaned.replace(';', ','); // Sustituye ';' por ',' para uniformidad
            String[] parts = cleaned.split(","); // Divide la línea en partes por coma
            if (parts.length < 2) continue; // Ignora si no hay al menos 2 columnas
            xListStr.add(parts[0].trim()); // Añade la primera columna como X (trim)
            yListStr.add(parts[1].trim()); // Añade la segunda columna como Y (trim)
        } // fin del for de parseo

        if (xListStr.size() < 2) { // Si hay menos de 2 pares válidos
            String err = "ERROR: No hay suficientes pares válidos (se requieren al menos 2)."; // Mensaje de error
            try { out.writeData(outFile, err); } catch (IOException e) { /* Ignora si falla escritura */ } // Intenta escribir error
            return; // Sale del método
        } // fin del if

        this.arrDataX = xListStr.toArray(new String[0]); // Convierte lista X a arreglo
        this.arrDataY = yListStr.toArray(new String[0]); // Convierte lista Y a arreglo

        EstimacionCorLineal est = new EstimacionCorLineal(this.arrDataX, this.arrDataY, null); // Crea instancia de EstimacionCorLineal con estado

        double b1 = est.getB1(); // Obtiene pendiente b1
        double b0 = est.getB0(); // Obtiene intersección b0
        double r = est.getR(); // Obtiene coeficiente de correlación r

        StringBuilder sb = new StringBuilder(); // Crea StringBuilder para resultados
        sb.append("Resultado obtenido en la prueba").append(System.lineSeparator()); // Añade título
        sb.append("Elementos totales analizados: ").append(est.getN()).append(System.lineSeparator()); // Añade número de pares
        sb.append(String.format(Locale.ROOT, "B0 = %.6f%n", b0)); // Formatea y añade B0 siguiendo el resultado del excel
        sb.append(String.format(Locale.ROOT, "B1 = %.6f%n", b1)); // Formatea y añade B1 siguiendo el resultado del excel
        sb.append(String.format(Locale.ROOT, "R  = %.6f%n", r)); // Formatea y añade R
        sb.append(String.format(Locale.ROOT, "Ecuacion: y = %.6f + %.6f * x%n", b0, b1)); // Añade ecuación

        if (maybeXk.isPresent()) { // Si se proporcionó un valor xk opcional
            double xk = maybeXk.get(); // Extrae xk
            double yk = est.getYk(xk); // Calcula yk usando el modelo
            sb.append(String.format(Locale.ROOT, "Predicciones obtenidas: xk=%.6f => yk=%.6f%n", xk, yk)); // Añade predicción
        } // fin del if maybeXk

        try { // Intenta escribir el archivo de salida
            out.writeData(outFile, sb.toString()); // Escribe contenido en outFile
        } catch (IOException e) { // Si falla la escritura
            try { out.writeData(outFile, "ERROR: No se pudo escribir resultados: " + e.getMessage()); } catch (IOException ex) { /* Ignora */ } // Intenta escribir error
        } // fin del try-catch
    } // fin del método logic1a
} // fin de la clase Logic
