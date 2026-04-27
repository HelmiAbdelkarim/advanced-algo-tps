package main.java;

public class AutocompleteTrieNode {
    AutocompleteTrieNode[] children = new AutocompleteTrieNode[26];
    boolean isEndOfUsername = false;
    Integer userId = null;
}