package org.blank.projectmanagementsystem.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.Enum.ReviewerType;
import org.blank.projectmanagementsystem.domain.entity.Amount;
import org.blank.projectmanagementsystem.domain.entity.ReviewCount;
import org.blank.projectmanagementsystem.domain.viewobject.ProjectViewObject;
import org.blank.projectmanagementsystem.service.AmountService;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.blank.projectmanagementsystem.service.ReviewCountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class KpiAPI {
    private final ReviewCountService reviewCountService;
    private final AmountService amountService;

    @GetMapping("/calculateAllKPIs/{projectId}")
    public Map<String, Map<String, Double>> calculateAllKPIs(@PathVariable Long projectId) {
        Map<String, Map<String, Double>> results = new HashMap<>();

        List<Amount> amounts = amountService.findByProjectId(projectId);

        for (ReviewerType reviewerType : ReviewerType.values()) {
            Map<String, Double> kpiMap = new HashMap<>();

            for (Amount amount : amounts) {
                DevelopmentPhase developmentPhase = amount.getDevelopmentPhase();

                ReviewCount reviewCount = reviewCountService.findByProjectIdAndDevelopmentPhaseAndReviewerType(projectId, developmentPhase, reviewerType);

                if (reviewCount != null) {
                    double kpi = reviewCountService.calculateBasicDesignKpi(projectId, developmentPhase, reviewerType);
                    kpiMap.put(developmentPhase.name(), kpi);
                } else {
                    kpiMap.put(developmentPhase.name(), 0.0);
                }
            }

            results.put(reviewerType.name(), kpiMap);
        }

        return results;
    }


}
