package com.temp.user.matecomputacional_huffman;

// https://www.geeksforgeeks.org/huffman-coding-greedy-algo-3/

import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ArrayList;

class HuffmanNode {
    int data;
    char c;

    HuffmanNode left;
    HuffmanNode right;
}

class MyComparator implements Comparator<HuffmanNode> {

    public int compare(HuffmanNode x, HuffmanNode y) {
        return x.data - y.data;
    }
}

class Compress {

    static HashMap<Character, String> code;

    public static void recCode(HuffmanNode root, String s) {

        if (root.left == null && root.right == null) {

            code.put(root.c, s);
            return;
        }

        recCode(root.left, s + "0");
        recCode(root.right, s + "1");
    }

    public static HashMap<Character, String> compress(String ss) {

        HashMap<Character, Integer> map = new HashMap<Character, Integer>();

        for (int i = 0; i < ss.length(); i++) {
            char c = ss.charAt(i);
            Integer val = map.get(c);
            if (val != null) {
                map.put(c, new Integer(val + 1));
            }
            else {
                map.put(c, 1);
            }
        }

        ArrayList<Character> charVec = new ArrayList<Character>();
        ArrayList<Integer> cfreq = new ArrayList<Integer>();

        for (HashMap.Entry<Character, Integer> entry : map.entrySet()) {
            charVec.add(entry.getKey());
            cfreq.add(entry.getValue());
        }

        int n = charVec.size();

        PriorityQueue<HuffmanNode> q
                = new PriorityQueue<HuffmanNode>(n, new MyComparator());

        for (int i = 0; i < n; i++) {

            HuffmanNode hn = new HuffmanNode();

            hn.c = charVec.get(i);
            hn.data = cfreq.get(i);

            hn.left = null;
            hn.right = null;

            q.add(hn);
        }

        HuffmanNode root = null;

        while (q.size() > 1) {

            HuffmanNode x = q.peek();
            q.poll();

            HuffmanNode y = q.peek();
            q.poll();

            HuffmanNode f = new HuffmanNode();

            f.data = x.data + y.data;
            f.c = '-';

            f.left = x;

            f.right = y;

            root = f;

            q.add(f);
        }
        code = new HashMap<Character, String>();

        recCode(root, "");
        return code;
    }
}