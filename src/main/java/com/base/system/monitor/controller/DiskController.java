package com.base.system.monitor.controller;

import com.base.common.api.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 监控磁盘信息
 *
 * @author ylg  2020-03-18
 */
@Slf4j
@RequestMapping("/actuator")
public class DiskController {
	/**
	 * 实时磁盘监控
	 *
	 * @param request
	 * @param response
	 *
	 * @return
	 */
	@GetMapping("/monitor/queryDiskInfo")
	public Result<List<Map<String, Object>>> queryDiskInfo(HttpServletRequest request, HttpServletResponse response) {
		Result<List<Map<String, Object>>> res = new Result<>();
		try {
			// 当前文件系统类
			FileSystemView fsv = FileSystemView.getFileSystemView();
			// 列出所有windows 磁盘
			File[] fs = File.listRoots();
			log.info("查询磁盘信息:" + fs.length + "个");
			List<Map<String, Object>> list = new ArrayList<>();

			for (int i = 0; i < fs.length; i++) {
				if (fs[i].getTotalSpace() == 0) {
					continue;
				}
				Map<String, Object> map = new HashMap<>();
				map.put("name", fsv.getSystemDisplayName(fs[i]));
				map.put("max", fs[i].getTotalSpace());
				map.put("rest", fs[i].getFreeSpace());
				map.put("restPPT", fs[i].getFreeSpace() * 100 / fs[i].getTotalSpace());
				list.add(map);
				log.info(map.toString());
			}
			res.setResult(list);
			res.success("查询成功");
		} catch (Exception e) {
			res.error500("查询失败" + e.getMessage());
		}
		return res;
	}

}
