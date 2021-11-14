package net.flow9.thisiskotlin.firebasechat.model

class User {
    var id: String = ""
    var password: String = ""
    var name: String = ""
    var fcm_token:String = "" // 여기만 추가

    constructor()

    constructor(id:String, password:String, name:String) {
        this.id = id
        this.password = password
        this.name = name
    }
}