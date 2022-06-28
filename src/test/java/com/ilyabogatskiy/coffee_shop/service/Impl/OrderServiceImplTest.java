package com.ilyabogatskiy.coffee_shop.service.Impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ilyabogatskiy.coffee_shop.exception.CoffeeVarietyNotFoundException;
import com.ilyabogatskiy.coffee_shop.exception.OrderNotFoundException;
import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;
import com.ilyabogatskiy.coffee_shop.models.Order;
import com.ilyabogatskiy.coffee_shop.models.OrderItem;
import com.ilyabogatskiy.coffee_shop.repository.CoffeeVarietyRepository;
import com.ilyabogatskiy.coffee_shop.repository.OrderItemRepository;
import com.ilyabogatskiy.coffee_shop.repository.OrderRepository;
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
    private CoffeeVarietyRepository coffeeVarietyRepository;

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
        order.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
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
    void testDelete() {
        Order order = new Order();
        order.setCustomerName("Customer Name");
        order.setDeliveryAddress("42 Main St");
        order.setId(123L);
        order.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
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
        order.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
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

    @Test
    void testAdd() {
        Order order = new Order();
        order.setCustomerName("Customer Name");
        order.setDeliveryAddress("42 Main St");
        order.setId(123L);
        order.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderItems(new ArrayList<>());
        order.setOrderPrice(BigDecimal.valueOf(42L));
        when(this.orderRepository.saveAndFlush((Order) any())).thenReturn(order);

        Order order1 = new Order();
        order1.setCustomerName("Customer Name");
        order1.setDeliveryAddress("42 Main St");
        order1.setId(123L);
        order1.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setOrderItems(new ArrayList<>());
        order1.setOrderPrice(BigDecimal.valueOf(42L));
        when(this.orderPriceCalculationService.orderPriceCalculation((Order) any())).thenReturn(order1);

        Order order2 = new Order();
        order2.setCustomerName("Customer Name");
        order2.setDeliveryAddress("42 Main St");
        order2.setId(123L);
        order2.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order2.setOrderItems(new ArrayList<>());
        order2.setOrderPrice(BigDecimal.valueOf(42L));
        Order actualAddResult = this.orderServiceImpl.add(order2);
        assertSame(order, actualAddResult);
        assertEquals("42", actualAddResult.getOrderPrice().toString());
        verify(this.orderRepository).saveAndFlush((Order) any());
        verify(this.orderPriceCalculationService).orderPriceCalculation((Order) any());
        assertEquals("Customer Name", order2.getCustomerName());
        assertEquals("42 Main St", order2.getDeliveryAddress());
        assertEquals("0001-01-01", order2.getOrderDateTime().toLocalDate().toString());
    }

    @Test
    void testAdd2() {
        Order order = new Order();
        order.setCustomerName("Customer Name");
        order.setDeliveryAddress("42 Main St");
        order.setId(123L);
        order.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderItems(new ArrayList<>());
        order.setOrderPrice(BigDecimal.valueOf(42L));
        when(this.orderRepository.saveAndFlush((Order) any())).thenReturn(order);
        when(this.orderPriceCalculationService.orderPriceCalculation((Order) any()))
                .thenThrow(new OrderNotFoundException("An error occurred"));

        Order order1 = new Order();
        order1.setCustomerName("Customer Name");
        order1.setDeliveryAddress("42 Main St");
        order1.setId(123L);
        order1.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
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
        order.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderItems(new ArrayList<>());
        order.setOrderPrice(BigDecimal.valueOf(42L));
        when(this.orderRepository.saveAndFlush((Order) any())).thenReturn(order);

        Order order1 = new Order();
        order1.setCustomerName("Customer Name");
        order1.setDeliveryAddress("42 Main St");
        order1.setId(123L);
        order1.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setOrderItems(new ArrayList<>());
        order1.setOrderPrice(BigDecimal.valueOf(42L));

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
        when(this.orderPriceCalculationService.orderItemPriceCalculation((OrderItem) any())).thenReturn(orderItem);
        when(this.orderPriceCalculationService.orderPriceCalculation((Order) any())).thenReturn(order1);

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
        when(this.orderItemRepository.saveAndFlush((OrderItem) any())).thenReturn(orderItem1);

        CoffeeVariety coffeeVariety2 = new CoffeeVariety();
        coffeeVariety2.setAvailable(true);
        coffeeVariety2.setId(123L);
        coffeeVariety2.setName("Name");
        coffeeVariety2.setPrice(BigDecimal.valueOf(42L));
        Optional<CoffeeVariety> ofResult = Optional.of(coffeeVariety2);
        when(this.coffeeVarietyRepository.findById((Long) any())).thenReturn(ofResult);

        CoffeeVariety coffeeVariety3 = new CoffeeVariety();
        coffeeVariety3.setAvailable(true);
        coffeeVariety3.setId(123L);
        coffeeVariety3.setName("Заказ ({}) на сумму ({}) добавлен");
        coffeeVariety3.setPrice(BigDecimal.valueOf(42L));

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setCoffeeVariety(coffeeVariety3);
        orderItem2.setCups(1);
        orderItem2.setId(123L);
        orderItem2.setOrderItemPrice(BigDecimal.valueOf(42L));

        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem2);

        Order order2 = new Order();
        order2.setCustomerName("Customer Name");
        order2.setDeliveryAddress("42 Main St");
        order2.setId(123L);
        order2.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order2.setOrderItems(orderItemList);
        order2.setOrderPrice(BigDecimal.valueOf(42L));
        Order actualAddResult = this.orderServiceImpl.add(order2);
        assertSame(order, actualAddResult);
        assertEquals("42", actualAddResult.getOrderPrice().toString());
        verify(this.orderRepository).saveAndFlush((Order) any());
        verify(this.orderPriceCalculationService).orderPriceCalculation((Order) any());
        verify(this.orderPriceCalculationService).orderItemPriceCalculation((OrderItem) any());
        verify(this.orderItemRepository).saveAndFlush((OrderItem) any());
        verify(this.coffeeVarietyRepository).findById((Long) any());
        assertEquals("Customer Name", order2.getCustomerName());
        assertEquals("42 Main St", order2.getDeliveryAddress());
        assertEquals("01:01", order2.getOrderDateTime().toLocalTime().toString());
        OrderItem getResult = order2.getOrderItems().get(0);
        assertEquals(coffeeVariety, getResult.getCoffeeVariety());
        assertEquals(1, getResult.getCups().intValue());
    }

    @Test
    void testAdd4() {
        Order order = new Order();
        order.setCustomerName("Customer Name");
        order.setDeliveryAddress("42 Main St");
        order.setId(123L);
        order.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderItems(new ArrayList<>());
        order.setOrderPrice(BigDecimal.valueOf(42L));
        when(this.orderRepository.saveAndFlush((Order) any())).thenReturn(order);

        Order order1 = new Order();
        order1.setCustomerName("Customer Name");
        order1.setDeliveryAddress("42 Main St");
        order1.setId(123L);
        order1.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setOrderItems(new ArrayList<>());
        order1.setOrderPrice(BigDecimal.valueOf(42L));

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
        when(this.orderPriceCalculationService.orderItemPriceCalculation((OrderItem) any())).thenReturn(orderItem);
        when(this.orderPriceCalculationService.orderPriceCalculation((Order) any())).thenReturn(order1);
        when(this.orderItemRepository.saveAndFlush((OrderItem) any()))
                .thenThrow(new OrderNotFoundException("An error occurred"));

        CoffeeVariety coffeeVariety1 = new CoffeeVariety();
        coffeeVariety1.setAvailable(true);
        coffeeVariety1.setId(123L);
        coffeeVariety1.setName("Name");
        coffeeVariety1.setPrice(BigDecimal.valueOf(42L));
        Optional<CoffeeVariety> ofResult = Optional.of(coffeeVariety1);
        when(this.coffeeVarietyRepository.findById((Long) any())).thenReturn(ofResult);

        CoffeeVariety coffeeVariety2 = new CoffeeVariety();
        coffeeVariety2.setAvailable(true);
        coffeeVariety2.setId(123L);
        coffeeVariety2.setName("Заказ ({}) на сумму ({}) добавлен");
        coffeeVariety2.setPrice(BigDecimal.valueOf(42L));

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setCoffeeVariety(coffeeVariety2);
        orderItem1.setCups(1);
        orderItem1.setId(123L);
        orderItem1.setOrderItemPrice(BigDecimal.valueOf(42L));

        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem1);

        Order order2 = new Order();
        order2.setCustomerName("Customer Name");
        order2.setDeliveryAddress("42 Main St");
        order2.setId(123L);
        order2.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order2.setOrderItems(orderItemList);
        order2.setOrderPrice(BigDecimal.valueOf(42L));
        assertThrows(OrderNotFoundException.class, () -> this.orderServiceImpl.add(order2));
        verify(this.orderPriceCalculationService).orderItemPriceCalculation((OrderItem) any());
        verify(this.orderItemRepository).saveAndFlush((OrderItem) any());
        verify(this.coffeeVarietyRepository).findById((Long) any());
    }

    @Test
    void testAddItem() {
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
        when(this.orderPriceCalculationService.orderItemPriceCalculation((OrderItem) any())).thenReturn(orderItem);

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
        when(this.orderItemRepository.saveAndFlush((OrderItem) any())).thenReturn(orderItem1);

        CoffeeVariety coffeeVariety2 = new CoffeeVariety();
        coffeeVariety2.setAvailable(true);
        coffeeVariety2.setId(123L);
        coffeeVariety2.setName("Name");
        coffeeVariety2.setPrice(BigDecimal.valueOf(42L));
        Optional<CoffeeVariety> ofResult = Optional.of(coffeeVariety2);
        when(this.coffeeVarietyRepository.findById((Long) any())).thenReturn(ofResult);

        CoffeeVariety coffeeVariety3 = new CoffeeVariety();
        coffeeVariety3.setAvailable(true);
        coffeeVariety3.setId(123L);
        coffeeVariety3.setName("Name");
        coffeeVariety3.setPrice(BigDecimal.valueOf(42L));

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setCoffeeVariety(coffeeVariety3);
        orderItem2.setCups(1);
        orderItem2.setId(123L);
        orderItem2.setOrderItemPrice(BigDecimal.valueOf(42L));
        OrderItem actualAddItemResult = this.orderServiceImpl.addItem(orderItem2);
        assertSame(orderItem1, actualAddItemResult);
        assertEquals("42", actualAddItemResult.getOrderItemPrice().toString());
        assertEquals("42", actualAddItemResult.getCoffeeVariety().getPrice().toString());
        verify(this.orderPriceCalculationService).orderItemPriceCalculation((OrderItem) any());
        verify(this.orderItemRepository).saveAndFlush((OrderItem) any());
        verify(this.coffeeVarietyRepository).findById((Long) any());
        assertEquals(coffeeVariety3, orderItem2.getCoffeeVariety());
        assertEquals(1, orderItem2.getCups().intValue());
    }

    @Test
    void testAddItem2() {
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
        when(this.orderPriceCalculationService.orderItemPriceCalculation((OrderItem) any())).thenReturn(orderItem);
        when(this.orderItemRepository.saveAndFlush((OrderItem) any()))
                .thenThrow(new OrderNotFoundException("An error occurred"));

        CoffeeVariety coffeeVariety1 = new CoffeeVariety();
        coffeeVariety1.setAvailable(true);
        coffeeVariety1.setId(123L);
        coffeeVariety1.setName("Name");
        coffeeVariety1.setPrice(BigDecimal.valueOf(42L));
        Optional<CoffeeVariety> ofResult = Optional.of(coffeeVariety1);
        when(this.coffeeVarietyRepository.findById((Long) any())).thenReturn(ofResult);

        CoffeeVariety coffeeVariety2 = new CoffeeVariety();
        coffeeVariety2.setAvailable(true);
        coffeeVariety2.setId(123L);
        coffeeVariety2.setName("Name");
        coffeeVariety2.setPrice(BigDecimal.valueOf(42L));

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setCoffeeVariety(coffeeVariety2);
        orderItem1.setCups(1);
        orderItem1.setId(123L);
        orderItem1.setOrderItemPrice(BigDecimal.valueOf(42L));
        assertThrows(OrderNotFoundException.class, () -> this.orderServiceImpl.addItem(orderItem1));
        verify(this.orderPriceCalculationService).orderItemPriceCalculation((OrderItem) any());
        verify(this.orderItemRepository).saveAndFlush((OrderItem) any());
        verify(this.coffeeVarietyRepository).findById((Long) any());
    }

    @Test
    void testAddItem3() {
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
        when(this.orderPriceCalculationService.orderItemPriceCalculation((OrderItem) any())).thenReturn(orderItem);

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
        when(this.orderItemRepository.saveAndFlush((OrderItem) any())).thenReturn(orderItem1);
        when(this.coffeeVarietyRepository.findById((Long) any())).thenReturn(Optional.empty());

        CoffeeVariety coffeeVariety2 = new CoffeeVariety();
        coffeeVariety2.setAvailable(true);
        coffeeVariety2.setId(123L);
        coffeeVariety2.setName("Name");
        coffeeVariety2.setPrice(BigDecimal.valueOf(42L));

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setCoffeeVariety(coffeeVariety2);
        orderItem2.setCups(1);
        orderItem2.setId(123L);
        orderItem2.setOrderItemPrice(BigDecimal.valueOf(42L));
        assertThrows(CoffeeVarietyNotFoundException.class, () -> this.orderServiceImpl.addItem(orderItem2));
        verify(this.coffeeVarietyRepository).findById((Long) any());
    }

    @Test
    void testAddItem4() {
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
        when(this.orderPriceCalculationService.orderItemPriceCalculation((OrderItem) any())).thenReturn(orderItem);

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
        when(this.orderItemRepository.saveAndFlush((OrderItem) any())).thenReturn(orderItem1);

        CoffeeVariety coffeeVariety2 = new CoffeeVariety();
        coffeeVariety2.setAvailable(true);
        coffeeVariety2.setId(123L);
        coffeeVariety2.setName("Name");
        coffeeVariety2.setPrice(BigDecimal.valueOf(42L));
        Optional<CoffeeVariety> ofResult = Optional.of(coffeeVariety2);
        when(this.coffeeVarietyRepository.findById((Long) any())).thenReturn(ofResult);

        CoffeeVariety coffeeVariety3 = new CoffeeVariety();
        coffeeVariety3.setAvailable(true);
        coffeeVariety3.setId(123L);
        coffeeVariety3.setName("Name");
        coffeeVariety3.setPrice(BigDecimal.valueOf(42L));

        CoffeeVariety coffeeVariety4 = new CoffeeVariety();
        coffeeVariety4.setAvailable(true);
        coffeeVariety4.setId(123L);
        coffeeVariety4.setName("Name");
        coffeeVariety4.setPrice(BigDecimal.valueOf(42L));
        OrderItem orderItem2 = mock(OrderItem.class);
        when(orderItem2.getCups()).thenThrow(new CoffeeVarietyNotFoundException("An error occurred"));
        when(orderItem2.getId()).thenThrow(new CoffeeVarietyNotFoundException("An error occurred"));
        when(orderItem2.getCoffeeVariety()).thenReturn(coffeeVariety4);
        doNothing().when(orderItem2).setCoffeeVariety((CoffeeVariety) any());
        doNothing().when(orderItem2).setCups((Integer) any());
        doNothing().when(orderItem2).setId((Long) any());
        doNothing().when(orderItem2).setOrderItemPrice((BigDecimal) any());
        orderItem2.setCoffeeVariety(coffeeVariety3);
        orderItem2.setCups(1);
        orderItem2.setId(123L);
        orderItem2.setOrderItemPrice(BigDecimal.valueOf(42L));
        assertThrows(CoffeeVarietyNotFoundException.class, () -> this.orderServiceImpl.addItem(orderItem2));
        verify(this.coffeeVarietyRepository).findById((Long) any());
        verify(orderItem2).getCoffeeVariety();
        verify(orderItem2).getCups();
        verify(orderItem2, atLeast(1)).setCoffeeVariety((CoffeeVariety) any());
        verify(orderItem2).setCups((Integer) any());
        verify(orderItem2).setId((Long) any());
        verify(orderItem2).setOrderItemPrice((BigDecimal) any());
    }

    @Test
    void testEdit() {
        Order order = new Order();
        order.setCustomerName("Customer Name");
        order.setDeliveryAddress("42 Main St");
        order.setId(123L);
        order.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderItems(new ArrayList<>());
        order.setOrderPrice(BigDecimal.valueOf(42L));
        Optional<Order> ofResult = Optional.of(order);

        Order order1 = new Order();
        order1.setCustomerName("Customer Name");
        order1.setDeliveryAddress("42 Main St");
        order1.setId(123L);
        order1.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setOrderItems(new ArrayList<>());
        order1.setOrderPrice(BigDecimal.valueOf(42L));
        when(this.orderRepository.saveAndFlush((Order) any())).thenReturn(order1);
        when(this.orderRepository.findById((Long) any())).thenReturn(ofResult);

        Order order2 = new Order();
        order2.setCustomerName("Customer Name");
        order2.setDeliveryAddress("42 Main St");
        order2.setId(123L);
        order2.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order2.setOrderItems(new ArrayList<>());
        order2.setOrderPrice(BigDecimal.valueOf(42L));
        when(this.orderPriceCalculationService.orderPriceCalculation((Order) any())).thenReturn(order2);

        Order order3 = new Order();
        order3.setCustomerName("Customer Name");
        order3.setDeliveryAddress("42 Main St");
        order3.setId(123L);
        order3.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order3.setOrderItems(new ArrayList<>());
        order3.setOrderPrice(BigDecimal.valueOf(42L));
        Order actualEditResult = this.orderServiceImpl.edit(order3);
        assertSame(order1, actualEditResult);
        assertEquals("42", actualEditResult.getOrderPrice().toString());
        verify(this.orderRepository).saveAndFlush((Order) any());
        verify(this.orderRepository).findById((Long) any());
        verify(this.orderPriceCalculationService).orderPriceCalculation((Order) any());
        assertEquals("Customer Name", order3.getCustomerName());
        assertEquals("0001-01-01", order3.getOrderDateTime().toLocalDate().toString());
    }

    @Test
    void testEdit2() {
        Order order = new Order();
        order.setCustomerName("Customer Name");
        order.setDeliveryAddress("42 Main St");
        order.setId(123L);
        order.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderItems(new ArrayList<>());
        order.setOrderPrice(BigDecimal.valueOf(42L));
        Optional<Order> ofResult = Optional.of(order);

        Order order1 = new Order();
        order1.setCustomerName("Customer Name");
        order1.setDeliveryAddress("42 Main St");
        order1.setId(123L);
        order1.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setOrderItems(new ArrayList<>());
        order1.setOrderPrice(BigDecimal.valueOf(42L));
        when(this.orderRepository.saveAndFlush((Order) any())).thenReturn(order1);
        when(this.orderRepository.findById((Long) any())).thenReturn(ofResult);
        when(this.orderPriceCalculationService.orderPriceCalculation((Order) any()))
                .thenThrow(new OrderNotFoundException("An error occurred"));

        Order order2 = new Order();
        order2.setCustomerName("Customer Name");
        order2.setDeliveryAddress("42 Main St");
        order2.setId(123L);
        order2.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order2.setOrderItems(new ArrayList<>());
        order2.setOrderPrice(BigDecimal.valueOf(42L));
        assertThrows(OrderNotFoundException.class, () -> this.orderServiceImpl.edit(order2));
        verify(this.orderRepository).findById((Long) any());
        verify(this.orderPriceCalculationService).orderPriceCalculation((Order) any());
    }

    @Test
    void testEdit3() {
        Order order = new Order();
        order.setCustomerName("Customer Name");
        order.setDeliveryAddress("42 Main St");
        order.setId(123L);
        order.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderItems(new ArrayList<>());
        order.setOrderPrice(BigDecimal.valueOf(42L));
        when(this.orderRepository.saveAndFlush((Order) any())).thenReturn(order);
        when(this.orderRepository.findById((Long) any())).thenReturn(Optional.empty());

        Order order1 = new Order();
        order1.setCustomerName("Customer Name");
        order1.setDeliveryAddress("42 Main St");
        order1.setId(123L);
        order1.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setOrderItems(new ArrayList<>());
        order1.setOrderPrice(BigDecimal.valueOf(42L));
        when(this.orderPriceCalculationService.orderPriceCalculation((Order) any())).thenReturn(order1);

        Order order2 = new Order();
        order2.setCustomerName("Customer Name");
        order2.setDeliveryAddress("42 Main St");
        order2.setId(123L);
        order2.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order2.setOrderItems(new ArrayList<>());
        order2.setOrderPrice(BigDecimal.valueOf(42L));
        assertThrows(OrderNotFoundException.class, () -> this.orderServiceImpl.edit(order2));
        verify(this.orderRepository).findById((Long) any());
    }

    @Test
    void testEdit4() {
        Order order = new Order();
        order.setCustomerName("Customer Name");
        order.setDeliveryAddress("42 Main St");
        order.setId(123L);
        order.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderItems(new ArrayList<>());
        order.setOrderPrice(BigDecimal.valueOf(42L));
        Optional<Order> ofResult = Optional.of(order);

        Order order1 = new Order();
        order1.setCustomerName("Customer Name");
        order1.setDeliveryAddress("42 Main St");
        order1.setId(123L);
        order1.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setOrderItems(new ArrayList<>());
        order1.setOrderPrice(BigDecimal.valueOf(42L));
        when(this.orderRepository.saveAndFlush((Order) any())).thenReturn(order1);
        when(this.orderRepository.findById((Long) any())).thenReturn(ofResult);

        Order order2 = new Order();
        order2.setCustomerName("Customer Name");
        order2.setDeliveryAddress("42 Main St");
        order2.setId(123L);
        order2.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order2.setOrderItems(new ArrayList<>());
        order2.setOrderPrice(BigDecimal.valueOf(42L));

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
        when(this.orderPriceCalculationService.orderItemPriceCalculation((OrderItem) any())).thenReturn(orderItem);
        when(this.orderPriceCalculationService.orderPriceCalculation((Order) any())).thenReturn(order2);

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
        when(this.orderItemRepository.saveAndFlush((OrderItem) any())).thenReturn(orderItem1);

        CoffeeVariety coffeeVariety2 = new CoffeeVariety();
        coffeeVariety2.setAvailable(true);
        coffeeVariety2.setId(123L);
        coffeeVariety2.setName("Name");
        coffeeVariety2.setPrice(BigDecimal.valueOf(42L));
        Optional<CoffeeVariety> ofResult1 = Optional.of(coffeeVariety2);
        when(this.coffeeVarietyRepository.findById((Long) any())).thenReturn(ofResult1);

        CoffeeVariety coffeeVariety3 = new CoffeeVariety();
        coffeeVariety3.setAvailable(true);
        coffeeVariety3.setId(123L);
        coffeeVariety3.setName("Заказ ({}) изменен, сумма перерасчитана ({})");
        coffeeVariety3.setPrice(BigDecimal.valueOf(42L));

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setCoffeeVariety(coffeeVariety3);
        orderItem2.setCups(1);
        orderItem2.setId(123L);
        orderItem2.setOrderItemPrice(BigDecimal.valueOf(42L));

        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem2);

        Order order3 = new Order();
        order3.setCustomerName("Customer Name");
        order3.setDeliveryAddress("42 Main St");
        order3.setId(123L);
        order3.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order3.setOrderItems(orderItemList);
        order3.setOrderPrice(BigDecimal.valueOf(42L));
        Order actualEditResult = this.orderServiceImpl.edit(order3);
        assertSame(order1, actualEditResult);
        assertEquals("42", actualEditResult.getOrderPrice().toString());
        verify(this.orderRepository).saveAndFlush((Order) any());
        verify(this.orderRepository).findById((Long) any());
        verify(this.orderPriceCalculationService).orderPriceCalculation((Order) any());
        verify(this.orderPriceCalculationService).orderItemPriceCalculation((OrderItem) any());
        verify(this.orderItemRepository).saveAndFlush((OrderItem) any());
        verify(this.coffeeVarietyRepository).findById((Long) any());
        assertEquals("Customer Name", order3.getCustomerName());
        assertEquals("01:01", order3.getOrderDateTime().toLocalTime().toString());
        OrderItem getResult = order3.getOrderItems().get(0);
        assertEquals(coffeeVariety, getResult.getCoffeeVariety());
        assertEquals(1, getResult.getCups().intValue());
    }

    @Test
    void testEdit5() {
        Order order = new Order();
        order.setCustomerName("Customer Name");
        order.setDeliveryAddress("42 Main St");
        order.setId(123L);
        order.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order.setOrderItems(new ArrayList<>());
        order.setOrderPrice(BigDecimal.valueOf(42L));
        Optional<Order> ofResult = Optional.of(order);

        Order order1 = new Order();
        order1.setCustomerName("Customer Name");
        order1.setDeliveryAddress("42 Main St");
        order1.setId(123L);
        order1.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order1.setOrderItems(new ArrayList<>());
        order1.setOrderPrice(BigDecimal.valueOf(42L));
        when(this.orderRepository.saveAndFlush((Order) any())).thenReturn(order1);
        when(this.orderRepository.findById((Long) any())).thenReturn(ofResult);

        Order order2 = new Order();
        order2.setCustomerName("Customer Name");
        order2.setDeliveryAddress("42 Main St");
        order2.setId(123L);
        order2.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order2.setOrderItems(new ArrayList<>());
        order2.setOrderPrice(BigDecimal.valueOf(42L));

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
        when(this.orderPriceCalculationService.orderItemPriceCalculation((OrderItem) any())).thenReturn(orderItem);
        when(this.orderPriceCalculationService.orderPriceCalculation((Order) any())).thenReturn(order2);
        when(this.orderItemRepository.saveAndFlush((OrderItem) any()))
                .thenThrow(new OrderNotFoundException("An error occurred"));

        CoffeeVariety coffeeVariety1 = new CoffeeVariety();
        coffeeVariety1.setAvailable(true);
        coffeeVariety1.setId(123L);
        coffeeVariety1.setName("Name");
        coffeeVariety1.setPrice(BigDecimal.valueOf(42L));
        Optional<CoffeeVariety> ofResult1 = Optional.of(coffeeVariety1);
        when(this.coffeeVarietyRepository.findById((Long) any())).thenReturn(ofResult1);

        CoffeeVariety coffeeVariety2 = new CoffeeVariety();
        coffeeVariety2.setAvailable(true);
        coffeeVariety2.setId(123L);
        coffeeVariety2.setName("Заказ ({}) изменен, сумма перерасчитана ({})");
        coffeeVariety2.setPrice(BigDecimal.valueOf(42L));

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setCoffeeVariety(coffeeVariety2);
        orderItem1.setCups(1);
        orderItem1.setId(123L);
        orderItem1.setOrderItemPrice(BigDecimal.valueOf(42L));

        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem1);

        Order order3 = new Order();
        order3.setCustomerName("Customer Name");
        order3.setDeliveryAddress("42 Main St");
        order3.setId(123L);
        order3.setOrderDateTime(LocalDateTime.of(1, 1, 1, 1, 1));
        order3.setOrderItems(orderItemList);
        order3.setOrderPrice(BigDecimal.valueOf(42L));
        assertThrows(OrderNotFoundException.class, () -> this.orderServiceImpl.edit(order3));
        verify(this.orderRepository).findById((Long) any());
        verify(this.orderPriceCalculationService).orderItemPriceCalculation((OrderItem) any());
        verify(this.orderItemRepository).saveAndFlush((OrderItem) any());
        verify(this.coffeeVarietyRepository).findById((Long) any());
    }
}

