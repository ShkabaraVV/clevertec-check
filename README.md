# Инструкция по запуску консольного приложения для формирования чека в магазине

1. **Скачать проект**

   Используйте командную строку (или терминал) и выполните следующую команду для скачивания проекта:

    ```sh
    git clone -b feature/entry-file https://github.com/ShkabaraVV/clevertec-check.git
    ```

2. **Перейти в директорию проекта**

   Используйте терминал и команду `cd`, чтобы перейти в директорию проекта в зависимости от его расположения на вашем компьютере. Настоятельно рекомендуется заранее создать папку для удобства перемещения:

    ```sh
    cd имя-папки (без тире)
    ```
3. **Создание файла проуктов**

   Создайте csv-файл для хранения продуктов в заданом формате:
   ```sh
   id;description;price;quantity_in_stock;wholesale_product
   1;Milk;1.07;10;true
   2;Cream 400g;2.71;20;true
   ```
4. **Сборка приложения**

   Запустите следующую команду для сборки приложения:

    ```sh
    .\gradlew build
    ```

5. **Запуск приложения**

   Используйте следующую команду для запуска приложения:

    ```sh
    java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java id-quantity discountCard=xxxx balanceDebitCard=xxxx
    ```

   Где:
    - `id-quantity` - номер товара и его количество
    - `discountCard=xxxx` - номер дисконтной карты
    - `balanceDebitCard=xxxx` - баланс на расчётной карте
    - `pathToFile=xxxx` - путь + название файла с расширением, в котором хранятся продукты
    - `saveToFile=xxxx` - путь + название файла с расширением для сохранениия 

   #### Пример запуска

    ```sh
    java -cp build/classes/java/main ru.clevertec.check.CheckRunner 3-5 2-3 4-1 discountCard=1111 balanceDebitCard=100 saveToFile=./error_result.csv
    ```

Надеюсь, эта инструкция поможет вам запустить приложение. Если у вас возникнут вопросы, пожалуйста, свяжитесь со мной по указанной ниже почте.

Почта для связи: [shkabaraw@gmail.com](https://mail.google.com/mail/u/0/?view=cm&fs=1&to=shkabaraw@gmail.com)

