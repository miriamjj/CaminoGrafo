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

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class GraphTest {

    class Vertex {
        int x;
        int y;

        public Vertex(int x, int y){
            this.x=x;
            this.y=y;
        }

        @Override
        public boolean equals(Object o){

            if (o == this) {
                return true;
            }

            if (!(o instanceof Vertex)) {
                return false;
            }

            Vertex v = (Vertex) o;

            return this.x==v.x && this.y==v.y;
        }

        @Override
        public String toString(){
            return "(" + this.x + ", " + this.y + ")";
        }
    }

    private Graph<Integer> g;
    private Graph<Vertex> gV;

    @Before
    public void setup(){
        g = new Graph<>();
        gV = new Graph<>();
    }

    /**
     * Este test comprueba que el grafo se ha creado correctamente.
     */
    @Test
    public void graphExists(){
        assertNotNull(this.g);
        assertNotNull(this.gV);
    }

    /**
     * Este test comprueba que el vértice se ha añadido correctamente.
     */
    @Test
    public void addsAVertex(){
        g.addVertex(1);
        Vertex v = new Vertex(0,0);
        gV.addVertex(v);

        assertTrue(this.g.containsVertex(1));
        assertTrue(this.gV.containsVertex(v));

        String grafoG = "1: []\n";
        assertEquals(grafoG, g.toString());
        String grafoGV = "(0, 0): []\n";
        assertEquals(grafoGV, gV.toString());
    }

    /**
     * Este test comprueba que se obtienen los vertices adjacentes correctamente.
     */
    @Test
    public void correctlyObtainsAdjacents(){
        g.addVertex(2);
        g.addVertex(3);
        g.addVertex(4);
        g.addVertex(5);
        Vertex v1 = new Vertex(1,0);
        Vertex v2 = new Vertex(2,1);
        Vertex v3 = new Vertex(3,4);
        Vertex v4 = new Vertex(5,2);
        Vertex v5 = new Vertex(8,1);
        gV.addVertex(v1);
        gV.addVertex(v2);
        gV.addVertex(v3);
        gV.addVertex(v4);
        gV.addVertex(v5);
        g.addEdge(2,3);
        g.addEdge(2,4);
        g.addEdge(4,5);
        gV.addEdge(v1,v2);
        gV.addEdge(v3,v5);
        gV.addEdge(v4,v2);
        gV.addEdge(v2,v3);

        try{
            Set<Integer> adjacents2 = new HashSet<>();
            adjacents2.add(3);
            adjacents2.add(4);
            assertEquals(this.g.obtainAdjacents(2),adjacents2);
            Set<Integer> adjacents4 = new HashSet<>();
            adjacents4.add(5);
            assertEquals(this.g.obtainAdjacents(4),adjacents4);

            Set<Vertex> adjacentsv1 = new HashSet<>();
            adjacentsv1.add(v2);
            assertEquals(this.gV.obtainAdjacents(v1),adjacentsv1);
            Set<Vertex> adjacentsv2 = new HashSet<>();
            adjacentsv2.add(v3);
            assertEquals(this.gV.obtainAdjacents(v2),adjacentsv2);
            Set<Vertex> adjacentsv3 = new HashSet<>();
            adjacentsv3.add(v5);
            assertEquals(this.gV.obtainAdjacents(v3),adjacentsv3);
            Set<Vertex> adjacentsv4 = new HashSet<>();
            adjacentsv4.add(v2);
            assertEquals(this.gV.obtainAdjacents(v4),adjacentsv4);

            String grafoG = "2: [3, 4]\n3: []\n4: [5]\n5: []\n";
            assertEquals(grafoG, g.toString());
        } catch(Exception e){
            fail("No existe el vértice en cuestión");
        }

    }

    /**
     * Este test comprueba que salta la excepción correctamente cuando no existe un vértice en el método para obtener
     * los adjacentes.
     */
    @Test(expected = Exception.class)
    public void ExcepcionDeVertices() throws Exception{
        this.g.obtainAdjacents(1);
    }

    /**
     * Este test comprueba que se añade el arco entre los vértices.
     */
    @Test
    public void addsEdge(){
        g.addEdge(1, 2);
        Vertex v1 = new Vertex(0,0);
        Vertex v2 = new Vertex(1,1);
        gV.addEdge(v1,v2);

        try {
            assertTrue(this.g.obtainAdjacents(1).contains(2));
            assertTrue(this.gV.obtainAdjacents(v1).contains(v2));

            String grafoG = "1: [2]\n2: []\n";
            assertEquals(grafoG, g.toString());
            String grafoGV = "(0, 0): [(1, 1)]\n(1, 1): []\n";
            assertEquals(grafoGV, gV.toString());
        } catch (Exception e){
            e.printStackTrace();
            fail("No se ha añadido el vertice correctamente.");
        }

    }

    /**
     * Este test comprueba que el toString funciona correctamente
     */
    @Test
    public void correctlyPrintsToString(){
        g.addEdge(1,3);
        g.addEdge(2,4);
        g.addEdge(3,1);
        g.addEdge(6,5);
        String grafo = "1: [3]\n2: [4]\n3: [1]\n4: []\n5: []\n6: [5]\n";
        assertEquals(grafo, g.toString());
    }


    /**
     * Este test comprueba que el método ‘onePath(V v1, V v2)‘
     * encuentra un camino entre ‘v1‘ y ‘v2‘ cuando existe.
     */
    @Test
    public void onePathFindsAPath() {
        System.out.println("\nTest onePathFindsAPath");
        System.out.println("----------------------");
// Se construye el grafo.
        g.addEdge(1, 2);
        g.addEdge(3, 4);
        g.addEdge(1, 5);
        g.addEdge(5, 6);
        g.addEdge(6, 4);
// Se construye el camino esperado.
        List<Integer> expectedPath = new ArrayList<>();
        expectedPath.add(1);
        expectedPath.add(5);
        expectedPath.add(6);
        expectedPath.add(4);
//Se comprueba si el camino devuelto es igual al esperado.
        assertEquals(expectedPath, g.onePath(1, 4));

    }

    /**
     * Este test comprueba que si no existe camino entre dos vértices devuelve null
     *
     */
    @Test
    public void esNull(){
        g.addEdge(1, 2);
        g.addEdge(3, 4);
        g.addEdge(1, 5);
        g.addEdge(5, 6);
        g.addEdge(6, 4);

        assertEquals(this.g.onePath(1,3),null);
    }


}
