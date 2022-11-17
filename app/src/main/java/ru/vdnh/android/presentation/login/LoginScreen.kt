package ru.vdnh.android.presentation.login

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import ru.vdnh.android.R
import kotlinx.coroutines.flow.collectLatest
import ru.vdnh.android.presentation.DevicePreviews
import ru.vdnh.android.presentation.util.Screen
import ru.vdnh.android.ui.theme.AppTheme

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController,
) {

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val email: TextFieldState by viewModel.email
    val password by viewModel.password

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        val context = LocalContext.current as Activity
        context.window.statusBarColor = AppTheme.colors.background.toArgb()
        context.window.navigationBarColor = AppTheme.colors.background.toArgb()

        LoginView(
            emailText = email.text,
            emailHint = email.hint,
            passwordText = password.text,
            passwordHint = password.hint,
            onLoginChange = { password ->
                viewModel.onEvent(LoginEvent.EnteredPassword(password))
            },
            onPasswordChange = { email ->
                viewModel.onEvent(LoginEvent.EnteredEmail(email))
            },
            onLoginClick = {
                viewModel.onEvent(LoginEvent.PerformLogin {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                    }
                })
            }
        )
    }
}

@Composable
fun LoginView(
    emailText: String,
    emailHint: String,
    passwordText: String,
    passwordHint: String,
    onLoginChange: (login: String) -> Unit,
    onPasswordChange: (password: String) -> Unit,
    onLoginClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background),
//        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.padding(30.dp, 80.dp, 30.dp, 20.dp),
            text = stringResource(R.string.welcome_please_register),
            textAlign = TextAlign.Center,
            style = AppTheme.typography.caption,
            color = AppTheme.colors.textSecondary,
        )

        Text(
            modifier = Modifier.padding(30.dp, 0.dp),
            text = stringResource(R.string.welcome_create_account),
            textAlign = TextAlign.Center,
            style = AppTheme.typography.headline5,
            color = AppTheme.colors.textPrimary,
        )

        Button(
            modifier = Modifier.width(200.dp),
            onClick = {
                onLoginClick()
            }
        ) {
            Text(
                text = stringResource(R.string.login_phone),
                fontSize = 16.sp,
            )
        }
        Button(
            modifier = Modifier.width(200.dp),
            onClick = {
                onLoginClick()
            }
        ) {
            Text(
                text = stringResource(R.string.login_sber_id),
                fontSize = 16.sp,
            )
        }
        Button(
            modifier = Modifier.width(200.dp),
            onClick = {
                onLoginClick()
            }
        ) {
            Text(
                text = stringResource(R.string.login_vk),
                fontSize = 16.sp,
            )
        }
        Button(
            modifier = Modifier.width(200.dp),
            onClick = {
                onLoginClick()
            }
        ) {
            Text(
                text = stringResource(R.string.login_yandex),
                fontSize = 16.sp,
            )
        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Column(
//            horizontalAlignment = Alignment.End
//
//        ) {
//            TextField(
//                value = emailText,
//                onValueChange = { onLoginChange(it) },
//                modifier = Modifier.width(280.dp),
//                placeholder = {
//                    Text(
//                        text = emailHint,
//                        modifier = Modifier.alpha(0.5f)
//                    )
//                },
//                shape = RoundedCornerShape(9.dp),
//                colors = TextFieldDefaults.textFieldColors(
//                    cursorColor = Color.White,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent,
//                ),
//                keyboardOptions = KeyboardOptions.Default.copy(
//                    imeAction = ImeAction.Next
//                ),
//                singleLine = true
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            TextField(
//                value = passwordText,
//                onValueChange = { },
//                modifier = Modifier.width(280.dp),
//                placeholder = {
//                    Text(
//                        text = passwordHint,
//                        Modifier.alpha(0.5f)
//                    )
//                },
//                visualTransformation = PasswordVisualTransformation(),
//                shape = RoundedCornerShape(9.dp),
//                colors = TextFieldDefaults.textFieldColors(
//                    cursorColor = Color.White,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent,
//                ),
//                keyboardOptions = KeyboardOptions.Default.copy(
//                    imeAction = ImeAction.Done
//                ),
//                singleLine = true
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = stringResource(R.string.forgot_password),
//                modifier = Modifier
//                    .alpha(0.5f)
//                    .clickable {
////                    Toast
////                        .makeText(context, R.string.coming_soon, Toast.LENGTH_SHORT)
////                        .show()
//                    },
//                fontSize = 14.sp,
//            )
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(
//            modifier = Modifier.width(200.dp),
//            onClick = {
//                onLoginClick()
//            }
//        ) {
//            Text(
//                text = stringResource(R.string.login),
//                fontSize = 16.sp,
//            )
//        }
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(24.dp),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Divider(
//                modifier = Modifier
//                    .weight(0.1f)
//                    .width(1.dp)
//            )
//            Spacer(modifier = Modifier.width(16.dp))
//
//            Text(
//                text = stringResource(R.string.or_sign_in_with),
//                modifier = Modifier
//                    .alpha(0.5f),
//                fontSize = 16.sp,
//            )
//            Spacer(modifier = Modifier.width(16.dp))
//
//            Divider(
//                modifier = Modifier
//                    .weight(0.1f)
//                    .width(1.dp)
//            )
//        }
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth(), horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.goog_icon),
//                contentDescription = stringResource(R.string.login_with_google),
//                modifier = Modifier
//                    .fillMaxSize(0.1f)
//                    .clickable {
////                    Toast
////                        .makeText(context, R.string.coming_soon, Toast.LENGTH_SHORT)
////                        .show()
//                    }
//            )
//            Spacer(modifier = Modifier.width(16.dp))
//            Image(
//                painter = painterResource(id = R.drawable.ic_vdnh_logo),
//                contentDescription = stringResource(R.string.login_with_facebook),
//                modifier = Modifier
//                    .fillMaxSize(0.1f)
//                    .clickable {
////                    Toast
////                        .makeText(context, R.string.coming_soon, Toast.LENGTH_SHORT)
////                        .show()
//                    }
//            )
//        }
//        Spacer(modifier = Modifier.height(16.dp))

    }
}

@Composable
fun FilledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    small: Boolean = false,
    colors: ButtonColors = NiaButtonDefaults.filledButtonColors(),
    contentPadding: PaddingValues = NiaButtonDefaults.buttonContentPadding(small = small),
    content: @Composable RowScope.() -> Unit
) {
    androidx.compose.material3.Button(
        onClick = onClick,
        modifier = if (small) {
            modifier.heightIn(min = NiaButtonDefaults.SmallButtonHeight)
        } else {
            modifier
        },
        enabled = enabled,
        colors = colors,
        contentPadding = contentPadding,
        content = {
            androidx.compose.material3.ProvideTextStyle(value = MaterialTheme.typography.labelSmall) {
                content()
            }
        }
    )
}

@DevicePreviews
@Composable
fun ForYouScreenLoading() {
    AppTheme {
        LoginView(
            emailText = "",
            emailHint = "",
            passwordText = "111",
            passwordHint = "asd",
            onLoginChange = { login -> },
            onPasswordChange = { password -> },
            onLoginClick = {},
        )
    }
}


