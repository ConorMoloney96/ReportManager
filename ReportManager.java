import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;
import java.io.*;
import javax.swing.*;

public class ReportManager
{
 public static ArrayList<Genre> genreList         = new ArrayList<Genre>();
 public static ArrayList<Game> gameList           = new ArrayList<Game>();
 public static ArrayList<Developer> developerList = new ArrayList<Developer>();
 public static ArrayList<Sale> saleList           = new ArrayList<Sale>();
 public static ArrayList<Integer> GamesByDeveloper = new ArrayList<Integer>();
 
 public static void main(String [] args) throws IOException
 {
    String filename1 = "GamesGenre.txt";
	String filename2 = "GamesDetails.txt";
	String filename3 = "GameDevelopers.txt";
	String filename4 = "GamesSales.txt";
	
	Genre aGenre;
	Game aGame;
	Developer aDeveloper;
	Sale aSale;
	
	int tempNum, gameID, devNumber, unitsSold, posOfComa, genreNumber;
    String genreType, gameDev, devName, saleDate, gameTitle;	
	double gamePrice;
	
	String lineFromFile = "";
	String [] fileElements;
	File aFile;
	Scanner in;
	
	aFile = new File(filename1);
	if(!aFile.exists())
	  System.out.println("File Does not Exist(1)");
	else
	{
	 in = new Scanner(aFile);
	 while(in.hasNext())
	 {
	    lineFromFile = in.nextLine(); //next line scanned from the file is stored
		posOfComa = lineFromFile.indexOf(","); //
		genreNumber = Integer.parseInt(lineFromFile.substring(0, posOfComa)); //gets everything from the start of the line to the first comma and stores as a genre Number
		genreType = lineFromFile.substring(posOfComa + 1); //
		aGenre = new Genre(genreNumber, genreType); //creates aGenre an onbject of Type genre with genreNumber and genreType passed to the constructor
		genreList.add(aGenre); //the object aGenre is added to the arraylist in which each element contains information about 1 specific genre
	 }
	 in.close();
	 
	 aFile = new File(filename2);
	 if(!aFile.exists())
	  System.out.println("File Does not Exist(2)");
	 else
	 {
	  in = new Scanner(aFile);
	  while(in.hasNext())
	  {
	     lineFromFile = in.nextLine();
		 fileElements = lineFromFile.split(",");
		 gameID = Integer.parseInt(fileElements[0]);
		 gameTitle = (fileElements[1]);
		 devNumber = Integer.parseInt(fileElements[2]);
		 genreNumber = Integer.parseInt(fileElements[3]);
		 gamePrice = Double.parseDouble(fileElements[4]);
		 aGame = new Game(gameID, gameTitle, devNumber, genreNumber, gamePrice);
		 gameList.add(aGame);
	  }
	  in.close();
	  
	  aFile = new File(filename3);
	  if(!aFile.exists())
	   System.out.println("File Does not Exist(3)");
	  else
	  {
	   in = new Scanner(aFile);
	   while(in.hasNext())
	   {
	      lineFromFile = in.nextLine();
		  fileElements = lineFromFile.split(",");
		  devNumber = Integer.parseInt(fileElements[0]);
		  devName = fileElements[1];
		  aDeveloper = new Developer(devNumber, devName);
		  developerList.add(aDeveloper);
		  System.out.println("Developer info added");
	   }
	   in.close();
	   
	   aFile = new File(filename4);
	   if(!aFile.exists())
	    System.out.println("File Does not Exist(4)");
	   else
	   {
	    in = new Scanner(aFile);
	    while(in.hasNext())
	    {
	       lineFromFile = in.nextLine();
		   fileElements = lineFromFile.split(",");
		   saleDate = fileElements[0];
		   unitsSold = Integer.parseInt(fileElements[1]);
		   gameID = Integer.parseInt(fileElements[2]);
		   aSale = new Sale(saleDate, unitsSold, gameID);
		   saleList.add(aSale);
	    }
	    in.close();
		
		
	   }
	}
   }
  }
  menu();
  
  
 }
 
