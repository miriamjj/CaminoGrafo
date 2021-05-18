/*
Copyright 2021 Miriam Jimenez

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License
*/

package pr2;
import java.util.*;

public class Graph<V> {
    //Lista de adyacencia.
    private Map<V, Set<V>> adjacencyList = new HashMap<>();

    /******************************************************************
     * Añade el vértice ‘v‘ al grafo.
     * Complejidad temporal O(1)
     * Complejidad espacial O(1)
     * @param v vértice a añadir.
     * @return ‘true‘ si no estaba anteriormente y ‘false‘ en caso contrario.
     ******************************************************************/
    public boolean addVertex(V v) {
        if (adjacencyList.containsKey(v)) {
            return false;
        }
        adjacencyList.put(v, new HashSet<V>()); //Añadimos el vértice y un conjunto de vecinos vacío
        return true;
    }

    /******************************************************************
     * Añade un arco entre los vértices ‘v1‘ y ‘v2‘ al grafo. En caso de que no exista alguno de los vértices, lo añade
     * también.
     * Complejidad temporal O(1)
     * Complejidad espacial O(1)
     * @param v1 el origen del arco.
     * @param v2 el destino del arco.
     * @return ‘true‘ si no existía el arco y ‘false‘ en caso contrario.
     ******************************************************************/
    public boolean addEdge(V v1, V v2) {
        if (!adjacencyList.containsKey(v1)) {
            adjacencyList.put(v1, new HashSet<V>()); //Añadimos el vértice y un conjunto de vecinos vacío
        }
        if (!adjacencyList.containsKey(v2)) {
            adjacencyList.put(v2, new HashSet<V>()); //Añadimos el vértice y un conjunto de vecinos vacío
        }
        Set<V> vecinosV1 = adjacencyList.get(v1); // Devuelve el conjunto de vértices vecinos a v1
        //vecinosV1 es de tipo Set<V>

        if (vecinosV1.contains(v2)) { //Comprobar si existe arco entre v1 y v2
            return false;
        }
        vecinosV1.add(v2); //Añadimos v2 al conjunto de vecinos de v1
        return true;
    }

    /******************************************************************
     * Obtiene el conjunto de vértices adyacentes a ‘v‘.
     * Complejidad temporal O(1)
     * Complejidad espacial O(1)
     * @param v vértice del que se obtienen los adyacentes.
     * @return conjunto de vértices adyacentes.
     ******************************************************************/
    public Set<V> obtainAdjacents(V v) throws Exception {
        Set<V> adyacentes = adjacencyList.get(v); //Devuelve el conjunto de vertices adjacentes de v
        if (adyacentes!=null) return adyacentes;
        else throw new Exception("No existe el vertice en cuestión.");
    }

    /******************************************************************
     * Comprueba si el grafo contiene el vértice dado.
     * Complejidad temporal O(1)
     * Complejidad espacial O(1)
     * @param v vértice para el que se realiza la comprobación.
     * @return ‘true‘ si ‘v‘ es un vértice del grafo.
     ******************************************************************/
    public boolean containsVertex(V v) {
        return adjacencyList.containsKey(v); // Comprueba si la lista de adjacencias contiene v
    }

    /******************************************************************
     * Método ‘toString()‘ reescrito para la clase ‘Grafo.java‘.
     * Complejidad temporal O(n) constante
     * Complejidad espacial O(1)
     * @return una cadena de caracteres con la lista de adyacencia.
     *
     ******************************************************************/
    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder();
        for (V v : adjacencyList.keySet()) {
            cadena.append(v.toString());
            cadena.append(": ");
            cadena.append(adjacencyList.get(v).toString());
            cadena.append("\n");
        }
        return cadena.toString();
    }

    /******************************************************************
     * Obtiene, en caso de que exista, un camino entre ‘v1‘ y ‘v‘. En caso contrario, devuelve ‘null‘.
     * Complejidad temporal O(n^2) cudrática
     * Complejidad espacial O(1)
     * @param v1 el vértice origen.
     * @param v2 el vértice destino.
     * @return lista con la secuencia de vértices desde ‘v1‘ hasta ‘v2‘ pasando por arcos del grafo.
     ******************************************************************/
    public List<V> onePath(V v1, V v2) {
        List<V> camino = new ArrayList<V>();
        List<V> reverse = null; // Da la vuelta a la lista de caminos.
        HashMap<V, V> traza = new HashMap<V, V>(); // Tabla llamada traza que va guardando entre que vertices hay aristas
        LinkedList<V> pila = new LinkedList<V>(); // Pila que contiene los vertices a los que tienes que acceder
        boolean encontrado = false;

        pila.push(v1); // Añade el vertice v1 a la pila
        traza.put(v1, null);
        while (!pila.isEmpty() && !encontrado) {
            V v = pila.pop(); // Va sacando vértices de la pila para comprobar si son v2 (final del camino)
            if (v.equals(v2))
                encontrado = true;
            if (!encontrado) {
                for (V adjacent : adjacencyList.get(v)) // Comprobamos cada vecino de v
                    if (!traza.containsKey(adjacent)) { // Comprobamos que el vecino no esta en la traza
                        pila.push(adjacent); // Añadimos el vecino a la pila
                        traza.put(adjacent, v); // Añadimos de donde procede el vecino, para saber de donde venimos.
                    }
            }
        }
        if (encontrado) { // Si encuentra v2, reconstruye el camino
            V v = v2;
            while (v != null) { // Bucle para encontrar el camino desde la traza
                camino.add(v);
                v = traza.get(v);
            }
            reverse = new ArrayList<V>(camino.size());   // Revierte la lista camino
            for (int i = camino.size() - 1; i >= 0; i--)
                reverse.add(camino.get(i));
        }
        return reverse; //devuelve el camino o null si no lo encuentra.
    }
}
