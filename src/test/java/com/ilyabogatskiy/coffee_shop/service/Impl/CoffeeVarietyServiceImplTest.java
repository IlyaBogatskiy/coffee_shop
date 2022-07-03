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

import com.ilyabogatskiy.coffee_shop.exception.CoffeeVarietyNotFoundException;
import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;
import com.ilyabogatskiy.coffee_shop.repository.CoffeeVarietyRepository;

import java.math.BigDecimal;
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

@ContextConfiguration(classes = {CoffeeVarietyServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CoffeeVarietyServiceImplTest {

    @MockBean
    private CoffeeVarietyRepository coffeeVarietyRepository;

    @Autowired
    private CoffeeVarietyServiceImpl coffeeVarietyServiceImpl;

    @Test
    void testFindAll() {
        ArrayList<CoffeeVariety> coffeeVarietyList = new ArrayList<>();
        when(this.coffeeVarietyRepository.findAll()).thenReturn(coffeeVarietyList);
        List<CoffeeVariety> actualFindAllResult = this.coffeeVarietyServiceImpl.findAll();
        assertSame(coffeeVarietyList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(this.coffeeVarietyRepository).findAll();
    }

    @Test
    void testFindAll2() {
        when(this.coffeeVarietyRepository.findAll()).thenThrow(new CoffeeVarietyNotFoundException("An error occurred"));
        assertThrows(CoffeeVarietyNotFoundException.class, () -> this.coffeeVarietyServiceImpl.findAll());
        verify(this.coffeeVarietyRepository).findAll();
    }

    @Test
    void testFindAllAvailable() {
        ArrayList<CoffeeVariety> coffeeVarietyList = new ArrayList<>();
        when(this.coffeeVarietyRepository.findCoffeeVarietyByAvailableIsTrue()).thenReturn(coffeeVarietyList);
        List<CoffeeVariety> actualFindAllAvailableResult = this.coffeeVarietyServiceImpl.findAllAvailable();
        assertSame(coffeeVarietyList, actualFindAllAvailableResult);
        assertTrue(actualFindAllAvailableResult.isEmpty());
        verify(this.coffeeVarietyRepository).findCoffeeVarietyByAvailableIsTrue();
    }

    @Test
    void testFindAllAvailable2() {
        when(this.coffeeVarietyRepository.findCoffeeVarietyByAvailableIsTrue())
                .thenThrow(new CoffeeVarietyNotFoundException("An error occurred"));
        assertThrows(CoffeeVarietyNotFoundException.class, () -> this.coffeeVarietyServiceImpl.findAllAvailable());
        verify(this.coffeeVarietyRepository).findCoffeeVarietyByAvailableIsTrue();
    }

    @Test
    void testFindById() {
        CoffeeVariety coffeeVariety = new CoffeeVariety();
        coffeeVariety.setAvailable(true);
        coffeeVariety.setId(123L);
        coffeeVariety.setName("Name");
        coffeeVariety.setPrice(BigDecimal.valueOf(42L));
        Optional<CoffeeVariety> ofResult = Optional.of(coffeeVariety);
        when(this.coffeeVarietyRepository.findById((Long) any())).thenReturn(ofResult);
        CoffeeVariety actualFindByIdResult = this.coffeeVarietyServiceImpl.findById(123L);
        assertSame(coffeeVariety, actualFindByIdResult);
        assertEquals("42", actualFindByIdResult.getPrice().toString());
        verify(this.coffeeVarietyRepository).findById((Long) any());
    }

    @Test
    void testFindById2() {
        when(this.coffeeVarietyRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(CoffeeVarietyNotFoundException.class, () -> this.coffeeVarietyServiceImpl.findById(123L));
        verify(this.coffeeVarietyRepository).findById((Long) any());
    }

    @Test
    void testFindById3() {
        when(this.coffeeVarietyRepository.findById((Long) any()))
                .thenThrow(new CoffeeVarietyNotFoundException("An error occurred"));
        assertThrows(CoffeeVarietyNotFoundException.class, () -> this.coffeeVarietyServiceImpl.findById(123L));
        verify(this.coffeeVarietyRepository).findById((Long) any());
    }

    @Test
    void testDelete() {
        CoffeeVariety coffeeVariety = new CoffeeVariety();
        coffeeVariety.setAvailable(true);
        coffeeVariety.setId(123L);
        coffeeVariety.setName("Name");
        coffeeVariety.setPrice(BigDecimal.valueOf(42L));
        Optional<CoffeeVariety> ofResult = Optional.of(coffeeVariety);
        doNothing().when(this.coffeeVarietyRepository).deleteById((Long) any());
        when(this.coffeeVarietyRepository.findById((Long) any())).thenReturn(ofResult);
        this.coffeeVarietyServiceImpl.delete(123L);
        verify(this.coffeeVarietyRepository, atLeast(1)).findById((Long) any());
        verify(this.coffeeVarietyRepository).deleteById((Long) any());
    }

    @Test
    void testDelete2() {
        CoffeeVariety coffeeVariety = new CoffeeVariety();
        coffeeVariety.setAvailable(true);
        coffeeVariety.setId(123L);
        coffeeVariety.setName("Name");
        coffeeVariety.setPrice(BigDecimal.valueOf(42L));
        Optional<CoffeeVariety> ofResult = Optional.of(coffeeVariety);
        doThrow(new CoffeeVarietyNotFoundException("An error occurred")).when(this.coffeeVarietyRepository)
                .deleteById((Long) any());
        when(this.coffeeVarietyRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(CoffeeVarietyNotFoundException.class, () -> this.coffeeVarietyServiceImpl.delete(123L));
        verify(this.coffeeVarietyRepository, atLeast(1)).findById((Long) any());
        verify(this.coffeeVarietyRepository).deleteById((Long) any());
    }

    @Test
    void testDelete3() {
        doNothing().when(this.coffeeVarietyRepository).deleteById((Long) any());
        when(this.coffeeVarietyRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(CoffeeVarietyNotFoundException.class, () -> this.coffeeVarietyServiceImpl.delete(123L));
        verify(this.coffeeVarietyRepository).findById((Long) any());
    }

    @Test
    void testAdd() {
        CoffeeVariety coffeeVariety = new CoffeeVariety();
        coffeeVariety.setAvailable(true);
        coffeeVariety.setId(123L);
        coffeeVariety.setName("Name");
        coffeeVariety.setPrice(BigDecimal.valueOf(42L));
        when(this.coffeeVarietyRepository.saveAndFlush((CoffeeVariety) any())).thenReturn(coffeeVariety);

        CoffeeVariety coffeeVariety1 = new CoffeeVariety();
        coffeeVariety1.setAvailable(true);
        coffeeVariety1.setId(123L);
        coffeeVariety1.setName("Name");
        coffeeVariety1.setPrice(BigDecimal.valueOf(42L));
        CoffeeVariety actualAddResult = this.coffeeVarietyServiceImpl.add(coffeeVariety1);
        assertSame(coffeeVariety, actualAddResult);
        assertEquals("42", actualAddResult.getPrice().toString());
        verify(this.coffeeVarietyRepository).saveAndFlush((CoffeeVariety) any());
    }

    @Test
    void testAdd2() {
        when(this.coffeeVarietyRepository.saveAndFlush((CoffeeVariety) any()))
                .thenThrow(new CoffeeVarietyNotFoundException("An error occurred"));

        CoffeeVariety coffeeVariety = new CoffeeVariety();
        coffeeVariety.setAvailable(true);
        coffeeVariety.setId(123L);
        coffeeVariety.setName("Name");
        coffeeVariety.setPrice(BigDecimal.valueOf(42L));
        assertThrows(CoffeeVarietyNotFoundException.class, () -> this.coffeeVarietyServiceImpl.add(coffeeVariety));
        verify(this.coffeeVarietyRepository).saveAndFlush((CoffeeVariety) any());
    }

    @Test
    void testEdit() {
        CoffeeVariety coffeeVariety = new CoffeeVariety();
        coffeeVariety.setAvailable(true);
        coffeeVariety.setId(123L);
        coffeeVariety.setName("Name");
        coffeeVariety.setPrice(BigDecimal.valueOf(42L));
        Optional<CoffeeVariety> ofResult = Optional.of(coffeeVariety);

        CoffeeVariety coffeeVariety1 = new CoffeeVariety();
        coffeeVariety1.setAvailable(true);
        coffeeVariety1.setId(123L);
        coffeeVariety1.setName("Name");
        coffeeVariety1.setPrice(BigDecimal.valueOf(42L));
        when(this.coffeeVarietyRepository.saveAndFlush((CoffeeVariety) any())).thenReturn(coffeeVariety1);
        when(this.coffeeVarietyRepository.findById((Long) any())).thenReturn(ofResult);

        CoffeeVariety coffeeVariety2 = new CoffeeVariety();
        coffeeVariety2.setAvailable(true);
        coffeeVariety2.setId(123L);
        coffeeVariety2.setName("Name");
        coffeeVariety2.setPrice(BigDecimal.valueOf(42L));
        CoffeeVariety actualEditResult = this.coffeeVarietyServiceImpl.edit(coffeeVariety2);
        assertSame(coffeeVariety1, actualEditResult);
        assertEquals("42", actualEditResult.getPrice().toString());
        verify(this.coffeeVarietyRepository).saveAndFlush((CoffeeVariety) any());
        verify(this.coffeeVarietyRepository).findById((Long) any());
    }

    @Test
    void testEdit2() {
        CoffeeVariety coffeeVariety = new CoffeeVariety();
        coffeeVariety.setAvailable(true);
        coffeeVariety.setId(123L);
        coffeeVariety.setName("Name");
        coffeeVariety.setPrice(BigDecimal.valueOf(42L));
        Optional<CoffeeVariety> ofResult = Optional.of(coffeeVariety);
        when(this.coffeeVarietyRepository.saveAndFlush((CoffeeVariety) any()))
                .thenThrow(new CoffeeVarietyNotFoundException("An error occurred"));
        when(this.coffeeVarietyRepository.findById((Long) any())).thenReturn(ofResult);

        CoffeeVariety coffeeVariety1 = new CoffeeVariety();
        coffeeVariety1.setAvailable(true);
        coffeeVariety1.setId(123L);
        coffeeVariety1.setName("Name");
        coffeeVariety1.setPrice(BigDecimal.valueOf(42L));
        assertThrows(CoffeeVarietyNotFoundException.class, () -> this.coffeeVarietyServiceImpl.edit(coffeeVariety1));
        verify(this.coffeeVarietyRepository).saveAndFlush((CoffeeVariety) any());
        verify(this.coffeeVarietyRepository).findById((Long) any());
    }

    @Test
    void testEdit3() {
        CoffeeVariety coffeeVariety = new CoffeeVariety();
        coffeeVariety.setAvailable(true);
        coffeeVariety.setId(123L);
        coffeeVariety.setName("Name");
        coffeeVariety.setPrice(BigDecimal.valueOf(42L));
        when(this.coffeeVarietyRepository.saveAndFlush((CoffeeVariety) any())).thenReturn(coffeeVariety);
        when(this.coffeeVarietyRepository.findById((Long) any())).thenReturn(Optional.empty());

        CoffeeVariety coffeeVariety1 = new CoffeeVariety();
        coffeeVariety1.setAvailable(true);
        coffeeVariety1.setId(123L);
        coffeeVariety1.setName("Name");
        coffeeVariety1.setPrice(BigDecimal.valueOf(42L));
        assertThrows(CoffeeVarietyNotFoundException.class, () -> this.coffeeVarietyServiceImpl.edit(coffeeVariety1));
        verify(this.coffeeVarietyRepository).findById((Long) any());
    }
}

