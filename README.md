# Новостной портал

## Стек технологий
- **Java 17**
- **Spring Boot**
- **Spring Security**
- **Spring Data**
- **Spring Web**
- **Thymeleaf**
- **Liquibase**
- **PostgreSQL**

## Установка и запуск

### Требования к базе данных
Перед запуском приложения необходимо создать пустую базу данных PostgreSQL на порту 5432.

### Запуск приложения
1. Склонируйте репозиторий на свой локальный компьютер.
2. Убедитесь, что у вас установлены все необходимые зависимости (например, Maven или Gradle).
3. Настройте параметры подключения к базе данных в конфигурационных файлах.
4. Запустите приложение, используя команду:
mvn spring-boot:run
5. Перейдите по URL `http://localhost:8080/news/login`, чтобы попасть на форму регистрации.

### Регистрация нового пользователя
- Нажмите кнопку **"Регистрация"** для создания нового пользователя.
- При регистрации пользователя автоматически назначаются права по умолчанию **"USER"**. У такого пользователя есть права на:
  - Просмотр статей
  - Оценивание статей
  - Написание комментариев под статьями
  - Редактирование и оценивание комментариев других пользователей

![Форма Регистрации](https://private-user-images.githubusercontent.com/121201082/443393818-3663210e-1746-4b7e-bd15-e515910d8051.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NDcxNzAyNjcsIm5iZiI6MTc0NzE2OTk2NywicGF0aCI6Ii8xMjEyMDEwODIvNDQzMzkzODE4LTM2NjMyMTBlLTE3NDYtNGI3ZS1iZDE1LWU1MTU5MTBkODA1MS5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUwNTEzJTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MDUxM1QyMDU5MjdaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT0wZGU1YzdmZjQwZTgxY2UyNDg0ZTE5MzVmYWE2N2E5MWJhOWJhMjU0MjJjYzM5ODUxZmVjMGE4NTI2OWIxNTFkJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.5AnpRBnV5s5f9yaMR-0FlScKfBIlhDGnMl8Y9FiHJR8)

## Данные для входа администратора
- **Логин:** vesel@gmail.com
- **Пароль:** 123

При входе с вышеуказанными данными администратор имеет возможность:
- Создавать и удалять статьи
- Удалять комментарии
- Назначать права другим пользователям

![Администраторский интерфейс](https://private-user-images.githubusercontent.com/121201082/443394081-0c9ad74c-9670-4e1c-857d-b81eba26817e.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NDcxNzAyMjcsIm5iZiI6MTc0NzE2OTkyNywicGF0aCI6Ii8xMjEyMDEwODIvNDQzMzk0MDgxLTBjOWFkNzRjLTk2NzAtNGUxYy04NTdkLWI4MWViYTI2ODE3ZS5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUwNTEzJTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MDUxM1QyMDU4NDdaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT0wMzNkN2U5YjAyYTc0NjllMDJhYTdiMDdlZTRkMGZhNjNiMjYxMmFjMWM1MmNiMGFjMThlYTg0NDdlMDA2ODRmJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.feXpCgP6pQ69CIHS4_fAqgnTejiiXwbfaDZayu8zB78)

## Права пользователя "EDITOR"
Пользователь с правами **"EDITOR"** может писать статьи, но для их публикации необходимо одобрение администратора. Администратор должен перейти во вкладку **"Управление статьями"** для утверждения статей.Также пользователь с данными правами обладает всем функционалам пользователя с правами **"USER"**

![Управление статьями](https://private-user-images.githubusercontent.com/121201082/443394517-68c73215-1412-4233-a03c-574df67f9db4.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NDcxNzAyOTQsIm5iZiI6MTc0NzE2OTk5NCwicGF0aCI6Ii8xMjEyMDEwODIvNDQzMzk0NTE3LTY4YzczMjE1LTE0MTItNDIzMy1hMDNjLTU3NGRmNjdmOWRiNC5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUwNTEzJTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MDUxM1QyMDU5NTRaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT1hY2NjZGU5ZGQwNTRlNmQ1ZTgwYmFjOTdkMzk5YmI4ZTkwN2JiMWFkNmJiMTQyZDhkOWNjZjhjNDg0MzY5OWIyJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.RLoK_D6nJWrrFJxlYzUxQdrK2b29eb6VPTfeIpvC1Ic)

## Главная страница
На главной странице доступны несколько вкладок для сортировки новостей по категориям. Во вкладке **"Популярные"** статьи сортируются по количеству просмотров. Чем больше пользователей просматривали статью, тем выше она будет в списке.
