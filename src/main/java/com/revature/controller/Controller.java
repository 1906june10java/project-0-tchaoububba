package com.revature.controller;

import java.util.Scanner;

import com.revature.model.User;
import com.revature.service.ControllerService;

public class Controller {
	
//	Have every class check for loginStatus.
	
	public Controller() {
		
		User user = new User();
//		Scanner for username and password (and everything)
		Scanner scanner = new Scanner(System.in);
//		boolean loginStatus = false;

		boolean infiniteLoop = true;		
		while (infiniteLoop == true) {
			
			System.out.println("What would you like to do? You may login, logout, view your balance, deposit money, or withdraw money."
					+ "\nEnter 'LOGIN', 'LOGOUT', 'VIEW', 'DEPOSIT', 'WITHDRAW', or 'EXIT' (without the quotes).");
			
			String userAction = scanner.nextLine();
//			Handles when null or "" is passed
			if (userAction == null || userAction.length() == 0) {
				try {
					throw new IllegalArgumentException();
				} catch (IllegalArgumentException e) {
					System.out.println("Expected an action request");
				}
			}
			
			String normalizedUserAction = userAction.toLowerCase();
			ControllerService service = ControllerService.getInstance();
			
			switch(normalizedUserAction) {
			case "login":
				service.login(user, scanner);
				break;
			case "exit":
				infiniteLoop = false;
			case "logout":
				service.logout(user);
				break;
			case "view":
				service.viewBalance();
				break;
			case "deposit":
				service.depositMoney();;
				break;
			case "withdraw":
				service.withdrawMoney();
				break;
			default:
				try {
					throw new IllegalArgumentException();
				} catch (IllegalArgumentException e) {
					System.out.println("Not a valid action request");
				}
			}
		}
		System.out.println("Exiting program");
		scanner.close();
	}
}
