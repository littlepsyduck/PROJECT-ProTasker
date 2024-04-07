/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package application;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author littlepsyduck
 */
public class Main {
    static LogInScreen logInScreen = new LogInScreen();
    static SignUpScreen signUpScreen = new SignUpScreen();
    /**
     */
    public static void main() {
        try{
            UIManager.setLookAndFeel("de.javasoft.synthetica.simple2d.SyntheticaSimple2DLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            Logger.getLogger(Main.class.getName()).log(Level.ALL, null, e);
        }
        
        switchWindow(1);

    }

    static void switchWindow(int i) {
        if(i == 1){
            Timer timer1 = new Timer(3000, _ -> {
                float opacity = signUpScreen.getOpacity() - 0.05f;
                if (opacity <= 0) {
                    signUpScreen.setVisible(false);
                } 
            });

            java.awt.EventQueue.invokeLater(() -> {
            logInScreen.setVisible(true);
            timer1.start();
            });
        }
        else if(i == 0){
            Timer timer1 = new Timer(3000, _ -> {
                float opacity = logInScreen.getOpacity() - 0.05f;
                if (opacity <= 0) {
                    logInScreen.setVisible(false);
                } 
            });

            java.awt.EventQueue.invokeLater(() -> {
            signUpScreen.setVisible(true);
            timer1.start();
            });
        }
    }
    
    static void openCalendar(String username) {
        MainScreen CL = new MainScreen(username);
        MainScreen.updateMonth();
        CL.setVisible(true);
    }

}
