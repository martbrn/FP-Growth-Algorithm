
package Algoritmo.ItemsetsFrecuentes;

import java.util.ArrayList;
import java.util.Collections;

public class ItemSet implements Comparable <ItemSet> {
     private ArrayList<Integer> Elementos; // Conjunto de elementos del itemset
     private Integer Frecuencia; // Frecuencia del itemset
     private String Soporte; // Soporte del itemset

      // Constructor de la clase
     public ItemSet(){
       this.setElementos(new ArrayList<Integer>());
       this.setFrecuencia(0);
        
    }
      // Getters y Setters
     public ArrayList<Integer> getElementos() {
        return Elementos;
    }

    public void setElementos(ArrayList<Integer> Elementos) {
        this.Elementos = Elementos;
    }

    public Integer getFrecuencia() {
        return Frecuencia;
    }

    public void setFrecuencia(Integer Frecuencia) {
        this.Frecuencia = Frecuencia;
    }

    public String getSoporte() {
        return Soporte;
    }

    public void setSoporte(String Soporte) {
        this.Soporte = Soporte;
    }
    
    public void AgregarElemento (Integer elemento){
        this.getElementos().add(elemento);
    }
    
    public void Agregartemset (ItemSet itemset){
        this.setElementos(itemset.getElementos());
    }
    
    public void SumarFrec () {
     this.setFrecuencia(this.getFrecuencia()+1);
    }
    
     // Aumenta la frecuencia del itemset en 1
    public boolean SumarFrecUno (String elemento){
        
       int elem= Integer.parseInt(elemento);
       int item= this.getElementos().get(0);
        
       if (elem == item){
        this.setFrecuencia(this.getFrecuencia()+1);
        return true;
       }
       
       return false;
    }
       
   // Preguna si el arreglo pasado como parametro es igual al itemset
    public boolean igual (ArrayList <Integer> arreglo){
        if (this.getElementos().equals(arreglo)){    
        return true;
        }else {
        return false; }   
    }
    
    @Override
    public int compareTo (ItemSet i){
        if (this.getElementos().get(0)< i.getElementos().get(0)){
            return -1;
        }
         if (this.getElementos().get(0)> i.getElementos().get(0)){
            return 1;
        } 
        return 0;
    }
    
    public void ordenar (){
        Collections.sort(this.getElementos());
    }
}