/* ═══════════════════════════════════════════════
   DSA Progress Tracker — Application Logic
   ═══════════════════════════════════════════════ */

// ─── Complete 60-Day Plan Data ───
const PLAN = [
  // ═══════════ PHASE 1: FOUNDATION (Days 1–17) ═══════════
  {
    id: 0,
    name: 'Phase 1: Foundation',
    desc: 'Arrays, Strings, Hashing, Two Pointers, Sorting & Binary Search',
    days: [
      {
        day: 1, title: 'Arrays: Prefix Sum & Kadane\'s', topic: 'Arrays',
        tasks: [
          { id: 'd1_learn', type: 'learn', text: 'Learn Prefix Sum concept', link: 'https://www.youtube.com/watch?v=7pJo_rM0z_s' },
          { id: 'd1_p1', type: 'problem', text: '#303 Range Sum Query', difficulty: 'easy', link: 'https://leetcode.com/problems/range-sum-query-immutable/' },
          { id: 'd1_p2', type: 'problem', text: '#238 Product of Array Except Self', difficulty: 'medium', link: 'https://leetcode.com/problems/product-of-array-except-self/' },
          { id: 'd1_p3', type: 'problem', text: '#53 Maximum Subarray (Kadane\'s) ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/maximum-subarray/' },
        ]
      },
      {
        day: 2, title: 'Kadane\'s Algorithm Continued', topic: 'Arrays',
        tasks: [
          { id: 'd2_learn', type: 'learn', text: 'Watch: Kadane\'s Algorithm Explained', link: 'https://www.youtube.com/watch?v=5WZl3MMT0Eg' },
          { id: 'd2_p1', type: 'problem', text: '#918 Max Sum Circular Subarray', difficulty: 'medium', link: 'https://leetcode.com/problems/maximum-sum-circular-subarray/' },
          { id: 'd2_p2', type: 'problem', text: '#152 Maximum Product Subarray', difficulty: 'medium', link: 'https://leetcode.com/problems/maximum-product-subarray/' },
        ]
      },
      {
        day: 3, title: 'Sliding Window (Fixed Size)', topic: 'Sliding Window',
        tasks: [
          { id: 'd3_learn', type: 'learn', text: 'Learn Fixed-Size Sliding Window', link: 'https://www.youtube.com/watch?v=GcW4mgmgSbw' },
          { id: 'd3_p1', type: 'problem', text: '#643 Maximum Average Subarray I', difficulty: 'easy', link: 'https://leetcode.com/problems/maximum-average-subarray-i/' },
          { id: 'd3_p2', type: 'problem', text: '#1456 Max Vowels in Substring', difficulty: 'medium', link: 'https://leetcode.com/problems/maximum-number-of-vowels-in-a-substring-of-given-length/' },
          { id: 'd3_p3', type: 'problem', text: '#219 Contains Duplicate II', difficulty: 'easy', link: 'https://leetcode.com/problems/contains-duplicate-ii/' },
        ]
      },
      {
        day: 4, title: 'Sliding Window (Variable Size)', topic: 'Sliding Window',
        tasks: [
          { id: 'd4_learn', type: 'learn', text: 'Learn Variable-Size Sliding Window', link: 'https://www.youtube.com/watch?v=wiGpQwVHdE0' },
          { id: 'd4_p1', type: 'problem', text: '#3 Longest Substring Without Repeating ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/longest-substring-without-repeating-characters/' },
          { id: 'd4_p2', type: 'problem', text: '#76 Minimum Window Substring', difficulty: 'hard', link: 'https://leetcode.com/problems/minimum-window-substring/' },
          { id: 'd4_p3', type: 'problem', text: '#424 Longest Repeating Character Replacement', difficulty: 'medium', link: 'https://leetcode.com/problems/longest-repeating-character-replacement/' },
        ]
      },
      {
        day: 5, title: 'String Manipulation', topic: 'Strings',
        tasks: [
          { id: 'd5_p1', type: 'problem', text: '#125 Valid Palindrome', difficulty: 'easy', link: 'https://leetcode.com/problems/valid-palindrome/' },
          { id: 'd5_p2', type: 'problem', text: '#242 Valid Anagram', difficulty: 'easy', link: 'https://leetcode.com/problems/valid-anagram/' },
          { id: 'd5_p3', type: 'problem', text: '#49 Group Anagrams ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/group-anagrams/' },
          { id: 'd5_p4', type: 'problem', text: '#271 Encode and Decode Strings', difficulty: 'medium', link: 'https://leetcode.com/problems/encode-and-decode-strings/' },
        ]
      },
      {
        day: 6, title: 'Matrix Traversal', topic: 'Matrix',
        tasks: [
          { id: 'd6_learn', type: 'learn', text: 'Learn Matrix Traversal Patterns', link: 'https://www.youtube.com/watch?v=fMSJSS7eO1w' },
          { id: 'd6_p1', type: 'problem', text: '#48 Rotate Image', difficulty: 'medium', link: 'https://leetcode.com/problems/rotate-image/' },
          { id: 'd6_p2', type: 'problem', text: '#54 Spiral Matrix', difficulty: 'medium', link: 'https://leetcode.com/problems/spiral-matrix/' },
          { id: 'd6_p3', type: 'problem', text: '#73 Set Matrix Zeroes', difficulty: 'medium', link: 'https://leetcode.com/problems/set-matrix-zeroes/' },
        ]
      },
      {
        day: 7, title: '🔁 Week 1 Review & Contest', topic: 'Review',
        tasks: [
          { id: 'd7_t1', type: 'review', text: 'Re-solve 3 weakest problems (timed: 25 min each)' },
          { id: 'd7_t2', type: 'review', text: 'Attempt a LeetCode Weekly Contest', link: 'https://leetcode.com/contest/' },
          { id: 'd7_t3', type: 'review', text: '✅ Checkpoint: Can solve Easy array problems in < 15 min?' },
        ]
      },
      {
        day: 8, title: 'HashMap Fundamentals', topic: 'Hashing',
        tasks: [
          { id: 'd8_learn', type: 'learn', text: 'Learn HashMap Internals in Java', link: 'https://www.youtube.com/watch?v=KyUTuwz_b7Q' },
          { id: 'd8_p1', type: 'problem', text: '#1 Two Sum ⭐', difficulty: 'easy', link: 'https://leetcode.com/problems/two-sum/' },
          { id: 'd8_p2', type: 'problem', text: '#560 Subarray Sum Equals K', difficulty: 'medium', link: 'https://leetcode.com/problems/subarray-sum-equals-k/' },
          { id: 'd8_p3', type: 'problem', text: '#383 Ransom Note', difficulty: 'easy', link: 'https://leetcode.com/problems/ransom-note/' },
        ]
      },
      {
        day: 9, title: 'HashSet & Frequency Counting', topic: 'Hashing',
        tasks: [
          { id: 'd9_p1', type: 'problem', text: '#128 Longest Consecutive Sequence ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/longest-consecutive-sequence/' },
          { id: 'd9_p2', type: 'problem', text: '#347 Top K Frequent Elements', difficulty: 'medium', link: 'https://leetcode.com/problems/top-k-frequent-elements/' },
          { id: 'd9_p3', type: 'problem', text: '#36 Valid Sudoku', difficulty: 'medium', link: 'https://leetcode.com/problems/valid-sudoku/' },
        ]
      },
      {
        day: 10, title: 'Two Pointers (Sorted Arrays)', topic: 'Two Pointers',
        tasks: [
          { id: 'd10_learn', type: 'learn', text: 'Learn Two Pointer Technique', link: 'https://www.youtube.com/watch?v=On03HWe2tZM' },
          { id: 'd10_p1', type: 'problem', text: '#167 Two Sum II', difficulty: 'medium', link: 'https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/' },
          { id: 'd10_p2', type: 'problem', text: '#15 3Sum ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/3sum/' },
          { id: 'd10_p3', type: 'problem', text: '#11 Container With Most Water ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/container-with-most-water/' },
        ]
      },
      {
        day: 11, title: 'Two Pointers (Strings)', topic: 'Two Pointers',
        tasks: [
          { id: 'd11_p1', type: 'problem', text: '#344 Reverse String', difficulty: 'easy', link: 'https://leetcode.com/problems/reverse-string/' },
          { id: 'd11_p2', type: 'problem', text: '#5 Longest Palindromic Substring', difficulty: 'medium', link: 'https://leetcode.com/problems/longest-palindromic-substring/' },
          { id: 'd11_p3', type: 'problem', text: '#647 Palindromic Substrings', difficulty: 'medium', link: 'https://leetcode.com/problems/palindromic-substrings/' },
        ]
      },
      {
        day: 12, title: '🔁 Hashing & Two Pointers Review', topic: 'Review',
        tasks: [
          { id: 'd12_t1', type: 'review', text: 'Redo 2 unsolved problems from Days 8–11' },
          { id: 'd12_t2', type: 'review', text: 'Solve 2 random Medium problems' },
          { id: 'd12_t3', type: 'review', text: '✅ Checkpoint: Comfortable with HashMap & Two Pointers?' },
        ]
      },
      {
        day: 13, title: 'Sorting Algorithms', topic: 'Sorting',
        tasks: [
          { id: 'd13_learn', type: 'learn', text: 'Implement Merge Sort & Quick Sort from scratch', link: 'https://www.youtube.com/watch?v=MsYZSinhuFo' },
          { id: 'd13_p1', type: 'problem', text: '#912 Sort an Array', difficulty: 'medium', link: 'https://leetcode.com/problems/sort-an-array/' },
          { id: 'd13_p2', type: 'problem', text: '#75 Sort Colors (Dutch National Flag)', difficulty: 'medium', link: 'https://leetcode.com/problems/sort-colors/' },
        ]
      },
      {
        day: 14, title: 'Custom Sorting & Comparators', topic: 'Sorting',
        tasks: [
          { id: 'd14_learn', type: 'learn', text: 'Learn Java Comparator & Lambda Sorting', link: 'https://www.youtube.com/watch?v=oIp5e0E3Wj4' },
          { id: 'd14_p1', type: 'problem', text: '#179 Largest Number', difficulty: 'medium', link: 'https://leetcode.com/problems/largest-number/' },
          { id: 'd14_p2', type: 'problem', text: '#56 Merge Intervals ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/merge-intervals/' },
        ]
      },
      {
        day: 15, title: 'Binary Search (Basic)', topic: 'Binary Search',
        tasks: [
          { id: 'd15_learn', type: 'learn', text: 'Learn Binary Search Template & Edge Cases', link: 'https://www.youtube.com/watch?v=s4DPM8ct1pI' },
          { id: 'd15_p1', type: 'problem', text: '#704 Binary Search', difficulty: 'easy', link: 'https://leetcode.com/problems/binary-search/' },
          { id: 'd15_p2', type: 'problem', text: '#35 Search Insert Position', difficulty: 'easy', link: 'https://leetcode.com/problems/search-insert-position/' },
          { id: 'd15_p3', type: 'problem', text: '#74 Search a 2D Matrix', difficulty: 'medium', link: 'https://leetcode.com/problems/search-a-2d-matrix/' },
        ]
      },
      {
        day: 16, title: 'Binary Search on Answer', topic: 'Binary Search',
        tasks: [
          { id: 'd16_learn', type: 'learn', text: 'Learn Binary Search on Answer Space', link: 'https://www.youtube.com/watch?v=U2SozAs9RzA' },
          { id: 'd16_p1', type: 'problem', text: '#875 Koko Eating Bananas ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/koko-eating-bananas/' },
          { id: 'd16_p2', type: 'problem', text: '#1011 Capacity To Ship Within D Days', difficulty: 'medium', link: 'https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/' },
        ]
      },
      {
        day: 17, title: 'Advanced Binary Search', topic: 'Binary Search',
        tasks: [
          { id: 'd17_p1', type: 'problem', text: '#33 Search in Rotated Sorted Array ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/search-in-rotated-sorted-array/' },
          { id: 'd17_p2', type: 'problem', text: '#153 Find Min in Rotated Sorted Array', difficulty: 'medium', link: 'https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/' },
          { id: 'd17_p3', type: 'problem', text: '#4 Median of Two Sorted Arrays', difficulty: 'hard', link: 'https://leetcode.com/problems/median-of-two-sorted-arrays/' },
          { id: 'd17_t1', type: 'review', text: '✅ Checkpoint: Can apply binary search to non-obvious problems?' },
        ]
      },
    ]
  },

  // ═══════════ PHASE 2: CORE DATA STRUCTURES (Days 18–34) ═══════════
  {
    id: 1,
    name: 'Phase 2: Core Data Structures',
    desc: 'Linked Lists, Stacks, Queues, Trees, Heaps & Priority Queues',
    days: [
      {
        day: 18, title: 'Linked List Basics', topic: 'Linked Lists',
        tasks: [
          { id: 'd18_learn', type: 'learn', text: 'Learn Linked List Operations', link: 'https://www.youtube.com/watch?v=G0_I-ZF0S38' },
          { id: 'd18_p1', type: 'problem', text: '#206 Reverse Linked List ⭐', difficulty: 'easy', link: 'https://leetcode.com/problems/reverse-linked-list/' },
          { id: 'd18_p2', type: 'problem', text: '#21 Merge Two Sorted Lists ⭐', difficulty: 'easy', link: 'https://leetcode.com/problems/merge-two-sorted-lists/' },
          { id: 'd18_p3', type: 'problem', text: '#234 Palindrome Linked List', difficulty: 'easy', link: 'https://leetcode.com/problems/palindrome-linked-list/' },
        ]
      },
      {
        day: 19, title: 'Fast & Slow Pointers (Floyd\'s)', topic: 'Linked Lists',
        tasks: [
          { id: 'd19_learn', type: 'learn', text: 'Learn Floyd\'s Cycle Detection', link: 'https://www.youtube.com/watch?v=gBTe7lFR3vc' },
          { id: 'd19_p1', type: 'problem', text: '#141 Linked List Cycle', difficulty: 'easy', link: 'https://leetcode.com/problems/linked-list-cycle/' },
          { id: 'd19_p2', type: 'problem', text: '#142 Linked List Cycle II', difficulty: 'medium', link: 'https://leetcode.com/problems/linked-list-cycle-ii/' },
          { id: 'd19_p3', type: 'problem', text: '#876 Middle of the Linked List', difficulty: 'easy', link: 'https://leetcode.com/problems/middle-of-the-linked-list/' },
        ]
      },
      {
        day: 20, title: 'Advanced Linked List', topic: 'Linked Lists',
        tasks: [
          { id: 'd20_p1', type: 'problem', text: '#19 Remove Nth Node From End ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/remove-nth-node-from-end-of-list/' },
          { id: 'd20_p2', type: 'problem', text: '#143 Reorder List', difficulty: 'medium', link: 'https://leetcode.com/problems/reorder-list/' },
          { id: 'd20_p3', type: 'problem', text: '#148 Sort List', difficulty: 'medium', link: 'https://leetcode.com/problems/sort-list/' },
        ]
      },
      {
        day: 21, title: 'Hard Linked List', topic: 'Linked Lists',
        tasks: [
          { id: 'd21_p1', type: 'problem', text: '#138 Copy List with Random Pointer', difficulty: 'medium', link: 'https://leetcode.com/problems/copy-list-with-random-pointer/' },
          { id: 'd21_p2', type: 'problem', text: '#25 Reverse Nodes in k-Group', difficulty: 'hard', link: 'https://leetcode.com/problems/reverse-nodes-in-k-group/' },
        ]
      },
      {
        day: 22, title: 'Stack Fundamentals', topic: 'Stacks',
        tasks: [
          { id: 'd22_learn', type: 'learn', text: 'Learn Stack in Java (use ArrayDeque)', link: 'https://www.youtube.com/watch?v=O1KeXo8lE8A' },
          { id: 'd22_p1', type: 'problem', text: '#20 Valid Parentheses ⭐', difficulty: 'easy', link: 'https://leetcode.com/problems/valid-parentheses/' },
          { id: 'd22_p2', type: 'problem', text: '#155 Min Stack', difficulty: 'medium', link: 'https://leetcode.com/problems/min-stack/' },
          { id: 'd22_p3', type: 'problem', text: '#150 Evaluate Reverse Polish Notation', difficulty: 'medium', link: 'https://leetcode.com/problems/evaluate-reverse-polish-notation/' },
        ]
      },
      {
        day: 23, title: 'Monotonic Stack', topic: 'Stacks',
        tasks: [
          { id: 'd23_learn', type: 'learn', text: 'Learn Monotonic Stack Pattern', link: 'https://www.youtube.com/watch?v=cTBiBSnjO3c' },
          { id: 'd23_p1', type: 'problem', text: '#739 Daily Temperatures ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/daily-temperatures/' },
          { id: 'd23_p2', type: 'problem', text: '#84 Largest Rectangle in Histogram', difficulty: 'hard', link: 'https://leetcode.com/problems/largest-rectangle-in-histogram/' },
        ]
      },
      {
        day: 24, title: 'Queue & Deque', topic: 'Queues',
        tasks: [
          { id: 'd24_p1', type: 'problem', text: '#239 Sliding Window Maximum', difficulty: 'hard', link: 'https://leetcode.com/problems/sliding-window-maximum/' },
          { id: 'd24_p2', type: 'problem', text: '#622 Design Circular Queue', difficulty: 'medium', link: 'https://leetcode.com/problems/design-circular-queue/' },
        ]
      },
      {
        day: 25, title: 'Stack Applications', topic: 'Stacks',
        tasks: [
          { id: 'd25_p1', type: 'problem', text: '#394 Decode String', difficulty: 'medium', link: 'https://leetcode.com/problems/decode-string/' },
          { id: 'd25_p2', type: 'problem', text: '#71 Simplify Path', difficulty: 'medium', link: 'https://leetcode.com/problems/simplify-path/' },
          { id: 'd25_t1', type: 'review', text: '✅ Checkpoint: LL & Stack patterns feel natural?' },
        ]
      },
      {
        day: 26, title: 'Tree Traversals (DFS)', topic: 'Trees',
        tasks: [
          { id: 'd26_learn', type: 'learn', text: 'Learn Inorder, Preorder, Postorder', link: 'https://www.youtube.com/watch?v=1WxLM2hwts8' },
          { id: 'd26_p1', type: 'problem', text: '#94 Binary Tree Inorder Traversal', difficulty: 'easy', link: 'https://leetcode.com/problems/binary-tree-inorder-traversal/' },
          { id: 'd26_p2', type: 'problem', text: '#144 Binary Tree Preorder Traversal', difficulty: 'easy', link: 'https://leetcode.com/problems/binary-tree-preorder-traversal/' },
          { id: 'd26_p3', type: 'problem', text: '#104 Maximum Depth of Binary Tree', difficulty: 'easy', link: 'https://leetcode.com/problems/maximum-depth-of-binary-tree/' },
        ]
      },
      {
        day: 27, title: 'BFS / Level-Order Traversal', topic: 'Trees',
        tasks: [
          { id: 'd27_learn', type: 'learn', text: 'Learn BFS Level-Order Traversal', link: 'https://www.youtube.com/watch?v=6ZnyEApgFYg' },
          { id: 'd27_p1', type: 'problem', text: '#102 Level Order Traversal ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/binary-tree-level-order-traversal/' },
          { id: 'd27_p2', type: 'problem', text: '#199 Binary Tree Right Side View', difficulty: 'medium', link: 'https://leetcode.com/problems/binary-tree-right-side-view/' },
          { id: 'd27_p3', type: 'problem', text: '#103 Zigzag Level Order Traversal', difficulty: 'medium', link: 'https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/' },
        ]
      },
      {
        day: 28, title: 'Tree Construction & Properties', topic: 'Trees',
        tasks: [
          { id: 'd28_p1', type: 'problem', text: '#226 Invert Binary Tree', difficulty: 'easy', link: 'https://leetcode.com/problems/invert-binary-tree/' },
          { id: 'd28_p2', type: 'problem', text: '#101 Symmetric Tree', difficulty: 'easy', link: 'https://leetcode.com/problems/symmetric-tree/' },
          { id: 'd28_p3', type: 'problem', text: '#105 Construct from Preorder & Inorder', difficulty: 'medium', link: 'https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/' },
        ]
      },
      {
        day: 29, title: 'Tree Path Problems', topic: 'Trees',
        tasks: [
          { id: 'd29_p1', type: 'problem', text: '#112 Path Sum', difficulty: 'easy', link: 'https://leetcode.com/problems/path-sum/' },
          { id: 'd29_p2', type: 'problem', text: '#124 Binary Tree Max Path Sum ⭐', difficulty: 'hard', link: 'https://leetcode.com/problems/binary-tree-maximum-path-sum/' },
          { id: 'd29_p3', type: 'problem', text: '#543 Diameter of Binary Tree', difficulty: 'easy', link: 'https://leetcode.com/problems/diameter-of-binary-tree/' },
        ]
      },
      {
        day: 30, title: 'BST Operations', topic: 'Trees',
        tasks: [
          { id: 'd30_p1', type: 'problem', text: '#98 Validate BST ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/validate-binary-search-tree/' },
          { id: 'd30_p2', type: 'problem', text: '#230 Kth Smallest in BST', difficulty: 'medium', link: 'https://leetcode.com/problems/kth-smallest-element-in-a-bst/' },
          { id: 'd30_p3', type: 'problem', text: '#235 LCA of BST', difficulty: 'medium', link: 'https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/' },
        ]
      },
      {
        day: 31, title: '🔁 Tree Review', topic: 'Review',
        tasks: [
          { id: 'd31_p1', type: 'problem', text: '#236 LCA of Binary Tree', difficulty: 'medium', link: 'https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/' },
          { id: 'd31_p2', type: 'problem', text: '#297 Serialize & Deserialize BT', difficulty: 'hard', link: 'https://leetcode.com/problems/serialize-and-deserialize-binary-tree/' },
          { id: 'd31_t1', type: 'review', text: '✅ Checkpoint: Comfortable with all tree traversals?' },
        ]
      },
      {
        day: 32, title: 'Heap / PriorityQueue Basics', topic: 'Heaps',
        tasks: [
          { id: 'd32_learn', type: 'learn', text: 'Learn Min/Max Heap & PriorityQueue in Java', link: 'https://www.youtube.com/watch?v=t0Cq6tVNRBA' },
          { id: 'd32_p1', type: 'problem', text: '#215 Kth Largest Element ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/kth-largest-element-in-an-array/' },
          { id: 'd32_p2', type: 'problem', text: '#703 Kth Largest in Stream', difficulty: 'easy', link: 'https://leetcode.com/problems/kth-largest-element-in-a-stream/' },
        ]
      },
      {
        day: 33, title: 'Top-K & Merge Pattern', topic: 'Heaps',
        tasks: [
          { id: 'd33_p1', type: 'problem', text: '#347 Top K Frequent Elements', difficulty: 'medium', link: 'https://leetcode.com/problems/top-k-frequent-elements/' },
          { id: 'd33_p2', type: 'problem', text: '#23 Merge K Sorted Lists ⭐', difficulty: 'hard', link: 'https://leetcode.com/problems/merge-k-sorted-lists/' },
        ]
      },
      {
        day: 34, title: 'Two-Heap Pattern', topic: 'Heaps',
        tasks: [
          { id: 'd34_learn', type: 'learn', text: 'Learn Two-Heap Pattern', link: 'https://www.youtube.com/watch?v=itmhHWaHupI' },
          { id: 'd34_p1', type: 'problem', text: '#295 Find Median from Data Stream ⭐', difficulty: 'hard', link: 'https://leetcode.com/problems/find-median-from-data-stream/' },
          { id: 'd34_p2', type: 'problem', text: '#502 IPO', difficulty: 'hard', link: 'https://leetcode.com/problems/ipo/' },
        ]
      },
    ]
  },

  // ═══════════ PHASE 3: ADVANCED ALGORITHMS (Days 35–53) ═══════════
  {
    id: 2,
    name: 'Phase 3: Advanced Algorithms',
    desc: 'Recursion, Backtracking, Dynamic Programming & Graphs',
    days: [
      {
        day: 35, title: 'Recursion Fundamentals', topic: 'Recursion',
        tasks: [
          { id: 'd35_learn', type: 'learn', text: 'Learn Recursion & Call Stack', link: 'https://www.youtube.com/watch?v=IJDJ0kBx2LM' },
          { id: 'd35_p1', type: 'problem', text: '#509 Fibonacci Number', difficulty: 'easy', link: 'https://leetcode.com/problems/fibonacci-number/' },
          { id: 'd35_p2', type: 'problem', text: '#50 Pow(x, n)', difficulty: 'medium', link: 'https://leetcode.com/problems/powx-n/' },
        ]
      },
      {
        day: 36, title: 'Subsets & Combinations', topic: 'Backtracking',
        tasks: [
          { id: 'd36_learn', type: 'learn', text: 'Learn Backtracking Pattern', link: 'https://www.youtube.com/watch?v=REOH22Xwdkk' },
          { id: 'd36_p1', type: 'problem', text: '#78 Subsets ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/subsets/' },
          { id: 'd36_p2', type: 'problem', text: '#90 Subsets II', difficulty: 'medium', link: 'https://leetcode.com/problems/subsets-ii/' },
          { id: 'd36_p3', type: 'problem', text: '#77 Combinations', difficulty: 'medium', link: 'https://leetcode.com/problems/combinations/' },
        ]
      },
      {
        day: 37, title: 'Permutations', topic: 'Backtracking',
        tasks: [
          { id: 'd37_p1', type: 'problem', text: '#46 Permutations ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/permutations/' },
          { id: 'd37_p2', type: 'problem', text: '#47 Permutations II', difficulty: 'medium', link: 'https://leetcode.com/problems/permutations-ii/' },
        ]
      },
      {
        day: 38, title: 'Constrained Backtracking', topic: 'Backtracking',
        tasks: [
          { id: 'd38_p1', type: 'problem', text: '#39 Combination Sum', difficulty: 'medium', link: 'https://leetcode.com/problems/combination-sum/' },
          { id: 'd38_p2', type: 'problem', text: '#40 Combination Sum II', difficulty: 'medium', link: 'https://leetcode.com/problems/combination-sum-ii/' },
          { id: 'd38_p3', type: 'problem', text: '#131 Palindrome Partitioning', difficulty: 'medium', link: 'https://leetcode.com/problems/palindrome-partitioning/' },
        ]
      },
      {
        day: 39, title: 'Grid Backtracking', topic: 'Backtracking',
        tasks: [
          { id: 'd39_p1', type: 'problem', text: '#79 Word Search ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/word-search/' },
          { id: 'd39_p2', type: 'problem', text: '#51 N-Queens', difficulty: 'hard', link: 'https://leetcode.com/problems/n-queens/' },
          { id: 'd39_p3', type: 'problem', text: '#37 Sudoku Solver', difficulty: 'hard', link: 'https://leetcode.com/problems/sudoku-solver/' },
        ]
      },
      {
        day: 40, title: '1D DP: Climbing Stairs Pattern', topic: 'Dynamic Programming',
        tasks: [
          { id: 'd40_learn', type: 'learn', text: 'Learn DP Concepts (Overlapping Subproblems)', link: 'https://www.youtube.com/watch?v=oBt53YbR9Kk' },
          { id: 'd40_p1', type: 'problem', text: '#70 Climbing Stairs ⭐', difficulty: 'easy', link: 'https://leetcode.com/problems/climbing-stairs/' },
          { id: 'd40_p2', type: 'problem', text: '#746 Min Cost Climbing Stairs', difficulty: 'easy', link: 'https://leetcode.com/problems/min-cost-climbing-stairs/' },
          { id: 'd40_p3', type: 'problem', text: '#198 House Robber ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/house-robber/' },
        ]
      },
      {
        day: 41, title: '1D DP: Decision Making', topic: 'Dynamic Programming',
        tasks: [
          { id: 'd41_p1', type: 'problem', text: '#213 House Robber II', difficulty: 'medium', link: 'https://leetcode.com/problems/house-robber-ii/' },
          { id: 'd41_p2', type: 'problem', text: '#91 Decode Ways', difficulty: 'medium', link: 'https://leetcode.com/problems/decode-ways/' },
          { id: 'd41_p3', type: 'problem', text: '#139 Word Break ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/word-break/' },
        ]
      },
      {
        day: 42, title: '2D DP: Grid Paths', topic: 'Dynamic Programming',
        tasks: [
          { id: 'd42_learn', type: 'learn', text: 'Learn 2D DP Grid Pattern', link: 'https://www.youtube.com/watch?v=IlEsdxuD4lY' },
          { id: 'd42_p1', type: 'problem', text: '#62 Unique Paths', difficulty: 'medium', link: 'https://leetcode.com/problems/unique-paths/' },
          { id: 'd42_p2', type: 'problem', text: '#64 Minimum Path Sum', difficulty: 'medium', link: 'https://leetcode.com/problems/minimum-path-sum/' },
          { id: 'd42_p3', type: 'problem', text: '#63 Unique Paths II', difficulty: 'medium', link: 'https://leetcode.com/problems/unique-paths-ii/' },
        ]
      },
      {
        day: 43, title: '2D DP: String Matching', topic: 'Dynamic Programming',
        tasks: [
          { id: 'd43_p1', type: 'problem', text: '#1143 Longest Common Subsequence ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/longest-common-subsequence/' },
          { id: 'd43_p2', type: 'problem', text: '#72 Edit Distance ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/edit-distance/' },
        ]
      },
      {
        day: 44, title: 'Subsequence DP', topic: 'Dynamic Programming',
        tasks: [
          { id: 'd44_p1', type: 'problem', text: '#300 Longest Increasing Subsequence ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/longest-increasing-subsequence/' },
          { id: 'd44_p2', type: 'problem', text: '#354 Russian Doll Envelopes', difficulty: 'hard', link: 'https://leetcode.com/problems/russian-doll-envelopes/' },
        ]
      },
      {
        day: 45, title: 'Knapsack Variants', topic: 'Dynamic Programming',
        tasks: [
          { id: 'd45_learn', type: 'learn', text: 'Learn 0/1 Knapsack & Unbounded Knapsack', link: 'https://www.youtube.com/watch?v=8LusJS5-AGo' },
          { id: 'd45_p1', type: 'problem', text: '#416 Partition Equal Subset Sum ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/partition-equal-subset-sum/' },
          { id: 'd45_p2', type: 'problem', text: '#494 Target Sum', difficulty: 'medium', link: 'https://leetcode.com/problems/target-sum/' },
          { id: 'd45_p3', type: 'problem', text: '#322 Coin Change ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/coin-change/' },
        ]
      },
      {
        day: 46, title: 'Interval DP', topic: 'Dynamic Programming',
        tasks: [
          { id: 'd46_p1', type: 'problem', text: '#312 Burst Balloons', difficulty: 'hard', link: 'https://leetcode.com/problems/burst-balloons/' },
          { id: 'd46_p2', type: 'problem', text: '#1547 Min Cost to Cut a Stick', difficulty: 'hard', link: 'https://leetcode.com/problems/minimum-cost-to-cut-a-stick/' },
        ]
      },
      {
        day: 47, title: '🔁 DP Review', topic: 'Review',
        tasks: [
          { id: 'd47_t1', type: 'review', text: 'Re-solve 5 DP problems without looking at solutions' },
          { id: 'd47_p1', type: 'problem', text: '#10 Regular Expression Matching (bonus)', difficulty: 'hard', link: 'https://leetcode.com/problems/regular-expression-matching/' },
          { id: 'd47_t2', type: 'review', text: '✅ Checkpoint: Can identify DP state & transition?' },
        ]
      },
      {
        day: 48, title: 'Graph Basics & BFS', topic: 'Graphs',
        tasks: [
          { id: 'd48_learn', type: 'learn', text: 'Learn Graph Representations & BFS', link: 'https://www.youtube.com/watch?v=tWVWeAqZ0WU' },
          { id: 'd48_p1', type: 'problem', text: '#133 Clone Graph', difficulty: 'medium', link: 'https://leetcode.com/problems/clone-graph/' },
          { id: 'd48_p2', type: 'problem', text: '#994 Rotting Oranges ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/rotting-oranges/' },
        ]
      },
      {
        day: 49, title: 'DFS on Grids', topic: 'Graphs',
        tasks: [
          { id: 'd49_p1', type: 'problem', text: '#200 Number of Islands ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/number-of-islands/' },
          { id: 'd49_p2', type: 'problem', text: '#695 Max Area of Island', difficulty: 'medium', link: 'https://leetcode.com/problems/max-area-of-island/' },
          { id: 'd49_p3', type: 'problem', text: '#417 Pacific Atlantic Water Flow', difficulty: 'medium', link: 'https://leetcode.com/problems/pacific-atlantic-water-flow/' },
        ]
      },
      {
        day: 50, title: 'Topological Sort', topic: 'Graphs',
        tasks: [
          { id: 'd50_learn', type: 'learn', text: 'Learn Topological Sort (Kahn\'s BFS)', link: 'https://www.youtube.com/watch?v=cIBFEhD77b4' },
          { id: 'd50_p1', type: 'problem', text: '#207 Course Schedule ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/course-schedule/' },
          { id: 'd50_p2', type: 'problem', text: '#210 Course Schedule II', difficulty: 'medium', link: 'https://leetcode.com/problems/course-schedule-ii/' },
        ]
      },
      {
        day: 51, title: 'Union-Find (Disjoint Set)', topic: 'Graphs',
        tasks: [
          { id: 'd51_learn', type: 'learn', text: 'Learn Union-Find with Path Compression', link: 'https://www.youtube.com/watch?v=ibjEGG7ylHk' },
          { id: 'd51_p1', type: 'problem', text: '#547 Number of Provinces', difficulty: 'medium', link: 'https://leetcode.com/problems/number-of-provinces/' },
          { id: 'd51_p2', type: 'problem', text: '#684 Redundant Connection', difficulty: 'medium', link: 'https://leetcode.com/problems/redundant-connection/' },
        ]
      },
      {
        day: 52, title: 'Shortest Path (Dijkstra)', topic: 'Graphs',
        tasks: [
          { id: 'd52_learn', type: 'learn', text: 'Learn Dijkstra\'s Algorithm', link: 'https://www.youtube.com/watch?v=_lHSawdgXpI' },
          { id: 'd52_p1', type: 'problem', text: '#743 Network Delay Time', difficulty: 'medium', link: 'https://leetcode.com/problems/network-delay-time/' },
          { id: 'd52_p2', type: 'problem', text: '#787 Cheapest Flights Within K Stops', difficulty: 'medium', link: 'https://leetcode.com/problems/cheapest-flights-within-k-stops/' },
        ]
      },
      {
        day: 53, title: 'Advanced Graphs', topic: 'Graphs',
        tasks: [
          { id: 'd53_p1', type: 'problem', text: '#127 Word Ladder', difficulty: 'hard', link: 'https://leetcode.com/problems/word-ladder/' },
          { id: 'd53_p2', type: 'problem', text: '#332 Reconstruct Itinerary', difficulty: 'hard', link: 'https://leetcode.com/problems/reconstruct-itinerary/' },
          { id: 'd53_t1', type: 'review', text: '✅ Checkpoint: Can model real-world problems as graphs?' },
        ]
      },
    ]
  },

  // ═══════════ PHASE 4: MASTERY (Days 54–60) ═══════════
  {
    id: 3,
    name: 'Phase 4: Mastery',
    desc: 'Greedy, Tries, Mock Interviews & Final Review',
    days: [
      {
        day: 54, title: 'Greedy: Intervals', topic: 'Greedy',
        tasks: [
          { id: 'd54_p1', type: 'problem', text: '#56 Merge Intervals (re-solve for speed)', difficulty: 'medium', link: 'https://leetcode.com/problems/merge-intervals/' },
          { id: 'd54_p2', type: 'problem', text: '#435 Non-overlapping Intervals', difficulty: 'medium', link: 'https://leetcode.com/problems/non-overlapping-intervals/' },
          { id: 'd54_p3', type: 'problem', text: '#57 Insert Interval', difficulty: 'medium', link: 'https://leetcode.com/problems/insert-interval/' },
        ]
      },
      {
        day: 55, title: 'Greedy: Strategy', topic: 'Greedy',
        tasks: [
          { id: 'd55_p1', type: 'problem', text: '#55 Jump Game ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/jump-game/' },
          { id: 'd55_p2', type: 'problem', text: '#45 Jump Game II', difficulty: 'medium', link: 'https://leetcode.com/problems/jump-game-ii/' },
          { id: 'd55_p3', type: 'problem', text: '#134 Gas Station', difficulty: 'medium', link: 'https://leetcode.com/problems/gas-station/' },
        ]
      },
      {
        day: 56, title: 'Trie (Prefix Tree)', topic: 'Trie',
        tasks: [
          { id: 'd56_learn', type: 'learn', text: 'Learn Trie Data Structure', link: 'https://www.youtube.com/watch?v=oobqoCJlHA0' },
          { id: 'd56_p1', type: 'problem', text: '#208 Implement Trie ⭐', difficulty: 'medium', link: 'https://leetcode.com/problems/implement-trie-prefix-tree/' },
          { id: 'd56_p2', type: 'problem', text: '#211 Design Add & Search Words', difficulty: 'medium', link: 'https://leetcode.com/problems/design-add-and-search-words-data-structure/' },
          { id: 'd56_p3', type: 'problem', text: '#212 Word Search II', difficulty: 'hard', link: 'https://leetcode.com/problems/word-search-ii/' },
        ]
      },
      {
        day: 57, title: '🎯 Mock Interview #1', topic: 'Mock Interview',
        tasks: [
          { id: 'd57_t1', type: 'review', text: 'Pick 2 random Medium + 1 Hard, solve in 60 min' },
          { id: 'd57_t2', type: 'review', text: 'Practice explaining approach out loud' },
          { id: 'd57_t3', type: 'review', text: 'Review: What went well? What needs work?' },
        ]
      },
      {
        day: 58, title: '🔁 Weak Area Deep Dive', topic: 'Review',
        tasks: [
          { id: 'd58_t1', type: 'review', text: 'Re-solve your top 10 hardest problems' },
          { id: 'd58_t2', type: 'review', text: 'Focus on problems where you needed hints' },
        ]
      },
      {
        day: 59, title: '🎯 Mock Interview #2', topic: 'Mock Interview',
        tasks: [
          { id: 'd59_t1', type: 'review', text: 'Use LeetCode Mock Interview or Pramp', link: 'https://www.pramp.com/' },
          { id: 'd59_t2', type: 'review', text: 'Practice under real conditions (webcam, timer)' },
        ]
      },
      {
        day: 60, title: '🏁 Final Review & Celebration!', topic: 'Review',
        tasks: [
          { id: 'd60_t1', type: 'review', text: 'Review all templates & pattern cheat sheet' },
          { id: 'd60_t2', type: 'review', text: 'Light practice: 2–3 Easy/Medium for confidence' },
          { id: 'd60_t3', type: 'review', text: '🎉 DONE! You\'re interview ready! Go crush it!' },
        ]
      },
    ]
  }
];

