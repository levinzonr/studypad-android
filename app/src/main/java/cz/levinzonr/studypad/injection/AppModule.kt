package cz.levinzonr.studypad.injection

/**
 * Core koin module
 */
val appModule = listOf(
    repositoryModule,
    rest,
    interactorModule,
    adapterModule,
    viewModelModule,
    managerModule,
    presentation
)
