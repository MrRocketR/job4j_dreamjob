package ru.job4j.dream.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.User;
import ru.job4j.dream.service.CandidateService;
import ru.job4j.dream.service.CityService;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@ThreadSafe
public class CandidateController {

    private final CandidateService candidateService;
    private final CityService cityService;

    public CandidateController(CandidateService service, CityService cityService) {
        this.candidateService = service;
        this.cityService = cityService;
    }

    @GetMapping("/candidates")
    public String candidates(Model model,  HttpSession session) {
        model.addAttribute("candidates", candidateService.findAll());
        SessionChecker sessionChecker = SessionChecker.getInstance();
        User user = sessionChecker.getUserToModel(session);
        model.addAttribute("user", user);
        return "candidates";
    }

    @GetMapping("/addCandidate")
    public String addCandidate(Model model,  HttpSession session) {
        model.addAttribute("cities", cityService.getAllCities());
        SessionChecker sessionChecker = SessionChecker.getInstance();
        User user = sessionChecker.getUserToModel(session);
        model.addAttribute("user", user);
        return "addCandidate";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate candidate, @RequestParam("city.id") int id,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        candidate.setCity(cityService.findById(id));
        candidate.setPhoto(file.getBytes());
        candidateService.add(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/formUpdateCandidate/{candidateId}")
    public String formUpdateCandidate(Model model, @PathVariable("candidateId") int id) {
        model.addAttribute("candidate", candidateService.findById(id));
        model.addAttribute("cities", cityService.getAllCities());
        return "updateCandidate";
    }
    @PostMapping("/updateCandidate")
    public String updateCandidate(@ModelAttribute Candidate candidate, @RequestParam("city.id") int id,
                                  @RequestParam("file") MultipartFile file,
                                  HttpSession session, Model model) throws IOException {
        candidate.setCity(cityService.findById(id));
        candidate.setPhoto(file.getBytes());
        candidateService.update(candidate);
        SessionChecker sessionChecker = SessionChecker.getInstance();
        User user = sessionChecker.getUserToModel(session);
        model.addAttribute("user", user);
        return "redirect:/candidates";
    }


    @GetMapping("/photoCandidate/{candidateId}")
    public ResponseEntity<Resource> download(@PathVariable("candidateId") Integer candidateId) {
        Candidate candidate = candidateService.findById(candidateId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(candidate.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(candidate.getPhoto()));
    }
}
