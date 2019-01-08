package com.ihrm.common.exception;

import com.ihrm.common.entity.ResultCode;
import lombok.Getter;

/**
 * <p></p>
 *
 * @author xiaodongsun
 * @date 2019/01/07
 */
@Getter
public class CommonException extends RuntimeException  {

    private ResultCode resultCode;

    public CommonException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
