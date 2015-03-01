

public class Arbol
{
  private boolean division = false; // variable para saber si el arreglo (Nodo) fue dividido con exito
  private boolean encontrado = false; // variable para saber si el numero fue encontrado
  protected Arbol.Node raiz; // Nodo Raiz del arbol 234
  private Integer profundidad; // un numero entero para ir almacenando la profundidad del arbol
  
  public Arbol()
  {
    raiz = CrearNodo(); // creamos un nuevo nodo lol :v
  }
  

  public boolean insertar(int numero) throws NodoExcepciones // ya sabes Exceptions personalizadas
  {
    if ((encontrado) && (buscar(numero))) {  // no se inserta numeros iguales te retorna false :v
      return false;
    }
    if ((division) && (raiz.numeros == 3))// si el nodo ya esta lleno, creo un nuevo nodo para almacenar nuevos numeros
    {
      raiz.alturaArbol = true;
      Arbol.Node nodoAuxilar = CrearNodo();
      nodoAuxilar.hijos[0] = raiz; // el hijo sera la raiz
      raiz.padre = nodoAuxilar;// el nuevo nodo padre sera el nuevo nodo creado
      raiz = nodoAuxilar;
      nodoAuxilar.hijos[0].dividirNodo(); 
    }
    boolean bool = raiz.insertarNumero(numero);
    mantenimiento();
   return bool;
  }
  
  public boolean buscar(int numero)
  {
    return raiz.buscarNumero(numero);
  }
  
  public boolean borrar(int numero) throws NodoExcepciones
  {
    if ((encontrado) && (!buscar(numero))) { // si el numero no es encontrado te retorna false
      return false;
    }
    boolean bool = raiz.borrarNumero(numero);
    mantenimiento(); // este metodo sirve para inicializar la profundidad y llamar al metodo chequeoArbol
    return bool;
  }
  
  private void mantenimiento() throws NodoExcepciones
  {
    try
    {
      profundidad = null; 
      chequeoArbol(); // este metodo sirve reorganizar el arbol despues de haberse borrado un numero
    }
    catch (NodoExcepciones ex)
    {
      
      throw ex;
    }
  }
  
  private void chequeoArbol() throws NodoExcepciones
  {
    if (raiz.padre != null) {
      throw new NodoExcepciones("el padre debe ser null");
    }
    raiz.chequeoArbol(0); // si el padre es null, insertamos un cero al arbol
  }
  
 
  public void imprimir()
  {
    raiz.imprimirArbol();
  }
  
  
  
  protected Arbol.Node CrearNodo()
  {
    return new Arbol.Node(); // creamos un nuevo nodo
  }
  
  private class Node
  {
    protected int numeros; // los numeros que vamos a ingresar
    protected int[] valoresNodo; // es donde vamos a guardar los numeros
    protected Node padre; // nodo padre
    protected Node[] hijos; // es donde vamos a guardar los numeros que van hacer hijos
    protected boolean alturaArbol = false;
    
    public Node()
    {
      numeros = 0; 
      valoresNodo = new int[3]; // cada nodo tiene un maximo de 3 numeros
      padre = null;
      hijos = new Node[4]; // cuando el arbol este lleno osea con 3 numeros, el numero maximo de hijos podra ser de 4
                            // ojo cada elemento del arreglo podra tener hasta 3 numeros
    }
    
    public boolean insertarNumero(int numero) throws NodoExcepciones
    {
      alturaArbol = true;

      try
      {
    
        if (hijos[0] == null) 
        {
          if (numeros == 3)
          {
            if ((valoresNodo[0] == numero) || (valoresNodo[1] == numero) || (valoresNodo[2] == numero)) // no se inserta numeros repetidos
            {
              alturaArbol = false;
              return false;
            }
            return dividirNodo().insertarNumero(numero); // si el nodo es igual a 3 , se debe dividir el arreglo
          }
          for (int i = 0; i < numeros; i++)
          {
            if (valoresNodo[i] == numero) // es solo una comprobacion q no se repite el numero ingresado
            {
               alturaArbol = false;
              return false;
            }
            if (numero < valoresNodo[i])
            {
              for (int k = numeros; k > i; k--) {
                valoresNodo[k] = valoresNodo[(k - 1)];
              }
                valoresNodo[i] = numero;
              numeros += 1;
              return true;
            }
          }
              valoresNodo[(numeros++)] = numero;
          return true;
        }
         alturaArbol = false;
        for (int j = 0; j < numeros; j++)
        {
          if (numero == valoresNodo[j]) {
            return false;
          }
          if (numero < valoresNodo[j])
          {
            if ((Arbol.this.division) && (hijos[j].numeros == 3)) {
              return hijos[j].dividirNodo().insertarNumero(numero);
            }
            return hijos[j].insertarNumero(numero);
          }
        }
      
        if ((Arbol.this.division) && (hijos[numeros].numeros == 3)) {
          return hijos[numeros].dividirNodo().insertarNumero(numero);
        }
        return hijos[numeros].insertarNumero(numero);
      }
      finally
      {
        if (alturaArbol)
        {
          alturaArbol = false;
        }
      }
    }
    
