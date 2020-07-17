
package Algoritmo.ItemsetsFrecuentes;

import java.util.ArrayList;


public class Item implements Comparable<Item> {

    private Integer Producto; // Producto que representa la item
    private ArrayList<ItemSet> itemsets; // Conjunto de Sub Patrones
    private Integer Frecuencia; // Frecuencia del producto
    private ArrayList<ItemSet> itemFrecuentes; // Conjunto de productos que tienen una frecuencia mayor al Item y superan el soporte mínimo
    private ArrayList<ItemSet> itemFrecuentesFinal; // Conjunto de itemsets utilizados para generar las respectivas reglas
    private int bandera;

    //Getters y Setters
    public ArrayList<ItemSet> getItemFrecuentesFinal() {
        return itemFrecuentesFinal;
    }

    public void setItemFrecuentesFinal(ArrayList<ItemSet> itemFrecuentesFinal) {
        this.itemFrecuentesFinal = itemFrecuentesFinal;
    }
    
    
    public Integer getProducto() {
        return Producto;
    }

    public ArrayList<ItemSet> getItemFrecuentes() {
        return itemFrecuentes;
    }

    public void setItemFrecuentes(ArrayList<ItemSet> itemFrecuentes) {
        this.itemFrecuentes = itemFrecuentes;
    }

    public Integer getFrecuencia() {
        return Frecuencia;
    }

    public void setFrecuencia(Integer Frecuencia) {
        this.Frecuencia = Frecuencia;
    }

    public void setProducto(Integer Producto) {
        this.Producto = Producto;
    }

    public ArrayList<ItemSet> getItemsets() {
        return itemsets;
    }

    public void setItemsets(ArrayList<ItemSet> itemsets) {
        this.itemsets = itemsets;
    }

    //Constructor de la clase
    public Item(int produc) {
        this.setProducto(produc);
        this.setItemsets(new ArrayList<ItemSet>());
        this.setItemFrecuentes(new ArrayList<ItemSet>());
        this.setItemFrecuentesFinal(new ArrayList<ItemSet>());
        this.setFrecuencia(1);
        this.setBandera(0);
        
    }

    public int getBandera() {
        return bandera;
    }

    public void setBandera(int bandera) {
        this.bandera = bandera;
    }

    //Aumenta la frecuencia del sub patron condicional que posee el producto (item)
    public void aumentarfrec(ItemSet itemset) {

        if (this.getItemsets().size() == 0) {
            itemset.setFrecuencia(1);
            this.getItemsets().add(itemset);
        } else {
            boolean bandera = this.tieneItem(itemset);
            if (bandera == false) {
                itemset.setFrecuencia(1);
                this.getItemsets().add(itemset);
            } else {
                for (int i = 0; i < this.getItemsets().size(); i++) {
                    boolean band = this.getItemsets().get(i).igual(itemset.getElementos());
                    if (band == true) {
                           this.getItemsets().get(i).setFrecuencia(this.getItemsets().get(i).getFrecuencia()+1);
                           break;
                    }
                }
            }
        }
    }

    // Pregunta si tiene el nuevo itemset que se quiere agregar
    public boolean tieneItem(ItemSet itemset) {

        for (int i = 0; i < this.getItemsets().size(); i++) {
            boolean bandera = this.getItemsets().get(i).igual(itemset.getElementos());
            if (bandera == true) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(Item i) {
        if (this.getFrecuencia() > i.getFrecuencia()) {
            return -1;
        }
        if (this.getFrecuencia() < i.getFrecuencia()) {
            return 1;
        }
        return 0;
    }

}
