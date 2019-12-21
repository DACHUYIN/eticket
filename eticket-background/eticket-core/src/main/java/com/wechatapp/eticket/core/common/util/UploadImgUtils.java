package com.wechatapp.eticket.core.common.util;


import com.wechatapp.eticket.core.common.exception.FTPClientException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 电子券码图片上传工具
 *
 * @author virgo.zx
 * @date 2019/12/8 14:37
 */
@Slf4j
public class UploadImgUtils {

    private volatile static ThreadPoolExecutor threadPoolExecutor;

    private final FTPClient ftpClient = new FTPClient();

    private final Map<String, Boolean> dirCreateMap = new ConcurrentHashMap<>();

    private UploadImgUtils() {
    }

    static {
        threadPoolExecutor = new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors() * 2,
                60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(10),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = newThread(r);
                        t.setDaemon(true);
                        return t;
                    }
                }, new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    /**
     * 实例化Util时,通过newInstance
     * @return
     */
    private static Future<UploadImgUtils> newInstance() {
        Callable<UploadImgUtils> callable = new Callable<UploadImgUtils>() {
            @Override
            public UploadImgUtils call() throws Exception {
                return null;
            }
        };
        final FutureTask<UploadImgUtils> task = new FutureTask<UploadImgUtils>(callable);
        threadPoolExecutor.execute(task);
        return task;
    }

    /**
     * 初始化一个FTP链接
     */
    private void init(String ftpServer, String userName, String password) throws Exception {
        int reply;

        FTPClientConfig config = new FTPClientConfig();
        ftpClient.configure(config);
        ftpClient.connect(ftpServer);

        log.info("FTP服务器尝试连接之后，返回的信息为：" + ftpClient.getReplyString());

        reply = ftpClient.getReplyCode();

        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            throw new FTPClientException("FTP服务器拒绝连接");
        }
        boolean isOK = ftpClient.login(userName, password);
        if (isOK) {
            log.info("FTP服务器连接成功" + ftpClient.getReplyString());
        } else {
            throw new FTPClientException("FTP服务器登录失败" + ftpClient.getReplyString());
        }
        // 查找FTP服务器上面的远程目录
        reply = ftpClient.cwd("~/eticket-img");
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            throw new FTPClientException("远程目录访问失败，返回代码为:"
                    + reply);
        } else {
            log.info("远程目录访问成功，返回信息为：" + ftpClient.getReplyString());
        }

        ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
    }

    public void upload(MultipartFile file) throws Exception {

    }
}
