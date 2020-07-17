
package Algoritmo.GeneracionReglas;

import Algoritmo.ItemsetsFrecuentes.ItemSet;
import java.util.ArrayList;



public class HConsecuente {
    private int indice; // Indice del H
    private ArrayList<ItemSet> Itemsets; // Conjunto de consecuentes

    // Constructor de la clase
    public HConsecuente (int indice) {
    this.setItemsets(new ArrayList<ItemSet>());
    this.setIndice(indice);
    }
     // Getters y Setter de la clase
    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public ArrayList<ItemSet> getItemsets() {
        return Itemsets;
    }

    public void setItemsets(ArrayList<ItemSet> Itemsets) {
        this.Itemsets = Itemsets;
    }
    
    public void AgregarItemSet (ItemSet itemset){
        this.getItemsets().add(itemset);
    }
    
      // Compara si dos itemsets difieren en el ultimo elemento
    public boolean comparar (ItemSet uno, ItemSet dos, int barrera){
     int cont=0;
     while (cont<barrera){ // Itera segun la cantidad de elementos iguales que deberían tener los itemsets
     int item= uno.getElementos().get(cont); // Obtiene la valor que se encuentra en la poscición que indica el contador
     int item2= dos.getElementos().get(cont);
     
     if (item != item2) {
         return false;
     }
     cont=cont + 1;
     }
    return true;
    
    }
}
