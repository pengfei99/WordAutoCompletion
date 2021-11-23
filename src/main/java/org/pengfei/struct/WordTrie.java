package org.pengfei.struct;

public class WordTrie implements IWordTrie {
    private CharTrieNode root;

    public WordTrie() {
        //We use char 0 as the root node value, other numeric char can be used instead of 0
        root = new CharTrieNode('\0');
    }

    @Override
    public void insert(String word) {
        CharTrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char c=word.charAt(i);
            // if the current character is not in the children list, create a new node and add it to the children list
            if(!current.getChildren().contains(c)) current.addChild(new CharTrieNode(c));
            // if it exists, do nothing
        }
    }

    @Override
    public boolean startsWith(String prefix) {


    }
}
