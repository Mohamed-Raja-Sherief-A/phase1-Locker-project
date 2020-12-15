package com.locker.thread;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SearchThread implements Runnable {

	private StringBuffer found;
	private String userName;
	



	public SearchThread(StringBuffer found, String userName) {
		super();
		this.found = found;
		this.userName = userName;
		
	}




	@Override
	public void run() {
		File file=new File("users.txt");
		Boolean f=false;
		try {
			System.out.println("Searching if username exists");
			Scanner input=new Scanner(file);
			while(input.hasNext()&&!f)
			{
				if(input.next().equals(userName))
				{
					f=true;
					found.replace(0,5,"true");  //StringBuffer replaces false to true
					
					
					
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
