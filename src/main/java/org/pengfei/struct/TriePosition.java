package org.pengfei.struct;

import java.util.List;

public interface TriePosition<E> {
    /* Accessor methods */
    public E getElement();
    public List<TriePosition<E>> getChildren();

    /* Update methods */
    public void setElement(E e);
    public void addChild(TriePosition<E> child);
}
