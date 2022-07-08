package kr.net.macaronics.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

/**업로드 파일체크
 * Tika를 이용한 MimeType  파일 위조 변조 체크
 *  **/
@Slf4j
public class FileFilter {

	private static final Tika tika = new Tika();
    
    
	 /** 이미지 파일체크 */
	public static boolean validImgFile(MultipartFile multipartFile) throws Exception {		
		try {
            List<String> notValidTypeList = Arrays.asList("image/gif", "image/jpeg", "image/png", "image/bmp");
            
            String mimeType = tika.detect(multipartFile.getInputStream());
            return notValidTypeList.stream().anyMatch(notValidType -> notValidType.equalsIgnoreCase(mimeType));
            
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
	}
		

	
	
	 /** 업로드 가능한 파일 MimeType 체크 */
	public static boolean isPermisionFileMimeType(MultipartFile multipartFile) throws Exception {		
		//MIME 타입의 전체 목록 - https://developer.mozilla.org/ko/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types
		try {
            List<String> notValidTypeList = Arrays.asList(
            		"image/gif", "image/jpeg", "image/png", "image/bmp",
        			"application/pdf", "video/mp4" ,"application/zip" ,"application/x-zip-compressed" ,"application/x-tika-msoffice" ,     
        			 "video/quicktime"
            		);
            //application/x-tika-msoffice => hwp , msoffice 파일 
            //video/quicktime mp4 파일
            String mimeType = tika.detect(multipartFile.getInputStream());
            log.info("MimeType : " + mimeType);

            boolean isValid = notValidTypeList.stream().anyMatch(notValidType -> notValidType.equalsIgnoreCase(mimeType));

            return isValid;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
	}
}
