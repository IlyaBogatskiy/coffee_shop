package com.ilyabogatskiy.coffee_shop.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilyabogatskiy.coffee_shop.dto.CoffeeVarietyDto;
import com.ilyabogatskiy.coffee_shop.mapper.CoffeeVarietyMapper;
import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;
import com.ilyabogatskiy.coffee_shop.service.CoffeeVarietyService;

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

    @MockBean
    private CoffeeVarietyMapper coffeeVarietyMapper;

    @Autowired
    private CoffeeVarietyRestController coffeeVarietyRestController;

    @MockBean
    private CoffeeVarietyService coffeeVarietyService;

    @Test
    void testAddCoffeeVariety() throws Exception {
        CoffeeVariety coffeeVariety = new CoffeeVariety();
        coffeeVariety.setAvailable(true);
        coffeeVariety.setId(123L);
        coffeeVariety.setName("Name");
        coffeeVariety.setPrice(BigDecimal.valueOf(42L));
        when(this.coffeeVarietyService.add((CoffeeVariety) any())).thenReturn(coffeeVariety);

        CoffeeVarietyDto coffeeVarietyDto = new CoffeeVarietyDto();
        coffeeVarietyDto.setAvailable(true);
        coffeeVarietyDto.setId(123L);
        coffeeVarietyDto.setName("Name");
        coffeeVarietyDto.setPrice("Price");

        CoffeeVariety coffeeVariety1 = new CoffeeVariety();
        coffeeVariety1.setAvailable(true);
        coffeeVariety1.setId(123L);
        coffeeVariety1.setName("Name");
        coffeeVariety1.setPrice(BigDecimal.valueOf(42L));
        when(this.coffeeVarietyMapper.toDto((CoffeeVariety) any())).thenReturn(coffeeVarietyDto);
        when(this.coffeeVarietyMapper.toModel((CoffeeVarietyDto) any())).thenReturn(coffeeVariety1);

        CoffeeVarietyDto coffeeVarietyDto1 = new CoffeeVarietyDto();
        coffeeVarietyDto1.setAvailable(true);
        coffeeVarietyDto1.setId(123L);
        coffeeVarietyDto1.setName("Name");
        coffeeVarietyDto1.setPrice("Price");
        String content = (new ObjectMapper()).writeValueAsString(coffeeVarietyDto1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/variety/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.coffeeVarietyRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":123,\"name\":\"Name\",\"price\":\"Price\",\"available\":true}"));
    }

    @Test
    void testDeleteCoffeeVarietyById() throws Exception {
        doNothing().when(this.coffeeVarietyService).delete((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/variety/delete/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.coffeeVarietyRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetAllAvailableCoffeeVarieties() throws Exception {
        when(this.coffeeVarietyService.findAllAvailable()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/variety/all_available");
        MockMvcBuilders.standaloneSetup(this.coffeeVarietyRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllAvailableCoffeeVarieties2() throws Exception {
        when(this.coffeeVarietyService.findAllAvailable()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/variety/all_available");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.coffeeVarietyRestController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllCoffeeVarieties() throws Exception {
        when(this.coffeeVarietyService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/variety/all");
        MockMvcBuilders.standaloneSetup(this.coffeeVarietyRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllCoffeeVarieties2() throws Exception {
        when(this.coffeeVarietyService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/variety/all");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.coffeeVarietyRestController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetCoffeeVarietyById() throws Exception {
        CoffeeVariety coffeeVariety = new CoffeeVariety();
        coffeeVariety.setAvailable(true);
        coffeeVariety.setId(123L);
        coffeeVariety.setName("Name");
        coffeeVariety.setPrice(BigDecimal.valueOf(42L));
        when(this.coffeeVarietyService.findById((Long) any())).thenReturn(coffeeVariety);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/variety/find/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.coffeeVarietyRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"id\":123,\"name\":\"Name\",\"price\":42,\"available\":true}"));
    }

    @Test
    void testGetCoffeeVarietyById2() throws Exception {
        CoffeeVariety coffeeVariety = new CoffeeVariety();
        coffeeVariety.setAvailable(true);
        coffeeVariety.setId(123L);
        coffeeVariety.setName("Name");
        coffeeVariety.setPrice(BigDecimal.valueOf(42L));
        when(this.coffeeVarietyService.findById((Long) any())).thenReturn(coffeeVariety);
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

    @Test
    void testUpdateCoffeeVarieties() throws Exception {
        CoffeeVariety coffeeVariety = new CoffeeVariety();
        coffeeVariety.setAvailable(true);
        coffeeVariety.setId(123L);
        coffeeVariety.setName("Name");
        coffeeVariety.setPrice(BigDecimal.valueOf(42L));
        when(this.coffeeVarietyService.edit((CoffeeVariety) any())).thenReturn(coffeeVariety);

        CoffeeVarietyDto coffeeVarietyDto = new CoffeeVarietyDto();
        coffeeVarietyDto.setAvailable(true);
        coffeeVarietyDto.setId(123L);
        coffeeVarietyDto.setName("Name");
        coffeeVarietyDto.setPrice("Price");

        CoffeeVariety coffeeVariety1 = new CoffeeVariety();
        coffeeVariety1.setAvailable(true);
        coffeeVariety1.setId(123L);
        coffeeVariety1.setName("Name");
        coffeeVariety1.setPrice(BigDecimal.valueOf(42L));
        when(this.coffeeVarietyMapper.toDto((CoffeeVariety) any())).thenReturn(coffeeVarietyDto);
        when(this.coffeeVarietyMapper.toModel((CoffeeVarietyDto) any())).thenReturn(coffeeVariety1);

        CoffeeVarietyDto coffeeVarietyDto1 = new CoffeeVarietyDto();
        coffeeVarietyDto1.setAvailable(true);
        coffeeVarietyDto1.setId(123L);
        coffeeVarietyDto1.setName("Name");
        coffeeVarietyDto1.setPrice("Price");
        String content = (new ObjectMapper()).writeValueAsString(coffeeVarietyDto1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/variety/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.coffeeVarietyRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":123,\"name\":\"Name\",\"price\":\"Price\",\"available\":true}"));
    }
}