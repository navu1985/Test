package com.datacert.surveysystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SimpleTest {

	/**
	 * @param args
	 * @throws IOException
	 */
	 public static void main(String[] args) throws Exception {
	
	  ApplicationContext applicationContext = new	 ClassPathXmlApplicationContext("surveysystem-usercontext.xml");
	  PolicyService policyService= applicationContext.getBean("policyService",PolicyService.class);
	  File file = new File("C:\\Users\\parora\\Downloads\\HIbernate-refernce.pdf");
	  FileInputStream fis = new FileInputStream(file);
	  byte[] pdfFilebuffer= IOUtils.toByteArray(fis);
	  IOUtils.write(policyService.createThumbnailOfPdfFirstPage(2l, pdfFilebuffer), new FileOutputStream(new File("d://policyThumbnail.png")));
	 }

	 
	 public static Date incrementDateByDate(Date date, int noOfDays) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, noOfDays);
			return c.getTime();
		}
	 
//	public static Rectangle clip;
//
//	public static void main(String args[]) throws Exception {
//		String inputFileLocation = "E:\\SurveyPortal Enviroment For New Portal UI\\eclipse\\temp_1386680658547.jpg";
//		String outputFileLocation = "E:\\SurveyPortal Enviroment For New Portal UI\\eclipse\\croped_temp_1386680321284.jpg";
//
////		File file = new File(outputFileLocation);
//		System.out.println("Reading Original File : " + inputFileLocation);
//
//		BufferedImage originalImage = readImage(inputFileLocation);
//		
//		
//
//		/**
//		 * Image Cropping Parameters
//		 */
//		
//		int cropStartX = 29;//72
//		int cropStartY = 122;//57
//		int cropStartX2 = 146;//244
//		int cropStartY2 = 210;//212
//		int cropHeight = cropStartY2-cropStartY;
//		int cropWidth = cropStartX2-cropStartX;
//		
//		BufferedImage processedImage = cropMyImage(originalImage, cropWidth,cropHeight, cropStartX, cropStartY);
//
//		System.out.println("Writing the cropped image to: "	+ outputFileLocation);
//		writeImage(processedImage, outputFileLocation, "jpg");
//		System.out.println("...Done");
//	}
//
//	public static BufferedImage cropMyImage(BufferedImage img, int cropWidth,
//			int cropHeight, int cropStartX, int cropStartY) throws Exception {
//		BufferedImage clipped = null;
//		Dimension size = new Dimension(cropWidth, cropHeight);
//
//		createClip(img, size, cropStartX, cropStartY);
//
//		try {
//			int w = clip.width;
//			int h = clip.height;
//
//			System.out.println("Crop Width " + w);
//			System.out.println("Crop Height " + h);
//			System.out.println("Crop Location " + "(" + clip.x + "," + clip.y
//					+ ")");
//
//			clipped = img.getSubimage(clip.x, clip.y, w, h);
//
//			System.out.println("Image Cropped. New Image Dimension: "
//					+ clipped.getWidth() + "w X " + clipped.getHeight() + "h");
//		} catch (RasterFormatException rfe) {
//			System.out.println("Raster format error: " + rfe.getMessage());
//			return null;
//		}
//		return clipped;
//	}
//
//	/**
//	 * <span id="IL_AD10" class="IL_AD">This method</span> crops an original
//	 * image to the crop parameters provided.
//	 * 
//	 * If the crop rectangle lies outside the rectangle (even if partially),
//	 * adjusts the rectangle to be <span id="IL_AD5"
//	 * class="IL_AD">included</span> within the image area.
//	 * 
//	 * @param img
//	 *            = Original Image To Be Cropped
//	 * @param size
//	 *            = Crop area rectangle
//	 * @param clipX
//	 *            = <span id="IL_AD7" class="IL_AD">Starting</span> X-position
//	 *            of crop area rectangle
//	 * @param clipY
//	 *            = Strating Y-position of crop area rectangle
//	 * @throws Exception
//	 */
//	private static void createClip(BufferedImage img, Dimension size,
//			int clipX, int clipY) throws Exception {
//		/**
//		 * Some times clip area might lie outside the original image, fully or
//		 * partially. In such cases, this program will adjust the crop area to
//		 * fit within the original image.
//		 * 
//		 * isClipAreaAdjusted flas is usded to denote if there was any
//		 * adjustment made.
//		 */
//		boolean isClipAreaAdjusted = false;
//
//		/**
//		 * <span id="IL_AD6" class="IL_AD">Checking</span> for negative X
//		 * Co-ordinate
//		 **/
//		if (clipX < 0) {
//			clipX = 0;
//			isClipAreaAdjusted = true;
//		}
//		/** Checking for negative Y Co-ordinate **/
//		if (clipY < 0) {
//			clipY = 0;
//			isClipAreaAdjusted = true;
//		}
//
//		/** Checking if the clip area lies outside the rectangle **/
//		if ((size.width + clipX) <= img.getWidth()
//				&& (size.height + clipY) <= img.getHeight()) {
//
//			/**
//			 * Setting up a clip rectangle when clip area lies within the image.
//			 */
//
//			clip = new Rectangle(size);
//			clip.x = clipX;
//			clip.y = clipY;
//		} else {
//
//			/**
//			 * Checking if the width of the clip area lies outside the image. If
//			 * so, making the image width boundary as the clip width.
//			 */
//			if ((size.width + clipX) > img.getWidth())
//				size.width = img.getWidth() - clipX;
//
//			/**
//			 * Checking if the height of the clip area lies outside the image.
//			 * If so, making the image height boundary as the clip height.
//			 */
//			if ((size.height + clipY) > img.getHeight())
//				size.height = img.getHeight() - clipY;
//
//			/** Setting up the clip are based on our clip area size adjustment **/
//			clip = new Rectangle(size);
//			clip.x = clipX;
//			clip.y = clipY;
//
//			isClipAreaAdjusted = true;
//
//		}
//		if (isClipAreaAdjusted)
//			System.out.println("Crop Area Lied Outside The Image."
//					+ " Adjusted The Clip Rectangle\n");
//	}
//
//	/**
//	 * This method reads an image from <span id="IL_AD2" class="IL_AD">the
//	 * file</span>
//	 * 
//	 * @param fileLocation
//	 *            -- > eg. "C:/testImage.jpg"
//	 * @return BufferedImage of the file read
//	 */
//	public static BufferedImage readImage(String fileLocation) {
//		BufferedImage img = null;
//		try {
//			img = ImageIO.read(new File(fileLocation));
//			System.out.println("Image Read. Image Dimension: " + img.getWidth()
//					+ "w X " + img.getHeight() + "h");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return img;
//	}
//
//	/**
//	 * This method writes a buffered image to a file
//	 * 
//	 * @param img
//	 *            -- > BufferedImage
//	 * @param fileLocation
//	 *            --> e.g. "C:/testImage.jpg"
//	 * @param extension
//	 *            --> e.g. "jpg","gif","png"
//	 */
//	public static void writeImage(BufferedImage img, String fileLocation,
//			String extension) {
//		try {
//			BufferedImage bi = img;
//			File outputfile = new File(fileLocation);
//			ImageIO.write(bi, extension, outputfile);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/*
//	 * SANJAAL CORPS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE
//	 * SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT
//	 * LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
//	 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SANJAAL CORPS SHALL NOT BE
//	 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING,
//	 * MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS <span id="IL_AD12"
//	 * class="IL_AD">DERIVATIVES</span>.
//	 * 
//	 * THIS SOFTWARE IS NOT DESIGNED OR INTENDED FOR USE OR RESALE AS ON-LINE
//	 * CONTROL EQUIPMENT IN HAZARDOUS ENVIRONMENTS REQUIRING FAIL-SAFE
//	 * PERFORMANCE, SUCH AS IN THE OPERATION OF NUCLEAR FACILITIES, AIRCRAFT
//	 * NAVIGATION OR <span id="IL_AD3" class="IL_AD">COMMUNICATION
//	 * SYSTEMS</span>, AIR <span id="IL_AD4" class="IL_AD">TRAFFIC</span>
//	 * CONTROL, DIRECT LIFE SUPPORT MACHINES, OR WEAPONS SYSTEMS, IN WHICH THE
//	 * FAILURE OF THE SOFTWARE COULD LEAD DIRECTLY TO DEATH, PERSONAL INJURY, OR
//	 * SEVERE PHYSICAL OR ENVIRONMENTAL DAMAGE ("HIGH RISK ACTIVITIES"). SANJAAL
//	 * CORPS SPECIFICALLY DISCLAIMS ANY EXPRESS OR IMPLIED WARRANTY OF FITNESS
//	 * FOR HIGH RISK ACTIVITIES.
//	 */

}
