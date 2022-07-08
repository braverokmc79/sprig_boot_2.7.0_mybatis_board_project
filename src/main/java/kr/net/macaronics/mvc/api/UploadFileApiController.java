package kr.net.macaronics.mvc.api;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Delete;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kr.net.macaronics.configuration.GlobalConfig;
import kr.net.macaronics.configuration.exception.BaseException;
import kr.net.macaronics.configuration.http.BaseResponse;
import kr.net.macaronics.configuration.http.BaseResponseCode;
import kr.net.macaronics.mvc.domain.dto.UploadFileDTO;
import kr.net.macaronics.mvc.domain.dto.UploadFileInsertDTO;
import kr.net.macaronics.mvc.domain.enums.ThumbnailType;
import kr.net.macaronics.mvc.service.UploadFileService;
import kr.net.macaronics.utils.DownloadView;
import kr.net.macaronics.utils.pagination2.MysqlPageMaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@RestController
@RequestMapping("/api/file")
@Api(tags="파일 업로드 API")
@RequiredArgsConstructor
public class UploadFileApiController {

		
	private final GlobalConfig config;
	
	private final UploadFileService uploadFileService;
	
	
	@PostMapping("/save")
	@ApiOperation(value="테스트-파일업로드", notes = "파일업로드 샘플1")
	public BaseResponse<Boolean> save(@RequestParam("uploadFile") MultipartFile multipartFile) throws Exception {
		if(multipartFile==null || multipartFile.isEmpty()) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL.name());
		}
		
		//날짜폴더를 추가
		String currentDate=new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		log.debug("config : {} ", config);		
		String uploadFilePath=config.getUploadFilePath()+currentDate+"/";
		log.info("uploadFilePath  :  {} " , uploadFilePath);
				
		
		String prefix=multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1, multipartFile.getOriginalFilename().length());
		String filename=UUID.randomUUID().toString()+"."+prefix;
		File folder =new File(uploadFilePath);		
		if(!folder.isDirectory()) {
			folder.mkdirs();
		}

		String pathname=uploadFilePath +filename;
		String resourcePathname =config.getUploadResourcePath()+currentDate+"/"+filename;
		File dest=new File(pathname);
				
		log.info("dest  :  {}", dest);		
		multipartFile.transferTo(dest);
		
		//파일업로드 된 후 DB에 저장
		UploadFileInsertDTO uploadFileDTO=UploadFileInsertDTO.builder()
		.contentType(multipartFile.getContentType()) 	//컨텐츠 종류
		.originalFilename(multipartFile.getOriginalFilename()) //원본파일명
		.filename(filename) //저장파일명
		.pathname(pathname) //실제파일 저장경로
		.size((int)multipartFile.getSize()) //파일크기
		.resourcePathname(resourcePathname) 	//static resource 접근 경로
		.build();
		
		uploadFileService.save(uploadFileDTO);		
		return new BaseResponse<Boolean>(true);
	}
	
	
	/**
	 * 업로드된 파일 썸네일 만들기 공통 throws Exception
	 * http://localhost:8080/api/file/thumbnail/make/1/WEB_MAIN
	 * */
	@GetMapping("/thumbnail/make/{uploadFileSeq}/{thumbnailType}")
	@ApiOperation(value="테스트-업로드된 파일 썸네일 만들기", notes = "파일업로드 샘플2")
	public BaseResponse<String> thumb(@PathVariable int uploadFileSeq,
			@PathVariable ThumbnailType thumbnailType
			) throws Exception{
		
		UploadFileDTO uploadFileDTO=uploadFileService.get(uploadFileSeq);		
		String pathname=uploadFileDTO.getPathname();		
				
		String thumbnailPathname=uploadFileDTO.getPathname().replace(".", thumbnailType.width()+"_"+thumbnailType.height()+".");
		
		File thumbanilFile=new File(thumbnailPathname);
		if(!thumbanilFile.isFile()) {
			Thumbnails.of(new File(pathname))
				.size(thumbnailType.width(), thumbnailType.height())
				.toFile(new File(thumbnailPathname));			
		}	
		
		return new BaseResponse<String>(uploadFileDTO.getResourcePathname().replace(".", thumbnailType.width()+"_"+thumbnailType.height()+"."));
	}
	
	
	/**thumbnailator 라이브러리를 통한 업로드된 썸네일 이미지 출력
	 * http://localhost:8080/api/file/thumbnail/output/1/WIDTH_200
	 * */
	@GetMapping("/thumbnail/output/{uploadFileSeq}/{thumbnailType}")
	@ApiOperation(value="테스트-thumbnailator 라이브러리를 통한 업로드된 썸네일 이미지 출력", notes = "파일업로드 샘플3")
	public void thumbnailOutput(@PathVariable int uploadFileSeq,
			@PathVariable ThumbnailType thumbnailType,
			HttpServletResponse response
			) throws Exception{
		
		UploadFileDTO uploadFileDTO=uploadFileService.get(uploadFileSeq);		
		String pathname=uploadFileDTO.getPathname();		
				
		String thumbanilPathname=uploadFileDTO.getPathname().replace(".", thumbnailType.width()+"_"+thumbnailType.height()+".");
		
		File thumbanilFile=new File(thumbanilPathname);
		if(!thumbanilFile.isFile()) {
			Thumbnails.of(new File(pathname))
				.size(thumbnailType.width(), thumbnailType.height())
				.toFile(new File(thumbanilPathname));			
		}	
		
		
		response.setContentType(uploadFileDTO.getContentType());
		FileCopyUtils.copy(new FileInputStream(thumbanilFile), response.getOutputStream());		
	}
	
	
	/*****************************************************************************************************************/
	/*****************************************************************************************************************/
	/*****************************************************************************************************************/
	
	
	
	/**	 파일업로드 - 이미지일 경우 썸네일등록 */
	@PostMapping("/fileSave")
	@ApiOperation(value="파일 등록 - 이미지일 경우 썸네일등록", notes = "서비스에서 작업 -  MimeType  파일 위조 변조 체크")
	public BaseResponse<UploadFileDTO> fileSave(@RequestParam("uploadFile") MultipartFile multipartFile) throws Exception {	
		int  uploadFileSeq=uploadFileService.fileSave(multipartFile, null);
		return new BaseResponse<UploadFileDTO>(uploadFileService.get(uploadFileSeq));
	}
	
	
	/**	멀티파일업로드 - 이미지일 경우 썸네일등록 */
	@PostMapping("/multiFileSave")
	@ApiOperation(value="멀티파일 등록 - 이미지일 경우 썸네일등록", notes = "멀티파일 등록")
	public  BaseResponse<List<UploadFileDTO>> multiFileSave(@RequestParam("uploadFile") List<MultipartFile> multipartFile) throws Exception {		
		List<UploadFileDTO> uploadFileDTOList=uploadFileService.multiFileSave(multipartFile, null);		
		return new  BaseResponse<List<UploadFileDTO>>(uploadFileDTOList);
	}
	
	
	
	/** 전체 파일목록 불러오기 
	 * http://localhost:8080/api/file/fileList
	 * */
	@GetMapping({"/fileList"})
	@ApiOperation(value="전체 파일목록 불러오기", notes="전체 파일목록 불러오기 ")
	public BaseResponse<List<UploadFileDTO>> fileList(
			@ApiParam MysqlPageMaker pageMaker
			) throws Exception{
		
		 Map<String, Object> map=new HashMap<>();		
		 map.put("pageMaker", pageMaker);
		 map.put("boardSeq", "");  
		 pageMaker.setTotalCount(uploadFileService.getTotalCount(map));
	    
	    return new BaseResponse<List<UploadFileDTO>>(uploadFileService.fileList(map), pageMaker);
	}

	
	/** 게시판 pk 파일목록 불러오기
	 * http://localhost:8080/api/file/boarddPkFileList/1
	 *  */
	@GetMapping({"/boarddPkFileList/{boardSeq}"})
	@ApiOperation(value="게시판 번호에 따른 파일 목록 불러오기", notes="boardSeq 파일목록 불러오기 " )
	public BaseResponse<List<UploadFileDTO>> boarddPkFileList(
			@ApiParam MysqlPageMaker pageMaker,
			@ApiParam @PathVariable int boardSeq
			) throws Exception{
		Map<String, Object> map=new HashMap<>();		
		map.put("pageMaker", pageMaker);
		map.put("boardSeq", boardSeq);     		
		
		int totalCount =uploadFileService.getTotalCount(map);
	    pageMaker.setTotalCount(totalCount);
	     		
	    return new BaseResponse<List<UploadFileDTO>>(uploadFileService.bordPkFileList(map), pageMaker);
	}

	
	
	/** 게시판 boardType 별 파일목록 불러오기 *  */
	@GetMapping({"/boardTypeFileList/{boardType}"})
	@ApiOperation(value="게시판 타입별 파일 목록 불러오기", notes="boardType 별 파일목록 불러오기 ")
	public BaseResponse<List<UploadFileDTO>> boardTypeFileList(
			@ApiParam MysqlPageMaker pageMaker,
			@ApiParam @PathVariable String boardType
			) throws Exception{
		Map<String, Object> map=new HashMap<>();		
		map.put("pageMaker", pageMaker);
		map.put("boardSeq", "");
		map.put("searchType", boardType);
		
		log.info("map  {} ", map.toString());
		
		int totalCount =uploadFileService.getTotalCount(map);
	    pageMaker.setTotalCount(totalCount);	     		
	    return new BaseResponse<List<UploadFileDTO>>(uploadFileService.bordPkFileList(map), pageMaker);
	}

	
	


	/**	 파일정보가져오기  */
	@GetMapping("/getFileInfo/{uploadFileSeq}")
	@ApiOperation(value="파일 정보가져오기", notes = "파일 다운로드")
	public BaseResponse<UploadFileDTO> getFileInfo(@PathVariable int uploadFileSeq , Model model) throws Exception {			
			UploadFileDTO uploadFileDTO=uploadFileService.get(uploadFileSeq);	
			if(uploadFileDTO==null) {
				throw new BaseException("해당 파일이 존재하지 않습니다.");
			}						
		return new BaseResponse<UploadFileDTO>(uploadFileDTO);			
	}
	
	
	
	/**	 파일 다운로드  */	
	@GetMapping(value = "/fileDownload/{uploadFileSeq}")
	@ApiOperation(value="파일 다운로드", notes = "파일 다운로드")
	public DownloadView fileDownload(@PathVariable int uploadFileSeq , Model model) throws Exception {			
			UploadFileDTO uploadFileDTO=uploadFileService.get(uploadFileSeq);	
			if(uploadFileDTO==null) {
				throw new BaseException("해당 파일이 존재하지 않습니다.");
			}
			
			File file = new File(uploadFileDTO.getPathname());
			model.addAttribute("downloadFile", file);
			model.addAttribute("orignalName", uploadFileDTO.getOriginalFilename());
			DownloadView downloadView=new DownloadView();
			return downloadView;			
	}
	
	
	
	/**	 파일 삭제
	 * http://localhost:8080/api/file/fileDelate/1
	 *  */	
	@DeleteMapping("/fileDelate/{uploadFileSeq}")
	@ApiOperation(value="파일 삭제", notes = "파일 삭제")
	public BaseResponse<String> fileDelete(@PathVariable int uploadFileSeq) throws Exception{
		uploadFileService.fileDelete(uploadFileSeq);				
		return new BaseResponse<String>("success",  BaseResponseCode.SUCCESS, "삭제 처리되었습니다.");		
	}
	

	
	
}
