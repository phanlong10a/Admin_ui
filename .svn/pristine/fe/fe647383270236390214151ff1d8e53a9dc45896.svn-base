import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.globits.config.DatabaseConfig;
import com.globits.core.service.PersonService;
import com.globits.covid19.test.domain.Sample;
import com.globits.covid19.test.domain.SuspectedPerson;
import com.globits.covid19.test.dto.SampleDto;
import com.globits.covid19.test.dto.SuspectedPersonDto;
import com.globits.covid19.test.service.SampleService;
import com.globits.covid19.test.service.SuspectedPersonService;
import com.globits.security.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DatabaseConfig.class)
//@Transactional(propagation = Propagation.REQUIRED)
public class ServiceTest {

	@Autowired
	UserService service;
	@Autowired
	SampleService sampleService;
	@Autowired
	SuspectedPersonService personService;
	
	@Test
	public void testSaveSample() {
		Sample sample = new Sample();
		SuspectedPerson p = new SuspectedPerson();
		p.setBirthDate(new Date());
		p.setName("Test1");
		sample.setName("Test1");
		sample.setCode("Test1");
		p=personService.save(p);
		sample.setPerson(p);
		sample = sampleService.save(sample);
		System.out.println(sample.getId()+":"+sample.getPerson().getId());
	}
	//@Test
	public void testDeletePerson() {
		sampleService.delete(UUID.fromString("daab7311-17aa-497e-848a-0f0601dfa4c3"));
		
		SuspectedPersonDto p = personService.getById(UUID.fromString("5478bc8b-5cf5-4e13-adef-2e2e5887b507"));
		System.out.println("Test:"+p);
	}

}