 public static void salesReportFour() throws IOException
 {
	String gName [] = new String [gameList.size()];        //Array fr the list of Games(Parallel)
	double cost = 0;
	double totalSales [] = new double[gameList.size()];    //Array for the total Sales(Parallel)
	int units = 0;
	double temp;
	String temp1;
	
	for(int i = 0; i < gameList.size(); i++)        //Goes through the the game list
	{
	  units = 0;
	  cost = 0;
	  int ID = gameList.get(i).getGameID();             //gets game Id
	  String name = gameList.get(i).getGameTitle();     //gets..
	  double price = gameList.get(i).getGamePrice();    //gets...
	  
	  for(int x = 0; x < saleList.size(); x++)       //Goes through sales txt and checks units sold and what game
	  {
	    if(ID == saleList.get(x).getGameID())
		{
		 units += saleList.get(x).getSaleUnits();
		}
	  }
	  
	  cost = units * price;
	  gName[i] = name;
	  totalSales[i] = cost;  //Adds the Sales and Name into parallel arrays
	}
	
    for(int y = 1; y <= (gName.length - 1); y++)    //Use bubble sort to sort the name of the game and the Sales
	{
	 for(int z = 1; z <= (gName.length - y); z++)
	 {
	    if(totalSales[z - 1] < (totalSales[z]))
		{
		 temp = totalSales[z - 1];
		 temp1 = gName[z - 1];
		 totalSales[z - 1] = totalSales[z];
		 gName[z - 1] = gName[z];
		 totalSales[z] = temp;
		 gName[z] = temp1;
		}
	 }
	
  }
  
  System.out.println("\nSales to date based on game title"); //Prints the result.
  for(int t = 0; t < totalSales.length; t++)
  {
    System.out.println("Name:\t" + gName[t]);
	System.out.println("Sales:\t" + totalSales[t] + "\n");
  }
 }
 
 public static void salesReportFive() throws IOException
 {
	double cost = 0;
	int units = 0;
	double temp;   //Practically the same code as salesReportFour but gets the genre ID and the two parallel arrays(Genres and Sales)
	String temp1;
	int ID;
	double price;
	int genre;
	String [] listOfGenreNames = ReturnGenreName();    //Creates a list of the different types of Genres
	double [] genreSales = new double [listOfGenreNames.length];
	
	for(int i = 0; i < gameList.size(); i++)
	{
	  units = 0;
	  cost = 0;
	  genre = gameList.get(i).getGenreID();
	  price = gameList.get(i).getGamePrice();
	  ID = gameList.get(i).getGameID();
	  
	  for(int x = 0; x < saleList.size(); x++)
	  {
	    if(ID == saleList.get(x).getGameID())
		{
		 units += saleList.get(x).getSaleUnits();
		}
	  }
	  
	  cost = units * price;
	  genreSales[genre] += cost;
	  
	}
	
    for(int y = 1; y <= (genreSales.length - 1); y++)
	{
	 for(int z = 1; z <= (genreSales.length - y); z++)
	 {
	    if(genreSales[z - 1] < genreSales[z])
		{
		 temp = genreSales[z - 1];
		 temp1 = listOfGenreNames[z - 1];
		 genreSales[z - 1] = genreSales[z];
		 listOfGenreNames[z - 1] =  listOfGenreNames[z];
		 genreSales[z] = temp;
		 listOfGenreNames[z] = temp1;
		}
	 }
	}
	 System.out.println("\nSales to date based on game genre");
	 int t = (genreSales.length - 1);
	 while(t >= 0)
    {
      System.out.println("Name:\t" + listOfGenreNames[t]);
	  System.out.println("Sales:\t" + genreSales[t] + "\n");
	  t--;
    }
 }
   public static void menu() throws IOException  
 {
		Scanner in = new Scanner(System.in);
		String input = "";
		while(input != "0")
		{
		
		 System.out.println("\n\t\t\tMenu");
		 System.out.println("1.Game Sales for each month and total Sales for the year");
		 System.out.println("2.Genre Sales for each month and total Sales for the year");
		 System.out.println("3.Developer Sales for each month and total Sales for the year");
		 System.out.println("4.Game Sales for each Game to date(Descending Sequence)");
		 System.out.println("5.Genre Sales for each Genre to date(Ascending Sequence)");
		 System.out.println("0.Exit");
		 input = in.nextLine();
		 if(input.matches("0")) System.exit(0);
		 else if(input.matches("1")) gameSalesReport();
		 else if(input.matches("2")) genreSalesReport();
		 else if(input.matches("3")) developerSalesReport();
		 else if(input.matches("4")) salesReportFour();
		 else if(input.matches("5")) salesReportFive();
	   } 
 }
 
 
	public static void developerSalesReport() throws IOException
	{ 
	  int input;
	  String monthOption = "";
	  int [] monthlySales = {0,0,0,0,0,0,0,0,0,0,0,0}; //array with 12 elements, each representing the sales for a particular month. Default values are set to 0.
	  String [] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	  int month;
	  String date = "";
	  int totalSales = 0;
	  String [] dateElements;
	  Scanner keyboard = new Scanner(System.in);
	  System.out.println("Enter developer whose sales you would like to seach for:");
	  for(int i=0; i<developerList.size(); i++)
		  System.out.println(developerList.get(i).getDeveloperID()+"\t"+developerList.get(i).getDeveloperName());
	  input = Integer.parseInt(keyboard.nextLine());
	  if(input<1 || input>developerList.size()) //if the input is greater than the number of elements stored in the Developers Arraylist (i.e. number of developers listed)
	  { 
	     System.out.println("invalid entry ");
	  }
	  else
	  {  
		   for(int i=0;i<gameList.size();i++) //goes through the arraylist containing the details (specifically loooking for developers) for each game
			{ 
			   if(input==gameList.get(i).getDeveloperID()) //if the id of the developers being searched for matches the id of the developers for a certain game
			   { 
			       GamesByDeveloper.add(gameList.get(i).getDeveloperID()); //adds the id of the game whose sale information is stored in the same object (Details[i])
				   //as the developer id which matches the search
			   }
			
			}
			
			int currentMonth=(Calendar.getInstance().get(Calendar.MONTH))+1; //gets the current month and adds 1 as month indexing starts at 0
			
			
			for(int i=0;i<saleList.size();i++) //each iteration will have a new month value representing the month of the sale in question
			{ 
			   
			   date = saleList.get(i).getSaleDate(); //gets the date stored in each object of the sales Arraylist
			   dateElements = date.split("/"); //splits the date into it's component parts
			   month = Integer.parseInt(dateElements [1]); //gets the month
			   for(int j=0;j<GamesByDeveloper.size();j++)
			   {
				   if(month<=currentMonth && saleList.get(i).getGameID()==GamesByDeveloper.get(j))
					//as long as the month of the sale is less than the current month it is added to the sales to be displayed
				    //and if the ID for the specific sale matches any ID from the array containing all the games by the developer being searched
					//for
				   { 
			           monthlySales[month-1]+=saleList.get(i).getSaleUnits(); //the sales for the sale object (i.e game) in question are added to the monthlySales
					   //Array to the index position of the month which we got from the date of the object -1(as the month shown in the date of the object
					   //starts with 0 but the array monthlySales beings with index position 0)
					   
			       }
			   }
			}
			
			
			
			for (int i=0;i<monthlySales.length;i++)
			{ 
		        System.out.println("The sales for the month of " + months[i] + " were " + monthlySales[i]);
				totalSales+=monthlySales[i];
		    }
			
			System.out.println("The total sales were " + totalSales);
			
			
			
		   
		 }
	  }
  
