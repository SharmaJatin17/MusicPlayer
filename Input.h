#pragma once
// Small console-input helpers. Everything reads through std::getline on std::cin,
// so mixing "read a number" and "read a line" never leaves stray newlines behind
// and no input is lost between prompts.
#include <iostream>
#include <string>

// Reads one line. Returns false on EOF.
inline bool read_line(std::string &out) {
    return static_cast<bool>(std::getline(std::cin, out));
}

// Reads an integer (one per line). Returns false on EOF or if the line isn't a number.
inline bool read_int(int &out) {
    std::string line;
    if (!read_line(line)) { std::cin.setstate(std::ios::eofbit); return false; }
    try {
        size_t pos = 0;
        out = std::stoi(line, &pos);
        return true;
    } catch (...) {
        return false;
    }
}
