package org.pengfei.struct;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pengfei.struct.TrieNode;

import static org.junit.jupiter.api.Assertions.*;

public class TrieNodeTest {
    TrieNode node1;
    char init_val='a';
    @BeforeEach
    void setUp() {
       node1 = new TrieNode(init_val);
    }

    @Test
    @DisplayName("Test value getter method")
    void testValueGetter(){
        assertEquals(init_val,node1.getValue(),"The value getter should work");
    }

    @Test
    @DisplayName("Test isWord getter method")
    void testIsWordGetter(){
        assertFalse(node1.isWord(),"By default the value of isWord is false");
    }

    @Test
    @DisplayName("Test isWord setter method")
    void testIsWordSetter(){
        node1.setWord(true);
        assertTrue(node1.isWord(),"After setter to true, the value of isWord is True");
    }
}
