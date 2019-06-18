package bearmaps.proj2ab;

import java.util.LinkedList;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class MyTrieSet  {
    private Node root;

    public MyTrieSet(){
        root = new Node('a', false);
    }

    protected class Node{
        char ch;
        boolean isKey;
        List<bearmaps.hw4.streetmap.Node> details = new ArrayList<>();
        MyHashMap<Node, Boolean> next = new MyHashMap<>();

        public Node(char c, boolean bool){
            ch = c;
            isKey = bool;
        }

        public Node(char c, boolean bool, ArrayList<bearmaps.hw4.streetmap.Node> dts){
            details =dts;
            ch = c;
            isKey = bool;
        }

        @Override
        public int hashCode(){
            return ch;
        }

        @Override
        public boolean equals(Object o){
            if (o == null || getClass() != o.getClass()) {
                return false;}
            Node ob = (Node) o;
            if (this.ch == ob.ch && this.isKey == ob.isKey){
                return true;
            }
            else {return false;}

        }
    }


    public void clear() {
        root.next = null;

    }


    public boolean contains(String key) {
        MyHashMap<Node, Boolean> track = root.next;
        if (root.next == null) {
            return false;
        }
        int j = 0;
        while (j < key.length()){
            Node c = null;
            if (j == key.length()-1){
                c = new Node(key.charAt(j), true);
            }
            else{
                c = new Node(key.charAt(j), false);
            }

            if (!track.containsKey(c)) {
                return false;
            }
            track = track.getKey(c).next;
            j++;
        }
        return true;
    }


    public void add(String key, ArrayList<bearmaps.hw4.streetmap.Node> o) {
        if (contains(key)){
            return;
        }
        MyHashMap<Node, Boolean> trace = root.next;
        for (int i = 0; i < key.length(); i++) {
            Node n = null;
            if (i == key.length()-1) {
                n = new Node(key.charAt(i), true, o);

            }
            else {
                n = new Node(key.charAt(i), false);
            }
            if (!trace.containsKey(n)){
                trace.put(n, true);
            }


            trace = trace.getKey(n).next;
        }

    }

    public List<bearmaps.hw4.streetmap.Node> getStringNode(String s) {
        Node track = root;
        if (root.next == null) {
            return null;
        }
        int j = 0;
        while (j < s.length()){
            Node c = null;
            if (j == s.length()-1){
                c = new Node(s.charAt(j), true);
            }
            else{
                c = new Node(s.charAt(j), false);
            }

            if (!track.next.containsKey(c)) {
                return null;
            }
            track = track.next.getKey(c);
            j++;
        }
        return track.details;
    }


    public List<String> keysWithPrefix(String prefix) {
        MyHashMap<Node, Boolean> trace = root.next;
        int j = 0;
        while (j < prefix.length()){
            Node n = new Node(prefix.charAt(j), false);
            if (trace.containsKey(n)){
                trace = trace.getKey(n).next;
                j++;
            }
            else {return new LinkedList<>();}
        }
        ArrayList<String> out = new ArrayList<>();

        Set<Node> nodes = trace.keySet();
        for (Node n : nodes) {
            if (n.isKey) {
                out.add(prefix + n.ch);
            }
            out.addAll(keysWithPrefix(prefix + n.ch));
        }
        return out;
    }

    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        MyTrieSet tr = new MyTrieSet();



    }
}

