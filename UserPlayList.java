package ll;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class UserPlayList {
	//Declaring a inbuilt LinkedList, which is used to create UserPlayList
	LinkedList<String> list;
	
	//Constructor that creates a new LinkedList, when the object is created
	UserPlayList(){
		list = new LinkedList<>();
	}
	
	//Method to play the songs in the playlist
	void PlayMyPlaylist(Library b) throws IOException, UnsupportedAudioFileException, LineUnavailableException
	{
		for(int i =0; i<list.size(); i++) {
			String song = list.get(i);
			System.out.println("Now playing: " + song + " ");
			//Method that returns the filepath of the song in the playlist
			String filepath = b.search(song);
			SimpleAudioPlayer audio = new SimpleAudioPlayer(filepath);
			int flag = 1;
		       do
		       {
		    	   flag = audio.PlaySong();
		    	}
		       while(flag==1);
		       if(flag == 0)
		       {
		    	   return;
		       }
		}
	}
}
