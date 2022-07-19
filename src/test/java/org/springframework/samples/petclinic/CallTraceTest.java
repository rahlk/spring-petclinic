package org.springframework.samples.petclinic;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import com.google.gson.Gson;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

/* Follow the steps below to create a test case from the trace
 * -----------------------------------------------------------
 *
 * 1. Find the instance of the pet from the trace and unmarshall the json into a pet object
 *
 */

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CallTraceTest {

	@Mock
	private OwnerRepository mockedOwnerRepository;

	@Mock
	private BindingResult mockedBindingResult;
	/*
	Below we have a trace from the call tracing agent. We are trying to recreate this trace.

		1.  org.springframework.samples.petclinic.owner.Pet inst_182043233 = desirilezed({"birthDate":{"year":2022,"month":6,"day":30},"type":{"name":"bird","id":5},"visits":[],"name":"jjjj"})
		2.  java.lang.String inst_433539211 = inst_182043233.getName();   //org.springframework.samples.petclinic.model.NamedEntity.getName()Ljava/lang/String;
		3.  java.lang.Boolean inst_84815985 = org.springframework.util.StringUtils.hasLength(java/lang/String);   //org.springframework.util.StringUtils.hasLength(Ljava/lang/String;)Z
		4.  java.lang.Boolean inst_84815985 = inst_182043233.isNew();   //org.springframework.samples.petclinic.model.BaseEntity.isNew()Z
		5.  java.lang.String inst_433539211 = inst_182043233.getName();   //org.springframework.samples.petclinic.model.NamedEntity.getName()Ljava/lang/String;
		6.  org.springframework.samples.petclinic.owner.Owner inst_414679530 = desirilezed({"address":"110 W. Liberty St.","city":"Madison","telephone":"6085551023","pets":[{"birthDate":{"year":2010,"month":9,"day":7},"type":{"name":"cat","id":1},"visits":[],"name":"Leo","id":1}],"firstName":"George","lastName":"Franklin","id":1})
		7.  inst_414679530.getPet(inst_433539211, true);   //org.springframework.samples.petclinic.owner.Owner.getPet(Ljava/lang/String;Z)Lorg/springframework/samples/petclinic/owner/Pet;
		8.  inst_414679530.addPet(inst_182043233);   //org.springframework.samples.petclinic.owner.Owner.addPet(Lorg/springframework/samples/petclinic/owner/Pet;)V
		9.  java.lang.Boolean inst_1687605116 = org.springframework.validation.BindingResult.hasErrors();   //org.springframework.validation.BindingResult.hasErrors()Z
	   10.  org.springframework.samples.petclinic.owner.OwnerRepository.save(org/springframework/samples/petclinic/owner/Owner);   //org.springframework.samples.petclinic.owner.OwnerRepository.save(Lorg/springframework/samples/petclinic/owner/Owner;)V
	 */

	@Test
	void runTrace3() {

		// 1. We have to create instance inst_182043233.
		// So, we have to parse the deserialized JSON of the instance to get the field information and then unmarshall this into a Pet object.
		String inst_182043233_json = "{\"birthDate\":{\"year\":2022,\"month\":6,\"day\":30},\"type\":{\"name\":\"bird\",\"id\":5},\"visits\":[],\"name\":\"jjjj\"}";
		Pet inst_182043233 = new Gson().fromJson(inst_182043233_json, Pet.class); // NOTE: I had to determine the class from above.
		// 2. Create java.lang.String inst_433539211
		String inst_433539211_0 = inst_182043233.getName();
		// 3. This one is strange, we need to look at the instrumentation here. What is "org.springframework.util.StringUtils.hasLength(java/lang/String)" being run on?
		//    If I had to guess, I would assume inst_433539211
		Boolean inst_84815985_0 = StringUtils.hasLength(inst_433539211_0);
		// 4. This one is straightforward
		Boolean inst_84815985_1 = inst_182043233.isNew();
		/* ================================
		 * TODO: INTERMEDIATE STEPS MISSING
		 * ================================ */
		// 5. Seems like a redefinition here. I have a feeling we are missing some application logic here. But here goes...
		String inst_433539211_1 = inst_182043233.getName();
		// 6. Create a new owner
		String inst_414679530_json = "{\"address\":\"110 W. Liberty St.\",\"city\":\"Madison\",\"telephone\":\"6085551023\",\"pets\":[{\"birthDate\":{\"year\":2010,\"month\":9,\"day\":7},\"type\":{\"name\":\"cat\",\"id\":1},\"visits\":[],\"name\":\"Leo\",\"id\":1}],\"firstName\":\"George\",\"lastName\":\"Franklin\",\"id\":1}";
		Owner inst_414679530 = new Gson().fromJson(inst_414679530_json, Owner.class);
		// 7. Get pet (and ignore new)
		inst_414679530.getPet(inst_433539211_1, true);

		/* ================================
		 * TODO: INTERMEDIATE STEPS MISSING
		 * ================================ */
		// 8. Add the pet. I feel like there is some missing code that needs to run before the previous line and this one.
		inst_414679530.addPet(inst_182043233);

		/* ============================================================================================
		 * TODO: ERROR: A non-static method is being referenced in a static context. Have to mock this.
		 * =========================================================================================== */
		// 9. I am commenting out the following line. This is erroneous: A non-static method is being referenced in a static context.
		// Boolean inst_1687605116 = BindingResult.hasErrors();
		mockedBindingResult.hasErrors();

		// 10. Save the output. I had to mock this to be able to run.
		mockedOwnerRepository.save(inst_414679530);
	}
}
