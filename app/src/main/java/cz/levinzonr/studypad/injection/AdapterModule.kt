package cz.levinzonr.studypad.injection

import cz.levinzonr.studypad.presentation.adapters.UniversityAdapter
import org.koin.dsl.module.module


val adapterModule = module {


    factory { UniversityAdapter() }

}