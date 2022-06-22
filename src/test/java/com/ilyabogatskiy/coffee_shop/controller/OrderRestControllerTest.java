package com.ilyabogatskiy.coffee_shop.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilyabogatskiy.coffee_shop.dto.CoffeeVarietyDto;
import com.ilyabogatskiy.coffee_shop.dto.OrderDto;
import com.ilyabogatskiy.coffee_shop.dto.OrderItemDto;
import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;
import com.ilyabogatskiy.coffee_shop.models.Order;
import com.ilyabogatskiy.coffee_shop.models.OrderItem;
import com.ilyabogatskiy.coffee_shop.repository.OrderItemRepository;
import com.ilyabogatskiy.coffee_shop.repository.OrderRepository;
import com.ilyabogatskiy.coffee_shop.service.Impl.OrderServiceImpl;
import com.ilyabogatskiy.coffee_shop.service.OrderPriceCalculationService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
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

@ContextConfiguration(classes = {OrderRestController.class})
@ExtendWith(SpringExtension.class)
class OrderRestControllerTest {

    @Autowired
    private OrderRestController orderRestController;

    @MockBean
    private OrderServiceImpl orderServiceImpl;

    @Test
    void testCreateOrder() {
        OrderRestController orderRestController = new OrderRestController(new OrderServiceImpl(mock(OrderRepository.class),
                mock(OrderItemRepository.class), mock(OrderPriceCalculationService.class)));

        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerName("Customer Name");
        orderDto.setDeliveryAddress("42 Main St");
        orderDto.setId(123L);
        orderDto.setOrderDateTime("2020-03-01");
        orderDto.setOrderItemDtos(new ArrayList<>());
        orderDto.setOrderPrice("Order Price");
        orderRestController.createOrder(orderDto);
    }

    @Test
    void testCreateOrder2() {
        Order order = new Order();
        order.setCustomerName("Customer Name");
        order.setDeliveryAddress("42 Main St");
        order.setId(123L);
        order.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderItems(new ArrayList<>());
        order.setOrderPrice(BigDecimal.valueOf(42L));
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.saveAndFlush((Order) any())).thenReturn(order);
        OrderPriceCalculationService orderPriceCalculationService = mock(OrderPriceCalculationService.class);
        when(orderPriceCalculationService.orderPriceCalculation((Order) any())).thenReturn(BigDecimal.valueOf(42L));

        Order order1 = new Order();
        order1.setCustomerName("Customer Name");
        order1.setDeliveryAddress("42 Main St");
        order1.setId(123L);
        order1.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setOrderItems(new ArrayList<>());
        order1.setOrderPrice(BigDecimal.valueOf(42L));

