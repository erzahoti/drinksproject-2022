package com.drinkproject.drinks;

import com.drinkproject.drinks.data.drink.Drink;
import com.drinkproject.drinks.data.drink.DrinkType;
import com.drinkproject.drinks.repository.DrinkRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class DrinkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    DrinkRepository drinkRepository;

    @Test
    public void getAllDrinks() throws Exception {

        var drinkType = new DrinkType();
        drinkType.setDescription("ITALIAN COFFEE");
        drinkType.setId(UUID.randomUUID());
        drinkType.setName("ITALIAN_COFFEE");

        var drink = new Drink();
        drink.setId(UUID.randomUUID());
        drink.setDescription("coffee latte");
        drink.setDrinkType(drinkType);
        drink.setQuantity(100);
        drink.setPrice(50);

        Mockito.when(drinkRepository.findAll()).thenReturn(List.of(drink));

        var result = mockMvc.perform(get("/drinks")).andReturn();

        var returnedDrinks = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Drink>>() {});
        assertNotNull(returnedDrinks);
    }

}
