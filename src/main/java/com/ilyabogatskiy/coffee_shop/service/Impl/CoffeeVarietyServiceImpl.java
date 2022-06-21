package com.ilyabogatskiy.coffee_shop.service.Impl;

import com.ilyabogatskiy.coffee_shop.exception.CoffeeVarietyNotFoundException;
import com.ilyabogatskiy.coffee_shop.models.CoffeeVariety;
import com.ilyabogatskiy.coffee_shop.repository.CoffeeVarietyRepository;
import com.ilyabogatskiy.coffee_shop.service.CoffeeVarietyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoffeeVarietyServiceImpl implements CoffeeVarietyService {

    private final CoffeeVarietyRepository coffeeVarietyRepository;

    @Override
    public List<CoffeeVariety> findAll() {
        return coffeeVarietyRepository.findAll();
    }

    @Override
    public List<CoffeeVariety> findAllAvailable() {
        return coffeeVarietyRepository.findCoffeeVarietyByAvailableIsTrue();
    }

    @Override
    public CoffeeVariety findById(Long id) {
        CoffeeVariety maybeCoffeeVariety = coffeeVarietyRepository.findById(id).orElse(null);
        if (maybeCoffeeVariety == null) {
            log.warn("Сорта с указанным идентификатором ({}) нет в базе данных", id);
            throw new CoffeeVarietyNotFoundException("Сорта с указанным идентификатором нет в базе данных");
        }
        return maybeCoffeeVariety;
    }

    @Override
    public void delete(Long id) {
        CoffeeVariety maybeCoffeeVariety = coffeeVarietyRepository.findById(id).orElse(null);
        if (maybeCoffeeVariety == null) {
            log.warn("Сорта с указанным идентификатором ({}) нет в базе данных", id);
            throw new CoffeeVarietyNotFoundException("Сорта с указанным идентификатором нет в базе данных");
        }
        log.info("Сорт ({}) удалён", findById(id).getName());
        coffeeVarietyRepository.deleteById(id);
    }

    @Override
    public CoffeeVariety add(CoffeeVariety coffeeVariety) {
        log.info("Сорт ({}) добавлен", coffeeVariety.getName());
        return coffeeVarietyRepository.saveAndFlush(coffeeVariety);
    }

    @Override
    public CoffeeVariety edit(CoffeeVariety coffeeVariety) {
        CoffeeVariety maybeCoffeeVariety = coffeeVarietyRepository.findById(coffeeVariety.getId()).orElse(null);
        if (maybeCoffeeVariety == null) {
            log.warn("Сорта с указанным идентификатором ({}) нет в базе данных", coffeeVariety.getId());
            throw new CoffeeVarietyNotFoundException("Сорта с указанным идентификатором нет в базе данных");
        }
        log.info("Сорт ({}) изменен", coffeeVariety.getName());
        return coffeeVarietyRepository.saveAndFlush(coffeeVariety);
    }
}
