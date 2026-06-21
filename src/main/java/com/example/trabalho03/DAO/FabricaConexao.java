package com.example.trabalho03.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {
    public static Connection pega() throws ErroDao{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost/gertarefas?useSSL=false","root","root");
        } catch (ClassNotFoundException | SQLException e) {
            throw new ErroDao(e);
        }
    }
}
