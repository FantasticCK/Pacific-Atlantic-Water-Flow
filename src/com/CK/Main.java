package com.CK;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {

    public static void main(String[] args) {
        new Solution().pacificAtlantic(new int[][]{{1,2,3},{8,9,4},{7,6,5}});
    }
}

class Solution {
    private static final int[][] d = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        List<List<Integer>> res = new ArrayList<>();
        if (matrix.length == 0 || matrix[0].length == 0)
            return res;
        int n = matrix.length, m = matrix[0].length;
        int[][] dp = new int[n][m];
        Queue<int[]> q = new LinkedList<>();
        initialize(dp, 0, 0, q);
        bfs(matrix, dp, q, 1, n * m);

        q = new LinkedList<>();
        initialize(dp, n - 1, m - 1, q);
        bfs(matrix, dp, q, 2, n * m);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (dp[i][j] == (1 << 3) - 1) {
                    List<Integer> temp = new ArrayList<>();
                    temp.add(i);
                    temp.add(j);
                    res.add(temp);
                }
            }
        }
        return res;
    }

    private void initialize(int[][] dp, int targetRow, int targetCol, Queue<int[]> q) {
        int n = dp.length, m = dp[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dp[i][j] &= 6;
                if (i == targetRow || j == targetCol) {
                    q.offer(new int[]{i, j});
                }
            }
        }
    }
    private void bfs(int[][] matrix, int[][] dp, Queue<int[]> q, int flag, int total) {
        while (!q.isEmpty()) {
            int[] curr = q.poll();
            if (!isValid(dp, curr[0], curr[1]))
                continue;
            if (total == 0)
                return;
            dp[curr[0]][curr[1]] |= 1;
            dp[curr[0]][curr[1]] |= (1 << flag);
            total--;
            for (int i = 0; i < 4; i++) {
                int nr = curr[0] + d[i][0], nc = curr[1] + d[i][1];
                if (isValid(dp, nr, nc) && ( matrix[nr][nc] >= matrix[curr[0]][curr[1]] )) {
                    q.offer(new int[]{nr, nc});
                }
            }
        }
    }

    private boolean isValid(int[][] dp, int r, int c) {
        int n = dp.length, m = dp[0].length;
        return r >= 0 && c >= 0 && r < n && c < m && (dp[r][c] & 1) == 0;
    }
}