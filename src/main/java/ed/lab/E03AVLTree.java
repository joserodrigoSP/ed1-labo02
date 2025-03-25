package ed.lab;

import java.util.Comparator;

public class E03AVLTree<T> {

    private final Comparator<T> comparator;
    private Node<T> root;
    private int size;

    public E03AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.root = null;
        this.size = 0;
    }

    public void insert(T value) {
        root = insert(root, value);
    }

    private Node<T> insert(Node<T> node, T value) {
        if (node == null) {
            size++;
            return new Node<>(value);
        }

        int cmp = comparator.compare(value, node.value);
        if (cmp < 0) {
            node.left = insert(node.left, value);
        } else if (cmp > 0) {
            node.right = insert(node.right, value);
        } else {
            return node; // Duplicate values are not allowed
        }

        return balance(node);
    }

    public void delete(T value) {
        root = delete(root, value);
    }

    private Node<T> delete(Node<T> node, T value) {
        if (node == null) {
            return null;
        }

        int cmp = comparator.compare(value, node.value);
        if (cmp < 0) {
            node.left = delete(node.left, value);
        } else if (cmp > 0) {
            node.right = delete(node.right, value);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
                size--;
            } else {
                Node<T> minNode = findMin(node.right);
                node.value = minNode.value;
                node.right = delete(node.right, minNode.value);
            }
        }

        if (node != null) {
            node = balance(node);
        }

        return node;
    }

    public T search(T value) {
        Node<T> node = search(root, value);
        return (node != null) ? node.value : null;
    }

    private Node<T> search(Node<T> node, T value) {
        if (node == null) {
            return null;
        }

        int cmp = comparator.compare(value, node.value);
        if (cmp < 0) {
            return search(node.left, value);
        } else if (cmp > 0) {
            return search(node.right, value);
        } else {
            return node;
        }
    }

    public int height() {
        return height(root);
    }

    private int height(Node<T> node) {
        return (node == null) ? 0 : node.height;
    }

    public int size() {
        return size;
    }

    private Node<T> balance(Node<T> node) {
        updateHeight(node);

        int balanceFactor = getBalanceFactor(node);
        if (balanceFactor > 1) {
            if (getBalanceFactor(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            node = rotateRight(node);
        } else if (balanceFactor < -1) {
            if (getBalanceFactor(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            node = rotateLeft(node);
        }

        return node;
    }

    private void updateHeight(Node<T> node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private int getBalanceFactor(Node<T> node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    private Node<T> rotateRight(Node<T> y) {
        Node<T> x = y.left;
        Node<T> T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private Node<T> rotateLeft(Node<T> x) {
        Node<T> y = x.right;
        Node<T> T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    private Node<T> findMin(Node<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private static class Node<T> {
        T value;
        Node<T> left, right;
        int height;

        Node(T value) {
            this.value = value;
            this.height = 1;
        }
    }
}