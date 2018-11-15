package adykb.android.foodplanner

import adykb.android.foodplanner.utils.loadMeals
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class FoodListAdapter(val foodList : List<String>, val itemClickListener : View.OnClickListener): RecyclerView.Adapter<FoodListAdapter.ViewHolder>() {

    class ViewHolder (itemView : View, mealList: MutableList<Meal>) : RecyclerView.ViewHolder(itemView){
        val cardView = itemView.findViewById<CardView>(R.id.cardViewList)
        val textCount = itemView.findViewById<TextView>(R.id.textCount)
        val food     = itemView.findViewById<TextView>(R.id.food)
        val mealList = mealList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewItem        = inflater.inflate(R.layout.item_list, parent, false)
        val mealList = loadMeals(parent.context)
        return FoodListAdapter.ViewHolder(viewItem, mealList)
    }

    override fun onBindViewHolder(holder: FoodListAdapter.ViewHolder, position: Int) {
        val food= foodList[position]
        holder.cardView.setOnClickListener(itemClickListener)
        holder.cardView.tag   = position
        holder.food.text = food

        var cpt = 0
        for (meal in holder.mealList){
            if(meal.food1.equals(food) && !meal.available1) cpt++
            if(meal.food2.equals(food) && !meal.available2) cpt++
        }

        holder.textCount.text = cpt.toString()
    }

    override fun getItemCount(): Int {
        return foodList.size
    }
}