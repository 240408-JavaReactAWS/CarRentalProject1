package com.revature.CarRental;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarRentalApplicationTests {

	@MockBean
	private CarRentalApplication carRentalApplication;

	@Test
	void contextLoads() {
		assertThat(carRentalApplication).isNotNull();
	}

	@Test
	public void main_runsWithoutException() {
		String[] args = {};
		CarRentalApplication.main(args);
		assertThat(carRentalApplication).isNotNull();
	}
}
