/*--------------------------------------------------------------------------------------------------*/
/* Program Assignment: Program 3 - PSP 0.1                                                          */
/* Name: Rodriguez Quintero Luis Rodolfo                                                            */
/* Date: 30/Nov/2025                                                                                */
/* Description: Clase EstimacionCorLineal: calcula regresión lineal y correlación (estado interno). */
/*--------------------------------------------------------------------------------------------------*/

// Implementación con estado interno: mantiene arrays originales, sumas y coeficientes calculados.
import java.util.Locale; // Importa Locale por consistencia en formatos

public class EstimacionCorLineal { // Inicio de la clase

    private String[] xStr; // Almacena valores X en forma de String
    private String[] yStr; // Almacena valores Y en forma de String
    private double[] x; // Almacena valores X como double
    private double[] y; // Almacena valores Y como double
    private int n; // Número de pares válidos
    private double sumX; // Suma de X
    private double sumY; // Suma de Y
    private double sumXY; // Suma de X*Y
    private double sumXX; // Suma de X^2
    private double sumYY; // Suma de Y^2
    private double avgX; // Media de X
    private double avgY; // Media de Y
    private double b1; // Pendiente calculada
    private double b0; // Intersección calculada
    private double r; // Coeficiente de correlación

    /** 
     * Constructor que recibe arrays de String y opcional xk en String (puede ser null).
     * Convierte y calcula sumas básicas.
     */
    public EstimacionCorLineal(String[] xStr, String[] yStr, String xkStr) { // Constructor con parámetros
        this.xStr = xStr; // Guarda referencia al array de Strings X
        this.yStr = yStr; // Guarda referencia al array de Strings Y
        this.n = Math.min(xStr.length, yStr.length); // Determina n como el mínimo de longitudes
        this.x = new double[this.n]; // Crea array double para X
        this.y = new double[this.n]; // Crea array double para Y
        this.sumX = 0.0; // Inicializa sumX
        this.sumY = 0.0; // Inicializa sumY
        this.sumXY = 0.0; // Inicializa sumXY
        this.sumXX = 0.0; // Inicializa sumXX
        this.sumYY = 0.0; // Inicializa sumYY
        for (int i = 0; i < this.n; i++) { // Convierte cada par hasta n
            double xv = parseDoubleSafe(xStr[i]); // Convierte X seguro
            double yv = parseDoubleSafe(yStr[i]); // Convierte Y seguro
            this.x[i] = xv; // Asigna valor X convertido
            this.y[i] = yv; // Asigna valor Y convertido
            this.sumX += xv; // Suma X
            this.sumY += yv; // Suma Y
            this.sumXY += xv * yv; // Suma XY
            this.sumXX += xv * xv; // Suma XX
            this.sumYY += yv * yv; // Suma YY
        } // fin del for de conversión y sumas
        this.avgX = (this.n > 0) ? (this.sumX / this.n) : Double.NaN; // Calcula media X
        this.avgY = (this.n > 0) ? (this.sumY / this.n) : Double.NaN; // Calcula media Y
        computeCoefficients(); // Calcula b1, b0 y r
    } // fin del constructor

    // Método auxiliar para convertir String a double manejando comas decimales y errores
    private double parseDoubleSafe(String s) { // Inicio del método parseDoubleSafe
        if (s == null) return Double.NaN; // Si es nulo devuelve NaN
        String t = s.trim().replace(',', '.'); // Reemplaza coma decimal por punto
        try { // Intenta parsear
            return Double.parseDouble(t); // Devuelve el double parseado
        } catch (NumberFormatException e) { // Si falla el parseo
            return Double.NaN; // Devuelve NaN en caso de error
        } // fin del catch
    } // fin del método parseDoubleSafe

    // Calcula b1, b0 y r usando las sumas ya computadas
    private void computeCoefficients() { // Inicio de computeCoefficients
        if (this.n < 2) { // Si hay menos de 2 puntos
            this.b1 = Double.NaN; // No se puede calcular pendiente
            this.b0 = Double.NaN; // No se puede calcular intersección
            this.r = Double.NaN; // No se puede calcular correlación
            return; // Sale temprano
        } // fin del if
        double numeratorB1 = this.n * this.sumXY - this.sumX * this.sumY; // Numerador pendiente
        double denominatorB1 = this.n * this.sumXX - this.sumX * this.sumX; // Denominador pendiente
        if (denominatorB1 == 0.0) { // Si denominador es cero
            this.b1 = Double.NaN; // Pendiente indefinida
        } else { // Si denominador distinto de cero
            this.b1 = numeratorB1 / denominatorB1; // Calcula pendiente
        } // fin del else
        if (!Double.isNaN(this.b1)) { // Si b1 es válido
            this.b0 = this.avgY - this.b1 * this.avgX; // Calcula intersección b0
        } else { // Si b1 no es válido
            this.b0 = Double.NaN; // Asigna NaN a b0
        } // fin del else
        double numerR = numeratorB1; // Numerador para r (igual que numerador de b1)
        double denomPart1 = this.n * this.sumXX - this.sumX * this.sumX; // Primer término del denominador de r
        double denomPart2 = this.n * this.sumYY - this.sumY * this.sumY; // Segundo término del denominador de r
        double denomR = Math.sqrt(Math.max(0.0, denomPart1) * Math.max(0.0, denomPart2)); // Producto y raíz
        if (denomR == 0.0) { // Si denominador de r es cero
            this.r = Double.NaN; // Correlación indefinida
        } else { // Si denominador no es cero
            this.r = numerR / denomR; // Calcula r de Pearson
        } // fin del else
    } // fin del método computeCoefficients

    /** Devuelve B1 (pendiente) calculada. */
    public double getB1() { // Getter de b1
        return this.b1; // Devuelve b1
    } // fin de getB1

    /** Devuelve B0 (intersección) calculada. */
    public double getB0() { // Getter de b0
        return this.b0; // Devuelve b0
    } // fin de getB0

    /** Devuelve R (coeficiente de correlación) calculado. */
    public double getR() { // Getter de r
        return this.r; // Devuelve r
    } // fin de getR

    /** Predice Yk usando la regresión calculada para un Xk dado. */
    public double getYk(double xk) { // Método para predecir yk
        if (Double.isNaN(this.b0) || Double.isNaN(this.b1)) { // Si no hay modelo válido
            return Double.NaN; // Devuelve NaN
        } // fin del if
        return this.b0 + this.b1 * xk; // Calcula yk = b0 + b1*xk
    } // fin de getYk

    /** Devuelve número de pares usados en los cálculos. */
    public int getN() { // Getter de n
        return this.n; // Devuelve n
    } // fin de getN
} // fin de la clase EstimacionCorLineal
