package com.channabasu.dsa.templates;

import java.util.*;

/**
 * ═══════════════════════════════════════════════════
 * 📘 ALGORITHM TEMPLATES - Quick Reference
 * ═══════════════════════════════════════════════════
 * 
 * These are battle-tested templates you'll use repeatedly
 * in coding interviews. Memorize these patterns!
 */
public class AlgorithmTemplates {

    // ═══════════════════════════════════════
    // 1. BINARY SEARCH (Standard)
    // ═══════════════════════════════════════
    /**
     * Find target in sorted array. Returns index or -1.
     * Time: O(log n)
     */
    public static int binarySearch(int[] arr, int target) {
        int lo = 0, hi = arr.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;  // prevents overflow
            if (arr[mid] == target) return mid;
            else if (arr[mid] < target) lo = mid + 1;
            else hi = mid - 1;
        }
        return -1;
    }

    // ═══════════════════════════════════════
    // 2. BINARY SEARCH ON ANSWER
    // ═══════════════════════════════════════
    /**
     * When the answer is in a range [lo, hi] and we need to find
     * the minimum valid answer (e.g., Koko Eating Bananas).
     */
    public static int binarySearchOnAnswer(int lo, int hi) {
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (isValid(mid)) {
                hi = mid;       // mid works, try smaller
            } else {
                lo = mid + 1;   // mid doesn't work, try larger
            }
        }
        return lo;
    }

    private static boolean isValid(int candidate) {
        // Replace with problem-specific logic
        return true;
    }

    // ═══════════════════════════════════════
    // 3. SLIDING WINDOW (Variable Size)
    // ═══════════════════════════════════════
    /**
     * Find longest/shortest window satisfying a condition.
     * Example: Longest substring without repeating characters.
     * Time: O(n)
     */
    public static int slidingWindow(String s) {
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, maxLen = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            window.put(c, window.getOrDefault(c, 0) + 1);

            // Shrink window while condition is violated
            while (/* window is invalid */ false) {
                char leftChar = s.charAt(left);
                window.put(leftChar, window.get(leftChar) - 1);
                if (window.get(leftChar) == 0) window.remove(leftChar);
                left++;
            }

            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    // ═══════════════════════════════════════
    // 4. TWO POINTERS (Sorted Array)
    // ═══════════════════════════════════════
    /**
     * Find pair with target sum in sorted array.
     * Time: O(n)
     */
    public static int[] twoPointers(int[] sorted, int target) {
        int left = 0, right = sorted.length - 1;
        while (left < right) {
            int sum = sorted[left] + sorted[right];
            if (sum == target) return new int[]{left, right};
            else if (sum < target) left++;
            else right--;
        }
        return new int[]{-1, -1};
    }

    // ═══════════════════════════════════════
    // 5. BFS (Breadth-First Search)
    // ═══════════════════════════════════════
    /**
     * Level-order traversal / shortest path in unweighted graph.
     * Time: O(V + E)
     */
    public static void bfs(Map<Integer, List<Integer>> graph, int start) {
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        queue.offer(start);
        visited.add(start);
        int level = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();  // process level by level
            for (int i = 0; i < size; i++) {
                int node = queue.poll();
                // Process node here
                for (int neighbor : graph.getOrDefault(node, List.of())) {
                    if (visited.add(neighbor)) {
                        queue.offer(neighbor);
                    }
                }
            }
            level++;
        }
    }

    // ═══════════════════════════════════════
    // 6. DFS (Depth-First Search) - Recursive
    // ═══════════════════════════════════════
    public static void dfs(Map<Integer, List<Integer>> graph, int node, Set<Integer> visited) {
        visited.add(node);
        // Process node here
        for (int neighbor : graph.getOrDefault(node, List.of())) {
            if (!visited.contains(neighbor)) {
                dfs(graph, neighbor, visited);
            }
        }
    }

    // ═══════════════════════════════════════
    // 7. DFS ON GRID (Flood Fill / Islands)
    // ═══════════════════════════════════════
    private static final int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public static void dfsGrid(char[][] grid, int row, int col, boolean[][] visited) {
        int rows = grid.length, cols = grid[0].length;
        if (row < 0 || row >= rows || col < 0 || col >= cols) return;
        if (visited[row][col] || grid[row][col] == '0') return;

        visited[row][col] = true;
        for (int[] dir : DIRECTIONS) {
            dfsGrid(grid, row + dir[0], col + dir[1], visited);
        }
    }

    // ═══════════════════════════════════════
    // 8. BACKTRACKING
    // ═══════════════════════════════════════
    /**
     * Generate all subsets/combinations/permutations.
     * Time: O(2^n) for subsets, O(n!) for permutations
     */
    public static void backtrack(List<List<Integer>> result, List<Integer> current,
                                  int[] nums, int start) {
        result.add(new ArrayList<>(current));  // MUST copy!

        for (int i = start; i < nums.length; i++) {
            // Skip duplicates (if nums is sorted)
            if (i > start && nums[i] == nums[i - 1]) continue;

            current.add(nums[i]);               // choose
            backtrack(result, current, nums, i + 1);  // explore
            current.remove(current.size() - 1);  // un-choose (backtrack)
        }
    }

    // ═══════════════════════════════════════
    // 9. TOPOLOGICAL SORT (Kahn's Algorithm)
    // ═══════════════════════════════════════
    /**
     * Order nodes so that for every edge u→v, u comes before v.
     * Used for: Course scheduling, build systems, dependency resolution.
     * Time: O(V + E)
     */
    public static List<Integer> topologicalSort(int numNodes, int[][] edges) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        int[] inDegree = new int[numNodes];

        for (int[] edge : edges) {
            graph.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(edge[1]);
            inDegree[edge[1]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numNodes; i++) {
            if (inDegree[i] == 0) queue.offer(i);
        }

        List<Integer> order = new ArrayList<>();
        while (!queue.isEmpty()) {
            int node = queue.poll();
            order.add(node);
            for (int neighbor : graph.getOrDefault(node, List.of())) {
                if (--inDegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        return order.size() == numNodes ? order : List.of(); // empty = cycle detected
    }

    // ═══════════════════════════════════════
    // 10. UNION-FIND (Disjoint Set Union)
    // ═══════════════════════════════════════
    static int[] parent, rank;

    public static void initUnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    public static int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);  // path compression
        }
        return parent[x];
    }

    public static boolean union(int x, int y) {
        int rootX = find(x), rootY = find(y);
        if (rootX == rootY) return false;  // already connected
        if (rank[rootX] < rank[rootY]) { int temp = rootX; rootX = rootY; rootY = temp; }
        parent[rootY] = rootX;
        if (rank[rootX] == rank[rootY]) rank[rootX]++;
        return true;
    }

    // ═══════════════════════════════════════
    // 11. DYNAMIC PROGRAMMING (1D)
    // ═══════════════════════════════════════
    /**
     * Classic DP template (e.g., climbing stairs).
     * dp[i] = answer for subproblem of size i
     */
    public static int dp1D(int n) {
        if (n <= 1) return 1;
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2]; // transition
        }
        return dp[n];
    }

    // ═══════════════════════════════════════
    // 12. DYNAMIC PROGRAMMING (2D)
    // ═══════════════════════════════════════
    /**
     * Classic 2D DP template (e.g., grid paths, LCS).
     */
    public static int dp2D(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    // ═══════════════════════════════════════
    // 13. MONOTONIC STACK
    // ═══════════════════════════════════════
    /**
     * Find next greater element for each element.
     * Time: O(n)
     */
    public static int[] nextGreaterElement(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        Arrays.fill(result, -1);
        Deque<Integer> stack = new ArrayDeque<>();  // stores indices

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                result[stack.pop()] = nums[i];
            }
            stack.push(i);
        }
        return result;
    }

    // ═══════════════════════════════════════
    // 14. MERGE SORT
    // ═══════════════════════════════════════
    public static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = Arrays.copyOfRange(arr, left, right + 1);
        int i = 0, j = mid - left + 1, k = left;
        int rightLen = right - left + 1;
        
        while (i <= mid - left && j < rightLen) {
            if (temp[i] <= temp[j]) arr[k++] = temp[i++];
            else arr[k++] = temp[j++];
        }
        while (i <= mid - left) arr[k++] = temp[i++];
        while (j < rightLen) arr[k++] = temp[j++];
    }

    public static void main(String[] args) {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║  📘 Algorithm Templates - Ready to Use!    ║");
        System.out.println("║  Import and adapt these for your problems  ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Templates included:");
        System.out.println("  1.  Binary Search (Standard)");
        System.out.println("  2.  Binary Search on Answer");
        System.out.println("  3.  Sliding Window (Variable Size)");
        System.out.println("  4.  Two Pointers");
        System.out.println("  5.  BFS (Breadth-First Search)");
        System.out.println("  6.  DFS (Depth-First Search)");
        System.out.println("  7.  DFS on Grid");
        System.out.println("  8.  Backtracking");
        System.out.println("  9.  Topological Sort (Kahn's)");
        System.out.println("  10. Union-Find");
        System.out.println("  11. DP 1D");
        System.out.println("  12. DP 2D");
        System.out.println("  13. Monotonic Stack");
        System.out.println("  14. Merge Sort");
    }
}
