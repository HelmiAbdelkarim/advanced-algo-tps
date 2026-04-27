package main.java;

import java.util.ArrayList;
import java.util.List;
import main.java.AutocompleteTrieNode;

public class AutocompleteTrie {
    private AutocompleteTrieNode root;

    public AutocompleteTrie() {
        this.root = new AutocompleteTrieNode();
    }

    public void insert(String username, int userId) {
        AutocompleteTrieNode curr = root;
        for (char c : username.toCharArray()) {
            int index = c - 'a';
            if (curr.children[index] == null) {
                curr.children[index] = new AutocompleteTrieNode();
            }
            curr = curr.children[index];
        }
        curr.isEndOfUsername = true;
        curr.userId = userId;
    }

    public Integer search(String username) {
        AutocompleteTrieNode curr = root;
        for (char c : username.toCharArray()) {
            int index = c - 'a';
            if (curr.children[index] == null) {
                return null;
            }
            curr = curr.children[index];
        }
        return curr.isEndOfUsername ? curr.userId : null;
    }

    public boolean startsWith(String prefix) {
        AutocompleteTrieNode curr = root;
        for (char c : prefix.toCharArray()) {
            int index = c - 'a';
            if (curr.children[index] == null) {
                return false;
            }
            curr = curr.children[index];
        }
        return true;
    }

    public List<String> autocomplete(String prefix, int maxResults) {
        AutocompleteTrieNode curr = root;
        List<String> res = new ArrayList<>();

        for (char c : prefix.toCharArray()) {
            int index = c - 'a';
            if (curr.children[index] == null) {
                return res;
            }
            curr = curr.children[index];
        }

        dfsCollect(curr, prefix, res, maxResults);
        return res;
    }

    private void dfsCollect(AutocompleteTrieNode node, String currentString, List<String> res, int maxResults) {
        if (res.size() >= maxResults) return;

        if (node.isEndOfUsername) {
            res.add(currentString + " (ID: " + node.userId + ")");
        }

        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                char nextChar = (char) (i + 'a');
                dfsCollect(node.children[i], currentString + nextChar, res, maxResults);
            }
        }
    }

    public int countWords(AutocompleteTrieNode node) {
        if (node == null) return 0;
        int count = node.isEndOfUsername ? 1 : 0;
        for (AutocompleteTrieNode child : node.children) {
            if (child != null) count += countWords(child);
        }
        return count;
    }

    public int getHeight(AutocompleteTrieNode node) {
        if (node == null) return 0;
        int maxDepth = 0;
        for (AutocompleteTrieNode child : node.children) {
            if (child != null) {
                maxDepth = Math.max(maxDepth, getHeight(child));
            }
        }
        return maxDepth + 1;
    }

    public int getTotalNodes(AutocompleteTrieNode node) {
        if (node == null) return 0;
        int total = 1;
        for (AutocompleteTrieNode child : node.children) {
            if (child != null) {
                total += getTotalNodes(child);
            }
        }
        return total;
    }

    private boolean hasNoChildren(AutocompleteTrieNode node) {
        for (AutocompleteTrieNode child : node.children) {
            if (child != null) return false;
        }
        return true;
    }

    public boolean delete(AutocompleteTrieNode node, String username, int depth) {
        if (node == null) return false;

        if (depth == username.length()) {
            if (!node.isEndOfUsername) return false;
            node.isEndOfUsername = false;
            node.userId = null;
            return hasNoChildren(node);
        }

        int charIndex = username.charAt(depth) - 'a';
        AutocompleteTrieNode childNode = node.children[charIndex];

        boolean shouldDeleteChild = delete(childNode, username, depth + 1);

        if (shouldDeleteChild) {
            node.children[charIndex] = null;
            if (!node.isEndOfUsername && hasNoChildren(node)) {
                return true;
            }
        }
        return false;
    }
}