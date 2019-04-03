package cz.levinzonr.studypad.presentation.screens.library.publish

object TagsModels {


    sealed class TagSection(val tags: Set<String>) {
        class Default(tags: Set<String>) : TagsModels.TagSection(tags)
        class Recent(tags: Set<String>) : TagsModels.TagSection(tags)

    }
}