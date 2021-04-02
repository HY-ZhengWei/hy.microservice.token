package org.hy.microservice.token.yonyou.sso;

import org.hy.common.xml.SerializableDef;





/**
 * 集成认证中心获取登录临时code的响应数据
 *
 * @author      ZhengWei(HY)
 * @createDate  2020-12-23
 * @version     v1.0
 */
public class LoginCodeResponseData extends SerializableDef
{

    private static final long serialVersionUID = 2433487617090337488L;
    
    /** 当前环境 */
    private String curYhtEnvironment;
    
    /** 状态 */
    private String status;
    
    /** 用户登录临时code */
    private String code;
    
    /** 消息 */
    private String msg;

    
    
    /**
     * 获取：当前环境
     */
    public String getCurYhtEnvironment()
    {
        return curYhtEnvironment;
    }

    
    /**
     * 获取：状态
     */
    public String getStatus()
    {
        return status;
    }

    
    /**
     * 获取：用户登录临时code
     */
    public String getCode()
    {
        return code;
    }

    
    /**
     * 设置：当前环境
     * 
     * @param curYhtEnvironment 
     */
    public void setCurYhtEnvironment(String curYhtEnvironment)
    {
        this.curYhtEnvironment = curYhtEnvironment;
    }

    
    /**
     * 设置：状态
     * 
     * @param status 
     */
    public void setStatus(String status)
    {
        this.status = status;
    }

    
    /**
     * 设置：用户登录临时code
     * 
     * @param code 
     */
    public void setCode(String code)
    {
        this.code = code;
    }

    
    /**
     * 获取：消息
     */
    public String getMsg()
    {
        return msg;
    }

    
    /**
     * 设置：消息
     * 
     * @param msg 
     */
    public void setMsg(String msg)
    {
        this.msg = msg;
    }
    
}
