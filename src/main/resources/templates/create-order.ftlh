<#import "common.ftlh" as c>

<@c.page>
    <div> class="container mt-5" </div>
    Выберете кофе
    <ul class="list-group">
        <li class="list-group-item">
            <input class="form-check-input me-1" type="checkbox" value=""
                   aria-label="coffe_rade.id">
            <div class="col-md-3" style="background-color: bisque">
                Количество
            </div>
            Чекбокс id1.grade_name_ru(eng), price, disable
        </li>
        <li class="list-group-item">
            <input class="form-check-input me-1" type="checkbox" value="" aria-label="...">
            <div class="col-md-3" style="background-color: bisque">
                Количество
            </div>
            Чекбокс id2.grade_name_ru(eng), price, disable
        </li>
        <li class="list-group-item">
            <input class="form-check-input me-1" type="checkbox" value="" aria-label="...">
            <div class="col-md-3" style="background-color: bisque">
                Количество
            </div>
            Чекбокс id3.grade_name_ru, price, disable
        </li>
        <li class="list-group-item">
            <input class="form-check-input me-1" type="checkbox" value="" aria-label="...">
            <div class="col-md-3" style="background-color: bisque">
                Количество
            </div>
            Чекбокс id4.grade_name_ru(eng), price, disable
        </li>
        <li class="list-group-item">
            <input class="form-check-input me-1" type="checkbox" value="" aria-label="...">
            <div class="col-md-3" style="background-color: bisque">
                Количество
            </div>
            Чекбокс id5.grade_name_ru(eng), price, disable
        </li>
    </ul>
    <br>
    <!-- Меню доставки -->
    <div class="mb-3">
        <select class="form-select" required aria-label="select example">
            <option value="">Выберете способ доставки</option>
            <option value="1">Доставка</option>
            <option value="2">Самовывоз</option>
        </select>
    </div>
    <div class="col-md-12" style="background: aqua">
        Цена fullPrice
    </div>
<!-- Форма доставки -->
<div class="container">
    <div class="row">
        <div class="col-md-12" style="background-color: antiquewhite;">
            Выберете доставку
            <form class="was-validated">
                <div class="mb-3">
                    <label for="validationTextarea" class="form-label">Фамилия Имя Отчество</label>
                    <textarea class="form-control is-invalid" id="validationTextarea" placeholder="Иванов Иван Иванович" required></textarea>
                    <div class="invalid-feedback">
                        Введите данные.
                    </div>
                </div>

                <div class="mb-3">
                    <label for="validationTextarea" class="form-label">Адрес доставки</label>
                    <textarea class="form-control is-invalid" id="validationText" placeholder="г. Ткачев, улица Героев огорода, дом 1, корпус 2, квартира 3" required></textarea>
                    <div class="invalid-feedback">
                        Введите данные, если выбран способ доставки самовывоз, то пометьте об этом.
                    </div>
                </div>

                <div class="form-check mb-3">
                    <input type="checkbox" class="form-check-input" id="validationFormCheck1" required>
                    <label class="form-check-label" for="validationFormCheck1">Данные заполнены правильно</label>
                    <div class="invalid-feedback">Я подтверждаю правильность заполнения данных</div>
                </div>
                <div class="mb-3">
                    <button class="btn btn-primary" type="submit" onclick="location.href='/complete';">Отправить заказ</button>
                </div>
            </form>

        </div>
        <div class="col-md-12" style="background: aqua">
            Стоимость заказа fullPrice
        </div>
    </div>
</div>
</@c.page>