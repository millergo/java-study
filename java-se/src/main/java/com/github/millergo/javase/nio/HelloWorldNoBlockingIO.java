package com.github.millergo.javase.nio;


import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 NIO(New IO)特点是面向缓冲区，数据的传输都是通过缓冲区在通道内来进行传输的。且NIO是非阻塞式的。
 * 如何使用NIO：
 * 1. NIO针对每种不同的数据类型都提供了对应的缓冲区。
 * 2. 获取缓冲区直接调用 allocate()
 * 3. 存数据，put()
 * 4. 取数据，get()
 **/
public class HelloWorldNoBlockingIO {

    @Test
    public void test1() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        System.out.println("-------------初始值");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        System.out.println("-------------put()");
        String str = "abcde";
        // 写数据模式。将数据写入缓存区
        byteBuffer.put(str.getBytes());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());


        System.out.println("-------------get()");
//        切换到读模式。切换到读模式之后position位置又会从0开始读取数据。同时limit位置会改变成你上次写入缓冲区数据的长度5。
//        如果不切换的话那么position位置就还是指向你上一次存入数据的位置的索引。比如这里是abcde那position=5，limit不变。
        byteBuffer.flip();
        ByteBuffer byteBuffer1 = byteBuffer.get(str.getBytes(), byteBuffer.position(), byteBuffer.limit());
        System.out.println(new String(byteBuffer1.array(), byteBuffer.position(), byteBuffer.limit()));
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
    }

    @Test
    public void testReadFile() {
        String filePath = "pom.xml";
        // 通道
        FileChannel fileChannel = null;
        // 缓冲区
        ByteBuffer byteBuffer = null;
        try {
            // 建立通道,设置通过为只能读数据
            fileChannel = FileChannel.open(Paths.get(filePath), StandardOpenOption.READ);
            // 分配缓冲区大小
            byteBuffer = ByteBuffer.allocate(1024);
            StringBuilder stringBuilder = new StringBuilder();
            // 将通道中的数据存入缓冲区中
            while (fileChannel.read(byteBuffer) != -1) {
                // 切换模式-读模式
                byteBuffer.flip();
                stringBuilder.append(new String(byteBuffer.array(), 0, byteBuffer.limit()));
                byteBuffer.clear();
            }

            System.out.println(stringBuilder.toString());

        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                fileChannel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @Test
    public void testCopyFileByMemoryMap() {
        // 使用直接缓冲区完成文件的复制(内存映射文件)
        String sourceFilePath = "pom.xml";
        String destinationFilePath = "pom2.xml";
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            // 建立通道
            inputChannel = FileChannel.open(Paths.get(sourceFilePath), StandardOpenOption.READ);
            outputChannel = FileChannel.open(Paths.get(destinationFilePath), StandardOpenOption.READ,
                    StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            // JVM内存与物理内存的映射文件
            MappedByteBuffer inputMapMode = inputChannel.map(FileChannel.MapMode.READ_ONLY, 0, inputChannel.size());
            MappedByteBuffer outputMapMode = outputChannel.map(FileChannel.MapMode.READ_WRITE, 0, inputChannel.size());
            // 直接对缓冲区进行读写操作
            byte[] destination = new byte[inputMapMode.limit()];
            inputChannel.read(ByteBuffer.wrap(destination));
            outputMapMode.put(destination);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            if (null != inputChannel) {
                try {
                    inputChannel.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            if (null != null) {
                try {
                    outputChannel.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }


    @Test
    public void testCopyFileByChannel() {
        //利用通道完成文件的复制（非直接缓冲区）
        String sourceFilePath = "pom.xml";
        String destinationFilePath = "pom2.xml";
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        //①获取通道
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            fileInputStream = new FileInputStream(sourceFilePath);
            fileOutputStream = new FileOutputStream(destinationFilePath);

            inputChannel = fileInputStream.getChannel();
            outputChannel = fileOutputStream.getChannel();

            //②分配指定大小的缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);

            //③将通道中的数据存入缓冲区中
            while (inputChannel.read(buf) != -1) {
                //切换读取数据的模式
                buf.flip();
                //④将缓冲区中的数据写入通道中
                outputChannel.write(buf);
                //清空缓冲区
                buf.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputChannel != null) {
                try {
                    outputChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inputChannel != null) {
                try {
                    inputChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
