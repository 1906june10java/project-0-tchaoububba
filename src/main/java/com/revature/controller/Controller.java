package com.revature.controller;

import java.util.Scanner;

public class Controller {
	
	//Have every class check for loginStatus.
	
	public Controller() {
		System.out.println("What would you like to do?");
	}
	
	//Scanner for username and password (and everything)
	Scanner scanner = new Scanner(System.in);
	String username, password;
	boolean loginStatus = false;
	
	public void login() {
		System.out.println("Please enter your username and password.");
		//Code to log in here
		username = scanner.next();
		password = scanner.next();
		//I'm going to move the above variables into a Service class.
		//Look into checking if user is already logged in
		//TODO Insert scanned username + password into ConnectionDriver info.
		//Print "Welcome "username"
		//WrongUsernameOrPasswordException?
	}
	
	public void logout() {
		//Code to log out here
		//Should always log out, regardless of whether user is logged in or out
		//Print success
	}
	
	public void viewBalance() {
		//Code to print out the balance here
	}
	
	//Look for a way to check the money for no more than 2 decimal places and (also for positive money if we have time).  Throw IllegalArgumentException if that happens.
	public void withdrawMoney(double money) {
		//Code to check if balance > money (BalanceTooSmallException maybe will be a new IllegalArgumentException?)
		//Decrease the balance by money amount
		//Print out a success statement (maybe use viewBalance method)
	}
	
	public void depositMoney (double money) {
		//Code to increase balance by money amount
		//(IllegalArgumentException for negative money, if we have time)
		//Print out a success statement (maybe use viewBalance method)
	}

}
