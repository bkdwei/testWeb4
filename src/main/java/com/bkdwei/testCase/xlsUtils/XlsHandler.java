/**
 *
 */
package com.bkdwei.testCase.xlsUtils;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bkdwei.core.thread.SingleFileUploadThread;
import com.bkdwei.testCase.file.FileUploadController;

/**
 * @author bkd
 * @Date 2016年8月5日
 */
@Controller
@RequestMapping("/xls")
public class XlsHandler {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    @Autowired
    private XlsImport           xlsImport;

    @RequestMapping(
            value = "/upload", method = RequestMethod.POST)
    public String doUpload(@RequestParam("file") final MultipartFile file,
            final HttpServletRequest request) throws InterruptedException {
        new SingleFileUploadThread(file, FileUploadController.getUploadDir(request)).run();
        try {
            xlsImport.readXls(new File(
                    FileUploadController.getUploadDir(request) + file.getOriginalFilename()));
        } catch (final IOException e) {
            logger.error("导入xls文件失败。" + file.getOriginalFilename());
        }
        return "/file/uploadFile";
    }
}
