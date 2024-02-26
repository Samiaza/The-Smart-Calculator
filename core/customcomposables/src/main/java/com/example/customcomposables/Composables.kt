import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logger.Logger

@Composable
fun CustomTextField(
    label: String,
    value: String,
    description: String,
    onValueChange: (String) -> Unit,
    validator: (String) -> Boolean,
    textSize: TextUnit = 18.sp,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var isError by remember { mutableStateOf(true) }
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            isError = !validator(it) },
        label = { Text(if (isError) "$label*" else label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(2.dp, if (isError) Color.Red else Color.LightGray, RoundedCornerShape(5.dp))
            .padding(1.dp),
        textStyle = TextStyle(fontSize = textSize, textAlign = TextAlign.Center),
        trailingIcon = { Icon(Icons.TwoTone.Info, contentDescription = description) },
        singleLine = true,
        placeholder = { Text(description, color = Color(0xAAAAAAAA))}
    )
}

@Composable
fun CustomBigIntegerField(
    label: String,
    value: String,
    description: String,
    onValueChange: (String) -> Unit,
    validator: (String) -> Boolean = { str -> str.toBigIntegerOrNull() != null },
    keyboardType: KeyboardType = KeyboardType.Number
) {
    CustomTextField(
        label = label,
        value = value,
        description = description,
        onValueChange = onValueChange,
        validator = validator,
        keyboardType = keyboardType
    )
}

@Composable
fun CustomIntField(
    label: String,
    value: String,
    description: String,
    onValueChange: (String) -> Unit,
    validator: (String) -> Boolean = { str -> str.toIntOrNull() != null },
    keyboardType: KeyboardType = KeyboardType.Number
) {
    CustomTextField(
        label = label,
        value = value,
        description = description,
        onValueChange = onValueChange,
        validator = validator,
        keyboardType = keyboardType
    )
}

@Composable
fun CustomDoubleField(
    label: String,
    value: String,
    description: String,
    onValueChange: (String) -> Unit,
    validator: (String) -> Boolean = { str -> str.toDoubleOrNull() != null },
    keyboardType: KeyboardType = KeyboardType.Number
) {
    CustomTextField(
        label = label,
        value = value,
        description = description,
        onValueChange = onValueChange,
        validator = validator,
        keyboardType = keyboardType
    )
}

@Composable
fun ModuleNameText(name: String, color: Color = Color.Magenta) {
    Text(
        "Module: $name",
        Modifier
            .padding(10.dp)
            .border(2.dp, Color.Black)
            .background(color)
            .padding(10.dp),
        textAlign = TextAlign.Center,
        fontSize = 18.sp
    )
}

@Composable
fun ModuleLaunchButton(name: String, activityClass: Class<*>) {
    val context = LocalContext.current
    val startActivityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {}
    )
    Button(
        onClick = {
            Logger.i("${context::class.simpleName}", "Button $name clicked")
            startActivityLauncher.launch(Intent(context, activityClass))
            Logger.i("${context::class.simpleName}", "Activity ${activityClass.simpleName} launched")
                  },
        Modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp)
    ) {
        Text(name)
    }
}

@Composable
fun CustomLabeledResult(label: String, result: String) {
    TextField(
        value = result,
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(2.dp, Color.LightGray, RoundedCornerShape(5.dp))
            .padding(1.dp),
        readOnly = true,
        label = { Text(text = label) },
        maxLines = 5
    )
}
