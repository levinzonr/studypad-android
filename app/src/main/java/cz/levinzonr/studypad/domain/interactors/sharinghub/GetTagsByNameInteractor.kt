package cz.levinzonr.studypad.domain.interactors.sharinghub

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.repository.TagsRepository
import cz.levinzonr.studypad.presentation.screens.selectors.tag.TagsModels
import cz.levinzonr.studypad.rest.Api
import timber.log.Timber

class GetTagsByNameInteractor(val api: Api, val tagsRepository: TagsRepository) :
    BaseInputInteractor<String, List<TagsModels.TagSection>>() {


    override suspend fun executeOnBackground(input: String): List<TagsModels.TagSection> {
        val result = api.findTagsByNameAsync(input)
            .await()
            .toMutableList()
            .apply { if (input.isNotBlank() && !contains(input)) add(0, input.toLowerCase()) }

        val recent = tagsRepository.getRecent()
        Timber.d("Input: ")
        return if (input.isBlank()) {
            listOf(TagsModels.TagSection.Recent(recent.toSet()), TagsModels.TagSection.Default(result.toSet()))
        } else {
            listOf(TagsModels.TagSection.Default(result.toSet()))
        }

    }
}