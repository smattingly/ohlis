package com.example.ohlis.legislators;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/legislators")
@Slf4j
public class LegislatorsController {
  private static final String createFormTemplate = "legislator-form";
  private static final String listViewTemplate = "legislators";

  private final LegislatorRepository legislatorRepo;

  LegislatorsController(LegislatorRepository repo) {
    this.legislatorRepo = repo;
  }

  @GetMapping()
  public String getAll(Model model, @RequestParam(required = false) String op,
      @RequestParam(required = false) String record) {
    // List all records using template.
    List<Legislator> list = legislatorRepo.findAll();
    model.addAttribute("legislators", list);

    // TODO: check newId is valid for a recently created record; if not, ignore it
    model.addAttribute("op", op);
    model.addAttribute("record", record);
    model.addAttribute("activePage", LegislatorsController.listViewTemplate);
    return LegislatorsController.listViewTemplate;
  }

  @GetMapping(path = "{id}")
  public String getEditForm(Model model, @PathVariable String id) {
    Optional<Legislator> opt = legislatorRepo.findById(Long.parseLong(id));
    if (opt.isEmpty()) {
      return "error/404";
    }

    Legislator legislator = opt.get();
    model.addAttribute("legislator", legislator);

    // Show the create form.
    return LegislatorsController.createFormTemplate;
  }

  @PutMapping(path = "{id}")
  public ModelAndView updateRecord(Legislator legislator, BindingResult bindingResult, ModelMap model,
      @PathVariable String id) {
    // If validation fails, display form with data and feedback.
    if (bindingResult.hasErrors()) {
      model.addAttribute("bindingResult", bindingResult);
      return new ModelAndView(LegislatorsController.createFormTemplate, model, HttpStatus.BAD_REQUEST);
    }

    legislatorRepo.save(legislator);
    log.info("Updated legislator with ID {}", id);
    model.addAttribute("op", "update");
    model.addAttribute("record", id);
    return new ModelAndView(String.format("redirect:/%s", LegislatorsController.listViewTemplate), model);
  }

  @GetMapping(path = "new")
  public String getCreateForm(Legislator legislator) {
    // Show the create form.
    return LegislatorsController.createFormTemplate;
  }

  @PostMapping()
  public ModelAndView createNewRecord(@Valid Legislator legislator, BindingResult bindingResult, ModelMap model) {
    // If validation fails, display form with data and feedback.
    if (bindingResult.hasErrors()) {
      model.addAttribute("bindingResult", bindingResult);
      return new ModelAndView(LegislatorsController.createFormTemplate, model, HttpStatus.BAD_REQUEST);
    }

    // Save new record, then redirect to list screen.
    Legislator newLegislator = legislatorRepo
        .save(new Legislator(legislator.getFirstName(), legislator.getLastName(), legislator.getHometown()));
    log.info("Created new legislator with ID {}", newLegislator.getId());
    model.addAttribute("op", "create");
    model.addAttribute("record", newLegislator.getId());
    return new ModelAndView(String.format("redirect:/%s", LegislatorsController.listViewTemplate), model);
  }

  @DeleteMapping(path = "{id}")
  public ModelAndView deleteRecord(ModelMap model, @PathVariable String id) {
    Optional<Legislator> opt = legislatorRepo.findById(Long.parseLong(id));
    if (opt.isEmpty()) {
      return new ModelAndView("error/404");
    }

    Legislator legislator = opt.get();
    try {
      legislatorRepo.delete(legislator);
      model.addAttribute("op", "delete");
      log.info("Deleted legislator with ID {}", id);
    } catch (Exception e) {
      model.addAttribute("op", "deleteFail");
      log.error("Failed to delete legislator with ID {}", id);
    }
    model.addAttribute("record", id);
    return new ModelAndView(String.format("redirect:/%s", LegislatorsController.listViewTemplate), model);
  }
}
