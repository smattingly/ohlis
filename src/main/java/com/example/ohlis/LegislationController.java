package com.example.ohlis;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
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
@RequestMapping("/legislation")
@Slf4j
public class LegislationController {

  private final LegislationRepository legislationRepo;
  private final LegislatorRepository legislatorRepo;

  LegislationController(LegislationRepository legislationRepo, LegislatorRepository legislatorRepo) {
    this.legislationRepo = legislationRepo;
    this.legislatorRepo = legislatorRepo;
  }

  @GetMapping()
  public String getAll(Model model, HttpServletRequest request) {
    // List all records using template.
    List<Legislation> list = legislationRepo.findAll();
    model.addAttribute("legislation", list);

    // Extract newly created ID, if any, from query string so that view can
    // highlight it.
    Long newId = Long.valueOf(-1); // Default: will not match any record.
    try {
      newId = Long.valueOf(request.getQueryString());
    } catch (Exception e) {
      // When query is non-existent or non-numeric, keep default value.
    }
    model.addAttribute("newId", newId);
    return "legislation";
  }

  @GetMapping(path = "new")
  public String getCreateForm(Model model) {
    // Show the create form.
    List<Legislator> sponsors = legislatorRepo.findAll();
    model.addAttribute("sponsors", sponsors);
    return "legislation-create-form";
  }

  @PostMapping(consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
  public ResponseEntity<String> createLegislation(@RequestParam MultiValueMap<String, String> formData,
      HttpServletRequest request) {
    HttpStatus result = HttpStatus.SEE_OTHER; // PRG pattern
    HttpHeaders headers = new HttpHeaders();
    try {
      // Validate the form data.
      String title = formData.getFirst("title");
      String text = formData.getFirst("text");
      validateRequiredString(title);
      validateRequiredString(text);

      // Get Legislator objects for submitted sponsor ID strings.
      List<Long> sponsorIds = new ArrayList<Long>();
      List<String> sponsorIdStrings = formData.get("sponsors");
      if (sponsorIdStrings != null) {
        for (String sponsorIdString : sponsorIdStrings) {
          sponsorIds.add(Long.valueOf(sponsorIdString));
        }
      }
      List<Legislator> sponsors = (sponsorIdStrings == null ? new ArrayList<Legislator>()
          : legislatorRepo.findByIdIn(sponsorIds));
      int submittedSponsorCount = (sponsorIdStrings == null ? 0 : sponsorIdStrings.size());
      if (sponsors.size() != submittedSponsorCount) {
        // Each submitted sponsor ID should be for a valid Legislator.
        throw new Exception();
      }

      // Create new Legislation record, and any associated sponsorship records.
      Legislation newLegislation = legislationRepo.save(new Legislation(title, text, sponsors));
      log.info("Created new legislation with ID {}", newLegislation.getId());

      // Build Location URI to Legislation page, with new Legislaion ID as
      // 1) anchor for browser use and
      // 2) query string for server use.
      String location = UriComponentsBuilder.fromUriString(request.getRequestURI())
          .query(newLegislation.getId().toString())
          .fragment(String.format("%d", newLegislation.getId())).toUriString();
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
