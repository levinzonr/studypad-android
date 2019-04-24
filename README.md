# Goal

Goal: Most Modern Android App Possible ( Tech wise )

# Project Description

Currenty project consists of 4 apps, on of them being Android application and the other - spring REST API. Both written in Kotlin

## Technical Description

### Architecture

App is written in MVVM Architecture, so thats all pieces of app's logic are falling into following categories(layers):
  - Domain (Model layer to hold all entities)
  - Repositories (business logic layer)
  - Interactors (will represent use-cases)
  - ViewModel - binds view and domain layer together
  - View

### Integrations

App will communicate with different services, including third parties services

 - Spring REST API as a primary backed service
 - Google Auth will be used to easily authenticate users
 - Facebook Auth will be used to easily authenticate users
 - Firebase Cloud Messaging for PUSH notifications implementation


### Android Related Description (Go Go Jetpack)
- Min sdk version: 21
- `Room` as a local persistent storage
- `Android Navigation Component` is used for Navigation
- `ViewModel` as ViewModel ..
- `Koin` for dependency injection
- `Kotlin Coroutines` for async calls
- `LiveData` as main app state holder
- `Material Design v2`

## App Description

Note taking app with social and learning features. Core functionality is of course note taking, these notes are stored in notebooks.
 As a part of user registration flow user will have possibility to choose his school/university for better experience

An example of social features: *As an app user I want to be able to search notebooks created by other users
using good filtering options. If find some particular notebook interesting I want to be able to share it with other friends, add to my library or leave a comment*

An example of learning features: *As an app user I want to take advantages of Challenges which will allow me to learn content made by me or other users.*

### Possible use-cases

- Create/Edit/Delete notes
- Create/Edit/Delete notebooks
- Publish/share your notebook with others
- Leave/Delete comment on someones notebook
- Download and add  notebook to library
- Login/Register using Email
- Authentication using Facebook
- Authentication using Google
- Profile edits
- Access to content using deep links
-Challenges

## Existing solutions - Quizlet

- Quizlet main focus is learning languages. It was designed this way, which makes it functionality limited 
- Quizlet doesn't have any specific filtering options. Finding something specific is very hard when the only distinction between notebooks
is their name
- Notebook Management is not very straightforward
- Design could be be improved
- Paid version hides a lot of functionality
