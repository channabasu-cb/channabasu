package com.channabasu.dsa.utils;

/**
 * Definition for singly-linked list.
 * Used across linked list problems.
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {}

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    /**
     * Helper: Create linked list from array
     * Example: ListNode.of(1, 2, 3, 4, 5) → 1→2→3→4→5
     */
    public static ListNode of(int... values) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        for (int val : values) {
            current.next = new ListNode(val);
            current = current.next;
        }
        return dummy.next;
    }

    /**
     * Helper: Convert linked list to string for printing
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListNode current = this;
        while (current != null) {
            sb.append(current.val);
            if (current.next != null) sb.append(" → ");
            current = current.next;
        }
        return sb.toString();
    }

    /**
     * Helper: Convert to array for easy comparison in tests
     */
    public int[] toArray() {
        java.util.List<Integer> list = new java.util.ArrayList<>();
        ListNode current = this;
        while (current != null) {
            list.add(current.val);
            current = current.next;
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }
}
