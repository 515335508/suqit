package com.bytedance.pojo;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;


/**
 * 统计视图工具类
 * @author Dark-Matter
 */
public class ViewUtil {

	/**
	 * 导出Excel表工具
	 * @param response	HTTP响应对象
	 * @param list			存储了数据的对象
	 * @param pageSize		多少数据进行分页(新建面板)【取值范围:10-65530之间】
	 * @param title			标题
	 * @param shield		屏蔽的字段(可以屏蔽多字段【可以传递数组】)
	 * @throws Exception
	 */
	public static <T> void excelTool(HttpServletResponse response, List<T> list, int pageSize, String title, String... shield)  throws Exception {
		//创建HSSFWorkbook对象(excel的文档对象)
	      	HSSFWorkbook wb = new HSSFWorkbook();	
	    //建立新的sheet对象（excel的表单）
	      HSSFSheet sheet = null; 
	      sheet	= wb.createSheet(title); 
	      	//判断分页数据是否越界(分页数据最小不得低于10)
	      		if(pageSize < 10 || pageSize >65530){
	      			pageSize = 65530;
	      		}
	      //记录每次页面起始的行数
	      	int num = 0;
	      //记录页码数(初始页码数为:2)
	      	int pageNum = 2;
	      //list里有多少对象就创建多少行
	      for (int i = 0; i < list.size(); i++) {
	    	  num += 1;
	    	  //对数据进行分页
		    	  if(i != 0 && i%pageSize == 0){
		    		  num = 1;
		    		  sheet	= wb.createSheet("第"+ pageNum +"页"); 
		    		  pageNum = pageNum + 1;
		    	  }
	    	  //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
		      	HSSFRow row1=sheet.createRow(0);
		      //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
		      	HSSFCell cell = null;
		      		cell = row1.createCell(0);
			      //设置单元格内容
			      cell.setCellValue(title);
			   //在sheet里创建第二行
			      HSSFRow row2=sheet.createRow(1); 
			      HSSFRow row = null;
	    	  
	    	  //创建行数
	    	  	row = sheet.createRow(num+1);
	    	  	if(list.get(i) != null){
	    	  	//获取到list里存储的对象
		    	  	T t = list.get(i);
		    	  //获取到对象的Class类对象
		    	  	Class<? extends Object> clazz = t.getClass();
		    	  //获取到对象的所有属性
		    	  	Field[] fields = clazz.getDeclaredFields();
		    	  //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
				     sheet.addMergedRegion(new CellRangeAddress(0,0,0,fields.length-1));
		    	  //迭代所有属性方法
		    	  	for (int j = 0; j < fields.length; j++) {
		    	  		//属性的个数不能超过255个
		    	  			if(j > 255){break;}
		    	  		//存储获取到的属性值
		    	  			Object value = "";
		    	  		//获取到属性名
		    	  			String name = fields[j].getName();
		    	  			
		    	  			if(shield!=null){
		    	  				boolean ret = false;
			    	  			for(String sname : shield ){
				    	  			if( ret = (sname.toLowerCase()).equals(name.toLowerCase()) ){
				    	  				break;
				    	  			}
				    	  		}
			    	  			if(ret){continue;}
			    	  		}
		    	  			
			  		//设置头
			  			row2.createCell(j).setCellValue(name);
			  		//将属性名切割拼装为get方法,获取对象的属性值
			  			String fieldName = "get" + (name.substring(0, 1).toUpperCase()) +  (name.substring(1, name.length()));
			  		//获取到对应的get方法对象
			  			Method method =  null;
			  			try {
			  				method = clazz.getMethod(fieldName, null);
			  			//执行方法，得到对应的值
		    	  			value = method.invoke(t, null);
		    	  		//判断取出的值是否为空
		    	  			if(value == null){value = "";}
						} catch (Exception e) {
							e.printStackTrace();
						}
			  			//获取到每一格的Style样式
			  				HSSFCellStyle style = cell.getCellStyle();
			  			//将每一格文字居中对齐
			  				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			  				cell = row.createCell(j);
			  			//全部数据以字符串的格式储存
			  				cell.setCellValue(value.toString());
			  			//自动调整列宽
			  				sheet.autoSizeColumn(j);
					}
	    	  	}
	      }
	       
		//生成时间戳
			  Date date = new Date();
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSS");
			  title = new String(title.getBytes("UTF-8"), "ISO-8859-1");
			  String das = sdf.format(date);
		//生成文件名
			      String xlsName = das+":"+title+".xls";
	      //输出Excel文件
	          OutputStream output = response.getOutputStream();
	          response.reset();
	          response.setHeader("Content-disposition", "attachment; filename="+xlsName);
	          response.setContentType("application/msexcel");
	          wb.write(output);
	          output.flush();
	       //关闭流资源
	          output.close();
	}
	
