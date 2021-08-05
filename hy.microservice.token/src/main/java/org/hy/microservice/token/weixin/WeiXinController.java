package org.hy.microservice.token.weixin;

import java.util.HashMap;
import java.util.Map;

import org.hy.common.Help;
import org.hy.common.Return;
import org.hy.common.StringHelp;
import org.hy.common.xml.XHttp;
import org.hy.common.xml.XJSON;
import org.hy.microservice.common.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;





/**
 * 票据Token（与第三方对接口登录的票据）的控制层
 *
 * @author      ZhengWei(HY)
 * @createDate  2021-08-01
 * @version     v1.0
 */
@Controller
@RestController
@RequestMapping("weixin")
public class WeiXinController
{
    
    @Autowired
    @Qualifier("MS_Token_WeiXin_XHTTP_GetOpenID")
    private XHttp XhGetOpenID;
    
    
    
    /**
     * 获取微信小程序的OpenID
     * 
     * @author      ZhengWei(HY)
     * @createDate  2021-08-05
     * @version     v1.0
     *
     * @param i_Code  临时票据号
     * @return
     */
    @RequestMapping(value="openID" ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<OpenIDResponseData> getOpenID(@RequestParam(value="code" ,required=false) String i_Code)
    {
        BaseResponse<OpenIDResponseData> v_RetResp = new BaseResponse<OpenIDResponseData>();
        
        Map<String ,Object> v_ReqParams = new HashMap<String ,Object>();
        v_ReqParams.put("js_code" ,i_Code);
        
        try
        {
            Return<?> v_Ret = XhGetOpenID.request(v_ReqParams);
            
            if ( v_Ret != null && v_Ret.booleanValue() && !Help.isNull(v_Ret.getParamStr()) )
            {
                if ( v_Ret.getParamStr().indexOf("openid") >= 0 )
                {
                    // 成功时返回：{"session_key":"vOoxf1Ho1Jx3+Yw5wVgsmA==","openid":"ozHHx4ueoF9BK7ZlKStP-zxMwNZg"}
                    XJSON v_XJson = new XJSON();
                    v_XJson.setReturnNVL(false);
                    
                    OpenIDResponseData v_Data = (OpenIDResponseData)v_XJson.toJava(v_Ret.getParamStr() ,OpenIDResponseData.class);
                    return v_RetResp.setData(v_Data);
                }
                else
                {
                    // 异常时返回：{"errcode":40029,"errmsg":"invalid code, rid: 610bd4ad-0b8248fc-0a4d403b"}
                    return v_RetResp.setCode("-901").setMessage(StringHelp.replaceAll(v_Ret.getParamStr() ,"\"" ,"'"));
                }
            }
        }
        catch (Exception exce)
        {
            exce.printStackTrace();
        }
        
        return v_RetResp.setCode("-900");
    }
    
}
