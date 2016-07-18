package rest_api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import rest_api.model.UserFile;
import rest_api.repository.UserFileRepository;

@RestController
@RequestMapping("/userdoc")
public class UserFileController {	
	
		@SuppressWarnings("unused")
		@Autowired
		private UserFileRepository mUserFileRepository;

		/*-------------------------------------------------------------------------*/
		//Get All Documents
		@SuppressWarnings({ "deprecation"})
		@RequestMapping(method=RequestMethod.GET, value="/getUserFiles/{userName}")
		public ResponseEntity<Map<String,Object>> getUserFiles(@PathVariable ("userName") String userName){
			
			MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient("127.0.0.1"),"springtest");
			List<UserFile> filesList = new ArrayList<UserFile>();
			MongoClient mClient = new MongoClient("192.168.0.100", 27017);
			DB mDB = mClient.getDB("springtest");
			DBCollection dbCollection = mDB.getCollection(userName+".files");
			
			DBCursor dbCursor;
			dbCursor = dbCollection.find();
			
			while(dbCursor.hasNext()){
				DBObject dbO = dbCursor.next();
				UserFile file = mongoTemplate.getConverter().read(UserFile.class, dbO);
				filesList.add(file);
			}
			
			//List<UserFile> filesList = mUserFileRepository.findAll();
			Map<String,Object> response = new HashMap<String,Object>();
			
			if(filesList.size()>0){
				response.put("totalFiles", filesList.size());
				response.put("files", filesList);				
			}
			else{
				mClient.close();
				response.put("result", filesList.size() + " match found");
			}		
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
		}
		/*-------------------------------------------------------------------------*/

		/*-------------------------------------------------------------------------
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
		-------------------------------------------------------------------------*/

		/*-------------------------------------------------------------------------*/
		// Insert a File
		@SuppressWarnings("deprecation")
		@RequestMapping(method = RequestMethod.POST, value = "/insertdoc/")
		public ResponseEntity<String> insertFile(@RequestParam("userName") String userName, @RequestParam("file") MultipartFile file) throws IOException {
			MongoClient mClient = new MongoClient("192.168.0.100", 27017);
			DB mDB = mClient.getDB("springtest");
			GridFS gridFS;
			
			
			if(userName==null || userName.isEmpty()){
				 mClient.close();
				 throw new NullPointerException("User Name Issues....");
			  }
			
			
			if (!file.isEmpty()) {
				byte[] fileChunks = file.getBytes();
				
				gridFS = new GridFS(mDB, userName);				

				GridFSInputFile gridFSIF = gridFS.createFile(fileChunks);

				gridFSIF.setContentType(file.getContentType());
				gridFSIF.setFilename(file.getOriginalFilename());
				gridFSIF.put("userName",userName);
				// gridFSIF.containsField();
				gridFSIF.save();
				mClient.close();
			} else {
				mClient.close();
				return new ResponseEntity<String>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<String>(HttpStatus.CREATED);
		}
		/*-------------------------------------------------------------------------*/

		/*-------------------------------------------------------------------------*/
		//Download a File
		@RequestMapping(value="/downloadUserFiles/{userName}/id/{id}", method=RequestMethod.GET)
		public ResponseEntity<byte[]> downloadFile(@PathVariable("userName") String userName, @PathVariable("id") String id , HttpServletResponse response) throws IOException{
			
			String msgNotFound= "Either file not found or wrong ID";			
			byte[] file = null;
			
			MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient("127.0.0.1"),"springtest");
			MongoClient mClient = new MongoClient("192.168.0.100",27017);
			@SuppressWarnings("deprecation")
			DB mDB = mClient.getDB("springtest");
			DBCollection dbCollection = mDB.getCollection(userName+".files");			
			
			DBObject dbO;
			BasicDBObject query = new BasicDBObject();
			query.append("_id", new ObjectId(id));			
			dbO = dbCollection.findOne(query);
						
			UserFile mFile = mongoTemplate.getConverter().read(UserFile.class, dbO);			
			if(mFile==null){	
				mClient.close();
				return new ResponseEntity<byte[]>(msgNotFound.getBytes(),HttpStatus.NOT_FOUND);
			}			
			
			//Query to find file with incoming ID or any other parameter.
			//DBObject mBasicDBObjectQuery = new BasicDBObject();
			//mBasicDBObjectQuery.put("_id",new ObjectId(id));
			
			GridFS gridFS = new GridFS(mDB,userName);
			GridFSDBFile mDBGridFSFile = gridFS.findOne(new ObjectId(id)); //Short and neat way to send incoming ID for searching. 
			
			if(mDBGridFSFile != null){
				InputStream inputStream = mDBGridFSFile.getInputStream();
				file = IOUtils.toByteArray(inputStream);
					
				response.setContentType(mDBGridFSFile.getContentType());
				//both contentType will work
				//response.setContentType("application/force-download");
			    
				response.setHeader("Content-Disposition", "attachment; filename=\""+ mDBGridFSFile.getFilename() +"\"");
			    response.setContentLength(new Long(mDBGridFSFile.getLength()).intValue());
		
			    //FileCopyUtils.copy(mDBGridFSFile.getInputStream(), response.getOutputStream()); //optimized as it calls flush() itself.
			    IOUtils.copyLarge(mDBGridFSFile.getInputStream(), response.getOutputStream());
			    
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
		// Delete file by ID
		@RequestMapping(method = RequestMethod.DELETE, value = "/{userName}/delete/id/{docId}")
		public ResponseEntity<Map<String, Object>> deleteByID(@PathVariable("userName") String userName, @PathVariable("docId") String id) {
			// mFileRepository.delete(id);

			MongoClient mClient = new MongoClient("192.168.0.100", 27017);
			@SuppressWarnings("deprecation")
			DB mDB = mClient.getDB("springtest");
			DBCollection dbCollection = mDB.getCollection(userName+".files");
			
			//GridFS gridFS = new GridFS(mDB,userName);
			//gridFS.remove(new ObjectId(id));			
			
			BasicDBObject query = new BasicDBObject();
			query.append("_id", new ObjectId(id));
			dbCollection.remove(query);
			
			mClient.close();		
			
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("result", "Document with " + id + " removed");

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		}
		/*-------------------------------------------------------------------------*/
}