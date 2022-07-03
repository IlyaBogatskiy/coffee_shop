package com.ilyabogatskiy.coffee_shop.service.Impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ilyabogatskiy.coffee_shop.exception.OrderNotFoundException;
import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;
import com.ilyabogatskiy.coffee_shop.models.Order;
import com.ilyabogatskiy.coffee_shop.models.OrderItem;
import com.ilyabogatskiy.coffee_shop.repository.OrderItemRepository;
import com.ilyabogatskiy.coffee_shop.repository.OrderRepository;
import com.ilyabogatskiy.coffee_shop.service.CoffeeVarietyService;
import com.ilyabogatskiy.coffee_shop.service.OrderPriceCalculationService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {OrderServiceImpl.class})
@ExtendWith(SpringExtension.class)
class OrderServiceImplTest {

    @MockBean
    private CoffeeVarietyService coffeeVarietyService;

    @MockBean
    private OrderItemRepository orderItemRepository;

    @MockBean
    private OrderPriceCalculationService orderPriceCalculationService;

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @Test
    void testFindAll() {
        ArrayList<Order> orderList = new ArrayList<>();
        when(this.orderRepository.findAll()).thenReturn(orderList);
        List<Order> actualFindAllResult = this.orderServiceImpl.findAll();
        assertSame(orderList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(this.orderRepository).findAll();
    }

    @Test
    void testFindAll2() {
        when(this.orderRepository.findAll()).thenThrow(new OrderNotFoundException("An error occurred"));
        assertThrows(OrderNotFoundException.class, () -> this.orderServiceImpl.findAll());
        verify(this.orderRepository).findAll();
    }

    @Test
    void testFindById() {
        Order order = new Order();
        order.setCustomerName("Customer Name");
        order.setDeliveryAddress("42 Main St");
        order.setId(123L);
        order.setOrderDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderItems(new ArrayList<>());
        order.setOrderPrice(BigDecimal.valueOf(42L));
        Optional<Order> ofResult = Optional.of(order);
        when(this.orderRepository.findById((Long) any())).thenReturn(ofResult);
        Order actualFindByIdResult = this.orderServiceImpl.findById(123L);
        assertSame(order, actualFindByIdResult);
        assertEquals("42", actualFindByIdResult.getOrderPrice().toString());
        verify(this.orderRepository).findById((Long) any());
    }

    @Test
    void testFindById2() {
        when(this.orderRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> this.orderServiceImpl.findById(123L));
        verify(this.orderRepository).findById((Long) any());
    }

    @Test
    void testFindById3() {
        when(this.orderRepository.findById((Long) any())).thenThrow(new OrderNotFoundException("An error occurred"));
        assertThrows(OrderNotFoundException.class, () -> this.orderServiceImpl.findById(123L));
        verify(this.orderRepository).findById((Long) any());
    }

    @Test
    void testAdd() {
        Order order = new Order();
        order.setCustomerName("Customer Name");
        order.setDeliveryAddress("42 Main St");
        order.setId(123L);
        order.setOrderDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderItems(new ArrayList<>());
        order.setOrderPrice(BigDecimal.valueOf(42L));
        when(this.orderRepository.saveAndFlush((Order) any())).thenReturn(order);
        when(this.orderPriceCalculationService.orderPriceCalculation((Order) any())).thenReturn(BigDecimal.valueOf(42L));

        Order order1 = new Order();
        order1.setCustomerName("Customer Name");
        order1.setDeliveryAddress("42 Main St");
        order1.setId(123L);
        order1.setOrderDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setOrderItems(new ArrayList<>());
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        order1.setOrderPrice(valueOfResult);
        Order actualAddResult = this.orderServiceImpl.add(order1);
        assertSame(order, actualAddResult);
        assertEquals("42", actualAddResult.getOrderPrice().toString());
        verify(this.orderRepository).saveAndFlush((Order) any());
        verify(this.orderPriceCalculationService).orderPriceCalculation((Order) any());
        assertEquals(valueOfResult, order1.getOrderPrice());
    }

    @Test
    void testAdd2() {
        Order order = new Order();
        order.setCustomerName("Customer Name");
        order.setDeliveryAddress("42 Main St");
        order.setId(123L);
        order.setOrderDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderItems(new ArrayList<>());
        order.setOrderPrice(BigDecimal.valueOf(42L));
        when(this.orderRepository.saveAndFlush((Order) any())).thenReturn(order);
        when(this.orderPriceCalculationService.orderPriceCalculation((Order) any()))
                .thenThrow(new OrderNotFoundException("An error occurred"));

        Order order1 = new Order();
        order1.setCustomerName("Customer Name");
        order1.setDeliveryAddress("42 Main St");
        order1.setId(123L);
        order1.setOrderDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setOrderItems(new ArrayList<>());
        order1.setOrderPrice(BigDecimal.valueOf(42L));
        assertThrows(OrderNotFoundException.class, () -> this.orderServiceImpl.add(order1));
        verify(this.orderPriceCalculationService).orderPriceCalculation((Order) any());
    }

    @Test
    void testAdd3() {
        Order order = new Order();
        order.setCustomerName("Customer Name");
        order.setDeliveryAddress("42 Main St");
        order.setId(123L);
        order.setOrderDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderItems(new ArrayList<>());
        order.setOrderPrice(BigDecimal.valueOf(42L));
        when(this.orderRepository.saveAndFlush((Order) any())).thenReturn(order);
        when(this.orderPriceCalculationService.orderItemPriceCalculation((OrderItem) any()))
                .thenReturn(BigDecimal.valueOf(42L));
        when(this.orderPriceCalculationService.orderPriceCalculation((Order) any())).thenReturn(BigDecimal.valueOf(42L));

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
        when(this.orderItemRepository.saveAndFlush((OrderItem) any())).thenReturn(orderItem);

        CoffeeVariety coffeeVariety1 = new CoffeeVariety();
        coffeeVariety1.setAvailable(true);
        coffeeVariety1.setId(123L);
        coffeeVariety1.setName("Name");
        coffeeVariety1.setPrice(BigDecimal.valueOf(42L));
        when(this.coffeeVarietyService.findById((Long) any())).thenReturn(coffeeVariety1);

        CoffeeVariety coffeeVariety2 = new CoffeeVariety();
        coffeeVariety2.setAvailable(true);
        coffeeVariety2.setId(123L);
        coffeeVariety2.setName("Заказ добавлен на сумму ({}) добавлен");
        coffeeVariety2.setPrice(BigDecimal.valueOf(42L));

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setCoffeeVariety(coffeeVariety2);
        orderItem1.setCups(1);
        orderItem1.setId(123L);
        orderItem1.setOrderItemPrice(BigDecimal.valueOf(42L));

        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem1);

        Order order1 = new Order();
        order1.setCustomerName("Customer Name");
        order1.setDeliveryAddress("42 Main St");
        order1.setId(123L);
        order1.setOrderDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setOrderItems(orderItemList);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        order1.setOrderPrice(valueOfResult);
        Order actualAddResult = this.orderServiceImpl.add(order1);
        assertSame(order, actualAddResult);
        assertEquals("42", actualAddResult.getOrderPrice().toString());
        verify(this.orderRepository).saveAndFlush((Order) any());
        verify(this.orderPriceCalculationService).orderItemPriceCalculation((OrderItem) any());
        verify(this.orderPriceCalculationService).orderPriceCalculation((Order) any());
        verify(this.orderItemRepository).saveAndFlush((OrderItem) any());
        verify(this.coffeeVarietyService).findById((Long) any());
        assertEquals(valueOfResult, order1.getOrderPrice());
        OrderItem getResult = order1.getOrderItems().get(0);
        assertEquals(coffeeVariety, getResult.getCoffeeVariety());
        assertEquals(valueOfResult, getResult.getOrderItemPrice());
    }