// ─── State ───
let progress = { completedTasks: [], notes: {}, startDate: '', lastActive: '' };
let activePhase = 0;
let saveTimeout = null;

// ─── Init ───
document.addEventListener('DOMContentLoaded', async () => {
  await loadProgress();
  renderPhase(0);
  updateStats();
  bindSidebar();
  setTimeout(() => document.getElementById('loader').classList.add('hidden'), 500);
});

// ─── API ───
async function loadProgress() {
  try {
    const res = await fetch('/api/progress');
    progress = await res.json();
  } catch (e) {
    console.warn('Could not load progress, using defaults');
  }
}

async function toggleTask(taskId) {
  try {
    const res = await fetch('/api/toggle', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ taskId })
    });
    const data = await res.json();
    progress.completedTasks = data.completedTasks;
    updateStats();
    updateDayCardState();
    showToast(data.completed ? '✅ Task completed!' : '↩️ Task unchecked');
  } catch (e) {
    console.error('Failed to toggle task', e);
  }
}

function saveNote(dayId, note) {
  clearTimeout(saveTimeout);
  saveTimeout = setTimeout(async () => {
    try {
      await fetch('/api/notes', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ dayId, note })
      });
      progress.notes[dayId] = note;
      const indicator = document.querySelector(`#notes-saved-${dayId}`);
      if (indicator) {
        indicator.classList.add('show');
        setTimeout(() => indicator.classList.remove('show'), 2000);
      }
    } catch (e) {
      console.error('Failed to save note', e);
    }
  }, 800);
}

