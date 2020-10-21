package com.httpclient.core.log;

import java.util.Base64;
import java.util.UUID;

public class UUIDShort {
    /**生成唯一ID*/
    public static String generate(){
        UUID uuid = UUID.randomUUID();
        return compressedUUID(uuid);
    }

    protected static String compressedUUID(UUID uuid){
        byte[] byuuid = new byte[16];
        long least = uuid.getLeastSignificantBits();
        long most = uuid.getMostSignificantBits();
        long2bytes(most, byuuid, 0);
        long2bytes(least, byuuid, 8);
        return Base64.getEncoder().encodeToString(byuuid);
    }

    protected static void long2bytes(long value, byte[] bytes, int offset){
        for(int i = 7; i > -1; i--){
            bytes[offset++] = (byte) ((value >> 8 * i)& 0xFF);
        }
    }
}
