# MusicPlayer (C++)

Console music player written in C++17.

| File            | Class         | Data structure                    |
|-----------------|---------------|-----------------------------------|
| Library.h       | Library       | Binary search tree (by song name) |
| ArtistTable.h   | ArtistTable   | Hash table, separate chaining     |
| Playlist.h      | Playlist      | Linked list (`std::list`)         |
| AudioPlayer.h   | AudioPlayer   | play / pause / resume / restart / stop |
| Song.h, Input.h, main.cpp | Song, input helpers, menu |         |

## Build & run
    g++ -std=gnu++17 -O2 main.cpp -o musicplayer      (Windows: -o musicplayer.exe)
    ./musicplayer          # run from the folder that contains the .wav files