    private Node dividirNodo() throws NodoExcepciones
    {
        Node nodoAuxilar;
      if (numeros != 3) {
        throw new NodoExcepciones("error el nodo tiene 3 elementos");
      }
      if (padre == null)
      {
        nodoAuxilar = Arbol.this.CrearNodo();
        nodoAuxilar.hijos[0] = this;
        padre = nodoAuxilar;
        Arbol.this.raiz = nodoAuxilar;
      }
      padre.alturaArbol = true;
      alturaArbol = true;
     
      if (padre.numeros == 3)
      {
        padre.dividirNodo(); // si el padre tiene 3 numeros en el nodo, hay que dividir el arreglo
    
      }
      padre.alturaArbol = false;
      alturaArbol = false;
      
      nodoAuxilar = Arbol.this.CrearNodo();
      nodoAuxilar.numeros = 1;
      nodoAuxilar.valoresNodo = new int[3];
      nodoAuxilar.valoresNodo[0] = valoresNodo[2];
      nodoAuxilar.padre = padre;
      nodoAuxilar.hijos = new Node[4];
      if (hijos[2] != null)
      {
        nodoAuxilar.hijos[0] = hijos[2];
        nodoAuxilar.hijos[0].padre = nodoAuxilar;
        nodoAuxilar.hijos[1] = hijos[3];
        nodoAuxilar.hijos[1].padre = nodoAuxilar;
      }
        numeros = 1;  
       Node temporal = null;
        hijos[3] = temporal;
        hijos[2] = temporal;
        padre.Meter(valoresNodo[1], nodoAuxilar); // con el metodo meter, vamos metiendo otra vez los numeros sacados
      return padre;
    }
    
    private void Meter(int numero, Node nodito) throws NodoExcepciones 
    {
      int i = numeros;
      while ((i != 0) && (numero < valoresNodo[(i - 1)]))
      {
         valoresNodo[i] = valoresNodo[(i - 1)];
        hijos[(i + 1)] = hijos[i];
        i--;
      }
      numeros += 1;
      valoresNodo[i] = numero;
      hijos[(i + 1)] = nodito;
      nodito.padre = this;
    }

    public void imprimirArbol()
    {
    
      System.out.print("Cantidad de numeros en el nodo (" + numeros + "): " );
      for (int i = 0; i < numeros; i++) {
        System.out.print(valoresNodo[i] + " ");
      }
     
      
      System.out.println();
      if (hijos[0] != null) {
        hijos[0].imprimirArbol();
      }
       
      if (hijos[1] != null) {
            hijos[1].imprimirArbol();
      }
     if (hijos[2] != null) {
           hijos[2].imprimirArbol();
      }
      if (hijos[3] != null) {
            hijos[3].imprimirArbol();
     }
              
    }
    
    public boolean buscarNumero(int numero)
    {
      alturaArbol = true;
      alturaArbol = false;
      try
      {
        for (int i = 0; i < numeros; i++)
        {
          if (numero == valoresNodo[i]) {
            return true; // si el numero se encuentra en el arreglo retorna true 
          }
          if (numero < valoresNodo[i])
          {
            if (hijos[i] == null) { // retorna false si los hijos stan vacios
              return false;
            }
            return hijos[i].buscarNumero(numero); // llamada recursiva
          }
        }
        if (hijos[numeros] == null) { 
          return false;
        }
        return hijos[numeros].buscarNumero(numero); // llamada recursiva
      }
      finally
      {
        alturaArbol = false;
      }
    }
    
