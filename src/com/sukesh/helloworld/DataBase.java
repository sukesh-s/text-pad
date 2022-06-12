package com.sukesh.helloworld;

import javax.swing.*;
import java.sql.*;

public class DataBase {
    static Connection connection=null;
    public String TABLE_NAME_NOTES="NOTES";
    static String dbUrl="jdbc:sqlite::resource:appDB.sqlite";
    public String NOTE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME_NOTES+"(note_id INTEGER PRIMARY KEY AUTOINCREMENT,content TEXT)";
    public  static Connection connectDatabase(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection= DriverManager.getConnection(dbUrl);
            return  connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e);
        }
        return null;
    }

    public  void createTable(String query, Connection connection){
        if(connection==null || query==null){
            return;
        }
        try {
            Statement statement=connection.createStatement();
            statement.execute(query);
            statement.close();
//            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public  void insertData(String content,Connection connection)  {
        String query="INSERT INTO "+TABLE_NAME_NOTES+" (content) VALUES ('"+content+"')";
        if(connection==null){
            return;
        }
        try {
            Statement statement= null;
            statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public KeyPair getContentToFirstRowOfNoteTable(Connection connection){
        String query="SELECT * FROM "+TABLE_NAME_NOTES+" LIMIT 1";
        KeyPair keyPair;
        if(connection==null){
            return new KeyPair("","");
        }
        try {
            Statement statement=connection.createStatement();
            ResultSet resultSet= statement.executeQuery(query);
            while (resultSet.next()){
                String content=resultSet.getString("content");
                String id=resultSet.getString("note_id");
                return new KeyPair(id,content);
            }
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return new KeyPair("","");
        }
        return new KeyPair("","");
    }
    public void updateContentToFirstRowOfNoteTable(String contents,Connection connection){
        if(connection==null || contents==null){
            return;
        }
        KeyPair data=getContentToFirstRowOfNoteTable(connection);
        if(data.getKey()==null){
            insertData(contents, connection);
        }else{
        try {
            String lastKey=data.getKey();
            String lastValues=data.getValue();
            if(lastValues.equals(contents)){
                return;
            }
            String query="UPDATE "+TABLE_NAME_NOTES+" SET content='"+contents+"' WHERE note_id="+lastKey+"";
            Statement statement=connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }


    }
}
