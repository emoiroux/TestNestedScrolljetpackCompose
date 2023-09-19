package com.example.testnestedscroll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testnestedscroll.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    @OptIn(ExperimentalFoundationApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // val scrollState = LazyListState()
       // val scrollByState = mutableStateOf(0f)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        /*
        binding.scrollView.setScrollViewListener(scrollViewListener = object : ScrollViewListener {
            override fun onScrollChanged(scrollView: ScrollViewExt?, x: Int, y: Int, oldx: Int, oldy: Int) {
                val view = scrollView!!.getChildAt(scrollView.childCount - 1) as View
                val diff = view.bottom - (scrollView.height + scrollView.scrollY)

                val scrollBy = y - oldy
                val scrollUp = scrollBy < 0

                // if diff is zero, then the bottom has been reached
                if (diff == 0) {

                    scrollByState.value = scrollBy.toFloat()

                    /*
                     view.postDelayed({
                         lifecycleScope.launch {
                             scrollState.animateScrollBy(scrollBy.toFloat())
                             binding.composeTxtView.invalidate()
                         }
                     }, 300)

                     */
                } else if (scrollUp && scrollState.canScrollBackward) {

                    scrollByState.value = scrollBy.toFloat()

                    /*
                    view.postDelayed({

                        lifecycleScope.launch {
                            scrollState.animateScrollBy(scrollBy.toFloat())
                            view.invalidate()
                        }
                    }, 200)


                     */

                }
            }
        })

         */

        binding.composeTxtView.setContent {

            //    val coroutineScope = rememberCoroutineScope()

            /*
            LaunchedEffect(scrollByState.value) {
                coroutineScope.launch {
                    if (scrollByState.value != 0f) {
                        scrollState.animateScrollBy(scrollByState.value)
                    }
                }
            }

             */

          //  val pagerState = rememberPagerState(initialPage = 0)

            //  var currentPageIndex by remember { mutableStateOf(0) }
            //   val hapticFeedback = LocalHapticFeedback.current

            /*
             LaunchedEffect(pagerState) {
                 snapshotFlow { pagerState.currentPage }.collect { currentPage ->
                     if (currentPageIndex != currentPage) {

                         scrollState.scrollToItem(0)
                     }
                     // Anything to be triggered by page-change can be done here
                 }
             }

             */


            pager()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}