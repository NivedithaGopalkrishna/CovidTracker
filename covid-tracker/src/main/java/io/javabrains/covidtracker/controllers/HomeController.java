package io.javabrains.covidtracker.controllers;

import io.javabrains.covidtracker.models.LocationStats;
import io.javabrains.covidtracker.services.CovidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CovidDataService covidDataService;

    @GetMapping("/")
    public String home(Model model){
      List<LocationStats> allStats = covidDataService.getAllStates();
      int totalCases = allStats.stream().mapToInt(stat->stat.getLatestCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat->stat.getDiffFromPrevDay()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalCases);
        model.addAttribute("totalNewCases", totalNewCases);

        return "home";
}

}
