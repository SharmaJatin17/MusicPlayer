package ll;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


class Song{
	String filepath;
	String name;
	String artist;
	String duration;
	Song(String filepath, String name, String duration, String artist){
		this.filepath = filepath;
		this.name = name;
		this.duration = duration;
		this.artist = artist;
	}
}

class SimpleAudioPlayer {

	// to store current position
	Long currentFrame;
	Clip clip;

	// current status of clip
	String status;

	AudioInputStream audioInputStream;
	static String filePath;

	// constructor to initialize streams and clip
	public SimpleAudioPlayer(String fp) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		filePath = fp;
		// create AudioInputStream object
		audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

		// create clip reference
		clip = AudioSystem.getClip();

		// open audioInputStream to the clip
		clip.open(audioInputStream);

		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	int PlaySong() {
		try {
			play();
			Scanner sc = new Scanner(System.in);

			while (true) {
				System.out.println("0. stop");
				System.out.println("1. pause");
				System.out.println("2. resume");
				System.out.println("3. restart");
				System.out.println("4. next");
				int c = sc.nextInt();
				gotoChoice(c);
				if (c == 4)
					break;
				if (c==0) return 0;
			}
			// sc.close();
		}

		catch (Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();

		}
		return -1;
	}
	
	void PlaybstSong() {
		try {

			// fp = "Your path for the file";
			// SimpleAudioPlayer audioPlayer =
			// new SimpleAudioPlayer(fp);

			play();
			Scanner sc = new Scanner(System.in);

			while (true) {
				System.out.println("\t\t0. stop");
				System.out.println("\t\t1. pause");
				System.out.println("\t\t2. resume");
				System.out.println("\t\t3. restart");
				int c = sc.nextInt();
				gotoChoicebst(c);
				if (c == 0)
					break;
			}
		}

		catch (Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();

		}
	}
	void gotoChoicebst(int c) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		switch (c) {
		case 0:
			clip.stop();
			break;
		case 1:
			pause();
			break;
		case 2:
			resumeAudio();
			break;
		case 3:
			restart();
			break;
		}

    }

	


	// Work as the user enters his choice

	private void gotoChoice(int c) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		switch (c) {
		case 0:
			clip.stop();
			break;
		case 1:
			pause();
			break;
		case 2:
			resumeAudio();
			break;
		case 3:
			restart();
			break;
		case 4:
			stop();
			break;
		}

	}

	// Method to play the audio
	public void play() {
		// start the clip
		clip.start();

		status = "play";
	}

	// Method to pause the audio
	public void pause() {
		if (status.equals("paused")) {
			System.out.println("audio is already paused");
			return;
		}
		this.currentFrame = this.clip.getMicrosecondPosition();
		clip.stop();
		status = "paused";
	}

	// Method to resume the audio
	public void resumeAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (status.equals("play")) {
			System.out.println("Audio is already " + "being played");
			return;
		}
		clip.close();
		resetAudioStream();
		clip.setMicrosecondPosition(currentFrame);
		this.play();
	}

	// Method to restart the audio
	public void restart() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		clip.stop();
		clip.close();
		resetAudioStream();
		currentFrame = 0L;
		clip.setMicrosecondPosition(0);
		this.play();
	}

	// Method to stop the audio
	public void stop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		currentFrame = 0L;
		clip.stop();
		clip.close();
	}


	// Method to reset audio stream
	public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
		clip.open(audioInputStream);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

}

public class MusicPlayer {
	
	public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		
		Scanner sc = new Scanner(System.in);
		SimpleAudioPlayer a;
		Library b = new Library();			// creating the object on bst class
		// filepath,name,duration,artist,count

		// Arijit Singh songs
		b.create("Aedilhaimushkil.wav", "Ae dil hai mushkil", "4:54", "Arijit Singh");
		b.create("Tum hi ho.wav", "Tum hi ho", "4.49", "Arijit Singh");
		b.create("Kesariya.wav", "Kesariya", "3:00", "Arijit Singh");
		b.create("Humari aduri kahani.wav", "Humari adhuri kahani", "7.19", "Arijit Singh");
		b.create("Deva Deva.wav", "Deva Deva", "4.39", "Arijit Singh");

