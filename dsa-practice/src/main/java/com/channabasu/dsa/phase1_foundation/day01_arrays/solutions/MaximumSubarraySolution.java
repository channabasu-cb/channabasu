package com.channabasu.dsa.phase1_foundation.day01_arrays.solutions;

import java.util.Arrays;

/**
 * ✅ SOLUTION: Maximum Subarray (LeetCode #53) - Kadane's Algorithm
 * 
 * ⚠️ TRY SOLVING IT YOURSELF FIRST before looking here!
 * File: ../MaximumSubarray.java
 */
public class MaximumSubarraySolution {

    // Kadane's Algorithm - O(n) time, O(1) space
    public int maxSubArray(int[] nums) {
        int currentSum = nums[0];
        int maxSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            // Key decision: extend previous subarray or start new
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }

    // Bonus: Return the actual subarray indices + sum
    public int[] maxSubArrayWithIndices(int[] nums) {
        int currentSum = nums[0];
        int maxSum = nums[0];
        int start = 0, end = 0, tempStart = 0;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > currentSum + nums[i]) {
                currentSum = nums[i];
                tempStart = i;  // start a new subarray
            } else {
                currentSum += nums[i];  // extend current subarray
            }

            if (currentSum > maxSum) {
                maxSum = currentSum;
                start = tempStart;
                end = i;
            }
        }

        return new int[]{start, end, maxSum};
    }

    public static void main(String[] args) {
        MaximumSubarraySolution sol = new MaximumSubarraySolution();

        assert sol.maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}) == 6;
        assert sol.maxSubArray(new int[]{1}) == 1;
        assert sol.maxSubArray(new int[]{5, 4, -1, 7, 8}) == 23;
        assert sol.maxSubArray(new int[]{-3, -2, -5, -1}) == -1;

        // Test with indices
        int[] result = sol.maxSubArrayWithIndices(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});
        System.out.println("Subarray indices: [" + result[0] + ", " + result[1] + "], sum = " + result[2]);
        // Expected: [3, 6], sum = 6  →  subarray [4, -1, 2, 1]

        System.out.println("✅ All tests passed for Maximum Subarray!");
    }
}
