package com.example.demo.test;

import java.util.*;

public class DemoTest {

    class Solution {

        String[] splits;
        String[] operators;
        List<Integer> sol;
        Map<Integer, List<Integer>> cache;

        public List<Integer> diffWaysToCompute(String expression) {
            splits = expression.split("[+\\-*/]");
            operators = expression.split("[^+\\-*/]+");
            if(true){
                if(operators[2].equals("")){
                    return Arrays.asList(splits.length);
                }
            }
            sol = new ArrayList();
            cache = new HashMap();
            backtrack(operators.length, 0, Integer.valueOf(splits[0]));
            return sol;
        }

        private List<Integer> backtrack(int len, int i, int cur){
            if(i == len) {
                return new ArrayList();
            }
            if(cache.containsKey(i)) return cache.get(i);
            List<Integer> curSol = new ArrayList();
            int with = compute(operators[i], cur, Integer.valueOf(splits[i+1]));
            for(Integer next: backtrack(len, i+1, Integer.valueOf(splits[i+1]))){
                curSol.add(compute(operators[i], cur, next));
            }
            for(Integer next: backtrack(len, i+1, with)){
                curSol.add(next);
            }
            cache.put(i, curSol);
            return curSol;
        }

        private int compute(String operator, int a, int b){
            if(operator.equals("*")){
                return a*b;
            }
            if(operator.equals("+")){
                return a+b;
            }
            return a-b;
        }

    }

    public static void main(String[] args) {
        new DemoTest().runL();
    }

    private void runL(){
        List<Integer> sol = new Solution().diffWaysToCompute("2-1-1");
    }

    class Node implements Comparable<Node> {
        String list;
        Integer rank;
        public Node(String list, Integer rank){
            this.list = list;
            this.rank = rank;
        }
        @Override
        public int compareTo(Node o) {
            int d = rank - o.rank;
            if(d == 0){
                return list.compareTo(o.list);
            }
            return d;
        }
    }

    private void run(){

        Map<String, List<String>> input = new HashMap();
        input.put("2016-baby-center-girls", Arrays.asList("Annie", "Emma", "Aloc"));
        input.put("2016-baby-center-boys", Arrays.asList("Jackson", "An", "Lucas"));
        input.put("2015-baby-center-girls", Arrays.asList("Annie", "Emma", "Anntye"));

        Map<String, TreeSet<Node>> map = new HashMap();

        Set<String> names = new HashSet();

        TrieNode head = new TrieNode('#');
        for(String key: input.keySet()){
            int index = 1;
            for(String name: input.get(key)){
                if(!names.contains(name)){
                    names.add(name);
                    addToDictionary(name, 0, head);
                }
                TreeSet<Node> nodes = map.getOrDefault(name, new TreeSet());
                nodes.add(new Node(key, index++));
                map.put(name, nodes);
            }
        }

        String query = "";
        for(String word: prefix(query, head)){
            System.out.println(word);
            TreeSet<Node> sol = map.get(word);
            for(Node node: sol){
                System.out.println(node.list + " " + node.rank);
            }
        }


    }

    private List<String> prefix(String prefix, TrieNode head){
        int i=0;
        List<String> sol = new ArrayList();
        while(i < prefix.length()){
            Map<Character, TrieNode> children = head.children;
            if(children.containsKey(prefix.charAt(i))){
                head = children.get(prefix.charAt(i++));
            } else {
                return sol;
            }
        }
        backtrack(prefix, head, sol);
        return sol;
    }

    private void backtrack(String prefix, TrieNode node, List<String> sol){
        if(node.flag) {
            sol.add(prefix);
        }
        for(TrieNode child: node.children.values()){
            backtrack(prefix + child.c, child, sol);
        }
    }

    private void addToDictionary(String name, int i, TrieNode head){
        Map<Character, TrieNode> children = head.children;
        if(children.containsKey(name.charAt(i))){
            if(i < name.length() - 1) {
                addToDictionary(name, i+1, children.get(name.charAt(i)));
            } else {
                TrieNode node = children.get(name.charAt(i));
                node.flag = true;
            }
        } else {
            TrieNode node = new TrieNode(name.charAt(i));
            children.put(name.charAt(i), node);
            if(i == name.length() - 1) {
                node.flag = true;
            } else {
                addToDictionary(name, i+1, node);
            }
        }
    }

    class TrieNode  {
        public char c;
        public boolean flag;
        public Map<Character, TrieNode> children;
        public TrieNode(char c){
            this.c = c;
            this.flag = false;
            this.children = new HashMap();
        }
    }

}
