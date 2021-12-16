Название проверки | Фактический результат | Ожидаемый результат | Расположение |
--- | --- | --- | --- |
*Получение категории по валидному id* | Получено название категории | Получено название категории| CategoryTests |
*Получение категории по невалидному id* |Ошибка 404 Not Found | Ошибка 404 Not Found | CategoryTests |     
*Удаление продукта с валидным id* |	Продукт удален| Продукт удален | ProductDeleteTests|   
*Удаление продукта с невалидным id* | Ошибка 404 Not Found | Ошибка 404 Not Found | ProductDeleteTests|
*Удаление названия продукта* | Ошибка 400 Bad Request | Ошибка 400 Bad Request | ProductUpdateTests|  
*Обнуление цены продукта* | Ошибка 400 Bad Request | Ошибка 400 Bad Request | ProductUpdateTests|  
*Удаление цены продукта"* | Ошибка 400 Bad Request| Ошибка 400 Bad Request | ProductUpdateTests|  
*Получение продукта по валидному id* | Продукт получен | Продукт получен |ProductReadTests  |  
*Получение продукта по не валидному id* | Ошибка 404 Not Found | disc |ProductReadTests  | 
*Получение всех продуктов* | Список всех продуктов получен | Список всех продуктов получен|ProductReadTests  | 
*Создание продукта с валидными данными* | Продукт создан | Продукт создан | ProductCreateTests  | 
*Создание продукта с отрицательной ценой* | Ошибка 400 Bad Request | Ошибка 400 Bad Request  | ProductCreateTests  | 
*Создание продукта с пустой ценой* | Ошибка 400 Bad Request | Ошибка 400 Bad Request | ProductCreateTests  | 
*Создание продукта с нулевой ценой* | Ошибка 400 Bad Request | Ошибка 400 Bad Request  | ProductCreateTests  | 
