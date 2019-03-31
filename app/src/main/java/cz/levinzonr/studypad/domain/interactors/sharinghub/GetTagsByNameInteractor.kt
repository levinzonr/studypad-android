package cz.levinzonr.studypad.domain.interactors.sharinghub

import cz.levinzonr.studypad.domain.interactors.BaseInputInteractor
import cz.levinzonr.studypad.domain.repository.TagsRepository
import cz.levinzonr.studypad.presentation.screens.library.publish.TagsModels
import cz.levinzonr.studypad.rest.Api

class GetTagsByNameInteractor(val api: Api, val tagsRepository: TagsRepository) : BaseInputInteractor<String, List<TagsModels.TagSection>>() {


    override suspend fun executeOnBackground(input: String): List<TagsModels.TagSection> {
       val result =  api.findTagsByName(input).await().toMutableList()
       val recent = tagsRepository.getRecent()
        return if (!result.contains(input) && input.isNotBlank()) {
           listOf(TagsModels.TagSection.Default(result.apply { add(0, input.toLowerCase()) }.toSet()))
        } else {
             listOf(TagsModels.TagSection.Recent(recent.toSet()), TagsModels.TagSection.Default(result.toSet()))
        }
    }
}