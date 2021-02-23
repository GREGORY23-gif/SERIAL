/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.java_tp2;

/**
 *
 * @author ASUS
 */
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
 
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import java.util.TooManyListenersException;
 
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
 
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
 
import java.util.ArrayList;
import java.util.Enumeration;


public class SerialEventHandler extends SerialUsingTester implements SerialPortEventListener{
        
             @Override
             public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {
            case SerialPortEvent.DATA_AVAILABLE:
                readSerial();
                break;
        }
    }

         private void readSerial() {
            try {
                int availableBytes = inStream.available();
                if (availableBytes > 0) {
                    // Read the serial port
                    inStream.read(readBuffer, 0, availableBytes);
 
                    // Print it out
                    System.out.println("Recv :" +
                            new String(readBuffer, 0, availableBytes));
                }
            } catch (IOException e) {
            }
        }
}
