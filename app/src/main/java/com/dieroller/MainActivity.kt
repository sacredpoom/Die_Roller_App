package com.dieroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dieroller.R.drawable
import com.dieroller.ui.theme.DieRollerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DieRollerTheme {
                DiceRollerApp()
            }
        }
    }
}

@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    val diceTypes = listOf(4, 6, 8,10, 12, 20, 100)
    var currentDiceType by remember { mutableStateOf(diceTypes.first()) }
    var result by remember { mutableStateOf(0) }

    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(modifier = Modifier.padding(vertical = 16.dp)) {
            items(diceTypes) { dice ->
                DieTypeItem(diceType = dice, isSelected = dice == currentDiceType) { selectedType ->
                    currentDiceType = selectedType
                }
            }
        }
        Image(
            painter = painterResource(id = determineDiceImage(currentDiceType)),
            contentDescription = "Current Die"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.result, result))
        Button(onClick = { result = (1..currentDiceType).random() }) {
            Text(stringResource(R.string.roll))
        }
    }
}

@Composable
fun DieTypeItem(
    diceType: Int,
    isSelected: Boolean,
    onDiceSelected: (Int) -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable { onDiceSelected(diceType) }
            .background(color = backgroundColor)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "D$diceType",
            color = contentColor
        )
    }
}


fun determineDiceImage(diceType: Int): Int {
    return when (diceType) {
        4 -> drawable.hd4
        6 -> drawable.hd6
        8 -> drawable.hd8
        10 -> drawable.hd10
        12 -> drawable.hd12
        20 -> drawable.hd20
        100 -> drawable.hd100
        else -> drawable.hd20
    }
}

@Preview
@Composable
fun DiceRollerApp() {
    DiceWithButtonAndImage(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
    )
}