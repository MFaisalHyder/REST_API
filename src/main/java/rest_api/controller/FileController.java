package rest_api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
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
	@SuppressWarnings("deprecation")
	@RequestMapping(method=RequestMethod.POST, value = "/insert/")		
		public ResponseEntity<String> insertFile(@RequestParam("file") MultipartFile file) throws IOException{
		MongoClient mClient = new MongoClient("192.168.0.100",27017);
		DB mDB = mClient.getDB("springtest");
		if(!file.isEmpty()){
		byte[] fileChunks = file.getBytes();
		
		GridFS gridFS = new GridFS(mDB,"file");
		GridFSInputFile gridFSIF = gridFS.createFile(fileChunks);
		
		gridFSIF.setContentType(file.getContentType());
		gridFSIF.setFilename(file.getOriginalFilename());
		//gridFSIF.containsField();
		gridFSIF.save();	
		mClient.close();
		}else{
			mClient.close();
			return new ResponseEntity<String>("Error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}	
	/*-------------------------------------------------------------------------*/

	
	/*-------------------------------------------------------------------------*/
	//Download a File
	@RequestMapping(value="/download/id/{id}", method=RequestMethod.GET)
	public ResponseEntity<byte[]> downloadFile(@PathVariable ("id") String id, HttpServletResponse response) throws IOException{
		
		String msgNotFound= "Either file not found or wrong ID";	
		byte[] file = null;
		File mFile = mFileRepository.findOne(id);
		
		if(mFile==null){			
			return new ResponseEntity<byte[]>(msgNotFound.getBytes(),HttpStatus.NOT_FOUND); 
		}
		
		MongoClient mClient = new MongoClient("192.168.0.100",27017);
		@SuppressWarnings("deprecation")
		DB mDB = mClient.getDB("springtest");
		
		//Query to find file with incoming ID or any other parameter.
		//DBObject mBasicDBObjectQuery = new BasicDBObject();
		//mBasicDBObjectQuery.put("_id",new ObjectId(id));
		
		GridFS gridFS = new GridFS(mDB,"file");
		GridFSDBFile mDBGridFSFile = gridFS.findOne(new ObjectId(id)); //Short and neat way to send incoming ID for searching. 
		
		if(mDBGridFSFile != null){
			InputStream inputStream = mDBGridFSFile.getInputStream();
			file = IOUtils.toByteArray(inputStream);
				
			response.setContentType(mDBGridFSFile.getContentType());
			//both contentType will work
			//response.setContentType("application/force-download");
		    
			response.setHeader("Content-Disposition", "attachment; filename=\""+ mDBGridFSFile.getFilename() +"\"");
		    response.setContentLength(new Long(mDBGridFSFile.getLength()).intValue());

		    FileCopyUtils.copy(mDBGridFSFile.getInputStream(), response.getOutputStream()); //optimized as it calls flush() itself.
		    //IOUtils.copyLarge(mDBGridFSFile.getInputStream(), response.getOutputStream());
		    
		    inputStream.close();
			mClient.close();
			
			return new ResponseEntity<byte[]>(file,HttpStatus.OK); 
			
		}else{
			mClient.close();
			return new ResponseEntity<byte[]>(msgNotFound.getBytes(),HttpStatus.NOT_FOUND);
		}			
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
