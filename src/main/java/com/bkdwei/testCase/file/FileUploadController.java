/**
 *
 */
package com.bkdwei.testCase.file;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bkdwei.core.thread.SingleFileUploadThread;

/**
 * 文件上传模块
 *
 * @author bkd
 * @Date 2016年8月5日
 *
 */
@Controller
@RequestMapping("/file")
//TODO 尚未对文件大小及异常进行处理来
public class FileUploadController {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    public static String        UPLOAD_DIR;

    public static final String getUploadDir(final HttpServletRequest request) {
        if (UPLOAD_DIR != null) {
            return UPLOAD_DIR;
        }

        UPLOAD_DIR = request.getSession().getServletContext().getRealPath("/") + "upload/";
        final File tmpDir = new File(UPLOAD_DIR);
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }
        return UPLOAD_DIR;
    }

    //单个文件其实也可以通过批量上传来执行，所以可以禁用单文件上传的方法了。
    @RequestMapping(
            value = "/batchUpload", method = RequestMethod.POST)
    public String doBatchUpload(final HttpServletRequest request) throws InterruptedException {
        UPLOAD_DIR = getUploadDir(request);
        final List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        final int threadCount = files.size();
        final ExecutorService exe = Executors.newFixedThreadPool(threadCount);
        //此处使用concurrency包中的CountDownLanch进行监控任务的执行进度，
        //TODO 或许应该设置上传超时？
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; ++i) {
            exe.execute(new SingleFileUploadThread(files.get(i), UPLOAD_DIR, countDownLatch));
        }
        countDownLatch.await();
        logger.debug("文件上传结束。已上传" + threadCount + "个文件。");
        return "/file/uploadFile";
    }

    @Deprecated
    @RequestMapping(
            value = "/upload", method = RequestMethod.POST)
    public String doUpload(@RequestParam("file") final MultipartFile file,
            final HttpServletRequest request) {
        UPLOAD_DIR = getUploadDir(request);
        new SingleFileUploadThread(file, UPLOAD_DIR);
        logger.debug("文件上传结束。已上传1个文件。文件名为：" + file.getOriginalFilename());
        return "/file/uploadFile";
    }
}
