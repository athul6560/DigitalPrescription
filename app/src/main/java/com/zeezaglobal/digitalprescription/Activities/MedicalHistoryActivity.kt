package com.zeezaglobal.digitalprescription.Activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zeezaglobal.digitalprescription.Activities.ui.theme.DigitalPrescriptionTheme

class MedicalHistoryActivity : ComponentActivity() {

    private val sampleMedicalHistory = listOf(
        "Diabetes - Diagnosed 2015",
        "Hypertension - Diagnosed 2018",
        "Asthma - Childhood condition",
        "Appendectomy - Surgery in 2012"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DigitalPrescriptionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MedicalHistoryList(
                        historyItems = sampleMedicalHistory,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MedicalHistoryList(historyItems: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        itemsIndexed(historyItems) { index, item ->
            MedicalHistoryItem(item)
        }
    }
}
@Composable
fun MedicalHistoryItem(item: String) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxSize()
    ) {
        Text(
            text = item,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MedicalHistoryListPreview() {
    DigitalPrescriptionTheme {
        MedicalHistoryList(
            historyItems = listOf("Sample 1", "Sample 2", "Sample 3")
        )
    }
}