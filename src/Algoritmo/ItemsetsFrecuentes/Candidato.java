/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritmo.ItemsetsFrecuentes;

import java.util.ArrayList;
import java.util.Collections;

public class Candidato {
    private ArrayList<Item> Item; // Conjunto de items 
    private ArrayList <Item> ItemNO;

    public ArrayList<Item> getItemNO() {
        return ItemNO;
    }

    public void setItemNO(ArrayList<Item> ItemNO) {
        this.ItemNO = ItemNO;
    }
  
    // Constructor de la clase
    public Candidato(){ 
      this.setItem(new ArrayList<Item>());
      this.setItemNO(new ArrayList<Item> ());
   }

    public ArrayList<Item> getItem() {
        return Item;
    }

    public void setItem(ArrayList<Item> Item) {
        this.Item = Item;
    }
       
    public void AgregarItem (Item item){    
    this.getItem().add(item);
    }
    
    // Recorre su lista de itemsets y valida que no exista el nuevo itemsets que se quiere agregar al conjunto
    public boolean Existe (String elemento){
       Boolean bandera;
       bandera = false;  
       for (int i=0; i< this.getItem().size();i++){
           Integer item= this.getItem().get(i).getProducto();
          if ((Integer.parseInt(elemento)== item)){
           bandera= true;
           return bandera;
          }    
       }
       return bandera;
    }
    
    // Busca en el conjunto al itemset para aumentar la frecuencia en 1
    public void SumarFrec (String elemento){
        for (int i=0; i< this.getItem().size();i++){
            if (Integer.parseInt(elemento)== this.getItem().get(i).getProducto()){
                    this.getItem().get(i).setFrecuencia(this.getItem().get(i).getFrecuencia()+1);
                    break;
               }
        }
    }
    
    public void ordenar() {
        Collections.sort(this.getItem());
    }
    
    public boolean lotiene( ArrayList<Integer> Cadena, int producto){
                for (int y=0; y < Cadena.size();y++){                    
                    if((Cadena.get(y)) == producto){
                        return true;
                    } 
                }
      return false;
    }
}