    @Test
    void testAdd4() {
        Order order = new Order();
        order.setCustomerName("Customer Name");
        order.setDeliveryAddress("42 Main St");
        order.setId(123L);
        order.setOrderDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderItems(new ArrayList<>());
        order.setOrderPrice(BigDecimal.valueOf(42L));
        when(this.orderRepository.saveAndFlush((Order) any())).thenReturn(order);
        when(this.orderPriceCalculationService.orderItemPriceCalculation((OrderItem) any()))
                .thenReturn(BigDecimal.valueOf(42L));
        when(this.orderPriceCalculationService.orderPriceCalculation((Order) any())).thenReturn(BigDecimal.valueOf(42L));
        when(this.orderItemRepository.saveAndFlush((OrderItem) any()))
                .thenThrow(new OrderNotFoundException("An error occurred"));

        CoffeeVariety coffeeVariety = new CoffeeVariety();
        coffeeVariety.setAvailable(true);
        coffeeVariety.setId(123L);
        coffeeVariety.setName("Name");
        coffeeVariety.setPrice(BigDecimal.valueOf(42L));
        when(this.coffeeVarietyService.findById((Long) any())).thenReturn(coffeeVariety);

        CoffeeVariety coffeeVariety1 = new CoffeeVariety();
        coffeeVariety1.setAvailable(true);
        coffeeVariety1.setId(123L);
        coffeeVariety1.setName("Заказ добавлен на сумму ({}) добавлен");
        coffeeVariety1.setPrice(BigDecimal.valueOf(42L));

        OrderItem orderItem = new OrderItem();
        orderItem.setCoffeeVariety(coffeeVariety1);
        orderItem.setCups(1);
        orderItem.setId(123L);
        orderItem.setOrderItemPrice(BigDecimal.valueOf(42L));

        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem);

