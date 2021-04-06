package org.hy.microservice.token;

import org.hy.common.Help;
import org.hy.common.app.Param;
import org.hy.microservice.common.BaseResponse;
import org.hy.microservice.token.user.UserService;
import org.hy.microservice.token.yonyou.YonYouApi;
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
@RequestMapping("token")
public class TokenController
{
    
    @Autowired
    @Qualifier("UserService")
    private UserService userService;
    
    @Autowired
    @Qualifier("MS_Token_IsCheckToken")
    private Param isCheckToken;
    
    @Autowired
    @Qualifier("YonYouApi")
    private YonYouApi yonYouApi;
    
    
    
    /**
     * 用友的票据Token
     * 
     * @author      ZhengWei(HY)
     * @createDate  2021-04-01
     * @version     v1.0
     *
     * @param i_PostInfo
     * @return
     */
    @RequestMapping(value="yonyou" ,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<String> yonyou(@RequestParam(value="token" ,required=false) String i_Token)
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
    
}