        OrderServiceImpl orderServiceImpl = new OrderServiceImpl(orderRepository, mock(OrderItemRepository.class),
                orderPriceCalculationService);
        orderServiceImpl.add(order1);
        OrderRestController orderRestController = new OrderRestController(orderServiceImpl);

        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerName("Customer Name");
        orderDto.setDeliveryAddress("42 Main St");
        orderDto.setId(123L);
        orderDto.setOrderDateTime("2020-03-01");
        orderDto.setOrderItemDtos(new ArrayList<>());
        orderDto.setOrderPrice("Order Price");
        orderRestController.createOrder(orderDto);
    }

    @Test
    void testUpdateOrder() {
        OrderRestController orderRestController = new OrderRestController(new OrderServiceImpl(mock(OrderRepository.class),
                mock(OrderItemRepository.class), mock(OrderPriceCalculationService.class)));

        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerName("Customer Name");
        orderDto.setDeliveryAddress("42 Main St");
        orderDto.setId(123L);
        orderDto.setOrderDateTime("2020-03-01");
        orderDto.setOrderItemDtos(new ArrayList<>());
        orderDto.setOrderPrice("Order Price");
        orderRestController.updateOrder(orderDto);
    }

    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateOrder2() {
        Order order = new Order();
        order.setCustomerName("Customer Name");
        order.setDeliveryAddress("42 Main St");
        order.setId(123L);
        order.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderItems(new ArrayList<>());
        order.setOrderPrice(BigDecimal.valueOf(42L));
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.saveAndFlush((Order) any())).thenReturn(order);
        OrderPriceCalculationService orderPriceCalculationService = mock(OrderPriceCalculationService.class);
        when(orderPriceCalculationService.orderPriceCalculation((Order) any())).thenReturn(BigDecimal.valueOf(42L));

        Order order1 = new Order();
        order1.setCustomerName("Customer Name");
        order1.setDeliveryAddress("42 Main St");
        order1.setId(123L);
        order1.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setOrderItems(new ArrayList<>());
        order1.setOrderPrice(BigDecimal.valueOf(42L));

        OrderServiceImpl orderServiceImpl = new OrderServiceImpl(orderRepository, mock(OrderItemRepository.class),
                orderPriceCalculationService);
        orderServiceImpl.add(order1);
        OrderRestController orderRestController = new OrderRestController(orderServiceImpl);

        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerName("Customer Name");
        orderDto.setDeliveryAddress("42 Main St");
        orderDto.setId(123L);
        orderDto.setOrderDateTime("2020-03-01");
        orderDto.setOrderItemDtos(new ArrayList<>());
        orderDto.setOrderPrice("Order Price");
        orderRestController.updateOrder(orderDto);
    }

    @Test
    void testCreateOrderItem() throws Exception {
        CoffeeVariety coffeeVariety = new CoffeeVariety();
        coffeeVariety.setAvailable(true);
        coffeeVariety.setId(123L);
        coffeeVariety.setName("Name");
        coffeeVariety.setPrice(BigDecimal.valueOf(42L));

        OrderItem orderItem = new OrderItem();
        orderItem.setCoffeeVariety(coffeeVariety);
        orderItem.setCups(1);
        orderItem.setId(123L);
        when(this.orderServiceImpl.addItem((OrderItem) any())).thenReturn(orderItem);

        CoffeeVarietyDto coffeeVarietyDto = new CoffeeVarietyDto();
        coffeeVarietyDto.setAvailable(true);
        coffeeVarietyDto.setId(123L);
        coffeeVarietyDto.setName("Name");
        coffeeVarietyDto.setPrice("Price");

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setCoffeeVarietyDto(coffeeVarietyDto);
        orderItemDto.setCups(1);
        orderItemDto.setId(123L);
        String content = (new ObjectMapper()).writeValueAsString(orderItemDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/order/create_item")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.orderRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":123,\"coffeeVariety\":null,\"cups\":1}"));
    }

    @Test
    void testDeleteOrderById() throws Exception {
        doNothing().when(this.orderServiceImpl).delete((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/order/delete/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.orderRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeleteOrderById2() throws Exception {
        doNothing().when(this.orderServiceImpl).delete((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/order/delete/{id}", 123L);
        deleteResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.orderRestController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetAllOrders() throws Exception {
        when(this.orderServiceImpl.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/order/all");
        MockMvcBuilders.standaloneSetup(this.orderRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllOrders2() throws Exception {
        when(this.orderServiceImpl.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/order/all");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.orderRestController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetOrderById() throws Exception {
        Order order = new Order();
        order.setCustomerName("Customer Name");
        order.setDeliveryAddress("42 Main St");
        order.setId(123L);
        order.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderItems(new ArrayList<>());
        order.setOrderPrice(BigDecimal.valueOf(42L));
        when(this.orderServiceImpl.findById((Long) any())).thenReturn(order);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/order/find/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.orderRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"orderDateTime\":[1,1,1,1,1],\"customerName\":\"Customer Name\",\"deliveryAddress\":\"42 Main"
                                        + " St\",\"orderPrice\":42,\"orderItems\":[]}"));
    }

    @Test
    void testGetOrderById2() throws Exception {
        Order order = new Order();
        order.setCustomerName("Customer Name");
        order.setDeliveryAddress("42 Main St");
        order.setId(123L);
        order.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderItems(new ArrayList<>());
        order.setOrderPrice(BigDecimal.valueOf(42L));
        when(this.orderServiceImpl.findById((Long) any())).thenReturn(order);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/order/find/{id}", 123L);
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.orderRestController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"orderDateTime\":[1,1,1,1,1],\"customerName\":\"Customer Name\",\"deliveryAddress\":\"42 Main"
                                        + " St\",\"orderPrice\":42,\"orderItems\":[]}"));
    }
}

