package Algoritmo;

import Algoritmo.GeneracionReglas.HConsecuente;
import Algoritmo.GeneracionReglas.Regla;
import Algoritmo.ItemsetsFrecuentes.Candidato;
import Algoritmo.ItemsetsFrecuentes.Frecuente;
import Algoritmo.ItemsetsFrecuentes.Item;
import Algoritmo.ItemsetsFrecuentes.ItemSet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Growth {

    private String RutalFinal; // Ruta del archivo
    private Integer MinConf; // Umbral de confianza
    private Integer MinSup; // Umbral de soporte
    private Candidato Candidato; // Candidato que contiene los items
    private ArrayList<Regla> ConjunReglas; // Conjunto de reglas
    private Integer NumTransc; // Numero de transacciones
    private ArrayList<Frecuente> ConjunFrecuentes; // Conjunto de frecuentes

    //Constructor de la clase
    public Growth(String rutaFinal, Integer MiniCon, Integer MiniSup) {
        this.setRutalFinal(rutaFinal);
        this.setMinConf(MiniCon);
        this.setMinSup(MiniSup);
        this.setNumTransc(0);
        this.setConjunReglas(new ArrayList<Regla>());
        this.setConjunFrecuentes(new ArrayList<Frecuente>());
    }
    
    //Getters y Setters
    public String getRutalFinal() {
        return RutalFinal;
    }

    public void setRutalFinal(String RutalFinal) {
        this.RutalFinal = RutalFinal;
    }

    public Integer getMinConf() {
        return MinConf;
    }

    public void setMinConf(Integer MinConf) {
        this.MinConf = MinConf;
    }

    public Integer getMinSup() {
        return MinSup;
    }

    public void setMinSup(Integer MinSup) {
        this.MinSup = MinSup;
    }

    public Candidato getCandidato() {
        return Candidato;
    }

    public void setCandidatos(Candidato candidato) {
        this.Candidato = candidato;
    }

    public ArrayList<Regla> getConjunReglas() {
        return ConjunReglas;
    }

    public void setConjunReglas(ArrayList<Regla> COnjunReglas) {
        this.ConjunReglas = COnjunReglas;
    }

    public Integer getNumTransc() {
        return NumTransc;
    }

    public void setNumTransc(Integer NumTransc) {
        this.NumTransc = NumTransc;
    }

    public ArrayList<Frecuente> getConjunFrecuentes() {
        return ConjunFrecuentes;
    }

    public void setConjunFrecuentes(ArrayList<Frecuente> ConjunFrecuentes) {
        this.ConjunFrecuentes = ConjunFrecuentes;
    }

    // Genera el candidato numero 1 , que contiene los items 
    public Integer ObtenerCantProduc() throws FileNotFoundException, IOException {
        Integer CantProductos = 0;
        Candidato Candidato = new Candidato();
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            archivo = new File(this.getRutalFinal());
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            String linea;
            linea = br.readLine(); // Lectura de la primera linea del archivo
            boolean bandera = false;
            ArrayList<String> cadenaaux = new ArrayList<String>();
            while ((linea) != null) {
                this.setNumTransc(this.getNumTransc() + 1); // Cuenta el numero de transacciones
                String[] cadena = linea.split(" ");
                for (int i = 0; i < cadena.length; i++) {
                    boolean existe = Candidato.Existe(cadena[i]); // Se fija si el candidato no tiene el nuevo item que se quiere agregar

                    for (int x = 0; x < cadenaaux.size(); x++) {
                        if (cadenaaux.get(x).equals(cadena[i])) {
                            bandera = true;
                            break;
                        }
                    }
                    if (existe == false) { // Si no lo tiene
                        CantProductos = CantProductos + 1; // Cuenta la cantidad de productos
                        Item item = new Item(Integer.parseInt(cadena[i])); // Crea un nuevo item
                        Candidato.AgregarItem(item); //Se agrega al candidato
                         cadenaaux.add(cadena[i]);
                    } else if (bandera == false) {  // Si ya lo tiene 
                        cadenaaux.add(cadena[i]); 
                        Candidato.SumarFrec(cadena[i]); // Se busca el item en el candidato y se aumenta la frecuencia en 1
                    }
                    bandera=false;
                }
                cadenaaux.clear();
                linea = br.readLine(); // Lectura de la siguiente linea del archivo
            }
        } catch (FileNotFoundException ex) {
            System.out.print("No encontro el archivo");
        }
        Candidato.ordenar();
        this.setCandidatos(Candidato); // Se setea el atributo candidato con los items y sus respectivas frecuencias cargadas
        br.close();
        return CantProductos;
    }

    //En este metodo se eliminan aquellos items que no cumplen con el mínimo de soporte establecido previamente
    public void GenerarFrecuenteUno() throws IOException {
        float barrera = ((float) (this.getMinSup() * this.getNumTransc()) / (float) 100);
        Candidato candidato = new Candidato(); //Se crea un nuevo candidato para cargar aquellos items que superan el soporte mínimo
        for (int z = 0; z < this.getCandidato().getItem().size(); z++) { // Se recorren los items
            if ((float) this.getCandidato().getItem().get(z).getFrecuencia() >= barrera) {
                Item item = this.getCandidato().getItem().get(z);
                candidato.AgregarItem(item);//Si supera el soporte mínimo se agrega al nuevo candidato el item
            } else {
                candidato.getItemNO().add(this.getCandidato().getItem().get(z));
            }
        }
        this.setCandidatos(candidato);
    }

    
    //En este metodo se generarán todos los itemsets frecuentes finales para la obtención de las reglas
    public void GenerarItemSets() throws FileNotFoundException, IOException {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            archivo = new File(this.getRutalFinal());
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            String linea;
            linea = br.readLine(); // Lectura de la primera linea del archivo
            while ((linea) != null) {
                String[] cadena = linea.split(" ");
                
                // Ordenamos la cadena en función de la frecuencia del item
                ArrayList<Integer> cadena2 = new ArrayList<Integer>();

                for (int x = 0; x < this.getCandidato().getItem().size(); x++) {
                    int prod = this.getCandidato().getItem().get(x).getProducto();
                    for (int c = 0; c < cadena.length; c++) {
                        if ((prod == Integer.parseInt(cadena[c]))) {
                            cadena2.add(prod);
                            break;
                        }
                    }
                }

                //Se cargan los sub patrones de cada item 
                for (int i = 1; i < this.Candidato.getItem().size(); i++) {
                    Integer producto = this.Candidato.getItem().get(i).getProducto();
                    boolean bandera = this.Candidato.lotiene(cadena2, producto);
                    if (bandera == true && (producto != cadena2.get(0))) {
                        ItemSet itemset = new ItemSet();
                        for (int z = 0; z < cadena2.size(); z++) {
                            if (cadena2.get(z) == producto) {
                                break;
                            } else {
                                itemset.getElementos().add(cadena2.get(z)); //Se agrega un elemento al sub patron 
                            }
                        }
                        this.getCandidato().getItem().get(i).aumentarfrec(itemset); //Se agrega el sub patron al conjunto de itemsets del item
                    }
                }
                linea = br.readLine(); // Lectura de la siguiente linea del archivo
            }

            // Ahora se generarán los ItemsFrecuentes, se contabilizan las ocurrencias de cada producto en la lista de sub patrones de cada item , cargandolos a una lista de ItemFrecuentes
            float barrera = ((float) (this.getMinSup() * this.getNumTransc()) / (float) 100);
            for (int z = 1; z < this.getCandidato().getItem().size(); z++) { //Se recorren los items
                ArrayList<ItemSet> arreglo = this.getCandidato().getItem().get(z).getItemsets(); //Se obtienen los sub patrones del item
                float cont = (float) 0;
                for (int d = 0; d < z; d++) { //Se recorren los productos
                    for (int x = 0; x < arreglo.size(); x++) {  //Se recorre cada sub patron
                        for (int c = 0; c < arreglo.get(x).getElementos().size(); c++) {
                            if (this.getCandidato().getItem().get(d).getProducto() == arreglo.get(x).getElementos().get(c)) {
                                cont = (float) arreglo.get(x).getFrecuencia() + cont; //Contabiliza la frecuencia del producto 
                                break;
                            }
                        }
                    }

                    if (cont >= barrera) { //Si supera el mínimo de soporte
                        ItemSet items = new ItemSet(); //Se crea un nuevo itemset
                        items.getElementos().add(this.getCandidato().getItem().get(d).getProducto()); //Se carga el producto contabilizado 
                        items.setFrecuencia((int) cont); //Se setea su frecuencia
                        this.getCandidato().getItem().get(z).getItemFrecuentes().add(items); //Se carga el itemset de un elemento a la lista de ItemsFrecuentes del item
                    }
                    cont = (float) 0;
                }
            }
            
            // Ahora se proceden a generar las reglas con los itemsets de dos elementos
            float minconf = (float) this.getMinConf();
            for (int m = 1; m < this.getCandidato().getItem().size(); m++) {
                if (this.getCandidato().getItem().get(m).getItemFrecuentes().size() > 0) {
                    int frecuencia1 = this.getCandidato().getItem().get(m).getFrecuencia();
                    for (int c = 0; c < this.getCandidato().getItem().get(m).getItemFrecuentes().size(); c++) {
                        ItemSet itemset = new ItemSet();
                        itemset.setFrecuencia(this.getCandidato().getItem().get(m).getItemFrecuentes().get(c).getFrecuencia());
                        itemset.getElementos().add(this.getCandidato().getItem().get(m).getProducto());
                        itemset.getElementos().add(this.getCandidato().getItem().get(m).getItemFrecuentes().get(c).getElementos().get(0));
                        this.getCandidato().getItem().get(m).getItemFrecuentesFinal().add(itemset);//Se carga el itemset de dos elementos a la lista de ItemFrecuentesFinal del item
                        int frecuencia2 = this.getCandidato().getItem().get(m).getItemFrecuentes().get(c).getFrecuencia();
                        float confianza1 = (((float) frecuencia2 / (float) frecuencia1) * 100); //Se calcula la confianza de la regla
                        if (confianza1 >= minconf) {
                            Regla regla = new Regla(); //Se crea una nueva regla
                            regla.AgregarPremisa(this.getCandidato().getItem().get(m).getProducto()); //Se carga la premisa
                            regla.AgregarConclusion(this.getCandidato().getItem().get(m).getItemFrecuentes().get(c).getElementos().get(0)); //Se carga la conclusión
                            regla.setConfianza((int) confianza1); // Se agrega la confianza obtenida
                            regla.setSoporte((int) ((frecuencia1 * 100) / this.getNumTransc())); //Se agrega el soporte obtenido
                            this.getConjunReglas().add(regla); //Se agrega la nueva regla al conjunto de reglas de ObGrowth
                        }
                        int frecuencia = 0;
                        for (int u = 0; u < this.getCandidato().getItem().size(); u++) {
                            if (this.getCandidato().getItem().get(u).getProducto() == this.getCandidato().getItem().get(m).getItemFrecuentes().get(c).getElementos().get(0)) {
                                frecuencia = this.getCandidato().getItem().get(u).getFrecuencia();
                            }
                        }
                        float confianza2 = (((float) frecuencia2 / (float) frecuencia) * 100);
                        if (confianza2 >= minconf) {
                            Regla regla = new Regla(); //Se crea una nueva regla
                            regla.AgregarPremisa(this.getCandidato().getItem().get(m).getItemFrecuentes().get(c).getElementos().get(0));//Se carga la premisa
                            regla.AgregarConclusion(this.getCandidato().getItem().get(m).getProducto()); //Se carga la conclusión
                           regla.setConfianza((int) confianza2); // Se agrega la confianza obtenida
                            regla.setSoporte((int) ((frecuencia1 * 100) / this.getNumTransc())); //Se agrega el soporte obtenido
                            this.getConjunReglas().add(regla); //Se agrega la nueva regla al conjunto de reglas de ObGrowth
                        }
                    }
                }
            }

            
            //Se proceden a generar los itemsets finales de mas de dos elementos
            for (int i = 0; i < this.getCandidato().getItem().size(); i++) { //Se recorren los items
                ArrayList<Integer> itemaux = new ArrayList<Integer>(); //Utilizado para cargar aquellos productos que superaron el minimo de soporte y poseen una frecuencia mayor al del item 
                for (int y = 0; y < this.getCandidato().getItem().get(i).getItemFrecuentes().size(); y++) {
                    itemaux.add(this.getCandidato().getItem().get(i).getItemFrecuentes().get(y).getElementos().get(0));
                }
                //Si el tamaño del arreglo es mayor a 1, se genera el itemset de mayor tamaño, es decir el ultimo itemset 
                if (itemaux.size() > 1) {
                    int frecuencia = 0;
                    int cont = 0;
                    int tamanio = itemaux.size();
                    for (int x = 0; x < this.getCandidato().getItem().get(i).getItemsets().size(); x++) {
                        ItemSet items = this.getCandidato().getItem().get(i).getItemsets().get(x);

                        for (int u = 0; u < items.getElementos().size(); u++) {
                            int elemento = items.getElementos().get(u);
                            for (int t = 0; t < itemaux.size(); t++) {
                                if (elemento == itemaux.get(t)) {
                                    cont = cont + 1;
                                    break;
                                }
                            }
                        }

                        if (cont == tamanio) {
                            frecuencia = frecuencia + this.getCandidato().getItem().get(i).getItemsets().get(x).getFrecuencia();
                        }
                        cont = 0;
                    }
                    if (frecuencia >= barrera) { //Si supera el mínimo de soporte
                        ItemSet itemset = new ItemSet(); //Se crea un nuevo itemset
                        itemset.getElementos().add(this.getCandidato().getItem().get(i).getProducto()); //Se carga el producto al itemset
                        for (int j = 0; j < itemaux.size(); j++) {
                            itemset.getElementos().add(itemaux.get(j)); //Se cargan todos los elementos de itemaux
                        }
                        itemset.setFrecuencia(frecuencia); //Se setea la frecuencia
                        this.getCandidato().getItem().get(i).getItemFrecuentesFinal().add(itemset); //Se agrega el itemset al conjunto de ItemFrecuentesFinal
                    }
                }
                //Si el tamaño del arreglo es mayor a dos , se generan los itemsets de 3 elementos
                if (itemaux.size() > 2) {
                    if (this.getCandidato().getItem().get(i).getBandera() == 0) {
                        this.getCandidato().getItem().get(i).setBandera(1);
                        ArrayList<ItemSet> items = new ArrayList<ItemSet>();
                        for (int x = 0; x < this.getCandidato().getItem().get(i).getItemFrecuentesFinal().size(); x++) {
                            if (this.getCandidato().getItem().get(i).getItemFrecuentesFinal().get(x).getElementos().size() == 2) {
                                items.add(this.getCandidato().getItem().get(i).getItemFrecuentesFinal().get(x));
                            }
                        }
                        ArrayList<ItemSet> it = new ArrayList<ItemSet>();
                        for (int r = 0; r < items.size(); r++) {
                            for (int v = r + 1; v < items.size(); v++) {
                                ItemSet ite = new ItemSet();
                                ite.getElementos().add(this.getCandidato().getItem().get(i).getProducto());
                                ite.getElementos().add(items.get(r).getElementos().get(1));
                                ite.getElementos().add(items.get(v).getElementos().get(1));
                                it.add(ite);
                            }
                        }
                        int contador = 0;
                        float frecuencia = (float) 0;

                        for (int g = 0; g < it.size(); g++) {
                            ItemSet its = it.get(g);
                            for (int r = 0; r < this.getCandidato().getItem().get(i).getItemsets().size(); r++) {
                                for (int f = 1; f < its.getElementos().size(); f++) {
                                    int cont = 0;
                                    int elemt = its.getElementos().get(f);
                                    for (int b = 0; b < this.getCandidato().getItem().get(i).getItemsets().get(r).getElementos().size(); b++) {
                                        if (elemt == this.getCandidato().getItem().get(i).getItemsets().get(r).getElementos().get(b)) {
                                            cont = cont + 1;
                                            break;
                                        }
                                    }
                                    if (cont > 0) {
                                        contador = contador + 1;
                                    }
                                }
                                if (contador == it.get(g).getElementos().size() - 1) {
                                    frecuencia = (float) this.getCandidato().getItem().get(i).getItemsets().get(r).getFrecuencia() + frecuencia; //Se contabiliza la frecuencia del itemset de tres elementos generado
                                }
                                contador = 0;
                            }
                            if (frecuencia >= barrera) { //Si supera el mínimo de soporte
                                its.setFrecuencia((int) frecuencia); //Se setea la frecuencia
                                this.getCandidato().getItem().get(i).getItemFrecuentesFinal().add(its); //Se carga al conjunto de ItemFrecuentesFinal
                            }
                            frecuencia = (float) 0;
                        }
                        //Si el tamaño es mayor a 3 se generan las combinaciones restantes,es decir los itemsets de 4,5,... N-1 elementos , ya que el ultimo fue generado arriba
                        // Las combinaciones se realizan de la misma manera que en algoritmo A priori, formando los nuevos itemsets con aquellos que difieran en el ultimo elemento
                        if (itemaux.size() > 3) {
                            int cont = 0;
                            int contador2 = 3;
                            int cont2 = itemaux.size() - 3;
                            while (cont2 > 0) {
                                cont2 = cont2 - 1;
                                ArrayList<ItemSet> item3 = new ArrayList<ItemSet>();
                                for (int x = 0; x < this.getCandidato().getItem().get(i).getItemFrecuentesFinal().size(); x++) {
                                    if (this.getCandidato().getItem().get(i).getItemFrecuentesFinal().get(x).getElementos().size() == contador2) {
                                        item3.add(this.getCandidato().getItem().get(i).getItemFrecuentesFinal().get(x));
                                    }
                                }
                                if (item3.size() > 1) {
                                    for (int e = 0; e < item3.size(); e++) {
                                        ItemSet item1 = item3.get(e);
                                        for (int f = e + 1; f < item3.size(); f++) {
                                            ItemSet item2 = item3.get(f);
                                            int contd = 0;
                                            for (int v = 0; v < item1.getElementos().size() - 1; v++) {
                                                for (int b = 0; b < item2.getElementos().size() - 1; b++) {
                                                    if (item1.getElementos().get(v) == item2.getElementos().get(b)) {
                                                        contd = contd + 1;
                                                        break;
                                                    }
                                                }
                                            }
                                            if (contd == (contador2 - 1)) {
                                                ItemSet itemst = new ItemSet();
                                                for (int g = 0; g < item1.getElementos().size(); g++) {
                                                    itemst.getElementos().add(item1.getElementos().get(g));
                                                }
                                                itemst.getElementos().add(item2.getElementos().get(item2.getElementos().size() - 1));
                                                for (int z = 0; z < this.getCandidato().getItem().get(i).getItemsets().size(); z++) {
                                                    cont = 0;
                                                    for (int x = 1; x < itemst.getElementos().size(); x++) {
                                                        int elt = itemst.getElementos().get(x);
                                                        for (int c = 0; c < this.getCandidato().getItem().get(i).getItemsets().get(z).getElementos().size(); c++) {
                                                            if (this.getCandidato().getItem().get(i).getItemsets().get(z).getElementos().get(c) == elt) {
                                                                cont = cont + 1;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (cont == itemst.getElementos().size() - 1) {
                                                        frecuencia = (float) this.getCandidato().getItem().get(i).getItemsets().get(z).getFrecuencia() + frecuencia;
                                                    }
                                                }
                                                if (frecuencia >= barrera) { //Si el nuevo itemset supera el soporte
                                                    itemst.setFrecuencia((int) frecuencia); //Se setea su frecuencia
                                                    this.getCandidato().getItem().get(i).getItemFrecuentesFinal().add(itemst);// Se carga el nuevo itemset a ItemFrecuenteFinal
                                                }
                                                frecuencia = (float) 0;
                                            }
                                        }
                                    }
                                }
                                contador2 = contador2 + 1;
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException ex) {

        }

    }

    //Modulo de generación de reglas 
    public void generarReglas() {
        float minconf = (float) this.getMinConf();

        //Primeramente cada itemset final que posee cada item , es cargado en el frecuente que corresponde para poder hacer la generacion de reglas.
        // por ejemplo el itemset de dos elementos es cargado en el frecuente dos y asi sucesivamente.
        int conts = 1;
        for (int f = 0; f < this.getCandidato().getItem().size(); f++) {
            for (int g = 0; g < this.getCandidato().getItem().get(f).getItemFrecuentesFinal().size(); g++) {
                if (conts < this.getCandidato().getItem().get(f).getItemFrecuentesFinal().get(g).getElementos().size()) {
                    conts = this.getCandidato().getItem().get(f).getItemFrecuentesFinal().get(g).getElementos().size();
                }
            }
        }
        ArrayList<Frecuente> frecuentes = new ArrayList<Frecuente>(); //Se crea un arreglo de frecuentes
        int contador = 1;
        while (contador <= conts) {
            Frecuente frecun = new Frecuente(contador);
            frecuentes.add(frecun);
            contador = contador + 1;
        }
        //Se carga el frecuente numero 1 con los productos del candidato
        for (int k = 0; k < this.getCandidato().getItem().size(); k++) {
            ItemSet itemset = new ItemSet();
            itemset.getElementos().add(this.getCandidato().getItem().get(k).getProducto());
            itemset.setFrecuencia(this.getCandidato().getItem().get(k).getFrecuencia());
            frecuentes.get(0).getItemsets().add(itemset);
        }
        //Se cargan los demas frecuentes con los itemsets de la lista de ItemFrecuentesFinal de cada Item
        for (int k = 0; k < this.getCandidato().getItem().size(); k++) {
            for (int g = 0; g < this.getCandidato().getItem().get(k).getItemFrecuentesFinal().size(); g++) {
                int tamanio = this.getCandidato().getItem().get(k).getItemFrecuentesFinal().get(g).getElementos().size();
                for (int f = 0; f < frecuentes.size(); f++) {
                    if (tamanio == frecuentes.get(f).getK()) {
                        frecuentes.get(f).getItemsets().add(this.getCandidato().getItem().get(k).getItemFrecuentesFinal().get(g));
                        break;
                    }
                }
            }
        }
        this.setConjunFrecuentes(frecuentes); //Se setea el conjuntoFrecuentes con todos los frecuentes ya cargados

        for (int k = 2; k < this.getConjunFrecuentes().size(); k++) { //Se comienza del frecuente tres, ya que para el frecuente dos las reglas fueron cargadas anteriormente
            Frecuente frecuente = this.getConjunFrecuentes().get(k); //Se obtiene el frecuente K
            Frecuente frecuente2 = this.getConjunFrecuentes().get(k - 1); // Se obtiene el frecuente K-1
            for (int i = 0; i < frecuente.getItemsets().size(); i++) { // Se recorren los itemsets del frecuente
                HConsecuente H = new HConsecuente(1); // Se crea un objeto H1 para guardar los consecuentes
                ItemSet itemset = frecuente.getItemsets().get(i);
                int frecuencia = frecuente.getItemsets().get(i).getFrecuencia();
                for (int x = 0; x < itemset.getElementos().size(); x++) { // Se recorren los elementos del itemset que se va a utilizar para generar sus respectivas reglas
                    int frecuencia1 = 0;
                    ArrayList<Integer> Arreglo = new ArrayList<Integer>(); // Se utiliza para guardar el antecedente de la regla
                    for (int y = 0; y < itemset.getElementos().size(); y++) {

                        if (itemset.getElementos().get(y) != itemset.getElementos().get(x)) {
                            Arreglo.add(itemset.getElementos().get(y));
                            ;
                        }
                    }
                    // Se busca la frecuencia del antecedente utilizando la variable arreglo creada anteriormente
                    for (int l = 0; l < frecuente2.getItemsets().size(); l++) {
                        int cont = 0;
                        for (int m = 0; m < frecuente2.getItemsets().get(l).getElementos().size(); m++) {
                            for (int z = 0; z < Arreglo.size(); z++) {
                                if (Arreglo.get(z) == frecuente2.getItemsets().get(l).getElementos().get(m)) {
                                    cont = cont + 1;
                                    break;
                                }
                            }
                        }
                        if (cont == frecuente2.getItemsets().get(l).getElementos().size()) {
                            frecuencia1 = frecuente2.getItemsets().get(l).getFrecuencia(); // Se obtiene la frecuencia del antecedente
                        }
                    }

                    float confianza = (((float) frecuencia / (float) frecuencia1) * 100);// Se calcula la confianza
                    if (confianza >= minconf) { // Si supera le mínimo de umbral establecido
                        Regla regla = new Regla();//Se crea una nueva regla
                        regla.AgregarConclusion(frecuente.getItemsets().get(i).getElementos().get(x));// Se agrega la conclusión
                        regla.AgregarPremisas(Arreglo); // Se agrega las premisas
                        regla.setConfianza((int) confianza); // Se agrega la confianza obtenida
                        regla.setSoporte((int) ((frecuencia * 100) / this.getNumTransc())); //Se agrega el soporte obtenido
                        this.getConjunReglas().add(regla); // Se agrega la nueva regla al conjunto de reglas de ObGrowth
                        ItemSet itemset1 = new ItemSet();
                        itemset1.AgregarElemento(frecuente.getItemsets().get(i).getElementos().get(x));
                        H.AgregarItemSet(itemset1); // Se agrega un itemset a la lista de itemsets de H1 con el consecuente de la regla creada

                    }
                } // Se sige con la generación de otra regla utilizando el mismo itemset
                apgenRules(itemset, H); // Se llama al metodo apgenRules con el itemset y H1
            }
        }
    }

    // Este metodo genera las reglas con consecuentes mayores a un elemento
    public void apgenRules(ItemSet itemset, HConsecuente Hm) {
        float minconf = (float) this.getMinConf();
        if ((itemset.getElementos().size() > Hm.getIndice() + 1) && (Hm.getItemsets().size() > 0)) {
            Hm = GenerarHm(Hm); // Se genera el H que contiene los consecuentes de tamaño K
            HConsecuente Hmm = new HConsecuente(Hm.getIndice());  // Se utiliza un H auxiliar para guardar los consecuentes cuyas reglas superen el umbral de confianza 
            int frecuencia = itemset.getFrecuencia(); // Se obtiene la frecuencia del itemset
            for (int i = 0; i < Hm.getItemsets().size(); i++) { // Se recoren los consecuentes
                ItemSet itemset2 = new ItemSet();  // Se utiliza para guardar los antencedentes
                for (int m = 0; m < itemset.getElementos().size(); m++) {
                    int cont = 0;
                    for (int l = 0; l < Hm.getItemsets().get(i).getElementos().size(); l++) {
                        if (Hm.getItemsets().get(i).getElementos().get(l) == itemset.getElementos().get(m)) {
                            cont = cont + 1;
                        }
                    }
                    if (cont == 0) { // Si el elemento del itemset no se encuentra en el consecuente entonces se lo guarda como un antecedente
                        itemset2.AgregarElemento(itemset.getElementos().get(m)); // Se obtiene el antecedente de la regla
                    }
                }
                //Buscamos la frecuencia de la premisa o antecedente
                int tamanio = itemset2.getElementos().size();
                int frecuencia1 = 0;
                for (int z = 0; z < this.getConjunFrecuentes().size(); z++) {
                    if (this.getConjunFrecuentes().get(z).getK() == tamanio) {
                        for (int k = 0; k < this.getConjunFrecuentes().get(z).getItemsets().size(); k++) {
                            int cont = 0;
                            ItemSet itemset3 = this.getConjunFrecuentes().get(z).getItemsets().get(k);
                            for (int n = 0; n < itemset3.getElementos().size(); n++) {
                                for (int t = 0; t < itemset2.getElementos().size(); t++) {
                                    if (itemset3.getElementos().get(n) == itemset2.getElementos().get(t)) {
                                        cont = cont + 1;
                                        break;
                                    }
                                }
                            }
                            if (cont == itemset2.getElementos().size()) {
                                frecuencia1 = this.getConjunFrecuentes().get(z).getItemsets().get(k).getFrecuencia(); // Se guarda la frecuencia del antecedente
                            }
                        }
                    }
                }

                //Calculamos Confianza
                float confi = ((float) frecuencia / (float) frecuencia1) * 100;
                if (confi >= minconf) {
                    Regla regla = new Regla(); // Si pasa el umbral establecido se crea una nueva regla
                    regla.AgregarPremisas(itemset2.getElementos()); // Se cargan premisas
                    regla.AgregarConclusiones(Hm.getItemsets().get(i).getElementos()); // Se cargan conclusiones
                    regla.setConfianza((int) confi); // Se agrega la confianza obtenida
                    regla.setSoporte((int) ((frecuencia * 100) / this.getNumTransc())); //Se agrega el soporte obtenido
                    this.getConjunReglas().add(regla); //La nueva regla es cargada al conjunto de reglas 
                    Hmm.AgregarItemSet(Hm.getItemsets().get(i)); //Se agrega al H auxiliar el consecuente utilizado para generar la regla
                }
            }// Se prosige con otro consecuente
            this.apgenRules(itemset, Hmm); // De manera recursiva una vez recorrido todos los consecuente se llama a la función apgenRules con el itemset y el H m+1
        }
    }

    // Metodo que realiza la combinación de los itemsets tomando en consideración de que difieran en el ulitmo elemento
    public HConsecuente GenerarHm(HConsecuente Hm) {
        HConsecuente Hmm = new HConsecuente(Hm.getIndice() + 1);//Se crea un nuevo H
        if (Hm.getIndice() == 1) { //Si H=1 entonces se combina para generar los itemsets de dos elementos
            for (int i = 0; i < Hm.getItemsets().size(); i++) {
                int pos = i + 1;
                for (int y = pos; y < Hm.getItemsets().size(); y++) {
                    ItemSet itemset = new ItemSet();
                    itemset.AgregarElemento(Hm.getItemsets().get(i).getElementos().get(0));
                    itemset.AgregarElemento(Hm.getItemsets().get(y).getElementos().get(0));
                    Hmm.AgregarItemSet(itemset);
                }
            }
        } else { //Si no, se combina para generar los itemsets de mas de dos elementos
            for (int i = 0; i < Hm.getItemsets().size(); i++) {
                int pos = i + 1;
                for (int y = pos; y < Hm.getItemsets().size(); y++) {
                    boolean bandera = Hm.comparar(Hm.getItemsets().get(i), Hm.getItemsets().get(y), Hm.getIndice() - 1);
                    if (bandera == true) {
                        ItemSet itemset = new ItemSet();
                        for (int x = 0; x < Hm.getItemsets().get(i).getElementos().size(); x++) {
                            itemset.AgregarElemento(Hm.getItemsets().get(i).getElementos().get(x));
                        }
                        itemset.AgregarElemento(Hm.getItemsets().get(y).getElementos().get(Hm.getIndice() - 1));
                        Hmm.AgregarItemSet(itemset);
                    }
                }
            }

        }
        return Hmm; // Devuelve los itemsets combinados en un nuevo H
    }
}
