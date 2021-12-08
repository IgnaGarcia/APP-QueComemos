package com.ignagr.quecomemos.ui.main.foodSelection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ignagr.quecomemos.R
import com.ignagr.quecomemos.databinding.ItemFoodBinding
import com.ignagr.quecomemos.entities.Food

class FoodAdapter(var foodList : List<Food>,
                  val voting: Boolean = false,
                  val result: Boolean = false,
                  var onClickCheckbox : OnClickCheckbox? = null) :
    RecyclerView.Adapter<FoodAdapter.BaseViewHolder>() {

    inner class BaseViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val binding = ItemFoodBinding.bind(view)

        fun onBind(food: Food){
            binding.tvFoodName.text = food.name
            binding.tvFoodTags.text = food.getTags()

            if(voting){
                binding.checkSelected.visibility = View.VISIBLE
                binding.checkSelected.setOnCheckedChangeListener { _, isChecked ->
                    onClickCheckbox!!.onClickCheckbox(food, isChecked)
                }
            } else if (result){
                binding.tvVotes.text =
                    if(food.votes == 1) "${food.votes} Voto"
                    else "${food.votes} Votos"
                binding.tvVotes.visibility = View.VISIBLE
            }
        }
    }

    interface OnClickCheckbox{
        fun onClickCheckbox(food: Food, selected: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : BaseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food, parent, false)
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(foodList[position])
    }

    override fun getItemCount() = foodList.size
}