package com.starmicronics.starxpandsdk

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.view.WindowManager
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true)
@Composable
fun ContentView(modifier: Modifier = Modifier) {
    val menuItems =
        listOf(
            MenuItem("Discovery", stringResource(id = R.string.discovery_title)),
            MenuItem("Printing", stringResource(id = R.string.printing_title)),
            MenuItem("Spooler", stringResource(id = R.string.printingSpooler_title)),
            MenuItem("TemplatePrint", stringResource(id = R.string.templatePrint_title)),
            MenuItem("Monitor", stringResource(id = R.string.monitor_title)),
            MenuItem("Status", stringResource(id = R.string.status_title)),
            MenuItem("FirmwareUpdate", stringResource(id = R.string.firmwareUpdate_title)),
        )
    // Create a NavController to manage navigation between screens
    val navController = rememberNavController()

    var isProcessing by rememberSaveable { mutableStateOf(false) }

    val discoveryViewModel: DiscoveryViewModel = viewModel()
    val printingViewModel: PrintingViewModel = viewModel()
    val spoolerViewModel: SpoolerViewModel = viewModel()
    val templatePrintViewModel: TemplatePrintViewModel = viewModel()
    val monitorViewModel: MonitorViewModel = viewModel()
    val statusViewModel: StatusViewModel = viewModel()
    val firmwareUpdateViewModel: FirmwareUpdateViewModel = viewModel()

    val discoveryProcessing by discoveryViewModel.isProcessing.collectAsState()
    val printingProcessing by printingViewModel.isProcessing.collectAsState()
    val spoolerProcessing by spoolerViewModel.isProcessing.collectAsState()
    val templateProcessing by templatePrintViewModel.isProcessing.collectAsState()
    val monitorProcessing by monitorViewModel.isProcessing.collectAsState()
    val statusProcessing by statusViewModel.isProcessing.collectAsState()
    val firmwareProcessing by firmwareUpdateViewModel.isProcessing.collectAsState()

    LaunchedEffect(
        discoveryProcessing,
        printingProcessing,
        spoolerProcessing,
        templateProcessing,
        monitorProcessing,
        statusProcessing,
        firmwareProcessing
    ) {
        isProcessing = discoveryProcessing ||
                printingProcessing ||
                spoolerProcessing ||
                templateProcessing ||
                monitorProcessing ||
                statusProcessing ||
                firmwareProcessing
    }

    // Set up the navigation host with the start destination
    NavHost(
        modifier = modifier,
        navController =  navController,
        startDestination = "menu"
    ) {
        composable("menu") { MenuScreen(stringResource(id = R.string.app_name), navController, menuItems) }
        composable("Discovery") {
            createScreen(
                stringResource(id = R.string.discovery_title),
                onBackClick = { if (!isProcessing) navController.popBackStack() },
                subScreen = true,
                content = { paddingValues ->
                    DiscoveryView(
                        paddingValues,
                        discoveryViewModel,
                        )
                },
            )()
        }
        composable("Printing") {
            createScreen(
                stringResource(id = R.string.printing_title),
                onBackClick = { if (!isProcessing) navController.popBackStack() },
                subScreen = true,
                content = { paddingValues ->
                    PrintingView(
                        paddingValues,
                        printingViewModel,
                        )
                },
            )()
        }
        composable("Spooler") {
            createScreen(
                stringResource(id = R.string.printingSpooler_title),
                onBackClick = { if (!isProcessing) navController.popBackStack() },
                subScreen = true,
                content = { paddingValues ->
                    SpoolerView(
                        paddingValues,
                        spoolerViewModel,
                        )
                },
            )()
        }
        composable("TemplatePrint") {
            createScreen(
                stringResource(id = R.string.templatePrint_title),
                onBackClick = { if (!isProcessing) navController.popBackStack() },
                subScreen = true,
                content = { paddingValues ->
                    TemplatePrintView(
                        paddingValues,
                        templatePrintViewModel,
                        )
                },
            )()
        }
        composable("Monitor") {
            createScreen(
                stringResource(id = R.string.monitor_title),
                onBackClick = { if (!isProcessing) navController.popBackStack() },
                subScreen = true,
                content = { paddingValues ->
                    MonitorView(
                        paddingValues,
                        monitorViewModel,
                        )
                },
            )()
        }
        composable("Status") {
            createScreen(
                stringResource(id = R.string.status_title),
                onBackClick = { if (!isProcessing) navController.popBackStack() },
                subScreen = true,
                content = { paddingValues ->
                    StatusView(
                        paddingValues,
                        statusViewModel,
                        )
                },
            )()
        }
        composable("FirmwareUpdate") {
            createScreen(
                stringResource(id = R.string.firmwareUpdate_title),
                onBackClick = { if (!isProcessing) navController.popBackStack() },
                subScreen = true,
                content = { paddingValues ->
                    FirmwareUpdateView(
                        paddingValues,
                        firmwareUpdateViewModel,
                        )
                },
            )()
        }
    }
    if (isProcessing) {
        val context = LocalContext.current
        val activity = context as? Activity

        DisposableEffect(Unit) {
            activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            onDispose {
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }
        }
        BackHandler(enabled = true) {
            // Handle back press when in lock screen mode
            // Do nothing to prevent navigation
        }
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .pointerInput(Unit) {},
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(
                modifier =
                    Modifier
                        .width(48.dp)
                        .height(48.dp),
            )
        }
    }
}

