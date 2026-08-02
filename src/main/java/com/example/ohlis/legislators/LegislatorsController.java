package com.example.ohlis.legislators;

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
@RequestMapping("/legislators")
@Slf4j
public class LegislatorsController {
  private static final String createFormTemplate = "legislators-create-form";
  private static final String listViewTemplate = "legislators";

  private final LegislatorRepository legislatorRepo;

  LegislatorsController(LegislatorRepository repo) {
    this.legislatorRepo = repo;
  }

  @GetMapping()
  public String getAll(Model model, @RequestParam(required = false) String newId) {
    // List all records using template.
    List<Legislator> list = legislatorRepo.findAll();
    model.addAttribute("legislators", list);

    // TODO: check newId is valid for a recently created record; if not, ignore it
    model.addAttribute("newId", newId);
    model.addAttribute("activePage", LegislatorsController.listViewTemplate);
    return LegislatorsController.listViewTemplate;
  }

  @GetMapping(path = "new")
  public String getCreateForm(LegislatorDto legislatorDto) {
    // Show the create form.
    return LegislatorsController.createFormTemplate;
  }

  @PostMapping()
  public ModelAndView createNewRecord(@Valid LegislatorDto legislatorDto, BindingResult bindingResult, ModelMap model) {
    // If validation fails, display form with data and feedback.
    if (bindingResult.hasErrors()) {
      model.addAttribute("bindingResult", bindingResult);
      return new ModelAndView(LegislatorsController.createFormTemplate, model, HttpStatus.BAD_REQUEST);
    }

    // Save new record, then redirect to list screen.
    Legislator newLegislator = legislatorRepo
        .save(new Legislator(legislatorDto.getFirstName(), legislatorDto.getLastName(), legislatorDto.getHometown()));
    log.info("Created new legislator with ID {}", newLegislator.getId());
    model.addAttribute("newId", newLegislator.getId());
    return new ModelAndView(String.format("redirect:/%s", LegislatorsController.listViewTemplate), model);
  }
}
