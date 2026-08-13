package com.example.ohlis;

import io.gatling.javaapi.core.OpenInjectionStep;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import com.github.javafaker.Faker;

public class CreateLegislatorSimulation extends Simulation {

  @SuppressWarnings("null")
  public CreateLegislatorSimulation() {
    setUp(scenarioBuilder()
        .injectOpen(injection())
        .protocols(protocolBuilder()))
        .assertions(
            global().responseTime().max().lte(1000),
            global().successfulRequests().percent().gte(100d));
  }

  @SuppressWarnings("null")
  private static ScenarioBuilder scenarioBuilder() {
    return scenario("Load test: POST /legislators")
        .feed(generateData())
        .exec(
            http("Create Legislator")
                .post("/legislators")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(StringBody("firstName=#{firstName}&lastName=#{lastName}&hometown=#{hometown}"))
                // Verify that we end up on the expected page (after redirect).
                .check(substring("body id=\"legislators-list\"")));
  }

  private static Iterator<Map<String, Object>> generateData() {
    Faker faker = new Faker();
    return Stream.generate(() -> {
      Map<String, Object> stringObjectMap = new HashMap<>();
      stringObjectMap.put("firstName", faker.name().firstName());
      stringObjectMap.put("lastName", faker.name().lastName());
      stringObjectMap.put("hometown", faker.address().cityName());
      return stringObjectMap;
    }).iterator();
  }

  private static HttpProtocolBuilder protocolBuilder() {
    return http.baseUrl("http://localhost:8080")
        .maxConnectionsPerHost(10)
        .userAgentHeader("Load Test");
  }

  private OpenInjectionStep[] injection() {
    OpenInjectionStep[] steps = { rampUsers(10).during(5), constantUsersPerSec(10).during(10) };
    return steps;
  }
}