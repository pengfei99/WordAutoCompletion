package org.pengfei.struct;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrieTest {
    Trie t = new Trie();
    String[] words = {"project runway", "pinterest", "river", "kayak", "progenex", "progeria", "pg&e",
            "project free tv", "bank", "proactive", "progesterone", "press democrat", "priceline",
            "pandora", "reprobe", "paypal"};

    @BeforeEach
    void setUp() {
        for (String word : words) {
            t.insert(word);
        }
    }

    @Test
    @DisplayName("Test simple word contain with positive case")
    void testContainWordWithSimpleWord_shouldBeTrue() {
        assertTrue(t.containWord("bank"), "The word bank is inside the trie, should return ture");
    }

    @Test
    @DisplayName("Test simple word contain with negative case")
    void testContainWordWithNonExistentWord_shouldBeFalse() {
        assertFalse(t.containWord("foobar"), "The word foobar is not inside the trie, returns false");
    }

    @Test
    @DisplayName("Test wordContain method with words that has space")
    void testContainWordWithSpace_shouldBeTrue() {
        assertTrue(t.containWord("project runway"), "Project runway is inside the trie, returns ture");
    }

    @Test
    @DisplayName("Test wordContain method with words that has special character")
    void testContainWordWithSpecialChar_shouldBeTrue() {
        assertTrue(t.containWord("pg&e"), "The word pg&e is inside the trie, returns ture");
    }

    @Test
    @DisplayName("Test startsWith method with simple prefix positive case")
    void testStartsWithSimplePrefix_shouldBeTrue() {
        assertTrue(t.startsWith("pg"), "There are words starts with pg, returns ture");
    }

    @Test
    @DisplayName("Test startsWith method with non existent prefix")
    void testStartsWithNonExistentPrefix_shouldBeFalse() {
        assertFalse(t.startsWith("bac"), "There are no words starts with pg, returns false");
    }

    @Test
    @DisplayName("Test startsWith method with words that has space")
    void testStartsWithWithSpace_shouldBeTrue() {
        assertTrue(t.startsWith("project"), "There are words starts with project, returns ture");
    }

    @Test
    @DisplayName("Test startsWith method with simple prefix")
    void testStartsWithSpecialCharPrefix_shouldBeTrue() {
        assertTrue(t.startsWith("pg&"), "There are words starts with pg&, returns ture");
    }

    @Test
    @DisplayName("Test getAllWordsStartWith method with prefix=pro")
    void testGetAllWordsStartWith_shouldBeTrue() {
        List<String> first = new ArrayList<>();
        first.add("proactive");
        first.add("progenex");
        first.add("progeria");
        first.add("progesterone");
        first.add("project free tv");
        first.add("project runway");
        String prefix = "pro";
        List<String> second = t.getAllWordsStartWith(prefix);
        assertEquals(first, second);
    }

    @Test
    @DisplayName("Test getNFirstWordsStartWith method with prefix=pro maxNum=4")
    void testGetNFirstWordsStartWith_shouldBeTrue() {
        List<String> first = new ArrayList<>();
        first.add("proactive");
        first.add("progenex");
        first.add("progeria");
        first.add("progesterone");
        String prefix = "pro";
        int maxNum = 4;
        List<String> second = t.getNFirstWordsStartWith(prefix, maxNum);
        assertEquals(first, second);
    }

    @Test
    @DisplayName("Test getNFirstWordsStartWith method with prefix=pro maxNum=4 by ignoring the alphabet order")
    void testGetNFirstWordsStartWithIgnoreOrder_shouldBeTrue() {
        List<String> first = new ArrayList<>();
        first.add("proactive");
        first.add("progenex");
        first.add("progeria");
        first.add("progesterone");
        String prefix = "pro";
        int maxNum = 4;
        List<String> second = t.getNFirstWordsStartWith(prefix, maxNum);
        assertTrue(first.size() == second.size() && first.containsAll(second) && second.containsAll(first));
    }


}
