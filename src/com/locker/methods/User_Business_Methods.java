package com.locker.methods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.locker.exception.FileEmptyException;
import com.locker.exception.SearchException;
import com.locker.model.Credentials;

public class User_Business_Methods {
	
	private String userName;
	private String fileName;
	public User_Business_Methods(String userName) { //constructor to dynamically set the user details
		super();
		this.userName = userName;
		this.fileName=userName+".txt";
	}
	
	//method to display the main menu
	public void mainMenuDisplay()
	{
		int ch = 0;
		Scanner s=new Scanner(System.in);
		System.out.println("==========================================");
		System.out.println("*					*");
		System.out.println("*   WELCOME TO MAIN MENU*"+userName);
		System.out.println("*					*");
		System.out.println("==========================================");
		do
		{
			
			System.out.println("1.Show All Credentials");
			System.out.println("2.File Operations");
			System.out.println("3.Back");
			System.out.println("Enter your choice:");
			try
			{
			ch=s.nextInt();
			}
			catch(InputMismatchException e)
			{
				System.out.println("---------------------------------------");
				System.out.println("Enter a valid number 1,2,3- Re login");
				System.out.println("---------------------------------------");
				break;
			}
			switch(ch)
			{
			case 1:try {
					displayAll();// method call for display all
				} catch (FileEmptyException e) {
					System.out.println("---------------------------------------");
					System.out.println(e.getMessage());
					System.out.println("---------------------------------------");
					
				}  
			       break;
			case 2:subMenuDisplay(); // method call for sub menu
					break;
			case 3: 
					break;
			default:System.out.println("---------------------------------------");
					System.out.println("Enter a valid number 1,2,3");
					System.out.println("---------------------------------------");
					break;
			}
		}while(ch!=3); // loop exits at ch 3
	}
	
	//method to display all credentials
	public void displayAll() throws FileEmptyException {
		File file=new File(fileName);
		List<Credentials> credentials=new ArrayList<Credentials>(); //arrayList for credential storing
		int i=0;
		try
		{
		 Scanner input=new Scanner(file);
		 if(file.length()>0) // checks if file empty
		 {
			 while(input.hasNext()) //loops to end of the file
			 {
				 String socialNetwork=input.next();
				 String userName=input.next();
				 String password=input.next();
				 Credentials credential=new Credentials(socialNetwork, userName, password);
				 credentials.add(credential); 
				
				
			 }
			 Collections.sort(credentials);//sorted by social network in ascending order
			 for(Credentials c:credentials)
			 {
				 System.out.print("CREDENTIAL ");
				 System.out.print(++i);
				 System.out.println();
				 System.out.println("--------------------------------");
				 System.out.println("Social Network:"+c.getSocialNetwork());
				 System.out.println("Username:"+c.getUserName());
				 System.out.println("Password:"+c.getPassword());
				 System.out.println("--------------------------------");
			 }
		 }
		 else
		 {
			throw new FileEmptyException("No data Found"); // custom error thrown
		 }
		}
		catch(FileNotFoundException e) // if file not created this catch will be caught
		{
			System.out.println("---------------------------------------");
			System.out.println("File not found / No Data entered");
			System.out.println("---------------------------------------");
		}
	}
	
	
	//method to display sub menu with business logic
	public void subMenuDisplay()
	{
		int ch = 0;
		Scanner s=new Scanner(System.in);
		System.out.println("==========================================");
		System.out.println("*					*");
		System.out.println("*   WELCOME TO File Handling*"+userName);
		System.out.println("*					*");
		System.out.println("==========================================");
		do
		{
			
			System.out.println("1.Add credentials");
			System.out.println("2.Delete File");
			System.out.println("3.Search Credentials");
			System.out.println("4.Back");
			System.out.println("Enter your choice:");
			try
			{
			ch=s.nextInt();
			}
			catch(InputMismatchException e)
			{
				System.out.println("---------------------------------------");
				System.out.println("Enter a valid number 1,2,3,4- Entering Main menu");
				System.out.println("---------------------------------------");
				break;
			}
			switch(ch)
			{
			case 1:addCredentials(); //method call
					break;
			case 2: deleteFile();	//method call
					break;
			case 3: try {
					searchCredentials();//method call
				} catch (SearchException e) {
					System.out.println("---------------------------------------");
					System.out.println(e.getMessage());
					System.out.println("---------------------------------------");
				} 
					break;
			case 4:	
					break;
			default:System.out.println("---------------------------------------"); 
					System.out.println("Enter a valid number 1,2,3,4 ");
					System.out.println("---------------------------------------");
						break;
			}
		}while(ch!=4); // loop breaks at ch 4
	}
	
