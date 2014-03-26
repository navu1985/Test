package com.datacert.surveysystem.db;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.datacert.surveysystem.UserProfileService;
import com.datacert.surveysystem.db.dao.UserDao;
import com.datacert.surveysystem.db.dao.UserProfileDao;
import com.datacert.surveysystem.db.domain.UserDescriptor;
import com.datacert.surveysystem.db.util.ThumbnailCreation;
import com.datacert.surveysystem.dto.DaoCoreConstant;
import com.datacert.surveysystem.dto.User;
import com.datacert.surveysystem.dto.UserProfile;

@Service("userProfileService")
public class UserProfileServiceImpl implements UserProfileService {

	@Resource
	private UserProfileDao userProfileDao;
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private ThumbnailCreation thumbnailCreation;

	@Override
	public UserProfile getUserProfileDescriptor(Long profileId) {
		return (UserProfile) userProfileDao.getUserProfileDescriptor(profileId);
	}
	
	@Override
	public Boolean getUserProfileDescriptor(Long profileId,OutputStream out) {
		return userProfileDao.getUserProfileDescriptor(profileId,out);
	}

	@Override
	public void saveProfileImage(File profileImage, Long userId)
			throws IOException {
		FileInputStream fileInputStream =null;
		try{
			fileInputStream = new FileInputStream(profileImage);
			userProfileDao.saveProfileImage(IOUtils.toByteArray(fileInputStream), userId);
		}finally{
			fileInputStream.close();
			profileImage.delete();
		}
	}

	@Override
	public UserProfile getUserProfileInfo(Long userId) {
		return (UserProfile) userProfileDao.getUserProfileInfo(userId);
	}

	@Override
	public UserProfile loadUserProfileDescriptorByUserId(Long userId) {
		return (UserProfile) userProfileDao.loadUserProfileDescriptorByUserId(userId);
	}

	@Override
	public void loginedOnce(User user) {
		if(user instanceof UserDescriptor){
			UserDescriptor UserDescriptor = (UserDescriptor)user;
			UserDescriptor.setFirstLogin(Boolean.FALSE);
			userDao.saveUser(UserDescriptor);	
		}
	}

	@Override
	public File resizeImage(MultipartFile profileImage,String fileName) throws IOException {
        Image img = null;
        BufferedImage tempJPG = null;
        File newFileJPG = null;
        File tmpFile =null;
        try{
          if(!new File(DaoCoreConstant.TEMP_DIR).isDirectory())
            new File(DaoCoreConstant.TEMP_DIR).mkdir();
          tmpFile = new File(DaoCoreConstant.TEMP_DIR + System.getProperty("file.separator") + fileName);
          profileImage.transferTo(tmpFile);
          img = ImageIO.read(tmpFile);
          tempJPG = resizeImage(img, 500, 400);
          if(!new File(DaoCoreConstant.TEMP_DIR).isDirectory())
            new File(DaoCoreConstant.TEMP_DIR).mkdir();
          newFileJPG = new File(DaoCoreConstant.TEMP_DIR+"/temp_"+fileName);
          ImageIO.write(tempJPG, "jpg", newFileJPG);  
        }finally{
          tmpFile.delete();
        }
		return newFileJPG;
	}
	
	public BufferedImage resizeImage(final Image image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        //below three lines are for RenderingHints for better image quality at cost of higher processing time
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return bufferedImage;
    }

	@Override
	public File cropImage(File file, String xStart, String yStart, String xEnd,	String yEnd) throws Exception {
		BufferedImage originalImage = ImageIO.read(file);
		int cropStartX = Integer.valueOf(xStart);
		int cropStartY = Integer.valueOf(yStart);
		int cropStartX2 = Integer.valueOf(xEnd);
		int cropStartY2 = Integer.valueOf(yEnd);
		int cropWidth = cropStartX2-cropStartX;
		int cropHeight = cropStartY2-cropStartY;
		BufferedImage processedImage = thumbnailCreation.cropMyImage(originalImage, cropWidth,cropHeight, cropStartX, cropStartY);
		ImageIO.write(processedImage, "jpg", file);
		return file;
	}
}
