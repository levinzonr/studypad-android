package cz.levinzonr.studypad.domain.repository

import cz.levinzonr.studypad.domain.models.AuthenticationResult
import cz.levinzonr.studypad.domain.models.UserProfile

/**
 * Keychain repository to operate on User entity
 */
interface KeychainRepository {

    /**
     * Login the user using email/password combination
     * @param email - user's email
     * @param password - user's password
     */
    suspend fun login(email: String, password: String)

    /**
     * Login/Signup the user using facebook
     * @param token - facebook access token
     * @return the profile of the user
     */
    suspend fun loginViaFacebook(token: String) : UserProfile

    /**
     * Login/Signup the user using Google
     * @param token - google access token
     * @return the profile of the user
     */
    suspend fun loginViaGoogle(token: String) : UserProfile

    /**
     * Creates new user account using email provider
     * @param email - email of the user that will be user for login
     * @param password - password of the user that will be used for login
     * @param firstName - first name of the user
     * @param lastName - last name of the user
     * @return newly created user's profile
     */
    suspend fun createAccount(email: String, password: String, firstName: String, lasName: String) : UserProfile
}