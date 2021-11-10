package net.flow9.thisiskotlin.firebasechat.model

class User {
    var id: String = ""
    var password: String = ""
    var name: String = ""

    constructor()

    constructor(id:String, password:String, name:String) {
        this.id = id
        this.password = password
        this.name = name
    }
}