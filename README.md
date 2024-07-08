# Инструкция по запуску консольного приложения для формирования чека в магазине

1. **Скачать проект**

   Используйте командную строку (или терминал) и выполните следующую команду для скачивания проекта:

    ```sh
    git clone -b feature/entry-core https://github.com/ShkabaraVV/clevertec-check.git
    ```

2. **Перейти в директорию проекта**

   Используйте терминал и команду `cd`, чтобы перейти в директорию проекта в зависимости от его расположения на вашем компьютере. Настоятельно рекомендуется заранее создать папку для удобства перемещения:

    ```sh
    cd имя-папки (без тире)
    ```

3. **Сборка приложения**

   Запустите следующую команду для сборки приложения:

    ```sh
    .\gradlew build
    ```

4. **Запуск приложения**

   Используйте следующую команду для запуска приложения:

    ```sh
    java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java id-quantity discountCard=xxxx balanceDebitCard=xxxx
    ```

   Где:
    - `id-quantity` - номер товара и его количество
    - `discountCard=xxxx` - номер дисконтной карты
    - `balanceDebitCard=xxxx` - баланс на расчётной карте

   #### Пример запуска

    ```sh
    java -cp build/classes/java/main ru.clevertec.check.CheckRunner 3-5 2-3 4-1 discountCard=1111 balanceDebitCard=100
    ```

Надеюсь, эта инструкция поможет вам запустить приложение. Если у вас возникнут вопросы, пожалуйста, свяжитесь со мной по указанной ниже почте.

Почта для связи: [shkabaraw@gmail.com](mailto:shkabaraw@gmail.com)


# Instructions for Running the Console Application for Generating Store Receipts

1. **Download the Project**

   Use the command line (or terminal) and run the following command to download the project:

    ```sh
    git clone -b feature/entry-core https://github.com/ShkabaraVV/clevertec-check.git
    ```

2. **Navigate to the Project Directory**

   Use the terminal and the `cd` command to navigate to the project directory, depending on its location on your computer. It is highly recommended to create a folder beforehand for easier navigation:

    ```sh
    cd folder-name (without hyphens)
    ```

3. **Build the Application**

   Run the following command to build the application:

    ```sh
    .\gradlew build
    ```

4. **Run the Application**

   Use the following command to run the application:

    ```sh
    java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java id-quantity discountCard=xxxx balanceDebitCard=xxxx
    ```

   Where:
    - `id-quantity` - product ID and quantity
    - `discountCard=xxxx` - discount card with its number
    - `balanceDebitCard=xxxx` - balance on the debit card account

   #### Example

    ```sh
    java -cp build/classes/java/main ru.clevertec.check.CheckRunner 3-5 2-3 4-1 discountCard=1111 balanceDebitCard=100
    ```

I hope this guide helps you run the application. If you have any questions, please contact me at the email below.

Contact email: [shkabaraw@gmail.com](mailto:shkabaraw@gmail.com)
