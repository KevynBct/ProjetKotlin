package adykb.android.foodplanner

import adykb.android.foodplanner.utils.deleteMeal
import adykb.android.foodplanner.utils.loadMeals
import adykb.android.foodplanner.utils.persistMeal
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var mealList: MutableList<Meal>
    lateinit var adapter: MealAdapter
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var menuToolbar : Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ajout de la toolbar
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        coordinatorLayout = findViewById(R.id.coordinatorLayout) as CoordinatorLayout

        mealList = loadMeals(this)

        adapter = MealAdapter(mealList, this)
        val recyclerView = findViewById<RecyclerView>(R.id.mealRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        findViewById<FloatingActionButton>(R.id.addMeal).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.tag != null) {
            openMeal(view.tag as Int)
        } else {
            when (view.id) {
                R.id.addMeal -> addMeal()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode != MealActivity.REQUEST_EDIT_MEAL || data == null){
            return
        }
        when(resultCode){
            MealActivity.SAVE_CODE -> {
                var meal = data.getParcelableExtra<Meal>(MealActivity.EXTRA_MEAL)
                var index  = data.getIntExtra(MealActivity.EXTRA_INDEX, -1)
                if(checkValues(meal)){
                    if(index < 0)
                        mealList.add(meal)
                    else
                        mealList[index] = meal
                    adapter.notifyDataSetChanged()
                    persistMeal(this, meal)
                    updateCountToolbar()
                }
            }
            MealActivity.DELETE_CODE ->{
                var index = data.getIntExtra(MealActivity.EXTRA_INDEX, -1)
                if(index >= 0){
                    var meal = mealList.removeAt(index)
                    deleteMeal(this,meal)
                    Snackbar.make(coordinatorLayout, "Le repas à bien été supprimé", Snackbar.LENGTH_SHORT).show()
                    adapter.notifyDataSetChanged()
                }

            }
        }
    }

    // Ajout de la toolbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_main, menu)
        menuToolbar = menu
        return true
    }

    fun updateCountToolbar(){
        val itemMidi = menuToolbar.findItem(R.id.counterMidi)
        var cptMidi = 0
        val itemEvening = menuToolbar.findItem(R.id.counterEvening)
        var cptEvening = 0

        for(meal in mealList){
            if(meal.evening) cptEvening++ else cptMidi++
        }

        itemMidi.setTitle("Midi "+cptMidi.toString())
        itemEvening.setTitle(cptEvening.toString()+" Soir")
    }

    // Action des boutons de la toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.foodList -> {
                intent = Intent(this,FoodListActivity::class.java)
                startActivity(intent)
            }
        }
        return  super.onOptionsItemSelected(item)
    }

    // Ajout d'une note
    fun addMeal(){
        val intent = Intent(this, MealActivity::class.java)
        intent.putExtra(MealActivity.EXTRA_MEAL, Meal() as Parcelable)
        startActivityForResult(intent, MealActivity.REQUEST_EDIT_MEAL)
    }

    fun openMeal(index : Int){
        var meal= mealList[index]
        val intent = Intent(this, MealActivity::class.java)
        intent.putExtra(MealActivity.EXTRA_MEAL, meal as Parcelable)
        intent.putExtra(MealActivity.EXTRA_INDEX, index)
        startActivityForResult(intent, MealActivity.REQUEST_EDIT_MEAL)
    }

    fun checkValues(meal : Meal) : Boolean{
        if(meal.food1.trim().isBlank() && meal.food2.trim().isBlank())
            return false
        else
            return true
    }
}
