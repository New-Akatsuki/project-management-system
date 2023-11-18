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
    private final ProjectService projectService;
    private final ReviewCountService reviewCountService;
    private final AmountService amountService;

//    @GetMapping("/get-project-data")
//    public ResponseEntity<List<ProjectViewObject>> getProject() {
//        List<ProjectViewObject> projects = projectService.getAllProjectViewObjects();
//        return ResponseEntity.ok(projects);
//    }
//
//    @GetMapping("/get-review-count")
//    public ResponseEntity<List<ReviewCount>> getReviewCount() {
//        List<ReviewCount> reviewCounts = reviewCountService.getAllReviewCount();
//        return ResponseEntity.ok(reviewCounts);
//    }
//
//    @GetMapping("/get-amount")
//    public ResponseEntity<List<Amount>> getAmountCount(){
//        List<Amount> amounts = amountService.getAllAmount();
//        return ResponseEntity.ok(amounts);
//    }

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

//    @GetMapping("/calculateAllKPIs/{projectId}/{reviewerType}")
//    public Map<String, Double> calculateAllKPIs(@PathVariable Long projectId, @PathVariable ReviewerType reviewerType) {
//        Map<String, Double> results = new HashMap<>();
//
//        List<Amount> amounts = amountService.findByProjectId(projectId);
//
//        for (Amount amount : amounts) {
//            DevelopmentPhase developmentPhase = amount.getDevelopmentPhase();
//
//            ReviewCount reviewCount = reviewCountService.findByProjectIdAndDevelopmentPhaseAndReviewerType(projectId, developmentPhase, reviewerType);
//
//            if (reviewCount != null) {
//                reviewerType = reviewCount.getReviewerType();
//                double kpi = reviewCountService.calculateBasicDesignKpi(projectId, developmentPhase, reviewerType);
//
//                results.put(developmentPhase.name(), kpi);
//            } else {
//                results.put(developmentPhase.name(), 0.0);
//            }
//        }
//
//        return results;
//    }



}
