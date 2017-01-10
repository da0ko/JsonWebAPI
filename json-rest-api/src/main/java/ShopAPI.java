import static spark.Spark.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
* REST API for shop of electronic goods
* @author  Bulyga Artem
*/

public class ShopAPI {

	private static final int HTTP_BAD_REQUEST = 400;
	private static final int HTTP_OK = 200;

	private static final CRUD_API crud_api = new CRUD_API();

	public static void main (String[] args)  {

		get("/get/:collection", (request, response) -> {
			String collectionName = request.params(":collection");
			response.type("application/json");
			return crud_api.selectAllDocuments(collectionName);
		});

		get("/get/:collection/:id", (request, response) -> {
			String collectionName = request.params(":collection");
			String idToSelect = request.params(":id");
			response.type("application/json");
			return crud_api.selectDocument(idToSelect, collectionName);
		});

		post("/post/:collection", (request, response) -> {
			response.type("application/json");
			try {
				if (request.body().isEmpty()) {
					return "{ \"errormsg\": \"No data detected\"}";
				}
				ObjectMapper mapper = new ObjectMapper();
				String collectionName = request.params(":collection");

				if (collectionName.equals("products")) {
					Product newItem =  (Product)mapper.readValue(request.body(), Product.class);
					if (!newItem.isValid()) {
						response.status(HTTP_BAD_REQUEST);
						return "{ \"errormsg\": \"Invalid data\"}";
					}
					response.status(HTTP_OK);
					response.type("application/json");
					return crud_api.insertNewDocument(request.body(), collectionName);
				} else if (collectionName.equals("brands")) {
					Brand newItem = (Brand)mapper.readValue(request.body(), Brand.class);
					if (!newItem.isValid()) {
						response.status(HTTP_BAD_REQUEST);
						return "{ \"errormsg\": \"Invalid data\"}";
					}
					response.status(HTTP_OK);
					response.type("application/json");
					return crud_api.insertNewDocument(request.body(), collectionName);
				} else {
					return "{ \"errormsg\": \"Collection:" + collectionName + " not found\"}";
				}

			} catch (JsonParseException jpe) {
				response.status(HTTP_BAD_REQUEST);
				return "{ \"errormsg\": \"Invalid JSON\"}";
			}

		});

		put("/put/:collection/:id", (request, response) -> {
			String idToUpdate = request.params(":id");
			String collectionName = request.params(":collection");	
			response.type("application/json");
			return crud_api.updateDocument(request.body(), idToUpdate, collectionName);
		});

		delete("/delete/:collection/:id", (request, response) -> {
			String collectionName = request.params(":collection");
			String idToDelete = request.params(":id");
			response.type("application/json");
			return crud_api.deleteDocument(idToDelete, collectionName);
		});

	}
}
