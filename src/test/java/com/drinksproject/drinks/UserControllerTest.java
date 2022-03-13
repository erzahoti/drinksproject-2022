package com.drinksproject.drinks;

import com.drinksproject.drinks.model.user.User;
import com.drinksproject.drinks.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    UserRepository userRepository;

    @Test
    public void getUser() throws Exception {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setAddress("Oslo, Norway");
        user.setEmail("e@gmail.com");
        user.setName("Test");

        Mockito.when(userRepository.findById(any())).thenReturn(java.util.Optional.of(user));

        var result = mockMvc.perform(get("/user/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        var returnedUser = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertNotNull(returnedUser);
        assertEquals(user.getId(), returnedUser.getId());
        assertEquals(user.getEmail(), returnedUser.getEmail());
    }
}
