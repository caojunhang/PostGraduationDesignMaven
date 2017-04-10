package cn.edu.ncut.istc.plugin;

import cn.edu.ncut.istc.common.plugin.JsonUtils;
import cn.edu.ncut.istc.dao.AuthorDao;
import cn.edu.ncut.istc.dao.OrganizationinfoDao;
import cn.edu.ncut.istc.dao.ProductDao;
import cn.edu.ncut.istc.dao.UserDao;
import cn.edu.ncut.istc.model.plugin.AuthorPrototype;
import cn.edu.ncut.istc.model.plugin.ISTCPrototype;
import cn.edu.ncut.istc.model.plugin.Result;
import cn.edu.ncut.istc.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lixiwei on 2016/4/13.
 */
@Controller
@RequestMapping(value = "/productRegister")
@Scope("request")
public class ProductRegisterController
{
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ApplyService applyService;
    @Autowired
    private OrganizationinfoDao organizationinfoDao;
    @Autowired
    private AuthorDao authorDao;


    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody Object register(HttpServletRequest request)
    {
        Result result = new Result(1, "SUCCEED");
        String json = request.getParameter("json");
        if (json == null || json.equals(""))
            return "Failed:Json为空";
        try
        {
            ISTCPrototype prototype = JsonUtils.getObject(json, ISTCPrototype.class);
             /*
                1.Product完整性校验
                2.Product数据合法性检验
                3.关键字查重
                4.文本查重
                5.存储数据
             */
            String resultMes = applyService.checkText(prototype);
            if(!resultMes.contains("SUCCEED"))
            {
                result.setFlag(2);
                result.setMessage(resultMes);
                return result;
            }
            result.setMessage(resultMes);
        } catch (Exception e)
        {
            e.printStackTrace();
            result.setFlag(2);
            result.setMessage("登记出现异常！" + e.getMessage());
        }
        System.out.println("Result: " + result.getMessage());
        return result;
    }

    /**
     * 出版社作者登记接口
     * 1.接收Json参数,组织AuthorPrototype
     * 2.抽取出版社Ocode查找出版社,判断是否存在,不存在退回.
     * 3.抽取作者UniqueId查找作者,判断是否存在,存在退回.
     * 4.存储Author,AuthorOrg对应的表
     * 5.作者登记成功
     * @param request
     * @return
     */
    @RequestMapping(value = "/authorRegister", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public @ResponseBody Object authorRegister(HttpServletRequest request)
    {
        Result result = new Result(1, "SUCCEED");
        String json = request.getParameter("json");

        AuthorPrototype prototype = JsonUtils.getObject(json, AuthorPrototype.class);
        //检查是否存在相应的出版社
        try
        {
            String resultMes = applyService.checkAuthor(prototype);
            if(!resultMes.contains("SUCCEED"))
                result.setFlag(2);
            result.setMessage(resultMes);
        } catch (Exception e)
        {
            e.printStackTrace();
            result.setFlag(2);
            result.setMessage("Failed:作者登记出现异常！" + e.getMessage());
        }
        System.out.println("Result: " + result.getMessage());
        return result;
    }
}
