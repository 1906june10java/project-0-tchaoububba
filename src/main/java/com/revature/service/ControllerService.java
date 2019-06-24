package com.revature.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.exception.BalanceTooSmallException;
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
			System.out.println(
					"You are already logged in.  Please logout if you are attempting to login as someone else.");
		} else {
//			Code to log in here
			System.out.println("Please enter your username.");
			String username = scanner.nextLine();
			System.out.println("Please enter your password.");
			String password = scanner.nextLine();
			user.setUsername(username);
			user.setPassword(password);
//			Insert scanned username + password into ConnectionDriver info.
			LOGGER.trace("Entering login method with parameters: " + username + ", " + password);
			try (Connection connection = ConnectionUtil.getConnection(username, password)) {
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

	public void viewBalance(User user) {
//		Code to print out the balance here
		if (user.isLoginStatus()) {
			try (Connection connection = ConnectionUtil.getConnection(user.getUsername(), user.getPassword())) {
				// * could be "AS [DESIRED_NAME]
				String sql = "SELECT * FROM BANK_ACCOUNT";

				PreparedStatement statement = connection.prepareStatement(sql);

				ResultSet result = statement.executeQuery();

				if (result.next()) {
					double balance = result.getDouble("BALANCE");
					user.setBalance(balance);
					System.out.println("Your balance is $" + user.getBalance());
				}
			} catch (SQLException e) {
				LOGGER.error("Could not find balance.", e);
			}
		} else {
			System.out.println("You are not logged in. You must login before you may view or update your balance.");
		}
	}

//	Look for a way to check the money for no more than 2 decimal places if we have time.
	public void depositMoney(User user, Scanner scanner) {
		double depositAmount = 0;
		if (user.isLoginStatus()) {
			viewBalance(user);
			System.out.println("How much money would you like to deposit?");
			try {
				depositAmount = scanner.nextDouble();
			} catch (InputMismatchException e) {
				System.out.println("A valid number was not received. Please enter your deposit amount as a number.");
			}
			if (depositAmount >= 0) {
				user.setBalance((user.getBalance() + depositAmount));

				try (Connection connection = ConnectionUtil.getConnection(user.getUsername(), user.getPassword())) {
					// * could be "AS [DESIRED_NAME]
					String sql = "UPDATE bank_account SET BALANCE = ?";

					PreparedStatement statement = connection.prepareStatement(sql);
					System.out.println("REMOVE BEFORE FINALIZATION! Amount to be updated to: " + user.getBalance());
					statement.setDouble(1, user.getBalance());
					statement.executeUpdate();

				} catch (SQLException e) {
					LOGGER.error("Could not set balance.", e);
					System.out.println("There was an error in depositing your money.");
					return;
				}

				System.out.println("You have deposited $" + depositAmount + ".");
				viewBalance(user);
			} else {
				depositAmount = 0;
				try {
					throw new IllegalArgumentException();
				} catch (IllegalArgumentException e) {
					System.out.println("A valid number was not received.  You must enter a positive amount to deposit.");
				}
			}
		} else {
			System.out.println("You are not logged in. You must login before you may view or update your balance.");
		}
	}

//	Look for a way to check the money for no more than 2 decimal places if we have time.
	public void withdrawMoney(User user, Scanner scanner) {
		
		double withdrawAmount = 0;
		if (user.isLoginStatus()) {
			viewBalance(user);
			System.out.println("How much money would you like to withdraw?");
			try {
				withdrawAmount = scanner.nextDouble();
			} catch (InputMismatchException e) {
				System.out.println("A valid number was not received. Please enter your deposit amount as a number.");
			}
			if (withdrawAmount >= 0 && withdrawAmount <= user.getBalance()) {
				user.setBalance((user.getBalance() - withdrawAmount));

				try (Connection connection = ConnectionUtil.getConnection(user.getUsername(), user.getPassword())) {
					// * could be "AS [DESIRED_NAME]
					String sql = "UPDATE bank_account SET BALANCE = ?";

					PreparedStatement statement = connection.prepareStatement(sql);
					statement.setDouble(1, user.getBalance());
					statement.executeUpdate();

				} catch (SQLException e) {
					LOGGER.error("Could not set balance.", e);
					System.out.println("There was an error in withdrawing your money.");
					return;
				}

				System.out.println("You have withdrawn $" + withdrawAmount + ".");
				viewBalance(user);
			} else if (withdrawAmount < 0) {
				withdrawAmount = 0;
				try {
					throw new IllegalArgumentException();
				} catch (IllegalArgumentException e) {
					System.out.println("A valid number was not received.  You must enter a positive amount to withdraw.");
				}
			} else {
				withdrawAmount = 0;
				try {
					throw new BalanceTooSmallException();
				} catch (BalanceTooSmallException e) {
					System.out.println("You cannot withdraw an amount larger than your available balance.");
				}
			}
		} else {
			System.out.println("You are not logged in. You must login before you may view or update your balance.");
		}
	}
}
