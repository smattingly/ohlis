package com.example.ohlis;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/legislators")
@Slf4j
public class LegislatorsController {

  private final LegislatorRepository legislatorRepo;

  LegislatorsController(LegislatorRepository repo) {
    this.legislatorRepo = repo;
  }

  @GetMapping()
  public String getAll(Model model, HttpServletRequest request) {
    // List all records using template.
    List<Legislator> list = legislatorRepo.findAll();
    model.addAttribute("legislators", list);

    // Extract newly created ID, if any, from query string so that view can use it.
    Long newId = null;
    try {
      newId = Long.valueOf(request.getQueryString());
    } catch (Exception e) {
      // When query is non-existent or non-numeric, null is ok.
    }
    model.addAttribute("newId", newId);
    return "legislators";
  }

  @GetMapping(path = "new")
  public String getCreateForm() {
    // Show the create form.
    return "legislators-create-form";
  }

  @PostMapping(consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
  public ResponseEntity<String> createLegislator(@RequestParam MultiValueMap<String, String> formData,
      HttpServletRequest request) {
    HttpStatus result = HttpStatus.SEE_OTHER; // PRG pattern
    HttpHeaders headers = new HttpHeaders();
    try {
      // Validate the form data.
      String firstName = formData.getFirst("firstName");
      String lastName = formData.getFirst("lastName");
      String hometown = formData.getFirst("hometown");
      validateRequiredString(firstName);
      validateRequiredString(lastName);
      validateRequiredString(hometown);

      // Create new Legislator record.
      Legislator newLegislator = legislatorRepo.save(new Legislator(firstName, lastName, hometown));
      log.info("Created new legislator with ID {}", newLegislator.getId());

      // Build Location URI to Legislation page, with new ID as query string.
      String location = UriComponentsBuilder.fromUriString(request.getRequestURI())
          .query(newLegislator.getId().toString()).toUriString();
      headers.add("Location", location);
    } catch (Exception e) {
      result = HttpStatus.BAD_REQUEST;
    }

    return new ResponseEntity<String>(result.getReasonPhrase(), headers, result);
  }

  private void validateRequiredString(String value) throws Exception {
    if (value == null || value.trim().length() == 0) {
      throw new Exception("A required value was missing.");
    }
  }
}
