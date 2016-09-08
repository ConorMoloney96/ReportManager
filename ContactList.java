import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;


public class ContactList
    
{
  //All the test Patterns
  public static String NamesTest="[A-Za-z]+";				
  public static String LandlinesTest1="0[1-2]{1}-\\d{5,7}||02[1-9]{1}-\\d{5,7}||040[2,4]{1}-\\d{5,7}||04\\d[^0^8]{1}-\\d{5,7}";
  public static String LandlinesTest2="050[4,5]{1}-\\d{5,7}||05\\d[^0^4^5]{1}-\\d{5,7}||06[1-9]{1}-\\d{5,7}||07[1,4]{1}-\\d{5,7}||09\\d[^2]{1}-\\d{5,7}";
  public static String MobileTest="083-\\d{7}||085-\\d{7}||086-\\d{7}||087-\\d{7}||089-\\d{7}";
  public static String EmailTest="^(.+)@(.+)$";
  public static String AddressTest="[A-Za-z0-9\\S]+";
  //Array Lists (Multi-Dimensional)
  public static ArrayList<ArrayList<String>>  mainList; //main contact list
  public static boolean newFile;
  
  public static String inputArray [] = new String [6];  //global input array
  
  
 public static void main(String [] args) throws IOException
 {
    int validationInt = validateArgs(args);               // validates the command-line arguments
 	boolean ifFileExists = fileExists(args);              //Check if the file exists and returns a boolean value
	Scanner userIn = new Scanner(System.in);
    if(validationInt == 1)             //Files Exist 
	{
	  if(ifFileExists)   
	  {
	    runProgram();  //runs program
		newFile = false;   //boolean to check if its a new file
        printInfo(args);		
	  }
	  else                            //Files does not exist
	  {
	   System.out.println("File does not exist, creating an empty file");      
	   createFile(args);   //creates file passes through filename
	   createLists();     //creates lists
	   newFile = true;   //boolean to check if its a new file
	   runProgram();   //runs program
	   printInfo(args); //prints to file
	   
	  }
	  
	}
	else if(validationInt == 2) System.out.println("Your entry did not match required file");
	else if(validationInt == 3) System.out.println("Too many / Too little arguments provided");
	
  
  
 }
 
 public static int validateArgs(String [] args)  throws IOException              // Validating the command-line arguments
 {
    if(args.length == 1)
	{
	 if(args[0].matches("[0-9a-zA-Z_]+.txt"))
	  {
	    return 1;                                            //1 = Exists 2 = didn't match the required file  3 = Too many arguments  
	  }
	 else
	  {
	    return 2;
	  }
    }
	else
	{
	 return 3;
	}
 }
 
 public static boolean fileExists(String [] args) throws IOException    //checks if the file exists already 
 {
    String filename = args[0];                      //filename
	File contacts = new File (filename);            //new file object named after the file the user entered
	
	if(!contacts.exists())       //if statement allows us to check if the file exists and returns a boolean value to main
	   return false;
	else 
	{
	 System.out.print("File Exists");
	 createLists();								//create the multidimensional array list
	 readFilesIntoArrayLists(filename);			//load the opening file into the array list
	 return true;
	}
	
 }
 
 public static void createFile(String [] args) throws IOException  //creates a new file
 {
	String filename = args[0];     //filename 
	File filename1 = new File(filename);   //Creates a file object 
	filename1.createNewFile();             //Creates an empty .txt document
	FileOutputStream oFile = new FileOutputStream(filename, false);   //creates a file output stream object and sets the filename and the false indicates it doesnt exist
	oFile.close();             
	
	
 }
 
 
  public static void createLists() throws IOException    //creates array lists
 {
	mainList = new ArrayList<ArrayList<String>>(); //creates an array list and a for loop is set up to create more array  lists to add to mainLists
	int numOfSubLists=6;
	for(int i=0; i<numOfSubLists; i++)
		mainList.add(i, new ArrayList<String>());
 }
 
