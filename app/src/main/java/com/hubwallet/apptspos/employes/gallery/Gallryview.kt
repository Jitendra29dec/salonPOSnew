package com.hubwallet.apptspos.employes.gallery

import Gallerydetails
import Json4Kotlin_Base
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.hubwallet.apptspos.R
import com.hubwallet.apptspos.demo.ui.demo.showToast
import kotlinx.android.synthetic.main.frag_gallyview.view.*
import java.io.ByteArrayOutputStream
import java.io.IOException


class Gallryview : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = Gallryview()
    }

    private lateinit var root: View
    private lateinit var viewModel: GalleryDataViewModel
    private lateinit var mGalleyAdapter: GalleyAdapter
    private val mlistgally = ArrayList<Gallerydetails>()
    var dX = 0f
    var dY = 0f

    var imageString = ""

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.frag_gallyview, container, false)

        root.RLgallry.layoutManager = GridLayoutManager(requireContext(), 4)
        mGalleyAdapter = GalleyAdapter(requireContext(), mlistgally)
        root.RLgallry.adapter = mGalleyAdapter
        root.BTaddgalley.setOnClickListener(this)

        root.BTaddgalley.setOnTouchListener(View.OnTouchListener { view, event ->
            val newX: Float
            val newY: Float
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {

                    dX = view.x - event.rawX
                    dY = view.y - event.rawY
                }
                MotionEvent.ACTION_MOVE -> {

                    // var displacement = event.rawX + dX
                    newX = event.rawX + dX
                    newY = event.rawY + dY

                    if ((newX <= 0 || newX >= 120 - view.width) || (newY <= 0 || newY >= 200 - view.height)) {

                    }

                    view.animate()
                            .x(newX)
                            .y(newY)
                            .setDuration(0)
                            .start()
                    /*  view!!.animate()
                              .x(displacement)
                              // .y(event.getRawY() + rightDY)
                              .setDuration(0)
                              .start()*/
                }
                else -> { // Note the block
                    return@OnTouchListener false
                }
            }
            false
        })
        return root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GalleryDataViewModel::class.java)
        getService();
    }

    private fun addStylishGallry(imagepath: String, imagename: String) {
        viewModel.addstylishgallry(imagepath, "1", imagename, "stylist")
        viewModel.liveData!!.observe(viewLifecycleOwner, Observer {
            showToast(it.message)
        })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.BTaddgalley -> {
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 102)

            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 102 && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
            val bitmap: Bitmap
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, imageUri)
                // root.profileImg.setImageBitmap(bitmap)
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
                val byteArray = stream.toByteArray()
                imageString = Base64.encodeToString(byteArray, Base64.DEFAULT)
                addStylishGallry(imageString, "mystylish")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            Toast.makeText(context, "Error Occurred please try again.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getService() {
        viewModel.getStylishgallery("1", "stylist")
        viewModel.liveDataGalley!!.observe(viewLifecycleOwner, Observer<Json4Kotlin_Base> {
            if (it.status == 1) {
                mlistgally.addAll(it.result)
                mGalleyAdapter.notifyDataSetChanged()
            }
        })
    }


}