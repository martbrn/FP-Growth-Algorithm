
package Algoritmo.ItemsetsFrecuentes;

public class Comparacion {
    private String MinConf; // Confianza utilizada en la corrida
    private String MinSup; // Soporte utilizado en la corrida
    private double tiempo; // Tiempo de procesamiento de la corrida
    private int regla; // Cantidad de reglas generadas en la corrida

    //Getters y Setters
    public int getRegla() {
        return regla;
    }

    public void setRegla(int regla) {
        this.regla = regla;
    }
        
    public String getMinConf() {
        return MinConf;
    }

    public void setMinConf(String MinConf) {
        this.MinConf = MinConf;
    }

    public String getMinSup() {
        return MinSup;
    }

    public void setMinSup(String MinSup) {
        this.MinSup = MinSup;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }
 
}
