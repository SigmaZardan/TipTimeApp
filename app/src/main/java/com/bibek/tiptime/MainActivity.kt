package com.bibek.tiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bibek.tiptime.ui.theme.TipTimeTheme
import java.text.NumberFormat
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipTimeTheme {
                // A surface container using the 'background' color from the theme
                Surface(

                ) {
                            TipTimeScreen()
                }
            }
        }
    }
}

@Preview
@Composable
fun TipTimeScreen() {
    var amountInput by remember{ mutableStateOf("") }
    var tipInput by remember{ mutableStateOf("")}
    var roundUp by remember{ mutableStateOf(false)}

    val focusManager = LocalFocusManager.current




    val amount = amountInput.toDoubleOrNull()?: 0.0
    val tipPercentAmount = tipInput.toDoubleOrNull() ?: 0.0


    val tip = calculateTip(amount , tipPercentAmount , roundUp )





    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = stringResource(id = R.string.calculate_tip),
            fontSize = 25.sp ,
            modifier = Modifier.align(Alignment.CenterHorizontally)

        )

        Spacer(Modifier.height(16.dp))
        // bill amount text field
        EditNumberField(
            value = amountInput ,
            onValueChange =  { amountInput = it},
            id = R.string.bill_amount,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),


        )

            // tip percentage text field
        EditNumberField(
            value = tipInput ,
            onValueChange =  { tipInput = it},
            id = R.string.how_was_the_service,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus(true)
                }
            )
        )

        RoundTheTipRow(
            id  = R.string.round_the_tip,
            roundUp = roundUp,
            onRoundUpChanged = {roundUp = it}
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.tip_amount,tip ),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )



    }
}

    @Composable
    fun EditNumberField(
        value : String ,
        onValueChange : (String) -> Unit,
        id: Int,
        modifier : Modifier = Modifier,
        keyboardOptions : KeyboardOptions,
        keyboardActions : KeyboardActions


    ) {


        TextField(
            value = value,
            onValueChange =onValueChange,
            label = {Text(stringResource(id))},
            modifier = Modifier.fillMaxWidth(),
            singleLine = true ,
            keyboardOptions = keyboardOptions,
            keyboardActions  = keyboardActions

        )





    }

@Composable
private fun RoundTheTipRow(
    modifier : Modifier  = Modifier ,
    id : Int,
    roundUp : Boolean,
    onRoundUpChanged : (Boolean) -> Unit

) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {

        Text(
            text = stringResource(id =id)
        )

        Switch(
            checked = roundUp,
            onCheckedChange = onRoundUpChanged,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = Color.DarkGray
            )


        )



    }
}

@VisibleForTesting
internal fun calculateTip(
    amount : Double ,
    tipPercent : Double ,
    roundUp : Boolean
) :String {
    var tip = (tipPercent / 100 ) * amount
    if(roundUp) tip =  tip.roundToInt().toDouble()
    return  NumberFormat.getCurrencyInstance().format(tip)


}






@Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        TipTimeTheme {
            TipTimeScreen()
        }
    }
