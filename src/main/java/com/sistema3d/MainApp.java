package com.sistema3d;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("main-view.fxml"));
        
        // Modificado: Definido o tamanho inicial da cena para 1024px de largura por 680px de altura
        Scene scene = new Scene(fxmlLoader.load(), 1024, 680);
        
        stage.setTitle("Calculadora de Custos - Impressão 3D");
        stage.setScene(scene);
        
        // Modificado: Permitir que a tela seja redimensionada/maximizada pelo usuário
        stage.setResizable(true); 
        
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}