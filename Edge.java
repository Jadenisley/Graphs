package Graphs;

public class Edge {
    private final int from;
    private final int to;
    private final double weight;

    public Edge(int from, int to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public int getFrom() {
        return from; //Возвращает приходящих вершины
    }

    public int getTo() {
        return to; //Возвращает исходящие вершины
    }

    public double getWeight() {
        return weight; //возращает вес петли
    }

    @Override
    public String toString() {
        return from + " -> " + to + " (" + weight + ")"; //приходящая -> исходящая (Вес)
    }
}


