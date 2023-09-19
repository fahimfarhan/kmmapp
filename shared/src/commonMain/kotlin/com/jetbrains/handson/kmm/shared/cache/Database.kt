package com.jetbrains.handson.kmm.shared.cache

import com.example.kmmapp.model.Links
import com.example.kmmapp.model.Patch
import com.example.kmmapp.model.RocketLaunch

internal class Database (databaseDriverFactory: DatabaseDriverFactory) {
    private val database: AppDatabase = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery: AppDatabaseQueries = database.appDatabaseQueries


    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllLaunches()
        }
    }

    internal fun createLaunches(launches: List<RocketLaunch>) {
        dbQuery.transaction {
            launches.forEach { launch ->
                insertLaunch(launch)
            }
        }
    }

    private fun insertLaunch(launch: RocketLaunch) {
        dbQuery.insertLaunch(
            flightNumber = launch.flightNumber.toLong(),
            missionName = launch.missionName,
            details = launch.details,
            launchSuccess = launch.launchSuccess ?: false,
            launchDateUTC = launch.launchDateUTC,
            patchUrlSmall = launch.links.patch?.small,
            patchUrlLarge = launch.links.patch?.large,
            articleUrl = launch.links.article
        )
    }

    internal fun getAllLaunches(): List<RocketLaunch> {
        return dbQuery.selectAllLaunchesInfo { flightNumber, missionName, details, launchSuccess, launchDateUTC, patchUrlSmall, patchUrlLarge, articleUrl ->
            mapLaunchSelecting(flightNumber, missionName, details, launchSuccess, launchDateUTC, patchUrlSmall, patchUrlLarge, articleUrl)
        }.executeAsList()
    }


    private fun mapLaunchSelecting(
        flightNumber: Long,
        missionName: String,
        details: String?,
        launchSuccess: Boolean?,
        launchDateUTC: String,
        patchUrlSmall: String?,
        patchUrlLarge: String?,
        articleUrl: String?
    ): RocketLaunch {
        return RocketLaunch(
            flightNumber = flightNumber.toInt(),
            missionName = missionName,
            details = details,
            launchDateUTC = launchDateUTC,
            launchSuccess = launchSuccess,
            links = Links(
                patch = Patch(
                    small = patchUrlSmall,
                    large = patchUrlLarge
                ),
                article = articleUrl
            )
        )
    }

//    ------------

    internal fun getAllLaunchesV2ProbablyHorrible(): List<RocketLaunch> {
//        return dbQuery.selectAllLaunchesInfo().executeAsList()
        val listOfLaunch: List<Launch> = dbQuery.selectAllLaunchesInfo().executeAsList()
        val listOfRocketLaunch = listOfLaunch.map { l: Launch -> mapLaunchSelecting(l.flightNumber, l.missionName, l.details, l.launchSuccess,
            l.launchDateUTC,l.patchUrlSmall, l.patchUrlLarge, l.articleUrl )
        }.toList()
        return listOfRocketLaunch
    }

    internal fun getAllLaunchesV3(): List<RocketLaunch> {
        return dbQuery.selectAllLaunchesInfo(::mapLaunchSelecting).executeAsList()
    }


}