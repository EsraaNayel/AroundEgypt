package com.esraa.aroundegypt.ui.list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.esraa.aroundegypt.domain.models.Experience
import com.esraa.aroundegypt.ui.list.viewmodel.ExperienceViewModel


class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<ExperienceViewModel>(factoryProducer = {
        ExperienceViewModel.Factory
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Column(
                Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
            ) {
                HomeScreen(viewModel)
            }
        }
    }
}

@Composable
fun SearchBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Menu, contentDescription = "Menu")
        Spacer(modifier = Modifier.width(8.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Try “Luxor”") },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(Icons.Default.FilterList, contentDescription = "Filter")
    }
}

@Composable
fun WelcomeSection() {
    Column() {
        Text(text = "Welcome!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(
            text = "Now you can explore any experience in 360 degrees and get all the details about it all in one place.",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun ExperienceCard(experience: Experience, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Box {
            Image(
                painter = rememberImagePainter(experience.cover_photo),
                contentDescription = experience.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )

            // Icons and Tags
            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .background(Color.Blue.copy(alpha = 0.7f), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            ) {
                Text(text = "RECOMMENDED", color = Color.White, fontSize = 12.sp)
            }

            // Views and Likes
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Visibility, contentDescription = "Views", tint = Color.White
                )
                Text(text = experience.views_no.toString(), color = Color.White)
            }
        }
        Text(
            text = experience.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun RecommendedExperiences(recommendedExperiences: List<Experience>) {
    Text(text = "Recommended Experiences", fontSize = 20.sp, fontWeight = FontWeight.Bold)

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = rememberLazyListState()
        ) {
            items(recommendedExperiences) { experience ->
                ExperienceCard(experience, Modifier
                    .width(maxWidth - (maxWidth / 10))
                    .padding(8.dp)
                    .clickable { })
            }
        }
    }
}

@Composable
fun MostRecentExperiences(recentExperiences: List<Experience>) {
    Text(text = "Most Recent", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    LazyColumn {
        items(recentExperiences) { experience ->
            ExperienceCard(experience, Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { })
        }
    }
}

@Composable
fun HomeScreen(viewModel: ExperienceViewModel) {
    val recentExperiences by
    viewModel.recentExperiences.collectAsState()
    val recommendedExperiences by
    viewModel.recommendedExperiences.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar()
        WelcomeSection()
        RecommendedExperiences(recommendedExperiences)
        MostRecentExperiences(recentExperiences)
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    HomeScreen(
        viewModel = TODO()
    )
}