 public static void readFilesIntoArrayLists(String filename) throws IOException    //reads files into the array lists
 {
	Scanner in;   //create a scanner object
	in = new Scanner(new FileInputStream(filename), "UTF-8"); //uses the file as an input stream.checks if the file is in the right format
	String [] fileElements;       //Array to drop current words being passed through from the file
	while(in.hasNext())           //while loop to read files form .txt document
	{
	  fileElements = (in.nextLine().split(","));      //splits current line of words into the array and writes them to each array list
	  mainList.get(0).add(fileElements[0]);           //Surnames
	  mainList.get(1).add(fileElements[1]);           //forenames
	  mainList.get(2).add(fileElements[2]);           //landline
	  mainList.get(3).add(fileElements[3]);           //mobile
	  mainList.get(4).add(fileElements[4]);           //email
	  mainList.get(5).add(fileElements[5]);           //Address

	}
	in.close();                         //closes scanner object
	System.out.print("Files loaded.");
	}
 
 public static void displayMenu()   //displays menu
 {
    System.out.println("\n\t\t:Menu:");
	System.out.println("i(nsert)\tr(emove)\te(dit)");
	System.out.println("d(isplay)\ts(earch)\tq(uit)\n");
	System.out.println("Enter the first letter of a command to continue");
 }
 
 public static void runProgram() throws IOException    //runs menu/program(user input/methods for instructions)
 {
    Scanner in = new Scanner(System.in);         //creates a scanner object for the user to enter data
	String instruction = "x";                    //set instruction to a default letter allowing the while loop to start
	while(!(instruction.equalsIgnoreCase("q")))  //if the input is q, the program ends
	{
	 displayMenu();                              //displays menu
	 instruction = in.nextLine();                //gets instruction for user
	 if(instruction.isEmpty())                                  //if empty asks user to attempt again
		 System.out.print("No input given. Try again\n");
	 else
	 {
	   String startOfInst=instruction.substring(0,1);         //gets the first letter of the instruction, such as e, q, 
	   String userInput;                                      //String to pass to methods
	   if(startOfInst.equalsIgnoreCase("i"))
	   {
	     userInput = instruction.substring(1+1);           //insert method
	     validateInput(userInput);
	   }
	   else if(startOfInst.equalsIgnoreCase("r"))
	   { 
         userInput = instruction.substring(1+1);
		 removeContacts(userInput);
	   }
	   else if(startOfInst.equalsIgnoreCase("e"))
	   {
	     userInput = instruction.substring(1+1);
         editContacts(userInput);                          //edit method
	   }
	   else if(startOfInst.equalsIgnoreCase("d"))
	   {
	     displayContacts();                                //display methods
	   }
	   else if(startOfInst.equalsIgnoreCase("s"))
	   {
         searchContacts(instruction);                     //search method
	   }
	 }
	 
	}
	
 
 }
 
