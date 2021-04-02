package org.hy.microservice.token.yonyou.common;

import org.hy.common.xml.SerializableDef;





/**
 * 响应的基础类
 *
 * @author      ZhengWei(HY)
 * @createDate  2021-01-08
 * @version     v1.0
 */
public class BaseResponse extends SerializableDef
{
    
    private static final long serialVersionUID = -8937744203898397851L;

    /** 响应代码 */
    private String code;
    
    /** 响应消息 */
    private String message;

    
    
    /**
     * 获取：响应代码
     */
    public String getCode()
    {
        return code;
    }

    
    /**
     * 获取：响应消息
     */
    public String getMessage()
    {
        return message;
    }

    
    /**
     * 设置：响应代码
     * 
     * @param code 
     */
    public void setCode(String code)
    {
        this.code = code;
    }

    
    /**
     * 设置：响应消息
     * 
     * @param message 
     */
    public void setMessage(String message)
    {
        this.message = message;
    }
    
}
