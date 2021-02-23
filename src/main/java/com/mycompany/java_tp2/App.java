package com.mycompany.java_tp2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.fazecast.jSerialComm.*;
import java.io.IOException;
/**
 * JavaFX App
 */
public class App extends Application  {

    private static void addDataAvailableListener(SerialPort port) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void addDataListener(SerialPortDataListener serialPortDataListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

                    App(){ }
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
         SerialPort[] ports = SerialPort.getCommPorts();
         String write = "GENIE  EN   HERBE ";
         //String write_1 = "LIFE IS GIGAS";
        ports[1].setBaudRate(9600);
        ports[1].openPort();
       /* ports[2].setBaudRate(9600);
        ports[2].openPort();*/
        
        ports[1].writeBytes(write.getBytes(), write.length());
        ports[1].addDataListener(new SerialPortDataListener() {
			@Override
			public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
			@Override
			public void serialEvent(SerialPortEvent event)
			{
                               SerialPort comPort = event.getSerialPort();
				System.out.println("Available: " + comPort.bytesAvailable() + " bytes.");
				byte[] newData = new byte[comPort.bytesAvailable()];
				int numRead = comPort.readBytes(newData, newData.length);
				System.out.println("Read " + numRead + " bytes.");
			}
		});
        
        //launch();
        }

    
    }

 