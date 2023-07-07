package com.tpp.theperiodpurse.ui.legal

import android.annotation.SuppressLint
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.ui.component.TopNavBar
import com.tpp.theperiodpurse.ui.education.EducationBackground

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TermsScreen(navController: NavHostController) {
    val interactionSource = remember { MutableInteractionSource() }
    val screenName = stringResource(R.string.terms_and_conditions)

    EducationBackground()

    Scaffold(
        backgroundColor = Color.Transparent,
        topBar = { TopNavBar(screenName, navController, interactionSource) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 12.dp, horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                Title(str = screenName)

                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.Gray,
                    thickness = 2.dp,
                )

                Body(stringResource(R.string.terms_and_conditions))

                Header(stringResource(R.string.intellectual_property))
                Body(stringResource(R.string.intellectual_property_details))

                Header(stringResource(R.string.accounts))
                Body(stringResource(R.string.account_details))

                Header(stringResource(R.string.limitation_of_liability))
                Body(stringResource(R.string.limitation_of_liability_details))

                Header(stringResource(R.string.indemnity))
                Body(stringResource(R.string.indemnity_details))

                Header(stringResource(R.string.applicable_law))
                Body(stringResource(R.string.applicable_law_details))

                Header(stringResource(R.string.severability))
                Body(stringResource(R.string.severability_details))

                Header(stringResource(R.string.terms_and_conditions))
                Body(stringResource(R.string.terms_conditions_changes_details))

                Header(stringResource(R.string.contact_details_string))
                Body(stringResource(R.string.contact_details))
                Body(stringResource(R.string.terms_conditions_effective_date))
                Spacer(modifier = Modifier.size(36.dp))
            }
        },
    )
}

@Preview
@Composable
fun PreviewTermsConditionsScreen() {
    TermsScreen(rememberNavController())
}
