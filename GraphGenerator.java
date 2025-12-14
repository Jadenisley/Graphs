package Algorythm;

import Graphs.Graph;
import java.util.Random;

public class GraphGenerator {
    private final Random random = new Random();

    public Graph Generate(int CountVertex, double EdgeAbility) {
        Graph graph = new Graph(CountVertex);

        for (int from= 0; from < CountVertex; from++) {
            for (int to= 0; to < CountVertex; to++) {
                if (random.nextDouble() < EdgeAbility) {
                    double weight = 0.5 + random.nextDouble() * 2;
                    graph.AddEdge(from, to, weight);
                }
            }
        }
        return graph;
    }
}



