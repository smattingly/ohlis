# OH LIS Screening Exercise

## URLs

This Java Spring Boot app serves the following paths on `http://localhost:8080`:
- `/legislators` lists all Legislator records,
- `/legislators/new` displays a form to create a new Legislator record,
- `/legislation` lists all Legislation records,
- `/legislation/new` displays a form to create a new Legislation record.

Because of the server-side rendering, I did not create a RESTful API. 

## Database

The app uses a containerized PostgreSQL database. At launch, some data records are automatically populated (all with primary keys > 9000 to avoid collision with the sequence generated for app use). Records that you enter will be lost when the db container is shut down.

Because there are no business logic requirements to speak of, I did not create a Service layer.

## Instructions

To build and run the application: `docker compose up --build -d`

To shutdown the application: `docker compose down`

To run the Playwright tests:
1. One time setup: `npm install` (Node.js 18+ must be installed.)
2. Run `npm test`. This launches the app with authentication disabled and executes the playwright test cases twice:
    - first, with a "normal" configuration where the browser does client-side validation of form submissions;
    - second, with a feature flag that disables browser validation in order to test server side validation.

To run Gatling load tests:
1. Lauch the app with authentication disabled: `SPRING_PROFILES_ACTIVE=disable-authentication docker compose up --build -d` 
2. Run `./mvnw gatling:test`