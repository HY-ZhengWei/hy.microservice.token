package org.hy.microservice.token.yonyou;

import org.hy.common.Help;
import org.hy.common.app.Param;
import org.hy.microservice.common.BaseResponse;
import org.hy.microservice.token.user.UserService;
import org.hy.microservice.token.user.UserSSO;
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
 * @createDate  2021-04-01
 * @version     v1.0
 */
@Controller
@RestController
@RequestMapping("yonyou")
public class YonYouController
{
    
    @Autowired
    @Qualifier("UserService")
    private UserService userService;
    
    @Autowired
    @Qualifier("YonYouApi")
    private YonYouApi yonYouApi;
    
    @Autowired
    @Qualifier("MS_Token_IsCheckToken")
    private Param isCheckToken;
    
    
    
    /**
     * 用友的票据Token
     * 
     * @author      ZhengWei(HY)
     * @createDate  2021-04-01
     * @version     v1.0
     *
     * @param i_Token  票据号
     * @return
     */
    @RequestMapping(value="token" ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<String> token(@RequestParam(value="token" ,required=false) String i_Token)
    {
        BaseResponse<String> v_RetResp = new BaseResponse<String>();
        
        if ( isCheckToken != null && Boolean.parseBoolean(isCheckToken.getValue()) )
        {
            // 验证票据及用户登录状态
            if ( Help.isNull(i_Token) ) 
            {
                return v_RetResp.setCode("-901").setMessage("非法访问");
            }
            
            UserSSO v_User = this.userService.getUser(i_Token);
            if ( v_User == null ) 
            {
                return v_RetResp.setCode("-901").setMessage("非法访问");
            }
        }
        
        return v_RetResp.setData(this.yonYouApi.getTokenID());
    }
    
    
    
    /**
     * 用友单点登录，打开用友控制台
     * 
     * @author      ZhengWei(HY)
     * @createDate  2021-04-06
     * @version     v1.0
     *
     * @param i_Token     票据号
     * @param i_UserNo    用友的工号
     * @param i_UserName  用户名称
     * @return
     */
    @RequestMapping(value="/open" ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<String> open(@RequestParam(value="token"    ,required=false) String i_Token 
                                    ,@RequestParam(value="userNo"   ,required=false) String i_UserNo 
                                    ,@RequestParam(value="userName" ,required=false) String i_UserName)
    {
        BaseResponse<String> v_RetResp = new BaseResponse<String>();
        
        if ( isCheckToken != null && Boolean.parseBoolean(isCheckToken.getValue()) )
        {
            // 验证票据及用户登录状态
            if ( Help.isNull(i_Token) ) 
            {
                return v_RetResp.setCode("-901").setMessage("非法访问");
            }
            
            UserSSO v_User = this.userService.getUser(i_Token);
            if ( v_User == null ) 
            {
                return v_RetResp.setCode("-901").setMessage("非法访问");
            }
        }
        
        if ( Help.isNull(i_UserNo) )
        {
            return v_RetResp.setCode("-101").setMessage("用友工号为空");
        }
        
        if ( Help.isNull(i_UserName) )
        {
            return v_RetResp.setCode("-102").setMessage("用户名称为空");
        }
        
        String v_Url = this.yonYouApi.makeSSOLoginUrl(i_UserNo ,i_UserName);
        if ( Help.isNull(v_Url) )
        {
            v_Url = "#";
        }
        
        return v_RetResp.setData(v_Url);
    }
    
}
