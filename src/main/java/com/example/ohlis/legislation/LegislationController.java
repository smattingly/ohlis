package com.example.ohlis.legislation;

import com.example.ohlis.legislators.LegislatorRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/legislation")
@Slf4j
public class LegislationController {
  private static final String createFormTemplate = "legislation-create-form";
  private static final String listViewTemplate = "legislation";

  private final LegislationRepository legislationRepo;
  private final LegislatorRepository legislatorRepo;

  LegislationController(LegislationRepository legislationRepo, LegislatorRepository legislatorRepo) {
    this.legislationRepo = legislationRepo;
    this.legislatorRepo = legislatorRepo;
  }

  @GetMapping()
  public String getAll(Model model, @RequestParam(required = false) String newId) {
    // List all records using template.
    List<Legislation> list = legislationRepo.findAll();
    model.addAttribute("legislation", list);

    // TODO: check newId is valid for a recently created record; if not, ignore it
    model.addAttribute("newId", newId);
    model.addAttribute("activePage", LegislationController.listViewTemplate);
    return LegislationController.listViewTemplate;
  }

  @GetMapping(path = "new")
  public String getCreateForm(LegislationDto legislationDto, Model model) {
    // Lookup Legislator data for the form's selection list.
    model.addAttribute("sponsors", legislatorRepo.findAll());

    // Show the create form.
    return LegislationController.createFormTemplate;
  }

  @PostMapping()
  public ModelAndView createNewRecord(@Valid LegislationDto legislationDto, BindingResult bindingResult,
      ModelMap model) {
    // If validation fails, display form with data and feedback.
    if (bindingResult.hasErrors()) {
      model.addAttribute("bindingResult", bindingResult);
      // Lookup Legislator data for the form's selection list.
      model.addAttribute("sponsors", legislatorRepo.findAll());
      return new ModelAndView(LegislationController.createFormTemplate, model, HttpStatus.BAD_REQUEST);
    }

    Legislation newLegislation = legislationRepo
        .save(new Legislation(legislationDto.getTitle(), legislationDto.getText(), legislationDto.getSponsors()));
    log.info("Created new legislator with ID {}", newLegislation.getId());
    model.addAttribute("newId", newLegislation.getId());
    return new ModelAndView(String.format("redirect:/%s", LegislationController.listViewTemplate), model);
  }
}
