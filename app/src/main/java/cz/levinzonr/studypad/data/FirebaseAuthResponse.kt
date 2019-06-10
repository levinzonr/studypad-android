package cz.levinzonr.studypad.data

import cz.levinzonr.studypad.domain.models.UserProfile


data  class FirebaseAuthResponse(val token: String, val user: UserProfile)