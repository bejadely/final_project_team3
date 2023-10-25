package com.trip.finalProject;



import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class FinalProjectApplicationTests {

		
	@Autowired
	StringEncryptor jasptStringEncryptor;
	
	@Test
	public void encryptTest() {
		String testData = "jdbc:log4jdbc:oracle:thin:@13.124.177.184/xe";
		
		String encryptedData = jasptStringEncryptor.encrypt(testData);
		
		System.out.println(encryptedData);
	}
	
	@Test
	public void encyptAllTest() {
		int repeatCount = 0;
		String[] allData = { "net.sf.log4jdbc.sql.jdbcapi.DriverSpy",
							 "jdbc:log4jdbc:oracle:thin:@13.124.177.184/xe",
							 "tour",
							 "3team1234",
							 "QQvyoO7KirHLA7ueEZwH",
							 "4SkxwdgTWqS7kRgin9UEvsUaGURsBr4kO220Tjog",
							 "ncp:sms:kr:313039527592:yedam_last_project",
							 "01047357934",
							 "smtp.gmail.com",
							 "587",
							 "leekleek9494@gmail.com",
							 "cndvvjdoqeqwyvfb",
							 "d37c7ea721a01188dd47b9d069964fe4&libraries=services",
							 "7b4adfbd7d95d738f22c53a059516ffc",
							 "SgpQI9OLDRYcTC13sHnzaFNafSGG1B3BPsdYE2JJoilJrPFXOJ5E0pPE%2FRfLYRoPx75dcdfbs7kKvxYFYxioSg%3D%3D",
							 "wTMveh-1flVrt-nWAI8j-s9giwN-20230907121549",
							 "aeskey87522558466654321asekey987",
							 "aesiv12422411342" };
		
		for (String all : allData ) {
			
			repeatCount += 1;

			String encryptedData = jasptStringEncryptor.encrypt(all);
			System.out.println(repeatCount + "ENC(" + encryptedData + ")");
			
		}
		
	}
	


}
