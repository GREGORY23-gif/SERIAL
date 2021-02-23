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
import gnu.io.SerialPort;
 
import java.util.TooManyListenersException;
 
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
 
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
 


public class SerialUsingTester implements SerialPortEventListener{
                    
                SerialUsingTester(){

            }
                public static void main(String[] args) {
            if (args.length < 1) {
                System.out.println(
                        "Usage: java SerialUsingTester <dataToBeSentToSerialPort>");
                System.exit(1);
            }
 
            SerialUsing serialUsing = new SerialUsing();
 
            checkListSerialPorts(serialUsing);
            checkConnect(serialUsing);
            checkAddDataAvailableListener(serialUsing, args[0]);
            checkDisconnect(serialUsing);
        }
 
        private static void checkListSerialPorts(SerialUsing serialUsing) {
            System.out.println("Check the listSerialPorts");
            System.out.println("-------------------------");
            String[] serialPorts = SerialUsing.listSerialPorts();
            if (serialPorts != null) {
                for (int i = 0; i < serialPorts.length; i++) {
                    System.out.println("Port name: " + serialPorts[i]);
                }
            }
            System.out.println();
        }
            
            private void setSerialEventHandler(SerialPort serialPort) {
                try {
                    // Add the serial port event listener
                    serialPort.addEventListener(new SerialEventHandler());
                    serialPort.notifyOnDataAvailable(true);
                } catch (TooManyListenersException ex) {
                    System.err.println(ex.getMessage());
                }
            }
 
        private static void checkConnect(SerialUsing serialUsing) {
            // Replace it with the tested serial port
            final String serialPort = "/dev/ttyS0";
 
            System.out.println("Connect to serial port");
            System.out.println("-------------------------");
            try {
                serialUsing.connect(serialPort);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
 
            System.out.println();
        }
 
        private static void checkAddDataAvailableListener(
                SerialUsing serialUsing, String data) {
            System.out.println("Check data available listener");
            System.out.println("-----------------------------");
 
            SerialUsingTester tester =
                    new SerialUsingTester(serialUsing.getSerialInputStream(),
                    serialUsing.getSerialOutputStream());
 
            try {
                serialUsing.addDataAvailableListener(tester);
            } catch (TooManyListenersException ex) {
                System.err.println(ex.getMessage());
            }
 
            OutputStream outStream = serialUsing.getSerialOutputStream();
            data = data + "\r";
            try {
                outStream.write(data.getBytes());
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
 
            try {
                // Sleep for 10-secs
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
            }
        }
 
        private static void checkDisconnect(SerialUsing serialUsing) {
            System.out.println("Disconnect from serial port");
            System.out.println("---------------------------");
            serialUsing.disconnect();
            System.out.println("done");
        }
        /**
         * Buffer to hold the reading
         */
       public byte[] readBuffer = new byte[400];
 
        private void readSerial() {
            try {
                int availableBytes = inStream.available();
                if (availableBytes > 0) {
                    // Read the serial port
                    inStream.read(readBuffer, 0, availableBytes);
                    // Print it out
                    System.out.println(
                            new String(readBuffer, 0, availableBytes));
                }
            } catch (IOException e) {
            }
        }
        /**
         * I/O stream for serial port
         */
        public InputStream inStream;
        public OutputStream outStream;
 
        public SerialUsingTester(InputStream inStream, OutputStream outStream) {
            this.inStream = inStream;
            this.outStream = outStream;
        }
 
    /**
     *
     * @param events
     */
    @Override
        public void serialEvent(SerialPortEvent events) {
            switch (events.getEventType()) {
                case SerialPortEvent.DATA_AVAILABLE:
                    readSerial();
            }
        }
    }
