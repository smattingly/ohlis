package com.example.ohlis.legislation;

import com.example.ohlis.legislators.LegislatorRepository;

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
@RequestMapping("/legislation")
@Slf4j
public class LegislationController {
  private static final String createFormTemplate = "legislation-form";
  private static final String listViewTemplate = "legislation";

  private final LegislationRepository legislationRepo;
  private final LegislatorRepository legislatorRepo;

  LegislationController(LegislationRepository legislationRepo, LegislatorRepository legislatorRepo) {
    this.legislationRepo = legislationRepo;
    this.legislatorRepo = legislatorRepo;
  }

  @GetMapping()
  public String getAll(Model model, @RequestParam(required = false) String op,
      @RequestParam(required = false) String record) {
    // List all records using template.
    List<Legislation> list = legislationRepo.findAll();
    model.addAttribute("legislation", list);

    // TODO: check newId is valid for a recently created record; if not, ignore it
    model.addAttribute("op", op);
    model.addAttribute("record", record);
    model.addAttribute("activePage", LegislationController.listViewTemplate);
    return LegislationController.listViewTemplate;
  }

  @GetMapping(path = "new")
  public String getCreateForm(Legislation legislation, Model model) {
    // Lookup Legislator data for the form's selection list.
    model.addAttribute("sponsors", legislatorRepo.findAll());

    // Show the create form.
    return LegislationController.createFormTemplate;
  }

  @GetMapping(path = "{id}")
  public String getEditForm(Model model, @PathVariable String id) {
    // Lookup Legislator data for the form's selection list.
    model.addAttribute("sponsors", legislatorRepo.findAll());
    model.addAttribute("id", id);

    Optional<Legislation> opt = legislationRepo.findById(Long.parseLong(id));
    if (opt.isEmpty()) {
      return "error/404";
    }

    Legislation legislation = opt.get();
    model.addAttribute("legislation", legislation);

    // Show the create form.
    return LegislationController.createFormTemplate;
  }

  @PutMapping(path = "{id}")
  public ModelAndView updateRecord(Legislation legislation, BindingResult bindingResult, ModelMap model,
      @PathVariable String id) {
    // If validation fails, display form with data and feedback.
    if (bindingResult.hasErrors()) {
      model.addAttribute("bindingResult", bindingResult);
      // Lookup Legislator data for the form's selection list.
      model.addAttribute("sponsors", legislatorRepo.findAll());
      return new ModelAndView(LegislationController.createFormTemplate, model, HttpStatus.BAD_REQUEST);
    }

    legislationRepo.save(legislation);
    log.info("Updated legislation with ID {}", id);
    model.addAttribute("op", "update");
    model.addAttribute("record", id);
    return new ModelAndView(String.format("redirect:/%s", LegislationController.listViewTemplate), model);
  }

  @PostMapping()
  public ModelAndView createNewRecord(@Valid Legislation legislation, BindingResult bindingResult,
      ModelMap model) {
    // If validation fails, display form with data and feedback.
    if (bindingResult.hasErrors()) {
      model.addAttribute("bindingResult", bindingResult);
      // Lookup Legislator data for the form's selection list.
      model.addAttribute("sponsors", legislatorRepo.findAll());
      return new ModelAndView(LegislationController.createFormTemplate, model, HttpStatus.BAD_REQUEST);
    }

    Legislation newLegislation = legislationRepo
        .save(new Legislation(legislation.getTitle(), legislation.getText(), legislation.getSponsors()));
    log.info("Created new legislator with ID {}", newLegislation.getId());
    model.addAttribute("op", "create");
    model.addAttribute("record", newLegislation.getId());
    return new ModelAndView(String.format("redirect:/%s", LegislationController.listViewTemplate), model);
  }

  @DeleteMapping(path = "{id}")
  public ModelAndView deleteRecord(ModelMap model, @PathVariable String id) {
    Optional<Legislation> opt = legislationRepo.findById(Long.parseLong(id));
    if (opt.isEmpty()) {
      return new ModelAndView("error/404");
    }

    Legislation legislation = opt.get();
    try {
      legislationRepo.delete(legislation);
      model.addAttribute("op", "delete");
      log.info("Deleted legislation with ID {}", id);
    } catch (Exception e) {
      model.addAttribute("op", "deleteFail");
      log.error("Failed to delete legislation with ID {}", id);
    }
    model.addAttribute("record", id);
    return new ModelAndView(String.format("redirect:/%s", LegislationController.listViewTemplate), model);
  }
}
