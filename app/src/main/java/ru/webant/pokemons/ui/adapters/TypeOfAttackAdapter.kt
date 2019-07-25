package ru.webant.pokemons.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.type_of_attack_item.view.*
import ru.webant.core.models.TypeOfAttacksEntity
import ru.webant.pokemons.R

class TypeOfAttackAdapter(private var typeOfAttackList: List<TypeOfAttacksEntity>) :
    RecyclerView.Adapter<TypeOfAttackAdapter.TypeOfAttackViewHolder>() {

    override fun onCreateViewHolder(view: ViewGroup, position: Int): TypeOfAttackViewHolder {
        return TypeOfAttackViewHolder(
            LayoutInflater
                .from(view.context)
                .inflate(R.layout.type_of_attack_item, view, false)
        )
    }

    override fun onBindViewHolder(holder: TypeOfAttackViewHolder, position: Int) {
        holder.bind(typeOfAttackList[position])
    }

    override fun getItemCount(): Int {
        return typeOfAttackList.size
    }

    inner class TypeOfAttackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(typeOfAttack: TypeOfAttacksEntity) {
            with(itemView) {
                var typeOfAttackCostText = ""
                typeOfAttack.cost.forEach {
                    typeOfAttackCostText += "$it "
                }
                pokemonCostTypeOfAttack.text = typeOfAttackCostText
                pokemonNameTypeOfAttack.text = typeOfAttack.name
                pokemonDamageTypeOfAttack.text = typeOfAttack.damage
                pokemonEnergyCostTypeOfAttack.text = typeOfAttack.energyCost.toString()
            }
        }
    }
}