package cz.levinzonr.studypad.data

data class SubmitReviewRequest(
    val approved: List<Long>,
    val rejected: List<Long>
)