package com.aplace.community.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author apeace
 * @version 2017/6/11.
 */
public class ToolController {

    /**
     * 加密工具页面
     */
    @ResponseBody
    @RequestMapping(value = "strEncode")
    public String toStrEncodePage(){
        return "haven't this page";
    }

    /**
     * 照片解码页面
     */
    @ResponseBody
    @RequestMapping(value = "photoEncode")
    public String toPhotoEncodePage(){
        return "haven't this page";
    }

    /**
     * 模拟发送请求页面
     */
    @ResponseBody
    @RequestMapping(value = "httpMock")
    public String toHttpMockPage(){
        return "haven't this page";
    }
}
