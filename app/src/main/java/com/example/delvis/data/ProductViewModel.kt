package com.example.delvis.data


import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.delvis.models.ProductModel
import com.example.delvis.navigation.ROUTE_VIEW_PRODUCTS
import com.example.delvis.network.ImgurService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
class ProductViewModel : ViewModel() {
    private val database = FirebaseDatabase.getInstance().reference.child("Products")
    private fun getImgurService(): ImgurService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.imgur.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ImgurService::class.java)
    }
    private fun getFileFromUri(context: Context, uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val file = File.createTempFile("temp_image", ".jpg", context.cacheDir)
            file.outputStream().use { output ->
                inputStream?.copyTo(output)
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun uploadProductWithImage(
        uri: Uri,
        context: Context,
        productname: String,
        productquantity: String,
        productprice: String,
        desc: String,
        navController: NavController
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val file = getFileFromUri(context, uri)
                if (file == null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Failed to process image", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }
                val reqFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("image", file.name, reqFile)
                val response = getImgurService().uploadImage(
                    body,
                    "Client-ID 4a9cd0ac9fd5d4f"
                )
                if (response.isSuccessful) {
                    val imageUrl = response.body()?.data?.link ?: ""
                    val productId = database.push().key ?: ""
                    val product = ProductModel(
                        productname, productquantity, productprice,desc, imageUrl, productId
                    )
                    database.child(productId).setValue(product)
                        .addOnSuccessListener {
                            viewModelScope.launch {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Product saved successfully", Toast.LENGTH_SHORT).show()
                                    navController.navigate(ROUTE_VIEW_PRODUCTS)
                                }
                            }
                        }.addOnFailureListener {
                            viewModelScope.launch {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Failed to save product", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Upload error", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Exception: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    fun viewProducts(
        product: MutableState<ProductModel>,
        products: SnapshotStateList<ProductModel>,
        context: Context
    ): SnapshotStateList<ProductModel> {
        val ref = FirebaseDatabase.getInstance().getReference("Products")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                products.clear()
                for (snap in snapshot.children) {
                    val value = snap.getValue(ProductModel::class.java)
                    value?.let {
                        products.add(it)
                    }
                }
                if (products.isNotEmpty()) {
                    product.value = products.first()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to fetch products: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
        return products
    }
    fun updateProduct(context: Context,navController: NavController,
                      productname: String,productquantity: String,
                      productprice: String,desc: String,productId: String){
        val databaseReference = FirebaseDatabase.getInstance()
            .getReference("Products/$productId")
        val updatedClient = ProductModel(productname, productquantity,
            productprice, desc,"",productId)
        databaseReference.setValue(updatedClient)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(context,"Product Updated Successfully",Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_VIEW_PRODUCTS)
                }else{
                    Toast.makeText(context,"Product update failed",Toast.LENGTH_LONG).show()
                }
            }
    }
    fun deleteProduct(context: Context,productId: String,
                      navController: NavController){
        AlertDialog.Builder(context)
            .setTitle("Delete Product")
            .setMessage("Are you sure you want to delete this product?")
            .setPositiveButton("Yes"){ _, _ ->
                val databaseReference = FirebaseDatabase.getInstance()
                    .getReference("Products/$productId")
                databaseReference.removeValue().addOnCompleteListener {
                        task ->
                    if (task.isSuccessful){
                        Toast.makeText(context,"Product deleted Successfully",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(context,"Product not deleted",Toast.LENGTH_LONG).show()
                    }
                }
            }
            .setNegativeButton("No"){ dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}