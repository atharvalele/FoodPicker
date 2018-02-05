package com.atharvalele.foodpicker

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileInputStream
import java.util.*

class MainActivity : AppCompatActivity() {
    // list of foods
    var foodList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // file to save added foods
        val path = getFilesDir()
        val file = File(path, "foodList.csv")

        //create new file if not already present
        file.createNewFile()

        //reading from saved foods
        val inputAsString = FileInputStream(file).bufferedReader().use {
            it.readText()
        }

        // hacky solution, TODO: find proper fix later
        var foodsInit = inputAsString.split(",")
        var foods = foodsInit.subList(1, foodsInit.size)
        println(inputAsString)
        for (food in foods) {
            foodList.add(food)
            println("Added $food")
        }

        if (!foodList.isEmpty())
            selectedFoodText.text = "Press the button to know your fate"

        pickerButton.setOnClickListener {
            val random = Random()
            var randomFood: Int
            if (foodList.isNotEmpty()) {
                randomFood = random.nextInt(foodList.count())
                selectedFoodText.text = foodList[randomFood]
            }
        }

        addFoodButton.setOnClickListener {
            val newFood = addFoodText.text.toString()
            if (newFood.isNullOrBlank())
                return@setOnClickListener
            if (!foodList.contains(newFood)) {
                foodList.add(newFood)
                println("Added $newFood")
                file.appendText(",")
                file.appendText(foodList[foodList.size - 1])
            }
            addFoodText.text.clear()
        }
    }
}