// ─── Rendering ───
function renderPhase(phaseIdx) {
  activePhase = phaseIdx;
  const phase = PLAN[phaseIdx];
  document.getElementById('phase-title').textContent = phase.name;
  document.getElementById('phase-desc').textContent = phase.desc;

  const container = document.getElementById('days-container');
  container.innerHTML = '';

  phase.days.forEach(day => {
    const card = createDayCard(day);
    container.appendChild(card);
  });

  // Update active sidebar button
  document.querySelectorAll('.phase-btn').forEach(btn => {
    btn.classList.toggle('active', parseInt(btn.dataset.phase) === phaseIdx);
  });
}

function createDayCard(day) {
  const completedCount = day.tasks.filter(t => progress.completedTasks.includes(t.id)).length;
  const totalCount = day.tasks.length;
  const allDone = completedCount === totalCount;
  const pct = totalCount > 0 ? Math.round((completedCount / totalCount) * 100) : 0;

  const card = document.createElement('div');
  card.className = `day-card${allDone ? ' completed' : ''}`;
  card.id = `day-${day.day}`;

  card.innerHTML = `
    <div class="day-header" onclick="toggleDayCard(${day.day})">
      <div class="day-number">${day.day}</div>
      <div class="day-meta">
        <div class="day-title">${day.title}</div>
        <div class="day-topic">${day.topic}</div>
      </div>
      <div class="day-progress-info">
        <div class="day-progress-mini">
          <div class="day-progress-mini-fill" style="width: ${pct}%"></div>
        </div>
        <span>${completedCount}/${totalCount}</span>
      </div>
      <span class="day-chevron">▼</span>
    </div>
    <div class="day-body">
      <div class="day-content">
        ${renderTaskSections(day)}
        <div class="day-notes">
          <label for="note-${day.day}">📝 Notes & Key Insights</label>
          <textarea id="note-${day.day}" placeholder="Write what you learned today..." oninput="saveNote('day${day.day}', this.value)">${progress.notes['day' + day.day] || ''}</textarea>
          <div class="notes-saved" id="notes-saved-day${day.day}">✓ Saved</div>
        </div>
      </div>
    </div>
  `;
  return card;
}

