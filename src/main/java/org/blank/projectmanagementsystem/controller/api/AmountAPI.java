package org.blank.projectmanagementsystem.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Amount;
import org.blank.projectmanagementsystem.service.AmountService;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/amount")
public class AmountAPI {
    private final AmountService amountService;
    private final ProjectService projectService;


    @PostMapping("/add-amount")
    public ResponseEntity<Amount> addAmount(@RequestBody Amount amount) {
        return ResponseEntity.ok(amountService.save(amount));
    }



}
