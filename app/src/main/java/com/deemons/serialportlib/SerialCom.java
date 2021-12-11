package com.deemons.serialportlib;

import android.content.Context;
import android.serialport.SerialPort;
import android.util.Log;

import com.deemons.serialportlib.utils.crc17;
import com.example.testingserial.MainActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class SerialCom {
    Context cont;
    String quantity;
    String allstatus = "";

    public SerialCom(Context cont) {
        this.cont = cont;

    }

    MainActivity mcont;

    public SerialCom(MainActivity cont) {
        this.mcont = cont;
    }

    public ReadThread thisreadthread;

    private class ReadThread extends Thread {
        private ReadThread() {

        }

        public void run() {
            try {
                mMySerialLib = new SerialPort(new File(Hport), 9600, 1, 8, 1, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            super.run();
            int i = 1;
            int i2 = mMySerialLib != null ? 1 : 0;

            if ((i2 & i) != 0) {
                try {

                    if (mMySerialLib.getInputStream() != null) {

                        String readresponse;
                        readresponse = isToString(mMySerialLib.getInputStream());
                        System.out.println("Barla " + readresponse);

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();

                }


            }

        }
    }

    SerialPort mMySerialLib;

    String generatecommand(Integer motor, Integer row) {

        byte[] initialarr= new byte[2];
        byte[] dataarr= new byte[18];

        StringBuilder command = new StringBuilder();

        //head
        dataarr[0] = 0x01;
        //start machine
        dataarr[1] = 0x05;
        //motor index number
        dataarr[2] = motor.byteValue();
        //motor type
        dataarr[3] = row.byteValue();
        //lightscreen working mode
        dataarr[4] = 0x00;
        //overcurrent - default
        dataarr[5] = 0x00;
        //undercurrent - default
        dataarr[6] = 0x00;
        //Timeout - default
        dataarr[7] = 0x00;

       // command.append(ByteUtils.bytesToHexString(initialarr));
        command.append(ByteUtils.bytesToHexString(dataarr));

        int[] finalCRC = crc17.calculateCRC(dataarr,0,dataarr.length);


        command.append(ByteUtils.intArrToHex(finalCRC));

        return command.toString();


    }

    String generatedeliverycommand(String boardname, String status, String qty, String oid) {


        String orderid = oid;

        String fpart = "," + orderid + "," + qty + "," + status;

        String crccc = fpart;

        crc17 crcclas = new crc17();
        String finalCRC = ByteUtils.bytesToHexString(crcclas.computeCrcB(crccc.getBytes())).toLowerCase();

        fpart = "," + finalCRC + fpart;
        StringBuilder bool = new StringBuilder();
        ;
        try {
            //bool.append("[viceboard,");
            bool.append("[" + boardname + ",");
            bool.append(fpart.length());
            //oid,aile,qty
            bool.append(fpart);
            bool.append("]");
            //  sendSerialPort(bool.toString());
        } catch (Exception i2) {
            Log.i("test", i2.toString());
        }
        return bool.toString();
    }

    public String isToString(InputStream is) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(is, "UTF-8");

        int rsz = in.read(buffer, 0, buffer.length);

        out.append(buffer, 0, rsz);

        return out.toString();
    }

    static String Hport = "/dev/ttyS1";

    int motorno;
    int row;

    public void startmachine(int motorno,int row) {
        this.motorno = motorno;
        this.row = row;

        String command = "";
        command = generatecommand(motorno, row);
        sendSerialPort(command);

        try {
            thisreadthread = new ReadThread();
            thisreadthread.start();
        } catch (Exception e) {
            e.printStackTrace();
            closeSerialPort();
        }
    }

    public void closeSerialPort() {
        try {
            mMySerialLib = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendSerialPort(String str) {
        try {
            mMySerialLib.getOutputStream().write(str.getBytes());
            mMySerialLib.getOutputStream().flush();
        } catch (Exception e) {
            Log.i("test", e.toString());
        }
    }


}