package org.pengfei.struct;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    private final char value;
    private Map<Character, TrieNode> children;
    private boolean isWord;

    public TrieNode(char value) {
        this.value = value;
        this.children = new HashMap<>();
        this.isWord = false;
    }

    public char getValue() {
        return value;
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public TrieNode getChild(Character index){
        return this.children.get(index);
    }


    public void addChild(TrieNode node) {
        this.children.put(node.getValue(), node);
    }

    public boolean isWord() {
        return isWord;
    }

    public void setWord(boolean word) {
        isWord = word;
    }
}
