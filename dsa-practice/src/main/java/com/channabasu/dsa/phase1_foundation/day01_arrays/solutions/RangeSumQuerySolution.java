package com.channabasu.dsa.phase1_foundation.day01_arrays.solutions;

/**
 * ✅ SOLUTION: Range Sum Query (LeetCode #303)
 * 
 * ⚠️ TRY SOLVING IT YOURSELF FIRST before looking here!
 * File: ../RangeSumQuery.java
 */
public class RangeSumQuerySolution {

    private int[] prefix;

    public RangeSumQuerySolution(int[] nums) {
        // Build prefix sum array
        // prefix[i] = sum of nums[0..i-1]
        prefix = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
        // For nums = [-2, 0, 3, -5, 2, -1]:
        // prefix = [0, -2, -2, 1, -4, -2, -3]
    }

    public int sumRange(int left, int right) {
        return prefix[right + 1] - prefix[left];
    }

    public static void main(String[] args) {
        int[] nums = {-2, 0, 3, -5, 2, -1};
        RangeSumQuerySolution obj = new RangeSumQuerySolution(nums);

        assert obj.sumRange(0, 2) == 1 : "Test 1 failed";
        assert obj.sumRange(2, 5) == -1 : "Test 2 failed";
        assert obj.sumRange(0, 5) == -3 : "Test 3 failed";

        System.out.println("✅ All tests passed for Range Sum Query!");
    }
}
