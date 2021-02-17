package net.flow9.lateinit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    lateinit var name: String
    val person: Person by lazy { Person() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = "Lionel"
        checkLateinit()
        checkLazy()
    }

    fun checkLateinit() {
        name.plus(" Messi")
        print("이름의 길이 = ${name.length}")
        print("이름의 첫글자 = ${name.substring(0,1)}")
    }

    fun checkLazy() {
        print("name=${person.name}")
    }
}

class Person {
    var name: String = "Scott"
}

