package com.channabasu.dsa.phase1_foundation.day01_arrays;

import java.util.Arrays;

/**
 * ═══════════════════════════════════════════════════════════════
 * 📅 DAY 1 - Problem 3: Maximum Subarray (LeetCode #53) ⭐ MUST KNOW
 * ═══════════════════════════════════════════════════════════════
 * 
 * DIFFICULTY: Medium
 * PATTERN: Kadane's Algorithm
 * COMPANIES: Amazon, Microsoft, Google, LinkedIn, Apple (asked 100+ times!)
 * 
 * ─── Problem Statement ───
 * Given an integer array nums, find the subarray with the largest sum,
 * and return its sum.
 * 
 * A subarray is a contiguous non-empty sequence of elements within an array.
 * 
 * ─── Examples ───
 * Input:  nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]
 * Output: 6
 * Explanation: The subarray [4, -1, 2, 1] has the largest sum = 6.
 * 
 * Input:  nums = [1]
 * Output: 1
 * 
 * Input:  nums = [5, 4, -1, 7, 8]
 * Output: 23 (entire array)
 * 
 * ─── Understanding Kadane's Algorithm ───
 * 
 * KEY INSIGHT: At each position, we decide:
 *   "Should I EXTEND the previous subarray, or START a new one here?"
 * 
 * If previous subarray sum is NEGATIVE, it will only hurt us.
 * So we start fresh from the current element.
 * 
 * if (currentSum + nums[i] > nums[i])
 *     → extend: currentSum = currentSum + nums[i]
 * else
 *     → start new: currentSum = nums[i]
 * 
 * Simplified: currentSum = Math.max(nums[i], currentSum + nums[i])
 * 
 * ─── Dry Run ───
 * nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]
 * 
 * i=0: currentSum = max(-2, -2)     = -2, maxSum = -2
 * i=1: currentSum = max(1, -2+1)    =  1, maxSum =  1
 * i=2: currentSum = max(-3, 1+(-3)) = -2, maxSum =  1
 * i=3: currentSum = max(4, -2+4)    =  4, maxSum =  4
 * i=4: currentSum = max(-1, 4+(-1)) =  3, maxSum =  4
 * i=5: currentSum = max(2, 3+2)     =  5, maxSum =  5
 * i=6: currentSum = max(1, 5+1)     =  6, maxSum =  6  ← ANSWER
 * i=7: currentSum = max(-5, 6+(-5)) =  1, maxSum =  6
 * i=8: currentSum = max(4, 1+4)     =  5, maxSum =  6
 * 
 * Answer: 6 (subarray [4, -1, 2, 1])
 */
public class MaximumSubarray {

    // ╔════════════════════════════════════════╗
    // ║  APPROACH 1: Brute Force O(n²)        ║
    // ║  (Understand this first, then optimize)║
    // ╚════════════════════════════════════════╝

    /**
     * Check ALL possible subarrays and track the maximum sum.
     * Time:  O(n²)
     * Space: O(1)
     * 
     * This will TLE on LeetCode but helps you understand the problem.
     */
    public int maxSubArray_BruteForce(int[] nums) {
        int maxSum = Integer.MIN_VALUE;

        for (int i = 0; i < nums.length; i++) {
            int currentSum = 0;
            for (int j = i; j < nums.length; j++) {
                currentSum += nums[j];
                maxSum = Math.max(maxSum, currentSum);
            }
        }

        return maxSum;
    }

    // ╔════════════════════════════════════════╗
    // ║  APPROACH 2: Kadane's Algorithm O(n)  ║
    // ║  ⭐ This is what you need for interviews║
    // ╚════════════════════════════════════════╝

    /**
     * Kadane's Algorithm.
     * Time:  O(n) - single pass
     * Space: O(1)
     */
    public int maxSubArray(int[] nums) {
        // ──── YOUR CODE HERE ────
        // Hint: Track currentSum and maxSum
        // At each element: decide to extend or start fresh
        // currentSum = Math.max(nums[i], currentSum + nums[i])
        // maxSum = Math.max(maxSum, currentSum)
        
        
        return 0;
        // ──── END YOUR CODE ────
    }

    // ╔═══════════════════════════════════════════════════╗
    // ║  BONUS: Return the actual subarray (not just sum) ║
    // ╚═══════════════════════════════════════════════════╝

    /**
     * Returns [startIndex, endIndex, maxSum]
     * This is often asked as a follow-up in interviews!
     */
    public int[] maxSubArrayWithIndices(int[] nums) {
        // ──── YOUR CODE HERE ────
        // Track start, end, tempStart along with currentSum and maxSum
        
        
        return new int[]{0, 0, 0};
        // ──── END YOUR CODE ────
    }

    // ═══════════════════════════════════════
    // Test your solution
    // ═══════════════════════════════════════
    public static void main(String[] args) {
        MaximumSubarray solution = new MaximumSubarray();

        int[] nums1 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Input:  " + Arrays.toString(nums1));
        System.out.println("Output: " + solution.maxSubArray(nums1) + " (expected: 6)");
        System.out.println("Brute:  " + solution.maxSubArray_BruteForce(nums1) + " (verify)");
        System.out.println();

        int[] nums2 = {1};
        System.out.println("Input:  " + Arrays.toString(nums2));
        System.out.println("Output: " + solution.maxSubArray(nums2) + " (expected: 1)");
        System.out.println();

        int[] nums3 = {5, 4, -1, 7, 8};
        System.out.println("Input:  " + Arrays.toString(nums3));
        System.out.println("Output: " + solution.maxSubArray(nums3) + " (expected: 23)");
        System.out.println();

        // Edge case: all negative
        int[] nums4 = {-3, -2, -5, -1};
        System.out.println("Input:  " + Arrays.toString(nums4));
        System.out.println("Output: " + solution.maxSubArray(nums4) + " (expected: -1)");
    }
}
