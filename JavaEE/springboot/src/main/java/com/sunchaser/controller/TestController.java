package com.sunchaser.controller;

import com.sunchaser.model.response.HttpResponseCodeMsg;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;

/**
 * @author: sunchaser
 * @date: 2019/9/19
 * @description:
 */
@RestController
@Validated
public class TestController {

    @GetMapping("/test/pattern")
    public HttpResponseCodeMsg testPattern(@Pattern(regexp = "040206|040207",message = "正则不匹配")
                                               @RequestParam String test) {
        return HttpResponseCodeMsg.SUCCESS;
    }
}
