package org.blank.projectmanagementsystem.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.formInput.ReviewDto;
import org.blank.projectmanagementsystem.service.ReviewCountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewAPI {
    private final ReviewCountService reviewCountService;

    @PostMapping("/add-review")
    public ResponseEntity<List<ReviewDto>> addReview(@RequestBody List<ReviewDto> reviewDtoList) {
        log.info("reviewDto: {}", reviewDtoList);
        List<ReviewDto> saveReview = reviewDtoList.stream()
                .map(reviewCountService::save)
                .collect(Collectors.toList());

        return ResponseEntity.ok(saveReview);
    }


//    @GetMapping("/check-existing-development-phase")
//    public ResponseEntity<?> checkExistingDevelopmentPhase(
//            @RequestParam Long projectId,
//            @RequestParam DevelopmentPhase developmentPhase,
//            @RequestParam ReviewerType reviewerType) {
//        boolean exists = reviewCountService.isDevelopmentPhaseExists(projectId, developmentPhase, reviewerType);
//        return ResponseEntity.ok(exists);
//    }

}
