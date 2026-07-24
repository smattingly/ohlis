# OH LIS Screening Exercise

## URLs

This Java Spring Boot app serves the following paths on `http://localhost:8080`:
- `/legislators` lists all Legislator records,
- `/legislators/new` displays a form to create a new Legislator record,
- `/legislation` lists all Legislation records,
- `/legislation/new` displays a form to create a new Legislation record,

Because of the server-side rendering, I did not create a RESTful API. 

## Database

The app uses the H2 in-memory database. At launch, some data records are automatically populated (all with primary keys > 9000 to avoid collision with the sequence generated for app use). Records that you enter will be lost when the app is shut down.

Because there are no business logic requirements to speak of, I did not create a Service layer.

## Instructions

To run the application:
- using gradle: `./gradlew bootRun`
- using maven: `./mvnw spring-boot:run`

(You may need to install one of these tools if you haven't already.)

To run the Playwright tests: `npx playwright test`. (Node.js is required.)