function renderTaskSections(day) {
  const learns = day.tasks.filter(t => t.type === 'learn');
  const problems = day.tasks.filter(t => t.type === 'problem');
  const reviews = day.tasks.filter(t => t.type === 'review');

  let html = '';

  if (learns.length > 0) {
    html += `<div class="task-section">
      <div class="task-section-title">📖 Learn</div>
      ${learns.map(t => renderTaskItem(t)).join('')}
    </div>`;
  }

  if (problems.length > 0) {
    html += `<div class="task-section">
      <div class="task-section-title">💻 Problems to Solve</div>
      ${problems.map(t => renderTaskItem(t)).join('')}
    </div>`;
  }

  if (reviews.length > 0) {
    html += `<div class="task-section">
      <div class="task-section-title">🔁 Review & Checkpoints</div>
      ${reviews.map(t => renderTaskItem(t)).join('')}
    </div>`;
  }

  return html;
}

function renderTaskItem(task) {
  const done = progress.completedTasks.includes(task.id);
  const diffBadge = task.difficulty
    ? `<span class="badge badge-${task.difficulty}">${task.difficulty}</span>`
    : task.type === 'learn'
      ? '<span class="badge badge-learn">Learn</span>'
      : '<span class="badge badge-review">Review</span>';

  const linkBtn = task.link
    ? `<a href="${task.link}" target="_blank" rel="noopener" class="task-link" onclick="event.stopPropagation()">
        ${task.type === 'problem' ? '🔗 LeetCode' : '▶️ Resource'}
       </a>`
    : '';

  return `
    <div class="task-item ${done ? 'done' : ''}" onclick="handleTaskClick('${task.id}')">
      <div class="task-checkbox"></div>
      <span class="task-text">${task.text}</span>
      ${diffBadge}
      ${linkBtn}
    </div>
  `;
}

