package com.ilyabogatskiy.coffee_shop.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilyabogatskiy.coffee_shop.dto.OrderDto;
import com.ilyabogatskiy.coffee_shop.dto.OrderItemDto;
import com.ilyabogatskiy.coffee_shop.mapper.OrderItemMapper;
import com.ilyabogatskiy.coffee_shop.mapper.OrderItemMapperImpl;
import com.ilyabogatskiy.coffee_shop.mapper.OrderMapper;
import com.ilyabogatskiy.coffee_shop.mapper.OrderMapperImpl;
import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;
import com.ilyabogatskiy.coffee_shop.models.Order;
import com.ilyabogatskiy.coffee_shop.models.OrderItem;
import com.ilyabogatskiy.coffee_shop.repository.CoffeeVarietyRepository;
import com.ilyabogatskiy.coffee_shop.repository.OrderItemRepository;
import com.ilyabogatskiy.coffee_shop.repository.OrderRepository;
import com.ilyabogatskiy.coffee_shop.service.Impl.OrderPriceCalculationServiceImpl;
import com.ilyabogatskiy.coffee_shop.service.Impl.OrderServiceImpl;
import com.ilyabogatskiy.coffee_shop.service.OrderService;

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

    @MockBean
    private OrderItemMapper orderItemMapper;

    @MockBean
    private OrderMapper orderMapper;

    @Autowired
    private OrderRestController orderRestController;

    @MockBean
    private OrderService orderService;

    @Test
    void testCreateOrder() {
        Order order = new Order();
        order.setCustomerName("Customer Name");
        order.setDeliveryAddress("42 Main St");
        order.setId(123L);
        order.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        order.setOrderItems(orderItemList);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        order.setOrderPrice(valueOfResult);
        OrderServiceImpl orderServiceImpl = mock(OrderServiceImpl.class);
        when(orderServiceImpl.add((Order) any())).thenReturn(order);
        OrderMapperImpl orderMapper = new OrderMapperImpl();
        OrderRestController orderRestController = new OrderRestController(orderServiceImpl, orderMapper,
                new OrderItemMapperImpl());
        OrderDto actualCreateOrderResult = orderRestController.createOrder(new OrderDto());
        assertEquals("Customer Name", actualCreateOrderResult.getCustomerName());
        BigDecimal orderPrice = actualCreateOrderResult.getOrderPrice();
        assertSame(valueOfResult, orderPrice);
        assertEquals(orderItemList, actualCreateOrderResult.getOrderItemDtos());
        assertEquals("42 Main St", actualCreateOrderResult.getDeliveryAddress());
        assertEquals(123L, actualCreateOrderResult.getId().longValue());
        assertEquals("0001-01-01", actualCreateOrderResult.getOrderDateTime().toLocalDate().toString());
        assertEquals("42", orderPrice.toString());
        verify(orderServiceImpl).add((Order) any());
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
        orderItem.setOrderItemPrice(BigDecimal.valueOf(42L));
        when(this.orderService.addItem((OrderItem) any())).thenReturn(orderItem);

        CoffeeVariety coffeeVariety1 = new CoffeeVariety();
        coffeeVariety1.setAvailable(true);
        coffeeVariety1.setId(123L);
        coffeeVariety1.setName("Name");
        coffeeVariety1.setPrice(BigDecimal.valueOf(42L));

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setCoffeeVariety(coffeeVariety1);
        orderItem1.setCups(1);
        orderItem1.setId(123L);
        orderItem1.setOrderItemPrice(BigDecimal.valueOf(42L));
        when(this.orderItemMapper.toDto((OrderItem) any())).thenReturn(new OrderItemDto());
        when(this.orderItemMapper.toModel((OrderItemDto) any())).thenReturn(orderItem1);

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setCoffeeVarietyId(123L);
        orderItemDto.setCups(1);
        orderItemDto.setId(123L);
        orderItemDto.setOrderItemPrice(BigDecimal.valueOf(42L));
        String content = (new ObjectMapper()).writeValueAsString(orderItemDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/order/create_item")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.orderRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":null,\"coffeeVarietyId\":null,\"cups\":null,\"orderItemPrice\":null}"));
    }

    @Test
    void testDeleteOrderById() throws Exception {
        doNothing().when(this.orderService).delete((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/order/delete/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.orderRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeleteOrderById2() throws Exception {
        doNothing().when(this.orderService).delete((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/order/delete/{id}", 123L);
        deleteResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.orderRestController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetAllOrders() throws Exception {
        when(this.orderService.findAll()).thenReturn(new ArrayList<>());
        when(this.orderMapper.toDto((java.util.List<Order>) any())).thenReturn(new ArrayList<>());
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
        when(this.orderService.findAll()).thenReturn(new ArrayList<>());
        when(this.orderMapper.toDto((java.util.List<Order>) any())).thenReturn(new ArrayList<>());
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
        when(this.orderService.findById((Long) any())).thenReturn(order);
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
        when(this.orderService.findById((Long) any())).thenReturn(order);
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

    @Test
    void testUpdateOrder() throws Exception {
        Order order = new Order();
        order.setCustomerName("Customer Name");
        order.setDeliveryAddress("42 Main St");
        order.setId(123L);
        order.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderItems(new ArrayList<>());
        order.setOrderPrice(BigDecimal.valueOf(42L));
        when(this.orderService.edit((Order) any())).thenReturn(order);

        Order order1 = new Order();
        order1.setCustomerName("Customer Name");
        order1.setDeliveryAddress("42 Main St");
        order1.setId(123L);
        order1.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setOrderItems(new ArrayList<>());
        order1.setOrderPrice(BigDecimal.valueOf(42L));
        when(this.orderMapper.toDto((Order) any())).thenReturn(new OrderDto());
        when(this.orderMapper.toModel((OrderDto) any())).thenReturn(order1);

        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerName("Customer Name");
        orderDto.setDeliveryAddress("42 Main St");
        orderDto.setId(123L);
        orderDto.setOrderDateTime(null);
        orderDto.setOrderItemDtos(new ArrayList<>());
        orderDto.setOrderPrice(BigDecimal.valueOf(42L));
        String content = (new ObjectMapper()).writeValueAsString(orderDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/order/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.orderRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":null,\"orderDateTime\":null,\"customerName\":null,\"deliveryAddress\":null,\"orderPrice\":null,\"orderItemDtos"
                                        + "\":null}"));
    }
}

