package SEMESTROVKA_B_TREE;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class BTreeBenchmark {

    // Настройки
    private static final int[] SIZES = {100, 200, 500, 1000, 2000, 5000, 10000};
    private static final int DATASETS_PER_SIZE = 5;   // количество наборов каждого типа для каждого размера
    private static final int RUNS_PER_DATASET = 5;    // число повторных измерений для одного набора
    private static final int T = 3;                   // степень B-дерева

    // Параметры для генерации строк
    private static final int STRING_LENGTH = 10;
    private static final String STRING_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final Random random = new Random(42); // фиксированный seed для воспроизводимости
    private static final String DATASETS_DIR = "datasets";

    public static void main(String[] args) throws IOException {
        // 1. Создать директорию для наборов, если её нет
        Files.createDirectories(Paths.get(DATASETS_DIR));

        // 2. Сгенерировать наборы, если они ещё не существуют
        generateDatasetsIfNeeded();

        // 3. Выполнить бенчмарк
        runBenchmark();
    }

    private static void generateDatasetsIfNeeded() throws IOException {
        // Для каждого размера и типа проверяем, все ли файлы существуют
        boolean needGenerate = false;
        for (int size : SIZES) {
            for (int i = 0; i < DATASETS_PER_SIZE; i++) {
                Path intFile = getDatasetPath("integers", size, i);
                Path strFile = getDatasetPath("strings", size, i);
                if (!Files.exists(intFile) || !Files.exists(strFile)) {
                    needGenerate = true;
                    break;
                }
            }
        }
        if (!needGenerate) {
            System.out.println("Datasets already exist. Skipping generation.");
            return;
        }

        System.out.println("Generating datasets...");
        for (int size : SIZES) {
            for (int i = 0; i < DATASETS_PER_SIZE; i++) {
                generateIntegerDataset(size, i);
                generateStringDataset(size, i);
            }
        }
        System.out.println("Datasets generated.");
    }

    private static Path getDatasetPath(String type, int size, int index) {
        return Paths.get(DATASETS_DIR, type, String.format("size_%d_%d.csv", size, index));
    }

    private static void generateIntegerDataset(int size, int index) throws IOException {
        // Генерируем уникальные случайные числа
        Set<Integer> unique = new HashSet<>();
        while (unique.size() < size) {
            unique.add(random.nextInt(size * 10)); // диапазон больше размера для разнообразия
        }
        List<Integer> keys = new ArrayList<>(unique);
        Collections.shuffle(keys, random);

        // Сохраняем в CSV (по одному числу в строке)
        Path path = getDatasetPath("integers", size, index);
        Files.createDirectories(path.getParent());
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (Integer key : keys) {
                writer.write(key.toString());
                writer.newLine();
            }
        }
    }

    private static void generateStringDataset(int size, int index) throws IOException {
        Set<String> unique = new HashSet<>();
        while (unique.size() < size) {
            unique.add(randomString(STRING_LENGTH));
        }
        List<String> keys = new ArrayList<>(unique);
        Collections.shuffle(keys, random);

        Path path = getDatasetPath("strings", size, index);
        Files.createDirectories(path.getParent());
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (String key : keys) {
                writer.write(key);
                writer.newLine();
            }
        }
    }

    private static String randomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(STRING_CHARS.charAt(random.nextInt(STRING_CHARS.length())));
        }
        return sb.toString();
    }

    private static List<String> loadKeys(Path path) throws IOException {
        return Files.readAllLines(path);
    }

    private static void runBenchmark() throws IOException {
        // Выводим заголовок CSV в консоль
        System.out.println("dataset_type,size,dataset_index,run,operation,time_ns");

        for (int size : SIZES) {
            for (int i = 0; i < DATASETS_PER_SIZE; i++) {
                // Набор целых чисел
                Path intPath = getDatasetPath("integers", size, i);
                if (Files.exists(intPath)) {
                    List<String> strKeys = loadKeys(intPath);
                    List<Integer> intKeys = strKeys.stream().map(Integer::parseInt).collect(Collectors.toList());
                    runForDataset("integers", size, i, intKeys);
                }

                // Набор строк
                Path strPath = getDatasetPath("strings", size, i);
                if (Files.exists(strPath)) {
                    List<String> strKeys = loadKeys(strPath);
                    runForDataset("strings", size, i, strKeys);
                }
            }
        }
    }

    private static <K extends Comparable<K>> void runForDataset(String type, int size, int index, List<K> keys) {
        for (int run = 1; run <= RUNS_PER_DATASET; run++) {
            // 1. Вставка
            BTree<K, String> tree = new BTree<>(T);
            long start = System.nanoTime();
            for (K key : keys) {
                tree.insert(key, "val" + key);
            }
            long insertTime = System.nanoTime() - start;

            // 2. Поиск
            start = System.nanoTime();
            for (K key : keys) {
                tree.search(key);
            }
            long searchTime = System.nanoTime() - start;

            // 3. Удаление
            start = System.nanoTime();
            for (K key : keys) {
                tree.delete(key);
            }
            long deleteTime = System.nanoTime() - start;

            // Вывод результатов
            System.out.printf("%s,%d,%d,%d,insert,%d%n", type, size, index, run, insertTime);
            System.out.printf("%s,%d,%d,%d,search,%d%n", type, size, index, run, searchTime);
            System.out.printf("%s,%d,%d,%d,delete,%d%n", type, size, index, run, deleteTime);
        }
    }
}