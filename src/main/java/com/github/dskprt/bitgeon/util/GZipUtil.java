package com.github.dskprt.bitgeon.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class GZipUtil {

    public static byte[] decompress(byte[] bytes) {
        byte[] buffer = new byte[1024];

        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
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
