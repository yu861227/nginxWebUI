package com.cym.controller.adminPage;

import java.util.ArrayList;
import java.util.List;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.ModelAndView;

import com.cym.model.Stream;
import com.cym.model.Stream;
import com.cym.service.StreamService;
import com.cym.utils.BaseController;
import com.cym.utils.JsonResult;
import com.cym.utils.SnowFlakeUtils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

@Controller
@Mapping("/adminPage/stream")
public class StreamController extends BaseController {
	@Inject
	StreamService streamService;

	@Mapping("")
	public ModelAndView index( ModelAndView modelAndView) {
		List<Stream> streamList = streamService.findAll();

		modelAndView.put("streamList", streamList);
		modelAndView.view("/adminPage/stream/index.html");
		return modelAndView;
	}

	@Mapping("addOver")
	public JsonResult addOver(Stream stream) {
		if (StrUtil.isEmpty(stream.getId())) {
			stream.setSeq( SnowFlakeUtils.getId());
		}
		sqlHelper.insertOrUpdate(stream);

		return renderSuccess();
	}
	

	@Mapping("addTemplate")
	public JsonResult addTemplate(String templateId) {
		streamService.addTemplate(templateId);
		
		return renderSuccess();
	}

	@Mapping("detail")
	public JsonResult detail(String id) {
		return renderSuccess(sqlHelper.findById(id, Stream.class));
	}

	@Mapping("del")
	public JsonResult del(String id) {
		sqlHelper.deleteById(id, Stream.class);

		return renderSuccess();
	}

	@Mapping("setOrder")
	public JsonResult setOrder(String id, Integer count) {
		streamService.setSeq(id, count);

		return renderSuccess();
	}
	
	@Mapping("addGiudeOver")
	public JsonResult addGiudeOver(Boolean logStatus) {
		List<Stream> streams = new ArrayList<Stream>();
		if (logStatus) {

			Stream stream = new Stream();
			stream.setName("log_format basic");
			stream.setValue("'$remote_addr [$time_local] $protocol $status $bytes_sent $bytes_received $session_time \"$upstream_addr\" \"$upstream_bytes_sent\" \"$upstream_bytes_received\" \"$upstream_connect_time\"'");
			streams.add(stream);

			stream = new Stream();
			stream.setName("access_log");
			stream.setValue(homeConfig.home + "log/stream_access.log basic");
			streams.add(stream);

			stream = new Stream();
			stream.setName("open_log_file_cache");
			stream.setValue("off");
			streams.add(stream);
		}

		streamService.setAll(streams);

		return renderSuccess();
	}

}
