package restAssuredApi;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.Category;
import pojo.Pet;
import pojo.Tag;

public class TestScript {

	
	private static RequestSpecification request;
	private static Response response;
	Pet addNewPet;
	Pet updatePet;
	
	
	@BeforeClass
	public void setup() {
		int id = RandomUtils.nextInt();
		addNewPet = getPetObj(id, "test", "Tommy", "available","test");
		updatePet = getPetObj(id, "newCategory", "Musk", "Not available","test");
	}
	
	private Pet getPetObj(int id, String categoryName, String petName, String status, String tagName) {
		
		Category category = new Category();
		category.setId(id);
		category.setName(categoryName);
		Tag tag = new Tag();
		tag.setId(id);
		tag.setName(tagName);
		Pet pet = new Pet();
		pet.setCategory(category);
		pet.setId(id);
		
		List<String> photoUrls = new ArrayList<String>();
		List<Tag> tags = new ArrayList<Tag>();
		tags.add(tag);
		photoUrls.add("url");
		pet.setPhotoUrls(photoUrls);
//		pet.setPhotoUrls(Arrays.asList("test"));//Create new list and set data into list.
		pet.setStatus(status);
		//pet.setTags(Arrays.asList(tag));
		pet.setTags(tags);
		pet.setName(petName);
		return pet;
	}
	

	
	@Test(priority = 0)
	public void addPet() throws JsonProcessingException {
		System.out.println("=============add Pet===============");
		ObjectMapper mapper = new ObjectMapper();
		//String payload = "{\r\n  \"id\": 90,\r\n  \"category\": {\r\n    \"id\": 0,\r\n    \"name\": \"string\"\r\n  },\r\n  \"name\": \"Siberian Husky\",\r\n  \"photoUrls\": [\r\n    \"string\"\r\n  ],\r\n  \"tags\": [\r\n    {\r\n      \"id\": 0,\r\n      \"name\": \"string\"\r\n    }\r\n  ],\r\n  \"status\": \"available\"\r\n}";
		String payload = mapper.writeValueAsString(addNewPet);
		request = RestAssured.given();
		request.contentType(ContentType.JSON);
		request.baseUri("https://petstore.swagger.io/v2/pet");
		request.body(payload);
		response = request.post();
		List<String> IDs = 
				Arrays.asList(response.jsonPath().getString("id").split("\\s*,\\s*"));
		
		System.out.println(response.asString());
		System.out.println("Status code" + response.statusCode());
		System.out.println("id : " + IDs);
		
	}

	@Test(priority = 1)
	public void getPet() {
		System.out.println("=============Get Pet===============");
		request = RestAssured.given();
		request.contentType(ContentType.JSON);
		request.baseUri("https://petstore.swagger.io/v2/pet/" + addNewPet.getId());
		response = request.get();
		System.out.println("id " + addNewPet.getId());
		System.out.println(response.asPrettyString());
		System.out.println("Status code : " + response.statusCode());
		
	}

	@Test(priority = 2)
	public void updatePetsDetails() throws JsonProcessingException {
		System.out.println("=============update Pet===============");
		ObjectMapper mapper = new ObjectMapper();
		String payload = mapper.writeValueAsString(updatePet);
		System.out.println(payload);
		request = RestAssured.given();
		request.contentType(ContentType.JSON);
		request.baseUri("https://petstore.swagger.io/v2/pet");
		request.body(payload);
		response = request.put();
		
		System.out.println(response.asString());
		System.out.println("Status code : " + response.statusCode());
		
	}

	@Test(priority = 3)
	public void deletePet() {
		System.out.println("=============delete Pet===============");
		request = RestAssured.given();
		request.contentType(ContentType.JSON);
		request.baseUri("https://petstore.swagger.io/v2/pet/" + addNewPet.getId());
		response = request.delete();
		System.out.println(response.asPrettyString());
		System.out.println("Status code : " + response.statusCode());
		
	}
}
