package com.wyf.seckill.exception;

import com.wyf.seckill.result.CodeMsg;

/**
 * @author: wyf
 * @date:2022/1/11 14:48
 */
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private CodeMsg codeMsg;
    public GlobalException(CodeMsg codeMsg){
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
