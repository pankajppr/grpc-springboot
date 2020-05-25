package com.jucase.grpc.lib.util;
import com.jucase.grpc.lib.dto.BaseDto;

import java.io.*;

public class GrpcDtoUtil {

    public static byte[] getBytes(BaseDto baseDto) throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(baseDto);
        oos.flush();
        return bos.toByteArray();
    }


    public static <T> T deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return (T)is.readObject();
    }
}
