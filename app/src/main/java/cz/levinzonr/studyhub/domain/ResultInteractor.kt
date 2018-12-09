package cz.levinzonr.studyhub.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class ResultInteractor<out O>{


    suspend fun run() : O {
        return withContext(Dispatchers.Main) { execute() }
    }

    abstract suspend fun execute() : O

}