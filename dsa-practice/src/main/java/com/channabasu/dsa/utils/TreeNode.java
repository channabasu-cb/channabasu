package com.channabasu.dsa.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Definition for a binary tree node.
 * Used across tree problems.
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode() {}

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    /**
     * Helper: Build tree from level-order array (LeetCode style)
     * Use null for missing nodes.
     * Example: TreeNode.of(1, 2, 3, null, 5) builds:
     *       1
     *      / \
     *     2   3
     *      \
     *       5
     */
    public static TreeNode of(Integer... values) {
        if (values == null || values.length == 0 || values[0] == null) return null;

        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int i = 1;

        while (!queue.isEmpty() && i < values.length) {
            TreeNode current = queue.poll();

            if (i < values.length && values[i] != null) {
                current.left = new TreeNode(values[i]);
                queue.offer(current.left);
            }
            i++;

            if (i < values.length && values[i] != null) {
                current.right = new TreeNode(values[i]);
                queue.offer(current.right);
            }
            i++;
        }

        return root;
    }

    /**
     * Helper: Level-order traversal as list (for printing/testing)
     */
    public List<Integer> toLevelOrder() {
        List<Integer> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                result.add(null);
            } else {
                result.add(node.val);
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }

        // Remove trailing nulls
        while (!result.isEmpty() && result.get(result.size() - 1) == null) {
            result.remove(result.size() - 1);
        }
        return result;
    }

    @Override
    public String toString() {
        return "TreeNode{" + toLevelOrder() + "}";
    }
}
