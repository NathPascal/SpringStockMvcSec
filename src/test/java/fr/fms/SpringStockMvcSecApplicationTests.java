package fr.fms;

import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;
import fr.fms.service.Cart;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class SpringStockMvcSecApplicationTests {
	@Autowired
	Cart cart;

	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	CategoryRepository categoryRepository;

//	@Test
//	void contextLoads(){
//		assertFalse(1==2);
//	}

	private static Instant startedAt;

	@BeforeEach
	public void beforeEachTest(){
		System.out.println("avant chaque test");
	}

	@AfterEach
	public void afterEachTest(){
		System.out.println("après chaque test");
	}

	@BeforeAll
	public static void initStartingTime(){
		System.out.println("Appel avant tous les tests");
		startedAt = Instant.now();
	}

	@AfterAll
	public static void showTestDuration(){
		System.out.println("Appel après tous les tests");
		final Instant endedAt = Instant.now();
		final long duration = Duration.between(startedAt, endedAt).toMillis();
		System.out.println(MessageFormat.format("Durée des tests : {0} ms", duration));
	}

	@ParameterizedTest(name  = "{0} * 0 doit être égal à 0")
	@ValueSource(ints = {1, 2, 42, 1011, 5089})
	public void multiply_shouldReturnZero(int arg){
		assertEquals(0, arg*0);
	}

	@Timeout(1)
	@Test
	public void orderShouldComputeLess1Second(){
		cart.getTotalPrice();
	}

	@Test
	void testTotalAmontCart(){
		cart.addItem(new Article(null, "Samsung Galaxy S8", 250, null));
		cart.addItem(new Article(null, "Samsung Galaxy S9", 300, null));
		cart.addItem(new Article(null, "Apple iPhone 10", 500, null));

		double amount = cart.getTotalPrice();

		assertEquals(amount, 1050);
//		assertEquals(cart.getTotalPrice(), 1050);
	}



}


