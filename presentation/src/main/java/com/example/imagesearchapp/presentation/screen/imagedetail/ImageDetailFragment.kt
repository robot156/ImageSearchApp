package com.example.imagesearchapp.presentation.screen.imagedetail

import android.Manifest
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.imagesearchapp.presentation.R
import com.example.imagesearchapp.presentation.databinding.FragmentImageDetailBinding
import com.example.imagesearchapp.presentation.screen.DataBindingFragment
import com.example.imagesearchapp.presentation.utils.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import java.io.ByteArrayOutputStream
import java.io.File

@AndroidEntryPoint
class ImageDetailFragment : DataBindingFragment<FragmentImageDetailBinding>(R.layout.fragment_image_detail), EasyPermissions.PermissionCallbacks {

    private val imageDetailViewModel: ImageDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = imageDetailViewModel
        }

        initListener()
        initObserver()
    }

    private fun initListener() {
        with(dataBinding) {
            toolbar.apply {
                setNavigationOnClickListener {
                    if (!findNavController().navigateUp()) {
                        requireActivity().finish()
                    }
                }
            }
        }
    }

    private fun initObserver() {
        launchAndRepeatWithViewLifecycle {
            launch {
                imageDetailViewModel.saveToAlbum.collect {
                    requestStoragePermission()
                }
            }

            launch {
                imageDetailViewModel.saveComplete.collect {
                    Toasty.success(requireContext(), resources.getString(R.string.detail_success), Toasty.LENGTH_SHORT, false).show()
                }
            }

            launch {
                imageDetailViewModel.saveFail.collect {
                    Toasty.error(requireContext(), resources.getString(R.string.detail_fail), Toasty.LENGTH_SHORT, false).show()
                }
            }
        }
    }

    private fun requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            captureImage()
        } else if (EasyPermissions.hasPermissions(requireContext(), *getStoragePermission())) {
            captureImage()
        } else {
            EasyPermissions.requestPermissions(
                PermissionRequest.Builder(this, STORAGE_PERMISSION_REQUEST_CODE, *getStoragePermission())
                    .setRationale(R.string.detail_storage_description)
                    .setPositiveButtonText(R.string.all_confirm)
                    .setNegativeButtonText(R.string.all_cancel)
                    .setTheme(R.style.Theme_ImageSearchApp_Search)
                    .build()
            )
        }
    }

    private fun captureImage() {
        showLoadingDialog()
        lifecycleScope.launch {
            delay(100)
            val byteArray = dataBinding.ivUnsplashImage.let { view ->
                createByteArrayFromView(view, view.width, view.height)
            }

            imageDetailViewModel.imageSave(
                byteArray = byteArray,
                imagePath = requireContext().cacheDir.path + File.separator + "image",
                fileName = "IMG_${System.currentTimeMillis()}.png"
            )
            hideLoadingDialog()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) = Unit

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog
                .Builder(this)
                .setNegativeButton(R.string.all_cancel)
                .setPositiveButton(R.string.all_confirm)
                .setTitle(R.string.detail_permission)
                .setRationale(R.string.detail_storage_description)
                .build()
                .show()
        }
    }


    private fun createByteArrayFromView(view: View, width: Int, height: Int): ByteArray {
        return ByteArrayOutputStream().use { output ->
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            view.background?.draw(canvas) ?: canvas.drawColor(Color.WHITE)
            view.draw(canvas)

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
            bitmap.recycle()
            output.toByteArray()
        }
    }

    companion object {
        fun getStoragePermission() = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        private const val STORAGE_PERMISSION_REQUEST_CODE = 121
    }
}