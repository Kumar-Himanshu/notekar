package com.notekar

import android.content.Intent
import android.graphics.Bitmap
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
class SharePictureFragment : Fragment() {
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
        bitmap = textAsBitmap(args.bitmap, 18.0f, resources.getColor(R.color.app_base_color))!!
        imageView.setImageBitmap(bitmap)
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
            Log.e("GREC", e.localizedMessage, e)
        } catch (e: IOException) {
            Log.e("GREC", e.localizedMessage, e)
        }
        return FileProvider.getUriForFile(requireContext(), requireContext().applicationContext.packageName + ".provider", imagePath)
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

    private fun textAsBitmap(text: String?, textSize: Float, textColor: Int): Bitmap? {
        var width = 400 // round
        var height = 400
//        imageView.post {
//            width = imageView.width
//            height = imageView.height
//        }
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = textSize
        paint.color = textColor
        paint.textAlign = Paint.Align.LEFT
        val baseline: Float = -paint.ascent() // ascent() is negative
//        val width = (paint.measureText(text) + 0.5f).toInt() // round
//        val height = (baseline + paint.descent() + 0.5f).toInt()
        val image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(image)
        canvas.drawText(text!!, 0.0f, baseline, paint)
        return image
    }
}