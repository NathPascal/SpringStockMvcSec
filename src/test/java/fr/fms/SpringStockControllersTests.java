package fr.fms;

import fr.fms.service.Cart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SpringStockControllersTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private Cart cart;

    @Test
    public void test_get_welcome()throws Exception{
        when(cart.great()).thenReturn("Hello, Mock");

        this.mvc.perform(get("/greating"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                .string(containsString("Hello, Mock")));
    }

//    @Test
//    public void test_get_articles() throws Exception{
//
//        this.mvc.perform(get("/index"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].description", is("Samsung")));
//    }
}
