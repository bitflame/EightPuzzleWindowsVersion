package assignments;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class GameTree<Key extends Comparable<Key>, Value> {
    private node root;

    private class node {
        private Key key;
        private Value val;
        private node left, right;
        private int N;


        public node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    public Value get(Key key) {
        return get(root, key);
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }

    public void keys(node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }

    private void inorder(node x, Queue<Key> q) {
        if (x == null) return;
        inorder(x.left, q);
        q.enqueue(x.key);
        inorder(x.right, q);
    }

    private Value get(node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    public int size() {
        return size(root);
    }

    private int size(node x) {
        if (x == null) return 0;
        else return x.N;
    }


    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private node put(node x, Key key, Value val) {
        if (x == null) return new node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Key min() {
        return min(root).key;
    }

    private node min(node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    public Key floor(Key key) {
        node x = floor(root, key);
        if (x == null) return null;
        return x.key;
    }

    private node floor(node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        node t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }

    public Key max() {
        return max(root).key;
    }

    private node max(node x) {
        if (x.right == null) return x;
        return max(x.right);
    }

    public Key ceiling(Key key) {
        node x = ceiling(root, key);
        if (x == null) return null;
        return x.key;
    }


    private node ceiling(node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp > 0) return ceiling(x.right, key);
        node t = ceiling(x.left, key);
        if (t != null) return t;
        else return x;
    }

    // Find the subtree of a certain size. i.e. there are at least k nodes below the node you get back if the tree has it
    public Key select(int k) {
        return select(root, k).key;
    }

    private node select(node x, int k) {
        //Return Node containing key of rank k.
        if (x == null) return null;
        int t = size(x.left);
        if (t > k) return select(x.left, k);
        else if (t < k) return select(x.right, k - t - 1);
        else return x;
    }

    // Find the subtree of a certain rank i.e. all keys below it are less than the key we pass to the method
    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, node x) {
        // Return number of keys less than x.key in the subtree rooted at x
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else return size(x.left);
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private node deleteMin(node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    private node delete(node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void print() {
        print(root);
    }

    public void print(Key key) {
        print(root);
    }

    private void print(node x) {
        if (x == null) return;
        print(x.left);
        StdOut.println(x.key);
        print(x.right);
    }
}

