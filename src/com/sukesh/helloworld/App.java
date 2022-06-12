package com.sukesh.helloworld;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;

public class App {
    private JPanel panel1;
    private JEditorPane editorPane1;
    static DataBase dataBase;
    static Connection connection;

    public App() {
        KeyPair noteData=dataBase.getContentToFirstRowOfNoteTable(connection);
        editorPane1.setText(noteData.getValue());
        editorPane1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String text=editorPane1.getText();
                dataBase.updateContentToFirstRowOfNoteTable(text,connection);
            }
        });
    }


    public static void main(String[] args) {
        dataBase= new DataBase();
        connection= DataBase.connectDatabase();
        dataBase.createTable(dataBase.NOTE_TABLE,connection);
        JFrame  frame=new JFrame("Hello World");
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
        frame.setIconImage(icon);
        frame.setContentPane(new App().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
