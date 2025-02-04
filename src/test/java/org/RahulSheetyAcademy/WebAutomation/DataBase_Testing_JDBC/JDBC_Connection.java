package org.RahulSheetyAcademy.WebAutomation.DataBase_Testing_JDBC;

import java.sql.Statement;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC_Connection {
	public static void main(String[] args) throws SQLException {
		WebDriver driver = null;
		String host = "localhost";
		String port = "3306";
		String userNameOfDB = "root";
		String PasswordOfDB = "root";
		String DBname = "/mydb";

		Connection con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + DBname, userNameOfDB,
				PasswordOfDB);
		//This line creates a connection to the database using the DriverManager.getConnection() method.

		/*
		 * DriverManager.getConnection("URL","userName","Password"); 
		 * Syntax of URL >>"jdbc:mysql://"+host+":"+port+"//databas
		 * 
		 * The URL format for MySQL is "jdbc:mysql://[host]:[port]/[database]", 
		 * so the code constructs the URL like jdbc:mysql://localhost:3306/mydb.eName";
		 * 
		 * host: ours is localhost port: while installing MYSQL database we will give
		 * the port number databaseName: name of the database from where we are pulling
		 * the data.
		 * 
		 * "jdbc:mysql://"+localhost+":"+3306+"/FireFlink";
		 */
		Statement s = con.createStatement();
		/*
		 * Creating a Statement to Execute SQL Query:This line creates a Statement object 
		 * which will be used to execute SQL queries on the connected database
		 * */
		
		ResultSet rs = s.executeQuery("select * from FireFlink where envi='test'");
		/*Executing SQL Query: 
		 * The executeQuery() method executes the SQL query "select * from FireFlink where envi='test'"
		 * */
		
		// ResultSet is a array & it will store the value in 1st index by default
		// if "rs.next()" is not written then it will point to base index.
		while (rs.next()) {
			
			/*
			 * The while (rs.next()) loop iterates through each row of the result set.
               rs.next() moves the cursor to the next row. Initially, the cursor is before 
               the first row, so calling next() moves it to the first row.
			 * */
			String browserName = rs.getString("browser");
			String UserName = rs.getString("userName");
			String password = rs.getString("password");

			switch (browserName) {
			case "chrome":
				driver = new ChromeDriver();
				break;
			case "firefox":
				driver = new FirefoxDriver();
				break;
			case "edge":
				driver = new EdgeDriver();
				break;

			default:
				System.out.println("No browser is matching with the name" + browserName);
				break;
			}

			driver.manage().window().maximize();
			driver.get("https://www.fireflink.com/signin");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("emailId")));
			driver.findElement(By.id("emailId")).sendKeys(UserName);
			driver.findElement(By.xpath("//input[@name=\"password\"]")).sendKeys(password);
			driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();
			driver.quit();
			rs.close();
			s.close();
			con.close();
		}
	}
}