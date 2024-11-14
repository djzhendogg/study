import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BstSum {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
                Vertex tree = null;
                String line = reader.readLine();
                while (line != null) {
                    StringTokenizer tokenizer = new StringTokenizer(line, " ");
                    String operation = tokenizer.nextToken();
                    switch (operation) {
                        case "insert": tree = insert(tree, Integer.parseInt(tokenizer.nextToken()));
                            break;
                        case "exists": System.out.println(find(tree, Integer.parseInt(tokenizer.nextToken())));
                            break;
                        case "delete": tree = remove(tree, Integer.parseInt(tokenizer.nextToken()));
                            break;
                        case "next": System.out.println(upperBound(tree, Integer.parseInt(tokenizer.nextToken())));
                            break;
                        case "prev": System.out.println(lowerBound(tree, Integer.parseInt(tokenizer.nextToken())));
                    }
                    line = reader.readLine();
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static Vertex merge(Vertex root1, Vertex root2) {
        push(root1);
        push(root2);
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }
        if (root1.priority() < root2.priority()) {
            root1.right = merge(root1.right, root2);
            return recalculate(root1);
        } else {
            root2.left = merge(root1, root2.left);
            return recalculate(root2);
        }
    }
    public static List<Vertex> split(Vertex root, int x, int offset) {
        push(root);
        List<Vertex> arr = new ArrayList<>();
        if (root == null) {
            arr.add(null);
            arr.add(null);
            return arr;
        }
        if (offset + sizeOf(root.left) < x) {
            List<Vertex> vertexList = split(root.right, x, offset + sizeOf(root.left) + 1);
            root.right = vertexList.get(0);
            arr.add(recalculate(root));
            arr.add(vertexList.get(1));
            return arr;
        } else {
            List<Vertex> vertexList = split(root.left, x, offset);
            root.left = vertexList.get(1);
            arr.add(vertexList.get(0));
            arr.add(recalculate(root));
            return arr;
        }
    }

    public static Vertex insert(Vertex root, int x, int data, boolean shifting) {
        List<Vertex> ab = split(root, x, 0);
        Vertex b = ab.get(1);
        if (!shifting) {
            List<Vertex> cd = split(ab.get(1), 1, 0);
            b = cd.get(1);
        }
        int rand = (int) (100 + (Math.random() * (1000 - 100)));
        Vertex c = new Vertex(data, rand, null, null);
        return merge(ab.getFirst(), merge(c, b));
    }

    public static Vertex remove(Vertex root, int x) {
        List<Vertex> ab = split(root, x, 0);
        List<Vertex> cd = split(ab.get(1), 1, 0);
        return merge(ab.getFirst(), cd.getLast());
    }
    public static int get(Vertex root, int x) {
        List<Vertex> ab = split(root, x, 0);
        List<Vertex> cd = split(ab.get(1), 1, 0);
        int res = cd.getFirst().data;
        merge(ab.getFirst(), merge(cd.getFirst(), cd.getLast()));
        return res;
    }
    public static Vertex add(Vertex root, int left, int right, int value) {
        List<Vertex> ab = split(root, left, 0);
        List<Vertex> cd = split(ab.get(1), right - left, 0);
        update(cd.getFirst(), value);
        return merge(ab.getFirst(), merge(cd.getFirst(), cd.getLast()));
    }
    public static boolean find(Vertex root, int x) {
        List<Vertex> ab = split(root, x, 0);
        List<Vertex> cd = split(ab.get(1), 1, 0);
        boolean res = cd.getFirst() != null;
        merge(ab.getFirst(), merge(cd.getFirst(), cd.getLast()));
        return res;
    }

    public static int sizeOf(Vertex root) {
        push(root);
        if (root != null) {
            return root.size;
        } else {
            return 0;
        }
    }

    public static Vertex recalculate(Vertex root) {
        push(root);
        if (root != null) {
            root.size = sizeOf(root.left) + sizeOf(root.right) + 1;
            root.sum = sumOf(root.left) + sumOf(root.right) + dataOf(root);
        }
        return root;
    }

    public static int sumOf(Vertex root) {
        push(root);
        if (root != null) {
            return root.sum;
        } else {
            return 0;
        }
    }

    public static int dataOf(Vertex root) {
        push(root);
        if (root != null) {
            return root.data;
        } else {
            return 0;
        }
    }
    public static void update(Vertex root, int flag) {
        if (root != null) {
            root.flag += flag;
        }
    }
    public static void push(Vertex root) {
        if (root != null && root.flag != 0) {
            root.data += root.flag;
            root.sum += root.flag * root.size;
            update(root.left, root.flag);
            update(root.right, root.flag);
            root.flag = 0;
        }
    }
    public static class Vertex {
        protected int data;
        private final int priority;
        protected Vertex left;
        protected Vertex right;
        protected int size = 1;
        protected int sum;
        protected int flag;


        public Vertex(int data, int priority, Vertex left, Vertex right) {
            this.data = data;
            this.sum = data;
            this.priority = priority;
            this.left = left;
            this.right = right;
        }

        public int priority() {
            return priority;
        }

    }
}
