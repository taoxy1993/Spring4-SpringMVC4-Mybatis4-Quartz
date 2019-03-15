package com.dfs.controller.common;


import com.dfs.annotation.IgnoreSecurity;
import com.dfs.model.ApiCodeEnum;
import com.dfs.utils.FileUploadUtil;
import com.dfs.utils.ResourceUtil;
import com.dfs.utils.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author taoxy 2019/1/3
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private static final Logger logger = Logger.getLogger(FileController.class);

    /**
     * 表单需要enctype="multipart/form-data",名字为image
     */
    @RequestMapping(value = "/uploadAdvertImg", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @IgnoreSecurity
    public String uploadAdvertImg(@RequestParam(required = false, value = "image") MultipartFile image) {
        logger.info("开始上传广告图片:{}" + image);
        String path = "";
        if (image != null) {
            try {
                path = FileUploadUtil.uploadImage(image, ResourceUtil.advertImageDir());
            } catch (IOException e) {
                logger.error("上传图片失败:", e);
            }
        }
        Map<String, String> content = new HashMap<String, String>();
        // 上传失败
        if (StringUtils.isBlank(path)) {
            return ResponseUtil.getResponse(content, ApiCodeEnum.FAIL);
        }
        // 上传成功
        content.put("path", path);
        return ResponseUtil.getResponse(content, ApiCodeEnum.SUCCESS);
    }
}