 public static void validateInput(String input)  //insert method
	{
	  String[]temp=new String[6];							//create a string for the maximum amount of data in the line
	  ArrayList<String> info = new ArrayList<String>();
	  boolean Surname=false;								//booleans return whether or not there is 
	  boolean Forename=false;								//valid data for that entry
	  boolean Landline=false;
	  boolean Mobile=false;
	  boolean Email=false;
	  boolean Address=false;
	  boolean cont=true;
	  boolean spotfound=false;							    //boolean for whether or not the alpabetic spot is found
	  input=input.replaceAll(" ","");
	  temp=input.split(",");
	  for(int i=0; i<temp.length && temp[i]!=null; i++)
		info.add(i, temp[i]);								//load data from "temp" array into an arraylist
	  if(info.size()<3)										//check if the user entered enough data
	  {
		System.out.print("Invalid input. Not enough information given.\n");
		cont=false;
	  }
	   if(info.size()>3)										//check if the user entered enough data
	  {
		System.out.print("Invalid input. Too much information given.\n");
		cont=false;
	  }
	  int correctCounter=0;
	  if(info.get(0).matches(NamesTest))						//test each entry seeing if they match valid patterns
	  {
		  Surname=true;
		  correctCounter++;									//correctCounter counts that there is enough valid data
	  }
	  else
	  {
		  cont=false;										//if the surname or forename isnt correct it does not continue
		  System.out.print("Surname not in valid format\n");
	  }
	  if(info.get(1).matches(NamesTest))
	  {
		  Forename=true;
		  correctCounter++;
	  }
	  else
	  {
		  cont=false;
		  System.out.print("Forename not in valid format\n");
	  }
	  if(info.get(2).matches(LandlinesTest1)||info.get(2).matches(LandlinesTest2) && cont)
	  {
		  Landline=true;
		  correctCounter++;
		  cont=false;									//once the third value type is dicovered it does not keep searching
	  }

	  if(info.get(2).matches(MobileTest) && cont)
	  {
		  Mobile=true;
		  correctCounter++;
		  cont=false;
	  }
	  
	  if(info.get(2).matches(EmailTest) && cont)
	  {
		  Email=true;
		  correctCounter++;
		  cont=false;
	  }
	  
	  if(info.get(2).matches(AddressTest) && cont)
	  {
		  Address=true;
		  correctCounter++;
	  }
	  if(correctCounter!=3)							//Checks there is enough valid data 
	  {
		System.out.print("Invalid input. Not enough information given or not enough information in valid formats\n");
		cont=false;
	  }
	  int num=0;
	  if (!newFile)
    {
	  cont=true;											//finds where the alphabetic location for the input is
	  int iLength=(info.size()-1);
	  
	  
	  if(mainList.get(0).size()== 0)
	  {
		num=0;
   	  }
	  if(temp[0].compareToIgnoreCase(mainList.get(0).get(0))<0)
	  {
		num=0;
	  }
	  else if(temp[0].compareToIgnoreCase(mainList.get(0).get(mainList.get(0).size()-1))>0)
	  {
		num=mainList.get(0).size();
	  }
	  else
	  {
		boolean locationFound=false;
		for(int k=0; k<(mainList.get(0).size()-1) && !locationFound; k++)
		{
		  String current=mainList.get(0).get(k);
		  String next=mainList.get(0).get(k+1);
		  if(temp[0].compareToIgnoreCase(current)>0 && temp[0].compareToIgnoreCase(next)<0)
		  {
		     num=k+1;
			 locationFound=true;			
		  }
		}
	  }
	 }
	 
	  
	  if(info.get(0).matches(NamesTest) && cont)			//Enters the valid data or " " in the correct spot
	  mainList.get(0).add(num, info.get(0));
	  else
	  {
		System.out.print("Invalid input, first name not valid\n");
		cont=false;
	  }
	  if(info.get(1).matches(NamesTest) && cont)			//Enters the valid data or " " in the correct spot
		mainList.get(1).add(num, info.get(1));
	  else
	  {
		System.out.print("Invalid input, first name not valid\n");
		cont=false;
	  }
	  
	  int tracker=2;
	  
	  if(Landline)
	  {
		  mainList.get(2).add(num, info.get(tracker));
		  tracker++;
	  }
	  else
		  mainList.get(2).add(num, "\t");
	  
	  if(Mobile)
	  {
		  mainList.get(3).add(num, info.get(tracker));
		  tracker++;
	  }
	  else
		  mainList.get(3).add(num, "\t");
	  
	  if(Email)
	  {
		  mainList.get(4).add(num, info.get(tracker));
		  tracker++;
	  }
	  else
		  mainList.get(4).add(num, "\t");
	  
	  if(Address)
	  {
		  mainList.get(5).add(num, info.get(tracker));
	  }
	  else
		  mainList.get(5).add(num, "\t");
	}
  
	
	
