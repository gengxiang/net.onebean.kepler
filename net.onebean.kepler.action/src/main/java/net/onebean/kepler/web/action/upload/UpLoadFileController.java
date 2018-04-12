package net.onebean.kepler.web.action.upload;

import net.onebean.component.aliyun.AliyunOssUtil;
import net.onebean.component.aliyun.CkEditerUploadCallBackVo;
import net.onebean.component.aliyun.UploadCallBackVo;
import net.onebean.component.aliyun.image.ImageUtil;
import net.onebean.util.PropUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


/**
 * CkEditer上传文件到oss Controller
 * @author 0neBean
 */
@RequestMapping("upload")
@Controller
public class UpLoadFileController {
    @RequestMapping("filebrowserurl")
    @ResponseBody
    public CkEditerUploadCallBackVo filebrowserBrowseUrl(@RequestParam("upload") MultipartFile file, HttpServletRequest request, CkEditerUploadCallBackVo vo) throws Exception{
        String contentType = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")+1,file.getOriginalFilename().length());
        String imagePath = ImageUtil.imageName()+"."+contentType;
        contentType = AliyunOssUtil.contentType_map.get(contentType);
        String localPath =  request.getServletContext().getRealPath("/upload/");
        String allPath = localPath+imagePath;
        String ossPath = allPath.substring(allPath.indexOf("\\upload\\")+1,allPath.length()).replace("\\", "/");
        ImageUtil.savePicToDisk(file.getInputStream(),localPath,imagePath);
        String bucketName =  PropUtil.getConfig("aliyun.oss.bucketName");
        String ossHost =  PropUtil.getConfig("aliyun.oss.host");
        AliyunOssUtil.uploadFile(bucketName,ossPath,allPath,contentType);
        vo.setUploaded(1);
        vo.setFileName(imagePath);
        vo.setUrl(ossHost+ossPath);
        return vo;
    }

    /**
     * 上传文件控件上传接口
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("uploadmultipartfile")
    @ResponseBody
    public UploadCallBackVo uploadMultipartFile(MultipartFile file, HttpServletRequest request, UploadCallBackVo vo) throws Exception{
        Map<String,Object> data = new HashMap<>();
        try {
            String ossHost = PropUtil.getConfig("aliyun.oss.host");
            String contentType = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")+1,file.getOriginalFilename().length());
            String imagePath = ImageUtil.imageName()+"."+contentType;
            contentType = AliyunOssUtil.contentType_map.get(contentType);
            String localPath =  request.getServletContext().getRealPath("/upload/");
            String allPath = localPath+imagePath;
            String ossPath = allPath.substring(allPath.indexOf("\\upload\\")+1,allPath.length()).replace("\\", "/");
            ImageUtil.savePicToDisk(file.getInputStream(),localPath,imagePath);
            String bucketName =  PropUtil.getConfig("aliyun.oss.bucketName");
            AliyunOssUtil.uploadFile(bucketName,ossPath,allPath,contentType);
            data.put("id",ossHost+ossPath);
            data.put("name",imagePath);
        } catch (Exception e) {
            e.printStackTrace();
            vo.setStatus(false);
            vo.setMessage("上传失败!");
        }
        vo.setStatus(true);
        vo.setMessage("上传成功!");
        vo.setData(data);
        return vo;
    }

    /**
     * 上传文件空间上传接口
     * @param store
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("downfile")
    public ResponseEntity<byte[]> uploadMultipartFile(@RequestParam("store")String store, HttpServletRequest request) throws Exception{
        String filename = store.substring(store.lastIndexOf("/"),store.length()).replace("/","");
        String bucketName = PropUtil.getConfig("aliyun.oss.bucketName");
        String ossHost = PropUtil.getConfig("aliyun.oss.host");
        String key = store.replace(ossHost,"");
        String localPath =  request.getServletContext().getRealPath("/dowmload/");
        String filePath = localPath+key;
        filePath = filePath.replace("/","\\");
        filePath = filePath.substring(0,filePath.lastIndexOf("\\"));
        filePath+="\\";
        File path = new File(filePath);
        File file = new File(filePath+filename);
        path.mkdirs();
        file.createNewFile();
        AliyunOssUtil.downloadFile(bucketName,key,filePath+filename);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", filename);
        return new ResponseEntity<>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);
    }


}
