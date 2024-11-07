package com.example.tp_mobile.views

import android.app.Dialog
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.tp_mobile.MainActivity
import com.example.tp_mobile.R
import com.example.tp_mobile.databinding.ActivityApodnavigationControllerBinding
import com.example.tp_mobile.model.Apod
import com.example.tp_mobile.views.apod.ApodViewModel
import com.example.tp_mobile.views.login.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class APODNavigationControllerActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var backButton: ImageButton
    private lateinit var viewModel: ApodViewModel
    private lateinit var binding: ActivityApodnavigationControllerBinding
    var apod: Apod? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apodnavigation_controller)

        viewModel = ViewModelProvider(this)[ApodViewModel::class.java]

        setUpNavBar()
        setUpObservers()
        viewModel.fetchApod()
    }

    private fun setUpObservers() {
        apod = viewModel.apod.value

        viewModel.apod.observe(this) {
            updateView()
        }
    }

    private fun updateView() {
        val apod = viewModel.apod.value
        val imageView: ImageView = findViewById(R.id.apod_image)
        val videoView: VideoView = findViewById(R.id.apod_video)
        val inflater = layoutInflater

        //Setting up fullscreen
        val dialogView = inflater.inflate(R.layout.fullscreen_media, null)
        val closeButton: ImageButton = dialogView.findViewById(R.id.close_button)
        val downloadButton: ImageButton = dialogView.findViewById(R.id.download_button)

        val fullscreenVideoView: VideoView = dialogView.findViewById(R.id.video)
        val fullscreenImageView: ImageView = dialogView.findViewById(R.id.image)

        val dialog = Dialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.setContentView(dialogView)
        dialog.setCancelable(true)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        if (apod?.mediaType == "image" && apod.url != null) {
            imageView.visibility = View.VISIBLE
            videoView.visibility = View.GONE
            imageView.load(apod.url)

            imageView.setOnClickListener {
                fullscreenVideoView.visibility = View.GONE
                fullscreenImageView.visibility = View.VISIBLE

                fullscreenImageView.load(apod.url)

                downloadButton.setOnClickListener {
                    downloadButton.isEnabled = false
                    downloadFile(this, apod.url!!, "image.jpg", "image/jpeg")
                    downloadButton.postDelayed({
                        downloadButton.isEnabled = true
                    }, 3000)
                }

                dialog.show()
            }
        } else if (apod?.mediaType == "video" && apod.url != null) {
            val videoUri = Uri.parse(apod.url)
            videoView.setVideoURI(videoUri)

            videoView.visibility = View.VISIBLE
            imageView.visibility = View.GONE

            videoView.start()

            videoView.setOnCompletionListener {
                videoView.start()
            }

            videoView.setOnClickListener {
                fullscreenVideoView.visibility = View.VISIBLE
                fullscreenImageView.visibility = View.GONE

                fullscreenVideoView.setVideoURI(videoUri)

                val mediaController = MediaController(this)
                fullscreenVideoView.setMediaController(mediaController)
                mediaController.setAnchorView(fullscreenVideoView)

                fullscreenVideoView.start()

                downloadButton.setOnClickListener {
                    downloadButton.isEnabled = false
                    downloadFile(this, apod.url!!, "video.mp4", "video/mp4")
                    downloadButton.postDelayed({
                        downloadButton.isEnabled = true
                    }, 3000)
                }

                dialog.show()
            }
        }

        val date: TextView = findViewById(R.id.image_date)
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)

        val formattedDate = apod?.date?.let {
            val parsedDate = inputFormat.parse(it)
            outputFormat.format(parsedDate)
        }

        date.text = formattedDate

        val title: TextView = findViewById(R.id.image_title)
        title.text = apod?.title

        val copyright: TextView = findViewById(R.id.image_copyright)
        val text = "Credits & Copyright : " + apod?.copyright
        copyright.text = text

        val explanation: TextView = findViewById(R.id.apod_explanation)
        explanation.text = apod?.explanation

    }

    private fun setUpNavBar() {
        bottomNavigationView = findViewById(R.id.navBar)
        bottomNavigationView.selectedItemId = R.id.apod

        bottomNavigationView.setOnItemSelectedListener {
            try {
                when (it.itemId) {
                    R.id.home -> {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        true
                    }

                    R.id.profile -> {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        true
                    }

                    else -> false
                }
            } catch (e: Exception) {
                throw e
            }
        }

        backButton = findViewById(R.id.back)
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            bottomNavigationView.selectedItemId = R.id.home
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                bottomNavigationView.selectedItemId = R.id.home
            }
        })

    }

    fun downloadFile(context: Context, url: String, baseFileName: String, mimeType: String) {
        val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloadDir, baseFileName)
        if (file.exists()) {
            Toast.makeText(context, "$baseFileName existe déjà. Téléchargement ignoré.", Toast.LENGTH_SHORT).show()
            return
        }

        val request = DownloadManager.Request(Uri.parse(url))
        request.setTitle("Téléchargement en cours")
        request.setDescription("Téléchargement de $baseFileName")
        request.setMimeType(mimeType)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        val uniqueFileName = "${System.currentTimeMillis()}_$baseFileName"
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uniqueFileName)

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)

        Toast.makeText(context, "Téléchargement lancé pour $baseFileName", Toast.LENGTH_SHORT).show()
    }
}