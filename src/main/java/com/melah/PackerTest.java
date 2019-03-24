package com.melah;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOField;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.util.Logger;
import org.jpos.util.SimpleLogListener;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PackerTest {

        public static void main(String[] args) throws ISOException {
            Logger logger = new Logger();
            logger.addListener (new SimpleLogListener());

            // Load package from resources directory.
            InputStream is = com.melah.PackerTest.class.getResourceAsStream("/base1.xml");
            GenericPackager packager = new GenericPackager(is);
            packager.setLogger(logger, "debug");

            // Setting packager
            ISOMsg isoMsg = new ISOMsg();
            isoMsg.setPackager(packager);

            // Setting MTI
            isoMsg.set(0, "0200");
            isoMsg.set(2, "5800000000000000");
            isoMsg.set(3, "317000");
            isoMsg.set(4, "000000000000");
            isoMsg.set(7, new SimpleDateFormat("MMddHHmmss").format(1221133630));
            isoMsg.set(11, "594972");
            isoMsg.set(12, "153630");
            isoMsg.set(13, "1221");
            isoMsg.set(14, "1912");
            isoMsg.set(15, "1221");
            isoMsg.set(18, "6012");
            isoMsg.set(22, "020");
            isoMsg.set(25, "27");
            isoMsg.set(28, "C00000000");
            isoMsg.set(30, "C00000000");
            isoMsg.set(32, "500000");
            isoMsg.set(37, "000500000000");
            isoMsg.set(41, "11431580");
            isoMsg.set(42, "000000011400000");
            isoMsg.set(43, "XYZ Mobile Banking");
            isoMsg.set(49, "840");
            isoMsg.set(56, "1510");
            isoMsg.set(59, "0541953424");
            isoMsg.set(102, "5000000000000000");
            isoMsg.set(123, "100000000000000");
            isoMsg.set(54, "500000000");
            isoMsg.set("127.003", "XYZ Mobile");
            isoMsg.set("127.33", "6487");



            byte[] bIsoMsg = isoMsg.pack();

            String isoMessage = "";
            for (int i = 0; i < bIsoMsg.length; i++) {
                String a = "";
                isoMessage += (char) bIsoMsg[i];
            }
            System.out.println(" Packed ISO8385 Message = '"+isoMessage+"'");
        }

    }
