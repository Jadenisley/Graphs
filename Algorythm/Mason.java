package Algorythm;

import Graphs.Edge;
import Graphs.Graph;

import java.util.ArrayList;
import java.util.List;

public class Mason {
    public static class MasonResult {
        private final double Transfer; // T
        private final double Delta; // Дельта
        private final List<Double> Path; // Pk
        private final long NanoTime;

        public MasonResult(double Transfer, double Delta, List<Double> Path, long NanoTime) {
            this.Transfer = Transfer;
            this.Delta = Delta;
            this.Path = Path;
            this.NanoTime = NanoTime;
        }

        public double getTransfer() {
            return Transfer; // Возвращает значение Т
        }

        public double getDelta() {
            return Delta; // Возвращает дельту
        }

        public List<Double> getPath() {
            return Path; // Возращает список чисел, каждое число Pk
        }

        public long getNanoTime() {
            return NanoTime; // Время в наносекундах
        }
    }

    private void DFsPath(Graph graph, int Current, int Target, boolean[] checked, double CurrentGain, List<Double> PathGains, int Depth, int MaxDepth) {
        if (Depth > MaxDepth) {
            return; // Защита от длинных путей
        }

        if (Current == Target) {
            PathGains.add(CurrentGain);
            return;
        }

        checked[Current] = true;

        for (Edge e : graph.getOutEdges(Current)) {
            int to = e.getTo();

            if (!checked[to]) {
                DFsPath(graph, to, Target, checked, CurrentGain * e.getWeight(), PathGains, Depth + 1, MaxDepth);
            }
        }
        checked[Current] = false; // Меняем на false, чтобы искать другие пути
    }

    public MasonResult Calculate(Graph graph, int From, int To) {
        int n = graph.getCountVertex();

        if (From < 0 || From >= n || To >= n) { // Если исходный номер вершины < 0 или больше количества или больше выходной петли
            throw new IllegalArgumentException("Неправильные номера вершин источник или приемника");
        }

        long start = System.nanoTime();

        List<Double> PathGains = new ArrayList<>();
        boolean[] checked = new boolean[n];
        DFsPath(graph, From, To, checked, 1, PathGains, 0, n + 2); // Ограничиваем длинные пути

        double SumLoops = 0;
        for (int i= 0; i < n; i++) {
            for (Edge e : graph.getOutEdges(i)) {
                if (e.getFrom() == e.getTo()) {
                    SumLoops += e.getWeight();
                }
            }
        }

        double Delta = 1 - SumLoops; // Упрощенное, считаем только одиночные петли
        double Numerator = 0;
        for (double p : PathGains) {
            Numerator += p;
        }

        double Transfer = PathGains.isEmpty() ? 0 : Numerator/Delta;

        long end = System.nanoTime();

        return new MasonResult(Transfer, Delta, PathGains, end - start);
    }
}





