/**
 * 
 */
package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPoints2ndApproach;
import api.payload.User;
import io.restassured.response.Response;


public class UserTest2ndApproach {
	
	Faker faker;
	User userPayload;
	
	public Logger logger;
	
	@BeforeClass
	public void setup() {
		
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//Logs
		logger = LogManager.getLogger(this.getClass());
	}

	
	@Test(priority=1)
	public void testPostUser(){
		
		logger.info("************* Creating User ******************");
		Response response = UserEndPoints2ndApproach.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************* User is Created ******************");
	}
	
	@Test(priority=2)
	public void testGetUserByNam(){
		
		logger.info("************* Reading user info ******************");
		
		Response response = UserEndPoints2ndApproach.readUser(this.userPayload.getUsername()); //this. to extract exact data from userPayload
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************* User info is Displayed ******************");
	}
	
	@Test(priority=3)
	public void testUpdateUserByName(){
		
		logger.info("************* Updating User ******************");
		
		//update data using payLoad		
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		
		Response response = UserEndPoints2ndApproach.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().body().statusCode(200);// this is assert as well
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************* User is Updated ******************");
		
		//cheking data after update
		Response responseAfterPayload = UserEndPoints2ndApproach.readUser(this.userPayload.getUsername()); 		
		Assert.assertEquals(responseAfterPayload.getStatusCode(), 200);
		
	}
	
	@Test(priority=4)
	public void testDeleteUserBuName(){
		
		logger.info("************* Deleting User ******************");
		
		Response response =  UserEndPoints2ndApproach.deleteUser(this.userPayload.getUsername());
		response.then().log().body().statusCode(200);
		
		logger.info("************* User Deleted ******************");
		
	}
}
