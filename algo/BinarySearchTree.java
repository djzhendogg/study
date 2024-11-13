import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BinarySearchTree {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
                int n = Integer.parseInt(reader.readLine());
                String line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line, " ");
                Vertex tree = null;
                for (int i = 1; i <= n; i++) {
                    int x = Integer.parseInt(tokenizer.nextToken());
                    tree = insert(tree, x, i);
                }
                Map<Integer, Vertex> map = new TreeMap<>();
                map = rememberAll(tree, 1, map);
                System.out.println(n);
                if (tree != null) {
                    print(tree);
                }
                System.out.println(n);
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    public static Map<Integer, Vertex> rememberAll(Vertex root, int k, Map<Integer, Vertex> map) {
        if (root == null) {
            return map;
        }
        root.id = k + sizeOf(root.left);
        map.put(root.id, root);
        rememberAll(root.left, k, map);
        rememberAll(root.right, k + sizeOf(root.left) + 1, map);
        return map;
    }

    public static void print(Vertex tree) {
        StringBuilder str = new StringBuilder(Integer.toString(tree.key()));
        str.append(" ");
        if (tree.left == null) {
            str.append("-1");
        } else {
            str.append(tree.left.id);
            print(tree.left);
        }
        str.append(" ");
        if (tree.right == null) {
            str.append("-1");
        } else {
            str.append(tree.right.id);
            print(tree.right);
        }
        System.out.println(str);

    }
    public static Vertex merge(Vertex root1, Vertex root2) {
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }
        if (root1.priority() < root2.priority()) {
            root1.right = merge(root1.right, root2);
            return resize(root1);
        } else {
            root2.left = merge(root1, root2.left);
            return resize(root2);
        }
    }
    public static List<Vertex> split(Vertex root, int x) {
        List<Vertex> arr = new ArrayList<>();
        if (root == null) {
            arr.add(null);
            arr.add(null);
            return arr;
        }
        if (root.key() < x) {
            List<Vertex> vertexList = split(root.right, x);
            root.right = vertexList.get(0);
            root = resize(root);
            arr.add(root);
            arr.add(vertexList.get(1));
            return arr;
        } else {
            List<Vertex> vertexList = split(root.left, x);
            root.left = vertexList.get(1);
            root = resize(root);
            arr.add(vertexList.get(0));
            arr.add(root);
            return arr;
        }
    }

    public static Vertex insert(Vertex root, int x, int id) {
        List<Vertex> ab = split(root, x);
        List<Vertex> cd = split(ab.get(1), x + 1);
        int rand = (int) (100 + (Math.random() * (1000 - 100)));
        Vertex c = new Vertex(x, rand, id, null, null);
        return merge(ab.getFirst(), merge(c, cd.get(1)));
    }

    public static Vertex remove(Vertex root, int x) {
        List<Vertex> ab = split(root, x);
        List<Vertex> cd = split(ab.get(1), x);
        return merge(ab.getFirst(), cd.getLast());
    }
    public static int sizeOf(Vertex root) {
        if (root != null) {
            return root.size;
        } else {
            return 0;
        }
    }
    public static Vertex resize(Vertex root) {
        if (root != null) {
            root.size = sizeOf(root.left) + sizeOf(root.right) + 1;
        }
        return root;
    }
    public static class Vertex {
        private final int key;
        private final int priority;
        protected int size;
        protected int id;
        protected Vertex left;
        protected Vertex right;

        public Vertex(int key, int priority, int size, Vertex left, Vertex right) {
            this.key = key;
            this.priority = priority;
            this.size = size;
            this.left = left;
            this.right = right;
        }

        public int key() {
            return key;
        }

        public int priority() {
            return priority;
        }

    }
}
