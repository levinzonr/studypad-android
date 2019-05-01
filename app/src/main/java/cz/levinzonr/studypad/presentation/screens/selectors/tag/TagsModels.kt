package cz.levinzonr.studypad.presentation.screens.selectors.tag

object TagsModels {


    sealed class TagSection(val tags: Set<String>) {
        class Default(tags: Set<String>) : TagSection(tags)
        class Recent(tags: Set<String>) : TagSection(tags)

    }
}