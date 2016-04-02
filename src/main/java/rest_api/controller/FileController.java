package rest_api.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

import rest_api.model.File;
import rest_api.repository.FileRepository;

@RestController
@RequestMapping("/document")
public class FileController {

	@Autowired
	private FileRepository mFileRepository;
	
	/*-------------------------------------------------------------------------*/
	//Get All Documents
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
	/*-------------------------------------------------------------------------*/
	
	/*-------------------------------------------------------------------------*/
	////Get Document by ID
	@RequestMapping(method = RequestMethod.GET, value="/id/{id}")
	public ResponseEntity<Map<String, Object>> getFileByID(@PathVariable ("id") String id){
		File mFile = mFileRepository.findOne(id);
		Map<String,Object> response = new HashMap<String, Object>();
		
		if(mFile!=null)	response.put("file", mFile);
		else {
			response.put("result", "no file found with id " + id);
		}
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	/*-------------------------------------------------------------------------*/
 
	/*-------------------------------------------------------------------------*/
	//Insert a File
	@RequestMapping(method=RequestMethod.POST, value = "/insert/")
	public ResponseEntity<Void> insertFile(@RequestParam("file") MultipartFile file) throws IOException{
		MongoClient mClient = new MongoClient("192.168.0.100",27017);
		DB mDB = mClient.getDB("springtest");
		
		byte[] fileChunks = file.getBytes();
		
		GridFS gridFS = new GridFS(mDB,"file");
		GridFSInputFile gridFSIF = gridFS.createFile(fileChunks);
		gridFSIF.setFilename(file.getOriginalFilename());
		gridFSIF.save();
		
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}	
	/*-------------------------------------------------------------------------*/
	
	/*-------------------------------------------------------------------------*/
	//Delete file by ID
	@RequestMapping(method= RequestMethod.DELETE , value = "/delete/id/{id}")
	public ResponseEntity<Map<String, Object>> deleteByID(@PathVariable("id") String id){
		mFileRepository.delete(id);
		
		Map<String, Object> response = new HashMap<String,Object>();
		response.put("result","Document with "+id+ " removed");
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
		
	}
	/*-------------------------------------------------------------------------*/
	
}
