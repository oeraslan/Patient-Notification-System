# Patient-Notification-System

Bu proje, iki mikroservisten oluşan bir hasta kayıt ve bildirim sistemidir. Bu mikroservisler ve ön yüz uygulaması, Java 17, Spring Boot 3.3 ve React kullanılarak geliştirilmiştir. Ön yüz henüz tam olarak tamamlanmamıştır.
## Mikroservisler

### Patient Service

- Patient Service, hasta kayıt ve güncelleme operasyonlarından sorumludur. Yeni bir hasta kaydedildiğinde veya mevcut bir hasta güncellendiğinde, bu bilgileri Kafka üzerinden Notification System'e gönderir.

### Notification System

- Notification System, kriter ekleme ve güncelleme işlemlerinden sorumludur. Ayrıca, Patient Service'ten gelen her yeni hasta kaydını Kafka üzerinden alır ve gelen hasta bilgileri ile eşleşen bir kriter olup olmadığını kontrol eder. Eğer kritere uygun bir hasta varsa, bu hastayı veritabanına kaydeder.

## Frontend

Ön yüz, React ile geliştirilmiştirme aşamasındadır.

## Teknolojiler

- Java
- Spring Boot
- Maven
- Kafka
- JavaScript
- NPM
- React
- MySQL
- PostgreSQL
- Swagger

## Swagger
  - Patient Service : http://localhost:8080/swagger-ui.html
  - Notification Service : http://localhost:7070/swagger-ui.html
## Kurulum

- ### Kafka : 
    - 9092 Portunda bir Kafka broker oluşturun.

- ### Notification System : 
    -  3306 Portunda bir MySQL veritabanı oluşturun ve application.properties dosyasında veritabanı bilgilerinizi güncelleyin.
    - `cd notification system`
    - `mvn clean install`
    - `mvn spring-boot:run`
- ### Patient Service : 
    -  5432 Portunda bir PostgreSQL veritabanı oluşturun ve application.properties dosyasında veritabanı bilgilerinizi güncelleyin.
    - `cd patient service`
    - `mvn clean install`
    - `mvn spring-boot:run`

- ### Frontend :
    - `cd patient-notification-ui`
    - `npm install`
    - `npm start`
