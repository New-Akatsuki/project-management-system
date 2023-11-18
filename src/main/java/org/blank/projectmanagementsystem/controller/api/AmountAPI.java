package org.blank.projectmanagementsystem.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.entity.Amount;
import org.blank.projectmanagementsystem.domain.formInput.AmountDto;
import org.blank.projectmanagementsystem.service.AmountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/amount")
public class AmountAPI {
    private final AmountService amountService;


    @PostMapping("/add-amount")
    public ResponseEntity<List<AmountDto>> addAmounts(@RequestBody List<AmountDto> amountList) {
        List<AmountDto> savedAmounts = amountList.stream()
                .map(amountService::save)
                .collect(Collectors.toList());

        return ResponseEntity.ok(savedAmounts);
    }


//    @GetMapping("/check-existing-development-phase")
//    public ResponseEntity<?> checkExistingDevelopmentPhase(
//            @RequestParam Long projectId,
//            @RequestParam DevelopmentPhase developmentPhase) {
//        boolean exists = amountService.isDevelopmentPhaseExists(projectId, developmentPhase);
//        return ResponseEntity.ok(exists);
//    }



}
