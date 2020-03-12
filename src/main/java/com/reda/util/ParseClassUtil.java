package com.reda.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.LongBuffer;
import java.nio.channels.FileChannel;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParseClassUtil {

    public static String parseUtf8Info(FileChannel fc) {
        int length = readU2ToInt(fc);
        return readUtf8String(fc,length);
    }

    public static int parseIntegerInfo(FileChannel fc) {
        return readU4ToInt(fc);
    }

    public static float parseFloatInfo(FileChannel fc) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        try {
            fc.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer.flip();
        FloatBuffer floatBuffer = buffer.asFloatBuffer();
        return floatBuffer.get();
    }

    public static Long parseLongInfo(FileChannel fc) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        try {
            fc.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer.flip();
        LongBuffer floatBuffer = buffer.asLongBuffer();
        return floatBuffer.get();
    }

    public static double parseDoubleInfo(FileChannel fc) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        try {
            fc.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer.flip();
        DoubleBuffer floatBuffer = buffer.asDoubleBuffer();
        return floatBuffer.get();
    }

    public static int parseClassInfo(FileChannel fc) {
        return readU2ToInt(fc);
    }

    public static int parseStringInfo(FileChannel fc) {
        return readU2ToInt(fc);
    }

    // index:0 指向Class_info索引
    // index:1 指向字段描述符NameAndType索引
    public static String parseFieldRefInfo(FileChannel fc) {
        return Stream.of(readU2ToInt(fc),readU2ToInt(fc)).map(String::valueOf).collect(Collectors.joining(" #"));
    }

    // index:0 指向Class_info索引
    // index:1 指向字段描述符NameAndType索引
    public static String parseMethodRefInfo(FileChannel fc) {
        return Stream.of(readU2ToInt(fc),readU2ToInt(fc)).map(String::valueOf).collect(Collectors.joining(" #"));
    }

    // index:0 指向Class_info索引
    // index:1 指向字段描述符NameAndType索引
    public static String parseInterfaceMethodRefInfo(FileChannel fc) {
        return Stream.of(readU2ToInt(fc),readU2ToInt(fc)).map(String::valueOf).collect(Collectors.joining(" #"));
    }

    // index:0 指向字段或方法名称常量值索引
    // index:1 指向字段或方法描述符常量值索引
    public static String parseNameAndTypeInfo(FileChannel fc) {
        return Stream.of(readU2ToInt(fc),readU2ToInt(fc)).map(String::valueOf).collect(Collectors.joining(" #"));
    }

    // index:0 指向方法句柄类型（1-9之间）
    // index:1 指向常量值索引
    public static String parseMethodHandleInfo(FileChannel fc) {
        return Stream.of(readU1ToInt(fc),readU2ToInt(fc)).map(String::valueOf).collect(Collectors.joining(" #"));
    }

    // 指向常量值索引,必须是utf8Info
    public static int parseMethodTypeInfo(FileChannel fc) {
        return readU2ToInt(fc);
    }

    // index:0 指向当前class文件中引导方法表的bootstrap_methods数组的索引
    // index:1 指向常量值索引，必须是NameAndTypeInfo
    public static String parseInvokeDynamicInfo(FileChannel fc) {
        return Stream.of(readU2ToInt(fc),readU2ToInt(fc)).map(String::valueOf).collect(Collectors.joining(" #"));
    }

    public static String readUxToString(FileChannel fc, int n) {
        ByteBuffer buffer = ByteBuffer.allocate(n);
        try {
            fc.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer.flip();
        StringBuilder builder = new StringBuilder();
        if (buffer.hasArray()) {
            for (byte b : buffer.array()) {
                builder.append(Integer.toHexString(Byte.toUnsignedInt(b)));
            }
        }
        return builder.toString();
    }

    public static String readUtf8String(FileChannel fc, int n) {
        ByteBuffer buffer = ByteBuffer.allocate(n);
        try {
            fc.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer.flip();
        byte[] bytes = new byte[buffer.capacity()];
        buffer.get(bytes);
        return new String(bytes);
    }


    public static int readU1ToInt(FileChannel fc) {
        return readUxToInt(fc,1);
    }

    public static int readU2ToInt(FileChannel fc) {
        return readUxToInt(fc,2);

    }

    public static int readU4ToInt(FileChannel fc) {
        return readUxToInt(fc,4);
    }

    private static int readUxToInt(FileChannel fc, int n) {
        ByteBuffer buffer = ByteBuffer.allocate(n);
        try {
            fc.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer.flip();
        String result = "";
        while (buffer.hasRemaining()) {
            byte tmp = buffer.get();
            String hex = Integer.toHexString(tmp & 0xFF);
            if (hex.length() < 2) {
                hex = "0" + hex;
            }
           result = result + hex;
        }
        return Integer.parseInt(result,16);

    }
}
