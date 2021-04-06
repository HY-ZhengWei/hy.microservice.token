package org.hy.microservice.token.yonyou;

import java.util.HashMap;
import java.util.Map;

import org.hy.common.Date;
import org.hy.common.Help;
import org.hy.common.Return;
import org.hy.common.app.Param;
import org.hy.common.license.IHash;
import org.hy.common.xml.XHttp;
import org.hy.common.xml.XJSON;
import org.hy.common.xml.annotation.Xjava;
import org.hy.common.xml.log.Logger;
import org.hy.microservice.token.yonyou.sso.LoginCodeRequest;
import org.hy.microservice.token.yonyou.sso.LoginCodeResponse;
import org.hy.microservice.token.yonyou.sso.TokenResponse;





/**
 * 用友接口
 *
 * @author      ZhengWei(HY)
 * @createDate  2020-12-22
 * @version     v1.0
 */
@Xjava
public class YonYouApi
{
 
    private static final Logger $Logger = new Logger(YonYouApi.class);
    
    private static final String $SucceedCode_0   = "00000"; 
    
    private static final String $SucceedCode_200 = "200"; 
    
    /** 当前的用友票据 */
    private static String       $Token           = null;
    
    /** 获取用友票据的时间 */
    private static long         $TokenTime       = 0L;
    
    /** 获取用友票据的过期时长（单位：秒） */
    private static int          $TokenExpire     = 0;
    
    /** 应用编码 */
    @Xjava(ref="MS_Token_YonYou_AppKey")
    private Param appKey;
    
    /** 单点登录编码 */
    @Xjava(ref="MS_Token_YonYou_ThirdUcId")
    private Param thirdUcId;
    
    /** 数字签名 */
    @Xjava(ref="MS_Token_YonYou_Signaturer")
    private IHash signaturer;
    
    /** 单点登录第一步：获取2小时有效的Token */
    @Xjava(ref="MS_Token_YonYou_XHTTP_GetAccessToken")
    private XHttp xhGetToken;
    
    /** 单点登录第二步：获取临时登录code。只能用一次 */
    @Xjava(ref="MS_Token_YonYou_XHTTP_GetLoginCode")
    private XHttp xhLoginCode;
    
    
    
    /**
     * 获取TokenID
     * 
     * 参考文档
     * https://github.com/yonyoucloud-open/corp-demo?from
     * https://open.diwork.com/?from#/doc-center/docDes/doc?code=open_jrwd&section=022c941650ae4989af7dd6ac7fd4d412
     * 
     * appKey 、appSecret两编码
     *    从 https://yonsuite.diwork.com/ 页面的 左上角的【开始菜单】-》【数字化建模】-》【系统管理】-》
     *    【我的应用】-》【开放平台对接】-》【开放平台】-》【API授权】中获取
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-12-22
     * @version     v1.0
     *
     * @return            成功时才返回TokenID
     */
    public String getTokenID()
    {
        long v_Timestamp = Date.getNowTime().getTime();
        if ( $TokenTime + $TokenExpire * 1000 > v_Timestamp )
        {
            return $Token;
        }
        
        Map<String ,Object> v_ReqParams = new HashMap<String ,Object>();
        v_ReqParams.put("appKey"    ,appKey.getValue());
        v_ReqParams.put("timestamp" ,v_Timestamp);
        v_ReqParams.put("signature" ,this.signaturer.encrypt("appKey" + appKey.getValue() + "timestamp" + v_Timestamp));
        
        try
        {
            Return<?> v_Ret = xhGetToken.request(v_ReqParams);
            
            if ( v_Ret != null && v_Ret.booleanValue() && !Help.isNull(v_Ret.getParamStr()) )
            {
                XJSON v_XJson = new XJSON();
                
                TokenResponse v_Data = (TokenResponse)v_XJson.toJava(v_Ret.getParamStr() ,TokenResponse.class);
                
                if ( v_Data != null )
                {
                    if ( $SucceedCode_0.equals(v_Data.getCode()) && v_Data.getData() != null )
                    {
                        $Logger.info("获取用友Token：" + v_Data.getData().getAccess_token() + " ,过期时长：" + v_Data.getData().getExpire());
                        $TokenTime   = Date.getNowTime().getTime();
                        $TokenExpire = v_Data.getData().getExpire() - 10;    // 为容错而减10秒
                        $Token       = v_Data.getData().getAccess_token();
                        return $Token;
                    }
                    else
                    {
                        $Logger.error("获取用友Token异常：" + v_Data.getCode() + " - " + v_Data.getMessage());
                    }
                }
            }
        }
        catch (Exception exce)
        {
            exce.printStackTrace();
        }
        
        return null;
    }
    
    
    
