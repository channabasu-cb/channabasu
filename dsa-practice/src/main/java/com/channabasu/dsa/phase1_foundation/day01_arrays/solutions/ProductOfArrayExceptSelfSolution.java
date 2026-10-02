package com.channabasu.dsa.phase1_foundation.day01_arrays.solutions;

import java.util.Arrays;

/**
 * ✅ SOLUTION: Product of Array Except Self (LeetCode #238)
 * 
 * ⚠️ TRY SOLVING IT YOURSELF FIRST before looking here!
 * File: ../ProductOfArrayExceptSelf.java
 */
public class ProductOfArrayExceptSelfSolution {

    // Approach 1: Two separate arrays (easier to understand)
    public int[] productExceptSelf_TwoArrays(int[] nums) {
        int n = nums.length;
        int[] leftProducts = new int[n];
        int[] rightProducts = new int[n];
        int[] answer = new int[n];

        // Build left products
        leftProducts[0] = 1;
        for (int i = 1; i < n; i++) {
            leftProducts[i] = leftProducts[i - 1] * nums[i - 1];
        }

        // Build right products
        rightProducts[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            rightProducts[i] = rightProducts[i + 1] * nums[i + 1];
        }

        // Combine
        for (int i = 0; i < n; i++) {
            answer[i] = leftProducts[i] * rightProducts[i];
        }

        return answer;
    }

    // Approach 2: O(1) space - interview optimal
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];

        // Pass 1: answer[i] = product of all elements to the left of i
        answer[0] = 1;
        for (int i = 1; i < n; i++) {
            answer[i] = answer[i - 1] * nums[i - 1];
        }

        // Pass 2: multiply by right products using running variable
        int rightProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            answer[i] *= rightProduct;
            rightProduct *= nums[i];
        }

        return answer;
    }

    public static void main(String[] args) {
        ProductOfArrayExceptSelfSolution sol = new ProductOfArrayExceptSelfSolution();

        assert Arrays.equals(sol.productExceptSelf(new int[]{1, 2, 3, 4}), new int[]{24, 12, 8, 6});
        assert Arrays.equals(sol.productExceptSelf(new int[]{-1, 1, 0, -3, 3}), new int[]{0, 0, 9, 0, 0});

        System.out.println("✅ All tests passed for Product of Array Except Self!");
    }
}
