/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytcc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.PrimMinimumSpanningTree;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.ListenableUndirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import scala.concurrent.duration.Deadline;

/**
 *
 * @author Labin-L1
 * @author Marcio Cardoso
 */
public class WsnGraph {

    private static ArrayList<Sensor> wsnVertexSet = new ArrayList<Sensor>();

    private static UndirectedGraph<Sensor, DefaultEdge> wsnGraph
            = new SimpleGraph<Sensor, DefaultEdge>(DefaultEdge.class);

    public static void addVertex(Sensor vertex) {
        wsnGraph.addVertex(vertex);
        wsnVertexSet.add(vertex);
    }

    public static int size() {
        return wsnGraph.vertexSet().size();
    }

    /*método crítico para o função de FITNESS*/
    // recebe uma lista de vértices, monta o 
    // grafo junto com o vértives da WSN e retorna
    // o número de grafos desconexos.
    // Caso entre somente um grafo totalmente conexo,
    // retorna 0;
    public static int numberOfGraphs(ArrayList<Sensor> relayNodeVertexSet) {
        //cria o grafo que será analisado
        UndirectedGraph<Sensor, DefaultEdge> graph = new SimpleGraph<Sensor, DefaultEdge>(DefaultEdge.class);
        ArrayList<Sensor> graphVertexList = new ArrayList<Sensor>();
        PrimMinimumSpanningTree sPanningTree;

        //constrói o grafo
        for (int i = 0; i < WSN.size(); i++) {
            graph.addVertex(WSN.getSensor(i));
            graphVertexList.add(WSN.getSensor(i));
        }
        for (int i = 0; i < relayNodeVertexSet.size(); i++) {
            graph.addVertex(relayNodeVertexSet.get(i));
            graphVertexList.add(relayNodeVertexSet.get(i));
        }

        // insere as arestas
        for (int i = 0; i < graphVertexList.size(); i++) {
            for (int j = 0; j < graphVertexList.size(); j++) {
                if (i != j) {
                    if (graphVertexList.get(i).distanceTo(graphVertexList.get(j)) <= ConstantNumber.SENSOR_RANGE) {
                        graph.addEdge(graphVertexList.get(i), graphVertexList.get(j));
                    }
                }
            }
        }
        //se o grafo não possui arestas,
        //significa que todos os seus vértices estão desconectados.
        //Este é o pior resultado 
        if (graph.edgeSet().size() != 0) {
            sPanningTree = new PrimMinimumSpanningTree(graph);
        } else {
            return graph.vertexSet().size();
        }

        int numberOfSpannigTreeEdges = sPanningTree.getMinimumSpanningTreeEdgeSet().size();

        //o número de componenentes conexas será então
        //igual ao número de vértices menos o número de
        //aretas da spanning tree
        int numberOfGraphs = graphVertexList.size() - numberOfSpannigTreeEdges;

        return numberOfGraphs;
    }
}
