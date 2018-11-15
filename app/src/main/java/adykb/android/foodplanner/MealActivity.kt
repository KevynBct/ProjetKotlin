package adykb.android.foodplanner

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.Switch
import android.widget.TextView

class MealActivity : AppCompatActivity() {

    companion object {
        val REQUEST_EDIT_MEAL = 0
        val SAVE_CODE = 1
        val DELETE_CODE = 2
        val EXTRA_MEAL  = "meal"
        val EXTRA_INDEX = "index"
    }

    var mealIndex : Int = -1
    lateinit var meal : Meal
    lateinit var editFoodView1 : TextView
    lateinit var editFoodView2 : TextView
    lateinit var checkAvailableView1 : CheckBox
    lateinit var checkAvailableView2 : CheckBox
    lateinit var eveningView : Switch
    lateinit var note : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)

        // Ajout de la toolbar
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if (intent.hasExtra(EXTRA_MEAL)){
            loadMealInfo()
        }
    }

    // Sauvegarde lors du retour en arrière
    override fun onBackPressed() {
        saveMeal()
        super.onBackPressed()
    }

    // Ajout de la toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_meal, menu)
        return true
    }

    // Action des boutons de la toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.deleteMeal -> {
                deleteNote()
                return true
            }
        }
        return  super.onOptionsItemSelected(item)
    }

    // Sauvegarder la note en cours
    fun saveMeal (){
        meal.food1 = editFoodView1.text.toString()
        meal.food2 = editFoodView2.text.toString()
        if(meal.food1.trim().isNotEmpty()){
            meal.available1 = checkAvailableView1.isChecked
        }else{
            meal.available1 = true
        }
        if(meal.food2.trim().isNotEmpty()){
            meal.available2 = checkAvailableView2.isChecked
        }else{
            meal.available2 = true
        }
        meal.evening = eveningView.isChecked
        meal.note = note.text.toString()

        intent = Intent()
        intent.putExtra(EXTRA_MEAL, meal as Parcelable)
        intent.putExtra(EXTRA_INDEX, mealIndex)
        setResult(SAVE_CODE, intent)
        finish()
    }

    // Delete current meal
    fun deleteNote(){
        val fragment = ConfirmDeleteDialogFragment()
        fragment.listener = object: ConfirmDeleteDialogFragment.ConfirmDeleteListener {
            override fun onDialogPositiveClick() {
                intent = Intent()
                intent.putExtra(EXTRA_INDEX,mealIndex)
                setResult(DELETE_CODE, intent)
                finish()
            }

            override fun onDialogNegativeClick() {}

        }
        fragment.show(supportFragmentManager, "confirmDelete")
    }

    fun loadMealInfo(){
        meal      = intent.getParcelableExtra(EXTRA_MEAL)
        mealIndex = intent.getIntExtra(EXTRA_INDEX, -1)

        editFoodView1 = findViewById(R.id.editFood1)
        editFoodView2 = findViewById(R.id.editFood2)
        checkAvailableView1 = findViewById(R.id.checkAvailable1)
        checkAvailableView2 = findViewById(R.id.checkAvailable2)
        eveningView = findViewById(R.id.midiEvening)
        note = findViewById(R.id.note)

        editFoodView1.text = meal.food1
        editFoodView2.text = meal.food2
        checkAvailableView1.isChecked = meal.available1
        checkAvailableView2.isChecked = meal.available2
        eveningView.isChecked = meal.evening
        note.text = meal.note
    }
}