		// Camila Cabello
		b.create("Camila Cabello - Don't Go Yet (Official Video) (1).wav", "Dont go yet", "3:02", "Camila Cabello");
		b.create("Camila Cabello - Havana (Audio) ft. Young Thug.wav", "Havana", "3:08", "Camila Cabello");
		b.create("Camila Cabello - Liar (Lyrics).wav", "Liar", "3.26", "Camila Cabello");
		b.create("Shawn Mendes, Camila Cabello - Señorita (Lyrics).wav", "Senorita", "3.11", "Camila Cabello");

		// one direction
		b.create("One Direction - Night Changes.wav", "Night Changes", "4:00", "One Direction");
		b.create("One Direction - Story of My Life.wav", "Story of my life", "4:07", "One Direction");
		b.create("One Direction - What Makes You Beautiful (Official Video).wav", "What makes you beautiful", "3:26",
				"One Direction");
		b.create("One Direction - You  I (Lyrics).wav", "You I", "4:05", "One Direction");
		b.create("Steal My Girl - One Direction (Lyrics).wav", "Steal my girl", "3:48", "One Direction");

		// Kishor Kumar
		b.create(
				"Achha To Hum Chalte Hain - Kishore Kumar, Lata Mangeshkar - Aan Milo Sajna 1970 Songs- Asha Parekh.wav",
				"Accha toh hum chalte hai", "4:56", "Kishor Kumar");
		b.create("Chookar Mere Mann Ko Kiya Tune Kya Ishara - Kishore Kumar - Yaarana 1981 Songs- Amitabh Bachchan.wav",
				"Chookar mere mann ko", "2:35", "Kishor Kumar");
		b.create(
				"Ek Ajnabee Haseena Se - 4K Video - Ajanabee - Rajesh Khanna, Zeenat Aman - Kishore Kumar - 90s Hits.wav",
				"Ek ajnabee haseena se", "4:53", "Kishor Kumar");
		b.create("O Mere Dil Ke Chain (( 4K Video )) - Mere Jeevan Saathi - Rajesh Khanna, Tanuja - Kishore Kumar.wav",
				"O mere dil ke chain", "5:53", "Kishor Kumar");
		b.create("videoplayback.wav", "Mere samne wali khidki mai", "2:52", "Kishor Kumar");
		b.create(
				"Yeh Shaam Mastani 4K - Kishore Kumar - Rajesh Khanna - Kati Patang - Classic Bollywood 4K Video Song.wav",
				"Yeh shaam mastani", "4:04", "Kishor Kumar");

		Artist art = new Artist(4);
		//Arijit Singh songs: 
		art.put("Arijit Singh", new Song("Aedilhaimushkil.wav", "Ae dil hai mushkil", "4:54", "Arijit Singh"));
		art.put("Arijit Singh", new Song("Tum hi ho.wav", "Tum hi ho", "4.49", "Arijit Singh"));
		art.put("Arijit Singh", new Song("Kesariya.wav", "Kesariya", "3:00", "Arijit Singh"));
		art.put("Arijit Singh", new Song("Humari aduri kahani.wav", "Humari adhuri kahani", "7.19", "Arijit Singh"));
		art.put("Arijit Singh", new Song("Deva Deva.wav", "Deva Deva","4.39" , "Arijit Singh"));
		
