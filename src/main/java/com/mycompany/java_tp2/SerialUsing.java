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
 
import gnu.io.SerialPortEventListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
 
import java.util.ArrayList;
import java.util.Enumeration;


        
public class SerialUsing {

    private SerialPort serialPort;
    private OutputStream outStream;
    private InputStream inStream;
 
    /**
     * \brief List the available serial ports
     *
     * \return Array of string for the available serial port names
     * @return 
     */
    public static String[] listSerialPorts() {
        Enumeration ports = CommPortIdentifier.getPortIdentifiers();
        ArrayList portList = new ArrayList();
        String portArray[] = null;
        while (ports.hasMoreElements()) {
            CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
            if (port.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                portList.add(port.getName());
            }
        }
        portArray = (String[]) portList.toArray(new String[0]);
        return portArray;
    }
 
    /**
     * \brief Connect to the selected serial port with 9600bps-8N1 mode
     * @param portName
     */
    public void connect(String portName) throws IOException {
        try {
            // Obtain a CommPortIdentifier object for the port you want to open
            CommPortIdentifier portId =
                    CommPortIdentifier.getPortIdentifier(portName);
 
            // Get the port's ownership
            serialPort = (SerialPort) portId.open("Application", 5000);
 
            // Set the parameters of the connection.
            setSerialPortParameters();
 
            // Open the input and output streams for the connection.
            // If they won't open, close the port before throwing an
            // exception.
            outStream = serialPort.getOutputStream();
            inStream = serialPort.getInputStream();
        } catch (NoSuchPortException e) {
            throw new IOException(e.getMessage());
        } catch (PortInUseException e) {
            throw new IOException(e.getMessage());
        } catch (IOException e) {
            serialPort.close();
            throw e;
        }
    }
 
    /**
     * \brief Get the serial port input stream
     * \return The serial port input stream
     * @return 
     */
    public InputStream getSerialInputStream() {
        return inStream;
    }
 
    /**
     * \brief Get the serial port output stream
     * \return The serial port output stream
     */
    public OutputStream getSerialOutputStream() {
        return outStream;
    }
 
    /**
     * \brief Sets the serial port parameters to 9600bps-8N1
     */
    protected void setSerialPortParameters() throws IOException {
 
        final int baudRate = 9600; // 9600bps
 
        try {
            // Set serial port to 9600bps-8N1..my favourite
            serialPort.setSerialPortParams(
                    baudRate,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
 
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
        } catch (UnsupportedCommOperationException ex) {
            throw new IOException("Unsupported serial port parameter");
        }
    }
 
    /**
     * \brief Register listener for data available event
     *
     * @param dataAvailableListener The data available listener
     */
    public void addDataAvailableListener(SerialPortEventListener dataAvailableListener)
            throws TooManyListenersException {
        // Add the serial port event listener
        serialPort.addEventListener(dataAvailableListener);
        serialPort.notifyOnDataAvailable(true);
    }
 
    /**
     * \brief Disconnect the serial port
     */
    public void disconnect() {
        if (serialPort != null) {
            try {
                // close the i/o streams.
                outStream.close();
                inStream.close();
            } catch (IOException ex) {
                // don't care
            }
            // Close the port.
            serialPort.close();
            serialPort = null;
        }
}
}
