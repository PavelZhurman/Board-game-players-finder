package by.it.academy.boardgameplayersfinder.entity

class GameEventItem() {
    var id: String = ""
    var gameName: String = ""
    var playersNeedToFind: Int = 0
    var placeToPlayAddress: String = ""
    var dateOfEvent: String = ""
    var timeOfEvent: String = ""

    constructor(
        id: String,
        gameName: String,
        playersNeedToFind: Int,
        placeToPlayAddress: String,
        dateOfEvent: String,
        timeOfEvent: String
    ) : this() {
        this.id = id
        this.gameName = gameName
        this.playersNeedToFind = playersNeedToFind
        this.placeToPlayAddress = placeToPlayAddress
        this.dateOfEvent = dateOfEvent
        this.timeOfEvent = timeOfEvent
    }
}
