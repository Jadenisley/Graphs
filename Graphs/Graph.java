package Graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph {
    private final int CountVertex;
    private final List<List<Edge>> Adj;

    public Graph(int CountVertex) {
        if (CountVertex <= 0) { //Если количество вершин отрицательные, будет выводить ошибку
            throw new IllegalArgumentException("Количество вершин должно быть положительно");
        }

        this.CountVertex = CountVertex;
        this.Adj = new ArrayList<>(CountVertex);

        for (int i= 0; i < CountVertex; i++) {
            Adj.add(new ArrayList<>());
        }
    }

    private void checkVertexIndex(int p) {
        if (p < 0 || p >= CountVertex) { //Индекс вершины не может быть отрицательным или больше количества вершин
            throw new IndexOutOfBoundsException("Неверный индекс вершины:" + p);
        }
    }

    public int getCountVertex() { //Геттер для CountVertex, возвращает количество вершин
        return CountVertex;
    }

    public void AddEdge(int from, int to, double weight) {
        checkVertexIndex(from); //проверка приходящих вершин
        checkVertexIndex(to); //проверка исходящих вершин
        Adj.get(from).add(new Edge(from, to, weight));
    }

    public List<Edge> getOutEdges(int vertex) {
        checkVertexIndex(vertex);
        return Collections.unmodifiableList(Adj.get(vertex));
    }

    public List<List<Edge>> getAdj() {
        return Collections.unmodifiableList(Adj);
    }
}


