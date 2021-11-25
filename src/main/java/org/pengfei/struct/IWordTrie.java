package org.pengfei.struct;

public interface IWordTrie {

    public void insert(String word);
    public boolean startsWith(String prefix);
    public boolean containWord(String word);

}
