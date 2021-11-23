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

//                        String command = "";
//                        command = generatecommand(boardname, thisport.getSerial_port(), thisport.itemqty);
//                        sendSerialPort(command);
//                        Thread.sleep(5000);

//
//                        Collections.sort(cartListModels);
//
//
//                        ArrayList<String> reqproductid = new ArrayList<>();
//                        ArrayList<String> resproductid = new ArrayList<>();
//                        ArrayList<String> reqorderid = new ArrayList<>();
//                        ArrayList<String> sttus = new ArrayList<>();
//
//                        for (CartListModel thisport : cartListModels) {
//
//                            String boardname = "mainboard";
//
//                            //host send to equipment
//                            if (thisport.getTemp() == 1) {
//                                boardname = "mainboard";
//
//                                quantity = thisport.itemqty;
//                                command = generatecommand(boardname, thisport.getSerial_port(), thisport.itemqty);
//                                System.out.println("Delivery Request parameter is = " + command);
//                                reqproductid.add(command.split(",")[3]);
//                                reqorderid.add(thisport.getSerial_port());
//                                sendSerialPort(command);
//                                Thread.sleep(5000);
//
//                            } else {
//                                boardname = "viceboard";
//                                quantity = thisport.itemqty;
//                                command = generatecommand(boardname, thisport.getSerial_port(), thisport.itemqty);
//                                reqproductid.add(command.split(",")[3]);
//                                reqorderid.add(thisport.getSerial_port());
//                                sendSerialPort(command);
//                                Thread.sleep(5000);
//                            }
//
//                            // Thread.sleep(3000);
//
//                        }
//                        String readresponse;
//                        readresponse = isToString(mMySerialLib.getInputStream());
//                        System.out.println("Delivery request response is = " + readresponse);

//
//                        String status = null;
//                        String orderId;
//                        String qty;
//
//
//                        String[] myarray = readresponse.split("\\[");
//
//                        for (String thisone : myarray) {
//
//                            thisone = thisone.replace("]", "");
//                            if (thisone.contains("board")) {
//                                status = thisone.split(",")[4];
//                                orderId = thisone.split(",")[3];
//                                if (reqproductid.contains(orderId)) {
//                                    resproductid.add(orderId);
//                                    sttus.add(status);
//
//                                    qty = quantity;// readresponse.split(",")[2];
//                                    command = generatedeliverycommand("request", status, qty, orderId);
//                                    System.out.println("Delivery confirmation request parameter is = " + command);
//                                    sendSerialPort(command);
//                                }
//
//                            }
//
//                        }
//                        Thread.sleep(3000);

                        //importantline
//                        for (int j = 0; j < resproductid.size(); j++) {
//                            System.out.println("product id " + reqorderid.get(resproductid.indexOf(resproductid.get(j))) + " is dispensed by status " + sttus.get(j));
//                            allstatus += reqorderid.get(resproductid.indexOf(resproductid.get(j))) + ":" + sttus.get(j) + ",";
//                        }
//
//                        System.out.println("product status" + allstatus);
//
//                        mcont.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                // Update Tansaction here
//                                // mcont.updatetransactiondb(finalStatus);
//                            }
//                        });


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


//        char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
//        StringBuilder sb = new StringBuilder(20);
//        Random random = new Random();
//        for (int i = 0; i < 8; i++) {
//            char c = chars[random.nextInt(chars.length)];
//            sb.append(c);
//        }
//        String output = sb.toString();
//        String orderid = output;
//
//        String fpart = "," + orderid + "," + position + "," + qty;
//
//        String crccc = fpart;
//
//        crc17 crcclas = new crc17();
//        String finalCRC = ByteUtils.bytesToHexString(crcclas.computeCrcB(crccc.getBytes())).toLowerCase();
//
//        fpart = "," + finalCRC + fpart;
//        StringBuilder bool = new StringBuilder();
//        ;
//        try {
//            //bool.append("[viceboard,");
//            bool.append("[" + boardname + ",");
//            bool.append(fpart.length());
//            //oid,aile,qty
//
//            bool.append(fpart);
//            bool.append("]");
//            //  sendSerialPort(bool.toString());
//        } catch (Exception i2) {
//            Log.i("test", i2.toString());
//        }
//        return bool.toString();
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