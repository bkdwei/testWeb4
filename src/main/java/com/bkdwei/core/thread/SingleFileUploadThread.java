/**
 *
 */
package com.bkdwei.core.thread;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * 使用线程上传一个及多个文件
 * 
 * @author bkd
 * @Date 2016年8月5日
 */
public class SingleFileUploadThread implements Runnable {

    private static final Logger  logger = LoggerFactory.getLogger(SingleFileUploadThread.class);
    private final CountDownLatch countDownLatch;
    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    final MultipartFile          file;
    private final String         FILE_DIR;

    //单文件上传时使用
    public SingleFileUploadThread(final MultipartFile file, final String fileDir) {
        this.file = file;
        FILE_DIR = fileDir;
        countDownLatch = new CountDownLatch(1);
    }

    public SingleFileUploadThread(final MultipartFile file, final String fileDir,
            final CountDownLatch countDownLatch) {
        this.file = file;
        FILE_DIR = fileDir;
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        try {
            upload();
        } finally {
            countDownLatch.countDown();
        }

    }

    public void upload() {
        if (!file.isEmpty()) {
            try {
                logger.debug("开始上传文件：" + file.getOriginalFilename() + "文件大小为：" + file.getSize());
                final byte[] bytes = file.getBytes();
                final File myUpload = new File(FILE_DIR + file.getOriginalFilename());
                if (!myUpload.exists()) {
                    myUpload.createNewFile();
                }
                final BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(myUpload));
                stream.write(bytes);
                stream.close();
                logger.debug("文件：" + file.getOriginalFilename() + "上传结束。");
            } catch (final Exception e) {
                logger.debug("上传文件失败，异常原因： " + e.getMessage());
            }
        }
    }
}
