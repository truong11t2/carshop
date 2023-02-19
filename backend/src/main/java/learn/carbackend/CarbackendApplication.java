package learn.carbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import learn.carbackend.persistence.CarEntity;
import learn.carbackend.persistence.CarRepository;
import learn.carbackend.persistence.OwnerEntity;
import learn.carbackend.persistence.OwnerRepository;
import learn.carbackend.persistence.UserEntity;
import learn.carbackend.persistence.UserRepository;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@ComponentScan("learn")
public class CarbackendApplication implements CommandLineRunner{
	private static Logger LOG = LoggerFactory.getLogger(CarbackendApplication.class);
	
	@Autowired
	private CarRepository carRepo;
	
	@Autowired
	private OwnerRepository ownerRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
    PasswordEncoder encoder;

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(CarbackendApplication.class, args);
		String mysqlUri = ctx.getEnvironment().getProperty("spring.datasource.url");
		LOG.info("Connected to MySQL: " + mysqlUri);
	}

	//For H2 database
	@Override
	public void run(String...strings) throws Exception{
		// OwnerEntity owner1 = new OwnerEntity("John", "Wick");
		// OwnerEntity owner2 = new OwnerEntity("Micheal", "Owen");
		// ownerRepo.saveAll(Arrays.asList(owner1, owner2));
		
		// UserEntity user = new UserEntity("user", encoder.encode("user"), "USER");
		// userRepo.save(user);
		// UserEntity admin = new UserEntity("admin", encoder.encode("admin"), "ADMIN");
		// userRepo.save(admin);
		
		
		// carRepo.save(new CarEntity("Ford", "Mustang", "Red", "ADF-1121", 2021, 59000, owner1));
		// carRepo.save(new CarEntity("Nissan", "Leaf", "White", "SSJ-3002", 2019, 29000, owner2));
		// carRepo.save(new CarEntity("Toyota", "Prius", "Silver", "KKO-0212", 2020, 39000, owner1));
		
		//for(CarEntity car:carRepo.findAll()) {
		//	LOG.info(car.getBrand() + " " + car.getModel());
		//}
	}
}
