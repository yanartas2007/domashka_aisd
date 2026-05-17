import java.util.*;

public class BFS {
    private final int verticesCount;
    private final List<List<Integer>> adj;

    public BFS(int v) {
        this.verticesCount = v;
        this.adj = new ArrayList<>(v);
        for (int i = 0; i < v; i++) {
            this.adj.add(new ArrayList<>());
        }
    }

    //Добавления ребра между вершинами
    public void addEdge(int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u); // Для неориентированного графа
    }

    //Удаление вершины
    public void removeEdge(int u, int v) {
        adj.get(u).remove(Integer.valueOf(v));
        adj.get(v).remove(Integer.valueOf(u));
    }

    //Просто обход графа, вернет нам список вершин в порядке обхода
    public List<Integer> bfs(int startNode) {
        boolean[] visited = new boolean[verticesCount];
        Queue<Integer> queue = new ArrayDeque<>();
        List<Integer> result = new ArrayList<>();

        visited[startNode] = true;
        queue.offer(startNode);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            result.add(current);

            for (int neighbor : adj.get(current)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }

        return result;
    }

    //Поиск пути, вернет true, если такой путь есть
    public boolean searchPath(int start, int end) {
        if (start == end) return true;

        boolean[] visited = new boolean[verticesCount];
        Queue<Integer> queue = new ArrayDeque<>();

        visited[start] = true;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int neighbor : adj.get(current)) {
                if (neighbor == end) {
                    return true;
                }
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }

        return false;
    }

    //Помогает получить кол-во вершин
    public int getVerticesCount() {
        return verticesCount;
    }
    //Дает нам список смежности
    public List<List<Integer>> getAdjacencyList() {
        return adj;
    }
}