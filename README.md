# Mini-ERP

A small backend system that models the core of a business: sales, inventory, finance, and accounts, tied together in one application.

## Why this exists

I started this as the backend for an app idea I was working on. I ended up pausing that app, but kept building this piece on its own as a way to learn backend development properly — real authentication, a proper database layer, and a domain that's actually complex enough to require some thought about how the pieces connect.

It's a personal/learning project, not a production system. I'm sharing it as-is, including its rough edges.

## What it covers

- **Sales** — orders, order items, order steps
- **Inventory** — stock levels, inventory transactions, inventory requests
- **Finance** — accounts, account balances, financial transactions and receipts, tax, item pricing
- **Business** — businesses, business settings, item categories, step/step-set workflows
- **Supply** — supply requests and their items
- **Users & auth** — user accounts, login, JWT-based authentication


## Tech stack

- Java 21
- Spring Boot 3.3
- Spring Data JPA / Hibernate
- Spring Security with JWT (jjwt)
- H2 (in-memory database)
- Maven

## Running it locally

You'll need Java 21 and a JWT secret set as an environment variable (there's no default , the app won't start without one, which is intentional).

```bash
# generate a secret (or use any long random string)
python -c "import secrets, base64; print(base64.b64encode(secrets.token_bytes(64)).decode())"

# set it for your session
export JWT_SECRET="paste-the-generated-value-here"      # macOS/Linux
$env:JWT_SECRET="paste-the-generated-value-here"         # Windows PowerShell

# run
./mvnw spring-boot:run
```

The app starts on `http://localhost:8080`. It uses an in-memory H2 database, so all data resets every time you restart it. A small set of sample data (a couple of businesses, accounts, items, and orders) loads automatically on startup.

The H2 console is available at `http://localhost:8080/h2-console` for browsing the database directly.

## Example requests

Log in (using the sample user seeded on startup: `sina` / `123`):

```
POST /auth/login
Content-Type: application/json

{
  "username": "sina",
  "password": "123"
}
```

This returns a JWT. Every other endpoint requires it, passed as a header:

```
Authorization: Bearer <token>
```

Create an order:

```
POST /orders
Authorization: Bearer <token>
Content-Type: application/json

{
  "businessId": 1,
  "customerId": 1,
  "totalLogisticPrice": 10.0,
  "discountInPercent": 0,
  "discountInCurrency": 0,
  "currency": "USD",
  "paymentType": "CASH"
}
```

List orders:

```
GET /orders
Authorization: Bearer <token>
```

Other resources (items, accounts, inventory, etc.) follow the same REST pattern under their own paths (`/items`, `/accounts`, `/inventory`, and so on).

## What's not finished

Being upfront about this, since it matters more than a polished feature list:

- **Money fields use `double`, not `BigDecimal`.** This is a known issue with floating-point precision for financial values. I left it as-is because this project never handles real money, but I wouldn't do this in anything that did.
- **No frontend.** This is backend-only; there's nothing to look at in a browser beyond the H2 console.
- **Test coverage is limited to the core flows** — authentication, validation, and order creation. Most of the individual modules don't have their own tests yet.
- **Only one role (`ADMIN`) currently exists.** Role-based access control is wired up and working, but there's no second role to test the "logged in but not authorized" case against.
- **No pagination.** List endpoints return everything, which would be a problem at any real scale.

## Status

Actively used as a learning project, not actively developed as a product. I revisit it when I want to practice something specific.