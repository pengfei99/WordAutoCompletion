package org.pengfei.struct;

import java.util.ArrayList;
import java.util.List;

public class CharTrieNode<Character> implements TriePosition<Character> {

    private Character element;
    private List<TriePosition<Character>> children;
    private boolean isWord;

    public CharTrieNode(Character value) {
        this.element = value;
        children = new ArrayList<>(26);
        isWord = false;
    }

    @Override
    public Character getElement() {
        return this.element;
    }

    @Override
    public List<TriePosition<Character>> getChildren() {
        return this.children;
    }

    public boolean isWord() {
        return isWord;
    }

    public void setWord(boolean word) {
        isWord = word;
    }

    @Override
    public void setElement(Character character) {
        this.element = character;
    }

    @Override
    public void addChild(TriePosition<Character> child) {
        char c = (char) child.getElement();
        int index = c - 'a';
        children.add(index, child);
    }

    public void setChildren(List<TriePosition<Character>> children) {
        this.children = children;
    }
}
