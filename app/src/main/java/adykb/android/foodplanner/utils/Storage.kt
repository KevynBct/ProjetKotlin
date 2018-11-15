package adykb.android.foodplanner.utils

import adykb.android.foodplanner.Meal
import android.content.Context
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

private val TAG = "storage"

// Save meal
fun persistMeal(context : Context, meal : Meal) {
    if(meal.fileName.isEmpty()){
        meal.fileName = UUID.randomUUID().toString()+".meal"
    }

    val fileOutput = context.openFileOutput(meal.fileName, Context.MODE_PRIVATE)
    val outputStream = ObjectOutputStream(fileOutput)
    outputStream.writeObject(meal)
    outputStream.close()
}

// Delete meal
fun deleteMeal(context: Context, meal: Meal){
    context.deleteFile(meal.fileName)
}

// Load meal list
fun loadMeals(context: Context) : MutableList<Meal> {
    val notes = mutableListOf<Meal>()

    val notesDir = context.filesDir
    for (fileName in notesDir.list()){
        notes.add(loadNote(context, fileName))
    }

    return notes
}

// Charger une note à partir d'un fichier
private fun loadNote(context: Context, fileName : String) : Meal {
    val fileInput = context.openFileInput(fileName)
    val inputStream = ObjectInputStream(fileInput)
    val note = inputStream.readObject() as Meal
    inputStream.close()

    return note
}
