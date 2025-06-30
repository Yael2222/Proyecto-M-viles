package com.Proyecto.coffeepalace.ui.Screens.Customer.ProductDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil3.compose.rememberAsyncImagePainter
import com.Proyecto.coffeepalace.Data.AppDatabase
import com.Proyecto.coffeepalace.Data.Repository.ComentarioProductoRepository
import com.Proyecto.coffeepalace.ui.components.AddCommentBottomSheetProduct
import com.Proyecto.coffeepalace.ui.components.BottomBar
import com.Proyecto.coffeepalace.ui.components.CommentCard
import com.Proyecto.coffeepalace.ui.navigations.NavigationRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavController,
    idProducto: Int,
    idUsuario: Int
) {
    val context = LocalContext.current
    val dao = remember { AppDatabase.getDatabase(context).comentarioProductoDao() }
    val repository = remember { ComentarioProductoRepository(dao) }
    val viewModel = remember { ProductDetailViewModel(repository) }

    val comments by viewModel.comments.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val scope = rememberCoroutineScope()

    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.cargarComentarios(idProducto)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Product Detail",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        bottomBar = {
            BottomBar(
                currentRoute = currentRoute ?: "",
                onHomeClick = { navController.navigate(NavigationRoutes.HOME) },
                onCategoryClick = { navController.navigate(NavigationRoutes.CATEGORY) },
                onSearchClick = { navController.navigate(NavigationRoutes.SEARCH) },
                onProfileClick = { navController.navigate(NavigationRoutes.PROFILE) },
                onCartClick = { navController.navigate(NavigationRoutes.CART) }
            )
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                Image(
                    painter = rememberAsyncImagePainter("https://static01.nyt.com/images/2021/10/15/dining/aw-classic-deviled-eggs/aw-classic-deviled-eggs-mediumSquareAt3X.jpg"),
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )

                Column(Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        "Deviled Eggs",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    )

                    Spacer(Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        repeat(4) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Text(" 56,890", fontSize = 12.sp, modifier = Modifier.padding(start = 6.dp))
                    }

                    Spacer(Modifier.height(6.dp))

                    Text(
                        "$ 4.99",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    )

                    Spacer(Modifier.height(12.dp))

                    Text("Product Details", fontWeight = FontWeight.Bold, fontSize = 16.sp)

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = "Perhaps the most iconic sneaker of all-time...",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = if (expanded) Int.MAX_VALUE else 3,
                        overflow = if (expanded) TextOverflow.Visible else TextOverflow.Ellipsis
                    )

                    Text(
                        text = if (expanded) "Show less" else "...More",
                        color = Color(0xFF8A4F2E),
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .clickable { expanded = !expanded }
                    )

                    Spacer(Modifier.height(24.dp))

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { navController.navigate(NavigationRoutes.CART) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A4F2E)),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .height(48.dp)
                                .weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Go to Cart icon",
                                tint = Color.White
                            )
                            Spacer(Modifier.width(6.dp))
                            Text("Go to Cart", color = Color.White)
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    Text("Comments", fontWeight = FontWeight.Bold, fontSize = 16.sp)

                    Text(
                        text = "Add a comment",
                        color = Color(0xFF8A4F2E),
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .clickable {
                                showBottomSheet = true
                            }
                    )

                    LazyRow(modifier = Modifier.padding(bottom = 32.dp)) {
                        items(comments.size) { index ->
                            val comment = comments[index]
                            CommentCard(
                                text = comment.text,
                                stars = comment.rating
                            )
                        }
                    }
                }
            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = bottomSheetState,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    containerColor = Color.White
                ) {
                    AddCommentBottomSheetProduct(
                        viewModel = viewModel,
                        onDismiss = { showBottomSheet = false },
                        idProducto = idProducto,
                        idUsuario = idUsuario
                    )
                }
            }
        }
    }
}











