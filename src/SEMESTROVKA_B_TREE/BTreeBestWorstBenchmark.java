package SEMESTROVKA_B_TREE;

import java.io.*;
import java.util.*;

public class BTreeBestWorstBenchmark {

    // Настройки бенчмарка
    private static final int[] SIZES = {100, 500, 1000, 2000, 5000, 10000};
    private static final int RUNS = 10;           // повторений для усреднения
    private static final int T = 3;               // степень B-дерева
    private static final String OUTPUT_FILE = "results.csv";

    private static final Random RAND = new Random(42); // фиксированный seed

    public static void main(String[] args) throws IOException {
        System.out.println("Starting B-Tree Best/Worst Case Benchmark...");

        try (PrintWriter csv = new PrintWriter(new FileWriter(OUTPUT_FILE))) {
            // Заголовок CSV
            csv.println("case_type,size,operation,avg_time_ns,std_dev_ns,min_time_ns,max_time_ns");

            for (int size : SIZES) {
                System.out.println("Testing size: " + size);

                // === BEST CASE ===
                benchmarkBestCase(csv, size);

                // === WORST CASE ===
                benchmarkWorstCase(csv, size);
            }
        }
        System.out.println("Done! Results saved to " + OUTPUT_FILE);
    }

    // ==================== BEST CASE ====================
    private static void benchmarkBestCase(PrintWriter csv, int size) throws IOException {
        // Лучший случай для поиска: ищем ключ, который в корне
        // Лучший случай для вставки: вставляем в узлы, где много места
        // Лучший случай для удаления: удаляем из листа без слияний

        List<Long> insertTimes = new ArrayList<>();
        List<Long> searchTimes = new ArrayList<>();
        List<Long> deleteTimes = new ArrayList<>();

        for (int run = 0; run < RUNS; run++) {
            BTree<Integer, String> tree = new BTree<>(T);

            // Генерируем данные для BEST CASE:
            // Вставляем ключи ПОРЯДКОМ, чтобы минимизировать расщепления
            List<Integer> keys = generateBestCaseKeys(size);

            // --- INSERT ---
            long start = System.nanoTime();
            for (int key : keys) {
                tree.insert(key, "val" + key);
            }
            long insertTime = System.nanoTime() - start;
            insertTimes.add(insertTime);

            // --- SEARCH (best: ключ в корне) ---
            Integer rootKey = keys.get(keys.size() / 2); // примерно середина
            start = System.nanoTime();
            for (int i = 0; i < 100; i++) { // 100 поисков для точности
                tree.search(rootKey);
            }
            long searchTime = (System.nanoTime() - start) / 100;
            searchTimes.add(searchTime);

            // --- DELETE (best: из листа, без слияний) ---
            // Удаляем ключи в обратном порядке (листья)
            List<Integer> deleteKeys = new ArrayList<>(keys);
            Collections.reverse(deleteKeys);
            start = System.nanoTime();
            for (int key : deleteKeys) {
                tree.delete(key);
            }
            long deleteTime = System.nanoTime() - start;
            deleteTimes.add(deleteTime);
        }

        // Запись результатов в CSV
        writeResults(csv, "best", size, insertTimes, searchTimes, deleteTimes);
    }

    // ==================== WORST CASE ====================
    private static void benchmarkWorstCase(PrintWriter csv, int size) throws IOException {
        List<Long> insertTimes = new ArrayList<>();
        List<Long> searchTimes = new ArrayList<>();
        List<Long> deleteTimes = new ArrayList<>();

        for (int run =  0; run < RUNS; run++) {
            BTree<Integer, String> tree = new BTree<>(T);

            // Генерируем данные для WORST CASE:
            // Вставляем ключи в порядке, вызывающем максимальные расщепления
            List<Integer> keys = generateWorstCaseKeys(size);

            // --- INSERT ---
            long start = System.nanoTime();
            for (int key : keys) {
                tree.insert(key, "val" + key);
            }
            long insertTime = System.nanoTime() - start;
            insertTimes.add(insertTime);

            // --- SEARCH (worst: ключ не найден, идём до листа) ---
            int missingKey = -1; // гарантированно отсутствует
            start = System.nanoTime();
            for (int i = 0; i < 100; i++) {
                tree.search(missingKey);
            }
            long searchTime = (System.nanoTime() - start) / 100;
            searchTimes.add(searchTime);

            // --- DELETE (worst: вызывает слияния) ---
            // Удаляем ключи, которые заставляют дерево балансироваться
            List<Integer> deleteKeys = new ArrayList<>(keys);
            Collections.shuffle(deleteKeys, RAND); // случайный порядок = больше слияний
            start = System.nanoTime();
            for (int key : deleteKeys) {
                tree.delete(key);
            }
            long deleteTime = System.nanoTime() - start;
            deleteTimes.add(deleteTime);
        }

        writeResults(csv, "worst", size, insertTimes, searchTimes, deleteTimes);
    }

    // ==================== ГЕНЕРАТОРЫ ДАННЫХ ====================

    // Лучший случай: ключи в отсортированном порядке
    // (минимум расщеплений при вставке в B-дерево)
    private static List<Integer> generateBestCaseKeys(int size) {
        List<Integer> keys = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            keys.add(i); // 0, 1, 2, 3...
        }
        return keys;
    }

    // Худший случай: ключи в порядке, вызывающем частые расщепления
    // (чередование малых и больших значений)
    private static List<Integer> generateWorstCaseKeys(int size) {
        List<Integer> keys = new ArrayList<>(size);
        int low = 0, high = size * 2;
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0) {
                keys.add(low++);
            } else {
                keys.add(high--);
            }
        }
        Collections.shuffle(keys, RAND); // добавляем случайности для реального худшего случая
        return keys;
    }

    // ==================== ЗАПИСЬ В CSV ====================
    private static void writeResults(PrintWriter csv, String caseType, int size,
                                     List<Long> insert, List<Long> search, List<Long> delete) {
        csv.printf("%s,%d,insert,%d,%d,%d,%d%n",
                caseType, size, avg(insert), stdDev(insert), min(insert), max(insert));
        csv.printf("%s,%d,search,%d,%d,%d,%d%n",
                caseType, size, avg(search), stdDev(search), min(search), max(search));
        csv.printf("%s,%d,delete,%d,%d,%d,%d%n",
                caseType, size, avg(delete), stdDev(delete), min(delete), max(delete));
        csv.flush();
    }

    // ==================== СТАТИСТИКА ====================
    private static long avg(List<Long> data) {
        return data.stream().mapToLong(Long::longValue).sum() / data.size();
    }

    private static long stdDev(List<Long> data) {
        long mean = avg(data);
        double variance = data.stream()
                .mapToDouble(v -> Math.pow(v - mean, 2))
                .average().orElse(0);
        return (long) Math.sqrt(variance);
    }

    private static long min(List<Long> data) {
        return Collections.min(data);
    }

    private static long max(List<Long> data) {
        return Collections.max(data);
    }
}