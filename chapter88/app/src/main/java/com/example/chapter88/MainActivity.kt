package com.example.chapter88

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.chapter88.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private val imageLoadLauncher = registerForActivityResult(ActivityResultContracts.GetMultipleContents()){uriList ->
        updateImages(uriList)
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageAdapter: ImageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loadImageButton.setOnClickListener {
            checkPermission()
        }

        binding.navigateFrameActivityButton.setOnClickListener {
            navigateToFrameActivity()
        }
        initRecyclerView()
    }

    private fun navigateToFrameActivity(){
        val images = imageAdapter.currentList.filterIsInstance<ImageItems.Image>().map{it.uri.toString()}.toTypedArray()
        val intent = Intent(this, FrameActivity::class.java)
            .putExtra("images", images)
        startActivity()
    }
    private fun initRecyclerView(){
        imageAdapter = ImageAdapter(object : ImageAdapter.ItemClickListener {
            override fun onLoadMoreClick() {
                checkPermission()
            }
        })

        binding.imageRecyclerView.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(context, 2)
            
        }
    }


    private fun checkPermission() {
        when{
            ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED -> {
                loadImage()
            }

//            ActivityCompat.shouldShowRequestPermissionRationale
//                → 사용자가 권한 요청을 명시적으로 거부한 경우 true를 반환한다.
//                → 사용자가 권한 요청을 처음 보거나, 다시 묻지 않음 선택한 경우, 권한을 허용한 경우 false를 반환한다.
            shouldShowRequestPermissionRationale(android.Manifest.permission.READ_MEDIA_IMAGES) -> {
                showPermissionInfoDialog()
            }

            else -> {
                requestReadExternalStorage()
            }
        }

    }

    private fun showPermissionInfoDialog(){
        AlertDialog.Builder(this).apply {
            setMessage("이미지를 가져오기 위해서,  외부저장소 읽기 권한이 필요합니다")
            setNegativeButton("취소", null)
            setPositiveButton("동의"){ _, _ ->
                requestReadExternalStorage()
            }
        }.show()
    }
    private fun loadImage() {
        imageLoadLauncher.launch("image/*")

    }



    private fun requestReadExternalStorage(){
        //이제 External Storage 안되서 MEDIA IMAGES로 해야됨
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES) , REQUEST_READ_MEDIA_IMAGES)
    }


    private fun updateImages(uriList: List<Uri>){
        val images = uriList.map {
            ImageItems.Image(it)
        }
                                                    //apply는 모른 명령 수행후 새로운 인스턴스를 반환하니깐 return값이 toMutableList()
        val updatedImages = imageAdapter.currentList.toMutableList().apply { addAll(images) }
        imageAdapter.submitList(updatedImages)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            REQUEST_READ_MEDIA_IMAGES -> {
                val resultCode = grantResults.firstOrNull() ?: PackageManager.PERMISSION_GRANTED
                if(resultCode == PackageManager.PERMISSION_GRANTED) {
                    loadImage()
                }
            }
        }
    }

    companion object {
        const val REQUEST_READ_MEDIA_IMAGES = 100
    }
}

