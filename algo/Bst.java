import java.util.ArrayList;
import java.util.List;

public class Bst {
    public static void main(String[] args) {

    }
//    public static BinarySearchTree.Vertex merge(BinarySearchTree.Vertex root1, BinarySearchTree.Vertex root2) {
//        if (root1 == null) {
//            return root2;
//        }
//        if (root2 == null) {
//            return root1;
//        }
//        if (root1.priority() < root2.priority()) {
//            root1.right = merge(root1.right, root2);
//            return root1;
//        } else {
//            root2.left = merge(root1, root2.left);
//            return root2;
//        }
//    }
//    public static List<BinarySearchTree.Vertex> split(BinarySearchTree.Vertex root, int x) {
//        List<BinarySearchTree.Vertex> arr = new ArrayList<>();
//        if (root == null) {
//            arr.add(null);
//            arr.add(null);
//            return arr;
//        }
//        if (root.key() < x) {
//            List<BinarySearchTree.Vertex> vertexList = split(root.right, x);
//            root.right = vertexList.get(0);
//            arr.add(root);
//            arr.add(vertexList.get(1));
//            return arr;
//        } else {
//            List<BinarySearchTree.Vertex> vertexList = split(root.left, x);
//            root.left = vertexList.get(1);
//            arr.add(vertexList.get(0));
//            arr.add(root);
//            return arr;
//        }
//    }
//
//    public static BinarySearchTree.Vertex insert(BinarySearchTree.Vertex root, int x, int id) {
//        List<BinarySearchTree.Vertex> ab = split(root, x);
//        List<BinarySearchTree.Vertex> cd = split(ab.get(1), x + 1);
//        int rand = (int) (100 + (Math.random() * (1000 - 100)));
//        BinarySearchTree.Vertex c = new BinarySearchTree.Vertex(x, rand, id, null, null);
//        return merge(ab.getFirst(), merge(c, cd.get(1)));
//    }
//
//    public static BinarySearchTree.Vertex remove(BinarySearchTree.Vertex root, int x) {
//        List<BinarySearchTree.Vertex> ab = split(root, x);
//        List<BinarySearchTree.Vertex> cd = split(ab.get(1), x);
//        return merge(ab.getFirst(), cd.getLast());
//    }
//
//    public static class Vertex {
//        private final int key;
//        private final int priority;
//        private final int id;
//        protected BinarySearchTree.Vertex left;
//        protected BinarySearchTree.Vertex right;
//
//        public Vertex(int key, int priority, int id, BinarySearchTree.Vertex left, BinarySearchTree.Vertex right) {
//            this.key = key;
//            this.priority = priority;
//            this.id = id;
//            this.left = left;
//            this.right = right;
//        }
//
//        public int key() {
//            return key;
//        }
//
//        public int priority() {
//            return priority;
//        }
//
//        public int id() {
//            return id;
//        }
//    }
}
