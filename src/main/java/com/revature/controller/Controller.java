package com.revature.controller;

public class Controller {
	
	//Have Controller ask for login when instantiated.
	public Controller() {
		System.out.println("Please enter your username and password.");
		//Scanner for username and password???????
	}
	
	public void login(String username, String password) {
		//Code to log in here
		//Look into checking if user is already logged in
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
