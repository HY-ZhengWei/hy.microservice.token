package org.hy.microservice.token.weixin;

import org.hy.common.xml.SerializableDef;





/**
 * 获取微信小程序OpenID的响应数据
 *
 * @author      ZhengWei(HY)
 * @createDate  2021-08-05
 * @version     v1.0
 */
public class OpenIDResponseData extends SerializableDef
{

    private static final long serialVersionUID = -3315415783605995724L;

    /** 微信小程序OpenID */
    private String openid;

    
    
    /**
     * 获取：微信小程序OpenID
     */
    public String getOpenid()
    {
        return openid;
    }

    
    /**
     * 设置：微信小程序OpenID
     * 
     * @param openid
     */
    public void setOpenid(String openid)
    {
        this.openid = openid;
    }
    
}
