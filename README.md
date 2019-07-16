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
  "amount": 250000,
  "interest": 3.79,
  "months": 360,
  "type": "CONSTANT_INSTALLMENT"
}
```

**response**

```json
{
  "installments": [
    {
      "month": 1,
      "amountLeft": 250000,
      "capitalPart": 373.89,
      "interestPart": 789.58,
      "overpayPart": 0,
      "whole": 1163.47
    },
    {
      "month": 2,
      "amountLeft": 249626.11,
      "capitalPart": 375.07,
      "interestPart": 788.4,
      "overpayPart": 0,
      "whole": 1163.47
    },
	{
		.....
	}
  ],
  "interestsSum": 168849.37,
  "mortage": {
    "type": "CONSTANT_INSTALLMENT",
    "interest": 3.79,
    "amount": 250000,
    "months": 360
  }
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
  "mortage": {
    "amount": 250000,
    "interest": 3.79,
    "months": 360,
    "type": "CONSTANT_INSTALLMENT"
  },
  "overpayment": {
    "overpayAmount": 1000,
    "period": "MONTHLY",
    "type": "SHORTER_PERIOD"
  }
}
```

**response**

```json
{
  "mortage": {
    "type": "CONSTANT_INSTALLMENT",
    "interest": 3.79,
    "amount": 250000,
    "months": 360
  },
  "overpayment": {
    "type": "SHORTER_PERIOD",
    "period": "MONTHLY",
    "overpayAmount": 1000
  },
  "installments": [
    {
      "month": 1,
      "amountLeft": 250000,
      "capitalPart": 373.89,
      "interestPart": 789.58,
      "overpayPart": 1000,
      "whole": 2163.47
    },
    {
      "month": 2,
      "amountLeft": 248626.11,
      "capitalPart": 373.57,
      "interestPart": 785.24,
      "overpayPart": 1000,
      "whole": 2158.81
    },
	{
		......
	}
  ],
  "interest": 75308.74,
  "newMonths": 199
}
```
---
