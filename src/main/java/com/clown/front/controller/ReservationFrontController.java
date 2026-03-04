package com.clown.front.controller;

import com.clown.front.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReservationFrontController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservations")
    public String listReservations(@RequestParam(value = "date", required = false) String date, Model model) {
        model.addAttribute("reservations", reservationService.getReservations(date));
        model.addAttribute("currentDate", date);
        return "list";
    }
}
