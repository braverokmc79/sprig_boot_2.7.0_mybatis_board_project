package kr.net.macaronics.mvc.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.net.macaronics.configuration.GlobalConfig;
import kr.net.macaronics.configuration.exception.BaseException;
import kr.net.macaronics.configuration.http.BaseResponseCode;
import kr.net.macaronics.mvc.domain.dto.BoardDTO;
import kr.net.macaronics.mvc.domain.dto.BoardInsertDTO;
import kr.net.macaronics.mvc.domain.dto.UploadFileDTO;
import kr.net.macaronics.mvc.domain.dto.UploadFileInsertDTO;
import kr.net.macaronics.mvc.domain.enums.BoardTypeInsert;
import kr.net.macaronics.mvc.domain.enums.ThumbnailType;
import kr.net.macaronics.mvc.repository.UploadFileRepository;
import kr.net.macaronics.utils.FileFilter;
import kr.net.macaronics.utils.pagination2.MysqlPageMaker;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

/** 업로드파일 Service */
@Service
@Slf4j
public class UploadFileService {

	@Autowired
	private UploadFileRepository uploadFileRepository;
	
	@Autowired
	private GlobalConfig config;
	
	/** 등록처리 */
	public int save(UploadFileInsertDTO uploadFileInsertDTO) throws Exception{
		return uploadFileRepository.save(uploadFileInsertDTO);
	}

	public UploadFileDTO get(int uploadFileSeq) throws Exception {
		return uploadFileRepository.get(uploadFileSeq);		
	}

	
	public int fileSave(MultipartFile multipartFile, BoardInsertDTO boardInsertDTO) throws Exception {
		if(multipartFile==null || multipartFile.isEmpty()) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL.name());
		}

		Integer boardSeq=null; //게시판 업로드일 파일 경우 - 게시판 테이블 PK 
		BoardTypeInsert boardType=BoardTypeInsert.NONE; //게시판 타입		
		if(boardInsertDTO!=null) {
			boardSeq=boardInsertDTO.getBoardSeq();   
			boardType=boardInsertDTO.getBoardType(); 		
		}
		
		
		//1.업로드 가능한지 파일인지 체크 			
		if(!FileFilter.isPermisionFileMimeType(multipartFile)) {
			throw new BaseException("업로드할 수 없는 파일입니다.");
		};

		
		//2.날짜폴더를 추가 - 이미지가 아닌파일은 files 디렉토리에 저장
		String currentDate=new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		String uploadFilePath=config.getUploadFilePath()+"files/"+currentDate+"/";
	
		//3.이미지파일 체크  - 이미지파일은 images 디렉토리에 저장
		boolean isImage=false;
		if(FileFilter.validImgFile(multipartFile)) {
			isImage=true;
			uploadFilePath=config.getUploadFilePath()+"images/"+currentDate+"/";
		}
					
		
		//4.랜덤 파일명 생성 및 디렉토리 생성 
		String prefix=multipartFile.getOriginalFilename().
				substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1, multipartFile.getOriginalFilename().length());
		String filename=UUID.randomUUID().toString()+"."+prefix;
		File folder =new File(uploadFilePath);		
		if(!folder.isDirectory()) {
			folder.mkdirs();
		}

		
		//5.URL 주소에서 보여줄 파일경로
		String pathname=uploadFilePath +filename;
		String resourcePathname =config.getUploadResourcePath()+(isImage?"images/":"files/") +currentDate+"/"+filename;
		File dest=new File(pathname);
		
		
		//6.파일 생성
		log.info("dest  :  {}", dest);		
		multipartFile.transferTo(dest);		
		
		String thumbnailPathname=null; //썸네일 전체경로
		String thumbnailResourcePathname=null; //썸네일 리소스파일경로
		
		//7.업로드 파일일 이미지 일경우 썸네일 생성
		if(isImage){
			thumbnailPathname=pathname.replace(".", "_"+ThumbnailType.WEB_MAIN.width()+"_"+ThumbnailType.WEB_MAIN.height()+".");			
			File thumbanilFile=new File(thumbnailPathname);
			if(!thumbanilFile.isFile()) {
				Thumbnails.of(new File(pathname))
					.size(ThumbnailType.WEB_MAIN.width(), ThumbnailType.WEB_MAIN.height())
					.toFile(new File(thumbnailPathname));	
				
				thumbnailResourcePathname =resourcePathname.replace(".",  "_"+ThumbnailType.WEB_MAIN.width()+"_"+ThumbnailType.WEB_MAIN.height()+".");
			}				
		}
		
		//9.UploadFileInsertDTO 객체에 저장
		UploadFileInsertDTO uploadFileInsertDTO=UploadFileInsertDTO.builder()
		.boardSeq(boardSeq)
		.boardType(boardType)	
		.contentType(multipartFile.getContentType()) 	//컨텐츠 종류
		.originalFilename(multipartFile.getOriginalFilename()) //원본파일명
		.filename(filename) //저장파일명
		.pathname(pathname) //실제파일 저장경로
		.size((int)multipartFile.getSize()) //파일크기
		.resourcePathname(resourcePathname) 	//static resource 접근 경로
		.thumbnailPathname(thumbnailPathname)
		.thumbnailResourcePathname(thumbnailResourcePathname)
		.build();

		
		uploadFileRepository.save(uploadFileInsertDTO);
		
		//9.파일업로드 된 후 DB에 저장후 반화
		return uploadFileInsertDTO.getUploadFileSeq();
	}

	
	
	public int getTotalCount(Map<String, Object> map) throws Exception {
		return uploadFileRepository.getTotalCount(map);
	}

	public List<UploadFileDTO> fileList(Map<String, Object> map) throws Exception {
		return uploadFileRepository.fileList(map);
	}

	public List<UploadFileDTO> bordPkFileList(Map<String, Object> map) throws Exception {
		return uploadFileRepository.bordPkFileList(map);
	}

	
	public void fileDelete(int uploadFileSeq)  throws Exception {
		UploadFileDTO uploadFileDTO=uploadFileRepository.get(uploadFileSeq);	
		if(uploadFileDTO==null) {
			throw new BaseException("해당 파일이 존재하지 않습니다.");
		}
				
		File file=new File(uploadFileDTO.getPathname());
		if(file.exists())file.delete();
		else throw new BaseException("해당 파일이 존재하지 않습니다.");
		
		//썸네일 삭제
		if(!StringUtils.isEmpty(uploadFileDTO.getThumbnailPathname())) {
			File thumbnailFile=new File(uploadFileDTO.getThumbnailPathname());
			if(thumbnailFile.exists()) thumbnailFile.delete();
			else throw new BaseException("해당 파일이 존재하지 않습니다."); 
		}
		
		//DB에서 삭제처리
		uploadFileRepository.fileDelete(uploadFileSeq);
	}

	public List<UploadFileDTO> multiFileSave(List<MultipartFile> multipartFile, BoardInsertDTO boardInsertDTO) throws Exception {
		
		List<Integer> uploadFileSeq=new ArrayList<Integer>();
		for(MultipartFile multiFile : multipartFile) {
			uploadFileSeq.add(fileSave(multiFile, null));
		}
		return	uploadFileRepository.fileSelectList(uploadFileSeq);
		
	}
	
	
	
}
