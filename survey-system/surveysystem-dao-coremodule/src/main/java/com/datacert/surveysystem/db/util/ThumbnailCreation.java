package com.datacert.surveysystem.db.util;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ThumbnailCreation {

  private Rectangle clip;
  private static Logger logger = LoggerFactory.getLogger(ThumbnailCreation.class);

  public BufferedImage cropMyImage(BufferedImage img, int cropWidth, int cropHeight, int cropStartX, int cropStartY)
		  throws Exception {
	BufferedImage clipped = null;
	Dimension size = new Dimension(cropWidth, cropHeight);
	createClip(img, size, cropStartX, cropStartY);

	try {
	  int w = clip.width;
	  int h = clip.height;
	  if(w==0 || h==0)
		return img; 
	  clipped = img.getSubimage(clip.x, clip.y, w, h);
	} catch (RasterFormatException rfe) {
	  rfe.printStackTrace();
	  logger.error(rfe.toString());
	  return null;
	}
	return clipped;
  }

  private void createClip(BufferedImage img, Dimension size, int clipX, int clipY) throws Exception {

	if (clipX < 0) {
	  clipX = 0;
	}
	if (clipY < 0) {
	  clipY = 0;
	}

	if ((size.width + clipX) <= img.getWidth() && (size.height + clipY) <= img.getHeight()) {

	  clip = new Rectangle(size);
	  clip.x = clipX;
	  clip.y = clipY;
	} else {
	  if ((size.width + clipX) > img.getWidth())
		size.width = img.getWidth() - clipX;

	  if ((size.height + clipY) > img.getHeight())
		size.height = img.getHeight() - clipY;

	  clip = new Rectangle(size);
	  clip.x = clipX;
	  clip.y = clipY;

	}
  }

  public BufferedImage readImage(String fileLocation) {
	BufferedImage img = null;
	try {
	  img = ImageIO.read(new File(fileLocation));
	} catch (IOException e) {
	  e.printStackTrace();
	  logger.error(e.toString());
	}
	return img;
  }

  public void writeImage(BufferedImage img, String fileLocation, String extension) {
	try {
	  BufferedImage bi = img;
	  File outputfile = new File(fileLocation);
	  ImageIO.write(bi, extension, outputfile);
	} catch (IOException e) {
	  e.printStackTrace();
	  logger.error(e.toString());
	}
  }
}
