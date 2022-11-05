package com.example.imagesearchapp.presentation.screen.imagedetail

import android.Manifest
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.imagesearchapp.presentation.R
import com.example.imagesearchapp.presentation.databinding.FragmentImageDetailBinding
import com.example.imagesearchapp.presentation.screen.DataBindingFragment
import com.example.imagesearchapp.presentation.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.ByteArrayOutputStream
import java.io.File

@AndroidEntryPoint
class ImageDetailFragment: DataBindingFragment<FragmentImageDetailBinding>(R.layout.fragment_image_detail), EasyPermissions.PermissionCallbacks {

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
        imageDetailViewModel.saveToAlbum.observe(viewLifecycleOwner, EventObserver {
            requestStoragePermission()
        })

        imageDetailViewModel.saveComplete.observe(viewLifecycleOwner, EventObserver {
            Toasty.success(requireContext(), resources.getString(R.string.detail_success), Toasty.LENGTH_SHORT, false).show()
        })

        imageDetailViewModel.saveFail.observe(viewLifecycleOwner, EventObserver {
            Toasty.error(requireContext(), resources.getString(R.string.detail_fail), Toasty.LENGTH_SHORT, false).show()
        })

        imageDetailViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it) showLoadingDialog() else hideLoadingDialog()
        })
    }

    @AfterPermissionGranted(STORAGE_PERMISSION_REQUEST_CODE)
    private fun requestStoragePermission() {
        if (EasyPermissions.hasPermissions(requireContext(), *STORAGE_PERMISSION)) {
            val byteArray = dataBinding.ivPhoto.let { view ->
                createByteArrayFromView(view, view.width, view.height)
            }

            imageDetailViewModel.apply {
                setLoading(true)
                imageSave(
                    byteArray = byteArray,
                    imagePath = requireContext().cacheDir.path + File.separator + "image",
                    fileName = "IMG_${System.currentTimeMillis()}.png"
                )
            }
        } else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.detail_storage_description),
                STORAGE_PERMISSION_REQUEST_CODE,
                *STORAGE_PERMISSION
            )
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
                .setNegativeButton(R.string.detail_cancel)
                .setPositiveButton(R.string.detail_confirm)
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
        private val STORAGE_PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        private const val STORAGE_PERMISSION_REQUEST_CODE = 121
    }
}