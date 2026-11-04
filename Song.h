#pragma once
#include <string>

// Plain data holder for one song.
struct Song {
    std::string filepath;
    std::string name;
    std::string duration;
    std::string artist;

    Song(std::string filepath, std::string name, std::string duration, std::string artist)
        : filepath(std::move(filepath)), name(std::move(name)),
          duration(std::move(duration)), artist(std::move(artist)) {}
};
