package kr.net.macaronics.mvc.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.net.macaronics.mvc.domain.dto.UploadFileDTO;
import kr.net.macaronics.mvc.domain.dto.UploadFileInsertDTO;

/** 업로드 파일 Repository */
@Repository
public interface UploadFileRepository {

	public int save(UploadFileInsertDTO uploadFileInsertDTO) throws Exception;

	public UploadFileDTO get(int uploadFileSeq) throws Exception;

	public int getTotalCount(Map<String, Object> map) throws Exception;

	public List<UploadFileDTO> fileList(Map<String, Object> map) throws Exception;

	public List<UploadFileDTO> bordPkFileList(Map<String, Object> map) throws Exception;

	public void fileDelete(int uploadFileSeq) throws Exception;

	public List<UploadFileDTO> fileSelectList(List<Integer> uploadFileSeq) throws Exception;
		
	
}
