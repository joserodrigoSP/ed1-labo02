package ed.lab;

import java.util.Stack;

public class E02KthSmallest {
    public int kthSmallest(TreeNode<Integer> root, int k) {
        Stack<TreeNode<Integer>> stack = new Stack<>();
        TreeNode<Integer> current = root;
        int count = 0;

        while (current != null || !stack.isEmpty()) {
            // Ir al extremo izquierdo
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            // Procesar el nodo
            current = stack.pop();
            count++;
            // Si hemos llegado al k-ésimo nodo
            if (count == k) {
                return current.value;
            }
            // Ir al subárbol derecho
            current = current.right;
        }

        // Si k es mayor que el número de nodos en el árbol
        return -1; // O lan
    }

}