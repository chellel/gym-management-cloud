package com.gym.common.utils;

import com.github.pagehelper.PageHelper;
import com.gym.common.core.page.PageDomain;
import com.gym.common.core.page.TableSupport;
import com.gym.common.utils.sql.SqlUtil;
import com.gym.common.utils.StringUtils;

/**
 * 分页工具类
 * 
 * @author ruoyi
 */
public class PageUtils extends PageHelper
{
    /**
     * 设置请求分页数据
     */
    public static void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = pageDomain.getOrderBy();
        Boolean reasonable = pageDomain.getReasonable();
        
        // 只有当orderBy不为空时才进行SQL验证
        if (StringUtils.isNotEmpty(orderBy))
        {
            orderBy = SqlUtil.escapeOrderBySql(orderBy);
        }
        
        PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }

    /**
     * 清理分页的线程变量
     */
    public static void clearPage()
    {
        PageHelper.clearPage();
    }
}
