package by.it.academy.boardgameplayersfinder.entity

class GameEventItem() {
    lateinit var id: String
    lateinit var gameEventOwner: String
    lateinit var gameName: String
    var playersAvailable: Int = 0
    var maximumNumberOfPlayers: Int = 0
    lateinit var placeToPlayAddress: String
    lateinit var dateOfEvent: String
    lateinit var timeOfEvent: String
    lateinit var listOfPlayers: MutableMap<String, String>
    lateinit var description: String

    constructor(
        id: String,
        gameEventOwner: String,
        gameName: String,
        playersAvailable: Int,
        maximumNumberOfPlayers: Int,
        placeToPlayAddress: String,
        dateOfEvent: String,
        timeOfEvent: String,
        listOfPlayers: MutableMap<String, String>,
        description: String
    ) : this() {
        this.id = id
        this.gameEventOwner = gameEventOwner
        this.gameName = gameName
        this.playersAvailable = playersAvailable
        this.maximumNumberOfPlayers = maximumNumberOfPlayers
        this.placeToPlayAddress = placeToPlayAddress
        this.dateOfEvent = dateOfEvent
        this.timeOfEvent = timeOfEvent
        this.listOfPlayers = listOfPlayers
        this.description = description
    }
}