data class MenuItem(
    val route: String,
    val label: String,
)

@Composable
fun MenuScreen(
    title: String,
    navController: NavController,
    menuItems: List<MenuItem>,
) {
    createScreen(
        title,
        onBackClick = { },
        subScreen = false,
        content = { paddingValues ->
            Column(
                modifier =
                    Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val context = LocalContext.current
                val permissionsToRequest =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CINNAMON_BUN) {
                        arrayOf(
                            Manifest.permission.ACCESS_LOCAL_NETWORK, // for LAN
                            Manifest.permission.BLUETOOTH_CONNECT, // for Bluetooth(Classic/LE)
                            Manifest.permission.BLUETOOTH_SCAN, // for Bluetooth LE
                        )
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        arrayOf(
                            Manifest.permission.BLUETOOTH_CONNECT, // for Bluetooth(Classic/LE)
                            Manifest.permission.BLUETOOTH_SCAN, // for Bluetooth LE
                        )
                    } else {
                        arrayOf(
                            Manifest.permission.BLUETOOTH, // for Bluetooth(Classic/LE)
                            Manifest.permission.BLUETOOTH_ADMIN, // for Bluetooth LE
                            Manifest.permission.ACCESS_FINE_LOCATION, // for Bluetooth LE
                        )
                    }
                val permissionState = remember { mutableStateOf(false) }

                // https://developer.android.com/privacy-and-security/local-network-permission
                Text(stringResource(id = R.string.lan_printer_notice) ,
                    modifier = Modifier.padding(8.dp),
                    color = Color.Red,
                )
                // https://developer.android.com/about/versions/12/features/bluetooth-permissions
                Text(stringResource(id = R.string.bluetooth_classic_printer_notice) ,
                    modifier = Modifier.padding(8.dp),
                    color = Color.Red,
                )
                Text(stringResource(id = R.string.bluetooth_low_energy_printer_notice) ,
                    modifier = Modifier.padding(8.dp),
                    color = Color.Red,
                )
                PermissionScreen(context, permissionsToRequest, permissionState)

                menuItems.forEach { item ->
                    Button(
                        onClick = { navController.navigate(item.route) },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(item.label)
                    }
                }
            }
        },
    )()
}

fun createScreen(
    title: String,
    subScreen: Boolean,
    onBackClick: () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
): @Composable () -> Unit =
    {
        Scaffold(
            topBar = createTopAppBar(title, subScreen, onBackClick),
        ) { padding ->
            Box(
                modifier = Modifier
            ) {
                content(padding)
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
private fun createTopAppBar(
    title: String,
    subScreen: Boolean,
    onBackClick: () -> Unit,
): @Composable () -> Unit =
    {
        CenterAlignedTopAppBar(
            title = { Text(title) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    if (subScreen) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            },
        )
    }

@Composable
fun PermissionScreen(
    context: Context,
    permissionsToRequest: Array<String>,
    permissionState: MutableState<Boolean>,
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.all { it.value }
        permissionState.value = allGranted
    }

    LaunchedEffect(Unit) {
        val allGranted =
            permissionsToRequest.all {
                checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }
        permissionState.value = allGranted
    }

    if (!permissionState.value) {
        Button(onClick = {
            launcher.launch(permissionsToRequest)
        }) {
            Text(stringResource(id = R.string.request_permissions))
        }
    } else {
        Text(stringResource(id = R.string.permissions_granted))
    }
}
