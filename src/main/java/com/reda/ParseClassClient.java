package com.reda;


import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import static com.reda.util.ParseClassUtil.*;

public class ParseClassClient {
    public static void main(String[] args) {

        try (FileInputStream fis = new FileInputStream("src/main/resources/AuditorInterceptor.class")) {
            FileChannel fc = fis.getChannel();
            //读魔数
            readMagicNumber(fc);
            //读版本
            readSubVersion(fc);
            readMainVersion(fc);
            //读常量池
            readConstantPool(fc);
            //读access flag
            readAccessFlag(fc);
            //读 类
            readClass(fc);
            //读 父类
            readParentClass(fc);
            //读 接口
            readInterfaces(fc);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readMagicNumber(FileChannel fc) {
        System.out.println("Magic Number: " + readUxToString(fc,4));
    }

    public static void readSubVersion(FileChannel fc) {
        System.out.println("Sub Version: " + readU2ToInt(fc));
    }

    public static void readMainVersion(FileChannel fc) {
        System.out.println("Major Version: " + readU2ToInt(fc));
    }

    public static void readAccessFlag(FileChannel fc) {
        int u2 = readU2ToInt(fc);
        int i = u2 / 16384;
        System.out.print("Access Flag: ");
        if(i == 1) {
            System.out.print("ACC_ENUM ");
            u2 = u2 % 16384;
        }
        i = u2 / 8192;
        if(i == 1) {
            System.out.print("ACC_ANNOTATION ");
            u2 = u2 % 8192;
        }
        i = u2 / 4096;
        if(i == 1) {
            System.out.print("ACC_SYNTHETIC ");
            u2 = u2 % 4096;
        }
        i = u2 / 1024;
        if(i == 1) {
            System.out.print("ACC_ABSTRACT ");
            u2 = u2 % 1024;
        }
        i = u2 / 512;
        if(i == 1) {
            System.out.print("ACC_INTERFACE ");
            u2 = u2 % 512;
        }
        i = u2 / 32;
        if(i == 1) {
            System.out.print("ACC_SUPER ");
            u2 = u2 % 32;
        }
        i = u2 / 16;
        if(i == 1) {
            System.out.print("ACC_FINAL ");
            u2 = u2 % 16;
        }
        if(u2 == 1) {
            System.out.println("ACC_PUBLIC ");
        }
    }

    public static void readClass(FileChannel fc) {
        int u2 = readU2ToInt(fc);
        System.out.println("Current Class = #" + u2);
    }

    public static void readParentClass(FileChannel fc) {
        int u2 = readU2ToInt(fc);
        System.out.println("Parent Class = #" + u2);
    }

    public static void readInterfaces(FileChannel fc) {
        int u2 = readU2ToInt(fc);
        System.out.println("Interfaces count: " + u2);
        for (int i = 1; i <= u2; i++) {
            System.out.println("interface " + i + " #" + readU2ToInt(fc));
        }
    }

    public static void readConstantPool(FileChannel fc) {
        int constantNum = readU2ToInt(fc) -1;
        System.out.println("Constant Num: " + constantNum);
        for (int i = 1; i <= constantNum; i++) {
            System.out.print("#"+ i);
            int tag = readU1ToInt(fc);
            parseConstantPool(fc, tag);
        }
    }

    public static void parseConstantPool(FileChannel fc, int tag) {
        switch (tag) {
            case 1:
                System.out.println(" = Utf8  " + parseUtf8Info(fc));
                break;
            case 3:
                System.out.println(" = Integer  " + parseIntegerInfo(fc));
                break;
            case 4:
                System.out.println(" = Float  " + parseFloatInfo(fc));
                break;
            case 5:
                System.out.println(" = Long  " + parseLongInfo(fc));
                break;
            case 6:
                System.out.println(" = Double  " + parseDoubleInfo(fc));
                break;
            case 7:
                System.out.println(" = Class  #" + parseClassInfo(fc));
                break;
            case 8:
                System.out.println(" = String  #" + parseStringInfo(fc));
                break;
            case 9:
                System.out.println(" = FieldRef  #" + parseFieldRefInfo(fc));
                break;
            case 10:
                System.out.println(" = MethodRef  #" + parseMethodRefInfo(fc));
                break;
            case 11:
                System.out.println(" = InterfaceMethodRef  #" + parseInterfaceMethodRefInfo(fc));
                break;
            case 12:
                System.out.println(" = NameAndType  #" + parseNameAndTypeInfo(fc));
                break;
            case 15:
                System.out.println(" = MethodHandle  #" + parseMethodHandleInfo(fc));
                break;
            case 16:
                System.out.println(" = MethodType  #" + parseMethodTypeInfo(fc));
                break;
            case 18:
                System.out.println(" = InvokeDynamic  #" + parseInvokeDynamicInfo(fc));
                break;
        }

    }

}
