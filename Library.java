package ll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//creating binary search tree class
public class Library {

	Node root;
	//constructor for the class BST
	Library()
	{
		this.root = null;
	}

	//creating a new class Node
	class Node
	{
        String filepath;
        String name;
        String duration;
        String artist ;  
        Node right;
        Node left;

        Node(String filepath, String name, String duration, String artist){
            this.filepath = filepath;
            this.name = name;
            this.duration = duration;
            this.artist = artist ;
        }
        
        Node(){
            right = null;
            left = null;
        }
	}

	//creating object for the scanner class
	BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

	//function which creates the binary search tree
	void create(String filepath, String name, String duration, String artist)
	{
		//taking required variables
		Node temp;
		Node ptr = null;
		temp = root;

		//if root == null then create a new node and make it as root of the tree
		if(root == null)
		{
			Node node = new Node(filepath,name,duration,artist);
			root = node;
			return;
		}

		//traverse the tree till the temp does not become null
		while(temp!=null)
		{
			ptr = temp;  //we are storing the last position of the pointer in order to insert the root at this position

			//if word to be inserted is less than already existing word then move the pointer to the left
			if(name.compareToIgnoreCase(temp.name)<0)
			{
				temp = temp.left;
			}

			//if word to be inserted is greater than already existing word then move the pointer to the left
			else if(name.compareToIgnoreCase(temp.name)>0)
			{
				temp = temp.right;
			}

			else   //when the data is already present in the binary search tree return from the function
			{
				return;
			}
		}

		//create a new node which is to be inserted
		Node node = new Node(filepath,name,duration,artist);

		//comparing again with the word
		if(name.compareToIgnoreCase(ptr.name)<0)
		{
			ptr.left = node;
		}
		else
		{
			ptr.right = node;
		}
	}

	//function to display the tree using inorder non-recursive traversal
	void display(Node temp)
	{
		if(temp!=null)
		{
			display(temp.left);
			System.out.println("\t"+ temp.name);
			display(temp.right);
		}
	}

	//function to search for the word in the binary search tree
	String search() throws IOException
	{
	
		System.out.println("Enter the song to be played : ");
		String key=br.readLine();
		Node temp = root;
		while(temp!=null)
		{
			if(key.equalsIgnoreCase(temp.name)) {
				return temp.filepath;
			}
			//if word to be inserted is less than already existing word then move the pointer to the left
			else if(key.compareToIgnoreCase(temp.name)<0)
			{
				temp = temp.left;
			}

			//if word to be inserted is greater than already existing word then move the pointer to the left
			else
			{
				temp = temp.right;
			}
		}

		System.out.println("Song is not present in the music player....");
		return null;
	}
	
	
	String search(String key) throws IOException
	{
		Node temp = root;
		while(temp!=null)
		{
			if(key.equalsIgnoreCase(temp.name)) {
				return temp.filepath;
			}
			//if word to be inserted is less than already existing word then move the pointer to the left
			else if(key.compareToIgnoreCase(temp.name)<0)
			{
				temp = temp.left;
			}

			//if word to be inserted is greater than already existing word then move the pointer to the left
			else
			{
				temp = temp.right;
			}
		}

		System.out.println("Song is not present in the music player....");
		return null;
	}
}