	// method to search credentials
	private void searchCredentials() throws SearchException {
		// TODO Auto-generated method stub
		File file=new File(fileName);
		String search;
		Boolean found=false;
		int i=0;
		Scanner s=new Scanner(System.in);
		Scanner input = null;
		try {
			input = new Scanner(file);
			System.out.println("Enter social network name to search (case sensitive):");
			search=s.nextLine();
			System.out.println();
			System.out.println(search+" Details");
			while(input.hasNext())
			{
				if(input.next().equals(search)) //condition to check the given string exists
				{
					System.out.println(++i);
					System.out.println("--------------------------");
					found=true;
					System.out.println("Username:"+input.next());
					System.out.println("Password:"+input.next());
					System.out.println("--------------------------");
				}
			}
			if(!found)
			{
				
				throw new SearchException("Social network details not found");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("---------------------------------------");
		   System.out.println("File not created");
		   System.out.println("---------------------------------------");
		}
		
		
		
		
	}
	
	//delete method to delete file
	private void deleteFile() {
		Scanner s=new Scanner(System.in);
		String ch;
		File file=new File(fileName);
		if(file.exists()) //checks if file exist
		{
			System.out.println("Entire file will be deleted are you sure (y/n):");
			ch=s.next();
			if(ch.equals("y")||ch.equals("n"))
			{
				if(ch.equals("y"))
				{
					if(file.delete()) //delete operation
					{
						System.out.println("---------------------------------------");
						System.out.println("File Deleted !!!!");
						System.out.println("---------------------------------------");
					}
					else
					{
						System.out.println("---------------------------------------");
						System.out.println("File cant be deleted used by other process in background(close all background process)");
						System.out.println("---------------------------------------");
					}
				}
				else
				{
					System.out.println("---------------------------------------");
					System.out.println("File not Deleted");
					System.out.println("---------------------------------------");
				}
			}
			else
			{
				System.out.println("---------------------------------------");
				System.out.println("Enter y/n");
				System.out.println("---------------------------------------");
			}
		}
		else
		{
			System.out.println("---------------------------------------");
			System.out.println("File is not created yet.To create add credentials");
			System.out.println("---------------------------------------");
		}
		
	}
	
	//method to add credentials
	private void addCredentials() {
		File file=new File(fileName);
		String socialNetwork,userName,password;
		Scanner input=new Scanner(System.in);
		try
		{
			file.createNewFile(); //to create file
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		try {
			PrintWriter output=new PrintWriter(new FileWriter(file,true));
			System.out.println("Enter Social network name:");
			socialNetwork=input.nextLine();
			System.out.println("Enter Useranme:");
			userName=input.nextLine();
			System.out.println("Enter Password");
			password=input.nextLine();
			if(!socialNetwork.isEmpty()&&!userName.isEmpty()&&!password.isEmpty()&&socialNetwork.charAt(0)!=' '&&userName.charAt(0)!=' '&&password.charAt(0)!=' ') //checks if any given value if empty
			{
			output.println(socialNetwork);
			output.println(userName);
			output.println(password);
			System.out.println("---------------------------------------");
			System.out.println("Credentials added succesfully !!!!");
			System.out.println("---------------------------------------");
			}
			else
			{
				System.out.println("---------------------------------------");
				System.out.println("Enter non null values-Write failed");
				System.out.println("---------------------------------------");
			}
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		
	}

}
