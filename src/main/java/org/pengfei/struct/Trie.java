package org.pengfei.struct;

import java.util.Locale;

public class Trie implements IWordTrie {
    private final TrieNode root;

    public Trie() {
        // Use a special value as the root node value to distinguish with other char node from 'a' to 'z'
        root = new TrieNode('\0');
    }

    @Override
    public void insert(String word) {
        TrieNode current = root;
        //convert to lower case
        String lWord = word.toLowerCase();
        for (int i = 0; i < lWord.length(); i++) {
            char val = lWord.charAt(i);
            // if val in the child list, get the child node, and set current to the child node
            // else create a new node with val, then add the node to the current node child list
            // set current to the new node.
            // note that 'a'-'a'=0, 'z'-'a'=25. So char in the child list is sorted.
            if (current.getChildren().containsKey(val - 'a')) {
                current=current.getChild(val - 'a');}
            else {
                TrieNode child = new TrieNode(val);
                current.addChild(child);
                current=child;
            }
        }
        //after the for loop, the current node is the last character of the word. So we set this node
        // isWord to true.
        current.setWord(true);
    }

    @Override
    public boolean startsWith(String prefix) {
        return this.getNode(prefix) != null && !this.getNode(prefix).isWord();
    }

    @Override
    public boolean containWord(String word) {
        return this.getNode(word) != null && this.getNode(word).isWord();
    }

    //This helper class gets the node in trie which is the last character of input.
    //If such node does not exist return null
    private TrieNode getNode(String input) {
        TrieNode current = root;
        //convert to lower case
        String lInput = input.toLowerCase();
        for (int i = 0; i < lInput.length(); i++) {
            char val = lInput.charAt(i);
            // if val not in child list, it means such node does not exist. We break the loop and return null
            // else reset current to the matching child node, and continue loop
            // note that 'a'-'a'=0, 'z'-'a'=25. So char in the child list is sorted.
            if (current.getChildren().containsKey(val - 'a')) {
                current=current.getChild(val-'a');
            }
            else return null;
        }
        // after loop, we found the node that matches the last char of input.
        // it means the trie contains the input, but it may be a word or prefix of another word which depends on
        // if the last node isWord is true or not
        return current;
    }

    public static void main(String[] args){
        Trie t=new Trie();
        String[] words={"project runway","pinterest","river","kayak","progenex","progeria","pg&e",
                "project free tv","bank","proactive","progesterone","press democrat","priceline",
                "pandora","reprobe","paypal"};
        for (String word : words) {
            t.insert(word);
        }
        boolean res=t.startsWith("pr");
        System.out.println("Res value: "+res);
    }
}
