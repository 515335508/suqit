package com.bytedance.app;


import com.bytedance.pojo.Bank;
import com.bytedance.pojo.ViewUtil;
import com.bytedance.service.testint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class cona {


    @Resource
    private testint suqiimpl;
    /**
     *
     * @return 测试
     */
    @ResponseBody
    @RequestMapping("/hello")
    public List<Bank> getA(HttpServletResponse response,HttpServletRequest request) throws Exception {
        System.out.println("aaa");
        List<Bank> getbank = suqiimpl.getbank();
        //ViewUtil.excelTool(response,getbank,15,"银行测试","realname");
        String realPath = request.getRealPath("/");
        System.out.println(realPath+"WEB-INF\\A");


        return getbank;
    }
    @RequestMapping("{uri}")
    public String getJsp(@PathVariable("uri")String uri) {
        return uri;
    }
}