	/*==============================一条分割线===================================*/
	
	
	/**
	 * 
	 * @param name	统计视图的对应的Name值字段名(传入对象的属性名)
	 * @param value	统计视图的对应的Value值字段名(传入对象的属性名)
	 * @param obj	要转换的带有数据的对象
	 * @param t		转换为视图对象的实体类对象
	 * @return
	 * @throws Exception
	 * 统计视图对象的属性名必须为 name & value
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> parse2JsonViewList(String name, String value, List<?> lists, Class<T> clazz2) throws Exception{
		List<T> arrayList = new ArrayList<T>();
		
			for (Object obj : lists) {
				Object jsv = parseData2JsonView(name, value, obj, clazz2);
				arrayList.add((T) jsv);
			}
			
		return arrayList;
	}
	
	
	/**
	 * 
	 * @param name	统计视图的对应的Name值字段名(obj的属性名)
	 * @param value	统计视图的对应的Value值字段名(obj的属性名)
	 * @param obj		要转换的带有数据的对象
	 * @param t			转换为视图对象的实体类对象
	 * @return
	 * @throws Exception
	 * 统计视图对象的属性名必须为 name(属性类型与传入对象相对应键的属性类型相同) &  value(属性类型与传入对象相对应键的属性类型相同)
	 */
	public static <T> Object parseData2JsonView(String name, String value, Object obj, Class<T> clazz2) throws Exception {
		//存储实体类对象
			Class<? extends Object> clazz1 = obj.getClass();
		//转换的类对象
			Method[] method = new Method[4];
			//获取到当前对象的所有私有属性
				Field[] fields1 = clazz1.getDeclaredFields();
				for (Field field1 : fields1) {
					String fieldName1 = field1.getName();
					String zname = "get" + (fieldName1.substring(0, 1).toUpperCase()) +  (fieldName1.substring(1, fieldName1.length()));
				//判断类中是否有这几个属性
					if(fieldName1.toLowerCase().equals(name.toLowerCase()) ){
						method[0] = clazz1.getMethod(zname, null);
					}
					if(fieldName1.toLowerCase().equals(value.toLowerCase()) ){
						method[1] = clazz1.getMethod(zname, null);
					}
				}
			
				Field[] fields2 = clazz2.getDeclaredFields();
				for (Field field2 : fields2) {
					String fieldName2 = field2.getName();
					String zname = "set" + (fieldName2.substring(0, 1).toUpperCase()) +  (fieldName2.substring(1, fieldName2.length()));
				//判断类中是否有这几个属性
					if("name".equals(fieldName2.toLowerCase())){
						method[2] = clazz2.getMethod(zname, field2.getType());
					}
					if("value".equals(fieldName2.toLowerCase())){
						method[3] = clazz2.getMethod(zname, field2.getType());
					}
				}
			int nu = -1;
				for (int i = 0; i < method.length; i++) {
					if(method[i] == null){
						nu = i;break;
					}
				}
			
			switch (nu) {
			case 0:
				throw new Exception("The property \""+name+"\" you entered does not exist ！");
			case 1:
				throw new Exception("The property \""+value+"\" you entered does not exist ！");
			case 2:
				throw new Exception("There is no \"name\" attribute in your class:" + clazz2);
			case 3:
				throw new Exception("There is no \"value\" attribute in your class:" + clazz2);
			default:
				T t = clazz2.newInstance();
				//创建对象
					method[2].invoke(t, method[0].invoke(obj, null));
					method[3].invoke(t, method[1].invoke(obj, null));
				return t;	
			}
	}
	
	//使这个类不能实例化
	private ViewUtil(){}
	

}
