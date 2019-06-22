package com.revature.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.exception.WrongUsernameOrPasswordException;
import com.revature.model.User;
import com.revature.util.ConnectionUtil;

public final class ControllerService {
	
	private static final Logger LOGGER = Logger.getLogger(ControllerService.class);
	
	private static ControllerService instance;
	
	private ControllerService() {
		System.out.println("Instantiation has been restricted.  REMOVE THIS LINE BEFORE FINALIZATION!");
	}
	
	public static ControllerService getInstance() {
		if (instance == null) {
			instance = new ControllerService();
		}
		return instance;
	}
	
	public void login(User user, Scanner scanner) {
//		Check if user is already logged in
		if (user.isLoginStatus()) {
			System.out.println("You are already logged in.  Please logout if you are attempting to login as someone else.");
		} else {
//			Code to log in here
			System.out.println("Please enter your username.");
			String username = scanner.nextLine();
			System.out.println("Please enter your password.");
			String password = scanner.nextLine();
			user.setUsername(username);
//			Insert scanned username + password into ConnectionDriver info.
			LOGGER.trace("Entering login method with parameters: " + username + ", " + password);
			try(Connection connection = ConnectionUtil.getConnection(username, password)) {
				if (connection.isValid(0)) {
					user.setLoginStatus(true);
				} else {
					user.setLoginStatus(false);
				}
			} catch (SQLException e) {
				LOGGER.error("Could not login.", e);
			}
			if (user.isLoginStatus()) {
				System.out.println("Welcome " + user.getUsername() + "!");
			} else {
				try {
					throw new WrongUsernameOrPasswordException();
				} catch (WrongUsernameOrPasswordException e) {
					System.out.println("Wrong username and/or password was given. Please try again.");
				}
			}
		}		
		
		
	}
	
	public void logout(User user) {
//		Code to log out here
		if (user.isLoginStatus()) {
			user.setLoginStatus(false);
			System.out.println("You have successfully logged out!");
		} else {
			System.out.println("You are not logged in.");
		}
	}
	
	public void viewBalance() {
//		Code to print out the balance here
	}
	
//	Look for a way to check the money for no more than 2 decimal places and (also for positive money if we have time).  Throw IllegalArgumentException if that happens.
	public void depositMoney() {
//		Ask how much money
//		Code to increase balance by money amount
//		(IllegalArgumentException for negative money, if we have time)
//		Print out a success statement (maybe use viewBalance method)
	}
	
	public void withdrawMoney() {
//		Ask how much money
//		Code to check if balance > money (BalanceTooSmallException maybe will be a new IllegalArgumentException?)
//		Decrease the balance by money amount
//		Print out a success statement (maybe use viewBalance method)
	}

}
