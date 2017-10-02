/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bankapplication.respository.impl;

import com.bankapplication.model.Customer;
import java.util.ArrayList;
import com.bankapplication.respository.ICustomerRepository;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @name CustomerRepositoryImpl
 * @author derickfelix
 * @date Oct 2, 2017
 */
public class CustomerRepository extends BaseRepository implements ICustomerRepository {

    private final String table = "`customers`";
    private ArrayList<Customer> customers;

    @Override
    public ArrayList<Customer> all() {
        Connection conn = connectionManager.createConnection();
        String sql = "select * from " + table;
        // Intantiate only once
        if (customers == null) {
            customers = new ArrayList<>();
        } else {
            customers.clear();
        }

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                String acc = rs.getString("account_number");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String sex = rs.getString("sex");
                String dob = rs.getString("born_date");
                String accType = rs.getString("account_type");
                String password = rs.getString("password");

                Customer tempCustomer = new Customer(acc, name, address, accType, sex.charAt(0), dob);
                tempCustomer.setPassword(password);
                customers.add(tempCustomer);
            }
            statement.close();
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex);
        }
        return customers;
    }

    @Override
    public Customer find(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Customer customer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void store(Customer customer) {
        Connection conn = connectionManager.createConnection();
        String sql = "INSERT INTO " + table + " (`account_number`, `name`, `address`, `sex`, `born_date`, `account_type`, `password`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            // Prepare Statement
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, customer.getAccountNumber());
            pstmt.setString(2, customer.getName());
            pstmt.setString(3, customer.getAddress());
            pstmt.setString(4, Character.toString(customer.getSex()));
            pstmt.setString(5, customer.getDob());
            pstmt.setString(6, customer.getAccountType());
            pstmt.setString(7, customer.getPassword());
            // Execute Staement
            pstmt.execute();
            // Close Connection
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("SQL Erro: " + ex);
        }
    }

    @Override
    public void destroy(Customer customer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
