#pragma once
// AudioPlayer: loads one audio file and lets the user play / pause / resume / restart / stop it.
//
// Audio backend:
//   * If "miniaudio.h" (https://github.com/mackron/miniaudio, public domain, single header)
//     is next to these sources, it is used automatically: real playback on Windows/macOS/Linux.
//   * Otherwise a console stub is used (prints what it *would* do) so the program still
//     builds and the menus can be exercised.
#include <fstream>
#include <memory>
#include <stdexcept>
#include <string>
#include "Input.h"

#if __has_include("miniaudio.h")
  #define MP_USE_MINIAUDIO 1
  #define MINIAUDIO_IMPLEMENTATION
  #include "miniaudio.h"
#else
  #define MP_USE_MINIAUDIO 0
#endif

class AudioPlayer {
    enum class Status { Stopped, Playing, Paused };
    Status status = Status::Stopped;
    std::string file_path;

#if MP_USE_MINIAUDIO
    static ma_engine &engine() {            // one shared engine for all players
        static ma_engine e;
        static bool ok = (ma_engine_init(nullptr, &e) == MA_SUCCESS);
        if (!ok) throw std::runtime_error("Could not initialise audio device");
        return e;
    }
    ma_sound sound{};
    bool sound_loaded = false;
#endif

public:
    // Loads the file and prepares looping playback (constructor).
    // Throws std::runtime_error if the file can't be opened/decoded.
    explicit AudioPlayer(const std::string &path) : file_path(path) {
#if MP_USE_MINIAUDIO
        if (ma_sound_init_from_file(&engine(), file_path.c_str(), 0, nullptr, nullptr, &sound) != MA_SUCCESS)
            throw std::runtime_error("Cannot open audio file: " + file_path);
        sound_loaded = true;
        ma_sound_set_looping(&sound, MA_TRUE);   // loop until stopped
#else
        if (!std::ifstream(file_path, std::ios::binary))
            throw std::runtime_error("Cannot open audio file: " + file_path);
#endif
    }

    AudioPlayer(const AudioPlayer &) = delete;
    AudioPlayer &operator=(const AudioPlayer &) = delete;

    ~AudioPlayer() {
#if MP_USE_MINIAUDIO
        if (sound_loaded) ma_sound_uninit(&sound);
#endif
    }

    void play() {
#if MP_USE_MINIAUDIO
        ma_sound_start(&sound);
#else
        std::cout << "[audio stub] playing " << file_path << "\n";
#endif
        status = Status::Playing;
    }

    void pause() {
        if (status == Status::Paused) { std::cout << "audio is already paused\n"; return; }
#if MP_USE_MINIAUDIO
        ma_sound_stop(&sound);                   // keeps the playback position
#else
        std::cout << "[audio stub] paused\n";
#endif
        status = Status::Paused;
    }

    void resume() {
        if (status == Status::Playing) { std::cout << "Audio is already being played\n"; return; }
        play();
    }

    void restart() {
#if MP_USE_MINIAUDIO
        ma_sound_stop(&sound);
        ma_sound_seek_to_pcm_frame(&sound, 0);
#else
        std::cout << "[audio stub] restarted\n";
#endif
        play();
    }

    void stop() {
#if MP_USE_MINIAUDIO
        ma_sound_stop(&sound);
        ma_sound_seek_to_pcm_frame(&sound, 0);
#else
        std::cout << "[audio stub] stopped\n";
#endif
        status = Status::Stopped;
    }

    // Controls used while playing from playlist / artist (menu has "next").
    // Returns 0 = user chose stop (leave playback entirely), -1 = go to next song.
    int play_with_next() {
        play();
        while (true) {
            std::cout << "0. stop\n1. pause\n2. resume\n3. restart\n4. next\n";
            int c;
            if (!read_int(c)) {
                if (std::cin.eof()) { stop(); return 0; }
                std::cout << "Error with playing sound.\n";
                stop();
                return -1;
            }
            switch (c) {
                case 0: stop(); return 0;
                case 1: pause(); break;
                case 2: resume(); break;
                case 3: restart(); break;
                case 4: stop(); return -1;
                default: break;
            }
        }
    }

    // Controls used by "Search and play" (no "next" option).
    void play_single() {
        play();
        while (true) {
            std::cout << "\t\t0. stop\n\t\t1. pause\n\t\t2. resume\n\t\t3. restart\n";
            int c;
            if (!read_int(c)) {
                if (std::cin.eof()) { stop(); return; }
                std::cout << "Error with playing sound.\n";
                continue;
            }
            switch (c) {
                case 0: stop(); return;
                case 1: pause(); break;
                case 2: resume(); break;
                case 3: restart(); break;
                default: break;
            }
        }
    }
};
