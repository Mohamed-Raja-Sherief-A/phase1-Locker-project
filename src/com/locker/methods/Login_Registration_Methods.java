package com.locker.methods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

import com.locker.exception.RegistrationException;
import com.locker.model.User;
import com.locker.thread.SearchThread;

public class Login_Registration_Methods {

private String loggedUser;
//Menu to display Login & Registration	
public void loginMenu()
{
	
	Scanner s=new Scanner(System.in);
	int ch = 0;
	welcomeScreen();//Welcome screen called only once
	do
	{	
	
	System.out.println("1.Login");
	System.out.println("2.Registration");
	System.out.println("3.Exit");
	System.out.println("Enter your option:");
	try
	{
	ch=s.nextInt();
	}
	catch(NumberFormatException e)
	{
		System.out.println("---------------------------------------");
		System.out.println("Enter valid input . Re Run the program ");
		System.out.println("---------------------------------------");
	}
	catch(InputMismatchException e)
	{
		System.out.println("---------------------------------------");
		System.out.println("Enter valid input . Re Run the program ");
		System.out.println("---------------------------------------");
		break;
	}
	switch(ch)
	{
	case 1: try {
			login();
		} catch (LoginException e) {
			System.out.println("---------------------------------------");
			System.out.println(e.getMessage());
			System.out.println("---------------------------------------");
		}
			break;
	case 2: try {
			registration();
				}
			catch(RegistrationException e)
			{
				System.out.println("---------------------------------------");
				System.out.println(e.getMessage());	
				System.out.println("---------------------------------------");
			}
	
		 	break;
		 	
	case 3:
		    System.out.println("---------------------------------------");
		    System.out.println("Thank you for visiting Locker.com");
		    System.out.println("---------------------------------------");
			break;
	default : 
		System.out.println("---------------------------------------");
		System.out.println("Enter valid input 1,2,3");
		System.out.println("---------------------------------------");
			  break;
	
	}
	}while(ch!=3);// Loop exits with option 3
	
	
	
}

//Method to search a given string in the file
//Used to search if username already exists while registering
public Boolean search(String userName)
{
	File file=new File("users.txt");
	Boolean found=false;
	try {
		Scanner input=new Scanner(file);
		while(input.hasNext()&&!found)
		{
			if(input.next().equals(userName))
			{
				found=true;
			}
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return found;
}

//Method to Enter login credentials and to search if they exists in the file
public void login() throws LoginException
{
	File file=new File("users.txt");
	Boolean found=false;
	String userName,password;
	Scanner s=new Scanner(System.in);
	System.out.println("==========================================");
	System.out.println("*					*");
	System.out.println("*   WELCOME TO LOGIN PAGE               *");
	System.out.println("*					*");
	System.out.println("==========================================");
	try {
		if(file.length()>0) //condition to check the file size .Wont proceed if 0 or less
		{
		Scanner input=new Scanner(file);
		System.out.println("Enter Username:");
		userName=s.nextLine();
			if(!userName.isEmpty()) //if empty characters are given
			{
				while(input.hasNext() && !found) //loop through the text file
				{
					if(input.next().equals(userName))//username validation
					{
						System.out.println("Enter Password:");
						password=s.nextLine();
						if(input.next().equals(password))//password validation
						{
							found=true;         //boolean 
							loggedUser=userName;
						}
						else
						{
							break;   // if password wrong the loop breaks
						}
					}
					
				}
				if(found==false)  //boolean validation
				{
					throw new LoginException("Enter Valid Username & Password"); //throw custom exception
				}
				else
				{
					User_Business_Methods um=new User_Business_Methods(loggedUser);
					System.out.println("---------------------------------------");
					System.out.println("Login Successfull!!!!!");
					System.out.println("---------------------------------------");
					System.out.println();
					um.mainMenuDisplay(); //method call for sub menu
				}
			}
			else
			{
				System.out.println("---------------------------------------");
				System.out.println("Enter non null values");
				System.out.println("---------------------------------------");
			}
		}
		else
		{
			System.out.println("---------------------------------------");
			System.out.println("No new Registrations");
			System.out.println("---------------------------------------");
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		System.out.println("---------------------------------------");
		System.out.println("No new Registrations");
		System.out.println("---------------------------------------");
	}
	
	
}

//Registration Display & implimentation
@SuppressWarnings("resource")
public void registration() throws RegistrationException
{
	File file=new File("users.txt");  //File declared
	Scanner s=new Scanner(System.in);
	String userName = null,password;
	Boolean fileExist=false;
	Boolean found=false;
	String name="Raja";

	try {
		fileExist=file.createNewFile(); // creating new file
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	System.out.println("==========================================");
	System.out.println("*					*");
	System.out.println("*   WELCOME TO REGISTRATION PAGE        *");
	System.out.println("*					*");
	System.out.println("==========================================");
	 try
	 {
		
		 PrintWriter output = new PrintWriter( new FileWriter(file, true));
			System.out.println("Enter User name:");
			 userName=s.nextLine();
			 System.out.println("Enter Password");
			 password=s.nextLine();
			User user=new User(userName, password);
			StringBuffer sb=new StringBuffer(found.toString()); //string buffer for mutablity
			SearchThread search=new SearchThread(sb, userName); //Thread Class
			Thread t=new Thread(search); //Thread initialisation
			t.start();                   //Thread start
			t.join();           // waits for the thread to complete
			found=Boolean.parseBoolean(sb.toString());
			System.out.println(found);
			//if(!search(userName)) // method call for search-alternative for thread
			if(!found)
			{
				if(!userName.isEmpty()&& !password.isEmpty() && userName.charAt(0)!=' ' && password.charAt(0)!=' ') //non null values are only added
				{
				output.println(userName);
				output.println(password);
				System.out.println("---------------------------------------");
				System.out.println("User Added Succesfully!!!!!");
				System.out.println("---------------------------------------");
				}
				else
				{
					System.out.println("---------------------------------------");
					System.out.println("Enter non null values");
					System.out.println("---------------------------------------");
				}
			}
			else
			{
				
				throw new RegistrationException("Username Already Exist"); //Custom exception throw
			}
			output.close();
		
	  }
	catch(Exception e)
	{
		System.out.println("---------------------------------------");
		System.out.println(e.getMessage());
		System.out.println("---------------------------------------");
	}
}
//welcome screen method
public static void welcomeScreen() {
	System.out.println("==========================================");
	System.out.println("*					*");
	System.out.println("*   Welcome To LockMe.com		*");
	System.out.println("*   Your Personal Digital Locaker	*");
	System.out.println("* Developed by : A.Mohamed Raja Sherief	*");
	System.out.println("==========================================");
	
}
	


}
