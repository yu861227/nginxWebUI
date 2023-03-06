package com.cym.controller.adminPage;

import java.util.List;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.ModelAndView;

import com.cym.config.InitConfig;
import com.cym.model.Http;
import com.cym.service.HttpService;
import com.cym.service.SettingService;
import com.cym.utils.BaseController;
import com.cym.utils.JsonResult;
import com.cym.utils.SnowFlakeUtils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

@Controller
@Mapping("/adminPage/http")
public class HttpController extends BaseController {
	@Inject
	HttpService httpService;
	@Inject
	SettingService settingService;

	@Mapping("")
	public ModelAndView index(ModelAndView modelAndView) {
		List<Http> httpList = httpService.findAll();

		modelAndView.put("httpList", httpList);
		modelAndView.view("/adminPage/http/index.html");
		return modelAndView;
	}

	@Mapping("addOver")
	public JsonResult addOver(Http http) {
		if (StrUtil.isEmpty(http.getId())) {
			http.setSeq(SnowFlakeUtils.getId());
		}
		sqlHelper.insertOrUpdate(http);

		return renderSuccess();
	}

	@Mapping("addTemplate")
	public JsonResult addTemplate(String templateId) {
		httpService.addTemplate(templateId);

		return renderSuccess();
	}

	@Mapping("detail")
	public JsonResult detail(String id) {
		return renderSuccess(sqlHelper.findById(id, Http.class));
	}

	@Mapping("del")
	public JsonResult del(String id) {
		sqlHelper.deleteById(id, Http.class);

		return renderSuccess();
	}

	@Mapping("addGiudeOver")
	public JsonResult addGiudeOver(String json, Boolean logStatus, Boolean webSocket) {
		List<Http> https = JSONUtil.toList(JSONUtil.parseArray(json), Http.class);

		if (logStatus) {

			Http http = new Http();
			http.setName("access_log");
			http.setValue(homeConfig.home + "log/access.log");
			http.setUnit("");
			https.add(http);

			http = new Http();
			http.setName("error_log");
			http.setValue(homeConfig.home + "log/error.log");
			http.setUnit("");
			https.add(http);

		}

		if (webSocket) {
			Http http = new Http();
			http.setName("map");
			http.setValue("$http_upgrade $connection_upgrade {\r\n" //
					+ "    default upgrade;\r\n" //
					+ "    '' close;\r\n" + "}\r\n" + "");//
			http.setUnit("");
			https.add(http);
		}

		httpService.setAll(https);

		return renderSuccess();
	}



	@Mapping("setOrder")
	public JsonResult setOrder(String id, Integer count) {
		httpService.setSeq(id, count);
		return renderSuccess();
	}
}
