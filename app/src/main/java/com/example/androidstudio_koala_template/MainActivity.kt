package com.example.androidstudio_koala_template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidstudio_koala_template.ui.theme.AndroidStudioKoalaTemplateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidStudioKoalaTemplateTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Repte1View(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Repte1View(modifier: Modifier = Modifier) {
    val icons by remember {
        mutableStateOf(
            listOf(
                Icons.Default.AccountBox,
                Icons.Default.Add,
                Icons.Default.Build,
                Icons.Default.KeyboardArrowDown,
                Icons.Default.MailOutline,
                Icons.Default.Menu
            )
        )
    }

    var icon by remember { mutableStateOf(0) }

    var min by remember { mutableStateOf(0) }
    var max by remember { mutableStateOf(100) }

    Column (
        modifier = modifier.padding(24.dp)
    ) {
        Text(
            text = "Repte 01",
            color = Color.Blue,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        IconDropDown(icons = icons)
        Row {
            Text(
                text = "Min:",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = "Max:",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Row {
            TextField(
                value = min.toString(),
                onValueChange = {
                    if ((it.toIntOrNull() ?: min) < max) {
                        min = it.toIntOrNull() ?: min
                    }
                },
                label = { Text(text = "Min") },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )

            TextField(
                value = max.toString(),
                onValueChange = {
                    if ((it.toIntOrNull() ?: max) > min) {
                        max = it.toIntOrNull() ?: max
                    }
                },
                label = { Text(text = "Max") },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
        }

        MySlider( min = min, max = max)

        Button(
            onClick = { icon = (icon + 1) % icons.size },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.End)
        ) {
            Text(text = "Enviar")
        }

        HorizontalDivider( thickness = 4.dp )

        Icon(
            imageVector = icons[icon],
            contentDescription = icons[icon].name,
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally),
            tint = Color.Yellow
        )
    }
}

@Composable
fun MySlider(min: Int, max: Int, modifier: Modifier = Modifier) {
    var currentValue by remember { mutableStateOf(min) }

    Text(text = currentValue.toString())

    Slider(
        modifier = modifier.padding(8.dp),
        value = currentValue.toFloat(),
        onValueChange = { currentValue = it.toInt() },
        valueRange = min.toFloat()..max.toFloat()
    )
}

@Composable
fun IconDropDown(modifier: Modifier = Modifier, icons: List<ImageVector>) {
    var selectedText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.padding(10.dp)
    ) {
        OutlinedTextField (
            placeholder = { Text(
                text = "Tria un Icon",
                style = TextStyle(fontSize = 20.sp, color = Color.White)
            ) },
            value = selectedText,
            onValueChange = { selectedText = it },
            enabled = false,
            readOnly = true,
            modifier = Modifier
                .clickable { expanded = true }
                .fillMaxWidth()
                .background(Color.Blue),
            trailingIcon = {
                Icon (
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = Icons.Default.ArrowDropDown.name,
                    tint = Color.White
                )
           },
            textStyle = TextStyle(fontSize = 20.sp, color = Color.White)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
        ) {
            icons.forEach { icon ->
                DropdownMenuItem(
                    text = { Text(text = icon.name.split("Filled.")[1]) },
                    onClick = {
                        expanded = false
                        selectedText = icon.name.split("Filled.")[1]
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    Repte1View()
}