package ll;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//class Artist which implements HashTable
public class Artist extends MusicPlayer{
	int size;				//size of the HashTable
	LinkedList<Song> artists[];				//Array of linked List
	Scanner s = new Scanner(System.in);
	
	@SuppressWarnings("unchecked")
	//parameterized constructor for initializing the variables
	Artist(int size){
		this.size = size;
		artists = new LinkedList[size];
		for(int i=0; i<artists.length; i++) {
			artists[i] = new LinkedList<>();
		}
	}
	
	//Function that returns the hash value
	int hashFunction(String key) {
		int bi = key.hashCode();
		bi = Math.abs(bi) % size;
		return bi;
	}
	
	//Function to put the songs in the HashTable
	void put(String key, Song value) {
		int bi = hashFunction(key);
		artists[bi].add(value);
	}
	
	//Searches for an artist in the HashTable, and plays it playlist
	//key is the artist name
	void search(String key) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		int bi = hashFunction(key);
		if(!artists[bi].isEmpty()) {
			for(int i=0; i<artists[bi].size(); i++) {
			    Song node = artists[bi].get(i);
			   
			    if(node.artist==key)
			    {
			    	System.out.println("***** Now playing: *****");
			    	System.out.println("Title: "+node.name);
			    	System.out.println("Artist: "+node.artist);
			    	System.out.println("Duration: "+node.duration);
			    	//System.out.println("Genre: "+node.genre);
			    	System.out.println();
			       SimpleAudioPlayer s = new SimpleAudioPlayer(node.filepath);
			       int flag = 1;
			       do
			       {
			    	   flag = s.PlaySong();
			    	}
			       while(flag==1);
			       if(flag == 0)
			       {
			    	   return;
			       }
			    	
			    }
			}
		}
	}
	
	//Displays the artist's names, for the user to choose from them 
	void displayChoice() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		int choice = 0;
		Scanner sc = new Scanner(System.in);
		
		do {
			System.out.println("**************************************");
			System.out.println("Choose your artist: ");
			System.out.println("0. Go back to main menu.");
			System.out.println("1. Arijit Singh");
			System.out.println("2. Kishor Kumar");
			System.out.println("3. Camila Cabello");
			System.out.println("4. One Direction");
			System.out.println("\n*************************************");
			System.out.println("\nEnter the number corresponding to the artist: ");
			choice = sc.nextInt();
			
			switch(choice) {
			case 0: 
				break;
			case 1: 
				displayArtists("Arijit Singh");
				search("Arijit Singh");
				break;
			case 2: 
				displayArtists("Kishor Kumar");
				search("Kishor Kumar");
				break;
			case 3:
				displayArtists("Camila Cabello");
				search("Camila Cabello");
				break;
			case 4:
				displayArtists("One Direction");
				search("One Direction");
				break;
				default: 
					System.out.println("Enter the correct option: ");
			}
		}while(choice != 0);
	}
	
	void displayArtists(String key) {
		//Displays all the songs from the artist's playlist
		int bi = hashFunction(key);
		
		for(int i=0; i<artists[bi].size(); i++) {
			Song node = artists[bi].get(i);
			if(node.artist.equals(key)) {
				System.out.println("Title: "+node.name);
		    	System.out.println("Artist: "+node.artist);
		    	System.out.println("Duration: "+node.duration);
		    	System.out.println();
			}
		}
	}
}
