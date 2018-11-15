package adykb.android.foodplanner

import android.graphics.Color
import android.graphics.Color.*
import android.graphics.drawable.Drawable
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Switch
import android.widget.TextView

class MealAdapter (val mealList : List<Meal>, val itemClickListener : View.OnClickListener) : RecyclerView.Adapter<MealAdapter.ViewHolder>() {
    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val cardView   = itemView.findViewById<CardView>(R.id.cardView)
        val food1      = itemView.findViewById<TextView>(R.id.food1)
        val food2      = itemView.findViewById<TextView>(R.id.food2)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewItem        = inflater.inflate(R.layout.item_meal, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal= mealList[position]
        holder.cardView.setOnClickListener(itemClickListener)
        holder.cardView.tag   = position
        holder.food1.text = meal.food1
        holder.food2.text = meal.food2
        if(meal.evening){
            holder.cardView.setBackgroundResource(R.drawable.soir)
            if(!meal.available1) holder.food1.setTextColor(RED) else holder.food1.setTextColor(WHITE)
            if(!meal.available2) holder.food2.setTextColor(RED) else holder.food2.setTextColor(WHITE)
        } else{
            holder.cardView.setBackgroundResource(R.drawable.midi)
            if(!meal.available1) holder.food1.setTextColor(RED) else holder.food1.setTextColor(BLACK)
            if(!meal.available2) holder.food2.setTextColor(RED) else holder.food2.setTextColor(BLACK)
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }
}
