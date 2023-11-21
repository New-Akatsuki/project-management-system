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

    @PostMapping("/add-or-update-amount")
    public ResponseEntity<List<AmountDto>> addOrUpdateAmounts(@RequestBody List<AmountDto> amountList) {
        List<AmountDto> savedAmounts = amountList.stream()
                .map(amountService::saveOrUpdate)
                .collect(Collectors.toList());

        return ResponseEntity.ok(savedAmounts);
    }



    @GetMapping("/get-amount/{projectId}")
    public ResponseEntity<List<AmountDto>> getAmounts(@PathVariable Long projectId) {
        List<AmountDto> amounts = amountService.findByProjectId(projectId).stream()
                .map(amount -> AmountDto.builder()
                        .id(amount.getId())
                        .projectId(amount.getProject().getId())
                        .developmentPhase(amount.getDevelopmentPhase())
                        .amount(amount.getAmount())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(amounts);
    }


}
