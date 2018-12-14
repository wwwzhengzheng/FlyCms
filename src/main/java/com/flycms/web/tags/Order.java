package com.flycms.web.tags;

import com.flycms.core.base.AbstractTagPlugin;
import com.flycms.module.order.service.OrderService;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Open source house, All rights reserved
 * 开发公司：28844.com<br/>
 * 版权：开源中国<br/>
 *
 * 分享订单查询标签
 * 
 * @author sunkaifei
 * 
 */
@Service
public class Order extends AbstractTagPlugin {

	@Autowired
	protected OrderService orderService;

	@Override
	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
		try {
			// 获取页面的参数
			Integer shareId = 0;
			// 获取文件的分页
			//审核设置，默认0
			Integer userId = 0;

			String createTime=null;
			//处理标签变量
			@SuppressWarnings("unchecked")
			Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);
			for(String str:paramWrap.keySet()){ 
				if("shareId".equals(str)){
					shareId = Integer.parseInt(paramWrap.get(str).toString());
				}
				if("userId".equals(str)){
					userId = Integer.parseInt(paramWrap.get(str).toString());
				}
				if("createTime".equals(str)){
					createTime = paramWrap.get(str).toString();
				}
			}
			boolean order = orderService.checkShareOrder(shareId,userId,createTime);
			env.setVariable("order", builder.build().wrap(order));
			body.render(env.getOut());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
