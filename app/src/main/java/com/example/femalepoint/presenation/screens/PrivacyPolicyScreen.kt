package com.example.femalepoint.presenation.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrivacyPolicyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Privacy Policy",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.tertiary
        )

        Spacer(modifier = Modifier.height(16.dp))

        PolicySection("Information Collection and Use") {
            PolicyText("The Application collects information when you download and use it. This includes:")
            BulletPoint("Your device's IP address")
            BulletPoint("Pages visited, time spent, and visit date")
            BulletPoint("Operating system used on your device")
            BulletPoint("No precise location data is collected")
        }

        PolicySection("Third-Party Access") {
            PolicyText("Aggregated, anonymized data is shared with third-party services like:")
            BulletPoint("Google Play Services")
            BulletPoint("Firebase Crashlytics")
        }

        PolicySection("Opt-Out Rights") {
            PolicyText("You can stop all data collection by uninstalling the app.")
        }

        PolicySection("Data Retention Policy") {
            PolicyText("User data is retained as long as you use the app. To delete data, contact:")
            ClickableEmail(email = "nutrinonovarage@gmail.com")
        }

        PolicySection("Children's Privacy") {
            PolicyText("The app does not knowingly collect data from children under 13.")
        }

        PolicySection("Security") {
            PolicyText("We take measures to protect your information.")
        }

        PolicySection("Changes") {
            PolicyText("This Privacy Policy may be updated. Please review it regularly.")
        }

        PolicySection("Contact Us") {
            PolicyText("For any privacy-related questions, reach out to:")
            ClickableEmail(email = "nutrinonovarage@gmail.com")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun PolicySection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(modifier = Modifier.height(4.dp))
        content()
    }
}

@Composable
fun PolicyText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun BulletPoint(text: String) {
    Row(modifier = Modifier.padding(vertical = 2.dp)) {
        Text(text = "•", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun ClickableEmail(email: String) {
    val context = LocalContext.current
    ClickableText(
        text = AnnotatedString(email),
        style = TextStyle(
            fontSize = 16.sp,
            color = Color.Blue,
            textDecoration = TextDecoration.Underline
        ),
        onClick = {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
            }
            context.startActivity(intent)
        }
    )
}
