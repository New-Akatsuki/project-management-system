
function prepareModal2(action){
    $('#basicDesignReviewCount').val('');
    $('#detailedDesignReviewCount').val('');
    $('#codeLineReviewCount').val('');
    $('#unitTestReviewCount').val('');
    $('#integrationTestReviewCount').val('');

    if(action === 'add'){
        $('#exampleModal').modal('show');
    }else if(action === 'update'){
        fetchExistingReview();
        $('#exampleModal').modal('show');
    }
}


function fetchExistingReview(){
    let projectId = $('#projectId').val();
    let reviewerType = $('#reviewerType').val();

    // Check if a project is selected
    if (!projectId || projectId === '#') {
        alert('Please select a project.');
        return;
    }

    $.ajax({
        url: '/api/review/get-review/' + projectId + '/' + reviewerType,
        type: 'GET',
        success: function (data) {
            // Clear existing values
            $('#basicDesignReviewCount').val('');
            $('#detailedDesignReviewCount').val('');
            $('#codeLineReviewCount').val('');
            $('#unitTestReviewCount').val('');
            $('#integrationTestReviewCount').val('');

            // Iterate through the data and update the modal inputs
            data.forEach(function (review) {
                let reviewCountField;
                switch (review.developmentPhase) {
                    case 'BASIC_DESIGN':
                        reviewCountField = $('#basicDesignReviewCount');
                        break;
                    case 'DETAILED_DESIGN':
                        reviewCountField = $('#detailedDesignReviewCount');
                        break;
                    case 'CODING':
                        reviewCountField = $('#codeLineReviewCount');
                        break;
                    case 'UNIT_TESTING':
                        reviewCountField = $('#unitTestReviewCount');
                        break;
                    case 'INTEGRATION_TESTING':
                        reviewCountField = $('#integrationTestReviewCount');
                        break;
                }

                if (reviewCountField) {
                    reviewCountField.val(review.count);
                }
            });
        },
        error: function (error) {
            console.error('Error fetching review data:', error);
        }
    });
}
// Function to add review data
function addOrUpdateReview() {
    let projectId =$('#projectId').val();

    if(!projectId || projectId ==='#'){
        alert('Please select a project');
        return;
    }

    let addReviews = [];
    function isDevelopmentPhaseAndReviewerTypeExists(developmentPhase, reviewerType) {
        return addReviews.some(function (review) {
            return review.developmentPhase === developmentPhase && review.reviewerType === reviewerType;
        });
    }

    let basicDesignReviewCount = $('#basicDesignReviewCount').val();
    if (basicDesignReviewCount !==null && basicDesignReviewCount !=='' && !isDevelopmentPhaseAndReviewerTypeExists('BASIC_DESIGN', $('#reviewerType').val())) {
        addReviews.push({id:null, projectId:projectId, reviewerType:$('#reviewerType').val(), developmentPhase:'BASIC_DESIGN', count:basicDesignReviewCount});
    }

    let detailedDesignReviewCount = $('#detailedDesignReviewCount').val();
    if (detailedDesignReviewCount !==null && detailedDesignReviewCount !=='' && !isDevelopmentPhaseAndReviewerTypeExists('DETAILED_DESIGN', $('#reviewerType').val())) {
        addReviews.push({id:null, projectId:projectId, reviewerType:$('#reviewerType').val(), developmentPhase:'DETAILED_DESIGN', count:detailedDesignReviewCount});
    }

    let codeLineReviewCount = $('#codeLineReviewCount').val();
    if (codeLineReviewCount !==null && codeLineReviewCount !=='' && !isDevelopmentPhaseAndReviewerTypeExists('CODING', $('#reviewerType').val())) {
        addReviews.push({id:null, projectId:projectId, reviewerType:$('#reviewerType').val(), developmentPhase:'CODING', count:codeLineReviewCount});
    }

    let unitTestReviewCount = $('#unitTestReviewCount').val();
    if (unitTestReviewCount !==null && unitTestReviewCount !=='' && !isDevelopmentPhaseAndReviewerTypeExists('UNIT_TESTING', $('#reviewerType').val())) {
        addReviews.push({id:null, projectId:projectId, reviewerType:$('#reviewerType').val(), developmentPhase:'UNIT_TESTING', count:unitTestReviewCount});
    }

    let integrationTestReviewCount = $('#integrationTestReviewCount').val();
    if (integrationTestReviewCount !==null && integrationTestReviewCount !=='' && !isDevelopmentPhaseAndReviewerTypeExists('INTEGRATION_TESTING', $('#reviewerType').val())) {
        addReviews.push({id:null, projectId:projectId, reviewerType:$('#reviewerType').val(), developmentPhase:'INTEGRATION_TESTING', count:integrationTestReviewCount});
    }

    if(addReviews.length ===0){
        alert('Please enter at least one review count');
        return;
    }


    $.ajax({
        url: '/api/review/add-review',
        method: 'POST',
        data: JSON.stringify(addReviews),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (data) {
            $("#exampleModal").modal('hide');
            console.log(data);
        },
        error: function (xhr, status, error) {
            console.log(status);
            console.log(xhr.responseText);
            console.error('Error adding review:', error);
        }
    });

    // Clear the modal inputs
    $('#basicDesignReviewCount').val('');
    $('#detailedDesignReviewCount').val('');
    $('#codeLineReviewCount').val('');
    $('#unitTestReviewCount').val('');
    $('#integrationTestReviewCount').val('');


}