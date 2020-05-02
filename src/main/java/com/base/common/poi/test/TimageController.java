package com.base.common.poi.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/imag")
@Slf4j
public class TimageController {
	/*@Resource
	private TImagesService service;
	
	@ResponseBody
	@RequestMapping("/imag")
	protected List<TImages> hello(Map<String, Object> map) {
		List<TImages> list = service.findALL();
	
		byte[] blob = list.get(0).getImag();
	
		InputStream inStream = new ByteArrayInputStream(blob);
		return service.findALL();
	}
	
	@RequestMapping("add")
	@ResponseBody
	public void add(TImages TImages) {
		JSONObject json = new JSONObject();
		String pic = "F:\\1.jpg";//这里暂时放一张本地图片，也可以让前台传过来
		File file = new File(pic);
		try {
			InputStream is = new FileInputStream(file);//得到文件流
			byte[] bytes = FileCopyUtils.copyToByteArray(is);//得到byte
			// TImages.setId(UUIDsupport.getUUIDWithoutMinus());
			TImages.setImag(bytes);
			// int add =  picInfoService.addPic(picInfo);//添加到数据库中
			int add = 0;
			if (add > 0) {
				//  json.put("add", add);
				// json.put("msg","上传成功");
			} else {
				// json.put("msg","上传失败");
			}
			//toClient(json.toString());
		} catch (IOException e) {
			log.info("异常信息", e);
		}
	}
	
	*//**
		因为图片的大小可能不一致，但是在页面中输出的大小需要统一，所以需要
		
		在OracleQueryBean类中增加一个函数，来进行缩放，具体代码如下：
		* 缩小或放大图片
		
		* @param data   图片的byte数据
		
		* @param nw      需要缩到的宽度
		
		* @param nh      需要缩到高度
		
		* @return       缩放后的图片的byte数据
		
		*//*
			
			private byte[] ChangeImgSize(byte[] data, int nw, int nh) {
			
			byte[] newdata = null;
			
			try {
			
				BufferedImage bis = ImageIO.read(new ByteArrayInputStream(data));
			
				int w = bis.getWidth();
			
				int h = bis.getHeight();
			
				double sx = (double) nw / w;
			
				double sy = (double) nh / h;
			
				AffineTransform transform = new AffineTransform();
			
				transform.setToScale(sx, sy);
			
				AffineTransformOp ato = new AffineTransformOp(transform, null);
			
				//原始颜色
			
				BufferedImage bid = new BufferedImage(nw, nh, BufferedImage.TYPE_3BYTE_BGR);
			
				ato.filter(bis, bid);
			
				//转换成byte字节
			
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
				ImageIO.write(bid, "jpeg", baos);
			
				newdata = baos.toByteArray();
			
			} catch (IOException e) {
			
				e.printStackTrace();
			
			}
			
			return newdata;
			
			}
			*/
}
