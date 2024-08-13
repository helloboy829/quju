package cn.codefit.base.qiniu.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 七牛云工具类
 * 官方文档地址: https://developer.qiniu.com/kodo/1239/java#1
 */
@Component
@Slf4j
public class QiNiuUtils {

    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;
    @Value("${qiniu.bucket}")
    private String bucket;

    private final String urlPrefix = "https://images.hzsyoudong.com//";

    /**
     * 获取上传token
     *
     * @return
     */
    public String getQiNiuToken() {
        Auth auth = Auth.create(accessKey, secretKey);
        //  token 24小时有效
        String upToken = auth.uploadToken(bucket, null, 86400L, null);
        return upToken;
    }

    /**
     * 服务器直传
     * 上传文件到七牛云
     *
     * @param localFilePath 文件全路径
     * @return
     */
    public String uploadFile(String localFilePath, String fileName) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
//        String key = UUID.randomUUID().toString()+ ".xlsx";
        String key = fileName + ".xlsx";
        String upToken = getQiNiuToken();
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info(putRet.key);
            log.info(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error(r.toString());
            try {
                log.error(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return urlPrefix + key;
    }

}
