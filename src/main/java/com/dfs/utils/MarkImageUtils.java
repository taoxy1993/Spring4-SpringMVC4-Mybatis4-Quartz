package com.dfs.utils;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;


import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


/**
 * @author taoxy 2018/01/09
 */
public class MarkImageUtils {


	/**
	 * 给图片添加单个图片水印、可设置水印图片旋转角度
	 * <p>
	 * icon      水印图片路径（如：/data/taoxy/icon.png）
	 * source    没有加水印的图片路径（如：/data/taoxy/test.jpg）
	 * output    加水印后的图片路径（如：/data/taoxy/）
	 * imageName 图片名称（如：image）
	 * imageType 图片类型（如：jpg）
	 * degree    水印图片旋转角度，为null表示不旋转
	 */
	public static boolean markImageBySingleIcon(String logo, String icon, String originImage, File output) {
		String result = "添加图片水印出错";
		Integer degree = Math.random() < 0.5 ? 45 : -45;
		try {
			File file = new File(originImage);
			File ficon = new File(icon);
			File logocon = new File(logo);
			if (!file.isFile()) return false;
			BufferedImage img = ImageIO.read(new FileInputStream(file));
			BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
			BufferedImage logoImage = ImageIO.read(new FileInputStream(logocon));//logo
			BufferedImage imgIcon = ImageIO.read(new FileInputStream(ficon));//水印
			Image image = logoImage.getScaledInstance(img.getWidth(null) / 7, (img.getWidth(null) / 7) * (logoImage.getHeight()) / logoImage.getWidth(), Image.SCALE_DEFAULT);
			Graphics2D g = bi.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);//处理线段锯齿状边缘
			g.drawImage(img.getScaledInstance(img.getWidth(null), img.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
			Image con = imgIcon.getScaledInstance(img.getWidth(null) * 3 / 4, ((img.getWidth(null) * 3 / 4) * imgIcon.getHeight(null) / imgIcon.getWidth(null)), Image.SCALE_DEFAULT);
			int x = (img.getWidth(null) - con.getWidth(null)) / 2;
			int y = (img.getHeight(null) - con.getHeight(null)) / 2;
			float logoclarity = 0.7f;//透明度0到1
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, logoclarity));
			g.drawImage(image, 10, 0, null);
			float clarity = 1;
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, clarity));
			g.rotate(Math.toRadians(degree), (double) bi.getWidth() / 2, (double) bi.getHeight() / 2);//设置水印旋转
			g.drawImage(con, x, y, null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			g.dispose();
			ImageIO.write(bi, "jpg", output);
			Runtime.getRuntime().exec("chmod -R 777 " + output.getAbsolutePath());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String markImageByMoreIcon(String icon, String source, String output, String imageName, String imageType, Integer degree) {
		String result = "添加图片水印出错";
		try {
			File file = new File(source);
			File ficon = new File(icon);
			if (!file.isFile()) {
				return source + " 不是一个图片文件!";
			}
			//将icon加载到内存中
			Image ic = ImageIO.read(ficon);
			//icon高度
			int icheight = ic.getHeight(null);

			//将源图片读到内存中
			Image img = ImageIO.read(file);
			//图片宽
			int width = img.getWidth(null);
			//图片高
			int height = img.getHeight(null);
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			//创建一个指定 BufferedImage 的 Graphics2D 对象
			Graphics2D g = bi.createGraphics();
			//x,y轴默认是从0坐标开始
			int x = 0;
			int y = 0;
			//默认两张水印图片的间隔高度是水印图片的1/3
			int temp = icheight / 3;
			int space = 1;
			if (height >= icheight) {
				space = height / icheight;
				if (space >= 2) {
					temp = y = icheight / 2;
					if (space == 1 || space == 0) {
						x = 0;
						y = 0;
					}
				}
			} else {
				x = 0;
				y = 0;
			}
			//设置对线段的锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			//呈现一个图像，在绘制前进行从图像空间到用户空间的转换
			g.drawImage(img.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
			for (int i = 0; i < space; i++) {
				if (null != degree) {
					//设置水印旋转
					g.rotate(Math.toRadians(degree), (double) bi.getWidth() / 2, (double) bi.getHeight() / 2);
				}
				//水印图象的路径 水印一般为gif或者png的，这样可设置透明度
				ImageIcon imgIcon = new ImageIcon(icon);

				//得到Image对象。
				Image con = imgIcon.getImage();
				//透明度，最小值为0，最大值为1
				float clarity = 0.6f;
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, clarity));

				//表示水印图片的坐标位置(x,y)
				//g.drawImage(con, 300, 220, null);
				g.drawImage(con, x, y, null);
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
				y += (icheight + temp);
			}
			g.dispose();
			File sf = new File(output, imageName + "." + imageType);
			ImageIO.write(bi, imageType, sf); // 保存图片
			result = "图片完成添加Icon水印";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String markImageByMoreText(String source, String output, String imageName, String imageType, Color color, String word, Integer degree) {
		String result = "添加文字水印出错";
		try {
			//读取原图片信息
			File file = new File(source);
			if (!file.isFile()) {
				return file + " 不是一个图片文件!";
			}
			Image img = ImageIO.read(file);
			//图片宽
			int width = img.getWidth(null);
			//图片高
			int height = img.getHeight(null);
			//文字大小
			int size = 50;
			//加水印
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bi.createGraphics();
			g.drawImage(img, 0, 0, width, height, null);
			//设置水印字体样式
			Font font = new Font("宋体", Font.PLAIN, size);
			//根据图片的背景设置水印颜色
			g.setColor(color);
			int x = width / 3;
			int y = size;
			int space = height / size;
			for (int i = 0; i < space; i++) {
				//如果最后一个坐标的y轴比height高，直接退出
				if ((y + size) > height) {
					break;
				}
				if (null != degree) {
					//设置水印旋转
					g.rotate(Math.toRadians(degree), (double) bi.getWidth() / 2, (double) bi.getHeight() / 2);
				}
				g.setFont(font);
				//水印位置
				g.drawString(word, x, y);
				y += (2 * size);
			}
			g.dispose();
			//输出图片
			File sf = new File(output, imageName + "." + imageType);
			ImageIO.write(bi, imageType, sf); // 保存图片
			result = "图片完成添加Word水印";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String markImageBySingleText(String source, String output, String imageName, String imageType, Color color, String word, Integer degree) {
		String result = "添加文字水印出错";
		try {
			//读取原图片信息
			File file = new File(source);
			if (!file.isFile()) {
				return file + " 不是一个图片文件!";
			}
			Image img = ImageIO.read(file);
			int width = img.getWidth(null);
			int height = img.getHeight(null);
			//加水印
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bi.createGraphics();
			g.drawImage(img, 0, 0, width, height, null);
			//设置水印字体样式
			Font font = new Font("宋体", Font.PLAIN, 50);
			//根据图片的背景设置水印颜色
			g.setColor(color);
			if (null != degree) {
				//设置水印旋转
				g.rotate(Math.toRadians(degree), (double) bi.getWidth() / 2, (double) bi.getHeight() / 2);
			}
			g.setFont(font);
			int x = width / 3;
			int y = height / 2;
			//水印位置
			g.drawString(word, x, y);
			g.dispose();
			//输出图片
			File sf = new File(output, imageName + "." + imageType);
			ImageIO.write(bi, imageType, sf); // 保存图片
			result = "图片完成添加Word水印";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String markImageByMosaic(String source, String output, String imageName, String imageType, int size) {
		String result = "图片打马赛克出错";
		try {
			File file = new File(source);
			if (!file.isFile()) {
				return file + " 不是一个图片文件!";
			}
			BufferedImage img = ImageIO.read(file); // 读取该图片
			int width = img.getWidth(null); //原图片宽
			int height = img.getHeight(null); //原图片高
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			//马赛克格尺寸太大或太小
			if (width < size || height < size) {
				return "马赛克格尺寸太大";
			}
			if (size <= 0) {
				return "马赛克格尺寸太小";
			}
			int xcount = 0; //x方向绘制个数
			int ycount = 0; //y方向绘制个数
			if (width % size == 0) {
				xcount = width / size;
			} else {
				xcount = width / size + 1;
			}
			if (height % size == 0) {
				ycount = height / size;
			} else {
				ycount = height / size + 1;
			}
			int x = 0; //x坐标
			int y = 0; //y坐标
			//绘制马赛克(绘制矩形并填充颜色)
			Graphics2D g = bi.createGraphics();
			for (int i = 0; i < xcount; i++) {
				for (int j = 0; j < ycount; j++) {
					//马赛克矩形格大小
					int mwidth = size;
					int mheight = size;
					if (i == xcount - 1) {  //横向最后一个不够一个size
						mwidth = width - x;
					}
					if (j == ycount - 1) { //纵向最后一个不够一个size
						mheight = height - y;
					}
					//矩形颜色取中心像素点RGB值
					int centerX = x;
					int centerY = y;
					if (mwidth % 2 == 0) {
						centerX += mwidth / 2;
					} else {
						centerX += (mwidth - 1) / 2;
					}
					if (mheight % 2 == 0) {
						centerY += mheight / 2;
					} else {
						centerY += (mheight - 1) / 2;
					}
					Color color = new Color(img.getRGB(centerX, centerY));
					g.setColor(color);
					g.fillRect(x, y, mwidth, mheight);
					y = y + size;// 计算下一个矩形的y坐标
				}
				y = 0;// 还原y坐标
				x = x + size;// 计算x坐标
			}
			g.dispose();
			File sf = new File(output, imageName + "." + imageType);
			ImageIO.write(bi, imageType, sf); // 保存图片
			result = "打马赛克成功";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}