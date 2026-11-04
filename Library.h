#pragma once
// Song library stored as a binary search tree keyed on song name (case-insensitive).
#include <algorithm>
#include <cctype>
#include <iostream>
#include <memory>
#include <string>
#include "Input.h"

class Library {
public:
    struct Node {
        std::string filepath, name, duration, artist;
        std::unique_ptr<Node> left, right;
        Node(std::string f, std::string n, std::string d, std::string a)
            : filepath(std::move(f)), name(std::move(n)),
              duration(std::move(d)), artist(std::move(a)) {}
    };

private:
    std::unique_ptr<Node> root;

    // Case-insensitive comparison: <0, 0 or >0 like strcmp.
    static int compare_ignore_case(const std::string &a, const std::string &b) {
        size_t n = std::min(a.size(), b.size());
        for (size_t i = 0; i < n; ++i) {
            int ca = std::tolower(static_cast<unsigned char>(a[i]));
            int cb = std::tolower(static_cast<unsigned char>(b[i]));
            if (ca != cb) return ca - cb;
        }
        return static_cast<int>(a.size()) - static_cast<int>(b.size());
    }

    static void print_inorder(const Node *n) {
        if (!n) return;
        print_inorder(n->left.get());
        std::cout << "\t" << n->name << "\n";
        print_inorder(n->right.get());
    }

public:
    // Inserts a song; duplicates (same name ignoring case) are ignored.
    void insert(const std::string &filepath, const std::string &name,
                const std::string &duration, const std::string &artist) {
        std::unique_ptr<Node> *slot = &root;
        while (*slot) {
            int c = compare_ignore_case(name, (*slot)->name);
            if (c == 0) return;
            slot = (c < 0) ? &(*slot)->left : &(*slot)->right;
        }
        *slot = std::make_unique<Node>(filepath, name, duration, artist);
    }

    // Prints all song names in alphabetical order.
    void print_sorted() const { print_inorder(root.get()); }

    // Returns the file path, or "" if the song isn't present.
    std::string search(const std::string &key) const {
        const Node *t = root.get();
        while (t) {
            int c = compare_ignore_case(key, t->name);
            if (c == 0) return t->filepath;
            t = (c < 0) ? t->left.get() : t->right.get();
        }
        std::cout << "Song is not present in the music player....\n";
        return "";
    }

    // Prompts the user for a song name, then searches.
    std::string search() const {
        std::cout << "Enter the song to be played : \n";
        std::string key;
        read_line(key);
        return search(key);
    }
};