// ─── Interactions ───
function handleTaskClick(taskId) {
  toggleTask(taskId);
}

function toggleDayCard(dayNum) {
  const card = document.getElementById(`day-${dayNum}`);
  card.classList.toggle('expanded');
}

function bindSidebar() {
  document.querySelectorAll('.phase-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      renderPhase(parseInt(btn.dataset.phase));
      window.scrollTo({ top: 0, behavior: 'smooth' });
    });
  });
}

// ─── Stats ───
function updateStats() {
  const allTasks = PLAN.flatMap(p => p.days.flatMap(d => d.tasks));
  const totalTasks = allTasks.length;
  const completedCount = progress.completedTasks.length;
  const pct = totalTasks > 0 ? Math.round((completedCount / totalTasks) * 100) : 0;

  // Overall progress
  document.getElementById('ring-text').textContent = pct + '%';
  document.getElementById('ring-fill').setAttribute('stroke-dasharray', `${pct}, 100`);
  document.getElementById('stat-overall').textContent = `${completedCount}/${totalTasks}`;

  // Current day (based on start date)
  const start = new Date(progress.startDate || new Date().toISOString().split('T')[0]);
  const today = new Date();
  const diffDays = Math.floor((today - start) / (1000 * 60 * 60 * 24)) + 1;
  document.getElementById('stat-day').textContent = Math.min(diffDays, 60);

  // Streak (simple: count consecutive days from today backwards)
  document.getElementById('stat-streak').textContent = calculateStreak() + ' days';

  // Problems solved (type = problem only)
  const problemTasks = allTasks.filter(t => t.type === 'problem');
  const solvedProblems = problemTasks.filter(t => progress.completedTasks.includes(t.id)).length;
  document.getElementById('stat-problems').textContent = `${solvedProblems}/${problemTasks.length}`;

  // Phase progress bars
  PLAN.forEach((phase, idx) => {
    const phaseTasks = phase.days.flatMap(d => d.tasks);
    const phaseDone = phaseTasks.filter(t => progress.completedTasks.includes(t.id)).length;
    const phasePct = phaseTasks.length > 0 ? Math.round((phaseDone / phaseTasks.length) * 100) : 0;
    const bar = document.getElementById(`phase-prog-${idx}`);
    if (bar) bar.style.width = phasePct + '%';
  });
}

