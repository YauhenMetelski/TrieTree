package com.metelski.prefixtree.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * TrieTree structure for storing data
 *
 * @param <V>
 */
public final class Trie<V> {
    private final Node<V> root;

    /**
     * Class constructor
     */
    public Trie() {
        this.root = new TrieNode<>();
    }

    /**
     * Adds word and value to the storage.
     *
     * @param keyWord word for adding
     * @param value   value for adding
     */
    public void insert(final String keyWord, final V value) {
        Node<V> current = root;

        for (char letter : keyWord.toCharArray()) {
            current = current.getChildren().computeIfAbsent(letter, character -> new TrieNode<V>());
        }
        current.setValue(value);
    }

    /**
     * Returns value or empty Optional.
     *
     * @param keyWord word for searching
     * @return {@link Optional}
     */
    public V find(final String keyWord) {
        Node<V> current = root;
        for (int i = 0; i < keyWord.length(); i++) {
            char ch = keyWord.charAt(i);
            Node<V> node = current.getChildren().get(ch);
            if (node == null) {
                return null;
            }
            current = node;
        }
        return current.getValue();
    }

    /**
     * Deletes word from the storage.
     *
     * @param word for deleting
     */

    public void delete(final String word) {
        delete(root, word, 0);
    }

    /**
     * Inserts keyWord in trieTree or if such value present applies function.
     *
     * @param keyWord           word for adding.
     * @param value             initial value for keyWord.
     * @param remappingFunction function for applying.
     */
    public void computeOrInsert(final String keyWord, final V value, final UnaryOperator<V> remappingFunction) {
        Node<V> node = findNode(keyWord);
        if (node != null && !node.isEmpty()) {
            node.setValue(remappingFunction.apply(node.getValue()));
        } else {
            insert(keyWord, value);
        }
    }

    /**
     * Returns true if this tree contains value for the keyWord
     *
     * @param keyWord word for searching.
     * @return true if this tree contains value for the keyWord
     */
    public boolean containsValue(final String keyWord) {
        Node<V> current = root;

        for (int i = 0; i < keyWord.length(); i++) {
            char ch = keyWord.charAt(i);
            Node<V> node = current.getChildren().get(ch);
            if (node == null) {
                return false;
            }
            current = node;
        }
        return !current.isEmpty();
    }

    private Node<V> findNode(final String keyWord) {
        Node<V> current = root;
        for (int i = 0; i < keyWord.length(); i++) {
            char ch = keyWord.charAt(i);
            Node<V> node = current.getChildren().get(ch);
            if (node == null) {
                return null;
            }
            current = node;
        }
        return current;
    }

    private boolean delete(final Node<V> current, final String keyWord, final int index) {
        if (index == keyWord.length()) {
            if (current.isEmpty()) {
                return false;
            }
            current.setValue(null);
            return current.getChildren().isEmpty();
        }
        char ch = keyWord.charAt(index);
        Node<V> node = current.getChildren().get(ch);
        if (node == null) {
            return false;
        }
        boolean shouldDeleteCurrentNode = delete(node, keyWord, index + 1) && node.isEmpty();

        if (shouldDeleteCurrentNode) {
            current.getChildren().remove(ch);
            return current.getChildren().isEmpty();
        }
        return false;
    }

    /**
     * Implementation of the Node interface.
     */
    private static class TrieNode<V> implements Node<V> {
        private final HashMap<Character, Node<V>> children;
        private V value;

        /**
         * Class constructor.
         */
        public TrieNode() {
            this.children = new HashMap<>();
        }

        /**
         * Class constructor.
         *
         * @param value
         */
        public TrieNode(V value) {
            this();
            this.value = value;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public Map<Character, Node<V>> getChildren() {
            return this.children;
        }

        @Override
        public boolean isEmpty() {
            return value == null;
        }
    }

    /**
     * Base part of the trieTree.
     *
     * @param <V> generic type.
     */
    interface Node<V> {
        /**
         * Returns value stored in node.
         */
        V getValue();

        /**
         * Sets value.
         */
        void setValue(V value);

        /**
         * Returns next node.
         */
        Map<Character, Node<V>> getChildren();

        /**
         * Returns true if node contains value=null.
         */
        boolean isEmpty();
    }
}