 public static String [] ReturnGenreName() throws IOException
 {
	String [] genreNames = new String [genreList.size()];
	String gName;
	for(int x = 0; x < genreList.size(); x++)
	{
	 gName = genreList.get(x).getGenreName();
	 genreNames[x] = gName;
	}
	
	return genreNames;
 }
 
 public static void gameSalesReport()
	{
		Scanner in=new Scanner(System.in);
		int input;
		boolean cont=true;										//we dont want to continue if a false number is input
		int[] Numbers  = new int[gameList.size()];				//3 parrallel arrays to store game number, name and price
		String[] Names = new String[gameList.size()];
		double[] Price    = new double[gameList.size()];
		for(int i=0; i<gameList.size(); i++)					//fill in the arrays
		{
			Numbers[i] = gameList.get(i).getGameID();
			Names[i]   = gameList.get(i).getGameTitle();
			Price[i]   = gameList.get(i).getGamePrice();
			System.out.println(gameList.get(i).getGameID()+"\t"+gameList.get(i).getGameTitle());	//print the game number and title for user to choose from
		}
		System.out.print("\nPlease enter the number of the game you wish to view:\t");
		input=Integer.parseInt(in.nextLine());
		if (input<1 || input>gameList.size())
		{
			System.out.print("Invalid number, no corresponding game found");
			cont=false;
		}
		if(cont)
		{
			GregorianCalendar aCalendar = new GregorianCalendar();				//get current month
			int currentMonth=((aCalendar.get(Calendar.MONTH)) + 1);
			int[]monthlySales=new int[12];
			for(int i=0; i<saleList.size(); i++)							//for all the sales before the current month and match the game number
			{																//we get the number of unit sold for that month
				String Date		 =saleList.get(i).getSaleDate();
				String[]DateSplit=Date.split("/");
				int month		 =Integer.parseInt(DateSplit[1]);
				if(saleList.get(i).getGameID()==input && month<=currentMonth)
					monthlySales[month]+=saleList.get(i).getSaleUnits();
			}
			String tempM="";
			double totalSales=0;
			System.out.println();
			for(int i=0; i<currentMonth; i++)
			{
				if(monthlySales[i]!=0)					//if there was units sold for a certain month its printed out
				{
					switch(i)
					{
					case 1:	tempM="January";	break;
					case 2:	tempM="February";	break;
					case 3:	tempM="March"; 		break;
					case 4:	tempM="April";	 	break;
					case 5:	tempM="May";	 	break;
					case 6:	tempM="June"; 		break;
					case 7:	tempM="July"; 		break;
					case 8:	tempM="August";		break;
					case 9:	tempM="September"; 	break;
					case 10:tempM="October";	break;
					case 11:tempM="November";	break;
					case 12:tempM="December"; 	break;
					}
					int mSales=monthlySales[i];
					System.out.printf("The Sales for "+tempM+" were "+mSales+" unit(s), which equals "+(Price[input-1]*mSales)+"\n");
					totalSales+=(Price[input-1]*mSales);
				}
			}
			System.out.printf("The total Sales for the year are %.2f \n",totalSales);
		}
	}
	
