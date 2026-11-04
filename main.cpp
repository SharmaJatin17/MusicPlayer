// Music Player - console music player (BST library + hash-table artists +
// linked-list playlist). Build with:  make   (see README.md)
#include <iostream>
#include <string>
#include "ArtistTable.h"
#include "AudioPlayer.h"
#include "Input.h"
#include "Library.h"
#include "Song.h"
#include "Playlist.h"

int main() {
    Library lib;
    ArtistTable art(4);

    // Single source of truth for the catalogue; each song goes into both structures.
    struct Entry { const char *file, *name, *duration, *artist; };
    const Entry catalogue[] = {
        // Arijit Singh
        {"Aedilhaimushkil.wav", "Ae dil hai mushkil", "4:54", "Arijit Singh"},
        {"Tum hi ho.wav", "Tum hi ho", "4.49", "Arijit Singh"},
        {"Kesariya.wav", "Kesariya", "3:00", "Arijit Singh"},
        {"Humari aduri kahani.wav", "Humari adhuri kahani", "7.19", "Arijit Singh"},
        {"Deva Deva.wav", "Deva Deva", "4.39", "Arijit Singh"},
        // Kishor Kumar
        {"Achha To Hum Chalte Hain - Kishore Kumar, Lata Mangeshkar - Aan Milo Sajna 1970 Songs- Asha Parekh.wav",
         "Accha toh hum chalte hai", "4:56", "Kishor Kumar"},
        {"O Mere Dil Ke Chain (( 4K Video )) - Mere Jeevan Saathi - Rajesh Khanna, Tanuja - Kishore Kumar.wav",
         "O mere dil ke chain", "5:53", "Kishor Kumar"},
        {"Ek Ajnabee Haseena Se - 4K Video - Ajanabee - Rajesh Khanna, Zeenat Aman - Kishore Kumar - 90s Hits.wav",
         "Ek ajnabee haseena se", "4:53", "Kishor Kumar"},
        {"Chookar Mere Mann Ko Kiya Tune Kya Ishara - Kishore Kumar - Yaarana 1981 Songs- Amitabh Bachchan.wav",
         "Chookar mere mann ko", "2:35", "Kishor Kumar"},
        {"Yeh Shaam Mastani 4K - Kishore Kumar - Rajesh Khanna - Kati Patang - Classic Bollywood 4K Video Song.wav",
         "Yeh shaam mastani", "4:04", "Kishor Kumar"},
        {"videoplayback.wav", "Mere samne wali khidki mai", "2:52", "Kishor Kumar"},
        // Camila Cabello
        {"Camila Cabello - Don't Go Yet (Official Video) (1).wav", "Dont go yet", "3:02", "Camila Cabello"},
        {"Camila Cabello - Havana (Audio) ft. Young Thug.wav", "Havana", "3:08", "Camila Cabello"},
        {"Camila Cabello - Liar (Lyrics).wav", "Liar", "3.26", "Camila Cabello"},
        {"Shawn Mendes, Camila Cabello - Se\xC3\xB1orita (Lyrics).wav", "Senorita", "3.11", "Camila Cabello"},
        // One Direction
        {"Steal My Girl - One Direction (Lyrics).wav", "Steal my girl", "3:48", "One Direction"},
        {"One Direction - Night Changes.wav", "Night Changes", "4:00", "One Direction"},
        {"One Direction - What Makes You Beautiful (Official Video).wav", "What makes you beautiful", "3:26", "One Direction"},
        {"One Direction - You  I (Lyrics).wav", "You I", "4:05", "One Direction"},
        {"One Direction - Story of My Life.wav", "Story of my life", "4:07", "One Direction"},
    };
    for (const Entry &e : catalogue) {
        lib.insert(e.file, e.name, e.duration, e.artist);
        art.put(e.artist, Song(e.file, e.name, e.duration, e.artist));
    }

    std::cout << "************************Welcome to the music player**********************\n\n";

    Playlist playlist;
    int choice = -1;
    do {
        std::cout << "------------------------------------------------------------------------\n"
                     "0.Exit.\n1.Display all songs.\n2.Search and play songs.\n3.Add to your playlist.\n"
                     "4.Play your playlist.\n5.Artist's Playlist for playing songs.\n"
                     "-------------------------------------------------------------------------\n"
                     "Enter your choice-\n";
        if (!read_int(choice)) {
            if (std::cin.eof()) break;
            std::cout << "Invalid Choice......\n";
            choice = -1;
            continue;
        }
        switch (choice) {
            case 0:
                std::cout << "Exiting the program....\n";
                break;
            case 1:
                std::cout << "Displaying the list of songs-\n\n";
                lib.print_sorted();
                break;
            case 2: {
                std::string file = lib.search();
                if (!file.empty()) {
                    try {
                        AudioPlayer player(file);
                        player.play_single();
                    } catch (const std::exception &e) {
                        std::cout << "Error with playing sound: " << e.what() << "\n";
                    }
                }
                break;
            }
            case 3: {
                std::cout << "Enter song name you want to add in the playlist : \n";
                std::string song;
                read_line(song);
                if (!lib.search(song).empty()) {
                    playlist.songs.push_back(song);
                    std::cout << "Successfully Added!\n";
                }
                break;
            }
            case 4:
                playlist.play_all(lib);
                break;
            case 5:
                art.artist_menu();
                break;
            default:
                std::cout << "Invalid Choice......\n";
                break;
        }
    } while (choice != 0);
    return 0;
}
