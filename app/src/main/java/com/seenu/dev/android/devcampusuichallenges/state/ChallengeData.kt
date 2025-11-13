package com.seenu.dev.android.devcampusuichallenges.state

import com.seenu.dev.android.devcampusuichallenges.navigation.Route


val months
    get() = listOf(
        MonthUiModel(
            "November 2025",
            "Black Friday Madness",
            novemberChallenges
        ),
        MonthUiModel(
            "October 2025",
            "Android Halloween Lab",
            octoberChallenges
        ),
        MonthUiModel(
            "September 2025",
            "Designing the Festival",
            septemberChallenges
        ),
        MonthUiModel(
            "August 2025",
            "Async Adventures",
            augustChallenges
        ),
        MonthUiModel(
            "July 2025",
            "Conversations",
            julyChallenges
        ),
        MonthUiModel(
            "June 2025",
            "Birthday Celebrations",
            juneChallenges
        ),
    )

private val novemberChallenges
    get() = listOf(
        ChallengeUiModel(
            "Hidden Discount Swipe",
            Route.November1
        ),
        ChallengeUiModel(
            "Sticky Ad Banner",
            Route.November2
        ),
        ChallengeUiModel(
            "Circular Stock Tracker",
            Route.November3
        ),
        ChallengeUiModel(
            "Global Black Friday Deals",
            Route.November5
        ),
    )

private val octoberChallenges
    get() = listOf(
        ChallengeUiModel(
            "Haunted Theme Switcher",
            Route.October2
        ),
        ChallengeUiModel(
            "Cursed Countdown",
            Route.October3
        ),
        ChallengeUiModel(
            "Coven Booking Desk",
            Route.October4
        ),
        ChallengeUiModel(
            "Halloween Skeleton Puzzle",
            Route.October5
        ),
    )

private val septemberChallenges
    get() = listOf(
        ChallengeUiModel(
            "Expandable Lineup List",
            Route.September1
        ),
        ChallengeUiModel(
            "Ticket Builder",
            Route.September2
        ),
        ChallengeUiModel(
            "Festival Map",
            Route.September3
        ),
        ChallengeUiModel(
            "Accessible Audio Schedule",
            Route.September4
        ),
        ChallengeUiModel(
            "Multi Stage Timeline",
            Route.September5
        ),
    )

private val augustChallenges
    get() = listOf(
        ChallengeUiModel(
            "Thermometer Trek",
            Route.August1
        ),
        ChallengeUiModel(
            "Order Queue Outpost",
            Route.August2
        ),
    )

private val julyChallenges
    get() = listOf(
        ChallengeUiModel(
            "Emoji Reaction Bubble",
            Route.July
        ),
        ChallengeUiModel(
            "Bottom Navigation with Unread Badge",
            Route.July2
        ),
        ChallengeUiModel(
            "Message Card",
            Route.July3
        ),
    )

private val juneChallenges
    get() = listOf(
        ChallengeUiModel(
            "Birthday Invitation Card",
            Route.June
        )
    )