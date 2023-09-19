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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testnestedscroll.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ThirdFragment : Fragment() {



    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return ComposeView(requireContext()).apply {
            setContent {
                //binding.buttonFirst.setOnClickListener {
                  //  findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                //}

           //     binding.composeTxtView.setContent {
                    HorizontalPager(pageCount = 3/*, state = pagerState*/) {
                        Column {
                            LazyColumn(modifier = Modifier
                                .fillMaxSize()
                                .nestedScroll(rememberNestedScrollInteropConnection()),
                                /*state = scrollState*/) {
                                item { Text("Page $it / 3", modifier = Modifier.height(200.dp)) }
                                items(100) {
                                    Text("Item $it / 100", modifier = Modifier.height(100.dp))
                                }
                            }
                        }
                    }

                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}