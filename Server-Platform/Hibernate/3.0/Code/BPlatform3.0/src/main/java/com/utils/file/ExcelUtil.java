package com.utils.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Excel导入导出工具类
 * </p>
 * 
 * @author memory 2017年6月21日 上午10:17:22
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2017年6月21日
 * @modify by reason:{方法名}:{原因}
 */
public class ExcelUtil {
	private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

	/**
	 * 判断excel文件后缀名，生成不同的workbook
	 * 
	 * @author memory 2017年6月21日 上午10:18:44
	 * @param is
	 * @param excelFileName
	 * @return
	 * @throws IOException
	 */
	private Workbook createWorkbook(InputStream is, String excelFileName) throws IOException {
		if (excelFileName.endsWith(".xls")) {
			return new HSSFWorkbook(is);
		} else if (excelFileName.endsWith(".xlsx")) {
			return new XSSFWorkbook(is);
		}
		return null;
	}

	/**
	 * @Title: getSheet
	 * @Description: 根据sheet索引号获取对应的sheet
	 * @param @param workbook
	 * @param @param sheetIndex
	 * @param @return
	 * @return Sheet
	 * @throws
	 */
	private Sheet getSheet(Workbook workbook, int sheetIndex) {
		return workbook.getSheetAt(0);
	}

	/**
	 * @param <T>
	 * @Title: importDataFromExcel
	 * @Description: 将sheet中的数据保存到list中， 1、调用此方法时，vo的属性个数必须和excel文件每行数据的列数相同且一一对应，vo的所有属性都为String 2、在调用此方法时，需声明
	 *               private File excelFile; 3、页面的file控件name需对应File的文件名
	 * @param @param vo javaBean
	 * @param @param is 输入流
	 * @param @param excelFileName 原始文件的文件名
	 * @param @return
	 * @return List<Object>
	 * @throws
	 */
	public <T> List<T> importDataFromExcel(Class<T> type, InputStream is, String excelFileName) {
		List<T> list = new ArrayList<T>();
		try {
			// 创建工作簿
			Workbook workbook = this.createWorkbook(is, excelFileName);
			// 创建工作表sheet
			Sheet sheet = this.getSheet(workbook, 0);
			// 获取sheet中数据的行数
			int rows = sheet.getPhysicalNumberOfRows();
			// 获取表头单元格个数
			int cells = sheet.getRow(0).getPhysicalNumberOfCells();
			
			for (int i = 1; i < rows; i++) {// 第一行为标题栏，从第二行开始取数据
				// 利用反射，给JavaBean的属性进行赋值
				T vo = org.springframework.beans.BeanUtils.instantiate(type);
				Field[] fields = vo.getClass().getDeclaredFields();
				
				Row row = sheet.getRow(i);
				int index = 0;
				while (index < cells) {
					Cell cell = row.getCell(index);
					if (null == cell) {
						cell = row.createCell(index);
					}
					cell.setCellType(Cell.CELL_TYPE_STRING);
					String value = null == cell.getStringCellValue() ? "" : cell.getStringCellValue();

					Field field = fields[index];
					String fieldName = field.getName();
					String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					Method setMethod = vo.getClass().getMethod(methodName, new Class[] { String.class });
					setMethod.invoke(vo, new Object[] { value });
					index++;
				}
				if (!isHasValues(vo)) {// 判断对象属性是否有值
					list.add(vo);
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				is.close();// 关闭流
			} catch (Exception e2) {
				logger.error(e2.getMessage());
				e2.printStackTrace();
			}
		}
		return list;

	}

	/**
	 * @Title: isHasValues
	 * @Description: 判断一个对象所有属性是否有值，如果一个属性有值(分空)，则返回true
	 * @param @param object
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	private boolean isHasValues(Object object) {
		Field[] fields = object.getClass().getDeclaredFields();
		boolean flag = false;
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			Method getMethod;
			try {
				getMethod = object.getClass().getMethod(methodName);
				Object obj = getMethod.invoke(object);
				if (null != obj && "".equals(obj)) {
					flag = true;
					break;
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}

		}
		return flag;

	}

	/**
	 * 将数据导出到excel中
	 * @param list
	 * @param headers
	 * @param title
	 * @param os
	 */
	public <T> void exportDataToExcel(List<T> list, String[] headers, String title, OutputStream os) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽15个字节
		sheet.setDefaultColumnWidth(15);
		// 生成一个样式
		HSSFCellStyle style = this.getCellStyle(workbook);
		// 生成一个字体
		HSSFFont font = this.getFont(workbook);
		// 把字体应用到当前样式
		style.setFont(font);

		// 生成表格标题
		HSSFRow row = sheet.createRow(0);
		row.setHeight((short) 300);
		HSSFCell cell = null;

		for (int i = 0; i < headers.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		// 将数据放入sheet中
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(i + 1);
			T t = list.get(i);
			// 利用反射，根据JavaBean属性的先后顺序，动态调用get方法得到属性的值
			Field[] fields = t.getClass().getFields();
			try {
				for (int j = 0; j < fields.length; j++) {
					cell = row.createCell(j);
					Field field = fields[j];
					String fieldName = field.getName();
					String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					Method getMethod = t.getClass().getMethod(methodName, new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});

					if (null == value)
						value = "";
					cell.setCellValue(value.toString());

				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		try {
			workbook.write(os);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				os.flush();
				os.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}

	}

	/**
	 * @Title: getCellStyle
	 * @Description: 获取单元格格式
	 * @param @param workbook
	 * @param @return
	 * @return HSSFCellStyle
	 * @throws
	 */
	private HSSFCellStyle getCellStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		return style;
	}

	/**
	 * @Title: getFont
	 * @Description: 生成字体样式
	 * @param @param workbook
	 * @param @return
	 * @return HSSFFont
	 * @throws
	 */
	private HSSFFont getFont(HSSFWorkbook workbook) {
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		return font;
	}

	public boolean isIE(HttpServletRequest request) {
		return request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0 ? true : false;
	}
}
