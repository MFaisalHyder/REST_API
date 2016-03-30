package rest_api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rest_api.model.File;
import rest_api.repository.FileRepository;

@RestController
@RequestMapping("/document")
public class FileController {

	@Autowired
	private FileRepository mFileRepository;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> getAllFiles(){
		List<File> filesList = mFileRepository.findAll();
		Map<String,Object> response = new HashMap<String,Object>();
		
		if(filesList.size()>0){
			response.put("totalFiles", filesList.size());
			response.put("files", filesList);
		}
		else{
			response.put("result", filesList.size() + " match found");
		}		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
 
}
