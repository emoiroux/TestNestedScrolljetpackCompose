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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import kotlin.math.roundToInt
import androidx.compose.material.TopAppBar


class FirstFragmentPureCompose : Fragment() {

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Test()
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
    @Composable
    private fun Test() {

// here we use LazyColumn that has build-in nested scroll, but we want to act like a
// parent for this LazyColumn and participate in its nested scroll.
// Let's make a collapsing toolbar for LazyColumn
        val toolbarHeight = 48.dp
        val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
// our offset to collapse toolbar
        val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
// now, let's create connection to the nested scroll system and listen to the scroll
// happening inside child LazyColumn
        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    // try to consume before LazyColumn to collapse toolbar if needed, hence pre-scroll
                    val delta = available.y
                    val newOffset = toolbarOffsetHeightPx.value + delta
                    toolbarOffsetHeightPx.value = newOffset//.coerceIn(-toolbarHeightPx, 0f)
                    // here's the catch: let's pretend we consumed 0 in any case, since we want
                    // LazyColumn to scroll anyway for good UX
                    // We're basically watching scroll without taking it
                    return Offset.Zero
                }

            }
        }
        Box(Modifier
            .fillMaxSize()
            // attach as a parent to the nested scroll system
            .nestedScroll(nestedScrollConnection)) {
            // our list with build in nested scroll support that will notify us about its scroll
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
    private fun NestedScroll() {
        val rememberScrollState = rememberScrollState()
        val screenHeaderHeight = LocalConfiguration.current.screenHeightDp.dp
        val pagerState = rememberPagerState()

        Column(modifier = Modifier
            .verticalScroll(state = rememberScrollState)
            .fillMaxHeight()) {

            var delta by remember { mutableStateOf(0f) }

            val nestedScrollConnection = remember {
                object : NestedScrollConnection {
                    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                        delta -= available.y
                        Log.e("onPreScroll", "delta : $delta")
                        return Offset.Zero
                    }
                }
            }


            Box(modifier = Modifier
                .background(Color.Black)
                .height(200.dp)

                .fillMaxWidth()
                .offset(y = delta.dp)) {
                Text("Header")
            }

            HorizontalPager(pageCount = 3, state = pagerState) {
                val lazyListState = rememberLazyListState()


                LazyColumn(
                    state = lazyListState,
                    userScrollEnabled = true,
                    modifier = Modifier
                        .height(screenHeaderHeight)
                        .nestedScroll(nestedScrollConnection),
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

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun UserScrollEnabled() {
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

            //  snapshotFlow { endReached }.collect {
            //    Log.e("snapshotFlow", it.toString())
            // }

        }
    }
}