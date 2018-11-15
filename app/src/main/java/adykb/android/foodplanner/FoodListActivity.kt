package adykb.android.foodplanner

import adykb.android.foodplanner.utils.loadMeals
import adykb.android.foodplanner.utils.persistMeal
import android.graphics.Color
import android.graphics.Color.BLACK
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.Toast

class FoodListActivity : AppCompatActivity(), View.OnClickListener  {
    companion object {
        val EXTRA_LIST  = "list"
    }

    lateinit var foodList: ArrayList<String>
    lateinit var mealList : MutableList<Meal>
    lateinit var adapter: FoodListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)

        // Ajout de la toolbar
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mealList = loadMeals(this)
        foodList = ArrayList<String>()
        for(meal in mealList){
            if(!meal.available1){
                if(!foodList.contains(meal.food1)) foodList.add(meal.food1)
            }
            if(!meal.available2){
                if(!foodList.contains(meal.food2)) foodList.add(meal.food2)
            }
        }

        adapter = FoodListAdapter(foodList, this)
        val recyclerView = findViewById<RecyclerView>(R.id.foodListRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onClick(view: View) {
        var food = foodList[view.tag as Int]
        for(meal in mealList){
            if(meal.food1.equals(food)){
                meal.available1 = true
                persistMeal(this, meal)
            }
            if(meal.food2.equals(food)){
                meal.available2 = true
                persistMeal(this, meal)
            }
        }
        foodList.removeAt(view.tag as Int)
        adapter.notifyDataSetChanged()
    }

    // Ajout de la toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_food_list, menu)
        return true
    }
}
