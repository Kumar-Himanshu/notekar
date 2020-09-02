package com.notekar

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.notekar.interfaces.IOnBackPressed
import com.notekar.utils.Constants
import com.notekar.utils.Utility
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


/**
 * A simple [Fragment] subclass.
 * Use the [SharePictureFragment] factory method to
 * create an instance of this fragment.
 */
class SharePictureFragment : Fragment(), IOnBackPressed {
    private val args: SharePictureFragmentArgs by navArgs()

    private lateinit var bitmap: Bitmap
    private lateinit var imageView: AppCompatImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        requireActivity().title = getString(R.string.action_share)
        requireActivity().titleColor = resources.getColor(R.color.app_base_color)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share_picture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView = view.findViewById(R.id.imgView)
    }

    override fun onResume() {
        super.onResume()
        initUI()
    }

    private fun initUI() {
//        imageView.post(Runnable {
//            bitmap = textAsBitmap(args.bitmap, 100.0f, resources.getColor(R.color.app_base_color),imageView)!!
            imageView.setImageBitmap(args.bitmap)
//        })
//        imageView.setImageBitmap(bitmap)
    }

    private fun shareAsImage(imagePath: Uri) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        sharingIntent.type = "image/*"
        sharingIntent.putExtra(Intent.EXTRA_STREAM, imagePath)
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        //removing menu options from toolbar
        menu.findItem(R.id.action_delete).isVisible = false
        menu.findItem(R.id.action_cancel).isVisible = false
        menu.findItem(R.id.action_save).isVisible = false
        menu.findItem(R.id.action_text).isVisible = false
    }

    fun saveBitmap(bitmap: Bitmap): Uri {
        val imagePath =
            File(Constants.EXTERNAL_STORAGE_PATH + "/Notekar_" + Utility.getCurrentDate() + "_" + Utility.getCurrentTime() + ".png")
        val fos: FileOutputStream
        try {
            fos = FileOutputStream(imagePath)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            Log.e("SharePictureFragment", e.localizedMessage, e)
        } catch (e: IOException) {
            Log.e("SharePictureFragment", e.localizedMessage, e)
        }
        return FileProvider.getUriForFile(
            requireContext(),
            requireContext().applicationContext.packageName + ".provider",
            imagePath
        )
//        return Uri.fromFile(imagePath)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_image -> {
                shareAsImage(saveBitmap(bitmap))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun textAsBitmap(text: String?, textSize: Float, textColor: Int, imageView:View): Bitmap? {
//        val options = BitmapFactory.Options()
//        options.inJustDecodeBounds = true
//        val bitmap = BitmapFactory.decodeResource(resources, R.id.imgView, options)
//        val imageHeight = options.outHeight
//        val imageWidth = options.outWidth
//        val imageType = options.outMimeType
        val imageHeight = imageView.height
//        val imageWidth = imageView.width
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = textSize
        paint.color = textColor
        paint.textAlign = Paint.Align.LEFT
        val baseline: Float = -paint.ascent() // ascent() is negative
        val width = (paint.measureText(text) + 0.5f).toInt() // round
//        var height = (paint.measureText(text)  + 0.5f).toInt() // round
////        var height = (baseline + paint.descent() + 0.5f).toInt()
//        if(height < 400){
//            height = 400
//        }
        val image = Bitmap.createBitmap(width, imageHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(image)
        canvas.drawText(text!!, 0.0f, baseline, paint)
        val newBitmap = resize(image, image.width / 2, 1800)
        return newBitmap
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    private fun resize(image: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap? {
        var image = image
        return if (maxHeight > 0 && maxWidth > 0) {
            val width = image.width
            val height = image.height
            val ratioBitmap = width.toFloat() / height.toFloat()
            val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()
            var finalWidth = maxWidth
            var finalHeight = maxHeight
            if (ratioMax > ratioBitmap) {
                finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
            } else {
                finalHeight = (maxWidth.toFloat() / ratioBitmap).toInt()
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true)
            image
        } else {
            image
        }
    }
}