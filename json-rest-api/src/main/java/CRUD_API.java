import com.mongodb.MongoException;
import java.net.UnknownHostException;
import com.mongodb.*;
import com.mongodb.util.*;
import java.util.List;


class CRUD_API {


	public String selectDocument(String idString, String collectionName) {
		try {
			int id = Integer.parseInt(idString);
	     	Mongo mongo = new Mongo("localhost", 27017);
			DB db = mongo.getDB("shop");
			DBCollection collection = db.getCollection(collectionName);
			BasicDBObject whereIdQuery = new BasicDBObject();
			whereIdQuery.put("id",id);
			DBCursor cursor = collection.find(whereIdQuery);
			if (cursor.hasNext()) {
				return cursor.next().toString();
			} else {
				return "{ \"errormsg\": \"document doesnt exist\"}";
			}
		} catch (UnknownHostException e) {
			return e.getMessage();
		} catch (MongoException e) {
			return e.getMessage();
		}
	}

	public String selectAllDocuments(String collectionName) {
		try {
			Mongo mongo = new Mongo("localhost", 27017);
			DB db = mongo.getDB("shop");
			DBCollection collection = db.getCollection(collectionName);
		//	DBCursor cursor = collection.find();
			List<DBObject> documents = collection.find().toArray();
			return documents.toString();
		} catch (UnknownHostException e) {
			return e.getMessage();
		} catch (MongoException e) {
			return e.getMessage();
		}
	}

	public String insertNewDocument(String inputJsonString, String collectionName) {
		try {
			Mongo mongo = new Mongo("localhost", 27017);
			DB db = mongo.getDB("shop");
			DBCollection collection = db.getCollection(collectionName);
			DBObject newdocument = (DBObject) JSON.parse(inputJsonString);
			collection.insert(newdocument);
			return "{ \"result\": \"document has been added succesfully\"}";
		} catch (UnknownHostException e) {
			return e.getMessage();
		} catch (MongoException e) {
			return e.getMessage();
		}
	}


	public String updateDocument(String updateData, String idString, String collectionName) {
		try {
			Mongo mongo = new Mongo("localhost", 27017);
			DB db = mongo.getDB("shop");
			DBCollection collection = db.getCollection(collectionName);
			
			int id = Integer.parseInt(idString);
			BasicDBObject newDocument = new BasicDBObject();
			BasicDBObject dataToUpdate = (BasicDBObject)JSON.parse(updateData);
			newDocument.append("$set", dataToUpdate); //FML 
			BasicDBObject searchQuery = new BasicDBObject().append("id", id);
			collection.update(searchQuery, newDocument);
			return "{ \"result\": \"Document has been updated\"}";
		} catch (UnknownHostException e) {
			return e.getMessage();
		} catch (MongoException e) {
			return e.getMessage();
		}

	}

	public String deleteDocument(String idString, String collectionName) {
		try {
			int id = Integer.parseInt(idString);
	     	Mongo mongo = new Mongo("localhost", 27017);
			DB db = mongo.getDB("shop");
			DBCollection collection = db.getCollection(collectionName);
			BasicDBObject whereIdDeleteQuery = new BasicDBObject();
			whereIdDeleteQuery.put("id",id);
			collection.remove(whereIdDeleteQuery);
			return "{ \"result\" : \"Document has been deleted\" }";
		} catch (UnknownHostException e) {
			return e.getMessage();
		} catch (MongoException e) {
			return e.getMessage();
		}
			
	}

}