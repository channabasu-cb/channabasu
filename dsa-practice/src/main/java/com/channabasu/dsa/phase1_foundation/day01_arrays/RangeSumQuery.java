package com.channabasu.dsa.phase1_foundation.day01_arrays;

/**
 * ═══════════════════════════════════════════════════════
 * 📅 DAY 1 - Problem 1: Range Sum Query (LeetCode #303)
 * ═══════════════════════════════════════════════════════
 * 
 * DIFFICULTY: Easy
 * PATTERN: Prefix Sum
 * 
 * ─── Problem Statement ───
 * Given an integer array nums, handle multiple queries of the following type:
 * Calculate the sum of elements between indices left and right inclusive.
 * 
 * Implement the NumArray class:
 * - NumArray(int[] nums) → initializes with the array
 * - int sumRange(int left, int right) → returns sum of nums[left..right]
 * 
 * ─── Examples ───
 * Input: nums = [-2, 0, 3, -5, 2, -1]
 *   sumRange(0, 2) → 1   (because -2 + 0 + 3 = 1)
 *   sumRange(2, 5) → -1  (because 3 + -5 + 2 + -1 = -1)
 *   sumRange(0, 5) → -3  (because -2 + 0 + 3 + -5 + 2 + -1 = -3)
 * 
 * ─── Key Concept: Prefix Sum ───
 * Instead of summing from left to right each time (O(n) per query),
 * we precompute a prefix sum array so each query is O(1).
 * 
 * prefix[0] = 0
 * prefix[1] = nums[0]
 * prefix[2] = nums[0] + nums[1]
 * prefix[i] = nums[0] + nums[1] + ... + nums[i-1]
 * 
 * sumRange(left, right) = prefix[right + 1] - prefix[left]
 * 
 * Visualized:
 * nums:     [-2,  0,  3, -5,  2, -1]
 * prefix: [0, -2, -2,  1, -4, -2, -3]
 *                      ↑           ↑
 *                   prefix[2]   prefix[6]
 * sumRange(2,5) = prefix[6] - prefix[2] = -3 - (-2) = -1 ✓
 */
public class RangeSumQuery {

    // ╔═══════════════════════════════════════╗
    // ║  TODO: Implement your solution below  ║
    // ╚═══════════════════════════════════════╝

    private int[] prefix;

    /**
     * Initialize prefix sum array.
     * 
     * Think about:
     * - Why do we make prefix array of size nums.length + 1?
     * - What does prefix[i] represent?
     * 
     * Time:  O(n) - one pass to build prefix array
     * Space: O(n) - to store prefix array
     */
    public RangeSumQuery(int[] nums) {
        // Step 1: Create prefix array of size n+1
        // Step 2: Fill it: prefix[i+1] = prefix[i] + nums[i]
        
        // ──── YOUR CODE HERE ────
        
        
        // ──── END YOUR CODE ────
    }

    /**
     * Return sum of nums[left..right] using prefix sum.
     * 
     * Formula: sumRange(left, right) = prefix[right + 1] - prefix[left]
     * 
     * Time:  O(1)
     * Space: O(1)
     */
    public int sumRange(int left, int right) {
        // ──── YOUR CODE HERE ────
        return 0;
        // ──── END YOUR CODE ────
    }

    // ═══════════════════════════════════════
    // Test your solution
    // ═══════════════════════════════════════
    public static void main(String[] args) {
        int[] nums = {-2, 0, 3, -5, 2, -1};
        RangeSumQuery obj = new RangeSumQuery(nums);

        // Test cases
        System.out.println("sumRange(0, 2) = " + obj.sumRange(0, 2) + " (expected: 1)");
        System.out.println("sumRange(2, 5) = " + obj.sumRange(2, 5) + " (expected: -1)");
        System.out.println("sumRange(0, 5) = " + obj.sumRange(0, 5) + " (expected: -3)");

        System.out.println("\n✅ All test cases passed? Check the outputs above!");
    }
}
