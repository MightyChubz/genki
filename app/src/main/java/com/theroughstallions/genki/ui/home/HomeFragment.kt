package com.theroughstallions.genki.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.theroughstallions.genki.R
import com.theroughstallions.genki.databinding.FragmentContactUsBinding
import com.theroughstallions.genki.databinding.FragmentHomeBinding
import com.theroughstallions.genki.listItems
import com.theroughstallions.genki.ui.activities.AddProductActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!


    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var listItemsList: ArrayList<listItems>

    lateinit var title: Array<String>
    lateinit var info: Array<String>
    lateinit var quantity: Array<String>
    lateinit var price: Array<String>
    lateinit var total: List<Float>
    lateinit var ints: Array<Int>
    lateinit var calTotal: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.addProduct.setOnClickListener {
            startActivity(
                Intent(
                    activity,
                    AddProductActivity::class.java
                )
            )
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInit()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.listView)
        recyclerView.layoutManager = layoutManager
        adapter = HomeViewModel(listItemsList)
        recyclerView.adapter = adapter
    }


    private fun dataInit() {
        listItemsList = arrayListOf<listItems>()

        title = arrayOf(getString((R.string.GV_Eggs)))
        info = arrayOf(getString((R.string.GV_Eggs_info)))
        quantity = arrayOf(getString(R.string.GV_Eggs_number))
        price = arrayOf(getString(R.string.GV_Eggs_price))
        //don't need an array if its a cal value
        //total = arrayOf(getString(R.string.list_total))

        //println(price.map{ it.drop(1).toFloat()})
        total = price.map { it.drop(1).toFloat() }
        println(total)
        val calTotal = total.sum()

        for (i in title.indices) {
            val list = listItems(title[0], info[0], quantity[0], price[0], calTotal.toString())
            listItemsList.add(list)
        }

    }

}