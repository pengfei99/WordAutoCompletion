package org.pengfei.struct;

import java.util.*;

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
            if (current.getChildren().containsKey(val)) {
                current = current.getChild(val);
            } else {
                TrieNode child = new TrieNode(val);
                current.addChild(child);
                current = child;
            }
        }
        //after the for loop, the current node is the last character of the word. So we set this node
        // isWord to true.
        current.setWord(true);
    }

    /**
     * Returns a boolean to indicate if the given word exists in the Trie or not if yes return true, otherwise return false
     * <p>
     *
     * @param prefix a prefix of a word
     * @return a boolean value, if the given prefix exists and the last character node has child then return true,
     * otherwise return false
     * @see Trie
     */
    @Override
    public boolean startsWith(String prefix) {
        return this.getNode(prefix) != null && !this.getNode(prefix).isWord();
    }

    /**
     * Returns a boolean to indicate if the given word exists in the Trie or not if yes return true, otherwise return false
     * <p>
     *
     * @param word a word that you are searching
     * @return a boolean value, if the given word exists in the Trie then return true, otherwise return false
     * @see Trie
     */
    @Override
    public boolean containWord(String word) {
        return this.getNode(word) != null && this.getNode(word).isWord();
    }

    //This helper class gets the node in trie which is the last character of input.
    //If such node does not exist return null

    /**
     * Returns a TrieNode that starts with the given prefix, if no such word exist in the trie, return null
     * <p>
     *
     * @param input a prefix of a word
     * @return a list of words that starts with the given prefix
     * @see Trie
     */
    private TrieNode getNode(String input) {
        TrieNode current = root;
        //convert to lower case
        String lInput = input.toLowerCase();
        for (int i = 0; i < lInput.length(); i++) {
            char val = lInput.charAt(i);
            // if val not in child list, it means such node does not exist. We break the loop and return null
            // else reset current to the matching child node, and continue loop
            // note that 'a'-'a'=0, 'z'-'a'=25. So char in the child list is sorted.
            if (current.getChildren().containsKey(val)) {
                current = current.getChild(val);
            } else return null;
        }
        // after loop, we found the node that matches the last char of input.
        // it means the trie contains the input, but it may be a word or prefix of another word which depends on
        // if the last node isWord is true or not
        return current;
    }

    /**
     * Returns a List words that starts with the given prefix, if no such word exist in the trie, return null
     * <p>
     *
     * @param prefix a prefix of a word
     * @return a list of words that starts with the given prefix
     * @see Trie
     */
    public List<String> getAllWordsStartWith(String prefix) {
        return getNFirstWordsStartWith(prefix, Integer.MAX_VALUE);
    }


    /**
     * Returns a List words that starts with the given prefix, if no such word exist in the trie, return null
     * <p>
     *
     * @param prefix a prefix of a word
     * @return a list of words that starts with the given prefix
     * @see Trie
     */
    public List<String> getNFirstWordsStartWith(String prefix, int maxNum) {
        List<String> resultWords = new ArrayList<>();
        StringBuffer currentPath = new StringBuffer();
        currentPath.append(prefix);
        // Get the node of the last character of the prefix
        TrieNode start = this.getNode(prefix);
        if (start != null) {
            getNFirstChildrenPathByAlphaOrder(start, resultWords, currentPath, maxNum);
            return resultWords;
        } else return null;
    }

    /**
     * Returns a List words that starts with the given prefix, if no such word exist in the trie, return null
     * <p>
     *
     * @param start       The starting node to find the completion of a prefix of a word
     * @param resultWords The resulting list to store all words that is the completion of the prefix
     * @param currentPath A string buffer to store the path of the current node. (e.g. p->r->o, currentPath="pro")
     */
    private void getNFirstChildrenPathByAlphaOrder(TrieNode start, List<String> resultWords, StringBuffer currentPath, int maxNum) {
        // Step1: if the start node is a word, add the path of the start node to the list
        if (start.isWord()) {
            resultWords.add(currentPath.toString());
        }
        // Step2: if the start node has no child, stop the process
        if (start.getChildren() == null || start.getChildren().isEmpty())
            return;
        // Step3: if the start node has children. Call getChildrenPathByAlphaOrder on all children with alphabet order
        //get all the children
        Map<Character, TrieNode> children = start.getChildren();
        // sort the children in alphabet order
        Character[] keys = children.keySet().toArray(new Character[0]);
        Arrays.sort(keys);
        for (Character key : keys) {
            if (resultWords.size() >= maxNum) break;
            else {
                TrieNode child = children.get(key);
                // add the value of child node to the currentPath
                getNFirstChildrenPathByAlphaOrder(child, resultWords, currentPath.append(child.getValue()), maxNum);
                currentPath.setLength(currentPath.length() - 1);
            }
        }
    }

    public static void main(String[] args) {
        Trie t = new Trie();
        String[] words = {"project runway", "pinterest", "river", "kayak", "progenex", "progeria", "pg&e",
                "project free tv", "bank", "proactive", "progesterone", "press democrat", "priceline",
                "pandora", "reprobe", "paypal"};
        for (String word : words) {
            t.insert(word);
        }
        boolean res = t.startsWith("pr");
        System.out.println("Res value: " + res);

        List<String> autoComplete = t.getNFirstWordsStartWith("pr", 4);
        System.out.println(autoComplete);

        List<String> autoCompleteFull = t.getAllWordsStartWith("pr");
        System.out.println(autoCompleteFull);
    }
}
