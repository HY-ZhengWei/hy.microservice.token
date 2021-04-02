package org.hy.microservice.token.yonyou.sso;

import org.hy.common.xml.SerializableDef;





/**
 * 集成认证中心获取登录临时code的请求对象
 *
 * @author      ZhengWei(HY)
 * @createDate  2020-12-23
 * @version     v1.0
 */
public class LoginCodeRequest extends SerializableDef
{

    private static final long serialVersionUID = -6999560564085315445L;
    
    /** 第三方系统在友户通注册的唯一标识，从友户通集成认证中心页面获取 */
    private String thirdUcId;
    
    /** 用户在第三方用户中心的唯一标识 */
    private String userId;
    
    /** 用户在第三方系统的手机号 */
    private String mobile;
    
    /** 用户在第三方系统的邮箱 */
    private String email;
    
    /** 用户在第三方系统的用户名 */
    private String userName;
    
    /** 用户在第三方系统的编码 */
    private String userCode;

    
    
    /**
     * 获取：第三方系统在友户通注册的唯一标识，从友户通集成认证中心页面获取
     */
    public String getThirdUcId()
    {
        return thirdUcId;
    }

    
    /**
     * 获取：用户在第三方用户中心的唯一标识
     */
    public String getUserId()
    {
        return userId;
    }

    
    /**
     * 获取：用户在第三方系统的手机号
     */
    public String getMobile()
    {
        return mobile;
    }

    
    /**
     * 获取：用户在第三方系统的邮箱
     */
    public String getEmail()
    {
        return email;
    }

    
    /**
     * 获取：用户在第三方系统的用户名
     */
    public String getUserName()
    {
        return userName;
    }

    
    /**
     * 获取：用户在第三方系统的编码
     */
    public String getUserCode()
    {
        return userCode;
    }

    
    /**
     * 设置：第三方系统在友户通注册的唯一标识，从友户通集成认证中心页面获取
     * 
     * @param thirdUcId 
     */
    public void setThirdUcId(String thirdUcId)
    {
        this.thirdUcId = thirdUcId;
    }

    
    /**
     * 设置：用户在第三方用户中心的唯一标识
     * 
     * @param userId 
     */
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    
    /**
     * 设置：用户在第三方系统的手机号
     * 
     * @param mobile 
     */
    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    
    /**
     * 设置：用户在第三方系统的邮箱
     * 
     * @param email 
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    
    /**
     * 设置：用户在第三方系统的用户名
     * 
     * @param userName 
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    
    /**
     * 设置：用户在第三方系统的编码
     * 
     * @param userCode 
     */
    public void setUserCode(String userCode)
    {
        this.userCode = userCode;
    }
    
}