	public static void displayContacts() throws IOException //method to display information
    { 
      String anyKey = "";
	  String [] contactHeadings = {"Surname: ", "Forename: ", "Landline number: ", 
			"Mobile number: ", "Email address: ", "Postal address: "}; 
	   //stores the headings to be printed out in the display
	   String results = ""; 
	   Scanner keyboard = new Scanner(System.in); 
	   for(int row=1;row<=mainList.get(0).size();row++) 
	   //counter keeping track of which row in the arrayList (each row contains the information
	   // of one contact) is being used 
	   {  
		 for(int column=0;column<mainList.size();column++) 
		 //counter keeping track of which column in the arraylist(each column contains one 
		 //specific type of information i.e mobile number) is being used
		 {  
	         results+=String.format("%-30s %s", contactHeadings[column], mainList.get(column).get(row-1)  )+ "\n";
	         //the string [] contactHeadings is looped through to print out the heading that corresponds
	         //to the type of information that is to be printed out
			 if(row%4==0&&column==4)
			 //If the list counter is a multiple of 4 then we have stored the maximum ammount of information 
			 //that can be printed at one time to the results variable and that informaton should be
			 //printed before continuing to add to results
			 {  
				 System.out.println(results); //prints out all the contact information stored so far
				 System.out.println(((row)/4) + " of " + (((mainList.get(0).size()+4)/4) ) + " (hit any key to continue)");
				 //displays a message showing the number of pages of output produced so far 
				 //and the total number
				 anyKey=keyboard.nextLine();
				 //the user can input anything they want, provided they then press the 
				 //enter key in order to continue
				 results="";
				 //the information previously stored in results that has now been printed out is reset
			     
			 }
			 else if (row==mainList.get(0).size()&&column==4)
		     //if the row counter has reached the end of the arraylist this statement is entered
		     //and all the remaining information stored in results is printed out.
			 { 
				 System.out.println(results + "\n" + " All contacts are displayed");
				 System.out.println(((row+3)/4) + " of " + (((mainList.get(0).size()+4)/4) ) + " (hit any key to continue)");
			 }
		 			 
	      }
    
	   }
		  
	    
	  
	  
	}
	