    private void UnirNodos(int numero) throws NodoExcepciones
    {
      if ((numeros == 1) && (this != Arbol.this.raiz)) {
        throw new NodoExcepciones("no hay suficiente valores para el nodo!");
      }
      Node localNode1 = hijos[numero];
      Node localNode2 = hijos[(numero + 1)];
      if (localNode1.numeros != 1) {
        throw new NodoExcepciones("demasiado valores en los hijos --> error!");
      }
      if (localNode2.numeros != 1) {
        throw new NodoExcepciones("demasiado valores en los hijos --> error!");
      }
      localNode1.alturaArbol = true;
      localNode2.alturaArbol = true;
    

      localNode1.valoresNodo[1] = valoresNodo[numero];
      localNode1.valoresNodo[2] = localNode2.valoresNodo[0];
      localNode1.numeros = 3;
      for (int i = numero; i < numeros - 1; i++)
      {
         valoresNodo[i] = valoresNodo[(i + 1)];
        hijos[(i + 1)] = hijos[(i + 2)];
      }
        hijos[numeros] = null;
      numeros -= 1;
      if (localNode1.hijos[0] != null)
      {
        localNode1.hijos[2] = localNode2.hijos[0];
        localNode1.hijos[2].padre = localNode1;
        localNode1.hijos[3] = localNode2.hijos[1];
        localNode1.hijos[3].padre = localNode1;
      }
     
      localNode1.alturaArbol = false;
      if ((this == Arbol.this.raiz) && (numeros == 0))
      {
        Arbol.this.raiz = localNode1;
        Arbol.this.raiz.padre = null;
        Arbol.this.raiz.alturaArbol = true;
      
      }
    }
    
    private Node mantenerValores(int numero, Node nodo1, Node nodo2, Node nodo3)
      throws NodoExcepciones
    {
      if (nodo2.numeros != 1) {
        return nodo2;
      }
      nodo2.alturaArbol = true;
      int i = 1;
      if ((nodo1 != null) && (nodo1.numeros > 1))
      {
        i = 1;
        nodo1.alturaArbol = true;
        numero--;
      }
      else if ((nodo3 != null) && (nodo3.numeros > 1))
      {
        i = 0;
        nodo3.alturaArbol = true;
      }
      else
      {
        if (nodo1 != null)
        {
          UnirNodos(numero - 1);
          return nodo1;
        }
        if (nodo3 != null)
        {
          UnirNodos(numero);
          return nodo2;
        }
        throw new NodoExcepciones("error !");
      }
     
      if (i != 0)
      {
        nodo2.valoresNodo[1] = nodo2.valoresNodo[0];
        nodo2.valoresNodo[0] = valoresNodo[numero];
        valoresNodo[numero] = nodo1.valoresNodo[(nodo1.numeros - 1)];
        if (nodo2.hijos[0] != null)
        {
          nodo2.hijos[2] = nodo2.hijos[1];
          nodo2.hijos[1] = nodo2.hijos[0];
          nodo2.hijos[0] = nodo1.hijos[nodo1.numeros];
          nodo1.hijos[nodo1.numeros] = null;
          nodo2.hijos[0].padre = nodo2;
        }
        nodo1.numeros -= 1;
        nodo2.numeros = 2;
      }
      else
      {
        nodo2.valoresNodo[1] = valoresNodo[numero];
        valoresNodo[numero] = nodo3.valoresNodo[0];
        if (nodo2.hijos[0] != null)
        {
          nodo2.hijos[2] = nodo3.hijos[0];
          nodo2.hijos[2].padre = nodo2;
        }
        for (int j = 0; j < nodo3.numeros - 1; j++)
        {
          nodo3.valoresNodo[j] = nodo3.valoresNodo[(j + 1)];
          nodo3.hijos[j] = nodo3.hijos[(j + 1)];
        }
        nodo3.hijos[(nodo3.numeros - 1)] = nodo3.hijos[nodo3.numeros];
        nodo3.hijos[nodo3.numeros] = null;
        nodo3.numeros -= 1;
        

        nodo2.numeros = 2;
      }
      
      if (nodo1 != null) {
        nodo1.alturaArbol = false;
      }
      if (nodo3 != null) {
        nodo3.alturaArbol = false;
      }
      nodo3.alturaArbol = false;
      return nodo3;
    }
    
    public int DeleteMax()
      throws NodoExcepciones
    {
      if (numeros == 1) {
        throw new NodoExcepciones("no hay como borrar!");
      }
          alturaArbol = true;
      
      try
      {
        int j;
        if (hijos[0] != null)
        {
          Node nodoAuxiliar1 = hijos[numeros];
          nodoAuxiliar1.alturaArbol = true;
         
          if (nodoAuxiliar1.numeros == 1)
          {
            Node nodoAuxiliar2 = hijos[(numeros - 1)];
            nodoAuxiliar2.alturaArbol = true;
           
            if (nodoAuxiliar2.numeros == 1) {
              UnirNodos(numeros - 1);
            } else {
              mantenerValores(numeros, hijos[(numeros - 1)], hijos[numeros], null);
            }
            return hijos[numeros].DeleteMax();
          }
          return nodoAuxiliar1.DeleteMax();
        }
        int i = valoresNodo[(--numeros)];
       
        return i;
      }
      finally
      {
         alturaArbol = false;
      }
    }
    
