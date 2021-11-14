package net.flow9.thisiskotlin.firebasechat.model

class Room {
    var id: String = ""
    var title: String = ""
    var users: String = "" // 사용자 이름을 ,(쉼표) 로 구분해서 저장

    constructor()

    constructor(title:String, creatorName: String) { // creator = 방 생성자
        this.title = title
        users = creatorName
    }
}