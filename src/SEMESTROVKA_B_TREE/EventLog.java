package SEMESTROVKA_B_TREE;

import java.util.ArrayList;
import java.util.List;

public class EventLog {
    private BTree<Long, String> tree; // ключ = timestamp (миллисекунды), значение = сообщение

    public EventLog(int degree) {
        tree = new BTree<>(degree);
    }

    // Добавить событие в лог
    public void addEvent(String message) {
        long timestamp = System.currentTimeMillis();
        tree.insert(timestamp, message);
        // Если нужна уникальность по времени + сообщению, можно добавить счётчик
    }

    // Найти событие по точному времени (возвращает null, если нет)
    public String getEventAt(long timestamp) {
        return tree.search(timestamp);
    }

    // Получить все события в интервале [from, to] (включительно)
    public List<String> getEventsInRange(long from, long to) {
        List<String> result = new ArrayList<>();
        // Для диапазонного запроса нужно уметь обходить дерево.
        // Простейший способ: получить все ключи в диапазоне (но B-дерево не хранит список всех ключей).
        // Реализуем рекурсивный обход (добавим вспомогательный метод в BTree или используем существующий print).
        // Можно добавить в класс BTree метод rangeSearch, но для простоты сделаем обход здесь.
        rangeSearch(tree.root, from, to, result);
        return result;
    }

    // Рекурсивный обход дерева (предполагаем, что root доступен? Нужно сделать поле root публичным или добавить метод).
    // Упростим: добавим в класс BTree метод getRoot() (или сделаем поле package-private).
    // Но для чистоты давайте добавим в BTree метод для диапазонного поиска.
    // Ниже я покажу, как это сделать прямо в EventLog, если открыть доступ к корню.
    // Альтернатива: написать итератор по дереву.

    // Временно используем "костыль": добавим в класс BTree метод public Node<K,V> getRoot() { return root; }
    // Тогда здесь можно написать:
    private void rangeSearch(Node<Long, String> node, long from, long to, List<String> result) {
        if (node == null) return;
        int i = 0;
        while (i < node.keys.size() && node.keys.get(i) < from) {
            i++;
        }
        // Обрабатываем детей слева
        if (!node.leaf) {
            if (i > 0) rangeSearch(node.children.get(i-1), from, to, result);
        }
        while (i < node.keys.size() && node.keys.get(i) <= to) {
            result.add(node.values.get(i));
            if (!node.leaf) {
                rangeSearch(node.children.get(i), from, to, result);
            }
            i++;
        }
        if (!node.leaf && i < node.children.size()) {
            rangeSearch(node.children.get(i), from, to, result);
        }
    }

    // Удалить все события старше заданной метки
    public void deleteEventsOlderThan(long timestamp) {
        // Удалять по одному неэффективно. Можно реализовать метод deleteRange.
        // Для простоты удаляем в цикле, но это O(k log n). При большом k можно оптимизировать.
        List<Long> keysToDelete = new ArrayList<>();
        collectKeysOlderThan(tree.root, timestamp, keysToDelete);
        for (Long key : keysToDelete) {
            tree.delete(key);
        }
    }

    private void collectKeysOlderThan(Node<Long, String> node, long timestamp, List<Long> keys) {
        if (node == null) return;
        int i = 0;
        while (i < node.keys.size() && node.keys.get(i) < timestamp) {
            keys.add(node.keys.get(i));
            i++;
        }
        if (!node.leaf) {
            for (int j = 0; j <= i; j++) {
                collectKeysOlderThan(node.children.get(j), timestamp, keys);
            }
        }
    }

    // Демонстрация работы
    public static void main(String[] args) throws InterruptedException {
        EventLog log = new EventLog(3);
        log.addEvent("Старт системы");
        Thread.sleep(10);
        log.addEvent("Пользователь вошёл");
        Thread.sleep(10);
        log.addEvent("Ошибка подключения");
        Thread.sleep(10);
        log.addEvent("Отправка отчёта");
        long now = System.currentTimeMillis();
        long fiveSecondsAgo = now - 5000;
        List<String> recent = log.getEventsInRange(fiveSecondsAgo, now);
        System.out.println("События за последние 5 секунд:");
        recent.forEach(System.out::println);

        // Удалим всё, кроме последнего события
        log.deleteEventsOlderThan(now - 5);
        System.out.println("После очистки:");
        recent = log.getEventsInRange(0, Long.MAX_VALUE);
        recent.forEach(System.out::println);
    }
}