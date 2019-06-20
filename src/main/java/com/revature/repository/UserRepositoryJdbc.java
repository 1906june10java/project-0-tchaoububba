package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.model.User;
import com.revature.util.ConnectionUtil;

public class UserRepositoryJdbc {
	
	private static final Logger LOGGER = Logger.getLogger(UserRepositoryJdbc.class);
	
	public User findByUsernameAndPassword(String username) {
		LOGGER.trace("Entering find user by username method with parameter: " + username);
		try(Connection connection = ConnectionUtil.getConnection()) {
			int parameterIndex = 0;
			//* could be "AS [DESIRED_NAME]
			String sql = "SELECT * FROM HERO WHERE H_NAME = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(++parameterIndex, name);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				return new Hero (
						result.getLong("H_ID"),
						result.getString("H_NAME"),
						result.getString("H_REAL_NAME"),
						result.getString("H_SUPER_POWER"),
						null,
						result.getString("H_GENDER"),
						new Universe(result.getLong("U_ID"), "")
						);
			}
		} catch (SQLException e) {
			LOGGER.error("Could not find hero.", e);
		}
		
		return null;
	}

}
