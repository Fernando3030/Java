




public class Main {
    public static void main(String [] args) throws NodoExcepciones
    {
        Arbol arbol= new Arbol();
        arbol.insertar(5);
        arbol.insertar(10);
        arbol.insertar(20);
        arbol.insertar(7);
        arbol.insertar(40);
        arbol.insertar(55);
        arbol.insertar(8);
        arbol.insertar(60);
         // arbol.insertar(70);
        arbol.imprimir();
        boolean resul=arbol.buscar(7);
        System.out.println("el numero 7 se encuentra en el arbol? " + resul);
    }
    
}
