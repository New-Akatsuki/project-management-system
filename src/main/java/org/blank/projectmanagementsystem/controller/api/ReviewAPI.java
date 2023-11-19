package org.blank.projectmanagementsystem.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.Enum.ReviewerType;
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
                .map(reviewCountService::saveOrUpdate)
                .collect(Collectors.toList());

        return ResponseEntity.ok(saveReview);
    }


    @GetMapping("/get-review/{projectId}/{reviewerType}")
    public ResponseEntity<List<ReviewDto>> getReview(@PathVariable Long projectId, @PathVariable ReviewerType reviewerType) {
        List<ReviewDto> reviewDtoList = reviewCountService.findByProjectId(projectId).stream()
                .filter(reviewCount -> reviewCount.getReviewerType().equals(reviewerType))
                .map(reviewCount -> ReviewDto.builder()
                        .id(reviewCount.getId())
                        .projectId(reviewCount.getProject().getId())
                        .developmentPhase(reviewCount.getDevelopmentPhase())
                        .reviewerType(reviewCount.getReviewerType())
                        .count(reviewCount.getCount())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(reviewDtoList);
    }
}
