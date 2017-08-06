package com.aplace.community.web.controller;

import com.aplace.community.core.service.IdSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author apeace
 * @version 2017/5/28.
 */
@Controller()
public class TestController {

    @Autowired
    private IdSequenceService idSequenceService;

    @ResponseBody
    @RequestMapping(value = "gettest", method = RequestMethod.GET)
    public String testResponse() {
        return String.format("This is test, your input is : %s",idSequenceService.nextIdSequence("hehe"));
    }

    @ResponseBody
    @RequestMapping(value = "posttest", method = RequestMethod.POST)
    public String testRequest() {
        return String.format("This is test, your input is : %s",111);
    }


}
