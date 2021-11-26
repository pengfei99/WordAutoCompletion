package org.pengfei;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pengfei.struct.Trie;

import static org.junit.jupiter.api.Assertions.*;

public class TrieTest {
    Trie t=new Trie();
    String[] words={"project runway","pinterest","river","kayak","progenex","progeria","pg&e",
            "project free tv","bank","proactive","progesterone","press democrat","priceline",
            "pandora","reprobe","paypal"};

    @BeforeEach
    void setUp() {
        for (String word : words) {
            t.insert(word);
        }
    }

    @Test
    @DisplayName("Test simple word contain with positive case")
    void testContainWordWithSimpleWord(){
        assertTrue(t.containWord("bank"),"The word bank is inside the trie, should return ture");
    }
    @Test
    @DisplayName("Test simple word contain with negative case")
    void testContainWordWithNonExistentWord(){
        assertFalse(t.containWord("foobar"),"The word foobar is not inside the trie, returns false");
    }

    @Test
    @DisplayName("Test wordContain method with words that has space")
    void testContainWordWithSpace(){
        assertTrue(t.containWord("project runway"),"Project runway is inside the trie, returns ture");
    }

    @Test
    @DisplayName("Test wordContain method with words that has special character")
    void testContainWordWithSpecialChar(){
        assertTrue(t.containWord("pg&e"),"The word pg&e is inside the trie, returns ture");
    }

    @Test
    @DisplayName("Test startsWith method with simple prefix positive case")
    void testStartsWithSimplePrefix(){
        assertTrue(t.startsWith("pg"),"There are words starts with pg, returns ture");
    }

    @Test
    @DisplayName("Test startsWith method with non existent prefix")
    void testStartsWithNonExistentPrefix(){
        assertFalse(t.startsWith("bac"),"There are no words starts with pg, returns false");
    }

    @Test
    @DisplayName("Test startsWith method with words that has space")
    void testStartsWithWithSpace(){
        assertTrue(t.startsWith("project"),"There are words starts with project, returns ture");
    }

    @Test
    @DisplayName("Test startsWith method with simple prefix")
    void testStartsWithSpecialCharPrefix(){
        assertTrue(t.startsWith("pg&"),"There are words starts with pg&, returns ture");
    }



}