	public static void searchContacts(String input) throws IOException //method that searches through array list
    { 
	  	  String anyKey = "";
	  int totalPages=((mainList.size()+4)/4);
	  String [] contactHeadings = {"Surname: ", "Forename: ", "Landline number: ", "Mobile number: ", "Email address: ", "Postal address: "}; 
	  String results = "";
	  ArrayList<ArrayList<String>> searchResultsToDisplay = new ArrayList<ArrayList<String>>();
	  //arraylist that stores all of the information to be printed out,contains 6 arraylists
	  //each of which correspond to one of the different pieces of information required
	  searchResultsToDisplay.add(new ArrayList<String>());
	  searchResultsToDisplay.add(new ArrayList<String>());
	  searchResultsToDisplay.add(new ArrayList<String>());
	  searchResultsToDisplay.add(new ArrayList<String>());
	  searchResultsToDisplay.add(new ArrayList<String>());
	  searchResultsToDisplay.add(new ArrayList<String>());
	  boolean partOfSearch;
	  String [] allInput;
	  String letterPattern = "[A-Za-z]+";
	  String landLinePattern = "0[1,2]{1}||02[1,9]{1}||0402||0404||04[^8^0]{1}||050[4,5]{1}||";
	  String landLinePattern2= "05[^4^5]{1}||06[1,9]{1}||071||074||09[^2]{1}";
	  String mobilePattern = "083||085||086||087||089";
	  String emailPattern = "@(.+)$"; 
	  String fullName = "", searchFor = "", name = "";
	  Scanner keyboard = new Scanner(System.in);
	  allInput = input.split(" "); //splits the "s" that prompts the search from the input being searched for
	  searchFor = allInput[1]; //the input being searched for is copied into the string searchFor
	  if(searchFor.matches(letterPattern)) 
	  {
		     for (int row=0;row<mainList.get(0).size() ;row++)
		     //outer for loop loops through each row in the arraylist, each row contains all 
		     // details of a single contact
			 { 
				 fullName=mainList.get(0).get(row); //gets a permanent handle on the column (0) containing names and goes through the rows getting each name
				 String firstLetter = fullName.substring(0, 1); //creates a string consisting of the letters at index positions from 0 to 1(not inclusive)i.e the first letter
				 if (searchFor.equalsIgnoreCase(firstLetter)) //checks if the letter being searched for matches the first letter of a surname stored in the ArrayList
					  { 
						 for(int column=0;column<6;column++ )
					     { 
						    String info = (mainList.get(column).get(row));
						    searchResultsToDisplay.get(column).add(info);
						   //the column tracker loops through the arraylist getting all 
						   //of the information for a contact who matches the search, this 
						   //information is stored in the info String then added to a new 
						   //arraylist which was designed to store the information to 
						   //be printed out
					     
						 }
					  
			          }
		     }
		  }
		  else if(searchFor.matches(landLinePattern)||searchFor.matches(landLinePattern2) )
		  { 
			 for(int row=0;row<mainList.get(2).size();row++)
			 { 
				 String number = mainList.get(2).get(row);
				 int prefixLength = searchFor.length();
				 String prefix = number.substring(0, (prefixLength));//takes an ammount of numbers equal to the length of the area code being searched for
				 if(searchFor.matches(prefix)) //if the input being searched for matches the prefix of the contacts number
				 { 
					 for(int column=0;column<6;column++ )
				     { 
					    String info = (mainList.get(column).get(row));
					    searchResultsToDisplay.get(column).add(info);
				     }
			     }
		     }
		  }
		 else if(searchFor.matches(mobilePattern))
		 { 
			for(int row=0;row<mainList.get(3).size();row++)
			{ 
				String mobileNum=mainList.get(3).get(row);//gets a handle on the column containing all the mobile numbers and goes down through it
				String prefix=mobileNum.substring(0,3); //gets the first 3 digits(the prefix) as a substring
			    if (searchFor.matches(prefix))
			    { 
					 for(int column=0;column<6;column++ )
				     { 
					    String info = (mainList.get(column).get(row));
					    searchResultsToDisplay.get(column).add(info);
				     }
			     }
			}
		 }
		 else if(searchFor.matches(emailPattern))
		 { 
			 for (int row=0;row<mainList.get(4).size();row++)
			 { 
				 String email = mainList.get(4).get(row);
				 int atSymbol = email.indexOf("@");
				 //gets the position of the @ symbol in the email address
				 String domain = email.substring(atSymbol, email.length());
				 //gets the email service provider i.e. @gmail.com
				 if(searchFor.matches(domain))
				 { 
					 for(int column=0;column<6;column++ )
				     { 
					    String info = (mainList.get(column).get(row));
					    searchResultsToDisplay.get(column).add(info);
				     }
			     }
				 
			 }
		  }
		  else 
		  {
			  System.out.println("Invalid entry, does not match any pattern");
		  }
		  for(int row=1;row<=searchResultsToDisplay.get(0).size();row++) 
				 //counter keeping track of which row in the arrayList (each row contains the information
				 // of one contact) is being used 
				  {  
					 for(int column=0;column<6;column++) 
					 //counter keeping track of which column in the arraylist(each column contains one 
					 //specific type of information i.e mobile number) is being used
					 {  
						 results+= String.format("%-30s %s", contactHeadings[column], searchResultsToDisplay.get(column).get(row-1))+"\n";
						 //The information to be stored in results is formatted such that the first string is left justified with "padding"
						 //being placed to the right of the string as far as the 30th space position, the next string is then added on 
						 //followed by the "\n" next line command.
				         
						 if(row%4==0&&column==5)
						 {  
							 System.out.println(results); //prints out all the contact information stored so far
							 System.out.println(((row+3)/4) + " of " + (((searchResultsToDisplay.get(0).size()+4)/4) ) + " (hit any key to continue)");
							 anyKey=keyboard.nextLine();
							 results="";
							 //the information previously stored in results that has now been preinted out is reset
						 }
						 else if (row==searchResultsToDisplay.get(0).size()&&column==5)
						 { 
							 System.out.println(results + "\n" + " All contacts are displayed");
							 System.out.println((((searchResultsToDisplay.get(0).size()+4)/4) ) + " of " + (((searchResultsToDisplay.get(0).size()+4)/4) ) + " (hit any key to continue)");
							 anyKey=keyboard.nextLine();
						 }
					 }			 
				  }
		  
		  
	    }
		  
