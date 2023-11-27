package org.blank.projectmanagementsystem.domain.viewobject;

import lombok.Data;
import org.blank.projectmanagementsystem.domain.entity.ReviewCount;

@Data
public class ReviewViewObject {
    private String name;
    private String type;
    private int count;

    public ReviewViewObject(ReviewCount reviewCount){
        this.name = reviewCount.getDevelopmentPhase().name();
        this.type = reviewCount.getReviewerType().name();
        this.count = reviewCount.getCount();
    }
}