//Este es el anterior intentando hacer el
/*import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.Proyecto.coffeepalace.ui.components.BottomBar
import com.Proyecto.coffeepalace.ui.components.CommentCard
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.room.Room
import com.Proyecto.coffeepalace.Data.Repository.CommentRepository
import kotlin.jvm.java
import androidx.compose.foundation.lazy.items
import com.Proyecto.coffeepalace.Data.Daos.comentario.ComentarioProductoDaoImpl
import com.Proyecto.coffeepalace.Data.Model.ComentarioProducto
import com.Proyecto.coffeepalace.Data.Repository.ComentarioRepository
import com.Proyecto.coffeepalace.ui.components.AddCommentBottomSheet


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    //viewModel: ProductViewModel = viewModel(),
    navController: NavController,
    idProducto: Int,
    idUsuario: Int
) {
    /*val context = LocalContext.current
    val db = remember {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "coffee_palace_db"
        ).build()
    }
    val repository = remember { CommentRepository(db.commentDao()) }
    val viewModel = remember { ProductViewModel(repository) }
     */

    val dao = remember { ComentarioProductoDaoImpl() }
    val repository = remember { ComentarioRepository(dao) }
    val viewModel = remember { ProductViewModel(repository) }

    val comments by viewModel.comments.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.cargarComentarios(idProducto)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Product Detail",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        bottomBar = {
            BottomBar(
                currentRoute = currentRoute ?: "",
                onHomeClick = { navController.navigate("home") },
                onCategoryClick = { navController.navigate("category") },
                onSearchClick = { navController.navigate("search") },
                onProfileClick = { navController.navigate("profile") },
                onCartClick = { navController.navigate("cart") }
            )
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                Image(
                    painter = rememberAsyncImagePainter("https://static01.nyt.com/images/2021/10/15/dining/aw-classic-deviled-eggs/aw-classic-deviled-eggs-mediumSquareAt3X.jpg"),
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )

                Column(Modifier.padding(horizontal = 20.dp)) {
                    Text("Deviled Eggs", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        repeat(4) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Text(" 56,890", fontSize = 12.sp, modifier = Modifier.padding(start = 6.dp))
                    }

                    Spacer(Modifier.height(6.dp))
                    Text("$ 4.99", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(12.dp))
                    Text("Product Details", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Descripción del producto...",
                        maxLines = if (expanded) Int.MAX_VALUE else 3,
                        overflow = if (expanded) TextOverflow.Visible else TextOverflow.Ellipsis
                    )
                    Text(
                        text = if (expanded) "Show less" else "...More",
                        color = Color(0xFF8A4F2E),
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .clickable { expanded = !expanded }
                    )

                    Spacer(Modifier.height(24.dp))
                    Row(
                        Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A4F2E)),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.height(48.dp).weight(1f)
                        ) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color.White)
                            Spacer(Modifier.width(6.dp))
                            Text("Go to Cart", color = Color.White)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                    }

                    Spacer(Modifier.height(24.dp))
                    Text("Comments", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(
                        text = "Add a comment",
                        color = Color(0xFF8A4F2E),
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.padding(vertical = 8.dp).clickable { showBottomSheet = true }
                    )

                    LazyRow(modifier = Modifier.padding(bottom = 32.dp)) {
                        items(comments) { comment ->
                            CommentCard(
                                text = comment.texto,
                                stars = comment.calificacion
                            )
                        }
                    }
                }
            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = bottomSheetState,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    containerColor = Color.White
                ) {
                    AddCommentBottomSheet(
                        viewModel = viewModel,
                        idUsuario = idUsuario,
                        idProducto = idProducto,
                        onDismiss = { showBottomSheet = false }
                    )
                }
            }
        }
    }
}
 */


/*Scaffold(
    topBar = {
        TopAppBar(
            title = {
                Text(
                    "Product Detail",
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp)
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color.Black
            )
        )
    },
    bottomBar = {
        BottomBar(
            currentRoute = currentRoute ?: "",
            onHomeClick = { navController.navigate("home") },
            onCategoryClick = { navController.navigate("category") },
            onSearchClick = { navController.navigate("search") },
            onProfileClick = { navController.navigate("profile") },
            onCartClick = { navController.navigate("cart") }
        )
    }
) { padding ->
    Box(Modifier.padding(padding)) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            Image(
                painter = rememberAsyncImagePainter("https://static01.nyt.com/images/2021/10/15/dining/aw-classic-deviled-eggs/aw-classic-deviled-eggs-mediumSquareAt3X.jpg"),
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop
            )

            Column(Modifier.padding(horizontal = 20.dp)) {
                Text(
                    "Deviled Eggs",
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp, fontWeight = FontWeight.Bold)
                )

                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(4) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Text(" 56,890", fontSize = 12.sp, modifier = Modifier.padding(start = 6.dp))
                }

                Spacer(Modifier.height(6.dp))
                Text(
                    "$ 4.99",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                )

                Spacer(Modifier.height(12.dp))
                Text("Product Details", fontWeight = FontWeight.Bold, fontSize = 16.sp)

                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Perhaps the most iconic sneaker of all-time, this original \"Chicago\" colorway " +
                            "is the cornerstone to any sneaker collection. Made famous in 1985 by Michael Jordan, the shoe has stood the test of time, " +
                            "becoming the most famous colorway of the Air Jordan 1. This 2015 release saw the return of the iconic high-cut silhouette, " +
                            "Nike Air branding on the tongue, and the beloved Varsity Red, black, and white color combo.",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = if (expanded) Int.MAX_VALUE else 3,
                    overflow = if (expanded) TextOverflow.Visible else TextOverflow.Ellipsis
                )

                Text(
                    text = if (expanded) "Show less" else "...More",
                    color = Color(0xFF8A4F2E),
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .clickable { expanded = !expanded }
                )

                Spacer(Modifier.height(24.dp))

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { /* Acción carrito */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A4F2E)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .height(48.dp)
                            .weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Go to Cart icon",
                            tint = Color.White
                        )
                        Spacer(Modifier.width(6.dp))
                        Text("Go to Cart", color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    /*Button(
                        onClick = { /* Acción comprar */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A4F2E)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .height(48.dp)
                            .weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Buy icon",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Buy", color = Color.White)
                    }*/
                }

                Spacer(Modifier.height(24.dp))
                Text("Comments", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(
                    text = "Add a comment",
                    color = Color(0xFF8A4F2E),
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable { showBottomSheet = true }
                )

                LazyRow(modifier = Modifier.padding(bottom = 32.dp)) {
                    items(comments) { comment ->
                        CommentCard(
                            text = comment.text,
                            stars = comment.rating
                        )
                    }
                }
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = bottomSheetState,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                containerColor = Color.White
            ) {
                AddCommentBottomSheet(
                    viewModel = viewModel,
                    onDismiss = { showBottomSheet = false }
                )
            }
        }
    }
}*/

