package com.ymdx.rabbitmq.exception;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2021/2/4
 * @Description
 */
public class BusinessException extends RuntimeException {

    public BusinessException(){ }
    public BusinessException(String message){
        super(message);
    }
}