	public static void genreSalesReport()				//very similar code to gameSalesReport
	{
		Scanner in=new Scanner(System.in);
		int input;
		boolean cont=true;
		for(int i=0; i<genreList.size(); i++)
		{
			System.out.println(genreList.get(i).getGenreID()+"\t"+genreList.get(i).getGenreName());
		}
		System.out.print("\nPlease enter the number of the Genre you wish to view:\t");
		input=Integer.parseInt(in.nextLine());
		if (input<1 || input>genreList.size())
		{
			System.out.print("Invalid number, no corresponding game found ");
			cont=false;
		}
		if(cont)														//if the input for genre is valid
		{
			double totalSales=0;
			ArrayList<Integer> IDNums = new ArrayList<Integer>();
			ArrayList<Double>  Price  = new ArrayList<Double>();
			for(int i=0; i<gameList.size(); i++)
			{
				if(gameList.get(i).getGenreID()==input)					//we go through the list of games and any with a 
				{														//matching genre we store the games number and price
					Price.add(gameList.get(i).getGamePrice());
					IDNums.add(gameList.get(i).getGameID());
				}
			}
			GregorianCalendar aCalendar = new GregorianCalendar();		//again we get the current month
			int currentMonth=((aCalendar.get(Calendar.MONTH)) + 1);
			double[] monthlySales =new double[12];
			int[]    monthlyUnits =new int[12];
			for(int i=0; i<saleList.size(); i++)
			{
				String Date		 =saleList.get(i).getSaleDate();
				String[]DateSplit=Date.split("/");
				int month		 =Integer.parseInt(DateSplit[1]);
				if(month<=currentMonth)
					for(int k=0; k<IDNums.size(); k++)
						if(saleList.get(i).getGameID()==IDNums.get(k))		//we then go through the saleList and find any sales with
						{													//game ID matching the list of gameID's we stored above
							monthlySales[month]+=(saleList.get(i).getSaleUnits()*Price.get(k));		//if so we get the units sold and the sales for that month
							monthlyUnits[month]+=(saleList.get(i).getSaleUnits());
							totalSales+=Price.get(k)*saleList.get(i).getSaleUnits();
						}
			}
			String tempM="";
			System.out.println();
			for(int i=0; i<=currentMonth; i++)
			{
				if(monthlyUnits[i]!=0)
				{
					switch(i)
					{
					case 1:	tempM="January";	break;
					case 2:	tempM="February";	break;
					case 3:	tempM="March"; 		break;
					case 4:	tempM="April";	 	break;
					case 5:	tempM="May";	 	break;
					case 6:	tempM="June"; 		break;
					case 7:	tempM="July"; 		break;
					case 8:	tempM="August";		break;
					case 9:	tempM="September"; 	break;
					case 10:tempM="October";	break;
					case 11:tempM="November";	break;
					case 12:tempM="December"; 	break;
					}
					
					System.out.printf("The Sales for "+tempM+" were "+monthlyUnits[i]+" unit(s), which equals "+monthlySales[i]+"\n");
				}
			}
			System.out.printf("The total Sales for the year are %.2f \n",totalSales);
		}
	}
}