package org.pengfei.completions;

import org.pengfei.struct.Trie;

import java.util.List;

public class main {

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

        List<String> autoCompleteFull = t.getAllWordsStartWith("pro");
        System.out.println("Complete list of the auto completion for pr: "+autoCompleteFull);

        List<String> autoComplete1 = t.getNFirstWordsStartWith("p", 4);
        System.out.println("Auto completion for p: "+autoComplete1);

        List<String> autoComplete2 = t.getNFirstWordsStartWith("pr", 4);
        System.out.println("Auto completion for pr: "+autoComplete2);

        List<String> autoComplete3 = t.getNFirstWordsStartWith("pro", 4);
        System.out.println("Auto completion for pro: "+autoComplete3);

        List<String> autoComplete4 = t.getNFirstWordsStartWith("prog", 4);
        System.out.println("Auto completion for prog: "+autoComplete4);


    }
}