	public static void printInfo(String [] args) throws IOException  //prints to file
    { 
          FileWriter aFileWriter = new FileWriter(args[0]);       //creates a file object
          PrintWriter out = new PrintWriter(aFileWriter);         //creates a print object, printing out to the file
		  int numberOfContacts = mainList.get(0).size();          //number of contacts in the file
          String currentWord;
	      for(int x = 0; x < numberOfContacts; x++)               //for loop set up to print out the array lists to the file
	      {
	        for(int y = 0; y < 6; y++)
	        {
			  currentWord = mainList.get(y).get(x);
			  if(!currentWord.equals("*"))
			  {
	           out.print(mainList.get(y).get(x) + ",");
			  }
	        }
	        out.println();
	        
 
        }
		System.out.println("Data have been printed to file");
		out.close();
   
  }
	public static void editContacts(String input)   //edit contact information
 {
   int k = 0;                                 //variable for keeping track of what the user entered(landline etc..)
   String [] words;                           //String to keep userInout in
   words = input.split(",");                  //Splits input into an array
   boolean notFound = true;                   //boolean to check if the method found a contact 
   String currentSurname, currentForename, finalCheck;     //Strings for use in method
  
   
   if(words.length != 4)         //if the user enters more or less then for entry's it stops 
   {
	   System.out.println("Error invalid input. ");
   }
   else
   {
    for(int x = 0; (x < mainList.get(0).size())&& notFound;x++)        //for loop set up to go through the array list
	{
	 currentSurname = mainList.get(0).get(x);                          //current surname
	 currentForename = mainList.get(1).get(x);                         //current forename
	 if((currentSurname.equals(words[0])) && (currentForename.equals(words[1])))        //if surnames and forenames match..
	 {
	   
     if(words[2].matches(LandlinesTest1))                  //checks what type of information the user enters and if it is invalid prints out invalid and stops
			k = 2;
		
		else if(words[2].matches(LandlinesTest2))
			k = 2;
		
		else if(words[2].matches(MobileTest))
			k = 3;
		
		else if(words[2].matches(EmailTest))
			k = 4;
		else if(words[2].matches(AddressTest))
		    k = 5;
		
		finalCheck = mainList.get(k).get(x);             //String for what the user wants to change and checks it to see if it is equal to what he entered
		if(finalCheck.equals(words[2]))                  //if it is it is changed and notFound is set to false and stops the for loop
		{
		  mainList.get(k).set(x, words[3]);
		  
		  notFound = false;
		}
		
		
		
      }
	}
	  if(notFound) System.out.println("Not found");      //prints out if it has been found or not
	  else System.out.println("Found and edited");
	}
     
	 
   
 }
    public static void removeContacts(String input)
	{
	  int k = 0;                                 //variable for keeping track of what the user entered(landline etc..)
     String [] words;                           //String to keep userInout in
     words = input.split(",");                  //Splits input into an array
     boolean notFound = true;                   //boolean to check if the method found a contact 
     String currentSurname, currentForename, finalCheck;     //Strings for use in method
  
   
   if(words.length != 3)         //if the user enters more or less then for entry's it stops 
   {
	   System.out.println("Error invalid input. ");
   }
   else
   {
    for(int x = 0; (x < mainList.get(0).size())&& notFound;x++)        //for loop set up to go through the array list
	{
	 currentSurname = mainList.get(0).get(x);                          //current surname
	 currentForename = mainList.get(1).get(x);                         //current forename
	 if((currentSurname.equals(words[0])) && (currentForename.equals(words[1])))        //if surnames and forenames match..
	 {
	   
     if(words[2].matches(LandlinesTest1))                  //checks what type of information the user enters and if it is invalid prints out invalid and stops
			k = 2;
		
		else if(words[2].matches(LandlinesTest2))
			k = 2;
		
		else if(words[2].matches(MobileTest))
			k = 3;
		
		else if(words[2].matches(EmailTest))
			k = 4;
		else if(words[2].matches(AddressTest))
		    k = 5;
		
		finalCheck = mainList.get(k).get(x);             //String for what the user wants to change and checks it to see if it is equal to what he entered
		if(finalCheck.equals(words[2]))                  //if it is it is changed and notFound is set to false and stops the for loop
		{
		  for(int z = 0; z < 6; z++)
		  {
		     mainList.get(z).set(x, "*");
		  }
		  
		  notFound = false;
		}
		
		
		
      }
	}
	  if(notFound) System.out.println("Not found");      //prints out if it has been found or not
	  else System.out.println("Contact removed");
	}
	}
}