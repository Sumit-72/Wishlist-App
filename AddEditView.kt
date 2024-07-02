package com.example.whishlistapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.whishlistapp.data.Wish
import kotlinx.coroutines.launch

@Composable
fun AddEditView(
    id: Long,
    viewModel: WishViewModel,
    navController: NavController
){

    val snackMessage = remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()
    val scaffoldState= rememberScaffoldState()
    if(id!=0L){
        val wish =viewModel.getWishbyid(id).collectAsState(initial = Wish(0L,"",""))
        viewModel.wishTitleState=wish.value.title
        viewModel.wishDescriptionState=wish.value.description
    }else{
        viewModel.wishTitleState=""
        viewModel.wishDescriptionState=""
    }


    Scaffold(
        scaffoldState= scaffoldState,
       topBar = { AppBarView(title =
       if(id!= 0L) stringResource(id = R.string.update_wish)
       else stringResource(id = R.string.add_wish)
       ){navController.navigateUp()}},


        ) {
            Column(modifier= Modifier
                .padding(it)
                .wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                
                WishTextField(label = "Title",
                    value = viewModel.wishTitleState,
                    onValueChange ={
                        viewModel.onWishTitleChange(it)
                    } )

                Spacer(modifier = Modifier.height(10.dp))

                WishTextField(label = "Description",
                    value = viewModel.wishDescriptionState,
                    onValueChange ={
                        viewModel.onWishDescriptionChange(it)
                    } )

                Spacer(modifier = Modifier.height(10.dp))
                
                Button(onClick = {
                    if(viewModel.wishTitleState.isNotEmpty() &&
                        viewModel.wishDescriptionState.isNotEmpty()){
                        if(id !=0L){
                            //TODO UpdateWish
                            viewModel.updateWish(
                                Wish(
                                    id=id,
                                    title = viewModel.wishTitleState.trim(),
                                    description = viewModel.wishDescriptionState.trim()
                                )
                            )
                        }
                        else{
                            //AddWish
                            viewModel.addWish(
                                Wish(
                                    title = viewModel.wishTitleState.trim(),
                                    description = viewModel.wishDescriptionState.trim()
                                )
                            )

                            snackMessage.value="Wish has been created"
                        }
                    }else{
                        ///Enter field for wish to be created
                        snackMessage.value="Enter field to create a wish"
                    }

                    scope.launch {
                        //scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                        navController.navigateUp()
                    }
                }) {
                    Text(
                        text =if(id!=0L) stringResource(id = R.string.update_wish)
                        else stringResource(id = R.string.add_wish),
                        style = TextStyle(
                            fontSize = 20.sp
                        )
                    )
                }
            }
    }
}

@Composable
fun WishTextField(
    label: String,
    value: String,
    onValueChange: (String)->Unit
){
    OutlinedTextField(value = value, onValueChange = onValueChange,
        label={ Text(text = label, color = Color.Black)},
        modifier =  Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            //Using predefined Color
            textColor = Color.Black,
            focusedBorderColor = Color.Black,
            // Using own defined color
            unfocusedBorderColor = colorResource(id = R.color.black),
            cursorColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black
        )
    )
}