package cz.levinzonr.studypad.presentation.screens.sharinghub.search

import android.os.Parcelable
import cz.levinzonr.studypad.domain.models.OrderBy
import cz.levinzonr.studypad.domain.models.Topic
import cz.levinzonr.studypad.domain.models.University
import kotlinx.android.parcel.Parcelize

object NotebookSearchModels {



    @Parcelize
    data class SearchState(
        val university: University? = null,
        val topic: List<Topic> = listOf(),
        val tags : List<String> = listOf(),
        val query: String = "",
        val orderBy: OrderBy? = null
    ) : Parcelable {
        val isDefaultState: Boolean
            get() {
                return university == null && topic.isEmpty() && tags.isEmpty() && query.isEmpty() && orderBy == null
            }
    }


    enum class EmptyType {
        Error, Empty, Default
    }

}