package com.memory.platform.core.springmvc;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * spring中日期转换
 * 
 * <pre>
 * &#064;InitBinder
 * public void initBinder(WebDataBinder binder) {
 * 	binder.registerCustomEditor(Date.class, new DateConvertEditor());
 * 	// binder.registerCustomEditor(Date.class, new
 * 	// DateConvertEditor(&quot;yyyy-MM-dd&quot;));
 * 	binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
 * }
 * </pre>
 * 
 * 
 * @author zhgangkeqi
 * @date 2014-6-5 下午1:48:37
 */
public class DateConvertEditor extends PropertyEditorSupport {
	private static final Logger logger = LoggerFactory
			.getLogger(DateConvertEditor.class);

	private SimpleDateFormat format;
	private SimpleDateFormat formatDate;

	public DateConvertEditor() {
		this.format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.formatDate = new SimpleDateFormat("yyyy-MM-dd");
	}

	public DateConvertEditor(String format) {
		this.format = new SimpleDateFormat(format);
	}

	/** Date -> String */
	@Override
	public String getAsText() {
		if (getValue() == null)
			return "";
		Date d = (Date) getValue();
		if (d.getHours() == 0 && d.getMinutes() == 0 && d.getSeconds() == 0) {
			return this.formatDate.format(getValue());
		}
		return this.format.format(getValue());
	}

	/** String -> Date */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		if (!StringUtils.isNotBlank(text)) {
			setValue(null);
		} else {
			// 将yyyy-MM-dd HH:mm格式自动加0补全为yyyy-MM-dd HH:mm:ss
			if (text.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")) {
				text = text + ":00";
			}

			try {
				if (text.matches("\\d{4}-\\d{2}-\\d{2}")) {
					setValue(this.formatDate.parse(text));
				} else {
					setValue(this.format.parse(text));
				}
			} catch (ParseException e) {
				logger.error("不能被转换的日期字符串（yyyy-MM-dd HH:mm:ss），请检查!", e);
				throw new IllegalArgumentException(
						"不能被转换的日期字符串（yyyy-MM-dd HH:mm:ss），请检查!", e);
			} catch (Throwable e) {
				logger.error("不能被转换的日期字符串（yyyy-MM-dd HH:mm:ss），请检查!", e);
				throw new IllegalArgumentException(
						"不能被转换的日期字符串（yyyy-MM-dd HH:mm:ss），请检查!", e);
			}
		}
	}

	public static void main(String[] args) {
		String text = "2016-04-07";
		DateConvertEditor convertEditor = new DateConvertEditor();
		convertEditor.setAsText(text);
		System.out.println(convertEditor.getAsText());
	}
}
