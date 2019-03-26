package com.melah;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.util.Logger;
import org.jpos.util.SimpleLogListener;

import java.io.InputStream;

public class UnpackISOMessage {

    public ISOMsg parseISOMessage(byte[] message) throws Exception {
        Logger logger = new Logger();
        logger.addListener (new SimpleLogListener());

        try {
            InputStream is = getClass().getResourceAsStream("/base1.xml");
            GenericPackager packager = new GenericPackager(is);
            packager.setLogger(logger, "debug");
            ISOMsg isoMsg = new ISOMsg();
            isoMsg.setPackager(packager);
            isoMsg.unpack(message);
            printISOMessage(isoMsg);
            return isoMsg;
        } catch (ISOException e) {
            throw new Exception(e);
        }
    }

    public void printISOMessage(ISOMsg isoMsg) {
        try {
            isoMsg.dump(System.out, "");
            System.out.println(ISOUtil.hexdump(isoMsg.pack()));

            System.out.printf("MTI = %s%n", isoMsg.getMTI());
            System.out.println("127.003 : " + isoMsg.getString("127.003"));
            System.out.println("127.033 : " + isoMsg.getString("127.33"));
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