		//Kishor Kumar Songs: 
		art.put("Kishor Kumar", new Song("Achha To Hum Chalte Hain - Kishore Kumar, Lata Mangeshkar - Aan Milo Sajna 1970 Songs- Asha Parekh.wav", "Accha toh hum chalte hai", "4:56", "Kishor Kumar"));
		art.put("Kishor Kumar", new Song("O Mere Dil Ke Chain (( 4K Video )) - Mere Jeevan Saathi - Rajesh Khanna, Tanuja - Kishore Kumar.wav", "O mere dil ke chain", "5:53", "Kishor Kumar"));
		art.put("Kishor Kumar", new Song("Ek Ajnabee Haseena Se - 4K Video - Ajanabee - Rajesh Khanna, Zeenat Aman - Kishore Kumar - 90s Hits.wav", "Ek ajnabee haseena se", "4:53", "Kishor Kumar"));
		art.put("Kishor Kumar", new Song("Chookar Mere Mann Ko Kiya Tune Kya Ishara - Kishore Kumar - Yaarana 1981 Songs- Amitabh Bachchan.wav", "Chookar mere mann ko", "2:35", "Kishor Kumar"));
		art.put("Kishor Kumar", new Song("Yeh Shaam Mastani 4K - Kishore Kumar - Rajesh Khanna - Kati Patang - Classic Bollywood 4K Video Song.wav", "Yeh shaam mastani", "4:04", "Kishor Kumar"));
		art.put("Kishor Kumar", new Song("videoplayback.wav", "Mere samne wali khidki mai", "2:52", "Kishor Kumar"));
		
		//Camila Cabello Songs: 
		art.put("Camila Cabello", new Song("Camila Cabello - Don't Go Yet (Official Video) (1).wav", "Dont go yet", "3:02", "Camila Cabello"));
		art.put("Camila Cabello", new Song("Camila Cabello - Havana (Audio) ft. Young Thug.wav", "Havana", "3:08", "Camila Cabello"));
		art.put("Camila Cabello", new Song("Camila Cabello - Liar (Lyrics).wav", "Liar", "3.26", "Camila Cabello"));
		art.put("Camila Cabello", new Song("Shawn Mendes, Camila Cabello - Señorita (Lyrics).wav", "Senorita", "3.11", "Camila Cabello"));
		
		//One Direction Songs:
		art.put("One Direction", new Song("Steal My Girl - One Direction (Lyrics).wav", "Steal my girl", "3:48", "One Direction"));
		art.put("One Direction", new Song("One Direction - Night Changes.wav", "Night Changes", "4:00", "One Direction"));
		art.put("One Direction", new Song("One Direction - What Makes You Beautiful (Official Video).wav", "What makes you beautiful", "3:26", "One Direction"));
		art.put("One Direction", new Song("One Direction - You  I (Lyrics).wav", "You I", "4:05", "One Direction"));
		art.put("One Direction", new Song("One Direction - Story of My Life.wav", "Story of my life", "4:07", "One Direction"));
		
		System.out.println("************************Welcome to the music player**********************\n");

		int choice;
		UserPlayList play = new UserPlayList();
		do {
			System.out.println("------------------------------------------------------------------------");
			System.out.println(
					"0.Exit.\n1.Display all songs.\n2.Search and play songs.\n3.Add to your playlist.\n4.Play your playlist.\n5.Artist's Playlist for playing songs.");
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("Enter your choice-");
			choice = sc.nextInt();
			switch (choice) {
			case 0:
				System.out.println("Exiting the program....");
				break;
			case 1:
				// Displaying all the songs:
				System.out.println("Displaying the list of songs-\n");
				b.display(b.root);
				break;
			case 2:// search and play the songs
				String file = b.search();
				if(file != null) {
					a = new SimpleAudioPlayer(file);
					a.PlaybstSong();
				}
				break;
			case 3:// Add to your playlist
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Enter song name you want to add in the playlist : ");
				String song = br.readLine();
				//If song is present in the library, adding it in the list
				if(b.search(song) != null) {
					play.list.add(song);
					System.out.println("Successfully Added!");
				}
				break;
			case 4:
				//Playing the songs from the user playlist
				play.PlayMyPlaylist(b);
				break;
			case 5:
				//Displays all the artists & plays their PlayList
				art.displayChoice();
				break;
			default:
				//If user inputs the wrong option
				System.out.println("Invalid Choice......");
				break;
			}
		} while (choice != 0);			//Condition for the do-while loop
	}
}
