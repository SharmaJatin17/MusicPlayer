#pragma once
// Artist -> songs, stored as a hash table with separate chaining (array of linked lists).
#include <cstdint>
#include <iostream>
#include <list>
#include <string>
#include <vector>
#include "AudioPlayer.h"
#include "Input.h"
#include "Song.h"

class ArtistTable {
    int size;
    std::vector<std::list<Song>> buckets;

    // Polynomial rolling hash (h = 31*h + c) over the key's characters.
    static int32_t string_hash(const std::string &s) {
        uint32_t h = 0;
        for (unsigned char c : s) h = 31u * h + c;
        return static_cast<int32_t>(h);
    }

    int bucket_index(const std::string &key) const {
        int64_t h = string_hash(key);
        if (h < 0) h = -h;               // avoids Math.abs(Integer.MIN_VALUE) overflow
        return static_cast<int>(h % size);
    }

public:
    explicit ArtistTable(int size) : size(size), buckets(size) {}

    void put(const std::string &key, const Song &value) {
        buckets[bucket_index(key)].push_back(value);
    }

    // Plays every song by this artist, one after another, until the user stops.
    void search(const std::string &key) {
        for (const Song &node : buckets[bucket_index(key)]) {
            if (node.artist != key) continue;   // different artist sharing the bucket
            std::cout << "***** Now playing: *****\n"
                      << "Title: " << node.name << "\n"
                      << "Artist: " << node.artist << "\n"
                      << "Duration: " << node.duration << "\n\n";
            try {
                AudioPlayer player(node.filepath);
                if (player.play_with_next() == 0) return;
            } catch (const std::exception &e) {
                std::cout << "Error with playing sound: " << e.what() << "\n";
            }
        }
    }

    void show_songs(const std::string &key) const {
        for (const Song &node : buckets[bucket_index(key)]) {
            if (node.artist == key) {
                std::cout << "Title: " << node.name << "\n"
                          << "Artist: " << node.artist << "\n"
                          << "Duration: " << node.duration << "\n\n";
            }
        }
    }

    void artist_menu() {
        static const char *names[] = {"Arijit Singh", "Kishor Kumar", "Camila Cabello", "One Direction"};
        int choice = 0;
        do {
            std::cout << "**************************************\n"
                         "Choose your artist: \n"
                         "0. Go back to main menu.\n"
                         "1. Arijit Singh\n"
                         "2. Kishor Kumar\n"
                         "3. Camila Cabello\n"
                         "4. One Direction\n"
                         "\n*************************************\n"
                         "\nEnter the number corresponding to the artist: \n";
            if (!read_int(choice)) {
                if (std::cin.eof()) return;
                std::cout << "Enter the correct option: \n";
                choice = -1;
                continue;
            }
            if (choice >= 1 && choice <= 4) {
                show_songs(names[choice - 1]);
                search(names[choice - 1]);
            } else if (choice != 0) {
                std::cout << "Enter the correct option: \n";
            }
        } while (choice != 0);
    }
};
