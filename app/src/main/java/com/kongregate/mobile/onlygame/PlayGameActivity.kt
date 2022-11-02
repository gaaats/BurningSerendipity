package com.kongregate.mobile.onlygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.kongregate.mobile.R
import com.kongregate.mobile.databinding.ActivityPlayGameBinding

class PlayGameActivity : AppCompatActivity() {

    private var _binding: ActivityPlayGameBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentStartBinding = null")

    private val listFactsLogo = listOf(
        R.drawable.pizza3,
        R.drawable.pizza4,
        R.drawable.pizza5,
        R.drawable.pizza6,
        R.drawable.pizza7,
    )

    private val listFactsText = listOf(
        "93% OF AMERICANS ORDER A PIZZA AT LEAST ONCE A MONTH. \nPizza is much more than America’s favourite comfort food. It has become a staple in their diet. More than 93% of Americans order a pizza at least once a month. This means that it is a major part of the American diet. And probably the most delicious part of their diet too.",
        "PEPPERONI IS AMERICA’S FAVOURITE PIZZA TOPPING. \nPepperoni is the most popular pizza topping choice. About 251.7 million pounds of this flavorful meaty topping is consumed annually in the US on pizza. More than 36 per cent of the pizzas ordered in pizzerias around the US feature pepperoni as a topping. With its delicious slightly smoky and spicy flavour, it’s easy to see why so many people love it. ",
        "PIZZA CONSUMPTION IS HIGHEST DURING BIG GAME DAYS.\nPizza Hut often records selling over 2 million pizzas during the Super Bowl every year. It’s not only delicious but also easy to share with friends and family as you enjoy the game together. You can easily grab a slice and wash it down with a cold beer or other drink of your choice. Life couldn’t get any better.",
        "PIZZA WAS FIRST DEVELOPED AS AN EASY AND AFFORDABLE MEAL FOR LOW-INCOME FAMILIES\nPizza may be an international favourite today but it has its origins as a last resort meal for low-income families in Naples. While pizza today is enjoyed by people across different classes of society, it was a meal reserved for low-income people who were trying to cut back on costs.",
        "NEW YORK CITY WAS HOME TO THE FIRST PIZZERIA IN THE US.\nNew York City is famous for pizza. So- much so, that it has a style of pizza named after it. In fact, the founder of Big Mario’s Pizza, Mario, learned his trade in the pizzerias of New York City. It isn’t surprising that New York’s pizzas are famous. It was the home of the first-ever pizzeria in the US, Lombardi’s. This pizzeria was originally a grocery store before it began selling pizzas in the early 1900s. This set of an explosion of pizza shops in the city. ",
        "PIZZA BECOME POPULAR IN THE US AFTER WORLD WAR II\nAlthough pizza was widely sold in the US from the early 1900s, it only became popular after World War II. This was likely because of the large number of US soldiers stationed in Italy. These soldiers grew to like pizza and helped to make the dish more popular as they influenced people on their return.",
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPlayGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            generateRandomFact()
            binding.btnImgExit.setOnClickListener {
                initAlertDialogBuilderExit()
            }

            binding.btnNextFact.setOnClickListener {
                generateRandomFact()
            }



        } catch (e: Exception) {
            makeErroe()
        }
    }

    private fun generateRandomFact() {
        binding.tvSingleIntFact.text = listFactsText.random()
        binding.imgIntFacts.setImageResource(listFactsLogo.random())
    }


    private fun makeErroe() {
        Snackbar.make(
            binding.root,
            "Oops. Something went wrong. Please try again.",
            Snackbar.LENGTH_LONG
        ).show()
        onBackPressed()
    }

    private fun initAlertDialogBuilderExit() {
        AlertDialog.Builder(this)
            .setTitle("Exit")
            .setMessage("The current data will not be saved. Do you really want to log out?")
            .setPositiveButton("Yes, Exit") { _, _ ->
                onBackPressed()
            }
            .setNegativeButton("Deny") { _, _ ->
            }
            .setCancelable(true)
            .create()
            .show()
    }
}