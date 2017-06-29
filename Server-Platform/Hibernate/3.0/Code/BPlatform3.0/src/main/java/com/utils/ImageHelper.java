package com.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

import java.util.List;

import javax.imageio.ImageIO;

/***
 * 对图片进行操作
 * 
 * @author chenzheng_java
 * @since 2011/7/29
 * 
 */
public class ImageHelper {

	private static ImageHelper imageHelper = null;

	public static ImageHelper getImageHelper() {
		if (imageHelper == null) {
			imageHelper = new ImageHelper();
		}
		return imageHelper;
	}

	public static void main(String[] args) {
		scaleImage("E:\\Administrator\\桌面\\鱼眼拍摄\\07_b.jpg", "C:\\Program Files\\file cache\\file.jpg", 0.1, "jpg");
	}

	/**
	 * 按指定的比例缩放图片
	 * 
	 * @author yangshaoping 2016年12月2日 下午5:11:43
	 * @param sourceImagePath
	 *            源地址
	 * @param destinationPath
	 *            改变大小后图片的地址
	 * @param scale
	 *            缩放比例，如1.2
	 * @param format
	 *            图图片格式 例如 jpg
	 */
	public static boolean scaleImage(String sourceImagePath, String destinationPath, double scale, String format) {

		File file = new File(sourceImagePath);
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(file);
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();

			width = parseDoubleToInt(width * scale);
			height = parseDoubleToInt(height * scale);

			Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics graphics = outputImage.getGraphics();
			graphics.drawImage(image, 0, 0, null);
			graphics.dispose();

			ImageIO.write(outputImage, format, new File(destinationPath));

			return true;
		} catch (IOException e) {
			System.out.println("scaleImage方法压缩图片时出错了");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 按指定的比例缩放图片
	 * 
	 * @author yangshaoping 2016年12月2日 下午5:36:08
	 * @param file
	 * @param destinationPath
	 *            改变大小后图片的地址
	 * @param scale
	 *            缩放比例，如1.2
	 * @param format
	 *            图图片格式 例如 jpg
	 */
	public static boolean scaleImage(File file, String destinationPath, double scale, String format) {

		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(file);
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();

			width = parseDoubleToInt(width * scale);
			height = parseDoubleToInt(height * scale);

			Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics graphics = outputImage.getGraphics();
			graphics.drawImage(image, 0, 0, null);
			graphics.dispose();

			ImageIO.write(outputImage, format, new File(destinationPath));

			return true;
		} catch (IOException e) {
			System.out.println("scaleImage方法压缩图片时出错了");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 将图片缩放到指定的高度或者宽度
	 * 
	 * @author yangshaoping 2016年12月2日 下午5:13:49
	 * @param sourceImagePath
	 *            图片源地址
	 * @param destinationPath
	 *            压缩完图片的地址
	 * @param width
	 *            缩放后的宽度
	 * @param height
	 *            缩放后的高度
	 * @param auto
	 *            是否自动保持图片的原高宽比例
	 * @param format
	 *            图图片格式 例如 jpg
	 */
	public static void scaleImageWithParams(final String sourceImagePath, final String destinationPath, int width, int height,
			final boolean auto, final String format) {

		try {
			final File file = new File(sourceImagePath);
			final BufferedImage bufferedImage = ImageIO.read(file);
			if (auto) {
				final List<Integer> paramsArrayList = getAutoWidthAndHeight(bufferedImage, width, height);
				width = paramsArrayList.get(0);
				height = paramsArrayList.get(1);
				System.out.println("自动调整比例，width=" + width + " height=" + height);
			}

			final Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
			final BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			final Graphics graphics = outputImage.getGraphics();
			graphics.drawImage(image, 0, 0, null);
			graphics.dispose();
			ImageIO.write(outputImage, format, new File(destinationPath));
		} catch (Exception e) {
			System.out.println("scaleImageWithParams方法压缩图片时出错了");
			e.printStackTrace();
		}
	}

	/**
	 * 将double类型的数据转换为int，四舍五入原则
	 * 
	 * @author yangshaoping 2016年12月2日 下午5:20:58
	 * @param sourceDouble
	 * @return
	 */
	private static int parseDoubleToInt(double sourceDouble) {
		int result = 0;
		result = (int) sourceDouble;
		return result;
	}

	/**
	 * 
	 * @author yangshaoping 2016年12月2日 下午5:21:45
	 * @param bufferedImage
	 *            要缩放的图片对象
	 * @param width_scale
	 *            要缩放到的宽度
	 * @param height_scale
	 *            要缩放到的高度
	 * @return 一个集合，第一个元素为宽度，第二个元素为高度
	 */
	private static List<Integer> getAutoWidthAndHeight(final BufferedImage bufferedImage, final int width_scale,
			final int height_scale) {
		List<Integer> arrayList = new ArrayList<Integer>();
		final int width = bufferedImage.getWidth();
		final int height = bufferedImage.getHeight();
		final double scale_w = getDot2Decimal(width_scale, width);

		final double scale_h = getDot2Decimal(height_scale, height);
		if (scale_w < scale_h) {
			arrayList.add(parseDoubleToInt(scale_w * width));
			arrayList.add(parseDoubleToInt(scale_w * height));
		} else {
			arrayList.add(parseDoubleToInt(scale_h * width));
			arrayList.add(parseDoubleToInt(scale_h * height));
		}
		return arrayList;
	}

	/***
	 * 返回两个数a/b的小数点后三位的表示
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double getDot2Decimal(final int decimalA, final int decimalB) {

		final BigDecimal bigDecimal_1 = new BigDecimal(decimalA);
		final BigDecimal bigDecimal_2 = new BigDecimal(decimalB);
		final BigDecimal bigDecimal_result = bigDecimal_1.divide(bigDecimal_2, new MathContext(4));
		final Double double1 = new Double(bigDecimal_result.toString());
		return double1;
	}

}