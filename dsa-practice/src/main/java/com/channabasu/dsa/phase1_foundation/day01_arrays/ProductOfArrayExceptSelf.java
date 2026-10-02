package com.channabasu.dsa.phase1_foundation.day01_arrays;

import java.util.Arrays;

/**
 * ═══════════════════════════════════════════════════════════════
 * 📅 DAY 1 - Problem 2: Product of Array Except Self (LeetCode #238)
 * ═══════════════════════════════════════════════════════════════
 * 
 * DIFFICULTY: Medium
 * PATTERN: Prefix/Suffix Products
 * COMPANIES: Amazon, Facebook, Apple, Microsoft, Google
 * 
 * ─── Problem Statement ───
 * Given an integer array nums, return an array answer such that answer[i]
 * is equal to the product of all elements of nums except nums[i].
 * 
 * You must solve it WITHOUT using division and in O(n) time.
 * 
 * ─── Examples ───
 * Input:  nums = [1, 2, 3, 4]
 * Output: [24, 12, 8, 6]
 * Explanation: 
 *   answer[0] = 2*3*4 = 24
 *   answer[1] = 1*3*4 = 12
 *   answer[2] = 1*2*4 = 8
 *   answer[3] = 1*2*3 = 6
 * 
 * ─── Approach: Left & Right Products ───
 * 
 * For each index i:
 *   answer[i] = (product of all elements to the LEFT of i)
 *             × (product of all elements to the RIGHT of i)
 * 
 * Visualized for nums = [1, 2, 3, 4]:
 * 
 *  Index:       0     1     2     3
 *  nums:       [1,    2,    3,    4]
 *  leftProd:   [1,    1,    2,    6]    ← product of elements before i
 *  rightProd:  [24,   12,   4,    1]    ← product of elements after i
 *  answer:     [1×24, 1×12, 2×4,  6×1] = [24, 12, 8, 6] ✓
 * 
 * ─── Optimization ───
 * We can do this with O(1) extra space (output array doesn't count)
 * by building left products into the answer array, then multiplying
 * right products in a second pass.
 */
public class ProductOfArrayExceptSelf {

    // ╔════════════════════════════════════════════════╗
    // ║  APPROACH 1: Two arrays (easier to understand) ║
    // ╚════════════════════════════════════════════════╝

    /**
     * Time:  O(n) - two passes
     * Space: O(n) - for left and right product arrays
     */
    public int[] productExceptSelf_TwoArrays(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];

        // Step 1: Build left product array
        //   leftProd[i] = product of nums[0] * nums[1] * ... * nums[i-1]
        //   leftProd[0] = 1 (nothing to the left of index 0)
        
        // Step 2: Build right product array
        //   rightProd[i] = product of nums[i+1] * nums[i+2] * ... * nums[n-1]
        //   rightProd[n-1] = 1 (nothing to the right of last index)
        
        // Step 3: answer[i] = leftProd[i] * rightProd[i]

        // ──── YOUR CODE HERE ────
        
        
        // ──── END YOUR CODE ────

        return answer;
    }

    // ╔═════════════════════════════════════════════════╗
    // ║  APPROACH 2: O(1) space (interview optimal)     ║
    // ╚═════════════════════════════════════════════════╝

    /**
     * Time:  O(n) - two passes
     * Space: O(1) - only the output array (doesn't count as extra space)
     * 
     * Trick: Use the answer array to store left products first,
     *        then multiply right products in-place using a running variable.
     */
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];

        // Pass 1: Fill answer with left products
        // Pass 2: Multiply with right products using a running variable

        // ──── YOUR CODE HERE ────
        
        
        // ──── END YOUR CODE ────

        return answer;
    }

    // ═══════════════════════════════════════
    // Test your solution
    // ═══════════════════════════════════════
    public static void main(String[] args) {
        ProductOfArrayExceptSelf solution = new ProductOfArrayExceptSelf();

        // Test case 1
        int[] nums1 = {1, 2, 3, 4};
        System.out.println("Input:  " + Arrays.toString(nums1));
        System.out.println("Output: " + Arrays.toString(solution.productExceptSelf(nums1)));
        System.out.println("Expect: [24, 12, 8, 6]");
        System.out.println();

        // Test case 2
        int[] nums2 = {-1, 1, 0, -3, 3};
        System.out.println("Input:  " + Arrays.toString(nums2));
        System.out.println("Output: " + Arrays.toString(solution.productExceptSelf(nums2)));
        System.out.println("Expect: [0, 0, 9, 0, 0]");
        System.out.println();

        // Test case 3: Edge case with two elements
        int[] nums3 = {4, 3};
        System.out.println("Input:  " + Arrays.toString(nums3));
        System.out.println("Output: " + Arrays.toString(solution.productExceptSelf(nums3)));
        System.out.println("Expect: [3, 4]");
    }
}