    /**
     * 集成认证中心获取登录临时code
     * 
     * 参考文档：
     *    https://open.diwork.com/#/doc-center/docDes/api?code=diwork&section=d1c38bd20e2e41fbaf200c4fdf883d9b
     * 
     * LoginCodeRequest.thirdUcId
     *    从 https://yonsuite.diwork.com/ 页面的 左上角的【开始菜单】-》【数字化建模】-》【系统管理】-》【集成认证中心】的编码
     * 
     * LoginCodeRequest.userId  （郑伟：4a4a8641-3332-4eb1-907f-85fe3c58233b）
     *    从 https://apcenter.yonyoucloud.com/apptenant/cloud/cloudDataCenter 页面的
     *   【企业账号】-》【用户数】-》【用户数据】中导出得到
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-12-22
     * @version     v1.0
     *
     * @param i_Token          
     * @param i_YonYouUserId  用友的用户编码
     */
    public String getLoginCode(String i_Token ,String i_YonYouUserId)
    {
        Map<String ,Object> v_ReqParams = new HashMap<String ,Object>();
        v_ReqParams.put("access_token" ,i_Token);
        
        LoginCodeRequest v_LoginCode = new LoginCodeRequest();
        
        v_LoginCode.setThirdUcId(this.thirdUcId.getValue());
        v_LoginCode.setUserId(i_YonYouUserId);
        
        try
        {
            XJSON v_XJson = new XJSON();
            
            v_XJson.setReturnNVL(false);
            
            Return<?> v_Ret = xhLoginCode.request(v_ReqParams ,v_XJson.toJson(v_LoginCode).toJSONString());
            
            if ( v_Ret != null && v_Ret.booleanValue() && !Help.isNull(v_Ret.getParamStr()) )
            {
                LoginCodeResponse v_Data = (LoginCodeResponse)v_XJson.toJava(v_Ret.getParamStr() ,LoginCodeResponse.class);
                
                if ( v_Data != null )
                {
                    if ( $SucceedCode_200.equals(v_Data.getCode()) && v_Data.getData() != null )
                    {
                        $Logger.info("获取用友用户登录临时code：" + v_Data.getData().getCode());
                        return v_Data.getData().getCode();
                    }
                    else
                    {
                        if ( v_Data.getData() != null )
                        {
                            $Logger.error("获取用友用户登录临时code异常：" + v_Data.getData().getMsg());
                        }
                        else
                        {
                            $Logger.error("获取用友用户登录临时code异常");
                        }
                    }
                }
            }
        }
        catch (Exception exce)
        {
            exce.printStackTrace();
        }
        
        return null;
    }
    
    
    
    /**
     * 通过用友的工号，生成用友单点登录的链接
     * 
     * @author      ZhengWei(HY)
     * @createDate  2020-12-23
     * @version     v1.0
     *
     * @param i_UserNo   用友的工号
     * @param i_UserName 用户名称（只用于显示）
     * @return
     */
    public String makeSSOLoginUrl(String i_UserNo ,String i_UserName)
    {
        if ( Help.isNull(i_UserNo) )
        {
            return null;
        }
        
        String v_TokenID = this.getTokenID();
        if ( Help.isNull(v_TokenID) )
        {
            return null;
        }
        
        String v_Code = this.getLoginCode(v_TokenID ,i_UserNo);
        if ( Help.isNull(v_Code) )
        {
            return null;
        }
        
        String v_Url = "https://euc.diwork.com/cas/thirdOauth2CodeLogin?thirdUCId=" + this.thirdUcId.getValue() 
                     + "&code=" + v_Code 
                     + "&service=https%3A%2F%2Fyonsuite.diwork.com%2Flogin";
        
        $Logger.info("用户" + i_UserNo + i_UserName +  "的用友单点登录地址：" + v_Url);
        
        return v_Url;
    }
    
}
