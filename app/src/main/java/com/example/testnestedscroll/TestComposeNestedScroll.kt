package com.example.testnestedscroll

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import kotlin.math.roundToInt
import androidx.compose.material.TopAppBar


class TestComposeNestedScroll : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Option1NestedScrollModifier()
            }
        }
    }

    @Composable
    private fun Option2CustomPager() {
        // TODO
    }
    @Composable
    private fun Option1NestedScrollModifier() {

        val toolbarHeight = 48.dp
        val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }

        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    val delta = available.y
                    val newOffset = toolbarOffsetHeightPx.value + delta
                    toolbarOffsetHeightPx.value = newOffset//.coerceIn(-toolbarHeightPx, 0f)
                    return Offset.Zero
                }

            }
        }
        Box(Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)) {
            LazyColumn(contentPadding = PaddingValues(top = toolbarHeight)) {
                items(100) { index ->
                    Text("I'm item $index", modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp))
                }
            }
            TopAppBar(modifier = Modifier
                .height(toolbarHeight)
                .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt()) },
                title = { Text("toolbar offset is ${toolbarOffsetHeightPx.value}") })
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun Option3UserScrollEnabled() {
        val rememberScrollState = rememberScrollState()
        val screenHeaderHeight = LocalConfiguration.current.screenHeightDp.dp
        val pagerState = rememberPagerState()
        val endReached by remember {
            derivedStateOf {
                rememberScrollState.value > (rememberScrollState.maxValue - 50)
            }
        }

        Column(modifier = Modifier
            .verticalScroll(state = rememberScrollState)
            .fillMaxHeight()) {

            if (endReached) {
                Log.e("endReached", "endReached")
            } else {
                Log.e("rememberScrollState", rememberScrollState.value.toString())
            }

            Box(modifier = Modifier
                .background(Color.Black)
                .height(200.dp)
                .fillMaxWidth()) {
                Text("Header")
            }

            HorizontalPager(pageCount = 3, state = pagerState) {

                val lazyListState = rememberLazyListState()

                LazyColumn(
                    state = lazyListState,
                    userScrollEnabled = endReached,
                    modifier = Modifier.height(screenHeaderHeight),
                    /*state = scrollState*/
                ) {

                    items(100) {

                        Log.e("item", "item : $it")
                        Box(modifier = Modifier
                            .background(color = Color.LightGray)
                            .height(200.dp)
                            .fillMaxWidth()) {

                            Text("Item $it / 100", modifier = Modifier.height(100.dp))
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}