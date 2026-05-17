import java.util.*;
import java.io.*;


//Бенчмарк для ЛУЧШЕГО случая BFS

public class BFSBestCaseBenchmark {

    private static Random random = new Random();
    private static PrintWriter csvWriter;

    public static void main(String[] args) {
        try {
            csvWriter = new PrintWriter(new FileWriter("bfs_bestcase_results.csv"));
            csvWriter.println("GraphSize;BestCaseSearchTime_ns;Iterations");

            System.out.println("=== БЕНЧМАРК ЛУЧШЕГО СЛУЧАЯ BFS ===\n");
            System.out.println("Лучший случай: целевая вершина - сосед стартовой\n");

            int[] sizes = {100, 500, 1000, 2000, 3000, 5000, 7000, 10000};
            int testsPerSize = 50;

            for (int size : sizes) {
                System.out.println("Тестирование размера: " + size);
                runBestCaseTests(size, testsPerSize);
            }

            csvWriter.close();
            System.out.println("\nРезультаты сохранены в: bfs_bestcase_results.csv");

        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void runBestCaseTests(int size, int numTests) throws IOException {
        long totalTime = 0;
        long totalIterations = 0;

        for (int test = 0; test < numTests; test++) {
            // Создаем граф
            BFS graph = new BFS(size);
            // Добавляем случайные ребра
            int edgesCount = size * 2;
            for (int i = 0; i < edgesCount; i++) {
                int from = random.nextInt(size);
                int to = random.nextInt(size);
                if (from != to) {
                    graph.addEdge(from, to);
                }
            }
            // ЛУЧШИЙ СЛУЧАЙ: целевая вершина - первый сосед стартовой
            int startNode = 0;
            int targetNode = -1;
            // Находим любого соседа стартовой вершины
            if (!graph.getAdjacencyList().get(startNode).isEmpty()) {
                targetNode = graph.getAdjacencyList().get(startNode).get(0);
            } else {
                // Если нет соседей, создаем искусственно
                targetNode = 1;
                graph.addEdge(startNode, targetNode);
            }
            // Замер времени
            long startTime = System.nanoTime();
            boolean found = bfsBestCase(graph, startNode, targetNode);
            long endTime = System.nanoTime();

            long iterations = countIterationsBestCase(graph, startNode, targetNode);

            totalTime += (endTime - startTime);
            totalIterations += iterations;
        }

        long avgTime = totalTime / numTests;
        long avgIterations = totalIterations / numTests;

        System.out.printf("  Размер: %5d | Время: %6d ns | Итерации: %d%n",
                size, avgTime, avgIterations);

        csvWriter.println(size + ";" + avgTime + ";" + avgIterations);
    }


     //BFS с ранним выходом при нахождении цели

    private static boolean bfsBestCase(BFS graph, int start, int target) {
        if (start == target) return true;

        int n = graph.getVerticesCount();
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new ArrayDeque<>();

        visited[start] = true;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();

            // Проверяем соседей
            for (int neighbor : graph.getAdjacencyList().get(current)) {
                if (neighbor == target) {
                    return true; // Нашли сразу!
                }
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }

        return false;
    }

    //Подсчет итераций для лучшего случая
    private static long countIterationsBestCase(BFS graph, int start, int target) {
        int n = graph.getVerticesCount();
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new ArrayDeque<>();
        long iterations = 0;

        visited[start] = true;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            iterations++;

            for (int neighbor : graph.getAdjacencyList().get(current)) {
                if (neighbor == target) {
                    return iterations; // Вернулись сразу после нахождения
                }
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }

        return iterations;
    }
}