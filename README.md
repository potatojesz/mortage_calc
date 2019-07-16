## Mortage calculator

Backend REST Mortage and mortage overpay calculator

## Prerequisites

**JDK8**

**Maven** (not needed, u can use wrapper ```./mvnw``` instead of ```mvn```)

## Installation


---

1. clone this source

2. build
```shell
mvn clean package
```

3. run
```shell
mvn spring-boot:run
```

---
## Informations

Application by default is working at port ```8080```, u can change it by adding ```server.port``` value in ```application.properties```

Working Server example is available at [https://mortage-calc.herokuapp.com/](https://mortage-calc.herokuapp.com/)

API is documented by SWAGGER2 Library available at [https://mortage-calc.herokuapp.com/swagger-ui.html](https://mortage-calc.herokuapp.com/swagger-ui.html)

## REST Methods

### 1. Mortage calculation

#### POST /mortage/calc

##### _Example_
```POST method``` on [https://mortage-calc.herokuapp.com/mortage/calc](https://mortage-calc.herokuapp.com/mortage/calc)

**body**
```json
{
    "cardNumber": "0000000000000000",
    "pin": "0000"
}
```

**response**

```json
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMDAwMDAwMDAwMDAwMDAwIiwiaWF0IjoxN...",
    "tokenType": "Bearer",
    "number": "0000000000000000"
}
```
---

### 2. Overpay calculation

#### POST /mortage/overpay

##### _Example_
```POST method``` on [https://mortage-calc.herokuapp.com/mortage/overpay](https://mortage-calc.herokuapp.com/mortage/overpay)

**body**
```json
{
    "cardNumber": "0000000000000000",
    "pin": "0000"
}
```

**response**

```json
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMDAwMDAwMDAwMDAwMDAwIiwiaWF0IjoxN...",
    "tokenType": "Bearer",
    "number": "0000000000000000"
}
```
---