    public int DeleteMin()
      throws NodoExcepciones
    {
      if (numeros == 1) {
        throw new NodoExcepciones("no hay como borrar!");
      }
         alturaArbol = true;
     
      try
      {
        if (hijos[0] != null)
        {
          Node nodoAuxiliar1 = hijos[0];
          nodoAuxiliar1.alturaArbol = true;
         
          if (nodoAuxiliar1.numeros == 1)
          {
            Node nodoAuxilar2 = hijos[1];
            nodoAuxilar2.alturaArbol = true;
          
            if (nodoAuxilar2.numeros == 1) {
              UnirNodos(0);
            } else {
              mantenerValores(0, null, hijos[0], hijos[1]);
            }
            return hijos[0].DeleteMin();
          }
          return nodoAuxiliar1.DeleteMin();
        }
        int i = valoresNodo[0];
        for (int j = 0; j < numeros - 1; j++) {
          valoresNodo[j] = valoresNodo[(j + 1)];
        }
         numeros -= 1;
       
        return i;
      }
      finally
      {
         alturaArbol = false;
      }
    }
    
    public boolean borrarNumero(int numero) throws NodoExcepciones
    {
      if (numeros == 0) {
        return false;
      }
          alturaArbol = true;
     
      boolean bool1 = false;
      int i = 0;
      Object miObjeto = null;
      Node nodoAuxiliar1 = null;
      Node nodoAuxiliar2 = null;
      try
      {
        for (; i < numeros; i++)
        {
          miObjeto = nodoAuxiliar1;
          nodoAuxiliar1 = hijos[i];
          nodoAuxiliar2 = hijos[(i + 1)];
          if (numero == valoresNodo[i]) {
            bool1 = true;
          } else {
            if (numero < valoresNodo[i]) {
              break;
            }
          }
        }
        if (i == numeros)
        {
          miObjeto = hijos[(i - 1)];
          nodoAuxiliar1 = hijos[i];
          nodoAuxiliar2 = null;
        }
        boolean bool2;
        if (hijos[0] == null)
        {
          if (bool1)
          {
            for (; i < numeros - 1; i++) {
              valoresNodo[i] = valoresNodo[(i + 1)];
            }
               numeros -= 1;
           
          }
          return bool1;
        }
        if (bool1)
        {
          if (nodoAuxiliar1.numeros > 1)
          {
            valoresNodo[i] = nodoAuxiliar1.DeleteMax();
           
            return true;
          }
          if (nodoAuxiliar2.numeros > 1)
          {
             valoresNodo[i] = nodoAuxiliar2.DeleteMin();
           
            return true;
          }
          UnirNodos(i);
           alturaArbol = false;
          return nodoAuxiliar1.borrarNumero(numero);
        }
        Node nodoAuxiliar3 = mantenerValores(i, (Node)miObjeto, nodoAuxiliar1, nodoAuxiliar2);
        

        alturaArbol = false;
        return nodoAuxiliar3.borrarNumero(numero);
      }
      finally
      {
        alturaArbol = false;
      }
    }
    
    public void chequeoArbol(int numero) throws NodoExcepciones
    {
      for (int i = 0; i <= numeros; i++) {
        if (hijos[i] != null)
        {
          if (hijos[i].padre != this) {
            throw new NodoExcepciones(this + " error coño :v!");
          }
             hijos[i].chequeoArbol(numero + 1);
        }
        else if (Arbol.this.profundidad == null)
        {
          if (i != 0) {
            throw new NodoExcepciones(this + " problemas con el primer nodito!");
          }
          Arbol.this.profundidad = new Integer(numero);
        }
        else if (Arbol.this.profundidad.intValue() != numero)
        {
          throw new NodoExcepciones(this + " la profundidad no es igual al numero");
        }
      }
      for (int i = numeros + 1; i < 4; i++) {
        if (hijos[i] != null) {
          throw new NodoExcepciones(this + " los hijos deberian ser nulos coño");
        }
      }
    }
  }
}
