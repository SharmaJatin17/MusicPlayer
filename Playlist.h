#pragma once
#include <list>
#include <string>
#include "AudioPlayer.h"
#include "Library.h"

class Playlist {
public:
    std::list<std::string> songs;   // song names, in the order added

    // Plays each song in order until the user chooses "stop".
    void play_all(const Library &lib) {
        for (const std::string &song : songs) {
            std::cout << "Now playing: " << song << " \n";
            std::string filepath = lib.search(song);
            if (filepath.empty()) continue;
            try {
                AudioPlayer audio(filepath);
                if (audio.play_with_next() == 0) return;
            } catch (const std::exception &e) {
                std::cout << "Error with playing sound: " << e.what() << "\n";
            }
        }
    }
};
