package com.melah;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.util.Logger;
import org.jpos.util.SimpleLogListener;

import java.io.InputStream;

public class UnpackISOMessage {
    public static void main(String[] args) {
        UnpackISOMessage iso = new UnpackISOMessage();
        try {
            ISOMsg isoMsg = iso.parseISOMessage();
            iso.printISOMessage(isoMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ISOMsg parseISOMessage() throws Exception {
        Logger logger = new Logger();
        logger.addListener (new SimpleLogListener());
         String message = "0200F23E449508E0852000000000040000221658000000000000003170000000000000000115051213594972153630122119121221601202027C00000000C000000000650000000050000000011431580000000011400000XYZ Mobile Banking                      84000950000000000415100100541953424165000000000000000015100000000000000000060    ﾀ   XYZ Mobile                                      6000";
        System.out.printf("Message = %s%n", message);
        try {
            InputStream is = getClass().getResourceAsStream("/fields.xml");
            GenericPackager packager = new GenericPackager(is);
            packager.setLogger(logger, "debug");
            ISOMsg isoMsg = new ISOMsg();
            isoMsg.setPackager(packager);
            isoMsg.unpack(message.getBytes());
            return isoMsg;
        } catch (ISOException e) {
            throw new Exception(e);
        }
    }

    private void printISOMessage(ISOMsg isoMsg) {
        try {
            isoMsg.dump(System.out, "");
            System.out.println(ISOUtil.hexdump(isoMsg.pack()));

            System.out.printf("MTI = %s%n", isoMsg.getMTI());
            System.out.println("127.003 : " + isoMsg.getString("127.003"));
            System.out.println("127.033 : " + isoMsg.getString("127.033"));
            System.out.println("52 : " + isoMsg.getString("52").getBytes());
            for (int i = 1; i <= isoMsg.getMaxField(); i++) {
                if (isoMsg.hasField(i)) {
                    System.out.printf("Field (%s) = %s%n", i, isoMsg.getString(i));
                }
            }
        } catch (ISOException e) {
            e.printStackTrace();
        }
    }
}
