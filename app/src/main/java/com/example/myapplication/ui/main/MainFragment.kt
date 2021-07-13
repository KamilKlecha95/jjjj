package com.example.myapplication.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setPadding
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    lateinit var viewPager2: ViewPager2

    private lateinit var viewModel: MainViewModel
     var sliderHander : Handler = Handler(Looper.getMainLooper())
    val sliderRunnable = Runnable {
        viewPager2.setCurrentItem(viewPager2.currentItem+1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager2 = view.findViewById(R.id.viewPagerSlider)
        val sliderItems = mutableListOf<SlideItem>(
            SlideItem(R.drawable.ic_baseline_add_business_24),
            SlideItem(R.drawable.ic_baseline_add_comment_24),
            SlideItem(R.drawable.ic_baseline_account_balance_24)
        )
        viewPager2.adapter = SliderAdapter(sliderItems, viewPager2)
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.offscreenPageLimit = 3
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val composite = CompositePageTransformer()
        composite.addTransformer(MarginPageTransformer(40))
        composite.addTransformer(object : ViewPager2.PageTransformer{
            override fun transformPage(page: View, position: Float) {
               /* val r = 1- abs(position)
                page.scaleY = 0.85f + r * 0.15f*/
            }
        })
        viewPager2.setPageTransformer(composite)
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
               // sliderHander.removeCallbacks(sliderRunnable)
              //  sliderHander.postDelayed(sliderRunnable, 3000)
            }
        })
        viewPager2.setCurrentItem(1, true)
        lifecycleScope.launch {
            delay(3000)
            val constraintLayout : ConstraintLayout = view.findViewById(R.id.main)
           // TransitionManager.beginDelayedTransition(constraintLayout)
            val composite2 = CompositePageTransformer()
            composite2.addTransformer(MarginPageTransformer(0))
            viewPager2.setPageTransformer(composite2)
            viewPager2.setPadding(0)
            viewPager2.setCurrentItem(1, false)
            viewPager2.beginFakeDrag()
            viewPager2.fakeDragBy(0f)
            viewPager2.endFakeDrag()
        }
    }

}