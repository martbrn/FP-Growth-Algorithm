
package Algoritmo.GeneracionReglas;

import java.util.ArrayList;

public class Regla implements Comparable <Regla> {
    private ArrayList<Integer> Premsisa; // Premisa de la regla
    private ArrayList<Integer> Conclusion; // Conclusión de la regla
       private int soporte; // Soporte de la regla
    private int confianza; // Confianza de la regla
    private boolean bandera;

    // Constructor de la clase 
    public Regla (){
     this.setPremsisa(new ArrayList<Integer>());
     this.setConclusion(new ArrayList<Integer>());    
    }
    
    // Getters y Setters de la clase
    public ArrayList<Integer> getPremsisa() {
        return Premsisa;
    }

    public int getSoporte() {
        return soporte;
    }

    public void setSoporte(int soporte) {
        this.soporte = soporte;
    }

    public int getConfianza() {
        return confianza;
    }

    public void setConfianza(int confianza) {
        this.confianza = confianza;
    }

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }
    
    // Agrega el itemset a la premisa
    public void setPremsisa(ArrayList<Integer> Premsisa) {
        this.Premsisa = Premsisa;
    }

    public ArrayList<Integer> getConclusion() {
        return Conclusion;
    }

    public void setConclusion(ArrayList<Integer> Conclusion) {
        this.Conclusion = Conclusion;
    }
    
    // Agrega el elemento a la premisa
    public void AgregarPremisa(int elemento){
        this.getPremsisa().add(elemento); 
    }
    
    public void AgregarPremisas (ArrayList <Integer> arreglo){
        this.setPremsisa(arreglo);
    }
    // Agrega el elemento a la conclusion
    public void AgregarConclusion(int elemento){
        this.getConclusion().add(elemento);
    }
     // Agrega el itemset a la conclusion
    public void AgregarConclusiones (ArrayList <Integer> arreglo){
        this.setConclusion(arreglo);
    }

@Override
    public int compareTo(Regla o) {
        if (!(this.isBandera())){
        if (this.getConfianza()> o.getConfianza()){
        return -1;
        }
        if (this.getConfianza()< o.getConfianza()){
        return 1;
        }
        return 0;
        } else {
           if (this.getSoporte()> o.getSoporte()){
        return -1;
        }
        if (this.getSoporte()< o.getSoporte()){
        return 1;
        } 
        return 0;
        }
    }

}
