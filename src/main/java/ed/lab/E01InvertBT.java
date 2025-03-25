package ed.lab;

public class E01InvertBT {
    public TreeNode<Integer> invertTree(TreeNode<Integer> root) {

        if(root == null){
            return null;
        }
        TreeNode temporal = root.left;
        root.left =  root.right;
        root.right = temporal;

        invertTree(root.left);
        invertTree(root.right);



        return root;
    }



}
