package com.ihrm.common.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p></p>
 *
 * @author xiaodongsun
 * @date 2019/01/10
 */
@RestController
public class ErrorController {

   @GetMapping("/autherror")
    public Result autherror(int code){
       return code == 1?new Result(ResultCode.UNAUTHENTICATED):new Result(ResultCode.UNAUTHORISE);
   }
}
