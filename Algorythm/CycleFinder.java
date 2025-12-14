package Algorythm;

import Graphs.Edge;
import Graphs.Graph;

public class CycleFinder {
    public static class Result {
        private final int CountCycles;
        private final int LoopsCount;
        private final long NanoTime;

        public Result(int CountCycles, int LoopsCount, long NanoTime) {
            this.CountCycles = CountCycles;
            this.LoopsCount = LoopsCount;
            this.NanoTime = NanoTime;
        }

        public int getCountCycles() {
            return CountCycles; // Возвращает количество циклов
        }

        public int getLoopsCount() {
            return LoopsCount; // Возвращает количество петель
        }

        public long getNanoTime() {
            return NanoTime; // Возращает время, потраченное на вычисления петель (в наносекундах)
        }
    }

    private void DFS(int v, Graph graph, boolean[] checked, boolean[] inStack, int[] CounterCycles) { // Depth First Search
        checked[v] = true; // Посещение вершины
        inStack[v] = true; // Вершина прямо сейчас находится на обходе

        for (Edge e : graph.getOutEdges(v)) {
            int to = e.getTo(); // Получаем нынешнюю исходящую вершину

            if (!checked[to]) { // Если вершина не была посещена хотя бы один раз, возвращаемся обратно откуда мы начали и посещаем вершину
                DFS(to, graph, checked, inStack, CounterCycles);
            } else if (inStack[to]) { // Если вершина посещена, засчитываем её
                CounterCycles[0]++;
            }
        }
        inStack[v] = false; // Ложна, если по DFS мы возвращаемся обратно, чтобы посетить непосещенные вершины
    }

    public Result FindCycles(Graph graph) {
        int n = graph.getCountVertex(); // Получаем все вершины в графе
        boolean[] checked = new boolean[n];
        boolean[] inStack = new boolean[n];

        int[] CounterCycles = {0};
        int[] LoopsCounter = {0};

        long start = System.nanoTime();

        for (int i= 0; i < n; i++) {
            for (Edge e : graph.getOutEdges(i)) {
                if (e.getFrom() == e.getTo()) {
                    LoopsCounter[0]++;
                }
            }
        }

        for (int i= 0; i < n; i++) {
            if (!checked[i]) { // Если вершина не посещена, откатываемся назад и посещяем её
                DFS(i, graph, checked, inStack, CounterCycles);
            }
        }

        long end = System.nanoTime();
        return new Result(CounterCycles[0], LoopsCounter[0], end-start); // Возвращаем результат пройденных вершин, циклов и кол-во затраченного времени
    }
}