        Order order1 = new Order();
        order1.setCustomerName("Customer Name");
        order1.setDeliveryAddress("42 Main St");
        order1.setId(123L);
        order1.setOrderDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setOrderItems(orderItemList);
        order1.setOrderPrice(BigDecimal.valueOf(42L));
        assertThrows(OrderNotFoundException.class, () -> this.orderServiceImpl.add(order1));
        verify(this.orderPriceCalculationService).orderItemPriceCalculation((OrderItem) any());
        verify(this.orderItemRepository).saveAndFlush((OrderItem) any());
        verify(this.coffeeVarietyService).findById((Long) any());
    }

    @Test
    void testDelete() {
        Order order = new Order();
        order.setCustomerName("Customer Name");
        order.setDeliveryAddress("42 Main St");
        order.setId(123L);
        order.setOrderDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderItems(new ArrayList<>());
        order.setOrderPrice(BigDecimal.valueOf(42L));
        Optional<Order> ofResult = Optional.of(order);
        doNothing().when(this.orderRepository).deleteById((Long) any());
        when(this.orderRepository.findById((Long) any())).thenReturn(ofResult);
        this.orderServiceImpl.delete(123L);
        verify(this.orderRepository, atLeast(1)).findById((Long) any());
        verify(this.orderRepository).deleteById((Long) any());
    }

    @Test
    void testDelete2() {
        Order order = new Order();
        order.setCustomerName("Customer Name");
        order.setDeliveryAddress("42 Main St");
        order.setId(123L);
        order.setOrderDate(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderItems(new ArrayList<>());
        order.setOrderPrice(BigDecimal.valueOf(42L));
        Optional<Order> ofResult = Optional.of(order);
        doThrow(new OrderNotFoundException("An error occurred")).when(this.orderRepository).deleteById((Long) any());
        when(this.orderRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(OrderNotFoundException.class, () -> this.orderServiceImpl.delete(123L));
        verify(this.orderRepository, atLeast(1)).findById((Long) any());
        verify(this.orderRepository).deleteById((Long) any());
    }

    @Test
    void testDelete3() {
        doNothing().when(this.orderRepository).deleteById((Long) any());
        when(this.orderRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> this.orderServiceImpl.delete(123L));
        verify(this.orderRepository).findById((Long) any());
    }
}

