import java.util.*;
import java.io.*;


public class BFSBenchmarks {

    private static Random random = new Random();
    private static PrintWriter csvWriter;
    private static PrintWriter iterationsWriter;

    public static void main(String[] args) {
        try {
            // Инициализация файлов для записи результатов
            csvWriter = new PrintWriter(new FileWriter("bfs_time_results.csv"));
            iterationsWriter = new PrintWriter(new FileWriter("bfs_iterations.csv"));
            // Заголовки CSV файлов
            csvWriter.println("GraphSize;AddTime_ns;RemoveTime_ns;SearchTime_ns;TotalTime_ns");
            iterationsWriter.println("GraphSize;BFSIterations");

            System.out.println("=== ЗАПУСК БЕНЧМАРКОВ BFS ===\n");

            // Размеры графов: от 100 до 10000 вершин
            int[] sizes = {100, 500, 1000, 2000, 3000, 5000, 7000, 10000};
            int testsPerSize = 50; // 50 тестов для каждого размера

            for (int size : sizes) {
                System.out.println("Тестирование размера: " + size + " вершин");
                runTestsForSize(size, testsPerSize);
            }
            csvWriter.close();
            iterationsWriter.close();

            System.out.println("\n=== РЕЗУЛЬТАТЫ СОХРАНЕНЫ ===");
            System.out.println("- bfs_time_results.csv (замеры времени)");
            System.out.println("- bfs_iterations.csv (количество итераций)");

        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
            e.printStackTrace();
        }
    }


    //Запуск тестов для графа заданного размера
    private static void runTestsForSize(int size, int numTests) throws IOException {
        long totalAddTime = 0;
        long totalRemoveTime = 0;
        long totalSearchTime = 0;
        long totalIterations = 0;

        for (int test = 0; test < numTests; test++) {
            // Создаем граф
            BFS graph = new BFS(size);
            // 1. ЗАМЕР ДОБАВЛЕНИЯ
            int u = random.nextInt(size);
            int v = random.nextInt(size);
            if (u != v) {
                long startTime = System.nanoTime();
                graph.addEdge(u, v);
                long endTime = System.nanoTime();
                totalAddTime += (endTime - startTime);
            }
            // 2. ЗАМЕР УДАЛЕНИЯ
            long startRemove = System.nanoTime();
            graph.removeEdge(u, v);
            long endRemove = System.nanoTime();
            totalRemoveTime += (endRemove - startRemove);

            // 3. Подготавливаем граф для поиска (добавляем случайные ребра)
            int edgesCount = size * 2; // В среднем 2 ребра на вершину
            for (int i = 0; i < edgesCount; i++) {
                int from = random.nextInt(size);
                int to = random.nextInt(size);
                if (from != to) {
                    graph.addEdge(from, to);
                }
            }

            // 4. ЗАМЕР ПОИСКА + ПОДСЧЕТ ИТЕРАЦИЙ
            int startNode = 0;
            int endNode = size - 1;

            // Замер времени поиска
            long startSearch = System.nanoTime();
            boolean found = graph.searchPath(startNode, endNode);
            long endSearch = System.nanoTime();
            totalSearchTime += (endSearch - startSearch);

            // Подсчет итераций (приблизительно - количество посещенных вершин)
            long iterations = countBFSIterations(graph, startNode);
            totalIterations += iterations;
        }

        // Вычисляем средние значения
        long avgAddTime = totalAddTime / numTests;
        long avgRemoveTime = totalRemoveTime / numTests;
        long avgSearchTime = totalSearchTime / numTests;
        long totalTime = avgAddTime + avgRemoveTime + avgSearchTime;
        long avgIterations = totalIterations / numTests;

        // Вывод в консоль
        System.out.printf("  Размер: %5d | Add: %6d ns | Remove: %6d ns | " +
                        "Search: %8d ns | Iterations: %6d%n",
                size, avgAddTime, avgRemoveTime, avgSearchTime, avgIterations);

        // Запись в CSV
        csvWriter.println(size + ";" + avgAddTime + ";" + avgRemoveTime + ";" +
                avgSearchTime + ";" + totalTime);
        iterationsWriter.println(size + ";" + avgIterations);
    }

    //Подсчет количества итераций BFS (посещенных вершин)
    private static long countBFSIterations(BFS graph, int startNode) {
        int n = graph.getVerticesCount();
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new ArrayDeque<>();
        long iterations = 0;

        visited[startNode] = true;
        queue.offer(startNode);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            iterations++; // Считаем каждую извлеченную вершину

            for (int neighbor : graph.getAdjacencyList().get(current)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }

        return iterations;
    }

    //Дополнительный метод: демонстрация работы BFS
    public static void demonstrate() {
        System.out.println("=== ДЕМОСТРАЦИЯ РАБОТЫ BFS ===\n");

        // Создаем небольшой граф
        BFS graph = new BFS(6);

        // Добавляем ребра
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 4);
        graph.addEdge(3, 5);
        graph.addEdge(4, 5);

        System.out.println("Граф с 6 вершинами:");
        System.out.println("Рёбра: 0-1, 0-2, 1-3, 1-4, 2-4, 3-5, 4-5\n");

        // Выполняем BFS
        System.out.print("BFS обход начиная с вершины 0: ");
        List<Integer> result = graph.bfs(0);
        for (int vertex : result) {
            System.out.print(vertex + " ");
        }
        System.out.println();
        // Поиск пути
        System.out.println("\nПоиск пути из 0 в 5: " +
                (graph.searchPath(0, 5) ? "найден" : "не найден"));
        System.out.println("Поиск пути из 0 в 3: " +
                (graph.searchPath(0, 3) ? "найден" : "не найден"));
    }
}