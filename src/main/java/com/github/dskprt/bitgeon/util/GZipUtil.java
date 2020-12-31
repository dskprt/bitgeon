package com.github.dskprt.bitgeon.util;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class GZipUtil {

    public static byte[] decompress(byte[] bytes) {
        byte[] buffer = new byte[1024];

        try {
            ByteInputStream bis = new ByteInputStream(bytes, bytes.length);
            GZIPInputStream gis = new GZIPInputStream(bis);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int bytesRead;

            while((bytesRead = gis.read(buffer)) > 0) {
                baos.write(buffer, 0, bytesRead);
            }

            bis.close();
            gis.close();

            return baos.toByteArray();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
