# Инструкция по запуску консольного приложения для формирования чека в магазине

1. **Скачать проект**

   Используйте командную строку (или терминал) и выполните следующую команду для скачивания проекта:

    ```sh
    git clone -b feature/entry-database https://github.com/ShkabaraVV/clevertec-check.git
    ```

2. **Перейти в директорию проекта**

   Используйте терминал и команду `cd`, чтобы перейти в директорию проекта в зависимости от его расположения на вашем компьютере. Настоятельно рекомендуется заранее создать папку для удобства перемещения:

    ```sh
    cd имя-папки (без тире)
    ```
3. **Сборка приложения**

   Запустите следующую команду для сборки приложения:

    ```sh
    ./gradlew build shadowJar
    ```

4. **Запуск приложения**

   Используйте следующую команду для запуска приложения:

    ```sh
    java -jar build/libs/check-all.jar id-quantity discountCard=xxxx balanceDebitCard=xxxx saveToFile=xxxx datasource.url=хххx datasource.username=хххx datasource.password=хххx
    ```

   Где:
    - `id-quantity` - номер товара и его количество
    - `discountCard=xxxx` - номер дисконтной карты
    - `balanceDebitCard=xxxx` - баланс на расчётной карте
    - `saveToFile=xxxx` - путь + название файла с расширением для сохранениия
    - `datasource.url=хххx` - url к базе данных 
    - `datasource.username=хххx` - имя пользователя
    - `datasource.password=хххx` - пароль


   #### Пример запуска

   ```sh
    java -jar build/libs/check-all.jar 1-1 discountCard=3333 balanceDebitCard=100 saveToFile=./result.csv datasource.url=jdbc:postgresql://localhost:5432/check datasource.username=postgres datasource.password=postgres
   ```

Надеюсь, эта инструкция поможет вам запустить приложение. Если у вас возникнут вопросы, пожалуйста, свяжитесь со мной по указанной ниже почте.

Почта для связи: [shkabaraw@gmail.com](https://mail.google.com/mail/u/0/?view=cm&fs=1&to=shkabaraw@gmail.com)

