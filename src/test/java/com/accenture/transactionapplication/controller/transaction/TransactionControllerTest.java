package com.accenture.transactionapplication.controller.transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@SpringBootTest
class TransactionControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionController transactionController;

//    @BeforeEach
//    public void setup(){
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .apply(springSecurity())
//                .build();
//    }

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void testFindController() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        org.assertj.core.api.Assertions.assertThat(servletContext).isNotNull();
        org.assertj.core.api.Assertions.assertThat(servletContext instanceof MockServletContext).isTrue();
        org.assertj.core.api.Assertions.assertThat(webApplicationContext.getBean("transactionController")).isNotNull();
    }
    @WithMockUser(username = "USER")
    @Test
    void findAllByAmountBeforeAndAmountAfterOrderByAmountDesc() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/transactions/byAmount"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "Admin1")
    @Test
    void findTransactionById_FirstAuthenticationMethod() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/transactions/2").with(user("Admin1").roles("ADMIN")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "ADMIN")
    @Test
    void findTransactionById_SecondAuthenticationMethod() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/transactions/2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "USER")
    @Test
    void findTransactionById_WithUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/transactions/2"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}