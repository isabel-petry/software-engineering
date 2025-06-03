package com.example;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class App implements Serializable{
    private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws URISyntaxException, SQLException, ClassNotFoundException {
        Menu.mostrarMenu();
    }
}
