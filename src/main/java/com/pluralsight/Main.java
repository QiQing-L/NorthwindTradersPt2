package com.pluralsight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) {
        try {
            if(args.length !=2 ){
                System.out.println("Missing Username and Password to run this application!");
                System.exit(1);
            }

            String username = args[0];
            String password = args[1];

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/northwind",
                    username,
                    password
            );

            String query = """
                    SELECT ProductID AS `Product ID`,\s
                    		ProductName AS `Product Name`,\s
                    		UnitPrice AS `Unit Price`,\s
                    		UnitsInStock AS `Units In Stock`
                    FROM products
                    ORDER BY ProductID;\s
                    """;

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet results = preparedStatement.executeQuery();

            System.out.println("ID   Name                                Price       Stock");
            System.out.println("---  ---------------------------------   ---------   -----");
            while (results.next()) {
                int productID = results.getInt(1);
                String productName = results.getString(2);
                double unitPrice = results.getDouble(3);
                int unitsInStock = results.getInt(4);

                System.out.printf("%-4d %-35s $%-10.2f %d\n",
                       productID, productName, unitPrice, unitsInStock);

            }

            results.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("There's an error! ");
            e.printStackTrace();
        }


    }
}