function calculateStreak() {
  if (!progress.completedTasks || progress.completedTasks.length === 0) return 0;
  // Simple streak: number of distinct days that have at least 1 task completed
  const daySet = new Set();
  progress.completedTasks.forEach(id => {
    const match = id.match(/^d(\d+)/);
    if (match) daySet.add(parseInt(match[1]));
  });
  // Count consecutive from day 1
  let streak = 0;
  for (let d = 1; d <= 60; d++) {
    if (daySet.has(d)) streak++;
    else break;
  }
  return streak;
}

function updateDayCardState() {
  const phase = PLAN[activePhase];
  phase.days.forEach(day => {
    const card = document.getElementById(`day-${day.day}`);
    if (!card) return;

    const completedCount = day.tasks.filter(t => progress.completedTasks.includes(t.id)).length;
    const totalCount = day.tasks.length;
    const allDone = completedCount === totalCount;
    const pct = totalCount > 0 ? Math.round((completedCount / totalCount) * 100) : 0;

    card.classList.toggle('completed', allDone);

    // Update mini progress
    const fill = card.querySelector('.day-progress-mini-fill');
    if (fill) fill.style.width = pct + '%';
    const countSpan = card.querySelector('.day-progress-info span');
    if (countSpan) countSpan.textContent = `${completedCount}/${totalCount}`;

    // Update individual checkboxes
    day.tasks.forEach(task => {
      const done = progress.completedTasks.includes(task.id);
      const items = card.querySelectorAll('.task-item');
      items.forEach(item => {
        if (item.getAttribute('onclick')?.includes(task.id)) {
          item.classList.toggle('done', done);
        }
      });
    });
  });
}

// ─── Toast ───
let toastTimer = null;
function showToast(message) {
  let toast = document.querySelector('.toast');
  if (!toast) {
    toast = document.createElement('div');
    toast.className = 'toast';
    document.body.appendChild(toast);
  }
  toast.textContent = message;
  clearTimeout(toastTimer);
  requestAnimationFrame(() => {
    toast.classList.add('show');
    toastTimer = setTimeout(() => toast.classList.remove('show'), 2000);
  });
}
