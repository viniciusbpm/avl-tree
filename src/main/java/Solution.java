import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;


public class Solution {
    public static void main(String[] args) {
        Node node = new Node(3);
        Node root = insert(node, 4);
        Node root2 = insert(root, 5);
    }

    public static Node insert(Node root, int val){
        Node node = new Node(val);
        boolean leftIsNull = root.getLeft() == null;
        boolean rightIsNull = root.getRight() == null;
        int leftHeight = leftIsNull ? 0 : root.getLeft().getHt();
        int rightHeight = rightIsNull ? 0 : root.getRight().getHt();

        if(val > root.getVal() && rightIsNull){
            root.setRight(node);
            rightHeight = root.getRight().getHt();
        } else if(val > root.getVal() && !rightIsNull){
            root.setRight(insert(root.getRight(), val));
            rightHeight = root.getRight().getHt();
        }

        if(val < root.getVal() && leftIsNull) {
            root.setLeft(node);
            leftHeight = root.getLeft().getHt();
        } else if(val < root.getVal() && !leftIsNull){
            root.setLeft(insert(root.getLeft(), val));
            leftHeight = root.getLeft().getHt();
        }

        root.setHt(calculateHeight(leftHeight, rightHeight));

        int balanceFactor = leftHeight - rightHeight;

        adjustTree(root, balanceFactor, val);

        return root;
    }

    public static int calculateHeight(int leftHeight, int rightHeight){
        if(leftHeight - rightHeight == 0 || leftHeight - rightHeight > 0){
            return leftHeight + 1;
        } else {
            return rightHeight + 1;
        }
    }

    public static void adjustTree(Node root, int balanceFactor, int val){
        int leftVal = root.getLeft() == null ? 0 : root.getLeft().getVal();
        int rightVal = root.getRight() == null ? 0 : root.getRight().getVal();

        if(balanceFactor < -1 && val > rightVal){
            rotateLeft(root);
        }

        if(balanceFactor > 1 && val > leftVal){
            rotateLeft(root.getLeft());
            rotateRight(root);
        }

        if(balanceFactor < -1 && val < rightVal){
            rotateRight(root.getRight());
            rotateLeft(root);
        }

        if(balanceFactor > 1 && val < leftVal){
            rotateRight(root);
        }
    }

    public static void rotateLeft(Node root){
        Node nodeLeft = new Node(root.getVal());

        root.setVal(root.getRight().getVal());
        root.setRight(root.getRight().getRight());
        root.setLeft(nodeLeft);
        root.setHt(root.getHt() - 1);
    }

    public static void rotateRight(Node root){
        Node nodeRight = new Node(root.getVal());

        root.setVal(root.getLeft().getVal());
        root.setRight(nodeRight);
        root.setLeft(root.getLeft().getLeft());
        root.setHt(root.getHt() - 1);
    }
}
