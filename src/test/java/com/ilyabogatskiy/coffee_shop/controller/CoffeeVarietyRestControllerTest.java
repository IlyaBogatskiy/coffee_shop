package com.ilyabogatskiy.coffee_shop.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilyabogatskiy.coffee_shop.dto.CoffeeVarietyDto;
import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;
import com.ilyabogatskiy.coffee_shop.service.Impl.CoffeeVarietyServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CoffeeVarietyRestController.class})
@ExtendWith(SpringExtension.class)
class CoffeeVarietyRestControllerTest {
    @Autowired
    private CoffeeVarietyRestController coffeeVarietyRestController;

    @MockBean
    private CoffeeVarietyServiceImpl coffeeVarietyServiceImpl;

    /**
     * Method under test: {@link CoffeeVarietyRestController#addCoffeeVariety(CoffeeVarietyDto)}
     */
    @Test
    void testAddCoffeeVariety() throws Exception {
        CoffeeVariety coffeeVariety = new CoffeeVariety();
        coffeeVariety.setAvailable(true);
        coffeeVariety.setId(123L);
        coffeeVariety.setName("Name");
        coffeeVariety.setPrice(BigDecimal.valueOf(42L));
        when(this.coffeeVarietyServiceImpl.add((CoffeeVariety) any())).thenReturn(coffeeVariety);

        CoffeeVarietyDto coffeeVarietyDto = new CoffeeVarietyDto();
        coffeeVarietyDto.setAvailable(true);
        coffeeVarietyDto.setId(123L);
        coffeeVarietyDto.setName("Name");
        coffeeVarietyDto.setPrice("42");
        String content = (new ObjectMapper()).writeValueAsString(coffeeVarietyDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/variety/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.coffeeVarietyRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"id\":123,\"name\":\"Name\",\"price\":42,\"available\":true}"));
    }

    /**
     * Method under test: {@link CoffeeVarietyRestController#deleteCoffeeVarietyById(Long)}
     */
    @Test
    void testDeleteCoffeeVarietyById() throws Exception {
        doNothing().when(this.coffeeVarietyServiceImpl).delete((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/variety/delete/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.coffeeVarietyRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link CoffeeVarietyRestController#deleteCoffeeVarietyById(Long)}
     */
    @Test
    void testDeleteCoffeeVarietyById2() throws Exception {
        doNothing().when(this.coffeeVarietyServiceImpl).delete((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/variety/delete/{id}", 123L);
        deleteResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.coffeeVarietyRestController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link CoffeeVarietyRestController#getAllAvailableCoffeeVarieties()}
     */
    @Test
    void testGetAllAvailableCoffeeVarieties() throws Exception {
        when(this.coffeeVarietyServiceImpl.findAllAvailable()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/variety/all_available");
        MockMvcBuilders.standaloneSetup(this.coffeeVarietyRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link CoffeeVarietyRestController#getAllAvailableCoffeeVarieties()}
     */
    @Test
    void testGetAllAvailableCoffeeVarieties2() throws Exception {
        when(this.coffeeVarietyServiceImpl.findAllAvailable()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/variety/all_available");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.coffeeVarietyRestController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link CoffeeVarietyRestController#getAllCoffeeVarieties()}
     */
    @Test
    void testGetAllCoffeeVarieties() throws Exception {
        when(this.coffeeVarietyServiceImpl.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/variety/all");
        MockMvcBuilders.standaloneSetup(this.coffeeVarietyRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link CoffeeVarietyRestController#getAllCoffeeVarieties()}
     */
    @Test
    void testGetAllCoffeeVarieties2() throws Exception {
        when(this.coffeeVarietyServiceImpl.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/variety/all");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.coffeeVarietyRestController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link CoffeeVarietyRestController#getCoffeeVarietyById(Long)}
     */
    @Test
    void testGetCoffeeVarietyById() throws Exception {
        CoffeeVariety coffeeVariety = new CoffeeVariety();
        coffeeVariety.setAvailable(true);
        coffeeVariety.setId(123L);
        coffeeVariety.setName("Name");
        coffeeVariety.setPrice(BigDecimal.valueOf(42L));
        when(this.coffeeVarietyServiceImpl.findById((Long) any())).thenReturn(coffeeVariety);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/variety/find/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.coffeeVarietyRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"id\":123,\"name\":\"Name\",\"price\":42,\"available\":true}"));
    }

    /**
     * Method under test: {@link CoffeeVarietyRestController#getCoffeeVarietyById(Long)}
     */
    @Test
    void testGetCoffeeVarietyById2() throws Exception {
        CoffeeVariety coffeeVariety = new CoffeeVariety();
        coffeeVariety.setAvailable(true);
        coffeeVariety.setId(123L);
        coffeeVariety.setName("Name");
        coffeeVariety.setPrice(BigDecimal.valueOf(42L));
        when(this.coffeeVarietyServiceImpl.findById((Long) any())).thenReturn(coffeeVariety);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/variety/find/{id}", 123L);
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.coffeeVarietyRestController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"id\":123,\"name\":\"Name\",\"price\":42,\"available\":true}"));
    }

    /**
     * Method under test: {@link CoffeeVarietyRestController#updateCoffeeVarieties(CoffeeVarietyDto)}
     */
    @Test
    void testUpdateCoffeeVarieties() throws Exception {
        CoffeeVariety coffeeVariety = new CoffeeVariety();
        coffeeVariety.setAvailable(true);
        coffeeVariety.setId(123L);
        coffeeVariety.setName("Name");
        coffeeVariety.setPrice(BigDecimal.valueOf(42L));
        when(this.coffeeVarietyServiceImpl.edit((CoffeeVariety) any())).thenReturn(coffeeVariety);

        CoffeeVarietyDto coffeeVarietyDto = new CoffeeVarietyDto();
        coffeeVarietyDto.setAvailable(true);
        coffeeVarietyDto.setId(123L);
        coffeeVarietyDto.setName("Name");
        coffeeVarietyDto.setPrice("42");
        String content = (new ObjectMapper()).writeValueAsString(coffeeVarietyDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/variety/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.coffeeVarietyRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"id\":123,\"name\":\"Name\",\"price\":42,\"available\":true}"));
    }
}

